package cryptography.tripledes.dao;

import java.io.IOException;

public interface KeyReaderInterface {
    String[] readKeys(String path) throws IOException;

    void writeKeys(String path, String[] keys) throws IOException;
}
