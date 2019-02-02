CREATE TABLE car_body (
  body_id BIGSERIAL PRIMARY KEY,
  type    VARCHAR(255)
);

INSERT INTO car_body (type)
VALUES ('sedan'), ('hatchback'), ('crossover');

CREATE TABLE car_engine (
  engine_id BIGSERIAL PRIMARY KEY,
  type      VARCHAR(255)
);

INSERT INTO car_engine (type)
VALUES ('petrol'), ('diesel'), ('hybrid');

CREATE TABLE car_transmission (
  transmission_id BIGSERIAL PRIMARY KEY,
  type            VARCHAR(255)
);

INSERT INTO car_transmission (type)
VALUES ('mechanical'), ('automatic'), ('robot');

