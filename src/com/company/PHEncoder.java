package com.company;

import main.java.org.bibits.BitSequence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PHEncoder {
    private BigInteger n, n_1, e, d;
    private final int blockSize = 16;
    private static final int nLength = 155;
    private String blockDivider = "";

    public PHEncoder() {
        this.n = makePrimeN();
        this.n_1 = n.subtract(BigInteger.ONE);
        this.e = makeE();
        this.d = makeD();
    }

    private static String randomBigIntValueAsString() {
        StringBuilder bigIntSB = new StringBuilder("12");
        for (int i = 0; i < nLength - 2; i++) {
            bigIntSB.append((int) (Math.random() * 10));
        }
        return bigIntSB.toString();
    }

    private static BigInteger makePrimeN() {
        BigInteger n = new BigInteger(randomBigIntValueAsString());
        while (!n.isProbablePrime(1)) {
            n = new BigInteger(randomBigIntValueAsString());
        }
        return n;
    }

    private BigInteger makeE() {
        BigInteger n_1 = this.n.subtract(BigInteger.ONE);
        BigInteger bigE = BigInteger.ONE.nextProbablePrime();
        for (int i = 0; i < 10000; i++) {
            while (n_1.remainder(bigE).equals(BigInteger.ZERO)) {
                bigE = bigE.nextProbablePrime();
            }
            bigE = bigE.nextProbablePrime();
        }
        return bigE;
    }

    private BigInteger makeD() {
        BigInteger d = n_1.divide(e).add(BigInteger.ONE);
        BigInteger de = d.multiply(e);
        BigInteger _n_1 = new BigInteger(n_1.toString());
        while (!de.subtract(_n_1).equals(BigInteger.ONE)) {
            _n_1 = _n_1.add(n_1);
            de = de.add(e.multiply(_n_1.subtract(de).divide(e).add(BigInteger.ONE)));
        }
        d = de.divide(e);
        return d;
    }

    public String encoded(String message) {
        while (message.length() % blockSize != 0) {
            message += " ";
        }
        StringBuilder encMsgSB;
        encMsgSB = new StringBuilder();
        this.blockDivider = "";
        for (int i = 0; i < 3; i++) {
            this.blockDivider += (char) (Math.random() * 10);
        }
        for (int i = 0; i < message.length() / blockSize; i++) {
            String blockMsg = message.substring(i * blockSize, i * blockSize + blockSize);
            BitSequence blockMsgSeq = new BitSequence(blockMsg);
            BigInteger encodedBlockAsBI = blockMsgSeq.asBigInteger().modPow(e, n);
            try {
                String encodedBlockAsString = new BitSequence(encodedBlockAsBI).asCharSequence();
                encMsgSB.append(encodedBlockAsString);
                encMsgSB.append(blockDivider);
            } catch (Exception exception) {
                System.out.println("Encoding: Something went wrong!");
            }
        }
        return encMsgSB.toString();
    }

    public String decoded(String message) {
        StringBuilder sb = new StringBuilder();
        for (String sub : message.split(blockDivider)) {
            BigInteger dec = new BitSequence(sub).asBigInteger().modPow(d, n);
            try {
                sb.append(new BitSequence(dec).asCharSequence());
            } catch (Exception exception) {
                System.out.println("Decoding: Something went wrong!");
            }
        }
        return sb.toString();
    }

    public void setN(final String nString) {
        this.n = new BigInteger(nString);
    }

    public void setE(final String eString) {
        this.e = new BigInteger(eString);
    }

    public void setD(final String dString) {
        this.d = new BigInteger(dString);
    }

    public String nAsString() {
        return n.toString();
    }

    public String eAsString() {
        return e.toString();
    }

    public String dAsString() {
        return d.toString();
    }
}
