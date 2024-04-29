package com.service;


import com.entity.Person;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for managing person.
 */
public interface PersonService {

  /**
   * Get a list of persons.
   *
   * @return the {@link List} containing the {@link Person}
   * @see Person
   */
  List<Person> findAllPerson();


  /**
   * Creates a new user.
   *
   * @param person {@link Person} an object with user parameters to save.
   * @return the created person.
   * @see Person
   */
  Person createPerson(Person person);


  /**
   * Update person data.
   *
   * @param person {@link Person} an object with user parameters to save.
   * @return the created person.
   * @see Person
   */
  Person updatePerson(Person person);


  /**
   * Delete person by define id.
   *
   * @param id an object with user parameters to save.
   * @see Person
   */
  void deletePersonById(Long id);


  /**
   * Finding list of person between two date.
   *
   * @param from {@link LocalDate} date from which we start searching person by his birthday date
   * @param to   {@link LocalDate} date of which we end searching person by his birthday date
   * @return a {@link ResponseEntity} containing the {@link List {@link Person}} object .
   */
  List<Person> findPersonByBirthDateRange(LocalDate from, LocalDate to);
}
