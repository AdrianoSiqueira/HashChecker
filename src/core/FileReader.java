package core;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileReader {
    private final File hashFile;

    public FileReader(File hashFile) {
        this.hashFile = hashFile;
    }

    public Map<Integer, String> read() {
        final Map<Integer, String> officialHashes = new HashMap<>();

        /*
         * Length table:
         *     MD5: 32
         *   SHA-1: 40
         * SHA-224: 56
         * SHA-256: 64
         * SHA-384: 96
         * SHA-512: 128
         */

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(hashFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.split(" ")[0];
                switch (line.length()) {
                    case 32:
                    case 40:
                    case 56:
                    case 64:
                    case 96:
                    case 128:
                        officialHashes.put(line.length(), line);
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return officialHashes;
    }
}
