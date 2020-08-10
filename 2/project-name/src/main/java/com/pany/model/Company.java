package com.pany.model;

/**
 * represents a company
 * 
 * @author Sobek
 *
 */
public class Company {
    private float latitude;
    private float longitude;
    private String name;
    private String country;
    private String ipAddress;

    public Company(float latitude, float longitude, String name, String country, String ipAddress) {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.country = country;
        this.ipAddress = ipAddress;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
