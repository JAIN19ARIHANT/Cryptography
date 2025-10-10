package Lab2;

import java.util.Scanner;

// Multiplicative Cipher
public class MulCipher {

    static String RED = "\u001b[31m";
    static String BLUE = "\u001b[34m";
    static String RESET = "\u001b[0m";

    // Check if plaintext is valid: only lowercase letters
    public static boolean isValidPlaintext(String text) {
        return text.matches("[a-z]+");
    }

    // Check if ciphertext is valid: only uppercase letters
    public static boolean isValidCiphertext(String text) {
        return text.matches("[A-Z]+");
    }

    // Compute GCD using Euclidean Algorithm
    public static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    // Compute modular inverse
    public static int modInverse(int key, int mod) {
        key = key % mod;
        for (int x = 1; x < mod; x++) {
            if ((key * x) % mod == 1)
                return x;
        }
        throw new IllegalArgumentException("Key has no multiplicative inverse modulo " + mod);
    }

    // Encryption method (lowercase -> uppercase)
    public static String encrypt(String plaintext, int key) {
        if (gcd(key, 26) != 1) {
            throw new IllegalArgumentException("Key must be coprime with 26.");
        }

        StringBuilder ciphertext = new StringBuilder();
        for (char ch : plaintext.toCharArray()) {
            int p = ch - 'a';
            int c = (p * key) % 26;
            ciphertext.append((char) (c + 'A'));
        }

        return ciphertext.toString();
    }

    // Decryption method (uppercase -> lowercase)
    public static String decrypt(String ciphertext, int key) {
        int inverseKey = modInverse(key, 26);
        StringBuilder plaintext = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            int c = ch - 'A';
            int p = (c * inverseKey) % 26;
            if (p < 0) p += 26;
            plaintext.append((char) (p + 'a'));
        }

        return plaintext.toString();
    }

    // Brute-force decryption using all valid keys
    public static void bruteForce(String ciphertext) {
        if (!isValidCiphertext(ciphertext)) {
            System.out.println(RED + "Error: Ciphertext must contain only uppercase letters (A-Z)." + RESET);
            return;
        }

        int[] validKeys = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        System.out.println("\n🔍 Brute-force results:\n");

        for (int key : validKeys) {
            try {
                String attempt = decrypt(ciphertext, key);
                System.out.printf("Key %2d: %s%n", key, attempt);
            } catch (IllegalArgumentException e) {
                System.out.println("Key " + key + ": " + RED + "Invalid (no modular inverse)" + RESET);
            }
        }
    }

    // Main program with validation
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------- Multiplicative Cipher Menu -------");
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
                        System.out.println(RED + "Error: Invalid format. Use only lowercase letters (a-z)." + RESET);
                    }

                    int encKey;
                    while (true) {
                        System.out.print("Enter key (must be coprime with 26): ");
                        if (sc.hasNextInt()) {
                            encKey = sc.nextInt();
                            sc.nextLine();
                            if (gcd(encKey, 26) == 1) {
                                break;
                            } else {
                                System.out.println(RED + "Error: Key must be coprime with 26." + RESET);
                            }
                        } else {
                            System.out.println(RED + "Error: Invalid input. Please enter a number." + RESET);
                            sc.next();
                        }
                    }

                    try {
                        String encrypted = encrypt(plaintext, encKey);
                        System.out.println(BLUE + "✅ Encrypted Ciphertext: " + encrypted + RESET);
                    } catch (IllegalArgumentException e) {
                        System.out.println(RED + "Error: " + e.getMessage() + RESET);
                    }
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
                        System.out.println(RED + "Error: Invalid format. Use only uppercase letters (A-Z)." + RESET);
                    }

                    int decKey;
                    while (true) {
                        System.out.print("Enter key (must be coprime with 26): ");
                        if (sc.hasNextInt()) {
                            decKey = sc.nextInt();
                            sc.nextLine();
                            if (gcd(decKey, 26) == 1) {
                                break;
                            } else {
                                System.out.println(RED + "Error: Key must be coprime with 26." + RESET);
                            }
                        } else {
                            System.out.println(RED + "Error: Invalid input. Please enter a number." + RESET);
                            sc.next();
                        }
                    }

                    try {
                        String decrypted = decrypt(ciphertext, decKey);
                        System.out.println(BLUE + "✅ Decrypted Plaintext: " + decrypted + RESET);
                    } catch (IllegalArgumentException e) {
                        System.out.println(RED + "Error: " + e.getMessage() + RESET);
                    }
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
                        System.out.println(RED + "Error: Invalid format. Use only uppercase letters (A-Z)." + RESET);
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