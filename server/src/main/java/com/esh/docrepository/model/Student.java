package com.esh.docrepository.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "students")
public class Student {

    @Id
    private String passportId;
    private String name;
    private String surname;
    private String email;

    public Student(String passportId, String name, String surname, String email) {
        super();
        this.passportId = passportId;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Student() {}

    public String getPassportId() {
        return passportId;
    }

    public void setPassportId(String passportId) {
        this.passportId = passportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student [passportId=" + passportId + ", name=" + name + ", surname=" + surname + ", email=" + email + "]";
    }

}
