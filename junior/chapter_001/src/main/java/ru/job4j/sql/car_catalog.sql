-- Создание базы данных:
CREATE DATABASE car_catalog;

-- Создание таблицы кузовов:
CREATE TABLE car_body (
  car_body_id SERIAL PRIMARY KEY,
  name VARCHAR(40) NOT NULL
);

-- Создание таблицы двигателей:
CREATE TABLE engine (
  engine_id SERIAL PRIMARY KEY,
  name VARCHAR(40) NOT NULL
);

-- Создание таблицы коробок передач:
CREATE TABLE transmission (
  transmission_id SERIAL PRIMARY KEY,
  name VARCHAR(40) NOT NULL
);

-- Создание таблицы автомобилей:
CREATE TABLE cars (
  id SERIAL PRIMARY KEY,
  name VARCHAR(40) NOT NULL,
  car_body_id INT NOT NULL,
  CONSTRAINT car_body_car_body_id_fk
  FOREIGN KEY (car_body_id) REFERENCES car_body(car_body_id),
  engine_id INT NOT NULL,
  CONSTRAINT engine_engine_id_fk
  FOREIGN KEY (engine_id) REFERENCES engine(engine_id),
  transmission_id INT NOT NULL,
  CONSTRAINT transmission_transmission_id_fk
  FOREIGN KEY (transmission_id) REFERENCES transmission(transmission_id)
);

-- Заполение таблиц данными:
INSERT INTO car_body(name) VALUES ('синий'), ('красный'), ('белый'), ('зеленый'), ('черный');

INSERT INTO engine(name) VALUES ('лада'), ('форд'), ('айсин'), ('тойота'), ('бмв');

INSERT INTO transmission(name) VALUES ('ручная'), ('механическая'), ('робот'), ('спортивная'), ('ДСГ');

INSERT INTO cars(name, car_body_id, engine_id, transmission_id) VALUES ('королла',
                                                                        (SELECT car_body_id FROM car_body
                                                                        WHERE car_body.name = 'синий'),
                                                                        (SELECT engine_id FROM engine
                                                                        WHERE engine.name = 'тойота'),
                                                                        (SELECT transmission_id FROM transmission
                                                                        WHERE transmission.name = 'робот'));

INSERT INTO cars(name, car_body_id, engine_id, transmission_id) VALUES ('форд',
                                                                        (SELECT car_body_id FROM car_body
                                                                        WHERE car_body.name = 'красный'),
                                                                        (SELECT engine_id FROM engine
                                                                        WHERE engine.name = 'форд'),
                                                                        (SELECT transmission_id FROM transmission
                                                                        WHERE transmission.name = 'механическая'));

INSERT INTO cars(name, car_body_id, engine_id, transmission_id) VALUES ('БМВ Х5',
                                                                        (SELECT car_body_id FROM car_body
                                                                        WHERE car_body.name = 'черный'),
                                                                        (SELECT engine_id FROM engine
                                                                        WHERE engine.name = 'бмв'),
                                                                        (SELECT transmission_id FROM transmission
                                                                        WHERE transmission.name = 'ДСГ'));
-- Запросы:
-- 1. Вывести список всех машин и все привязанные к ним детали.
SELECT c.id, c.name, b.name, e.name, t.name FROM cars as c
  LEFT OUTER JOIN car_body as b
    ON c.car_body_id = b.car_body_id
  LEFT OUTER JOIN engine as e
    ON c.engine_id = e.engine_id
  LEFT OUTER JOIN transmission as t
    ON c.transmission_id = t.transmission_id;

-- 2. Вывксти список кузовов, которые не используются.
SELECT b.car_body_id, b.name FROM cars as c
  RIGHT OUTER JOIN car_body as b
    ON c.car_body_id = b.car_body_id
WHERE c.id IS NULL;

-- 3. Вывксти список двигателей, которые не используются.
SELECT e.engine_id, e.name FROM cars as c
  RIGHT OUTER JOIN engine as e
    ON c.engine_id = e.engine_id
WHERE c.id IS NULL;

-- 4. Вывести список коробок передач, которые не используются.
SELECT t.transmission_id, t.name FROM cars as c
  RIGHT OUTER JOIN transmission as t
    ON c.transmission_id = t.transmission_id
WHERE c.id IS NULL;

