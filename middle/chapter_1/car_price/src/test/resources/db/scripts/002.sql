CREATE TABLE IF NOT EXISTS users (
user_id BIGSERIAL PRIMARY KEY UNIQUE,
login VARCHAR(255),
password VARCHAR(255)
);

INSERT INTO users (login, password) VALUES ('first', '123');