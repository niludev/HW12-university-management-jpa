package ir.maktabsharif.config;


import ir.maktabsharif.repositories.PersonRepository;
import ir.maktabsharif.repositories.PersonRepositoryImpl;
import ir.maktabsharif.services.PersonService;
import ir.maktabsharif.services.PersonServiceImpl;
import ir.maktabsharif.view.UniversityConsoleApp;

public class ApplicationContext {

    private static ApplicationContext instance;

    // Repositories
    private PersonRepository personRepository;

    // Services
    private PersonService personService;

    private ApplicationContext() {}

    public static ApplicationContext getInstance() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public UniversityConsoleApp createApp() {
        return new UniversityConsoleApp(
                getPersonService()
        );
    }

    // ---------- Repositories ----------

//    lazy load:

    public PersonRepository getPersonRepository() {
        if (personRepository == null) {
            personRepository = new PersonRepositoryImpl();
        }
        return personRepository;
    }

    // ---------- Services ----------

    public PersonService getPersonService() {
        if(personService == null) {
            personService = new PersonServiceImpl(getPersonRepository());
        }
        return personService;
    }
}
