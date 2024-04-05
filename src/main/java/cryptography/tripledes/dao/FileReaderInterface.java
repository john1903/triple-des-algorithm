package cryptography.tripledes.dao;

import java.io.IOException;

public interface FileReaderInterface {
    public byte[] read(String path) throws IOException;

    void write(String path, byte[] content) throws IOException;
}
