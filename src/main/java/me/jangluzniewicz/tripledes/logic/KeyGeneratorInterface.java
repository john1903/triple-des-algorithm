package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

/**
 * The KeyGeneratorInterface defines the method for generating encryption keys.
 */
public interface KeyGeneratorInterface {

    /**
     * Generates a secure random key.
     *
     * @return a BitSet representing the generated key
     */
    BitSet generateKey();
}
