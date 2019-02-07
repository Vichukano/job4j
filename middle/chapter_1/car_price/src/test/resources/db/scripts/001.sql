CREATE TABLE IF NOT EXISTS car_body (
  body_id BIGSERIAL PRIMARY KEY UNIQUE,
  type    VARCHAR(255)
);

INSERT INTO car_body (body_id, type)
VALUES (1, 'sedan'), (2, 'hatchback'), (3, 'crossover');

CREATE TABLE IF NOT EXISTS car_engine (
  engine_id BIGSERIAL PRIMARY KEY UNIQUE,
  type      VARCHAR(255)
);

INSERT INTO car_engine (type)
VALUES ('petrol'), ('diesel'), ('hybrid');

CREATE TABLE IF NOT EXISTS car_transmission (
  transmission_id BIGSERIAL PRIMARY KEY UNIQUE,
  type            VARCHAR(255)
);

INSERT INTO car_transmission (type)
VALUES ('mechanical'), ('automatic'), ('robot');
