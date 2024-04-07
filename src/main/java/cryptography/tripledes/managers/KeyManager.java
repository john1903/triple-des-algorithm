package cryptography.tripledes.managers;

import cryptography.tripledes.dao.KeyReader;
import cryptography.tripledes.logic.KeyGeneratorInterface;

import java.io.IOException;

public class KeyManager {
    KeyReader keyReader;
    KeyGeneratorInterface generator;

    public KeyManager(KeyReader keyReader, KeyGeneratorInterface generator) {
        this.keyReader = keyReader;
        this.generator = generator;
    }

    public void generateKeys(String path) throws IOException {
        String[] keys = new String[3];
        for (int i = 0; i < 3; i++) {
            keys[i] = generator.generateKey();
        }
        keyReader.writeKeys(path, keys);
    }

    public byte[][] readKeys(String path) throws IOException {
        byte[][] keys = new byte[3][];
        String[] keyStrings = keyReader.readKeys(path);
        for (int i = 0; i < 3; i++) {
            keys[i] = generator.convertKeyToBits(keyStrings[i]);
        }
        return keys;
    }
}
