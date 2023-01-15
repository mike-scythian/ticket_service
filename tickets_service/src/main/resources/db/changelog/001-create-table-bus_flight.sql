CREATE TABLE IF NOT EXISTS bus_flight (
    id BIGINT NOT NULL PRIMARY KEY,
    point_departure VARCHAR(255) NOT NULL,
    point_arrival VARCHAR(255) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    price DECIMAL(6,2),
    tickets_amount INT NOT NULL
);