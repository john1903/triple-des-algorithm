package cryptography.tripledes.managers;

import cryptography.tripledes.dao.KeyReader;
import cryptography.tripledes.logic.KeyGenerator;

import java.io.IOException;

import static cryptography.tripledes.logic.KeyGenerator.hexToBitsArray;

public class KeyManager {
    KeyReader keyReader;

    public KeyManager(KeyReader keyReader) {
        this.keyReader = keyReader;
    }

    public void generateKeys(String path) throws IOException {
        String[] keys = new String[3];
        for (int i = 0; i < 3; i++) {
            keys[i] = KeyGenerator.generateKey();
        }
        keyReader.writeKeys(path, keys);
    }

    public byte[][] readKeys(String path) throws IOException {
        byte[][] keys = new byte[3][];
        String[] keyStrings = keyReader.readKeys(path);
        for (int i = 0; i < 3; i++) {
            keys[i] = hexToBitsArray(keyStrings[i]);
        }
        return keys;
    }
}
