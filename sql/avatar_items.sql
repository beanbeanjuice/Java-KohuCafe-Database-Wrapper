CREATE TABLE avatar_items
(
  id BIGINT NOT NULL AUTO_INCREMENT,
  name TEXT NOT NULL,
  description TEXT NOT NULL,
  image_url TEXT NOT NULL,
  total INT NOT NULL DEFAULT -1,
  weight DOUBLE NOT NULL,
  damage DOUBLE NOT NULL,
  PRIMARY KEY (id)
);
