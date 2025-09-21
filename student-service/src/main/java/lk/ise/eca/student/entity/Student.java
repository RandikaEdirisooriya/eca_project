package lk.ise.eca.student.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ise.eca.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @NotBlank(message = "Registration number can't be empty")
    @Pattern(regexp = "^S\\d{3}$", message = "Registration number must follow the format SXXX")
    @Column(name = "registration_number", length = 10)
    private String registrationNumber;

    @NotBlank(message = "Full name can't be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Full name should only contain letters and spaces")
    @Column(name = "full_name", length = 255)
    private String fullName;

    @NotBlank(message = "Address can't be empty")
    @Size(min = 3, message = "Address must contain at least 3 letters")
    @Column(name = "address", length = 500)
    private String address;

    @NotBlank(message = "Contact number can't be empty")
    @Pattern(regexp = "\\d{3}-\\d{7}", message = "Contact must follow the format XXX-XXXXXXX")
    @Column(name = "contact", unique = true, length = 11)
    private String contact;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Invalid email format")
    @Column(name = "email", unique = true, length = 255)
    private String email;

    @Component
    @RepositoryEventHandler
    @RequiredArgsConstructor
    public static class StudentEntityEventHandler {
        private final StudentRepository studentRepository;

        @HandleBeforeCreate
        public void handleBeforeCreate(Student student) {
            if (studentRepository.existsById(student.getRegistrationNumber())) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        String.format("Student with registration number: %s already exists", student.getRegistrationNumber())
                );
            }
        }
    }
}