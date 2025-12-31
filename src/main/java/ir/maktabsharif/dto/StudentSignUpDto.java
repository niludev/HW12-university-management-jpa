package ir.maktabsharif.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class StudentSignUpDto {

    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Birth date is required.")
    private LocalDate birthDate;

    @NotBlank(message = "Student code is required.")
    private String studentCode;

    @NotBlank(message = "Field of study cannot be empty.")
    private String fieldOfStudy;

    @Min(value = 1380, message = "Entry year must be after 1380.")
    @Positive(message = "Entry year must be positive.")
    private Integer entryYear;

}
