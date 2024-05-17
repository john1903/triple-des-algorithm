package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

public interface EncryptionInterface {
     BitSet encryption(BitSet input, BitSet key);

     BitSet decryption(BitSet input, BitSet key);
}
