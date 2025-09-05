package Lab1;

import java.util.Scanner;

//Caesar Cipher
public class ShiftCipher {

    // Check if plaintext is valid: only lowercase letters
    public static boolean isValidPlaintext(String text) {
        return text.matches("[a-z]+");
    }

    // Check if ciphertext is valid: only uppercase letters
    public static boolean isValidCiphertext(String text) {
        return text.matches("[A-Z]+");
    }

    // Encryption method (lowercase -> uppercase)
    public static String encrypt(String plaintext, int key) {
        if (!isValidPlaintext(plaintext)) {
            return "Error: Plaintext must contain only lowercase letters (a-z).";
        }

        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            int shifted = (ch - 'a' + key) % 26;
            char encryptedChar = (char) ('A' + shifted);
            ciphertext.append(encryptedChar);
        }

        return ciphertext.toString();
    }

    // Decryption method (uppercase -> lowercase)
    public static String decrypt(String ciphertext, int key) {
        if (!isValidCiphertext(ciphertext)) {
            return "Error: Ciphertext must contain only uppercase letters (A-Z).";
        }

        StringBuilder plaintext = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            int shifted = (ch - 'A' - key + 26) % 26;
            char decryptedChar = (char) ('a' + shifted);
            plaintext.append(decryptedChar);
        }

        return plaintext.toString();
    }

    // Brute force method to try all 25 possible keys
    public static void bruteForce(String ciphertext) {
        if (!isValidCiphertext(ciphertext)) {
            System.out.println("Error: Ciphertext must contain only uppercase letters (A-Z).");
            return;
        }

        System.out.println("\n🔍 Brute-force results:\n");
        for (int key = 1; key < 26; key++) {
            String attempt = decrypt(ciphertext, key);
            System.out.printf("Key %2d: %s%n", key, attempt);
        }
    }

    // Main program (demo)
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter plaintext (lowercase only): ");
        String plaintext = sc.nextLine();

        System.out.print("Enter key (1-25): ");
        int key = sc.nextInt();
        sc.nextLine(); // consume newline

        // Encrypt
        String encrypted = encrypt(plaintext, key);
        System.out.println("Encrypted ciphertext: " + encrypted);

        // Decrypt
        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted plaintext: " + decrypted);

        // Brute force
        bruteForce(encrypted);

        sc.close();
    }
}