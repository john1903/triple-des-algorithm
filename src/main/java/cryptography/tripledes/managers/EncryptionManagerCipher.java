package cryptography.tripledes.managers;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.spec.KeySpec;

public class EncryptionManagerCipher implements EncryptionManagerInterface {
    public byte[] encryptData(byte[] data, byte[] key1, byte[] key2, byte[] key3) throws Exception {
        Cipher cipher1 = Cipher.getInstance("DES");
        SecretKeyFactory skf1 = SecretKeyFactory.getInstance("DES");
        KeySpec ks1 = new DESKeySpec(key1);
        SecretKey key1Spec = skf1.generateSecret(ks1);
        cipher1.init(Cipher.ENCRYPT_MODE, key1Spec);
        byte[] encryptedData1 = cipher1.doFinal(data);

        Cipher cipher2 = Cipher.getInstance("DES");
        SecretKeyFactory skf2 = SecretKeyFactory.getInstance("DES");
        KeySpec ks2 = new DESKeySpec(key2);
        SecretKey key2Spec = skf2.generateSecret(ks2);
        cipher2.init(Cipher.ENCRYPT_MODE, key2Spec);
        byte[] encryptedData2 = cipher2.doFinal(encryptedData1);

        Cipher cipher3 = Cipher.getInstance("DES");
        SecretKeyFactory skf3 = SecretKeyFactory.getInstance("DES");
        KeySpec ks3 = new DESKeySpec(key3);
        SecretKey key3Spec = skf3.generateSecret(ks3);
        cipher3.init(Cipher.ENCRYPT_MODE, key3Spec);
        return cipher3.doFinal(encryptedData2);
    }

    public byte[] decryptData(byte[] encryptedData, byte[] key1, byte[] key2, byte[] key3) throws Exception {
        Cipher cipher3 = Cipher.getInstance("DES");
        SecretKeyFactory skf3 = SecretKeyFactory.getInstance("DES");
        KeySpec ks3 = new DESKeySpec(key3);
        SecretKey key3Spec = skf3.generateSecret(ks3);
        cipher3.init(Cipher.DECRYPT_MODE, key3Spec);
        byte[] decryptedData3 = cipher3.doFinal(encryptedData);

        Cipher cipher2 = Cipher.getInstance("DES");
        SecretKeyFactory skf2 = SecretKeyFactory.getInstance("DES");
        KeySpec ks2 = new DESKeySpec(key2);
        SecretKey key2Spec = skf2.generateSecret(ks2);
        cipher2.init(Cipher.DECRYPT_MODE, key2Spec);
        byte[] decryptedData2 = cipher2.doFinal(decryptedData3);

        Cipher cipher1 = Cipher.getInstance("DES");
        SecretKeyFactory skf1 = SecretKeyFactory.getInstance("DES");
        KeySpec ks1 = new DESKeySpec(key1);
        SecretKey key1Spec = skf1.generateSecret(ks1);
        cipher1.init(Cipher.DECRYPT_MODE, key1Spec);
        return cipher1.doFinal(decryptedData2);
    }
}
