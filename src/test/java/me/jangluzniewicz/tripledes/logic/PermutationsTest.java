package me.jangluzniewicz.tripledes.logic;

import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class PermutationsTest {
    @Test
    void initialPermutation() {
        BitSet input = Transformations.createBitSet(
                0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1,
                0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1,
                1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1,
                1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1
        );

        BitSet expectedLeft = Transformations.createBitSet(
                1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1
        );
        BitSet expectedRight = Transformations.createBitSet(
                1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0,
                1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0
        );

        BitSet[] result = Permutations.initialPermutation(input);

        assertEquals(expectedLeft, result[0]);
        assertEquals(expectedRight, result[1]);
    }

    @Test
    void finalPermutation() {
        BitSet left = Transformations.createBitSet(
                0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1
        );
        BitSet right = Transformations.createBitSet(
                0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0
        );
        BitSet expected = Transformations.createBitSet(
                1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0,
                0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1
        );

        BitSet result = Permutations.finalPermutation(left, right);

        assertEquals(expected, result);
    }

    @Test
    void permutedChoice1Test() {
        BitSet key = Transformations.createBitSet(
                0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1,
                0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1
        );
        BitSet expectedLeft = Transformations.createBitSet(
                1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1
        );
        BitSet expectedRight = Transformations.createBitSet(
                0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1
        );

        BitSet[] result = Permutations.permutedChoice1(key);

        assertEquals(expectedLeft, result[0]);
        assertEquals(expectedRight, result[1]);
    }

    @Test
    void permutedChoice2Test() {
        BitSet keyLeft = Transformations.createBitSet(
                1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1,
                0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1
        );
        BitSet keyRight = Transformations.createBitSet(
                1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 0,
                1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0
        );

        BitSet key = new BitSet(56);
        for (int i = 0; i < 28; i++) {
            key.set(i, keyLeft.get(i));
            key.set(i + 28, keyRight.get(i));
        }

        BitSet expected = Transformations.createBitSet(
                0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0,
                1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0
        );

        BitSet result = Permutations.permutedChoice2(key);

        assertEquals(expected, result);
    }

    @Test
    void expansionPermutation() {
        BitSet input = Transformations.createBitSet(
                1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0,
                1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0
        );
        BitSet expected = Transformations.createBitSet(
                0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1,
                0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0,
                0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1
        );

        BitSet result = Permutations.extensionPermutation(input);

        assertEquals(expected, result);
    }

    @Test
    void sBoxPermutation() {
        BitSet input = Transformations.createBitSet(
                0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1,
                1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0,
                0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1
        );
        BitSet expected = Transformations.createBitSet(
                0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0,
                1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1
        );

        BitSet result = Permutations.sBoxSubstitution(input);

        assertEquals(expected, result);
    }

    @Test
    void pTable() {
        BitSet input = Transformations.createBitSet(
                0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0,
                1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 1
        );
        BitSet expected = Transformations.createBitSet(
                0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0,
                1, 0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1
        );

        BitSet result = Permutations.pBoxPermutation(input);

        assertEquals(expected, result);
    }
}