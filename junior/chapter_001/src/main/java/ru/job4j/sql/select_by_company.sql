-- Create tables company and person.
CREATE TABLE company (
  id INTEGER NOT NULL,
  name character varying(30),
  CONSTRAINT company_pkey PRIMARY KEY(id)
);

CREATE TABLE person (
  id integer NOT NULL,
  name character varying(30),
  company_id INTEGER,
  CONSTRAINT person_pkey PRIMARY KEY(id));


-- Insert values into company table.
INSERT INTO company(id, name) VALUES(1, 'макдак'),(2, 'бургеркинг'), (3, 'МВД'),
  (4, 'РЖД'), (5, 'МАЙКРОСОФТ'), (6, 'ГУГЛ'), (7, 'АРМАГУС'), (8, 'ГУСАР');

-- Insert values into person table.
INSERT INTO person(id, name, company_id) VALUES(1, 'ВИКТОР', 3), (2, 'ПАША_ТЕХНИК', 3),
  (3, 'МАША', 3), (4, 'КОНОР_МАКГРЕГОР', 1), (5, 'ДАША', NULL), (6, 'САША', NULL),
  (7, 'СВЕТА', NULL), (8, 'ПЕТЯ', 5), (9, 'КОЛЯ', 4), (10, 'ТОЛЯ', 5), (11, 'ВАСЯ', 5),
  (12, 'ЖОЗЕ_АЛЬДО', 6), (13, 'ДЕРЕК_ЛЬЮИС', 7), (14, 'МОРОЗ', 7), (15, 'ЮЛЯ', 8),
  (16, 'ОЛЯ', 8), (17, 'ЮРА', 8),(18, 'ТАНК_ЭББОТ', 1), (19, 'ЮЛЯ', 1), (20, 'АРТЕМ', 4),
  (21, 'МЭТТ_ХЬЮЗ', 2);

-- 1) Retrieve in a single query:
--  names of all persons that are NOT in the company with id = 5.
SELECT p.name, c.name FROM person as p
  LEFT OUTER JOIN company as c
  ON p.company_id = c.id
WHERE p.company_id <> 5;

-- company name for each person.
SELECT p.name, c.name FROM person as p
  LEFT OUTER JOIN company as c
  ON p.company_id = c.id;

-- 2) Select the name of the company with the maximum number of persons + number of persons in this company.
SELECT c.name, COUNT(*) as person_count
FROM company AS c
LEFT OUTER JOIN person AS p
ON c.id = p.company_id
GROUP BY c.name
ORDER BY person_count DESC
LIMIT 1;