package ru.hogwarts.school.service;

import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;

/*
Methods for the service
 */
public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Student student);

    Student deleteStudent(Long id);

    Collection<Student> getStudentsSameAge(int age);

    Faculty getFacultyByStudent(Long studentId);

    List<Student> getStudentsByAgeRange(Integer min, Integer max);

    Integer numberAllStudents();

    Integer averageAgeStudents();

    Collection<Student> getLastFiveStudents();

    double getAverageAgeStudents();

    List<String> getAllStudentByNameIsA();

    void printParallel();

    void printSynchronized();

    void printStudentNameSync(Student student);
}
