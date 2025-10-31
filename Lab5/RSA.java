package Lab5;

import java.util.*;

public class RSA {

    // ANSI escape codes for coloring terminal output
    static final String RED = "\u001b[31m";
    static final String BLUE = "\u001b[34m";
    static final String GREEN = "\u001b[32m";
    static final String RESET = "\u001b[0m";

    static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    static int modInverse(int e, int phi) {
        for (int d = 2; d < phi; d++) {
            if ((d * e) % phi == 1)
                return d;
        }
        return -1;
    }

    static int modPow(int base, int exp, int mod) {
        int result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * base) % mod;
            exp = exp >> 1;
            base = (base * base) % mod;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int p, q;
        while (true) {
            System.out.print("Enter first prime number (p): ");
            p = sc.nextInt();
            System.out.print("Enter second prime number (q): ");
            q = sc.nextInt();

            if (!isPrime(p) || !isPrime(q)) {
                System.out.println(RED + "❌ Both numbers must be prime. Try again.\n" + RESET);
                continue;
            }
            if (p == q) {
                System.out.println(RED + "❌ p and q must be different primes. Try again.\n" + RESET);
                continue;
            }
            break;
        }

        int n = p * q;
        int phi = (p - 1) * (q - 1);

        System.out.println(GREEN + "\nCalculated Values:" + RESET);
        System.out.println("n = " + BLUE + n + RESET);
        System.out.println("φ(n) = " + BLUE + phi + RESET);

        List<Integer> validEs = new ArrayList<>();
        for (int i = 2; i < phi; i++) {
            if (gcd(i, phi) == 1) {
                validEs.add(i);
            }
        }

        System.out.println("\nPossible Public Keys (e values):");
        System.out.print(BLUE); // Set color for the list
        for (int i = 0; i < validEs.size(); i++) {
            System.out.print(validEs.get(i) + (i < validEs.size() - 1 ? ", " : ""));
        }
        System.out.println(RESET); // Reset color after the list

        int e;
        while (true) {
            System.out.print("\nSelect your public key 'e' from the list above: ");
            e = sc.nextInt();
            if (validEs.contains(e)) break;
            System.out.println(RED + " Invalid choice. Please choose a value of 'e' from the list." + RESET);
        }

        System.out.println(GREEN + " Selected Public Key (e, n) = " + RESET + BLUE + "(" + e + ", " + n + ")" + RESET);

        int d = modInverse(e, phi);
        if (d == -1) {
            System.out.println(RED + " Could not find private key d." + RESET);
            return;
        }
        System.out.println(GREEN + " Private Key (d, n) = " + RESET + BLUE + "(" + d + ", " + n + ")" + RESET);

        while (true) {
            System.out.println("\n===== RSA Menu =====");
            System.out.println("1. Encrypt a message");
            System.out.println("2. Decrypt a message");
            System.out.println("3. Exit");
            System.out.print("Choose an option (1-3): ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: {
                    System.out.print("Enter message (numeric form): ");
                    int message = sc.nextInt();
                    if (message <= 0 || message >= n) {
                        System.out.println(RED + " Message must be >0 and < n (" + n + ")." + RESET);
                        break;
                    }
                    int cipher = modPow(message, e, n);
                    System.out.println(GREEN + " Encrypted Message: " + RESET + BLUE + cipher + RESET);
                    break;
                }

                case 2: {
                    System.out.print("Enter encrypted message (numeric form): ");
                    int cipher = sc.nextInt();
                    if (cipher <= 0 || cipher >= n) {
                        System.out.println(RED + " Cipher must be >0 and < n (" + n + ")." + RESET);
                        break;
                    }
                    int decrypted = modPow(cipher, d, n);
                    System.out.println(GREEN + " Decrypted Message: " + RESET + BLUE + decrypted + RESET);
                    break;
                }

                case 3:
                    System.out.println(GREEN + "Exiting program. Goodbye!" + RESET);
                    sc.close();
                    return;

                default:
                    System.out.println(RED + "Invalid option. Please choose 1, 2, or 3." + RESET);
            }
        }
    }
}