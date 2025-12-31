package ir.maktabsharif.dto;

import ir.maktabsharif.models.enums.Degree;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TeacherSignUpDto {

    @NotBlank(message = "First name is required.")  // faghat baraye string hast
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotNull(message = "Birth date is required.")
    private LocalDate birthDate;

    @NotBlank(message = "Teacher code is required.")
    private String teacherCode;

    @NotBlank(message = "Qualification is required.")
    private String qualification;

    @NotNull(message = "Degree is required.")
    private Degree degree;

    @Positive(message = "Salary must be a positive number.")
    private Double monthlySalary;
}
