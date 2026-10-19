CREATE TABLE department (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            name VARCHAR(255),
                            active BOOLEAN NOT NULL,
                            created_at DATETIME,
                            updated_at DATETIME,

                            PRIMARY KEY (id)
);