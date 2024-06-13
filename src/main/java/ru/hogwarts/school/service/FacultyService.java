package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    Faculty deleteFaculty(Long id);

    Collection<Faculty> allFaculties();

    Collection<Faculty> getFacultiesSameColor(String color);

    List<Faculty> findFacultiesByNameOrColor(String name, String color);

    List<Student> getStudentsByFaculty(Long facultyId);
}
