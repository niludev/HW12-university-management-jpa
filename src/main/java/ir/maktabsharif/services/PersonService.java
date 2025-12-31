package ir.maktabsharif.services;

import ir.maktabsharif.models.Person;

public interface PersonService<T, E extends Person> {
    E signUp(T dto);
}
