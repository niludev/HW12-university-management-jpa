package ir.maktabsharif.services;

import ir.maktabsharif.dto.TeacherSignUpDto;
import ir.maktabsharif.models.Teacher;
import ir.maktabsharif.repositories.PersonRepository;
import jakarta.persistence.PersistenceException;

public class TeacherServiceImpl implements PersonService<TeacherSignUpDto, Teacher> {
    PersonRepository personRepository;

    public TeacherServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public Teacher signUp(TeacherSignUpDto dto) {

        if (dto == null) {
            throw new IllegalArgumentException("DTO cannot be null");
        }

        Teacher teacher = new Teacher();
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setBirthDate(dto.getBirthDate());
        teacher.setTeacherCode(dto.getTeacherCode());
        teacher.setQualification(dto.getQualification());
        teacher.setDegree(dto.getDegree());
        teacher.setMonthlySalary(dto.getMonthlySalary());

        try {
            return (Teacher) personRepository.save(teacher);
        } catch (PersistenceException e) {
            throw new RuntimeException("Error saving teacher: " + e.getMessage(), e);
        }
    }
}
