package ru.hogwarts.school.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private Map<Long, Student> studentMap;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateStudent() {
        Student student = new Student(0,"Harry Potter", 15);
        Student createdStudent = studentService.createStudent(student);
        assertEquals(0, createdStudent.getId());
        assertEquals("Harry Potter", createdStudent.getName());
        assertEquals(15, createdStudent.getAge());
    }

    @Test
    public void testGetStudent() {
        Student student = new Student(1,"Hermione Granger", 16);
        studentService.createStudent(student);

        Student retrievedStudent = studentService.getStudent(student.getId());

        assertNotNull(retrievedStudent);
        assertEquals("Hermione Granger", retrievedStudent.getName());
        assertEquals(0, retrievedStudent.getId());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student(2,"Ron Weasley", 15);

        studentService.createStudent(student);

        when(studentMap.containsKey(student.getId())).thenReturn(true);
        when(studentMap.put(student.getId(), student)).thenReturn(student);

        Student updatedStudent = studentService.updateStudent(student);

        assertNotNull(updatedStudent);
        assertEquals("Ron Weasley", updatedStudent.getName());
        assertEquals(student.getId(), updatedStudent.getId());
    }

    @Test
    public void testDeleteStudent() {
        Student student = new Student(4,"Draco Malfoy", 16);
        studentService.createStudent(student);

        when(studentMap.remove(student.getId())).thenReturn(student);

        Student deletedStudent = studentService.deleteStudent(student.getId());

        assertNotNull(deletedStudent);
        assertEquals("Draco Malfoy", deletedStudent.getName());
        assertEquals(student.getId(), deletedStudent.getId());
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
        studentService.createStudent(new Student(0, "Neville Longbottom", 18));
        studentService.createStudent(new Student(1, "Luna Lovegood", 18));
        studentService.createStudent(new Student(2, "Ginny Weasley", 19));

        Collection<Student> studentsAge18 = studentService.getStudentsSameAge(18);
        assertEquals(2, studentsAge18.size());
    }

    @Test
    public void testGetStudentsSameAge_NoStudentsWithAge() {
        when(studentMap.values()).thenReturn(Collections.emptyList());

        Collection<Student> studentsSameAge = studentService.getStudentsSameAge(18);

        assertEquals(0, studentsSameAge.size());
    }
}