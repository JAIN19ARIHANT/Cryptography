package Lab5;

import java.util.*;
import java.math.BigInteger;

public class RSA {
    private static BigInteger p, q, n, phi, e, d;
    private static Scanner sc = new Scanner(System.in);

    // ANSI escape codes for coloring terminal output
    static final String RED = "\u001b[31m";
    static final String BLUE = "\u001b[34m";
    static final String GREEN = "\u001b[32m";
    static final String RESET = "\u001b[0m";

    private static boolean isPrime(BigInteger num) {
        return num.isProbablePrime(20);
    }

    private static BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO))
            return a;
        return gcd(b, a.mod(b));
    }

    private static void generateKeys() {
        while (true) {
            try {
                System.out.print("\nEnter prime number p: ");
                p = new BigInteger(sc.next());
                System.out.print("Enter prime number q (different from p): ");
                q = new BigInteger(sc.next());

                if (!isPrime(p) || !isPrime(q)) {
                    System.out.println(RED + "Error: Both p and q must be prime numbers. Try again!" + RESET);
                    continue;
                }
                if (p.equals(q)) {
                    System.out.println(RED + "Error: p and q must be different. Try again!" + RESET);
                    continue;
                }

                n = p.multiply(q);
                phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

                // Find all valid public keys e (1 < e < phi and gcd(e, phi) == 1)
                List<BigInteger> validEs = new ArrayList<>();
                // Limit search for e to 1000 for performance with small primes
                for (BigInteger i = BigInteger.TWO; i.compareTo(BigInteger.valueOf(1000)) < 0 && i.compareTo(phi) < 0; i = i.add(BigInteger.ONE)) {
                    if (gcd(i, phi).equals(BigInteger.ONE)) {
                        validEs.add(i);
                    }
                }

                if (validEs.isEmpty()) {
                    System.out.println(RED + "No valid public keys found. Try different primes." + RESET);
                    continue;
                }

                // Display options for e
                System.out.println("\nSelect a public key (e) from the list below:");
                int displayCount = Math.min(10, validEs.size());
                for (int i = 0; i < displayCount; i++) {
                    System.out.println((i + 1) + ". " + BLUE + validEs.get(i) + RESET);
                }

                int choice;
                while (true) {
                    System.out.print("Enter your choice (1-" + displayCount + "): ");
                    try {
                        choice = sc.nextInt();
                        if (choice >= 1 && choice <= displayCount)
                            break;
                        else
                            System.out.println(RED + "Invalid choice! Please choose between 1 and " + displayCount + RESET);
                    } catch (Exception ex) {
                        System.out.println(RED + "Invalid input! Enter a number between 1 and " + displayCount + RESET);
                        sc.nextLine(); // Clear scanner buffer
                    }
                }

                e = validEs.get(choice - 1);
                d = e.modInverse(phi);

                System.out.println(GREEN + "\nKeys generated successfully!" + RESET);
                System.out.println("Public Key (e, n): " + BLUE + "(" + e + ", " + n + ")" + RESET);
                System.out.println("Private Key (d, n): " + BLUE + "(" + d + ", " + n + ")" + RESET);
                break; // Exit key generation loop

            } catch (Exception ex) {
                System.out.println(RED + "Enter valid integers." + RESET);
                sc.nextLine(); // Clear scanner buffer
            }
        }
    }

    private static void encrypt() {
        if (e == null || n == null) {
            System.out.println(RED + "Please generate RSA keys first!" + RESET);
            return;
        }

        try {
            System.out.print("Enter a number to encrypt: ");
            BigInteger message = sc.nextBigInteger();

            if (message.compareTo(n) >= 0 || message.compareTo(BigInteger.ZERO) < 0) {
                System.out.println(RED + "Message must be a positive number smaller than n (" + n + ")" + RESET);
                return;
            }

            BigInteger cipher = message.modPow(e, n);
            System.out.println("Encrypted number: " + BLUE + cipher + RESET);
        } catch (Exception ex) {
            System.out.println(RED + "Invalid input! Please enter a valid number." + RESET);
            sc.nextLine(); // Clear scanner buffer
        }
    }

    private static void decrypt() {
        if (d == null || n == null) {
            System.out.println(RED + "Please generate RSA keys first!" + RESET);
            return;
        }

        try {
            System.out.print("Enter encrypted number: ");
            BigInteger cipher = sc.nextBigInteger();

            if (cipher.compareTo(BigInteger.ZERO) < 0 || cipher.compareTo(n) >= 0) {
                System.out.println(RED + "Cipher must be between 0 and n (" + n + ")" + RESET);
                return;
            }

            BigInteger decrypted = cipher.modPow(d, n);
            System.out.println("Decrypted number: " + BLUE + decrypted + RESET);
        } catch (Exception ex) {
            System.out.println(RED + "Invalid input! Please enter a valid encrypted number." + RESET);
            sc.nextLine(); // Clear scanner buffer
        }
    }

    public static void main(String[] args) {
        System.out.println(GREEN + "==== RSA Number Encryption ====" + RESET);
        generateKeys();

        int choice = 0;
        while (true) {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Encrypt a Number");
            System.out.println("2. Decrypt a Number");
            System.out.println("3. End");
            System.out.print("Enter your choice: ");

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println(RED + "Invalid choice! Please enter 1–3." + RESET);
                sc.nextLine(); // Clear scanner buffer
                continue;
            }

            switch (choice) {
                case 1 -> encrypt();
                case 2 -> decrypt();
                case 3 -> {
                    System.out.println(GREEN + "Exiting... Thank you!" + RESET);
                    System.exit(0);
                }
                default -> System.out.println(RED + "Invalid choice! Please select 1–3." + RESET);
            }
        }
    }
}