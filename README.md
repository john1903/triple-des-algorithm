# TripleDES

Author: John Gluzniewicz

## Description

This is a Java repository that contains an implementation of the Triple Data Encryption Standard (DES) algorithm, along with unit tests and a sample JavaFX project demonstrating its usage.

Triple DES, also known as TDES or TDEA, is a symmetric-key block cipher that applies the DES algorithm three times to each data block for enhanced security.

## Features

- Triple DES implementation in Java for encryption and decryption.
- Unit tests ensuring the correctness and robustness of the algorithm.
- Sample JavaFX project showcasing the integration of Triple DES encryption and decryption into a graphical user interface for file manipulation.

## How to Use

### Setting Up the Project

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/john1903/triple-des-algorithm.git
    cd triple-des-algorithm
    ```

2. **Build the Project**:
    Use your preferred Java IDE (like IntelliJ IDEA or Eclipse) to open the project and build it.

3. **Run the JavaFX Application**:
    Run the `GUI` class located in the `me.jangluzniewicz.tripledes.gui` package to start the JavaFX application.

### Using the Application

1. **Create a Blank Text File**:
    Start by creating an empty text file in your desired location.

2. **Generate Keys**:
    - Click the "Generate Keys" button to create encryption and decryption keys.

3. **Encrypt File**:
    - Select the file you want to encrypt by clicking on the "Enter file path" text field or dragging and dropping the file into the field.
    - Click the "Encrypt File" button to encrypt the selected file.
    - Choose the location to save the encrypted file when prompted. The encrypted file will typically have a suffix like "encrypted".

4. **Decrypt File**:
    - Select the encrypted file either by entering its path in the "Enter file path" text field or by dragging and dropping it.
    - Click the "Decrypt File" button to decrypt the selected file.
    - Choose the location to save the decrypted file when prompted. The decrypted file will typically have a suffix like "decrypted".

### Detailed Steps for Encryption and Decryption

- **Encryption**:
  - The file is read and converted to a BitSet.
  - The Triple DES algorithm is applied using three keys.
  - The encrypted data is saved to a new file.

- **Decryption**:
  - The encrypted file is read and converted to a BitSet.
  - The Triple DES algorithm is applied using the same three keys, but in reverse order.
  - The decrypted data is saved to a new file.

Follow these steps to securely encrypt and decrypt your files using the Triple DES algorithm implemented in this Java program.

## License

This project is licensed under the Apache License - see the [LICENSE](LICENSE.txt) file for details.
