package org.example.util;

import java.util.Scanner;

public class Console {
    private static final Scanner in = new Scanner(System.in);

    public static String ask(String label) {
        System.out.print(label + ": ");
        return in.nextLine().trim();
    }

    public static int askInt(String label) {
        while (true) {
            try {
                return Integer.parseInt(ask(label));
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
    }
}

