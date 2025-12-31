package ir.maktabsharif.services;

import ir.maktabsharif.dto.TeacherSignUpDto;
import ir.maktabsharif.models.Teacher;
import ir.maktabsharif.repositories.PersonRepository;

public class TeacherServiceImpl implements PersonService<TeacherSignUpDto, Teacher>{
    PersonRepository personRepository;

    public TeacherServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public Teacher signUp(TeacherSignUpDto dto) {

        // TODO: validations

        Teacher teacher = new Teacher();
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setBirthDate(dto.getBirthDate());
        teacher.setTeacherCode(dto.getTeacherCode());
        teacher.setQualification(dto.getQualification());
        teacher.setDegree(dto.getDegree());
        teacher.setMonthlySalary(dto.getMonthlySalary());

        return (Teacher) personRepository.save(teacher);
    }
}
