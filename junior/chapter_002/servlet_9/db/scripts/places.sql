--Create table with places.
CREATE TABLE IF NOT EXISTS places_default (
  id       SERIAL PRIMARY KEY,
  row      INT NOT NULL,
  col      INT NOT NULL,
  cost     NUMERIC(5, 2),
  reserved BOOLEAN DEFAULT FALSE
);

--Insert values into places table.
INSERT INTO places_default (row, col, cost)
VALUES (1, 1, 900.00), (1, 2, 900.00), (1, 3, 900.00), (1, 4, 900.00), (1, 5, 900.00),
  (2, 1, 800.00), (2, 2, 800.00), (2, 3, 800.00), (2, 4, 800.00), (2, 5, 800.00),
  (3, 1, 600.00), (3, 2, 600.00), (3, 3, 600.00), (3, 4, 600.00), (3, 5, 600.00),
  (4, 1, 400.00), (4, 2, 400.00), (4, 3, 400.00), (4, 4, 400.00), (4, 5, 400.00),
  (5, 1, 200.00), (5, 2, 200.00), (5, 3, 200.00), (5, 4, 200.00), (5, 5, 200.00);

--Create table customers.
CREATE TABLE IF NOT EXISTS customers (
  id       SERIAL PRIMARY KEY,
  name     VARCHAR(50) NOT NULL,
  phone    VARCHAR(50) NOT NULL,
  place_id INT CONSTRAINT place_id_fk
  REFERENCES places_default (id),
  row      INT,
  cal      INT
);