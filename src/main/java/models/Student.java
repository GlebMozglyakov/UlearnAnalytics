package models;


public class Student {
    private String ulearnId;
    private String name;
    private String surname;
    private String email;
    private String group;
    private String bdate;
    private String city;

    public String getUlearnId() {
        return ulearnId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }

    public String getBdate() {
        return bdate;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public void setSurname(String surname) {
        if (surname == null) {
            throw new IllegalArgumentException();
        }
        this.surname = surname;
    }

    public void setUlearnId(String ulearnId) {
        if (ulearnId == null) {
            throw new IllegalArgumentException();
        }
        this.ulearnId = ulearnId;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public void setGroup(String group) {
        if (group == null) {
            throw new IllegalArgumentException();
        }
        this.group = group;
    }

    public void setBdate(String bdate) {
        if (bdate == null) {
            throw new IllegalArgumentException();
        }
        this.bdate = bdate;
    }

    public void setCity(String city) {
        if (city == null) {
            throw new IllegalArgumentException();
        }
        this.city = city;
    }
}