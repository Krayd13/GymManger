package com.example.gymmanager;

public enum Roles {
    MANAGER("manger"),
    ADMIN("admin"),
    VISITOR("visitor");

    private final String name;

    private Roles(String s){
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
