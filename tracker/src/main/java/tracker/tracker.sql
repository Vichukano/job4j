
CREATE TABLE IF NOT EXISTS items (
  item_id VARCHAR(200) PRIMARY KEY,
  item_name VARCHAR(50) NOT NULL,
  item_desc VARCHAR(50) NOT NULL,
  created TIMESTAMP NOT NULL
);