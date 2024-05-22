package ru.hogwarts.school.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(ru.hogwarts.school.exception.EntityNotFoundException::new);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        Student student = getStudent(id);
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> allStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getStudentsSameAge(int age) {
        return allStudents().stream().filter(student -> student.getAge() == age).collect(Collectors.toList());
//        return studentRepository.findStudentsSameAge(age);
    }

    public Faculty getFacultyByStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден " + studentId));
    }

    public List<Student> getStudentsByAgeRange(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Integer numberAllStudents(){
        return studentRepository.numberAllStudents();
    }

    public Integer averageAgeStudents() {
        return studentRepository.averageAgeStudents();
    }

    public Collection<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
