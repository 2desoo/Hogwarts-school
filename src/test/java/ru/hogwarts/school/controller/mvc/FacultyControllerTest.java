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
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.hogwarts.school.controller.TestContains.*;


@WebMvcTest(controllers = FacultyController.class)
class FacultyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FacultyRepository facultyRepository;

	@SpyBean
	private FacultyServiceImpl facultyServiceImp;

	@InjectMocks
	private FacultyController facultyController;

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void TestGetFacultyInfo () throws Exception {

		when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

		mockMvc.perform(MockMvcRequestBuilders.
						get("/faculty/" + MOCK_FACULTY_ID)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
				.andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
	}

	@Test
	public void TestGetStudentFaculty() throws Exception {
		MOCK_FACULTY.setStudent(MOCK_STUDENTS);

		when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

		mockMvc.perform(MockMvcRequestBuilders.
						get("/faculty/" + MOCK_FACULTY_ID + "/students")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(MOCK_STUDENTS)));
	}

	@Test
	public void TestGetAllFaculties() throws Exception {

		when(facultyRepository.findAll()).thenReturn(MOCK_FACULTIES);

		mockMvc.perform(MockMvcRequestBuilders.get("/faculty/all")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
	}

	@Test
	public void TesGetByColor() throws Exception {

		when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString()))
				.thenReturn(MOCK_FACULTIES);

		mockMvc.perform(MockMvcRequestBuilders.
						get("/faculty/search?color=" + MOCK_FACULTY_COLOR + "&name=" + MOCK_FACULTY_NAME)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(mapper.writeValueAsString(MOCK_FACULTIES)));
	}

	@Test
	public void testCreateFaculty() throws Exception {

		JSONObject createTestFaculty = new JSONObject();
		createTestFaculty.put("name", MOCK_FACULTY_NAME);
		createTestFaculty.put("color", MOCK_FACULTY_COLOR);

		when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);

		mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
						.content(createTestFaculty.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
				.andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
	}

	@Test
	public void testEditFaculty() throws Exception{

		JSONObject editTestFaculty = new JSONObject();
		editTestFaculty.put("id", MOCK_FACULTY_ID);
		editTestFaculty.put("name", MOCK_FACULTY_NAME);
		editTestFaculty.put("color", MOCK_FACULTY_COLOR);

		when(facultyRepository.save(any(Faculty.class))).thenReturn(MOCK_FACULTY);

		mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
						.content(editTestFaculty.toString())
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(MOCK_FACULTY_NAME))
				.andExpect(jsonPath("$.color").value(MOCK_FACULTY_COLOR));
	}

	@Test
	public void testDeleteFaculty() throws Exception{
		when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(MOCK_FACULTY));

		mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/" + MOCK_FACULTY_ID)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}