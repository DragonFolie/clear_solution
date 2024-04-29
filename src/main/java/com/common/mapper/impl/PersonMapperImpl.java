package com.common.mapper.impl;

import com.common.mapper.PersonMapper;
import com.entity.Person;
import com.model.dto.PersonDto;
import com.service.PersonService;
import org.springframework.stereotype.Component;

/**
 * Implementation of interface {@link PersonMapper}.
 */

@Component
public class PersonMapperImpl implements PersonMapper {


  /**
   * Maps a {@link PersonDto} object to a {@link Person} object.
   *
   * @param personDto The {@link PersonDto} object to be mapped.
   * @return The resulting {@link Person} object.
   */
  @Override
  public Person personDtoToPerson(PersonDto personDto) {
    if (personDto == null) {
      return null;
    }

    Person.PersonBuilder person = Person.builder();

    person.name(personDto.getName());
    person.surname(personDto.getSurname());
    person.email(personDto.getEmail());
    person.dateOfBirth(personDto.getDateOfBirth());
    person.address(personDto.getAddress());
    person.phoneNumber(personDto.getPhoneNumber());

    return person.build();
  }

  /**
   * Maps a {@link Person} object to a {@link PersonDto} object.
   *
   * @param person The {@link Person} object to be mapped.
   * @return The resulting {@link PersonDto} object.
   */

  @Override
  public PersonDto personToPersonDto(Person person) {

    if (person == null) {
      return null;
    }

    PersonDto.PersonDtoBuilder personDto = PersonDto.builder();

    personDto.name(person.getName());
    personDto.surname(person.getSurname());
    personDto.email(person.getEmail());
    personDto.dateOfBirth(person.getDateOfBirth());
    personDto.address(person.getAddress());
    personDto.phoneNumber(person.getPhoneNumber());

    return personDto.build();
  }
}
