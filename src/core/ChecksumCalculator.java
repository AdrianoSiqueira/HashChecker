package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class ChecksumCalculator {
    private final File fileToCheck;
    private final List<Integer> algorithms;

    public ChecksumCalculator(File fileToCheck, Set<Integer> algorithms) {
        this.fileToCheck = fileToCheck;
        this.algorithms = new ArrayList<>(algorithms);
    }

    public Map<Integer, String> calculateSpeed() {
        final Map<Integer, String> calculatedHashes = new HashMap<>();
        final List<Thread> threadList = new ArrayList<>();

        for (int i : algorithms) {
            Thread thread = new Thread(() -> {
                try (InputStream stream = new FileInputStream(fileToCheck)) {
                    MessageDigest digest = MessageDigest.getInstance(Algorithm.getByLength(i).getAlgorithm());
                    final byte[] buffer = new byte[1024];
                    int readBytes;

                    while ((readBytes = stream.read(buffer)) > -1)
                        digest.update(buffer, 0, readBytes);

                    calculatedHashes.put(i, new BigInteger(1, digest.digest()).toString(16));
                } catch (Exception e) {
                    System.err.println(e.toString());
                }
            });
            thread.start();
            threadList.add(thread);
        }

        /* Wait for all threads to finish */
        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e.toString());
            }
        }

        return calculatedHashes;
    }
}
