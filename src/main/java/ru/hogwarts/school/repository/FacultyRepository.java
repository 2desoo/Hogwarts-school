package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.entity.Faculty;

import java.util.List;

/*
Repository for work with faculties
 */
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    List<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String search, String search1);
}
