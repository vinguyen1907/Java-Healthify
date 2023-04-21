package com.example.javahealthify.data.models;

public class Admin extends User{
    public Admin(String email, String name, String type) {
        super(email, name, "ADMIN");
    }
}
