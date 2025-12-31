package ir.maktabsharif.config;


import ir.maktabsharif.dto.StudentSignUpDto;
import ir.maktabsharif.dto.TeacherSignUpDto;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.models.Teacher;
import ir.maktabsharif.repositories.PersonRepository;
import ir.maktabsharif.repositories.PersonRepositoryImpl;
import ir.maktabsharif.services.PersonService;
import ir.maktabsharif.services.StudentServiceImpl;
import ir.maktabsharif.services.TeacherServiceImpl;
import ir.maktabsharif.view.UniversityConsoleApp;

public class ApplicationContext {

    private static ApplicationContext instance;

    // Repositories
    private PersonRepository personRepository;

    // Services
    private PersonService<TeacherSignUpDto, Teacher> teacherService;
    private PersonService<StudentSignUpDto, Student> studentService;

    private ApplicationContext() {}

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public UniversityConsoleApp createApp() {
        return new UniversityConsoleApp(
                getTeacherService(),
                getStudentService()
        );
    }

    // ---------- Repositories ----------

//    lazy load:

    public PersonRepository getPersonRepository() {
        if (personRepository == null) {
            personRepository = new PersonRepositoryImpl();
        }
        return personRepository;
    }

    // ---------- Services ----------

    public PersonService<TeacherSignUpDto, Teacher> getTeacherService() {
        if(teacherService == null) {
            teacherService = new TeacherServiceImpl(getPersonRepository());
        }
        return teacherService;
    }

    public PersonService<StudentSignUpDto, Student> getStudentService() {
        if(studentService == null) {
            studentService = new StudentServiceImpl(getPersonRepository());
        }
        return studentService;
    }
}
