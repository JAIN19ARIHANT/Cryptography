package Lab6;

import java.util.Scanner;

public class AutokeyCipher {

    static String RED = "\u001b[31m";
    static String BLUE = "\u001b[34m";
    static String RESET = "\u001b[0m";

    public static boolean isValidText(String text) {
        return text != null && !text.isEmpty() && text.matches("[a-z]+");
    }

    public static boolean isValidCiphertext(String text) {
        return text != null && !text.isEmpty() && text.matches("[A-Z]+");
    }

    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        String keyStream = (key + plaintext).substring(0, plaintext.length());

        for (int i = 0; i < plaintext.length(); i++) {
            int p = plaintext.charAt(i) - 'a';
            int k = keyStream.charAt(i) - 'a';

            int c = (p + k) % 26;
            ciphertext.append((char) (c + 'A'));
        }

        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            int c = ciphertext.charAt(i) - 'A';
            int k;

            if (i < key.length()) {
                k = key.charAt(i) - 'a';
            } else {
                k = plaintext.charAt(i - key.length()) - 'a';
            }

            int p = (c - k + 26) % 26;
            plaintext.append((char) (p + 'a'));
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------- Autokey Cipher Menu -------");
            System.out.println("1. Encrypt Text");
            System.out.println("2. Decrypt Text");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println(RED + "Invalid input. Please enter a number." + RESET);
                sc.next();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: {
                    String plaintext;
                    while (true) {
                        System.out.print("Enter plaintext (lowercase only): ");
                        plaintext = sc.nextLine();
                        if (isValidText(plaintext)) break;
                        System.out.println(RED + "Error: Use only lowercase letters (a-z)." + RESET);
                    }

                    String key;
                    while (true) {
                        System.out.print("Enter key (lowercase only): ");
                        key = sc.nextLine();
                        if (isValidText(key)) break;
                        System.out.println(RED + "Error: Use only lowercase letters (a-z)." + RESET);
                    }

                    String encrypted = encrypt(plaintext, key);
                    System.out.println(BLUE + "✅ Encrypted Ciphertext: " + encrypted + RESET);
                    break;
                }

                case 2: {
                    String ciphertext;
                    while (true) {
                        System.out.print("Enter ciphertext (uppercase only): ");
                        ciphertext = sc.nextLine();
                        if (isValidCiphertext(ciphertext)) break;
                        System.out.println(RED + "Error: Use only uppercase letters (A-Z)." + RESET);
                    }

                    String key;
                    while (true) {
                        System.out.print("Enter key (lowercase only): ");
                        key = sc.nextLine();
                        if (isValidText(key)) break;
                        System.out.println(RED + "Error: Use only lowercase letters (a-z)." + RESET);
                    }

                    String decrypted = decrypt(ciphertext, key);
                    System.out.println(BLUE + "✅ Decrypted Plaintext: " + decrypted + RESET);
                    break;
                }

                case 3:
                    System.out.println("Exiting program. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println(RED + "Invalid choice. Please enter a number between 1 and 3." + RESET);
                    break;
            }
        }
    }
}