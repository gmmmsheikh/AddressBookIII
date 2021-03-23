package com.college.addressbookiii.Model;

public class ContactModel {
    private int id, status;
    private String firstName;

    public ContactModel(int id, int status, String firstName) {
        this.id = id;
        this.status = status;
        this.firstName = firstName;
    }
    public ContactModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
