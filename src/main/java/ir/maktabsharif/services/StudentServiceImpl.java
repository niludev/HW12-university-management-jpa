package ir.maktabsharif.services;

import ir.maktabsharif.dto.StudentSignUpDto;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.repositories.PersonRepository;
import jakarta.persistence.PersistenceException;

public class StudentServiceImpl implements PersonService<StudentSignUpDto, Student> {

    PersonRepository personRepository;

    public StudentServiceImpl (PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Student signUp(StudentSignUpDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setBirthDate(dto.getBirthDate());
        student.setStudentCode(dto.getStudentCode());
        student.setFieldOfStudy(dto.getFieldOfStudy());
        student.setEntryYear(dto.getEntryYear());


        try {
            return (Student) personRepository.save(student);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error saving student: " + e.getMessage(), e);
        }

    }
}
