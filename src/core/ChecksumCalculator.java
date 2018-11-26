package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

                    /*
                     * The following code works well
                     * final String hash = new BigInteger(1, digest.digest()).toString(16);
                     *
                     * But if the calculated hash starts with '0', this '0' will
                     * be ignored at conversion time.
                     *
                     * The following loop fix it.
                     */

                    final StringBuilder hash = new StringBuilder();
                    for (byte b : digest.digest())
                        hash.append(String.format("%02x", b));

                    calculatedHashes.put(i, hash.toString());
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
