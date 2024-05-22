package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByAgeBetween(Integer min, Integer max);

    @Query(value = "select count(*) from student s;", nativeQuery = true)
    Integer numberAllStudents();

    @Query(value = "select avg(age) from student s;", nativeQuery = true)
    Integer averageAgeStudents();

    @Query(value = "SELECT * FROM student s ORDER BY id desc limit 5;", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
