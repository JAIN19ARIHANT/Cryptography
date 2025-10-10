package Lab1;

import java.util.Scanner;

// Caesar Cipher
public class ceaserCipher {

    static String RED = "\u001b[31m";
    static String RESET = "\u001b[0m";

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
        // The effectiveKey calculation is no longer strictly necessary with the new
        // 1-25 validation, but it's good practice to keep it as a safeguard.
        int effectiveKey = key % 26;
        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            int shifted = (ch - 'a' + effectiveKey) % 26;
            char encryptedChar = (char) ('A' + shifted);
            ciphertext.append(encryptedChar);
        }

        return ciphertext.toString();
    }

    // Decryption method (uppercase -> lowercase)
    public static String decrypt(String ciphertext, int key) {
        int effectiveKey = key % 26;
        StringBuilder plaintext = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            int shifted = (ch - 'A' - effectiveKey + 26) % 26;
            char decryptedChar = (char) ('a' + shifted);
            plaintext.append(decryptedChar);
        }

        return plaintext.toString();
    }

    // Brute force method to try all 25 possible keys
    public static void bruteForce(String ciphertext) {
        if (!isValidCiphertext(ciphertext)) {
            System.out.println(RED + "Error: Ciphertext must contain only uppercase letters (A-Z)." + RESET);
            return;
        }

        System.out.println("\n🔍 Brute-force results:\n");
        for (int key = 1; key < 26; key++) {
            String attempt = decrypt(ciphertext, key);
            System.out.printf("Key %2d: %s%n", key, attempt);
        }
    }

    // Main program with improved key validation
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------- Caesar Cipher Menu -------");
            System.out.println("1. Encrypt Text");
            System.out.println("2. Decrypt Text");
            System.out.println("3. Brute Force Ciphertext");
            System.out.println("4. Exit");
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
                        if (isValidPlaintext(plaintext)) {
                            break;
                        }
                        System.out.println(RED + "Error: Invalid format. Please use only lowercase letters (a-z)." + RESET);
                    }

                    int encKey;
                    // Loop until a valid key (1-25) is entered
                    while (true) {
                        System.out.print("Enter key (1-25): ");
                        if (sc.hasNextInt()) {
                            encKey = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            if (encKey >= 1 && encKey <= 25) {
                                break; // Valid key, exit loop
                            } else {
                                System.out.println(RED + "Error: Key must be between 1 and 25." + RESET);
                            }
                        } else {
                            System.out.println(RED + "Error: Invalid input. Please enter a number." + RESET);
                            sc.next(); // Discard the non-integer input
                        }
                    }

                    String encrypted = encrypt(plaintext, encKey);
                    System.out.println("✅ Encrypted Ciphertext: " + encrypted);
                    break;
                }
                case 2: {
                    String ciphertext;
                    while (true) {
                        System.out.print("Enter ciphertext (uppercase only): ");
                        ciphertext = sc.nextLine();
                        if (isValidCiphertext(ciphertext)) {
                            break;
                        }
                        System.out.println(RED + "Error: Invalid format. Please use only uppercase letters (A-Z)." + RESET);
                    }

                    int decKey;
                    // Loop until a valid key (1-25) is entered
                    while (true) {
                        System.out.print("Enter key (1-25): ");
                        if (sc.hasNextInt()) {
                            decKey = sc.nextInt();
                            sc.nextLine(); // Consume newline
                            if (decKey >= 1 && decKey <= 25) {
                                break; // Valid key, exit loop
                            } else {
                                System.out.println(RED + "Error: Key must be between 1 and 25." + RESET);
                            }
                        } else {
                            System.out.println(RED + "Error: Invalid input. Please enter a number." + RESET);
                            sc.next(); // Discard the non-integer input
                        }
                    }

                    String decrypted = decrypt(ciphertext, decKey);
                    System.out.println("✅ Decrypted Plaintext: " + decrypted);
                    break;
                }
                case 3: {
                    String bruteText;
                    while (true) {
                        System.out.print("Enter ciphertext to brute force (uppercase only): ");
                        bruteText = sc.nextLine();
                        if (isValidCiphertext(bruteText)) {
                            break;
                        }
                        System.out.println(RED + "Error: Invalid format. Please use only uppercase letters (A-Z)." + RESET);
                    }
                    bruteForce(bruteText);
                    break;
                }
                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println(RED + "Invalid choice. Please enter a number between 1 and 4." + RESET);
                    break;
            }
        }
    }
}