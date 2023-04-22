package com.example.javahealthify.data.models;

public class Admin extends User{
    public Admin(String uid, String email, String name, String type) {
        super(uid, email, name, "ADMIN");
    }
}
