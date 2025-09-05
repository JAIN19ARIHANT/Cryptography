# Cryptography Implementations

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

This repository contains implementations of various cryptographic algorithms and ciphers. It serves as a collection of lab work and personal study in the field of cryptography.

## 🚀 About The Project

The goal of this repository is to explore the fundamentals of cryptography by building classic and modern ciphers from scratch. Each lab focuses on a specific algorithm, providing a clear Java implementation and explaining the underlying principles.

This project starts with one of the most foundational ciphers in history and will be expanded over time.

## 📂 Labs Overview

This repository is structured into different labs, each covering a unique topic.

| Lab Number | Topic                                   | Status      |
| :--------- | :-------------------------------------- | :---------- |
| **Lab 1** | **Shift Cipher (Caesar Cipher)** | ✅ Complete |
| **Lab 2** |  | ⏳ Pending  |
| **Lab 3** |  | ⏳ Pending  |

---

## Lab 1: The Shift Cipher (Caesar Cipher)

The first lab implements the **Shift Cipher**, also famously known as the Caesar Cipher. It is a simple substitution cipher where each letter in the plaintext is "shifted" a certain number of places down or up the alphabet.

### 🧠 How It Works

The core idea is to replace each character with another character a fixed number of positions away in the alphabet. For a given plaintext character $P$, a secret key (shift) $k$, the encrypted character $C$ is calculated as:

$$ E_k(P) = (P + k) \mod 26 $$

Decryption is the reverse process:

$$ D_k(C) = (C - k) \mod 26 $$

This implementation handles both uppercase and lowercase letters, preserving case, while non-alphabetic characters are ignored.

### 🛠️ Getting Started

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

1.  Navigate to the `Lab1` directory:
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

The program will then prompt you to enter a plaintext message and a shift key to perform the encryption and subsequent decryption.

#### Example Execution

```
Enter the plaintext to encrypt: hello world
Enter the shift key (an integer): 3

--- Results ---
Plaintext:  hello world
Shift Key:  3
Ciphertext: KHOOR ZRUOG
Decrypted:  hello world
```

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request
6.  

## 👤 Contact

**Arihant Jain** - [@JAIN19ARIHANT](https://github.com/JAIN19ARIHANT)

Project Link: [https://github.com/JAIN19ARIHANT/Cryptography](https://github.com/JAIN19ARIHANT/Cryptography)
