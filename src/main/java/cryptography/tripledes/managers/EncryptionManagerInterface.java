package cryptography.tripledes.managers;

public interface EncryptionManagerInterface {
    public byte[] encryptData(byte[] data, byte[] key1, byte[] key2, byte[] key3) throws Exception;

    public byte[] decryptData(byte[] encryptedData, byte[] key1, byte[] key2, byte[] key3) throws Exception;
}
