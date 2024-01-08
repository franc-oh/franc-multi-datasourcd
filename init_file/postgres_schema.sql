/** 컨첸츠 테이블 (Maria1으로 부터 이관될...) */
CREATE TABLE test.content (
	content_seq serial4 NOT NULL,
	content_name varchar(250) NOT NULL,
	description text NULL,
	is_valid bool default true,
	created timestamp NULL DEFAULT now(),
	CONSTRAINT content_pk PRIMARY KEY (content_seq)
);


/** 컨텐츠_파일 테이블 (Maria2로 부터 이관될...) */
CREATE TABLE test.content_file (
	file_seq serial4 NOT NULL,
	content_seq int4 NOT NULL,
	file_path varchar(200) NOT NULL,
	file_name varchar(200) NOT NULL,
	is_valid bool default true,
	content_order int4 NOT NULL,
	created timestamp NULL DEFAULT now(),
	CONSTRAINT content_file_pk PRIMARY KEY (file_seq)
);
