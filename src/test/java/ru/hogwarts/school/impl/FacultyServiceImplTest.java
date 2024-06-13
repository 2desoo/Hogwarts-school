package ru.hogwarts.school.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacultyServiceImplTest {

    @Mock
    private FacultyRepository rep;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");
        rep.save(faculty);

        assertNotNull(faculty);
        assertEquals("Gryffindor", faculty.getName());
        assertEquals("Red", faculty.getColor());
        assertTrue(faculty.getId() >= 0);
    }

    @Test
    public void testGetFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");
        rep.save(faculty);

        assertNotNull(faculty);
        assertEquals("Gryffindor", faculty.getName());
        assertEquals("Red", faculty.getColor());
        assertEquals(0, faculty.getId());
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");
        rep.save(faculty);

        assertNotNull(faculty);
        assertEquals("Gryffindor", faculty.getName());
        assertEquals("Red", faculty.getColor());
        assertEquals(faculty.getId(), faculty.getId());
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");
        rep.save(faculty);

        assertNotNull(faculty);
        assertEquals("Gryffindor", faculty.getName());
        assertEquals("Red", faculty.getColor());
        assertEquals(faculty.getId(), faculty.getId());
    }

    @Test
    void testGetFacultiesSameColor() {
        Faculty faculty1 = new Faculty(0,"Gryffindor", "Red");
        Faculty faculty2 = new Faculty(0,"Ravenclaw", "Blue");
        Faculty faculty3 = new Faculty(0,"Slytherin", "Red");
        rep.save(faculty1);
        rep.save(faculty2);
        rep.save(faculty3);

//        Collection<Faculty> redFaculties = rep.;
//        Collection<Faculty> greenFaculties = facultyService.getFacultiesSameColor("Red");
//        assertEquals(2, redFaculties.size());
    }
}