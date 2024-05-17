package me.jangluzniewicz.tripledes.logic;

import org.junit.jupiter.api.Test;
import java.util.BitSet;
import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorTest {
    @Test
    void testGenerateKey() {
        KeyGenerator keyGenerator = new KeyGenerator();
        BitSet key = keyGenerator.generateKey();
        assertNotNull(key);
        BitSet key2 = keyGenerator.generateKey();
        assertNotEquals(key, key2);
    }
}
