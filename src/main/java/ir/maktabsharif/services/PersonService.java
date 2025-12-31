package ir.maktabsharif.services;

import ir.maktabsharif.models.Person;

public interface PersonService<T, R> {
    R signUp(T dto);
}

