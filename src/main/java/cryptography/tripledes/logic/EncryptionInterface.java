package cryptography.tripledes.logic;

public interface EncryptionInterface {
     byte[] encryption(byte[] input, byte[] key);

     byte[] decryption(byte[] input, byte[] key);
}
