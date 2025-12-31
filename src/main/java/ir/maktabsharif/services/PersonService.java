package ir.maktabsharif.services;

import ir.maktabsharif.models.Person;

import java.util.List;

public interface PersonService<D, T> {
    T signUp(D dto);

    T update(Long id, D dto);

    boolean deleteById(Long id);

    boolean deleteByCode(String code);  // studentCode / teacherCode

    List<T> loadAll();
}

