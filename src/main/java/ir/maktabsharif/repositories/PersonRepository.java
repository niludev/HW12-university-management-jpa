package ir.maktabsharif.repositories;

import ir.maktabsharif.models.Person;

import java.util.List;

public interface PersonRepository {

    Person save(Person person);

    Person update(Person person);

    Person delete(Person person);

    List<Person> loadAll();

    boolean contains(Person person);


}
