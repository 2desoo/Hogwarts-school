ALTER TABLE students
ADD CONSTRAINT check_age CHECK (age >= 16);

ALTER TABLE students
ADD CONSTRAINT unique_name UNIQUE (name),
ADD CONSTRAINT not_null_name CHECK (name <> '');

ALTER TABLE faculties
ADD CONSTRAINT unique_name_color UNIQUE (name, color);

ALTER TABLE students
ALTER COLUMN age SET DEFAULT 20;