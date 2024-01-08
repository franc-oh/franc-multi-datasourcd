
/** 컨텐츠_파일 테이블 */
CREATE TABLE `content_file` (
  `file_seq` int(11) NOT NULL AUTO_INCREMENT,
  `content_seq` int(11) NOT NULL,
  `file_path` varchar(200) NOT NULL,
  `file_name` varchar(200) NOT NULL,
  `use_yn` char DEFAULT 'Y',
  `content_order` int(20) NOT NULL CHECK (`content_order` > 0 and `content_order` <= 10),
  `created` datetime DEFAULT current_timestamp(),
  CONSTRAINT content_file_pk PRIMARY KEY (`file_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
