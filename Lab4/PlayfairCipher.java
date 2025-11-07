package Lab4;

import java.util.Scanner;
import java.awt.Point; // Useful for storing (row, col) coordinates

public class PlayfairCipher {

    static final String RED = "\u001b[31m";
    static final String BLUE = "\u001b[34m";
    static final String RESET = "\u001b[0m";

    private final char[][] keyTable;
    private final Point[] charPositions;

    public PlayfairCipher(String key) {
        this.keyTable = new char[5][5];
        this.charPositions = new Point[26];
        generateKeyTable(key);
    }

    private void generateKeyTable(String key) {
        String cleanKey = key.toUpperCase().replaceAll("[^A-Z]", "").replaceAll("J", "I");
        boolean[] visited = new boolean[26];
        int r = 0;
        int c = 0;

        for (char ch : cleanKey.toCharArray()) {
            int index = ch - 'A';
            if (!visited[index]) {
                visited[index] = true;
                keyTable[r][c] = ch;
                charPositions[index] = new Point(r, c);
                c++;
                if (c == 5) {
                    c = 0;
                    r++;
                }
            }
        }

        for (char ch = 'A'; ch <= 'Z'; ch++) {
            if (ch == 'J') continue;
            int index = ch - 'A';
            if (!visited[index]) {
                visited[index] = true;
                keyTable[r][c] = ch;
                charPositions[index] = new Point(r, c);
                c++;
                if (c == 5) {
                    c = 0;
                    r++;
                    if (r == 5) break;
                }
            }
        }
        charPositions['J' - 'A'] = charPositions['I' - 'A'];
    }

    public void printKeyTable() {
        System.out.println(BLUE + "--- Generated 5x5 Key Table ---" + RESET);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(keyTable[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println(BLUE + "---------------------------------" + RESET);
    }

    private String prepareText(String text) {
        String cleanText = text.toUpperCase().replaceAll("[^A-Z]", "").replaceAll("J", "I");
        StringBuilder sb = new StringBuilder(cleanText);

        for (int i = 0; i < sb.length() - 1; i += 2) {
            if (sb.charAt(i) == sb.charAt(i + 1)) {
                sb.insert(i + 1, 'X');
            }
        }

        if (sb.length() % 2 != 0) {
            sb.append('X');
        }

        return sb.toString();
    }

    /**
     * Encrypts the plaintext.
     */
    public String encrypt(String plaintext) {
        String preparedText = prepareText(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < preparedText.length(); i += 2) {
            char c1 = preparedText.charAt(i);
            char c2 = preparedText.charAt(i + 1);

            Point p1 = charPositions[c1 - 'A'];
            Point p2 = charPositions[c2 - 'A'];

            int r1 = p1.x; int col1 = p1.y;
            int r2 = p2.x; int col2 = p2.y;

            char e1, e2;

            if (r1 == r2) {
                // Rule 1: Same Row
                e1 = keyTable[r1][(col1 + 1) % 5];
                e2 = keyTable[r2][(col2 + 1) % 5];
            } else if (col1 == col2) {
                // Rule 2: Same Column
                e1 = keyTable[(r1 + 1) % 5][col1];
                e2 = keyTable[(r2 + 1) % 5][col2];
            } else {
                // Rule 3: Rectangle
                e1 = keyTable[r1][col2];
                e2 = keyTable[r2][col1];
            }

            ciphertext.append(e1).append(e2);
        }

        return ciphertext.toString();
    }

    /**
     * Decrypts the ciphertext.
     */
    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char c1 = ciphertext.charAt(i);
            char c2 = ciphertext.charAt(i + 1);

            Point p1 = charPositions[c1 - 'A'];
            Point p2 = charPositions[c2 - 'A'];

            int r1 = p1.x; int col1 = p1.y;
            int r2 = p2.x; int col2 = p2.y;

            char d1, d2;

            if (r1 == r2) {
                // Rule 1: Same Row (move left)
                d1 = keyTable[r1][(col1 - 1 + 5) % 5];
                d2 = keyTable[r2][(col2 - 1 + 5) % 5];
            } else if (col1 == col2) {
                // Rule 2: Same Column (move up)
                d1 = keyTable[(r1 - 1 + 5) % 5][col1];
                d2 = keyTable[(r2 - 1 + 5) % 5][col2];
            } else {
                // Rule 3: Rectangle
                d1 = keyTable[r1][col2];
                d2 = keyTable[r2][col1];
            }

            plaintext.append(d1).append(d2);
        }

        return plaintext.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PlayfairCipher cipher;

        while (true) {
            System.out.print("Enter the Playfair key (e.g., 'monarchy'): ");
            String key = sc.nextLine();
            if (key != null && !key.trim().isEmpty()) {
                cipher = new PlayfairCipher(key);
                System.out.println(BLUE + "✅ Key table generated." + RESET);
                cipher.printKeyTable();
                break;
            } else {
                System.out.println(RED + "Error: Key cannot be empty." + RESET);
            }
        }

        // --- Main Menu Loop ---
        while (true) {
            System.out.println("\n------- Playfair Cipher Menu -------");
            System.out.println("1. Encrypt Text");
            System.out.println("2. Decrypt Text");
            System.out.println("3. Change Key");
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
                    System.out.print("Enter plaintext: ");
                    String plaintext = sc.nextLine();
                    if (plaintext.isEmpty()) {
                        System.out.println(RED + "Error: Plaintext cannot be empty." + RESET);
                        continue;
                    }
                    String encrypted = cipher.encrypt(plaintext);
                    System.out.println(BLUE + "✅ Encrypted Ciphertext: " + encrypted + RESET);
                    break;
                }

                case 2: {
                    System.out.print("Enter ciphertext (uppercase only): ");
                    String ciphertext = sc.nextLine().toUpperCase();

                    if (ciphertext.isEmpty() || ciphertext.length() % 2 != 0 || !ciphertext.matches("[A-Z]+")) {
                        System.out.println(RED + "Error: Ciphertext must be all uppercase, non-empty, and have an even length." + RESET);
                        continue;
                    }

                    String decrypted = cipher.decrypt(ciphertext);
                    System.out.println(BLUE + "✅ Decrypted Plaintext: " + decrypted + RESET);
                    System.out.println(BLUE + "(Note: 'X' may be a filler. 'J' is represented as 'I'.)" + RESET);
                    break;
                }

                case 3: {
                    System.out.print("Enter the new Playfair key: ");
                    String newKey = sc.nextLine();
                    if (newKey != null && !newKey.trim().isEmpty()) {
                        cipher = new PlayfairCipher(newKey);
                        System.out.println(BLUE + "✅ New key table generated." + RESET);
                        cipher.printKeyTable();
                    } else {
                        System.out.println(RED + "Error: Key cannot be empty." + RESET);
                    }
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