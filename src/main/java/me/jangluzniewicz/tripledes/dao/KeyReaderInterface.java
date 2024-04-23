package me.jangluzniewicz.tripledes.dao;

import java.io.IOException;

public interface KeyReaderInterface {
    String[] read(String path) throws IOException;

    void write(String path, String[] keys) throws IOException;
}
