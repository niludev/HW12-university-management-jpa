package ir.maktabsharif.services;

import ir.maktabsharif.dto.StudentSignUpDto;
import ir.maktabsharif.models.Person;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.repositories.PersonRepository;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements PersonService<StudentSignUpDto, Student> {

    PersonRepository personRepository;

    public StudentServiceImpl (PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Student signUp(StudentSignUpDto dto) {

        if (personRepository.findByCode(dto.getStudentCode())
                .isPresent()
        ) {
            throw new IllegalArgumentException("Student code is already taken.");
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

    @Override
    public Student update(String studentCode, StudentSignUpDto dto) {

        if (studentCode == null || studentCode.isBlank()) {
            throw new IllegalArgumentException("Teacher code is required");
        }
        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }

        Person p = personRepository.findByCode(studentCode)
                .orElseThrow(() -> new IllegalArgumentException("Student not found with Code: " + studentCode));

        if (!(p instanceof Student)) {
            throw new IllegalArgumentException("This ID does not belong to a Student");
        }

        Student s = (Student) p;
        s.setFirstName(dto.getFirstName());
        s.setLastName(dto.getLastName());
        s.setBirthDate(dto.getBirthDate());
        s.setStudentCode(dto.getStudentCode());
        s.setFieldOfStudy(dto.getFieldOfStudy());
        s.setEntryYear(dto.getEntryYear());

        return (Student) personRepository.update(s);
    }

//    @Override
//    public boolean deleteById(Long id) {
//        if (id == null) return false;
//
//        return personRepository.findById(id)
//                .filter(p -> p instanceof Student)
//                .map(p -> {
//                    personRepository.delete(p);
//                    return true;
//
//                }).orElse(false);
//    }

    @Override
    public boolean deleteByCode(String code) {
        if (code == null || code.isBlank()) return false;

        return personRepository.findByCode(code)
                .map(s -> { personRepository.delete(s);
                    return true;

                })
                .orElse(false);
    }

    @Override
    public List<Student> loadAll() {
        return personRepository.loadAll().stream()
                .filter(p -> p instanceof Student)
                .map(p -> (Student)p)
                .collect(Collectors.toList());
    }
}
