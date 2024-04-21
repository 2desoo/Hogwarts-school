package ru.hogwarts.school.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacultyServiceImplTest {

    @Mock
    private Map<Long, Faculty> facultyMap;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");

        when(facultyMap.put(any(Long.class), any(Faculty.class))).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            faculty.setId(id);
            return null;
        });

        Faculty createdFaculty = facultyService.createFaculty(faculty);

        assertNotNull(createdFaculty);
        assertEquals("Gryffindor", createdFaculty.getName());
        assertEquals("Red", createdFaculty.getColor());
        assertTrue(createdFaculty.getId() >= 0);
    }

    @Test
    public void testGetFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");
        facultyService.createFaculty(faculty);

        Faculty retrievedFaculty = facultyService.getFaculty(faculty.getId());

        assertNotNull(retrievedFaculty);
        assertEquals("Gryffindor", retrievedFaculty.getName());
        assertEquals("Red", retrievedFaculty.getColor());
        assertEquals(0, retrievedFaculty.getId());
    }

    @Test
    public void testUpdateFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");
        facultyService.createFaculty(faculty);

        when(facultyMap.containsKey(faculty.getId())).thenReturn(true);
        when(facultyMap.put(faculty.getId(), faculty)).thenReturn(faculty);

        Faculty updatedFaculty = facultyService.updateFaculty(faculty);

        assertNotNull(updatedFaculty);
        assertEquals("Gryffindor", updatedFaculty.getName());
        assertEquals("Red", updatedFaculty.getColor());
        assertEquals(faculty.getId(), updatedFaculty.getId());
    }

    @Test
    public void testDeleteFaculty() {
        Faculty faculty = new Faculty(0,"Gryffindor", "Red");
        facultyService.createFaculty(faculty);

        when(facultyMap.remove(faculty.getId())).thenReturn(faculty);

        Faculty deletedFaculty = facultyService.deleteFaculty(faculty.getId());

        assertNotNull(deletedFaculty);
        assertEquals("Gryffindor", deletedFaculty.getName());
        assertEquals("Red", deletedFaculty.getColor());
        assertEquals(faculty.getId(), deletedFaculty.getId());
    }

//    @Test
//    public void testGetFacultiesSameColor() {
//        Faculty faculty1 = new Faculty(0,"Gryffindor", "Red");
//        Faculty faculty2 = new Faculty(1,"Ravenclaw", "Blue");
//        Faculty faculty3 = new Faculty(2,"Slytherin", "Red");
//        facultyMap.put(faculty1.getId(), faculty1);
//        facultyMap.put(faculty2.getId(), faculty2);
//        facultyMap.put(faculty3.getId(), faculty3);
//
//        Collection<Faculty> facultiesSameColor = facultyService.getFacultiesSameColor("Red");
//
//        assertEquals(2, facultiesSameColor.size());
//        assertTrue(facultiesSameColor.contains(faculty1));
//        assertTrue(facultiesSameColor.contains(faculty3));
//    }

    @Test
    void testGetFacultiesSameColor() {
        facultyService.createFaculty(new Faculty(0, "Gryffindor", "Red"));
        facultyService.createFaculty(new Faculty(1, "Ravenclaw", "Blue"));
        facultyService.createFaculty(new Faculty(2, "Slytherin", "Red"));

        Collection<Faculty> greenFaculties = facultyService.getFacultiesSameColor("Red");
        assertEquals(2, greenFaculties.size());
    }
}