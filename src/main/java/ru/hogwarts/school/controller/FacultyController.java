package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/faculty")
@Tag(name = "API по работе с факультетами")
public class FacultyController {

    /*
    Added service
     */
    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    /*
    Created faculty
     */
    @PostMapping
    @Operation(summary = "Create faculty")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty faculty1 = facultyServiceImpl.createFaculty(faculty);
        return ResponseEntity.ok(faculty1);
    }

    /*
    Get faculty by ID
     */
    @GetMapping("{id}")
    @Operation(summary = "Get faculty by ID")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyServiceImpl.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    /*
    Get info for all faculties
     */
    @GetMapping("/all")
    @Operation(summary = "Get info for all faculties")
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = facultyServiceImpl.allFaculties();
        return ResponseEntity.ok(faculties);
    }

    /*
    Updated info for faculty
     */
    @PutMapping
    @Operation(summary = "Update info for faculty")
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyServiceImpl.updateFaculty(faculty);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundFaculty);
    }

    /*
    Deleted faculty by ID
     */
    @DeleteMapping("{id}")
    @Operation(summary = "Delete faculty by ID")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable long id) {
        Faculty faculty = facultyServiceImpl.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    /*
    Get info for faculties same color
     */
    @GetMapping
    @Operation(summary = "Get info for faculties same color")
    public ResponseEntity<Collection<Faculty>> getFacultiesSameColor(@RequestParam(required = false) String color) {
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok((facultyServiceImpl.getFacultiesSameColor(color)));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    /*
    Find faculties by name or color"
     */
    @GetMapping("/search")
    @Operation(summary = "Find faculties by name or color")
    public ResponseEntity<Collection<Faculty>> findFacultiesByNameOrColor(@RequestParam String color,
                                                                          @RequestParam String name) {
        Collection<Faculty> faculties = facultyServiceImpl.findFacultiesByNameOrColor(color, name);
        return ResponseEntity.ok(faculties);
    }

    /*
    Get info for student by faculty
     */
    @GetMapping("/{id}/students")
    @Operation(summary = "Get info for student by faculty")
    public ResponseEntity<Collection<Student>> getStudentsByFaculty(@PathVariable Long id) {
        Collection<Student> students = facultyServiceImpl.getStudentsByFaculty(id);
        return ResponseEntity.ok(students);
    }

    /*
    Find long name
     */
    @GetMapping("/long-name")
    @Operation(summary = "Get long name")
    public ResponseEntity<String> getLongNameByFaculties() {
        return ResponseEntity.ok(facultyServiceImpl.getLongNameFaculty());
    }
}
