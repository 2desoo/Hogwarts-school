package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;

    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @PostMapping
    @Operation(summary = "Create student")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student student1 = studentServiceImpl.createStudent(student);
        return ResponseEntity.ok(student);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get student by ID")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Student student = studentServiceImpl.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping
    @Operation(summary = "Get all students")
    public ResponseEntity<Collection<Student>> getAllStudents() {
        Collection<Student> students = studentServiceImpl.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping
    @Operation(summary = "Update info for student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student student1 = studentServiceImpl.updateStudent(student);
        if (student1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete student by ID")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        Student student = studentServiceImpl.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    @Operation(summary = "Get student same age")
    public ResponseEntity<Collection<Student>> getStudentsSameAge(@PathVariable int age) {
        Collection<Student> students = studentServiceImpl.getStudentsSameAge(age);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/age-range")
    @Operation(summary = "Get student by age-range")
    public ResponseEntity<Collection<Student>> getStudentsByAgeRange(@RequestParam int min, @RequestParam int max) {
        Collection<Student> students = studentServiceImpl.getStudentsByAgeRange(min, max);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}/faculty")
    @Operation(summary = "Get faculty by student")
    public ResponseEntity<Faculty> getFacultyByStudent(@PathVariable Long id) {
        Faculty faculties = studentServiceImpl.getFacultyByStudent(id);
        return ResponseEntity.ok(faculties);
    }
}
