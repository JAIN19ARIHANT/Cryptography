package Lab1;

import java.util.Scanner;

public class MultiplicativeCipher {

    static int modInverse(int key, int mod) {
        key = key % mod;
        for (int x = 1; x < mod; x++) {
            if ((key * x) % mod == 1)
                return x;
        }
        throw new IllegalArgumentException("Key has no multiplicative inverse modulo " + mod);
    }

    static String encrypt(String plaintext, int key) {
        if (gcd(key, 26) != 1) {
            throw new IllegalArgumentException("Key must be coprime with 26.");
        }

        plaintext = plaintext.toUpperCase();
        StringBuilder ciphertext = new StringBuilder();

        for (char ch : plaintext.toCharArray()) {
            if (Character.isLetter(ch)) {
                int p = ch - 'A';
                int c = (p * key) % 26;
                ciphertext.append((char) (c + 'A'));
            } else {
                ciphertext.append(ch); // keep spaces/punctuation
            }
        }

        return ciphertext.toString();
    }

    static String decrypt(String ciphertext, int key) {
        int inverseKey = modInverse(key, 26);
        ciphertext = ciphertext.toUpperCase();
        StringBuilder plaintext = new StringBuilder();

        for (char ch : ciphertext.toCharArray()) {
            if (Character.isLetter(ch)) {
                int c = ch - 'A';
                int p = (c * inverseKey) % 26;
                // Handle negative values
                if (p < 0) p += 26;
                plaintext.append((char) (p + 'A'));
            } else {
                plaintext.append(ch); // keep spaces/punctuation
            }
        }

        return plaintext.toString();
    }

    static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    // Test the cipher
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter plaintext: ");
        String plaintext = scanner.nextLine();

        System.out.print("Enter key (must be coprime with 26): ");
        int key = scanner.nextInt();
        scanner.nextLine(); // consume newline

        try {
            String encrypted = encrypt(plaintext, key);
            System.out.println("Encrypted: " + encrypted);

            String decrypted = decrypt(encrypted, key);
            System.out.println("Decrypted: " + decrypted);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}