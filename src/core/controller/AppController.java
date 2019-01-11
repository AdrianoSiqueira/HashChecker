package core.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AppController {
    private final Map<Integer, String> officialHashes;
    private final Map<Integer, String> calculatedHashes;
    private final Map<Integer, Boolean> results;

    private File fileToCheck;
    private File hashFile;

    public AppController() {
        officialHashes = new HashMap<>();
        calculatedHashes = new HashMap<>();
        results = new HashMap<>();

        fileToCheck = null;
        hashFile = null;
    }

    public boolean run() {
        if (fileToCheck != null && hashFile != null) {
            officialHashes.putAll(new FileReader(hashFile).read());
            calculatedHashes.putAll(new ChecksumCalculator(fileToCheck, officialHashes.keySet()).calculate());
            results.putAll(new ChecksumComparator(officialHashes, calculatedHashes).compare());
            return true;
        } else return false;
    }

    public void reset() {
        fileToCheck = null;
        hashFile = null;

        officialHashes.clear();
        calculatedHashes.clear();
        results.clear();
    }

    public File getFileToCheck() {
        return fileToCheck;
    }

    public void setFileToCheck(File fileToCheck) {
        this.fileToCheck = fileToCheck;
    }

    public File getHashFile() {
        return hashFile;
    }

    public void setHashFile(File hashFile) {
        this.hashFile = hashFile;
    }

    public Map<Integer, String> getOfficialHashes() {
        return officialHashes;
    }

    public Map<Integer, String> getCalculatedHashes() {
        return calculatedHashes;
    }

    public Map<Integer, Boolean> getResults() {
        return results;
    }
}
