package cryptography.tripledes.managers;

import cryptography.tripledes.logic.KeyGenerator;

import java.io.IOException;

import static cryptography.tripledes.logic.KeyGenerator.hexStringToBits;

public interface KeyManagerInterface {
    public void generateKeys(String path) throws IOException;

    public byte[][] readKeys(String path) throws IOException;
}
