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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.hogwarts.school.controller.TestContains.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentServiceImpl studentService;

    @Autowired
    private FacultyServiceImpl facultyService;

    @Test
    public void getStudentInfoTest() {

        Student studentTest = createMockStudent();

        ResponseEntity<Student> getStudent = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentTest.getId(),
                Student.class
        );

        assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student testsStudent = getStudent.getBody();

        assertThat(testsStudent.getName()).isEqualTo(studentTest.getName());
        assertThat(testsStudent.getAge()).isEqualTo(studentTest.getAge());
    }

    @Test
    public void getFacultyByIdStudentTest() {

        Faculty faculty = facultyService.createFaculty(MOCK_FACULTY);
        MOCK_STUDENT.setFaculty(faculty);
        Student student = studentService.createStudent(MOCK_STUDENT);

        ResponseEntity<Faculty> getFacultyStudent = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId() + "/faculty",
                Faculty.class
        );

        assertThat(getFacultyStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(getFacultyStudent.getBody());
    }

    @Test
    public void getAllStudentsTest() {

        createMockStudent();

        List<Student> students = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        ).getBody();

        assertFalse(students.isEmpty());
    }

    @Test
    public void getByAgeTest() {

        createMockStudent();
        createMockStudent(MOCK_STUDENT_NAME, MOCK_STUDENT_AGE + 1);

        List<Student> students = testRestTemplate.exchange(
                "http://localhost:" + port + "/student/age?age=" + MOCK_STUDENT_AGE
                        + "&maxAge=" + (MOCK_STUDENT_AGE + 10),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        ).getBody();

        assertTrue(students.size() >= 1);
    }

    @Test
    public void createStudentTest() {

        ResponseEntity<Student> newStudent = testRestTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                MOCK_STUDENT,
                Student.class
        );

        assertThat(newStudent.getStatusCode()).isEqualTo(HttpStatus.OK);

        Student testsStudent = newStudent.getBody();

        assertThat(testsStudent.getName()).isEqualTo(MOCK_STUDENT_NAME);
        assertThat(testsStudent.getAge()).isEqualTo(MOCK_STUDENT_AGE);
    }

    @Test
    public void editStudentTest() {

        Student studentTest = createMockStudent();
        studentTest.setName(MOCK_STUDENT_NEW_NAME);

        testRestTemplate.put(
                "http://localhost:" + port + "/student",
                studentTest,
                Student.class
        );

        ResponseEntity<Student> getStudent = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentTest.getId(),
                Student.class
        );

        assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.OK);
        Student student = getStudent.getBody();
        assertThat(student.getName()).isEqualTo(studentTest.getName());

    }

    @Test
    public void deleteStudentTest() {

        Student studentTest = createMockStudent();

        testRestTemplate.delete(
                "http://localhost:" + port + "/student/" + studentTest.getId(),
                Student.class
        );

        ResponseEntity<Student> getStudent = testRestTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + studentTest.getId(),
                Student.class
        );

        assertThat(getStudent.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    public Student createMockStudent() {
        return studentService.createStudent(MOCK_STUDENT);
    }

    public Student createMockStudent(String name, Integer age) {
        return studentService.createStudent(new Student(MOCK_STUDENT_ID, name, age));
    }
}