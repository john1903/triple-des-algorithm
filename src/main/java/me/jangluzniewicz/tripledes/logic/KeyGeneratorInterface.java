package me.jangluzniewicz.tripledes.logic;

public interface KeyGeneratorInterface {
    byte[] convertKeyToBits(String hex);

    String generateKey();
}
