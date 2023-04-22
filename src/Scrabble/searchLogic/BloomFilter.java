package Scrabble.searchLogic;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Vector;

public class BloomFilter {
    int bitSize;
    Vector<String> alogrithms = new Vector<>();
    BitSet bitSet;
    MessageDigest[] msg;

    BloomFilter(int size, String... args) {
        bitSize = size;
        alogrithms.addAll(Arrays.asList(args));
        bitSet = new BitSet(size);
        msg = new MessageDigest[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                msg[i] = MessageDigest.getInstance(args[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void add(String s) {
        for (int i = 0; i < msg.length; i++) {
            byte[] bts = msg[i].digest(s.getBytes());
            BigInteger bigInteger = new BigInteger(bts);
            bitSet.set(Math.abs(bigInteger.intValue() % bitSize), true);
        }
    }

    public boolean contains(String s) {
        for (int i = 0; i < msg.length; i++) {
            byte[] bts = msg[i].digest(s.getBytes());
            BigInteger bigInteger = new BigInteger(bts);
            if (!bitSet.get(Math.abs(bigInteger.intValue() % bitSize))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {
            if (bitSet.get(i)) {
                builder.append("1");
            } else {
                builder.append("0");
            }
        }
        return builder.toString();
    }
}