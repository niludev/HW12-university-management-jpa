package ir.maktabsharif.repositories;

import ir.maktabsharif.models.Person;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);

    Person update(Person person);

    Person delete(Person person);

    List<Person> loadAll();

    boolean contains(Person person);

    Optional<Person> findByCode(String code);



//    Optional<Person> findById(Long id);




//    Optional<Student> findByStudentCode(String code);
//    Optional<Teacher> findByTeacherCode(String code);


    // ye method baraye peyda kardane
    // age null shod error nakhore --> baraye hamin Optional
//    Optional<Person> findByCode(String code);

}
