package com.example.a0711_0127122usafeadminapp;

import java.util.ArrayList;

public class Admin {
    private static ArrayList<Admin> adminList = new ArrayList<>();

    private String adminId;
    private String password;

    public Admin(String adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static ArrayList<Admin> getUserList() {
        return adminList;
    }
}
