CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    type VARCHAR(1) NOT NULL,
);

CREATE TABLE IF NOT EXISTS tickets (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(20) DEFAULT 'OPEN',
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users(username,password,type) values ('ADMIN','1234','A');
INSERT INTO users(username,password,type) values ('USER','1234','U');