package com.basereh.app;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("\nPlease select one of this options:");
        System.out.println("\t[1] Parse csv formated string");
        System.out.println("\t[2] Parse csv formated string (Typed by Enter)");

        Scanner scanner = new Scanner(System.in).useDelimiter("\n");
        int selectedOption = scanner.nextInt();
        switch (selectedOption) {
            case 1:
                String str = scanner.next();
                CSVManager.print(CSVManager.parser(str));
                break;
            case 2:
                System.out.print("enter number of rows: ");
                int rowLength = scanner.nextInt();
                String[] lines = new String[rowLength];
                for (int i = 0; i < rowLength; i++) {
                    lines[i] = scanner.next();
                }
                CSVManager.print(CSVManager.parser(String.join(";", lines)));
                break;
            default:
                System.out.println("Invalid option!");

        }

        scanner.close();
    }
}
