create table if not exists users(
user_id BIGSERIAL primary key,
login varchar(255),
password varchar(255)
);

INSERT INTO users(login, password) VALUES ('root', 'root');