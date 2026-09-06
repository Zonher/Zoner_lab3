package ru.nikita.lab2.application;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Application().start();
        } catch (IOException e) {
            System.out.println("Console reader exception");
        }
    }
}
