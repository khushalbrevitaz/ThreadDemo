package org.example;

public class OneInstance {
    private OneInstance() {
    }
    public String exmaple(){
        return "khushal";
    }
    public static OneInstance getInstance(){
        return new OneInstance();
    }
    public static OneInstance oneInstance = new OneInstance();
}

