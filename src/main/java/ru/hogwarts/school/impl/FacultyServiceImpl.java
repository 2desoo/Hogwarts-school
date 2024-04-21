package ru.hogwarts.school.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.model.Faculty;

import java.util.*;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final Map<Long, Faculty> facultyMap = new HashMap<>();
    private long id = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(id++);
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty getFaculty(Long id) {
        return facultyMap.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!facultyMap.containsKey(faculty.getId())) {
            return null;
        }
        facultyMap.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(Long id) {
        return facultyMap.remove(id);
    }

    public Collection<Faculty> getFacultiesSameColor(String color) {
        ArrayList<Faculty> facultiesSameColor = new ArrayList<>();
        for (Faculty faculty : facultyMap.values()) {
            if (Objects.equals(faculty.getColor(), color)) {
                facultiesSameColor.add(faculty);
            }
        }
        return facultiesSameColor;
    }
}
