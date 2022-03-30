package sk.balaz.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    List<Student> findStudentByFirstNameEqualsAndAgeGreaterThan(String firstName, Integer age);
}
