package ir.maktabsharif.repositories;

import ir.maktabsharif.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Person save(Person person);

    Person update(Person person);

    Person delete(Person person);

    List<Person> loadAll();

    boolean contains(Person person);

    // ye method baraye peyda kardane
    // age null shod error nakhore --> baraye hamin Optional
//    Optional<Person> findByCode(String code);

}
