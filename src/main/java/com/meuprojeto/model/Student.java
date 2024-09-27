package com.meuprojeto.model;

import jakarta.persistence.Entity;

@Entity
public class Student extends Person {
    private String studentNumber;
    private String photo;

    // Getters
    public String getStudentNumber() {
        return studentNumber;
    }

    public String getPhoto() {
        return photo;
    }

    // Setters
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
