package cryptography.tripledes.managers;

import cryptography.tripledes.dao.KeyReader;
import cryptography.tripledes.logic.KeyGenerator;

import java.io.IOException;
import java.util.ArrayList;

public class KeyManager {
    KeyReader keyReader;

    public KeyManager(KeyReader keyReader) {
        this.keyReader = keyReader;
    }

    public void generateKeys(String path) throws IOException {
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            keys.add(KeyGenerator.generateKey());
        }
        keyReader.writeKeys(path, keys);
    }

    public byte[] readKeys(String path, int keyIndex) throws IOException {
        return KeyGenerator.hexStringToBits(keyReader.readKeys(path)[keyIndex]);
    }
}
