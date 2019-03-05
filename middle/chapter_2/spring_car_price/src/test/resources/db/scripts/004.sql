CREATE TABLE IF NOT EXISTS cars(
car_id BIGSERIAL PRIMARY KEY UNIQUE,
name VARCHAR(255),
price DOUBLE PRECISION,
color VARCHAR(255),
mileage INTEGER,
sold BOOLEAN,
description VARCHAR(255),
createDate TIMESTAMP,
body_id BIGSERIAL,
CONSTRAINT body_id_fk
FOREIGN KEY(body_id)
REFERENCES car_body(body_id),
engine_id BIGSERIAL,
CONSTRAINT engine_id_fk
FOREIGN KEY(engine_id)
REFERENCES car_engine(engine_id),
transmission_id BIGSERIAL,
CONSTRAINT transmission_id_fk
FOREIGN KEY(transmission_id)
REFERENCES car_transmission(transmission_id),
image_id BIGSERIAL,
CONSTRAINT image_id_fk
FOREIGN KEY(image_id)
REFERENCES images(image_id),
user_id BIGSERIAL,
CONSTRAINT user_id_fk
FOREIGN KEY(user_id)
REFERENCES users(user_id)
);

INSERT INTO cars(name, price, color, mileage, sold,
description, createdate, body_id, engine_id, transmission_id,
image_id, user_id) VALUES ('FORD', 1050, 'RED', 2000, false, 'PERFECT CAR!',
current_timestamp, 1, 1, 1, 1, 1);