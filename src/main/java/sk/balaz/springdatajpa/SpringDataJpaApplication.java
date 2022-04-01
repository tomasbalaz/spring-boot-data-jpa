package sk.balaz.springdatajpa;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {

            Faker faker = new Faker();
            generateRandomStudents(studentRepository, faker);
        };
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
