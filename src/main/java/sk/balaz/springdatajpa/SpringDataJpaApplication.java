package sk.balaz.springdatajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            StudentRepository studentRepository) {
        return args -> {

            Faker faker = new Faker();

            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            int age = faker.number().numberBetween(17, 55);
            Student student = new Student(
                    firstName,
                    lastName,
                    String.format("%s.%s@email.edu", firstName, lastName),
                    age);

            StudentIdCard studentIdCard = new StudentIdCard(
                    "1234567890",
                    student);

            student.addBook(
                    new Book("Clean code",
                    LocalDateTime.now().minusDays(4)));

            student.addBook(
                    new Book("Think and Grow Rich",
                            LocalDateTime.now()));

            student.addBook(
                    new Book("Spring Data JPA",
                            LocalDateTime.now().minusYears(4)));

            student.setStudentIdCard(studentIdCard);

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 1L),
                    student,
                    new Course("Computer Science", "IT"),
                    LocalDateTime.now()));

            student.addEnrolment(new Enrolment(
                    new EnrolmentId(1L, 2L),
                    student,
                    new Course("Spring Data JPA", "IT"),
                    LocalDateTime.now()));

//            student.addCourse(new Course("Computer Science", "IT"));
//
//            student.addCourse(new Course("Spring Data JPA", "IT"));

            studentRepository.save(student);

            studentRepository.findById(1L)
                    .ifPresent(s -> {
                        System.out.println("fetch books lazy...");
                        List<Book> books = student.getBooks();
                        books.forEach(book -> System.out.println(
                                s.getFirstName() + " borrowed " + book.getBookName()));
                    });

//            studentIdCardRepository.findById(1L)
//                    .ifPresent(System.out::println);
//
//            studentRepository.deleteById(1L);
        };
    }

    private void sortStudents(StudentRepository studentRepository) {
        //1. option
        Sort sort = Sort.by(Sort.Direction.DESC, "firstName");
        studentRepository.findAll(sort)
                .forEach(student -> System.out.println(student.getFirstName()));

        //2. option
        Sort sort2 = Sort.by("firstName").ascending()
                .and(Sort.by("age").descending());

        studentRepository.findAll(sort2)
                .forEach(student ->
                        System.out.println(student.getFirstName() + " " + student.getAge()));
    }

    private void generateRandomStudents(StudentRepository studentRepository, Faker faker) {
        for (int i = 0; i <= 20; i++) {
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            int age = faker.number().numberBetween(17, 55);

            studentRepository.save(
                    new Student(
                            firstName,
                            lastName,
                            String.format("%s.%s@email.edu", firstName, lastName),
                            age)
            );
        }
    }

}
