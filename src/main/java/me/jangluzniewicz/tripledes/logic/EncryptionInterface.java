package me.jangluzniewicz.tripledes.logic;

import java.util.BitSet;

/**
 * The EncryptionInterface defines the methods for encryption and decryption.
 */
public interface EncryptionInterface {

     /**
      * Encrypts the given input BitSet using the provided key BitSet.
      *
      * @param input the input BitSet to be encrypted
      * @param key the key BitSet to be used for encryption
      * @return the encrypted BitSet
      */
     BitSet encryption(BitSet input, BitSet key);

     /**
      * Decrypts the given input BitSet using the provided key BitSet.
      *
      * @param input the input BitSet to be decrypted
      * @param key the key BitSet to be used for decryption
      * @return the decrypted BitSet
      */
     BitSet decryption(BitSet input, BitSet key);
}