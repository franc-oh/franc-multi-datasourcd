package com.franc.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franc.dao.maria1.ContentDao;
import com.franc.dao.maria2.ContentFileDao;
import com.franc.exception.BizException;
import com.franc.exception.ExceptionResult;
import com.franc.vo.maria1.ContentVO;
import com.franc.vo.maria2.ContentFileVO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MigrationService {
    private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);

    private final SqlSessionFactory sqlSessionFactory;

    private final ContentDao m1ContentDao;
    private final ContentFileDao m2ContentDao;

    private final ObjectMapper objectMapper;


    /**
     * 컨텐츠 + 컨텐츠 파일정보 가져오기
     * @return result : {content_cnt : int, contents : [ContentVO], content_files : [ContentFileVO]}
     * @throws Exception
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getContensList() throws Exception {
        Map<String, Object> result = new HashMap<>();

        logger.info("===== getContensList() Start");

        // #1. 컨텐츠 정보 가져오기 => MariaDB#1
        List<ContentVO> contentList = m1ContentDao.selectContentList();
        if(contentList.isEmpty()) {
            result.put("content_cnt", 0);
            return result;
        }

        result.put("content_cnt", contentList.size());
        result.put("contents", contentList);


        // #2. 컨텐츠 파일정보 가져오기 => MariaDB#2 TODO : 성능개선
        List<ContentFileVO> contentFileList = new ArrayList<>();
        for(ContentVO content : contentList) {
            long content_seq = content.getContent_seq();

            List<ContentFileVO> fileListByContent = m2ContentDao.selectContentFileListByContent(content_seq);
            if(!fileListByContent.isEmpty()) {
                contentFileList.addAll(fileListByContent);
            }
        }

        result.put("content_files", contentFileList);

        logger.info("===== getContensList() Finish : " + result.toString());
        return result;
    }


    /**
     * 데이터 마이그레이션 - DB이관
     * @return result : {"contents_cnt" : int
     *                  "contents_result_cnt" : int
     *                  "content_files_cnt" : int
     *                  "content_file_result_cnt" : int}
     * @throws Exception
     */
    @Transactional
    public Map<String, Object> dataMigrationFromDb() throws Exception {
        Map<String, Object> result = new HashMap<>();

        logger.info("===== dataMigrationFromDb() Start");

        // # 1. 데이터 가져오기 (MariaDBs ..)
        Map<String, Object> dataMap = getContensList();
        List<ContentVO> contents = objectMapper.convertValue(dataMap.get("contents"), new TypeReference<List<ContentVO>>(){});
        List<ContentFileVO> content_files = objectMapper.convertValue(dataMap.get("content_files"), new TypeReference<List<ContentFileVO>>(){});

        // # 2. 데이터 등록 (PostgresSQL)
        result = migrationContents(contents, content_files);

        logger.info("===== dataMigrationFromDb() Finish : " + result.toString());

        return result;
    }


    /**
     * 데이터 마이그레이션 - 엑셀이관
     * @param file
     * @return result : {"contents_cnt" : int
     *                  "contents_result_cnt" : int
     *                  "content_files_cnt" : int
     *                  "content_file_result_cnt" : int}
     * @throws Exception
     */
    @Transactional
    public Map<String, Object> dataMigrationFromExcel(MultipartFile file) throws Exception {
        Map<String, Object> result = new HashMap<>();

        logger.info("===== dataMigrationFromExcel() Start");

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        // # 1. 데이터 가져오기 (Excel)
        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }


        List<ContentVO> contents = new ArrayList<>();
        List<ContentFileVO> content_files = new ArrayList<>();

        Sheet worksheet = workbook.getSheetAt(1); // 제목행 제외
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Row row = worksheet.getRow(i);

                long content_seq = (long) row.getCell(0).getNumericCellValue();
                String content_name = row.getCell(1).getStringCellValue();
                String description = row.getCell(2).getStringCellValue();
                long file_seq = (long) row.getCell(3).getNumericCellValue();
                String file_path = row.getCell(4).getStringCellValue();
                String file_name = row.getCell(5).getStringCellValue();
                int content_order = (int) row.getCell(3).getNumericCellValue();

                // Excel Cell -> Content
                ContentVO content = ContentVO.builder()
                        .content_seq(content_seq)
                        .name(content_name)
                        .description(description)
                        .build();
                // Excel Cell -> ContentFile
                ContentFileVO content_file = ContentFileVO.builder()
                        .file_seq(file_seq)
                        .content_seq(content_seq)
                        .file_path(file_path)
                        .file_name(file_name)
                        .content_order(content_order)
                        .build();

                contents.add(content);
                content_files.add(content_file);

        }

        // # 2. 데이터 등록 (PostgresSQL)
        result = migrationContents(contents, content_files);

        logger.info("===== dataMigrationFromExcel() Finish : " + result.toString());

        return result;
    }


    /**
     * 데이터 이관처리 (INSERT)
     * @param contents [maria1.ContentVO]
     * @param content_files [maria2.ContentFileVO]
     * @return result : {"contents_cnt" : int
     *                  "contents_result_cnt" : int
     *                  "content_files_cnt" : int
     *                  "content_file_result_cnt" : int}
     * @throws Exception
     */
    @Transactional
    public Map<String, Object> migrationContents(List<ContentVO> contents, List<ContentFileVO> content_files) throws Exception {
        Map<String, Object> result = new HashMap<>();

        if(contents.isEmpty() || content_files.isEmpty())
            throw new BizException(ExceptionResult.PARAMETER_NOT_VALID);

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH); // 다건 Insert

        int content_size = contents.size(); // 조회된 Content 수
        int content_result_cnt = 0; // 이관된 Content 수
        int content_file_size = content_files.size(); // 조회된 ContentFile 수
        int content_file_result_cnt = 0; // 이관된 ContentFile 수

        // # 1. Content 일괄등록
        try {
            for (ContentVO content : contents) {
                com.franc.vo.postgres.ContentVO migContentVO =
                        objectMapper.convertValue(content, com.franc.vo.postgres.ContentVO.class);

                sqlSession.insert("com.franc.dao.postgres.ContentsDao.insertContent", migContentVO);
                content_result_cnt++;

                // 1000건씩 잘라서 INSERT
                if(content_result_cnt % 1000 == 0) {
                    sqlSession.flushStatements();
                }
            }

            sqlSession.flushStatements(); // Content 작업 끝 (잔여 건 INSERT)

            // # 2. ContentFile 일괄등록
            for (ContentFileVO content : content_files) {
                com.franc.vo.postgres.ContentFileVO migContentFileVO =
                        objectMapper.convertValue(content, com.franc.vo.postgres.ContentFileVO.class);
                sqlSession.insert("com.franc.dao.postgres.ContentsDao.insertContentFile", migContentFileVO);
                content_file_result_cnt++;

                // 1000건씩 잘라서 INSERT
                if(content_file_result_cnt % 1000 == 0) {
                    sqlSession.flushStatements();
                }
            }
        } finally {
            sqlSession.flushStatements();
            sqlSession.commit();
            sqlSession.close();
        }

        result.put("contents_cnt", content_size);
        result.put("contents_result_cnt", content_result_cnt);
        result.put("content_files_cnt", content_file_size);
        result.put("content_file_result_cnt", content_file_result_cnt);

        return result;
    }

}
