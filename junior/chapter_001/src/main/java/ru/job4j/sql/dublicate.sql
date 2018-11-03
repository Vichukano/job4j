-- Удалить дубликаты из таблицы.

-- Создание таблицы.
CREATE TABLE test (
  id SERIAL PRIMARY KEY,
  name VARCHAR(30)
);

-- Добавляем в таблицу данные, содеражщие дубликаты.
INSERT INTO test(name) VALUES ('САША'), ('ПАША'), ('САША'), ('МАША'), ('ДАША'),
  ('САША'), ('МАША'), ('ОЛЕГ'), ('ДИМА');

-- Первый способ - соединить таблицу саму с собой используя USING.
DELETE FROM test AS t1
USING test AS t2
WHERE t1.id > t2.id AND t1.name = t2.name;

-- Второй способ - пересоздать таблицу, на основе запроса без дубликатов.
CREATE TABLE test_tmp(
  id SERIAL PRIMARY KEY,
  name VARCHAR(30)
);

INSERT INTO test_tmp(name) SELECT DISTINCT name FROM test;
DROP TABLE test;
ALTER TABLE test_tmp RENAME TO test;