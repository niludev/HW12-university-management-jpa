package ir.maktabsharif.view;

import ir.maktabsharif.dto.StudentSignUpDto;
import ir.maktabsharif.dto.TeacherSignUpDto;
import ir.maktabsharif.models.Person;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.models.Teacher;
import ir.maktabsharif.models.enums.Degree;
import ir.maktabsharif.services.PersonService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Set;

public class UniversityConsoleApp {

    private final PersonService<TeacherSignUpDto, Teacher> teacherService;
    private final PersonService<StudentSignUpDto, Student> studentService;

    private final Scanner scanner = new Scanner(System.in);

    private final Validator validator;

    public UniversityConsoleApp(
            PersonService<TeacherSignUpDto, Teacher> teacherService,
            PersonService<StudentSignUpDto, Student> studentService
    ) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

//    public void run() {
//        System.out.println("University App is running...");
//
//
//        try {
//            Student student = studentService.signUp(dto);
//            System.out.println("Student registered successfully: " + student.getId());
//
//        } catch (IllegalArgumentException e) {
//            System.out.println("Invalid data: " + e.getMessage());
//
//        } catch (RuntimeException e) {
//            System.out.println("Operation failed: " + e.getMessage());
//        }
//    }

    public void run() {

        while (true) {
            System.out.println("\n==== University System ====");
            System.out.println("1. Student Sign Up");
            System.out.println("2. Teacher Sign Up");
            System.out.println("3. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    studentSignUp();
                    break;
                case "2":
                    teacherSignUp();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid input, try again.");
            }
        }
    }

    // ----------------------------
    // STUDENT SIGN UP
    // ----------------------------
    private void studentSignUp() {
        System.out.println("\n--- Student Sign Up ---");

        StudentSignUpDto dto = new StudentSignUpDto();
        System.out.print("First Name: ");
        dto.setFirstName(scanner.nextLine().trim());

        System.out.print("Last Name : ");
        dto.setLastName(scanner.nextLine().trim());

        while (true) {
            System.out.print("Birth Date (YYYY-MM-DD): ");
            String birthInput = scanner.nextLine().trim();

            try {
                dto.setBirthDate(java.time.LocalDate.parse(birthInput));
                break;

            } catch (Exception e) {
                System.out.println("Invalid format. Example: 1994-05-21");
            }
        }

        System.out.print("Student Code: ");
        dto.setStudentCode(scanner.nextLine().trim());

        System.out.print("Field of Study: ");
        dto.setFieldOfStudy(scanner.nextLine().trim());

        System.out.print("Entry Year: ");
        dto.setEntryYear(Integer.parseInt(scanner.nextLine().trim()));

        if (!validateDto(dto)) return;

        try {
            Person saved = studentService.signUp(dto);
            System.out.println("Student Registered -> " + saved.getId());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
        }
    }

    // ----------------------------
    // TEACHER SIGN UP
    // ----------------------------
    private void teacherSignUp() {
        System.out.println("\n--- Teacher Sign Up ---");

        TeacherSignUpDto dto = new TeacherSignUpDto();

        System.out.print("First Name: ");
        dto.setFirstName(scanner.nextLine().trim());

        System.out.print("Last Name : ");
        dto.setLastName(scanner.nextLine().trim());

        // --- Date ---
        while (true) {
            System.out.print("Birth Date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Date cannot be empty.");
                continue;
            }

            try {
                dto.setBirthDate(LocalDate.parse(input));
                break;
            } catch (Exception e) {
                System.out.println("Invalid date. Example: 1994-05-21");
            }
        }

        System.out.print("Teacher Code: ");
        dto.setTeacherCode(scanner.nextLine().trim());

        System.out.print("Qualification: ");
        dto.setQualification(scanner.nextLine().trim());

        // --- Degree (Enum) ---
        while (true) {
            System.out.print("Degree (BACHELOR/MASTER/PHD): ");
            String degreeInput = scanner.nextLine().trim().toUpperCase();

            try {
                dto.setDegree(Degree.valueOf(degreeInput));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid degree. Options: BACHELOR / MASTER / PHD");
            }
        }

        // --- Salary ---
        while (true) {
            System.out.print("Monthly Salary: ");
            String salaryInput = scanner.nextLine().trim();

            try {
                double salary = Double.parseDouble(salaryInput);

                if (salary <= 0) {
                    System.out.println("Salary must be positive.");
                    continue;
                }

                dto.setMonthlySalary(salary);
                break;

            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Enter numbers only, like 3500");
            }
        }

        // Final Validation
        if (!validateDto(dto)) return;

        try {
            Teacher saved = teacherService.signUp(dto);
            System.out.println("Teacher Registered Successfully → ID: " + saved.getId());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Unexpected error: " + ex.getMessage());
        }
    }


    // ----------------------------
    // DTO VALIDATION FOR BOTH
    // ----------------------------
    private <T> boolean validateDto(T dto) {
        Set<ConstraintViolation<T>> errors = validator.validate(dto);

        if (!errors.isEmpty()) {
            System.out.println("Validation failed:");
            errors.forEach(err -> System.out.println(" - " + err.getMessage()));
            return false;
        }
        return true;
    }

}
