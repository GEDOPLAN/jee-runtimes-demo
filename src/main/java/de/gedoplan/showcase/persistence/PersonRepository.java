package de.gedoplan.showcase.persistence;

import de.gedoplan.showcase.entity.Person;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional(rollbackOn = Exception.class)
public class PersonRepository {

  @PersistenceContext
  EntityManager entityManager;

  public List<Person> findAll() {
    return this.entityManager
        .createQuery("select x from Person x", Person.class)
        .getResultList();
  }

  public Person findById(Integer id) {
    return this.entityManager.find(Person.class, id);
  }

  public void persist(Person person) {
    this.entityManager.persist(person);
  }

  public void merge(Person person) {
    this.entityManager.merge(person);
  }

  public void removeById(Integer id) {
    Person person = this.entityManager.find(Person.class, id);
    if (person != null) {
      this.entityManager.remove(person);
    }
  }

}
