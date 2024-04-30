package com.controller;

import com.common.mapper.impl.PersonMapperImpl;
import com.entity.Person;
import com.model.dto.PersonDto;
import com.service.impl.PersonServiceImpl;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for user.
 */
@RestController
@RequestMapping(value = "/person")
@PropertySource("classpath:configuration.person/person.properties")
public class PersonController {

  private final PersonServiceImpl personServiceImpl;
  private final Environment environment;
  private final PersonMapperImpl personMapper;

  @Autowired
  public PersonController(PersonServiceImpl personServiceImpl, Environment environment, PersonMapperImpl personMapper) {
    this.personServiceImpl = personServiceImpl;
    this.environment = environment;
    this.personMapper = personMapper;
  }


  /**
   * Retrieves a list of all persons .
   *
   * @return a {@link ResponseEntity} containing the {@link List {@link Person}} object and an HTTP status code.
   */
  @GetMapping(path = "/")
  public ResponseEntity<List<Person>> findAll() {

    List<Person> list = personServiceImpl.findAllPerson();
    if (list.isEmpty()) {
      throw new NoSuchElementException("No such person found in the list of persons");
    }
    return ResponseEntity.ok(list);
  }


  /**
   * Responsible for a creating person.
   *
   * @param personDto {@link PersonDto} object containing the person's information
   * @return a {@link ResponseEntity} containing the {@link PersonDto} object and an HTTP status code.
   */
  @PostMapping(path = "/")
  public ResponseEntity<PersonDto> createPerson(@Valid @RequestBody PersonDto personDto) {
    final var personRequest = personMapper.personDtoToPerson(personDto);


    final LocalDate currentDate = LocalDate.now();
    int minimumAge = Integer.parseInt(environment.getProperty("person.minimum.age"));
    int age = Period.between(personRequest.getDateOfBirth(), currentDate).getYears();

    if (age < minimumAge) {
      throw new IllegalArgumentException("The person's age is less than " + minimumAge + " years, registration is not possible.");
    }

    final var person = personServiceImpl.createPerson(personRequest);
    final var personResponse = personMapper.personToPersonDto(person);

    return ResponseEntity.status(HttpStatus.CREATED).body(personResponse);
  }


  /**
   * Responsible for a update information about person.
   *
   * @param personDto {@link PersonDto} object containing the person's information
   * @return a {@link ResponseEntity} containing the {@link PersonDto} object and an HTTP status code.
   */
  @PutMapping(path = "/")
  public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody PersonDto personDto) {
    final var personRequest = personMapper.personDtoToPerson(personDto);
    final var person = personServiceImpl.updatePerson(personRequest);
    final var personResponse = personMapper.personToPersonDto(person);
    return ResponseEntity.ok(personResponse);
  }


  /**
   * Responsible for deleting person by define id.
   *
   * @param id {@link Long} id define by person will be delete
   * @return a {@link ResponseEntity} containing the {@link Void} object and an HTTP status code.
   */
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deletePersonById(@PathVariable("id") Long id) {
    personServiceImpl.deletePersonById(id);
    return ResponseEntity.ok().build();
  }


  /**
   * Responsible for finding list of person between two date.
   *
   * @param from {@link LocalDate} date from which we start searching person by his birthday date
   * @param to   {@link LocalDate} date of which we end searching person by his birthday date
   * @return a {@link ResponseEntity} containing the {@link List {@link Person}} object and an HTTP status code.
   */
  @GetMapping(path = "/search/{from}/{to}")
  public ResponseEntity<List<Person>> findPersonByBirthDateRange(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
    if (from.isAfter(to)) {
      throw new IllegalArgumentException("Invalid date range: 'from' date should not be after 'to' date");
    }
    final List<Person> personList = personServiceImpl.findPersonByBirthDateRange(from, to);
    if (personList.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(personList);
  }


}
