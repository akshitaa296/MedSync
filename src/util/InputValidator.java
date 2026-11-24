package util;

import exceptions.InvalidInputException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputValidator {
    private static final Scanner sc = new Scanner(System.in);

    private static int parseInt(String s) throws InvalidInputException {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please enter a valid whole number.");
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return parseInt(sc.nextLine().trim());
            } catch (InvalidInputException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }

    public static int readIntInRange(String prompt, int min, int max) {
        while (true) {
            int v = readInt(prompt);
            if (v >= min && v <= max) return v;
            System.out.println("[ERROR] Enter a number between " + min + " and " + max + ".");
        }
    }

    public static int readPositiveInt(String prompt) {
        while (true) {
            int v = readInt(prompt);
            if (v > 0) return v;
            System.out.println("[ERROR] Value must be greater than 0.");
        }
    }

    public static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v > 0) return v;
                System.out.println("[ERROR] Value must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Please enter a valid number.");
            }
        }
    }

    public static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("[ERROR] This field cannot be empty.");
        }
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    public static boolean readYesNo(String prompt) {
        while (true) {
            String s = readLine(prompt).toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            System.out.println("[ERROR] Type y or n.");
        }
    }

    public static LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(sc.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("[ERROR] Use format yyyy-MM-dd (e.g. 2027-03-15).");
            }
        }
    }
}