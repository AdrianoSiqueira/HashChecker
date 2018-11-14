package core;

public enum Algorithm {
    MD5("MD5", 32),
    SHA1("SHA-1", 40),
    SHA224("SHA-224", 56),
    SHA256("SHA-256", 64),
    SHA384("SHA-384", 96),
    SHA512("SHA-512", 128);

    private final String algorithm;
    private final int length;

    Algorithm(String algorithm, int length) {
        this.algorithm = algorithm;
        this.length = length;
    }

    public static Algorithm getByLength(int length) {
        for (Algorithm value : values())
            if (value.length == length)
                return value;
        return null;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getLength() {
        return length;
    }
}
