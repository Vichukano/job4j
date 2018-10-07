-- create database --
CREATE DATABASE tracker;

-- create tables --
CREATE TABLE roles (
  role_id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(30)
);

CREATE TABLE rules (
  rule_id SERIAL PRIMARY KEY,
  rule_name CHARACTER VARYING(30)
);

CREATE TABLE roles_rules (
  role_id int NOT NULL,
  CONSTRAINT roles_role_id_fk
  FOREIGN KEY (role_id) REFERENCES roles(role_id),
  rule_id INT NOT NULL,
  CONSTRAINT rules_rule_id_fk
  FOREIGN KEY (rule_id) REFERENCES rules(rule_id)
);

CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(30) NOT NULL,
  password CHARACTER VARYING(30),
  role_id INT NOT NULL,
  CONSTRAINT roles_role_id_fk
  FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE categories (
  category_id SERIAL PRIMARY KEY,
  category_name CHARACTER VARYING(30) NOT NULL
);

CREATE TABLE states (
  state_id SERIAL PRIMARY KEY,
  state_name CHARACTER VARYING(30) NOT NULL
);

CREATE TABLE items (
  item_id SERIAL PRIMARY KEY,
  name CHARACTER VARYING(30) NOT NULL ,
  description CHARACTER VARYING(100),
  created TIMESTAMP,
  user_id INT UNIQUE NOT NULL,
  CONSTRAINT users_user_id_fk
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  category_id INT NOT NULL,
  CONSTRAINT categories_category_id_fk
  FOREIGN KEY (category_id) REFERENCES categories(category_id),
  state_id int NOT NULL,
  CONSTRAINT states_state_id_fk
  FOREIGN KEY (state_id) REFERENCES states(state_id)
);

CREATE TABLE files (
  file_id SERIAL PRIMARY KEY,
  file_path CHARACTER VARYING(100),
  item_id INT NOT NULL,
  CONSTRAINT items_item_id_fk
  FOREIGN KEY (item_id) REFERENCES items(item_id)
);

CREATE TABLE comments (
  com_id SERIAL PRIMARY KEY NOT NULL,
  comment CHARACTER VARYING(250),
  item_id INT NOT NULL,
  CONSTRAINT items_item_id_fk
  FOREIGN KEY (item_id) REFERENCES items(item_id)
);

-- Insert values into tables --
-- roles --
INSERT INTO roles (name) VALUES ('admin'), ('guest'), ('user');

-- rules --
INSERT INTO rules(rule_name) VALUES ('reed'), ('add'), ('update'), ('delete');

-- roles_rules --
INSERT INTO roles_rules(role_id, rule_id)
VALUES ((SELECT role_id FROM roles WHERE name = 'admin'),
        (SELECT rule_id FROM rules WHERE rule_name = 'reed')),
        ((SELECT role_id FROM roles WHERE name = 'admin'),
        (SELECT rule_id FROM rules WHERE rule_name = 'add')),
        ((SELECT role_id FROM roles WHERE name = 'admin'),
         (SELECT rule_id FROM rules WHERE rule_name = 'update')),
        ((SELECT role_id FROM roles WHERE name = 'admin'),
        (SELECT rule_id FROM rules WHERE rule_name = 'delete'));
INSERT INTO roles_rules(role_id, rule_id)
VALUES ((SELECT role_id FROM roles WHERE name = 'guest'),
        (SELECT rule_id FROM rules WHERE rule_name = 'reed'));
INSERT INTO roles_rules(role_id, rule_id)
VALUES ((SELECT role_id FROM roles WHERE name = 'user'),
        (SELECT rule_id FROM rules WHERE rule_name = 'reed')),
       ((SELECT role_id FROM roles WHERE name = 'user'),
        (SELECT rule_id FROM rules WHERE rule_name = 'add'));

-- users --
INSERT INTO users(name, password, role_id)
VALUES ('Victor', '123', (SELECT role_id FROM roles WHERE roles.name = 'admin')),
       ('Connor', 'king_id_back', (SELECT role_id FROM roles WHERE roles.name = 'guest')),
       ('Habib', 'eagle', (SELECT role_id FROM roles WHERE roles.name = 'user'));

-- categories --
INSERT INTO categories(category_name) VALUES ('item'), ('bug');

-- states --
INSERT INTO states(state_name) VALUES ('new'), ('in_progress'), ('done');

-- items --
INSERT INTO items(name, description, created, user_id, category_id, state_id)
VALUES ('sql', 'не получается выполнить задание по SQL', '07.10.2018', (SELECT user_id FROM users WHERE users.name = 'Victor'),
        (SELECT category_id FROM categories WHERE category_name = 'item'), (SELECT state_id FROM states WHERE state_name = 'new'));

-- files --
INSERT INTO files(file_path, item_id) VALUES ('127.0.0.1//file.jpg',
                                              (SELECT item_id FROM items WHERE name = 'sql'));

-- comments --
INSERT INTO comments (comment, item_id) VALUES ('SQL очень сложная тема, сразу не осилю',
                                                (SELECT item_id FROM items WHERE name = 'sql'));


