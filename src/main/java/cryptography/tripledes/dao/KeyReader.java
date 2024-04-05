package cryptography.tripledes.dao;

import java.io.*;
import java.util.ArrayList;

public class KeyReader implements KeyReaderInterface {
    @Override
    public String[] readKeys(String path) throws IOException {
        String[] keys = new String[3];
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                keys[count] = line;
                count++;
            }
        } catch (IOException e) {
            throw new IOException("Error reading keys from file: " + e.getMessage());
        }
        return keys;
    }

    @Override
    public void writeKeys(String path, ArrayList<String> keys) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))) {
            for (String key : keys) {
                writer.write(key);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error writing keys to file: " + e.getMessage());
        }
    }
}