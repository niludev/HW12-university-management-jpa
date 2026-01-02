package ir.maktabsharif.view;

import ir.maktabsharif.dto.StudentSignUpDto;
import ir.maktabsharif.dto.TeacherSignUpDto;
import java.util.List;
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
            System.out.println("3. List Students");
            System.out.println("4. List Teachers");
            System.out.println("5. Delete Student");
            System.out.println("6. Delete Teacher");
            System.out.println("7. Update Student");
            System.out.println("8. Update Teacher");
            System.out.println("9. Exit");

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": studentSignUp(); break;
                case "2": teacherSignUp(); break;
                case "3": listStudents(); break;
                case "4": listTeachers(); break;
                case "5": deleteStudent(); break;
                case "6": deleteTeacher(); break;
                case "7": updateStudent(); break;
                case "8": updateTeacher(); break;
                case "9":
                    System.out.println("Exited.");
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }


    // =======================
    // STUDENT SIGN UP
    // =======================
    private void studentSignUp() {
        System.out.println("\n--- Student Sign Up ---");

        StudentSignUpDto dto = new StudentSignUpDto();
        dto.setFirstName(ask("First Name"));
        dto.setLastName(ask("Last Name"));

        dto.setBirthDate(readDate("Birth Date (YYYY-MM-DD)"));
        dto.setStudentCode(ask("Student Code"));
        dto.setFieldOfStudy(ask("Field of Study"));

        dto.setEntryYear(readInt("Entry Year"));

        if (!validateDto(dto)) return;

        try {
            Student saved = studentService.signUp(dto);
            System.out.println("Student Registered ID: " + saved.getId());

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }


    // =======================
    // ️TEACHER SIGN UP
    // =======================
    private void teacherSignUp() {
        System.out.println("\n--- Teacher Sign Up ---");

        TeacherSignUpDto dto = new TeacherSignUpDto();
        dto.setFirstName(ask("First Name"));
        dto.setLastName(ask("Last Name"));

        dto.setBirthDate(readDate("Birth Date (YYYY-MM-DD)"));
        dto.setTeacherCode(ask("Teacher Code"));
        dto.setQualification(ask("Qualification"));

        dto.setDegree(readDegree());
        dto.setMonthlySalary(readDouble("Monthly Salary"));

        if (!validateDto(dto)) return;

        try {
            Teacher saved = teacherService.signUp(dto);
            System.out.println("Teacher Registered ID: " + saved.getId());

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        }
    }


    // =======================
    // LIST METHODS
    // =======================
    private void listStudents() {
        System.out.println("\n--- STUDENTS ---");
        List<Student> students = studentService.loadAll();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.forEach(s -> System.out.println(s.getId() + " | " + s.getFirstName() + " " + s.getLastName()));
    }

    private void listTeachers() {
        System.out.println("\n--- TEACHERS ---");
        List<Teacher> teachers = teacherService.loadAll();
        if (teachers.isEmpty()) {
            System.out.println("No teachers found.");
            return;
        }
        teachers.forEach(t -> System.out.println(t.getId() + " | " + t.getFirstName() + " " + t.getLastName()));
    }


    // =======================
    // DELETE METHODS
    // =======================
//    private void deleteStudent() {
//        Long id = readLong("Enter Student ID to delete");
//        System.out.println( studentService.deleteById(id)
//                ? "Deleted successfully" : "Student not found");
//    }
//
//    private void deleteTeacher() {
//        Long id = readLong("Enter Teacher ID to delete");
//        System.out.println( teacherService.deleteById(id)
//                ? "Deleted successfully" : "Teacher not found");
//    }


    private void deleteStudent() {
        System.out.print("Enter Student Code to Delete: ");
        String code = scanner.nextLine().trim();

        try {
            boolean deleted = studentService.deleteByCode(code);

            if (deleted) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("No student found with this code.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteTeacher() {
        System.out.print("Enter Teacher Code to Delete: ");
        String code = scanner.nextLine().trim();

        try {
            boolean deleted = teacherService.deleteByCode(code);

            if (deleted) {
                System.out.println("Teacher deleted successfully.");
            } else {
                System.out.println("No teacher found with this code.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // =======================
    // UPDATE METHODS
    // =======================
    private void updateStudent() {
        String code = ask("Enter Student Code to update");

        StudentSignUpDto dto = new StudentSignUpDto();
        dto.setFirstName(ask("New First Name"));
        dto.setLastName(ask("New Last Name"));
        dto.setBirthDate(readDate("Birth Date (YYYY-MM-DD)"));
        dto.setStudentCode(ask("New Student Code"));
        dto.setFieldOfStudy(ask("Field of Study"));
        dto.setEntryYear(readInt("Entry Year"));

        if (!validateDto(dto)) return;

        try {
            studentService.update(code, dto);
            System.out.println("Teacher Updated");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private void updateTeacher() {
        String code = ask("Enter Teacher Code to update");

        TeacherSignUpDto dto = new TeacherSignUpDto();
        dto.setFirstName(ask("New First Name"));
        dto.setLastName(ask("New Last Name"));
        dto.setBirthDate(readDate("Birth Date (YYYY-MM-DD)"));
        dto.setTeacherCode(ask("New Teacher Code"));
        dto.setQualification(ask("New Qualification"));
        dto.setDegree(readDegree());
        dto.setMonthlySalary(readDouble("Monthly Salary"));

        if (!validateDto(dto)) return;

        try {
            teacherService.update(code, dto);
            System.out.println("Teacher Updated");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }


    // =======================
    // MINI INPUT HELPERS
    // =======================
    private String ask(String title) {
        System.out.print(title + ": ");
        return scanner.nextLine().trim();
    }

    private LocalDate readDate(String label) {
        while (true) {
            System.out.print(label + ": ");
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("Invalid format | Example: 1994-05-21");
            }
        }
    }

    private int readInt(String label) {
        while (true) {
            System.out.print(label + ": ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid number");
            }
        }
    }

    private long readLong(String label) {
        while (true) {
            System.out.print(label + ": ");
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid number");
            }
        }
    }

    private double readDouble(String label) {
        while (true) {
            System.out.print(label + ": ");
            try {
                double v = Double.parseDouble(scanner.nextLine().trim());
                if (v <= 0) {
                    System.out.println("Must be positive");
                    continue;
                }
                return v;
            } catch (Exception e) {
                System.out.println("Enter a valid amount");
            }
        }
    }

    private Degree readDegree() {
        while (true) {
            System.out.print("Degree (BACHELOR/MASTER/PHD): ");
            try {
                return Degree.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (Exception e) {
                System.out.println("Invalid degree");
            }
        }
    }


    // =======================
    // DTO VALIDATION
    // =======================
    private <T> boolean validateDto(T dto) {
        Set<ConstraintViolation<T>> errors = validator.validate(dto);

        if (!errors.isEmpty()) {
            System.out.println("Validation errors:");
            errors.forEach(err -> System.out.println(" - " + err.getMessage()));
            return false;
        }
        return true;
    }

}
