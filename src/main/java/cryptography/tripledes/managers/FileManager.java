package cryptography.tripledes.managers;

import cryptography.tripledes.dao.FileReaderInterface;

public class FileManager {
    private final FileReaderInterface fileReader;

    public FileManager(FileReaderInterface fileReader) {
        this.fileReader = fileReader;
    }

    public byte[] read(String path) throws Exception {
        return fileReader.read(path);
    }

    public void write(String path, byte[] content) throws Exception {
        fileReader.write(path, content);
    }
}
