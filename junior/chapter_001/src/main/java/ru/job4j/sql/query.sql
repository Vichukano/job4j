-- Создание базы данных.
CREATE DATABASE product_shop;

-- Создание таблиц.
CREATE TABLE type (
  id   SERIAL PRIMARY KEY,
  name CHARACTER VARYING(30) NOT NULL
);

CREATE TABLE product (
  id           SERIAL PRIMARY KEY,
  name         CHARACTER VARYING(30) NOT NULL,
  type_id      INT                   NOT NULL,
  CONSTRAINT type_id_fk
  FOREIGN KEY (type_id) REFERENCES type (id),
  expired_date TIMESTAMP             NOT NULL,
  price        DECIMAL(6, 2)
);

-- Заполнение таблиц данными.
INSERT INTO type (name) VALUES ('СЫР'), ('Печенье'),
  ('ШОКОЛАД'), ('МОЛОКО'), ('КОНФЕТЫ'), ('МЯСО'), ('МАСЛО');

INSERT INTO product (name, type_id, expired_date, price) VALUES
  ('ПАРМЕЗАН', (SELECT id FROM type WHERE name = 'СЫР'), '10.11.2018', 12.30);

INSERT INTO product (name, type_id, expired_date, price) VALUES
  ('ОЛЕНИНА', (SELECT id FROM type WHERE type.name = 'МЯСО'), '10.12.2018', 40.00);

INSERT INTO product (name, type_id, expired_date, price) VALUES
  ('РОМАЩКА', (SELECT id FROM type WHERE type.name = 'КОНФЕТЫ'), '10.11.2018', 2.30);

INSERT INTO product (name, type_id, expired_date, price) VALUES
  ('АЛЕНКА', (SELECT id FROM type WHERE type.name = 'ШОКОЛАД'), '10.11.2018', 6.45);

INSERT INTO product (name, type_id, expired_date, price) VALUES
  ('КЕФИР', (SELECT id FROM type WHERE type.name = 'МОЛОКО'), '10.01.2019', 5.50);

INSERT INTO product (name, type_id, expired_date, price) VALUES
  ('ВКУСНОЕ МОРОЖЕНОЕ', (SELECT id FROM type WHERE type.name = 'МОЛОКО'), '10.12.2018', 11.30);

INSERT INTO product (name, type_id, expired_date, price) VALUES
  ('ПРОТИВНОЕ МОРОЖЕННОЕ', (SELECT id FROM type WHERE type.name = 'МОЛОКО'), '10.11.2018', 31.00);

-- 1. Написать запрос получение всех продуктов с типом "СЫР".
SELECT * FROM product AS p
INNER JOIN type AS t
ON p.type_id = t.id
WHERE t.name = 'СЫР';

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное".
SELECT * FROM product as p
INNER JOIN type as t
ON p.type_id = t.id
WHERE p.name LIKE '%МОРОЖЕН%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
SELECT * FROM product as p
INNER JOIN type as t
ON p.type_id = t.id
WHERE p.expired_date >= '01-11-2018'
AND p.expired_date <= '30-11-2018';

-- 4. Написать запрос, который выводит самый дорогой продукт.
SELECT * FROM product as p
INNER JOIN type as t
ON p.type_id = t.id
WHERE p.price = (SELECT max(price) FROM product);

-- 5. Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT count(p.id) FROM product as p
INNER JOIN type as t
ON p.type_id = t.id
WHERE t.name = 'МОЛОКО';

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT * FROM product as p
INNER JOIN type as t
ON p.type_id = t.id
WHERE t.name IN ('СЫР', 'МОЛОКО');

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
-- добавление в таблицу колонки in_store - количество продуктов.
ALTER TABLE product ADD COLUMN in_store INTEGER;
UPDATE product SET in_store = 20
WHERE name = 'ПАРМЕЗАН';
UPDATE product SET in_store = 30
WHERE name = 'ОЛЕНИНА';
UPDATE product SET in_store = 9
WHERE name = 'РОМАЩКА';
UPDATE product SET in_store = 5
WHERE name = 'АЛЕНКА';
UPDATE product SET in_store = 10
WHERE name = 'КЕФИР';
UPDATE product SET in_store = 6
WHERE name = 'ВКУСНОЕ МОРОЖЕНОЕ';
UPDATE product SET in_store = 60
WHERE name = 'ПРОТИВНОЕ МОРОЖЕННОЕ';

-- Запрос:
SELECT t.name FROM type as t
INNER JOIN product as p
ON t.id = p.type_id
WHERE p.in_store < 10;

-- 8. Вывести все продукты и их тип.
SELECT * FROM product as p
INNER JOIN type as t
ON p.type_id = t.id;