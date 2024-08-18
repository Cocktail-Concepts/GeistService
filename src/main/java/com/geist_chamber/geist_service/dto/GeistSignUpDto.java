package com.geist_chamber.geist_service.dto;

public class GeistSignUpDto {

    private String email;
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public GeistSignUpDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "GeistSignUpDTO{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public GeistSignUpDto() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
