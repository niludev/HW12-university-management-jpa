package ir.maktabsharif.services;

import ir.maktabsharif.dto.TeacherSignUpDto;
import ir.maktabsharif.models.Person;
import ir.maktabsharif.models.Teacher;
import ir.maktabsharif.repositories.PersonRepository;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherServiceImpl implements PersonService<TeacherSignUpDto, Teacher> {
    PersonRepository personRepository;

    public TeacherServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    @Override
    public Teacher signUp(TeacherSignUpDto dto) {

        if (personRepository.findByTeacherCode(dto.getTeacherCode()).isPresent()) {
            throw new IllegalArgumentException("Teacher code is already taken.");
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

    @Override
    public Teacher update(Long id, TeacherSignUpDto dto) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null");
        if (dto == null) throw new IllegalArgumentException("DTO cannot be null");

        Person p = personRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + id));

        if (!(p instanceof Teacher)) {
            throw new IllegalArgumentException("This ID does not belong to a Teacher");
        }

        Teacher t = (Teacher) p;
        t.setFirstName(dto.getFirstName());
        t.setLastName(dto.getLastName());
        t.setBirthDate(dto.getBirthDate());
        t.setTeacherCode(dto.getTeacherCode());
        t.setQualification(dto.getQualification());
        t.setDegree(dto.getDegree());
        t.setMonthlySalary(dto.getMonthlySalary());

        return (Teacher) personRepository.update(t);
    }


    @Override
    public boolean deleteById(Long id) {
        if (id == null) return false;

        return personRepository.findById(id)
                .filter(p -> p instanceof Teacher)
                .map(p -> { personRepository.delete(p);
                    return true;

                })
                .orElse(false);
    }


    @Override
    public boolean deleteByCode(String code) {
        if (code == null || code.isBlank()) return false;

        return personRepository.findByTeacherCode(code)
                .map(t -> { personRepository.delete(t);
                    return true;

                })
                .orElse(false);
    }

    @Override
    public List<Teacher> loadAll() {
        return personRepository.loadAll().stream()
                .filter(p -> p instanceof Teacher)
                .map(p -> (Teacher)p)
                .collect(Collectors.toList());
    }
}
