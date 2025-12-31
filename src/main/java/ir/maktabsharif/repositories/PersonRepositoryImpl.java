package ir.maktabsharif.repositories;

import ir.maktabsharif.config.JpaUtil;
import ir.maktabsharif.models.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.List;

public class PersonRepositoryImpl implements PersonRepository{
    @Override
    public Person save(Person person) {

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // faghat baraye sabte avalie dar DB
            em.persist(person);

            tx.commit();

            return person;

        } catch (RuntimeException e) {
            if(tx.isActive()) tx.rollback();
            throw e;

        }finally {
            em.close();
        }
    }

    @Override
    public Person update(Person person) {

        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

//            in kheili pichide bud:
//            Person foundedPerson = em.find(Person.class, person.getId());
//
//            if (foundedPerson == null) {
//                return null;
//            }
//
//            // bordane person be halate managed va amade baraye zakhire dar DB
//            Person personToUpdate = em.merge(person);


//            merge khodesh handle mikone niazi nist ghablesh person ro peyda konim:
//            merge age record vojod nadashte bashe exception mide
            Person merged = em.merge(person);


            // zakhire dar DB
            tx.commit();

//            return personToUpdate;

            return merged;

        } catch (RuntimeException e) {
            if(tx.isActive()) tx.rollback();
            throw e;

        }finally {
            em.close();
        }
    }

    @Override
    public Person delete(Person person) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            //check mikonim ke bebinim aya dar halate managed hast yana vali nemige aya dar DB hast ya na
//            Person attachedPerson = em.contains(person) ? person : em.merge(person);

            Person toDeletePerson = em.find(Person.class, person.getId());

            if(toDeletePerson == null) {
                return null;
            }

            em.remove(toDeletePerson);

            tx.commit();

            return toDeletePerson;

        } catch (RuntimeException e) {
            if(tx.isActive()) tx.rollback();
            throw e;

        }finally {
            em.close();
        }
    }

    @Override
    public List<Person> loadAll() {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery(
                    "select p from Person p",
                    Person.class

            ).getResultList();

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();

        }
    }

    @Override
    public boolean contains(Person person) {
        EntityManager em = JpaUtil.getEntityManager();

        if(person.getId() == null) {
            return false;
        }

        try {

//            in tulani hast:
//            Person existingPerson = em.find(Person.class, person.getId());
//
//            if(existingPerson == null) {
//                return false;
//            }

//            be jash in:
            return em.find(Person.class, person.getId()) != null;

        } catch (PersistenceException e) {
            throw e;

        } finally {
            em.close();
        }

//        return true;
    }
}
