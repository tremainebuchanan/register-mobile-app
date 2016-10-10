package com.tremainebuchanan.register.data;

/**
 * Created by captain_kirk on 10/3/16.
 */

public class Student {
    private String id;
    private String name;
    private String gender;
    private boolean isPresent;
    private String contact;

    public Student(String id, String name, String gender, boolean isPresent, String contact) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.isPresent = isPresent;
        this.contact = contact;

    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getId(){ return id;  }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
