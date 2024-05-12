package ru.hogwarts.school.controller.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestContains.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private StudentServiceImpl studentService;

    @InjectMocks
    private StudentController studentController;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateStudent() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", MOCK_STUDENT_NAME);
        jsonObject.put("age", MOCK_STUDENT_AGE);
        jsonObject.put("id", MOCK_STUDENT_ID);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE))
                .andExpect(jsonPath("$.id").value(MOCK_STUDENT_ID));
    }

    @Test
    public void testGetStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", MOCK_STUDENT_NAME);
        jsonObject.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + MOCK_STUDENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllStudents() throws Exception {
        when(studentRepository.findAll()).thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(MOCK_STUDENT);

        JSONObject updateStudent = new JSONObject();
        updateStudent.put("id", MOCK_STUDENT_ID);
        updateStudent.put("name", MOCK_STUDENT_NAME);
        updateStudent.put("age", MOCK_STUDENT_AGE);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(updateStudent.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(MOCK_STUDENT_NAME))
                .andExpect(jsonPath("$.age").value(MOCK_STUDENT_AGE));

    }

    @Test
    public void TesGetByAge() throws Exception {

        int min = 10;
        int max = 20;

        when(studentRepository.findByAgeBetween(anyInt(), anyInt()))
                .thenReturn(MOCK_STUDENTS);

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/student/age-range?min=" + MOCK_STUDENT_AGE + "&max=" + MOCK_STUDENT_AGE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/student/age-range?min=" + min + "&max=" + max)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
    }

    @Test
    public void TestGetFacultyByIdStudent() throws Exception {
        MOCK_STUDENT.setFaculty(MOCK_FACULTY);

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_STUDENT));

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/student/" + MOCK_STUDENT_ID + "/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTY)));
    }
}