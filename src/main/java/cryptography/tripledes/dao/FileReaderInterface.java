package cryptography.tripledes.dao;

import java.io.IOException;

public interface FileReaderInterface {
    byte[] read(String path) throws IOException;

    void write(String path, byte[] content) throws IOException;
}
