package com.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
 * User class represents a user in the db.
 */
@Entity
@Table(name = "Person")
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;

  @Column(name = "name")
  @NotBlank(message = "First name is required")
   String name;

  @Column(name = "surname")
  @Size(min = 2, max = 48, message = "Size must be between 2 and 48 symbols")
  @NotBlank(message = "Please input data")
   String surname;

  @NotBlank(message = "Email is required")
  @Email(message = "Invalid email format")
   String email;

  @Column(name = "date_of_birth")
  @Past(message = "Birth date must be in the past")
   LocalDate dateOfBirth;

  @Column(name = "address")
   String address;

  @Column(name = "phoneNumber")
  @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number format")
   String phoneNumber;


}