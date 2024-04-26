SELECT * FROM student;
SELECT * FROM student WHERE age BETWEEN 10 AND 20;
SELECT name FROM student;
SELECT * FROM student WHERE name LIKE '%O%';
SELECT * FROM student WHERE age < id;
SELECT * FROM student ORDER BY age;
SELECT * FROM faculty,student where faculty.id = student.id