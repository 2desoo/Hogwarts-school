package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    Student deleteStudent(Long id);

    Collection<Student> getStudentsSameAge(int age);

    Faculty getFacultyByStudent(Long studentId);

    List<Student> getStudentsByAgeRange(int min, int max);
}
