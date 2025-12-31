package ir.maktabsharif.services;

import ir.maktabsharif.dto.StudentSignUpDto;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.repositories.PersonRepository;

public class StudentServiceImpl implements PersonService<StudentSignUpDto, Student> {

    PersonRepository personRepository;

    public StudentServiceImpl (PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Student signUp(StudentSignUpDto dto) {

        // TODO: validations

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setBirthDate(dto.getBirthDate());
        student.setStudentCode(dto.getStudentCode());
        student.setFieldOfStudy(dto.getFieldOfStudy());
        student.setEntryYear(dto.getEntryYear());

        return (Student) personRepository.save(student);
    }
}
