package ru.hogwarts.school.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final static Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        log.info("Created student");
        return studentRepository.save(student);
    }

    public Student getStudent(Long id) {
        log.info("Student is get {}", id);
        return studentRepository.findById(id).orElseThrow(()->
        {log.error("Error for get student by id");
            return new EntityNotFoundException();
        });
    }

    public List<Student> getAllStudents() {
        log.info("Get all students");
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student) {
        log.info("Student is updated");
        return studentRepository.save(student);
    }

    public Student deleteStudent(Long id) {
        log.info("Student is deleted {}", id);
        Student student = getStudent(id);
        studentRepository.deleteById(id);
        return student;
    }

    public Collection<Student> getStudentsSameAge(int age) {
        log.info("Get student by age {}", age);
        return getAllStudents().stream().filter(student -> student.getAge() == age).collect(Collectors.toList());
    }

    public Faculty getFacultyByStudent(Long studentId) {
        log.info("Get faculty by student {}", studentId);
        return studentRepository.findById(studentId)
                .map(Student::getFaculty)
                .orElseThrow(() -> new EntityNotFoundException("Студент не найден " + studentId));
    }

    public List<Student> getStudentsByAgeRange(Integer min, Integer max) {
        log.info("Get student by age-range {} {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Integer numberAllStudents(){
        log.info("List count students");
        return studentRepository.numberAllStudents();
    }

    public Integer averageAgeStudents() {
        log.info("Get average age all students");
        return studentRepository.averageAgeStudents();
    }

    public Collection<Student> getLastFiveStudents() {
        log.info("Get last five students");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getAllStudentByNameIsA() {
        log.info("Get all student by name is start A");
        return getAllStudents().stream()
                .map(s -> s.getName())
                .map(s -> s.toUpperCase())
                .filter(s -> s.startsWith("A"))
                .sorted().toList();
    }

    public double getAverageAgeStudents() {
        log.info("Get average age students");
        return getAllStudents().stream()
                .mapToDouble(student -> student.getAge())
                .average().orElse(0);
    }

    public void printParallel() {
        log.info("Print parallel");
        List<Student> all = studentRepository.findAll();
        System.out.println(all.get(0));
        System.out.println(all.get(1));
        new Thread(() -> {
            System.out.println(all.get(2));
            System.out.println(all.get(3));
        }).start();
        new Thread(() -> {
            System.out.println(all.get(4));
            System.out.println(all.get(5));
        }).start();
    }

    public void printSynchronized() {
        List<Student> students = studentRepository.findAll();

        if(students.size() >= 6){
            Thread thread1 = new Thread(() -> {
                printStudentNameSync(students.get(3));
                printStudentNameSync(students.get(4));
            });
            Thread thread2 = new Thread(() -> {
                printStudentNameSync(students.get(5));
                printStudentNameSync(students.get(6));
            });
            thread1.start();
            thread2.start();
            printStudentNameSync(students.get(1));
            printStudentNameSync(students.get(2));
        }
    }

    public synchronized void printStudentNameSync(Student student) {
        log.info("Student, id: {}, name: {}", student.getId(), student.getName());
    }
}
