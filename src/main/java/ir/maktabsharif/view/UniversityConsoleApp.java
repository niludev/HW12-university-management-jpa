package ir.maktabsharif.view;

import ir.maktabsharif.dto.StudentSignUpDto;
import ir.maktabsharif.dto.TeacherSignUpDto;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.models.Teacher;
import ir.maktabsharif.services.PersonService;

public class UniversityConsoleApp {

    private final PersonService<TeacherSignUpDto, Teacher> teacherService;
    private final PersonService<StudentSignUpDto, Student> studentService;

    public UniversityConsoleApp(
            PersonService<TeacherSignUpDto, Teacher> teacherService,
            PersonService<StudentSignUpDto, Student> studentService
    ) {
        this.teacherService = teacherService;
        this.studentService = studentService;
    }

    public void run() {
        System.out.println("University App is running...");
    }
}
