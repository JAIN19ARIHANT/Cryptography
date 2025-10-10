package Lab1;

import java.util.Scanner;

// Affine Cipher: Encryption = (k1 * x + k2) % 26
public class AffineCipher {

    static String RED = "\u001b[31m";
    static String BLUE = "\u001b[34m";
    static String RESET = "\u001b[0m";

    public static boolean isValidPlaintext(String text) {
        return text.matches("[a-z]+");
    }

    public static boolean isValidCiphertext(String text) {
        return text.matches("[A-Z]+");
    }

    public static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    public static int modInverse(int key, int mod) {
        key = key % mod;
        for (int x = 1; x < mod; x++) {
            if ((key * x) % mod == 1)
                return x;
        }
        throw new IllegalArgumentException("Key has no modular inverse modulo " + mod);
    }

    public static String encrypt(String plaintext, int a, int b) {
        if (gcd(a, 26) != 1) {
            throw new IllegalArgumentException("Key 'a' must be coprime with 26.");
        }

        StringBuilder ciphertext = new StringBuilder();
        for (char ch : plaintext.toCharArray()) {
            int p = ch - 'a';
            int c = (a * p + b) % 26;
            ciphertext.append((char) (c + 'A'));
        }

        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, int a, int b) {
        int a_inv = modInverse(a, 26);
        StringBuilder plaintext = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            int c = ch - 'A';
            int p = (a_inv * (c - b + 26)) % 26;
            plaintext.append((char) (p + 'a'));
        }

        return plaintext.toString();
    }

    public static void bruteForce(String ciphertext) {
        if (!isValidCiphertext(ciphertext)) {
            System.out.println(RED + "Error: Ciphertext must contain only uppercase letters (A-Z)." + RESET);
            return;
        }

        int[] validA = {1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        System.out.println("\n🔍 Brute-force results:\n");

        for (int a : validA) {
            for (int b = 0; b < 26; b++) {
                try {
                    String attempt = decrypt(ciphertext, a, b);
                    System.out.printf("a=%2d, b=%2d: %s%n", a, b, attempt);
                } catch (IllegalArgumentException e) {
                    System.out.println("a=" + a + ", b=" + b + ": " + RED + "Invalid (no modular inverse)" + RESET);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------- Affine Cipher Menu -------");
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
                        if (isValidPlaintext(plaintext)) break;
                        System.out.println(RED + "Error: Use only lowercase letters (a-z)." + RESET);
                    }

                    int a, b;
                    while (true) {
                        System.out.print("Enter key 'a' (coprime with 26): ");
                        if (sc.hasNextInt()) {
                            a = sc.nextInt();
                            sc.nextLine();
                            if (gcd(a, 26) == 1) break;
                            else System.out.println(RED + "Error: 'a' must be coprime with 26." + RESET);
                        } else {
                            System.out.println(RED + "Error: Enter a valid number." + RESET);
                            sc.next();
                        }
                    }

                    System.out.print("Enter key 'b' (0-25): ");
                    b = sc.nextInt();
                    sc.nextLine();

                    try {
                        String encrypted = encrypt(plaintext, a, b);
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
                        if (isValidCiphertext(ciphertext)) break;
                        System.out.println(RED + "Error: Use only uppercase letters (A-Z)." + RESET);
                    }

                    int a, b;
                    while (true) {
                        System.out.print("Enter key 'a' (coprime with 26): ");
                        if (sc.hasNextInt()) {
                            a = sc.nextInt();
                            sc.nextLine();
                            if (gcd(a, 26) == 1) break;
                            else System.out.println(RED + "Error: 'a' must be coprime with 26." + RESET);
                        } else {
                            System.out.println(RED + "Error: Enter a valid number." + RESET);
                            sc.next();
                        }
                    }

                    System.out.print("Enter key 'b' (0-25): ");
                    b = sc.nextInt();
                    sc.nextLine();

                    try {
                        String decrypted = decrypt(ciphertext, a, b);
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
                        if (isValidCiphertext(bruteText)) break;
                        System.out.println(RED + "Error: Use only uppercase letters (A-Z)." + RESET);
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