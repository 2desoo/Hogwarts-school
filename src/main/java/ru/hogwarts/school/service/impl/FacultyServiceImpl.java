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
    /*
    Added log
     */
    private final static Logger log = LoggerFactory.getLogger(FacultyServiceImpl.class);
    /*
    Added faculty repository
     */
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    /*
    Created faculty methods
     */
    public Faculty createFaculty(Faculty faculty) {
        log.info("Faculty is created");
        return facultyRepository.save(faculty);
    }

    /*
    Get faculty methods
     */
    public Faculty getFaculty(Long id) {
        log.info("Get faculty {}", id);
        return facultyRepository.findById(id).orElseThrow(ru.hogwarts.school.exception.EntityNotFoundException::new);
    }

    /*
    Updated faculty methods
     */
    public Faculty updateFaculty(Faculty faculty) {
        log.info("Updated faculty");
        return facultyRepository.save(faculty);
    }

    /*
    Deleted faculty methods
     */
    public Faculty deleteFaculty(Long id) {
        log.info("Deleted faculty {}", id);
        Faculty faculty = getFaculty(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    /*
    Get all faculties methods
     */
    public Collection<Faculty> allFaculties() {
        log.info("Get all faculties");
        return facultyRepository.findAll();
    }

    /*
    Find faculties by name or color methods
     */
    public List<Faculty> findFacultiesByNameOrColor(String name, String color) {
        log.info("Find faculties by name or color");
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }


    /*
    Get faculties same color methods
     */
    public Collection<Faculty> getFacultiesSameColor(String color) {
        log.info("Get faculties same color");
        return facultyRepository.findAll().stream()
                .filter(f -> f.getColor().equals(color))
                .collect(Collectors.toList());
    }

    /*
    Get students by faculty methods
     */
    public List<Student> getStudentsByFaculty(Long facultyId) {
        log.info("Get students by faculty");
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("Факультет не найден " + facultyId));
        return faculty.getStudents();
    }

    /*
    Get long name from faculty methods
     */
    public String getLongNameFaculty() {
        log.info("Get long name");
        return allFaculties().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparing(String::length))
                .orElse(null);
    }

}
