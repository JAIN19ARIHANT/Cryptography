package Lab7;

import java.util.Scanner;

// Hill Cipher (N x N Implementation)
public class HillCipher {

    static String RED = "\u001b[31m";
    static String BLUE = "\u001b[34m";
    static String RESET = "\u001b[0m";

    public static boolean isValidText(String text) {
        return text != null && !text.isEmpty() && text.matches("[a-z]+");
    }

    public static boolean isValidCiphertext(String text) {
        return text != null && !text.isEmpty() && text.matches("[A-Z]+");
    }

    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1)
                return x;
        }
        throw new IllegalArgumentException("Key is not invertible (determinant is not coprime with 26)");
    }

    public static int[][] createKeyMatrix(String key, int n) {
        int[][] matrix = new int[n][n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = key.charAt(k) - 'a';
                k++;
            }
        }
        return matrix;
    }

    public static int determinant(int[][] matrix) {
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }

        int det = 0;
        for (int j = 0; j < n; j++) {
            int subDet = determinant(getSubmatrix(matrix, 0, j));
            int sign = (j % 2 == 0) ? 1 : -1;
            det += sign * matrix[0][j] * subDet;
        }
        return Math.floorMod(det, 26);
    }

    public static int[][] getSubmatrix(int[][] matrix, int rowToExclude, int colToExclude) {
        int n = matrix.length;
        int[][] submatrix = new int[n - 1][n - 1];
        int r = 0;
        for (int i = 0; i < n; i++) {
            if (i == rowToExclude) continue;
            int c = 0;
            for (int j = 0; j < n; j++) {
                if (j == colToExclude) continue;
                submatrix[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return submatrix;
    }

    public static int[][] cofactorMatrix(int[][] matrix) {
        int n = matrix.length;
        int[][] cofactors = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int det = determinant(getSubmatrix(matrix, i, j));
                int sign = ((i + j) % 2 == 0) ? 1 : -1;
                cofactors[i][j] = Math.floorMod(sign * det, 26);
            }
        }
        return cofactors;
    }

    public static int[][] transpose(int[][] matrix) {
        int n = matrix.length;
        int[][] transposed = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }
        return transposed;
    }

    public static int[][] getInverseMatrix(int[][] keyMatrix) {
        int n = keyMatrix.length;
        int det = determinant(keyMatrix);
        int detInv = modInverse(det, 26);

        int[][] adjoint = transpose(cofactorMatrix(keyMatrix));

        int[][] inverse = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse[i][j] = Math.floorMod(detInv * adjoint[i][j], 26);
            }
        }
        return inverse;
    }

    private static String processText(String text, int[][] matrix) {
        StringBuilder result = new StringBuilder();
        int n = matrix.length;
        final int charBase = 'a';

        for (int i = 0; i < text.length(); i += n) {
            int[] vector = new int[n];
            for (int j = 0; j < n; j++) {
                vector[j] = text.charAt(i + j) - charBase;
            }

            int[] resultVector = new int[n];
            for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                    sum += vector[k] * matrix[k][j];
                }
                resultVector[j] = Math.floorMod(sum, 26);
            }

            for (int val : resultVector) {
                result.append((char) (val + charBase));
            }
        }
        return result.toString();
    }

    public static String encrypt(String plaintext, String key) {
        int n = (int) Math.sqrt(key.length());
        int[][] keyMatrix = createKeyMatrix(key, n);
        getInverseMatrix(keyMatrix); // Check for invertibility

        StringBuilder sb = new StringBuilder(plaintext);
        while (sb.length() % n != 0) {
            sb.append('x');
        }

        return processText(sb.toString(), keyMatrix).toUpperCase();
    }

    public static String decrypt(String ciphertext, String key) {
        int n = (int) Math.sqrt(key.length());
        int[][] keyMatrix = createKeyMatrix(key, n);
        int[][] inverseMatrix = getInverseMatrix(keyMatrix);

        return processText(ciphertext.toLowerCase(), inverseMatrix);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------- Hill Cipher (N x N) Menu -------");
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

            String key;
            int n;
            int[][] keyMatrix;

            if (choice == 1 || choice == 2) {
                while (true) {
                    System.out.print("Enter key (length must be a perfect square, e.g., 4, 9, 16): ");
                    key = sc.nextLine();

                    if (!isValidText(key)) {
                        System.out.println(RED + "Error: Key must be 4 lowercase letters." + RESET);
                        continue;
                    }

                    double sqrt = Math.sqrt(key.length());
                    if (sqrt != Math.floor(sqrt) || key.length() < 4) {
                        System.out.println(RED + "Error: Key length must be a perfect square (>= 4)." + RESET);
                        continue;
                    }
                    n = (int) sqrt;

                    try {
                        keyMatrix = createKeyMatrix(key, n);
                        getInverseMatrix(keyMatrix);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println(RED + "Error: Key is not invertible. (Determinant is 0, 2, 13, or even). Try another." + RESET);
                    }
                }
            } else if (choice == 3) {
                System.out.println("Exiting program. Goodbye!");
                sc.close();
                return;
            } else {
                System.out.println(RED + "Invalid choice. Please enter a number between 1 and 3." + RESET);
                continue;
            }

            switch (choice) {
                case 1: {
                    String plaintext;
                    while (true) {
                        System.out.print("Enter plaintext (lowercase only): ");
                        plaintext = sc.nextLine();
                        if (isValidText(plaintext)) break;
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

                        if (ciphertext.length() % n != 0) {
                            System.out.println(RED + "Error: Ciphertext length must be a multiple of " + n + RESET);
                        } else if (isValidCiphertext(ciphertext)) {
                            break;
                        } else {
                            System.out.println(RED + "Error: Use only uppercase letters (A-Z)." + RESET);
                        }
                    }

                    try {
                        String decrypted = decrypt(ciphertext, key);
                        System.out.println(BLUE + "✅ Decrypted Plaintext: " + decrypted + RESET);
                        System.out.println(BLUE + "(Note: may contain filler 'x' at the end)" + RESET);
                    } catch (Exception e) {
                        System.out.println(RED + "An error occurred during decryption: " + e.getMessage() + RESET);
                    }
                    break;
                }
            }
        }
    }
}