CREATE TABLE employee (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          name VARCHAR(255),
                          age INT NOT NULL,
                          active BOOLEAN NOT NULL,
                          dept_id BIGINT,
                          created_at DATETIME,
                          updated_at DATETIME,

                          PRIMARY KEY (id),

                          CONSTRAINT fk_employee_department
                              FOREIGN KEY (dept_id)
                                  REFERENCES department(id)
);