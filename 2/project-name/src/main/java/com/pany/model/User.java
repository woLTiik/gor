package com.pany.model;

/**
 * Represents user detail
 *
 * @author Sobek
 *
 */
public class User extends Record {
    private String email;
    private Company company;
    private String detailID;

    public User(String id, String firstName, String lastName, String avatar) {
        super(id, firstName, lastName, avatar);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDetailID() {
        return detailID;
    }

    public void setDetailID(String detailID) {
        this.detailID = detailID;
    }

}
