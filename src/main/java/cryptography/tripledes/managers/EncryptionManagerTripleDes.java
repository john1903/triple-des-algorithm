package cryptography.tripledes.managers;

import cryptography.tripledes.logic.Encryption;

public class EncryptionManagerTripleDes implements EncryptionManagerInterface {
    @Override
    public byte[] encryptData(byte[] data, byte[] key1, byte[] key2, byte[] key3) throws Exception {
        byte[] encryptedData = new byte[data.length];
        int index = 0;
        while (index < data.length) {
            byte[] block = new byte[8];
            int length = Math.min(8, data.length - index); // Obliczenie długości bloku
            System.arraycopy(data, index, block, 0, length);
            Encryption.encryption(block, key1, 0);
            Encryption.encryption(block, key2, 0);
            Encryption.encryption(block, key3, 0);
            System.arraycopy(block, 0, encryptedData, index, length);
            index += 8;
        }
        return encryptedData;
    }

    @Override
    public byte[] decryptData(byte[] encryptedData, byte[] key1, byte[] key2, byte[] key3) throws Exception {
        byte[] decryptedData = new byte[encryptedData.length];
        int index = 0;
        while (index < encryptedData.length) {
            byte[] block = new byte[8];
            int length = Math.min(8, encryptedData.length - index); // Obliczenie długości bloku
            System.arraycopy(encryptedData, index, block, 0, length);
            Encryption.encryption(block, key3, 1);
            Encryption.encryption(block, key2, 1);
            Encryption.encryption(block, key1, 1);
            System.arraycopy(block, 0, decryptedData, index, length);
            index += 8;
        }
        return decryptedData;
    }
}