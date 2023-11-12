CREATE TABLE IF NOT EXISTS drone
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    serial_number    VARCHAR(100) UNIQUE                                                        NOT NULL,
    model            ENUM ('LIGHTWEIGHT', 'MIDDLEWEIGHT', 'CRUISERWEIGHT', 'HEAVYWEIGHT')       NOT NULL,
    weight_limit     INT                                                                        NOT NULL CHECK (weight_limit <= 500),
    battery_capacity INT                                                                        NOT NULL,
    state            ENUM ('IDLE', 'LOADING', 'LOADED', 'DELIVERING', 'DELIVERED', 'RETURNING') NOT NULL
);

CREATE TABLE IF NOT EXISTS medication
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    name   VARCHAR(255) NOT NULL,
    weight INT          NOT NULL,
    code   VARCHAR(50)  NOT NULL,
    image  VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS drone_medication
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    drone_id      INT,
    medication_id INT,
    quantity      INT,
    FOREIGN KEY (drone_id) REFERENCES Drone (id),
    FOREIGN KEY (medication_id) REFERENCES Medication (id)
);

CREATE TABLE IF NOT EXISTS audit_log
(
    id                  INT AUTO_INCREMENT PRIMARY KEY,
    drone_id            INT,
    drone_serial_number VARCHAR(100),
    event_description   VARCHAR(255),
    event_timestamp     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
