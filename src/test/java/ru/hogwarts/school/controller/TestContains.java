package ru.hogwarts.school.controller;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collections;
import java.util.List;

public class TestContains {

    public static final Long MOCK_FACULTY_ID = 7L;

    public static final String MOCK_FACULTY_NAME = "Ballas";

    public static final String MOCK_FACULTY_NEW_NAME = "Groove";

    public static final String MOCK_FACULTY_COLOR = "Purple";

    public static final Faculty MOCK_FACULTY = new Faculty(
            MOCK_FACULTY_ID,
            MOCK_FACULTY_NAME,
            MOCK_FACULTY_COLOR
    );

    public static final Long MOCK_STUDENT_ID = 66L;

    public static final String MOCK_STUDENT_NAME = "Vova";

    public static final String MOCK_STUDENT_NEW_NAME = "Sasha";

    public static final int MOCK_STUDENT_AGE = 22;

    public static final Student MOCK_STUDENT = new Student(
            MOCK_STUDENT_ID,
            MOCK_STUDENT_NAME,
            MOCK_STUDENT_AGE
    );

    public static final List<Student> MOCK_STUDENTS = Collections.singletonList(MOCK_STUDENT);

    public static final List<Faculty> MOCK_FACULTIES = Collections.singletonList(MOCK_FACULTY);
}
