package com.example.a0711_0127122usafeadminapp;

public class MainMenuItem {
    private String menuName;

    public MainMenuItem(){
        menuName = "";
    }

    public MainMenuItem(String menuName){
        this.menuName = menuName;
    }

    public String getMenuName(){
        return menuName;
    }

    public void setMenuName(String menuName){
        this.menuName = menuName;
    }
}