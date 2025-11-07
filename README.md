# Cryptography Implementations

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

This repository contains implementations of various cryptographic algorithms and ciphers. It serves as a collection of lab work and personal study in the field of cryptography.

## 🚀 About The Project

The goal of this repository is to explore the fundamentals of cryptography by building classic and modern ciphers from scratch. Each lab focuses on a specific algorithm, providing a clear Java implementation and explaining the underlying principles.

This project starts with some of the most foundational ciphers in history and will be expanded over time.

## 📂 Labs Overview

This repository is structured into different labs, each covering a unique topic.

| Lab Number | Topic | Status |
| :--- | :--- | :--- |
| **Lab 1** | **Shift Cipher (Caesar Cipher)** | ✅ Complete |
| **Lab 2** | **Multiplicative Cipher** | ✅ Complete |
| **Lab 3** | **Affine Cipher** | ✅ Complete |
| **Lab 4** | **Playfair Cipher** | ✅ Complete |
| **Lab 5** | **RSA** | ✅ Complete |
| **Lab 6** | **Autokey Cipher** | ✅ Complete |
| **Lab 7** | **Hill Cipher** | ✅ Complete |
| **Lab 8** | *(Pending)* | ⏳ Pending |
| **Lab 9** | *(Pending)* | ⏳ Pending |
| **Lab 10** | *(Pending)* | ⏳ Pending |

---

## Lab 1: The Shift Cipher (Caesar Cipher)

The first lab implements the **Shift Cipher**, also famously known as the Caesar Cipher. It is a simple substitution cipher where each letter in the plaintext is "shifted" a certain number of places down or up the alphabet.

### 🧠 How It Works

The core idea is to replace each character with another character a fixed number of positions away in the alphabet. For a given plaintext character $P$, a secret key (shift) $k$, the encrypted character $C$ is calculated as:

$$E_k(P) = (P + k) \mod 26$$

Decryption is the reverse process:

$$D_k(C) = (C - k) \mod 26$$

This implementation handles both uppercase and lowercase letters, preserving case, while non-alphabetic characters are ignored.

---

## Lab 2: The Multiplicative Cipher

This lab covers the **Multiplicative Cipher**, another monoalphabetic substitution cipher. Instead of adding a key, it multiplies the character's numerical value by the key.

### 🧠 How It Works

Each character is mapped to an integer (A=0, B=1, ..., Z=25). This value is then multiplied by a key $k$ modulo 26. For the cipher to be decryptable, the key $k$ must be coprime with 26, meaning their greatest common divisor must be 1 (i.e., $\text{gcd}(k, 26) = 1$).

Encryption is defined as:

$$E_k(P) = (P \times k) \mod 26$$

Decryption requires finding the modular multiplicative inverse of the key, denoted as $k^{-1}$, such that $(k \times k^{-1}) \mod 26 = 1$. The decryption formula is:

$$D_k(C) = (C \times k^{-1}) \mod 26$$

---

## Lab 3: The Affine Cipher

The **Affine Cipher** combines the principles of the Shift and Multiplicative ciphers. It uses a pair of keys, one for multiplication ($k_1$) and one for addition/shifting ($k_2$).

### 🧠 How It Works

The Affine Cipher provides a more secure encryption than its individual components. The first key, $k_1$, has the same constraint as the multiplicative cipher key: $\text{gcd}(k_1, 26) = 1$. The second key, $k_2$, is the shift value.

The encryption function is:

$$E_{k_1, k_2}(P) = (P \times k_1 + k_2) \mod 26$$

For decryption, we first subtract the shift key $k_2$ and then multiply by the modular inverse of the multiplicative key, $k_1^{-1}$:

$$D_{k_1, k_2}(C) = ((C - k_2) \times k_1^{-1}) \mod 26$$

---

## Lab 4: The Playfair Cipher

The **Playfair Cipher** is the first practical digraph substitution cipher. It encrypts pairs of letters (digraphs) instead of single letters, which makes it significantly harder to break with simple frequency analysis.

### 🧠 How It Works

The cipher uses a 5x5 grid of letters constructed from a keyword (omitting 'J' or 'I'). Encryption and decryption are based on three rules applied to the positions of the two letters in the grid:

1.  **Same Row:** Replace each letter with the letter to its immediate right (wrapping around to the left).
2.  **Same Column:** Replace each letter with the letter immediately below it (wrapping around to the top).
3.  **Rectangle:** Replace each letter with the letter that lies in its own row but in the other letter's column.

