# Cryptography Implementations

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

This repository contains implementations of various cryptographic algorithms and ciphers. It serves as a collection of lab work and personal study in the field of cryptography.

## 🚀 About The Project

The goal of this repository is to explore the fundamentals of cryptography by building classic and modern ciphers from scratch. Each lab focuses on a specific algorithm, providing a clear Java implementation and explaining the underlying principles.

This project starts with some of the most foundational ciphers in history and will be expanded over time.

## 📂 Labs Overview

This repository is structured into different labs, each covering a unique topic.

| Lab Number | Topic                   | Status      |
| :--------- | :---------------------- | :---------- |
| **Lab 1** | **Shift Cipher (Caesar Cipher)** | ✅ Complete |
| **Lab 2** | **Multiplicative Cipher**| ✅ Complete |
| **Lab 3** | **Affine Cipher** | ✅ Complete |
| **Lab 4** | **Playfair Cipher** | ⏳ Pending  |

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

The program will then prompt you to enter a plaintext message and the required key(s) to perform the encryption and subsequent decryption.
