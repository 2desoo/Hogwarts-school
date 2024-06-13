package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
@Tag(name = "API по работе с факультетами")
public class FacultyController {

    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    @PostMapping
    @Operation(summary = "Create faculty")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty faculty1 = facultyServiceImpl.createFaculty(faculty);
        return ResponseEntity.ok(faculty1);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get faculty by ID")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyServiceImpl.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/all")
    @Operation(summary = "Get info for all faculties")
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = facultyServiceImpl.allFaculties();
        return ResponseEntity.ok(faculties);
    }

    @PutMapping
    @Operation(summary = "Update info for faculty")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyServiceImpl.updateFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete faculty by ID")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        Faculty faculty = facultyServiceImpl.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Get info for faculties same color")
    public ResponseEntity<Collection<Faculty>> getFacultiesSameColor(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok((facultyServiceImpl.getFacultiesSameColor(color)));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/search")
    @Operation(summary = "Find faculties by name or color")
    public ResponseEntity<Collection<Faculty>> findFacultiesByNameOrColor(@RequestParam String color,
                                                                          @RequestParam String name) {
        Collection<Faculty> faculties = facultyServiceImpl.findFacultiesByNameOrColor(color, name);
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}/students")
    @Operation(summary = "Get info for student by faculty")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@PathVariable Long id) {
        Collection<Student> students = facultyServiceImpl.getStudentsByFaculty(id);
        return ResponseEntity.ok(students);
    }
}
