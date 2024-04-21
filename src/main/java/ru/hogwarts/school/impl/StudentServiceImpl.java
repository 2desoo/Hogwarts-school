package ru.hogwarts.school.impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    private final Map<Long, Student> studentMap = new HashMap<>();
    private long id = 0;

    public Student createStudent(Student student) {
        student.setId(id++);
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student getStudent(Long id) {
        return studentMap.get(id);
    }

    public Student updateStudent(Student student) {
        if (!studentMap.containsKey(student.getId())) {
            return null;
        }
        studentMap.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(Long id) {
        return studentMap.remove(id);
    }

    public Collection<Student> getStudentsSameAge(int age) {
        ArrayList<Student> studentsSameAge = new ArrayList<>();
        for (Student student : studentMap.values()) {
            if (student.getAge() == age) {
                studentsSameAge.add(student);
            }
        }
        return studentsSameAge;
    }
}
