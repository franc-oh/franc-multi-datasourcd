
/** 컨텐츠 테이블 */
CREATE TABLE IF NOT EXISTS content (
  content_seq int(11) NOT NULL AUTO_INCREMENT,
  name varchar(250) NOT NULL,
  description text DEFAULT NULL,
  use_yn char Default 'Y',
  created datetime DEFAULT current_timestamp(),
  CONSTRAINT content_pk PRIMARY KEY (content_seq)
);