SELECT s.name, s.age, f.name as faculty_name
FROM students s
JOIN faculties f ON s.faculty_id = f.id;

SELECT s.name, s.age, f.name as faculty_name
FROM students s
JOIN faculties f ON s.faculty_id = f.id
WHERE s.avatar_id IS NOT NULL;