CREATE TABLE avatar_items
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  name TEXT NOT NULL,
  description TEXT NOT NULL,
  weight DOUBLE NOT NULL,
  damage DOUBLE NOT NULL,
  owner BIGINT DEFAULT 0,
  PRIMARY KEY (id)
);
