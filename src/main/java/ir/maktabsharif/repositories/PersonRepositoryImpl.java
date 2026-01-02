package ir.maktabsharif.repositories;

import ir.maktabsharif.config.JpaUtil;
import ir.maktabsharif.models.Person;
import ir.maktabsharif.models.Student;
import ir.maktabsharif.models.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

import java.util.List;
import java.util.Optional;

public class PersonRepositoryImpl implements PersonRepository{
    @Override
    public Person save(Person person) {

        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }


        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();



        try {
            tx.begin();

            // faghat baraye sabte avalie dar DB
            em.persist(person);

            tx.commit();

            return person;

        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();

            // khataye DB
            throw new RuntimeException("Database error while saving Person", e);

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();

            // khataye gheyre montazere dg
            throw e;

        }finally {
            em.close();
        }
    }

    @Override
    public Person update(Person person) {

        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        if (person.getId() == null) {
            throw new IllegalArgumentException("Person ID is required for update");
        }

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

        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Database error while updating Person", e);

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;

        }finally {
            em.close();
        }
    }

    @Override
    public Person delete(Person person) {

        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        if (person.getId() == null) {
            throw new IllegalArgumentException("Person ID is required for delete");
        }


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

        } catch (PersistenceException e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Database error while deleting Person", e);

        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
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

            // inja transaction nadarim faghat query khundan hast
            throw new RuntimeException("Database error while loading all Persons", e);


        } finally {
            em.close();

        }
    }

    @Override
    public boolean contains(Person person) {

        if (person == null || person.getId() == null) {
            return false;
        }


        EntityManager em = JpaUtil.getEntityManager();

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
            throw new RuntimeException("Database error while checking Person existence", e);

        } finally {
            em.close();
        }

//        return true;
    }

    @Override
    public Optional<Person> findByCode(String code) {
        if (code == null || code.isBlank()) return Optional.empty();

        EntityManager em = JpaUtil.getEntityManager();

        try {
            return em.createQuery("""
            select p
            from Person p
            where
                (type(p) = Student and
                 exists (
                     select s.id 
                     from Student s
                        where s.id = p.id and s.studentCode = :code
                 ))
             or
                (type(p) = Teacher and
                 exists (
                     select t.id 
                     from Teacher t
                        where t.id = p.id and t.teacherCode = :code
                 ))
        """, Person.class)
                    .setParameter("code", code)
                    .getResultStream().findFirst();

//                    .getSingleResultOrNull();              //  vali moshkele uniq dare age az yeki bishtar bashe nayije error mide
//                    return Optional.ofNullable(result);

            // type(p) dar zamane runtime baresi mishe ke vaghan jensesh chie
            // chera mitunim in kar ro konim? chon jadval haye Student va Teacher az Person ersbari kardan:
            // @Inheritance(strategy = InheritanceType.JOINED)

            // exists ham ke baresi mikone aya hadeaghal ye record ba in shart vojud darad ya na --> true or false



        } finally {
            em.close();
        }

    }

//    @Override
//    public Optional<Person> findById(Long id) {
//        if (id == null) return Optional.empty();
//        EntityManager em = JpaUtil.getEntityManager();
//        try {
//            return Optional.ofNullable(em.find(Person.class, id));
//        } finally {
//            em.close();
//        }
//    }

    public Optional<Student> findByStudentCode(String code) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "select s from Student s where s.studentCode = :code", Student.class)
                    .setParameter("code", code)
                    .getResultStream().findFirst();
        } finally {
            em.close();
        }
    }

    public Optional<Teacher> findByTeacherCode(String code) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                            "select t from Teacher t where t.teacherCode = :code", Teacher.class)
                    .setParameter("code", code)
                    .getResultStream().findFirst();
        } finally {
            em.close();
        }
    }



//    @Override
//    public Optional<Person> findByCode(String code) {
//        EntityManager em = JpaUtil.getEntityManager();
//
//        try {
//            em.createQuery(
//                    "select p from Person p where (TYPE(p) = Student and p.stu) or ()"
//            )
//        }
//    }
}
