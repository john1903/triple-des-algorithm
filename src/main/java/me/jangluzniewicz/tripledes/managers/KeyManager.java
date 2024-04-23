package me.jangluzniewicz.tripledes.managers;

import me.jangluzniewicz.tripledes.dao.KeyReaderInterface;
import me.jangluzniewicz.tripledes.logic.KeyGeneratorInterface;

import java.io.IOException;

public class KeyManager {
    private final KeyReaderInterface keyReader;
    private final KeyGeneratorInterface generator;

    public KeyManager(KeyReaderInterface keyReader, KeyGeneratorInterface generator) {
        this.keyReader = keyReader;
        this.generator = generator;
    }

    public void write(String path) throws IOException {
        String[] keys = new String[3];
        for (int i = 0; i < 3; i++) {
            keys[i] = generator.generateKey();
        }
        keyReader.write(path, keys);
    }

    public byte[][] read(String path) throws IOException {
        byte[][] keys = new byte[3][];
        String[] keyStrings = keyReader.read(path);
        for (int i = 0; i < 3; i++) {
            keys[i] = generator.convertKeyToBits(keyStrings[i]);
        }
        return keys;
    }
}