Plaintext is first "prepared" by replacing 'J' with 'I', inserting an 'X' between identical letters in a pair, and padding with 'X' if the total length is odd.

---

## Lab 5: The RSA Algorithm

The **RSA (Rivest–Shamir–Adleman)** algorithm is one of the first public-key cryptosystems and is widely used for secure data transmission. It is an asymmetric algorithm, meaning it uses one key for encryption (the public key) and a different, private key for decryption.

### 🧠 How It Works

The security of RSA relies on the difficulty of factoring the product of two large prime numbers.

1.  **Key Generation:**
    * Generate two large primes, $p$ and $q$.
    * Compute $n = p \times q$ (the modulus).
    * Compute $\phi(n) = (p-1)(q-1)$ (Euler's totient function).
    * Choose an integer $e$ (public key exponent) such that $1 < e < \phi(n)$ and $\text{gcd}(e, \phi(n)) = 1$.
    * Compute $d$ (private key exponent) as the modular inverse of $e$ modulo $\phi(n)$. $d = e^{-1} \mod \phi(n)$.
    * **Public Key:** $(e, n)$
    * **Private Key:** $(d, n)$

2.  **Encryption:**
    A plaintext message $P$ is encrypted to ciphertext $C$ using the public key:
    $$C = P^e \mod n$$

3.  **Decryption:**
    The ciphertext $C$ is decrypted back to $P$ using the private key:
    $$P = C^d \mod n$$

---

## Lab 6: The Autokey Cipher

The **Autokey Cipher** is a polyalphabetic cipher that improves upon the Vigenere cipher by using the plaintext itself as part of the key. This prevents the short, repeating key cycles that make the Vigenere cipher vulnerable.

### 🧠 How It Works

The keystream is generated by concatenating a short secret keyword with the plaintext itself.

* **Keystream:** `Key` + `Plaintext`
* **Example:** If `Key` = `kilt` and `Plaintext` = `attackatdawn`, the `Keystream` is `kiltattackatda...`.

Encryption is a simple modulo addition:

$$C_i = (P_i + K_i) \mod 26$$

Decryption is the reverse, but the keystream is dynamically built. The first part of the key is the secret keyword, and every subsequent key character is the *previously decrypted* plaintext character.

$$P_i = (C_i - K_i) \mod 26$$

---

## Lab 7: The Hill Cipher

The **Hill Cipher** is a polygraphic substitution cipher that uses linear algebra. It encrypts blocks of $n$ letters at a time using an $n \times n$ matrix as the key.

### 🧠 How It Works

The cipher is based on matrix multiplication. A block of $n$ plaintext letters is treated as a vector $P$, and the $n \times n$ key matrix is $K$.

Encryption is defined as:

$$C = (P \times K) \mod 26$$

For decryption, we must use the modular inverse of the key matrix, $K^{-1}$. $K^{-1}$ is the matrix such that $(K \times K^{-1}) \mod 26$ is the identity matrix. It is calculated using the determinant and the adjoint matrix.

$$P = (C \times K^{-1}) \mod 26$$

This implementation is generalized for $n \times n$ matrices (e.g., $2 \times 2$, $3 \times 3$), provided the key length is a perfect square ($n^2$) and the key matrix is invertible modulo 26 (its determinant is coprime with 26).

---

## 🛠️ Getting Started

To get a local copy up and running, follow these simple steps.

#### Prerequisites

Make sure you have the Java Development Kit (JDK) installed on your machine.
* [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/)

#### Installation

1.  Clone the repository to your local machine:
    ```sh
    git clone [https://github.com/JAIN19ARIHANT/Cryptography.git](https://github.com/JAIN19ARIHANT/Cryptography.git)
    ```
2.  Navigate to the project directory:
    ```sh
    cd Cryptography
    ```

### 💻 Usage

Usage for each lab is similar.

1.  Navigate to the specific lab directory (e.g., `Lab1`):
    ```sh
    cd Lab1
    ```
2.  Compile the Java source file:
    ```sh
    javac ShiftCipher.java
    ```
3.  Run the compiled program:
    ```sh
    java ShiftCipher
    ```

The program will then prompt you to enter a plaintext message and the required key(s) to perform the encryption and subsequent decryption.

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 👤 Contact

**Arihant Jain** - [@JAIN19ARIHANT](https://github.com/JAIN19ARIHANT)

Project Link: [https://github.com/JAIN19ARIHANT/Cryptography](https://github.com/JAIN19ARIHANT/Cryptography)
