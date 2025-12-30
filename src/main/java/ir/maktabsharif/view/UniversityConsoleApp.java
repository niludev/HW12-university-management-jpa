package ir.maktabsharif.view;

import ir.maktabsharif.repositories.PersonRepository;
import ir.maktabsharif.services.PersonService;

public class UniversityConsoleApp {

    PersonService personService;

    public UniversityConsoleApp(PersonService personService) {
        this.personService = personService;
    }
}
