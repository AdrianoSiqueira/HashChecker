package core.model;

/**
 * <p> Data model for {@link gui.ReportUI}'s table. </p>
 */
public class Report {
    private final String algorithm;
    private final String officialHash;
    private final String calculatedHash;
    private final String status;

    public Report(String algorithm, String officialHash, String calculatedHash, String status) {
        this.algorithm = algorithm;
        this.officialHash = officialHash;
        this.calculatedHash = calculatedHash;
        this.status = status;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getOfficialHash() {
        return officialHash;
    }

    public String getCalculatedHash() {
        return calculatedHash;
    }

    public String getStatus() {
        return status;
    }
}