package iss.vanil.retrofitex.entity;

public class Account {

    String name;
    String key;

    public Account(String name) { this.name = name; }

    public Account(String name, String key) {
        this.name = name;
        this.key = key;
    }

}
