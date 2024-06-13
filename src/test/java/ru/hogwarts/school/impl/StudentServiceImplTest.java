package ru.hogwarts.school.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;
import static ru.hogwarts.school.controller.TestContains.MOCK_STUDENTS;

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

    @Test
    public void testGetStudentsSameAge() {

        when(rep.findByAgeBetween(any(), any())).thenReturn(MOCK_STUDENTS);

        Collection<Student> studentsAge18 = rep.findByAgeBetween(18,30);

        assertEquals(1, studentsAge18.size());
    }
}