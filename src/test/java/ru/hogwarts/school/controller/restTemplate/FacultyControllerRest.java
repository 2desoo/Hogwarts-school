package ru.hogwarts.school.controller.restTemplate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.controller.TestContains.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService;

    @Test
    public void getFacultyInfoTest() {

        Faculty faculty = createMockFaculty();

        ResponseEntity<Faculty> getFaculty = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + faculty.getId(),
                Faculty.class
        );

        assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty testsFaculty = getFaculty.getBody();

        assertThat(testsFaculty.getName()).isEqualTo(testsFaculty.getName());
        assertThat(testsFaculty.getColor()).isEqualTo(testsFaculty.getColor());
    }

    @Test
    public void getStudentFacultyTest() {
        Faculty faculty = createMockFaculty();

        List<Student> students = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + faculty.getId() + "/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        ).getBody();

        assertNotNull(students);
    }

    @Test
    public void getAllFacultiesTest() {

        createMockFaculty();

        List<Faculty> faculties = testRestTemplate.exchange(
                "http://localhost:" + port + "/faculty/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Faculty>>() {
                }
        ).getBody();

        assertFalse(faculties.isEmpty());
    }

    @Test
    void testGetFacultiesSameColor() {
        String url = "http://localhost:" + port + "/faculty?color=" + MOCK_FACULTY_COLOR;

        ResponseEntity<Collection> response = testRestTemplate.getForEntity(url, Collection.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void createFacultyTest() {

        ResponseEntity<Faculty> newFaculty = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/faculty",
                MOCK_FACULTY,
                Faculty.class
        );

        assertThat(newFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);

        Faculty testsFaculty = newFaculty.getBody();

        assertThat(testsFaculty.getName()).isEqualTo(MOCK_FACULTY_NAME);
        assertThat(testsFaculty.getColor()).isEqualTo(MOCK_FACULTY_COLOR);
    }

    @Test
    public void editFacultyTest() {

        Faculty facultyTest = createMockFaculty();
        facultyTest.setName(MOCK_FACULTY_NEW_NAME);

        testRestTemplate.put(
                "http://localhost:" + port + "/faculty",
                facultyTest,
                Faculty.class
        );

        ResponseEntity<Faculty> getFaculty = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + facultyTest.getId(),
                Faculty.class
        );

        assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        Faculty faculty = getFaculty.getBody();
        assertThat(faculty.getName()).isEqualTo(facultyTest.getName());
    }

    @Test
    public void deleteFacultyTest() {

        Faculty facultyTest = createMockFaculty();

        testRestTemplate.delete(
                "http://localhost:" + port + "/faculty/" + facultyTest.getId(),
                Faculty.class
        );

        ResponseEntity<Faculty> getFaculty = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + facultyTest.getId(),
                Faculty.class
        );

        assertThat(getFaculty.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    public Faculty createMockFaculty() {
        return facultyService.createFaculty(MOCK_FACULTY);
    }

    public Faculty createMockFaculty(String name, String color) {
        return facultyService.createFaculty(new Faculty(MOCK_STUDENT_ID, name, color));
    }
}
