package com.service.impl;

import com.common.mapper.impl.PersonMapperImpl;
import com.entity.Person;
import com.repository.PersonRepository;
import com.service.PersonService;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Implementation of interface {@link PersonService}.
 */

@Service
@Data
public class PersonServiceImpl implements PersonService {

  private final PersonRepository personRepository;
  private final Environment environment;
  private final PersonMapperImpl personMapper;


  @Autowired
  public PersonServiceImpl(PersonRepository personRepository, Environment environment, PersonMapperImpl personMapper) {
    this.personRepository = personRepository;
    this.environment = environment;
    this.personMapper = personMapper;
  }


  public List<Person> findAllPerson() {

    return personRepository.findAll();
  }


  public Person createPerson(Person person) {
    return personRepository.save(person);
  }


  public Person updatePerson(Person person) {
    return personRepository.save(person);
  }


  public void deletePersonById(Long id) {
    if (!personRepository.existsById(id)) {
      throw new NoSuchElementException("No such person found with id: " + id);
    }
    personRepository.deleteById(id);
  }


  public List<Person> findPersonByBirthDateRange(LocalDate from, LocalDate to) {
    return personRepository.findByDateOfBirthBetween(from, to);
  }


}
