package ru.kata.spring.boot_security.demo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

    @NotEmpty(message = "Name shouldn't be empty")
    @NotNull(message = "Name should't be null")
    @Size(min = 1, max = 30, message = "Name must be between 1 and 30 characters")
    private String name;

    @NotEmpty(message = "Surname shouldn't be empty")
    @NotNull(message = "Surname should't be null")
    @Size(min = 1, max = 30, message = "Surname must be between 1 and 30 characters")
    private String surname;

    @NotNull(message = "Age shouldn't be null")
    @Min(value = 18, message = "Age must be over 18 years (inclusive)")
    private Integer age;

    @Email
    @NotNull(message = "Email shouldn't be null")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
