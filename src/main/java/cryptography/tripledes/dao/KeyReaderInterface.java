package cryptography.tripledes.dao;

import java.io.IOException;
import java.util.ArrayList;

public interface KeyReaderInterface {
    String[] readKeys(String path) throws IOException;

    void writeKeys(String path, ArrayList<String> keys) throws IOException;
}
