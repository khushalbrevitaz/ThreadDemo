package org.example;

public class SecondInstance {
    public static void main(String[] args) {
        System.out.println(OneInstance.oneInstance.exmaple());
        OneInstance o = OneInstance.getInstance();
        o.exmaple();
    }
}
