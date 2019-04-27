package iss.vanil.retrofitex.entity;

public class Message {

    public String account_key;
    public String name;
    public String message;

    private boolean isSentByUser = false;

    public Message(String account_key, String message) {
        this.account_key = account_key;
        this.message = message;
    }

    public void setSentByUser() { isSentByUser = true; }

    public boolean isSentByUser() { return isSentByUser; }

}
