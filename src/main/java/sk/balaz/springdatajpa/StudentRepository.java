package sk.balaz.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    List<Student> findStudentByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 AND s.age = ?2")
    List<Student> findStudentByFirstNameEqualsAndAgeGreaterThan(String firstName, Integer age);
}
