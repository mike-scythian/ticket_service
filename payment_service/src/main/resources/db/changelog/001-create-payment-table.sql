CREATE TABLE IF NOT EXISTS payment (
    id BIGINT NOT NULL PRIMARY KEY,
    status VARCHAR(10) NOT NULL,
    client_name VARCHAR(100) NOT NULL,
    amount DECIMAL(6,2)
);