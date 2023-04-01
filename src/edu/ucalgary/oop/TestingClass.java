package edu.ucalgary.oop;

import java.time.LocalDate;
import java.lang.*;
import java.util.Scanner;
public class TestingClass {
    public static void main(String[] args) {
        System.out.println(LocalDate.now());  // prints the date for today.
        System.out.println("Text to be printed");
        Scanner scan = new Scanner(System.in);          // established a 'Scanner' object in order to read-in user input.
        System.out.print("Write a message: ");
        String message = scan.nextLine();
        System.out.println(message);

    }
}

