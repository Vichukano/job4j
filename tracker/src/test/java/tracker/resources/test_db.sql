CREATE TABLE IF NOT EXISTS test (
  item_id SERIAL PRIMARY KEY,
  item_name VARCHAR(50) NOT NULL,
  item_desc VARCHAR(50) NOT NULL,
  created TIMESTAMP NOT NULL
);