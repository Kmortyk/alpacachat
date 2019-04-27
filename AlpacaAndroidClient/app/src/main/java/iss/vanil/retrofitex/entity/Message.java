package iss.vanil.retrofitex.entity;

public class Message {

    public String account_key;
    public String name;
    public String message;

    public Message(String account_key, String message) {
        this.account_key = account_key;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getNickname() {
        return name;
    }

}
