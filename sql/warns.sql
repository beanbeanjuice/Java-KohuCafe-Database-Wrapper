CREATE TABLE warns
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  user_id BIGINT NOT NULL,
  user_nickname TEXT NOT NULL,
  issuer_id BIGINT NOT NULL,
  issuer_nickname TEXT NOT NULL,
  date TEXT NOT NULL,
  reason TEXT NOT NULL,
  active TINYINT DEFAULT 1,
  PRIMARY KEY (id)
);
