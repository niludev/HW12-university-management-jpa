package ir.maktabsharif.services;

import ir.maktabsharif.repositories.PersonRepository;

public class PersonServiceImpl implements PersonService{
    PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
}
