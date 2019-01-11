package core.controller;

import java.util.HashMap;
import java.util.Map;

public class ChecksumComparator {
    private final Map<Integer, String> officialHashes;
    private final Map<Integer, String> calculatedHashes;

    public ChecksumComparator(Map<Integer, String> officialHashes, Map<Integer, String> calculatedHashes) {
        this.officialHashes = officialHashes;
        this.calculatedHashes = calculatedHashes;
    }

    public Map<Integer, Boolean> compare() {
        final Map<Integer, Boolean> results = new HashMap<>();

        for (Integer key : officialHashes.keySet()) {
            final String official = officialHashes.get(key);
            final String calculated = calculatedHashes.get(key);
            final boolean status = official.equals(calculated);
            results.put(key, status);
        }

        return results;
    }
}
