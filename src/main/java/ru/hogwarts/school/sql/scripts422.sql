CREATE TABLE People (
id INT PRIMARY KEY,
name VARCHAR(255),
age INT,
has_license BOOLEAN
);

CREATE TABLE Cars (
id INT PRIMARY KEY,
brand VARCHAR(255),
model VARCHAR(255),
cost DECIMAL(10, 2)
);

CREATE TABLE People_Cars (
person_id INT,
car_id INT,
PRIMARY KEY (person_id, car_id),
FOREIGN KEY (person_id) REFERENCES People(id),
FOREIGN KEY (car_id) REFERENCES Cars(id)
);