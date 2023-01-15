CREATE TABLE IF NOT EXISTS ticket (
    id BIGINT NOT NULL PRIMARY KEY,
    flight BIGINT NOT NULL,
    payment_id BIGINT,
    FOREIGN KEY (flight) REFERENCES ticket_service.bus_flight(id)
);