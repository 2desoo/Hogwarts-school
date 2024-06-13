package ru.hogwarts.school.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository rep;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student(0, "Harry Potter", 16);

        rep.save(student);

        assertEquals(0, student.getId());
        assertEquals("Harry Potter", student.getName());
        assertEquals(16, student.getAge());
    }

    @Test
    public void testGetStudent() {
        Student student = new Student(1,"Hermione Granger", 16);

        rep.save(student);

        assertNotNull(student);
        assertEquals("Hermione Granger", student.getName());
        assertEquals(1, student.getId());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student(2,"Ron Weasley",15);

        rep.save(student);

        assertNotNull(student);
        assertEquals("Ron Weasley", student.getName());
        assertEquals(student.getId(), student.getId());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student(4,"Draco Malfoy", 16);

        rep.save(student);

        assertNotNull(student);
        assertEquals("Draco Malfoy", student.getName());
        assertEquals(student.getId(), student.getId());
    }

//    @Test
//    public void testGetStudentsSameAge() {
//        Student student1 = new Student(5,"Neville Longbottom", 15);
//        Student student2 = new Student(6,"Luna Lovegood", 15);
//        Student student3 = new Student(7,"Ginny Weasley", 16);
//        List<Student> allStudents = Arrays.asList(student1, student2, student3);
//
//        when(studentMap.values()).thenReturn(allStudents);
//
//        Collection<Student> studentsSameAge = studentService.getStudentsSameAge(15);
//
//        assertEquals(2, studentsSameAge.size());
//        assertTrue(studentsSameAge.contains(student1));
//        assertTrue(studentsSameAge.contains(student2));
//    }

    @Test
    void testGetStudentsSameAge() {
//        studentService.createStudent(new Student(0, "Neville Longbottom", 18));
//        studentService.createStudent(new Student(1, "Luna Lovegood", 18));
//        studentService.createStudent(new Student(2, "Ginny Weasley", 19));
//        studentService.createStudent(new Student(3, "Ginny Weasley", 27));
        Student student1 = new Student(0, "Neville Longbottom", 18);
        Student student2 = new Student(1, "Luna Lovegood", 18);
        Student student3 = new Student(2, "Ginny Weasley", 19);
        Student student4 = new Student(3, "Draco Malfoy", 27);
        rep.save(student1);
        rep.save(student2);
        rep.save(student3);
        rep.save(student4);

        Collection<Student> studentsAge18 = rep.findByAgeBetween(18,19);

//        Collection<Student> studentsAge1 = rep.;
        assertEquals(3, studentsAge18.size());
    }
}