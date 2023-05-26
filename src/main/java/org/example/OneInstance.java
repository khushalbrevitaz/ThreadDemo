package org.example;

public class OneInstance {
    private OneInstance() {
    }
    public String exmaple(){
        return "khushal";
    }
    public static OneInstance oneInstance = new OneInstance();
}

