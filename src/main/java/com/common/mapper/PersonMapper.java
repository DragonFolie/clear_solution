package com.common.mapper;

import com.entity.Person;
import com.model.dto.PersonDto;
import org.springframework.stereotype.Component;

/**
 * This interface defines methods for mapping between the {@link com.entity.Person} and DTO classes.
 */
@Component
public interface PersonMapper {


  /**
   * Maps a {@link PersonDto} object to a {@link Person} object.
   *
   * @param personDto The {@link PersonDto} object to be mapped.
   * @return The resulting {@link Person} object.
   */
  Person personDtoToPerson(PersonDto personDto);


  /**
   * Maps a {@link Person} object to a {@link PersonDto} object.
   *
   * @param person The {@link Person} object to be mapped.
   * @return The resulting {@link PersonDto} object.
   */
  PersonDto personToPersonDto(Person person);

}
