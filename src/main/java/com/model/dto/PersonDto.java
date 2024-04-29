package com.model.dto;

import com.common.ApplicationConstants;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;


/**
 * Incoming DTO to represent {@link com.entity.Person}.
 */
@Builder
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonDto {


  @Size(min = ApplicationConstants.DataValidation.MIN_SIZE_OF_NAME,
      max = ApplicationConstants.DataValidation.MAX_SIZE_OF_NAME)
  @NotBlank(message = "First name is required")
  String name;


  @Size(min = ApplicationConstants.DataValidation.MIN_SIZE_OF_SURNAME,
      max = ApplicationConstants.DataValidation.MAX_SIZE_OF_SURNAME)
  @Size(min = 2, max = 48, message = "Size must be between 2 and 48 symbols")
  @NotBlank(message = "Please input data")
  String surname;


  @Size(min = ApplicationConstants.DataValidation.MIN_SIZE_OF_EMAIL,
      max = ApplicationConstants.DataValidation.MAX_SIZE_OF_EMAIL)
  @NotBlank(message = "First name is required")
  @NotEmpty
  @Email(message = "Invalid email format")
  String email;


  @Past(message = "Birth date must be in the past")
  LocalDate dateOfBirth;


  @Size(min = ApplicationConstants.DataValidation.MIN_SIZE_OF_ADDRESS,
      max = ApplicationConstants.DataValidation.MAX_SIZE_OF_ADDRESS)
  String address;


  @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
  String phoneNumber;

}
