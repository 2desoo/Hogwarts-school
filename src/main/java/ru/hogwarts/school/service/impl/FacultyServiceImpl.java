package ru.hogwarts.school.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.entity.Faculty;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final static Logger log = LoggerFactory.getLogger(FacultyServiceImpl.class);
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        log.info("Faculty is created");
        return facultyRepository.save(faculty);
    }

    public Faculty getFaculty(Long id) {
        log.info("Get faculty {}", id);
        return facultyRepository.findById(id).orElseThrow(ru.hogwarts.school.exception.EntityNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty) {
        log.info("Updated faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty deleteFaculty(Long id) {
        log.info("Deleted faculty {}", id);
        Faculty faculty = getFaculty(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    public Collection<Faculty> allFaculties() {
        log.info("Get all faculties");
        return facultyRepository.findAll();
    }

    public List<Faculty> findFacultiesByNameOrColor(String name, String color) {
        log.info("Find faculties by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }


    public Collection<Faculty> getFacultiesSameColor(String color) {
        log.info("Get faculties same color");
        return facultyRepository.findAll().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    public List<Student> getStudentsByFaculty(Long facultyId) {
        log.info("Get students by faculty");
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Факультет не найден " + facultyId));
        return faculty.getStudents();
    }

    public String getLongNameFaculty() {
        log.info("Get long name");
        return allFaculties().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }

}
