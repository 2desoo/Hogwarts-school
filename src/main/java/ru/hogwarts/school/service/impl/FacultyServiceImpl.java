package ru.hogwarts.school.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.model.Faculty;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        return facultyRepository.getReferenceById(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        Faculty faculty = getFaculty(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> allFaculties() {
        return facultyRepository.findAll();
    }

    public List<Faculty> findFacultiesByNameOrColor(String search) {
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(search, search);
    }


    public Collection<Faculty> getFacultiesSameColor(String color) {
        return facultyRepository.findAll().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public Set<Student> getStudentsByFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Факультет не найден " + facultyId));
        return faculty.getStudents();
    }


}
