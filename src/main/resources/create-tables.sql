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
    drone_id      INT,
    medication_id INT,
    PRIMARY KEY (drone_id, medication_id),
    FOREIGN KEY (drone_id) REFERENCES drone (id),
    FOREIGN KEY (medication_id) REFERENCES medication (id),
    CONSTRAINT fk_drone FOREIGN KEY (drone_id) REFERENCES drone (id),
    CONSTRAINT fk_medication FOREIGN KEY (medication_id) REFERENCES medication (id)
);
