package Helper;

import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.csv.CSVRecord;

/**
 *
 */
public class Record {

    /**
     *
     */
    private final SimpleStringProperty id;
    /**
     *
     */
    private final SimpleStringProperty cpuName;
    /**
     *
     */
    private final SimpleStringProperty cpuScore;
    /**
     *
     */
    private final SimpleStringProperty gpuName;
    /**
     *
     */
    private final SimpleStringProperty gpuScore;
    /**
     *
     */
    private final SimpleStringProperty diskName;
    /**
     *
     */
    private final SimpleStringProperty diskScore;
    /**
     *
     */
    private final SimpleStringProperty ramName;
    /**
     *
     */
    private final SimpleStringProperty ramScore;
    /**
     *
     */
    private final SimpleStringProperty totalScore;

    /**
     * @param csvRecord
     */
    public Record(CSVRecord csvRecord){

        id = new SimpleStringProperty(csvRecord.get(CsvHeaders.ID));
        cpuName = new SimpleStringProperty(csvRecord.get(CsvHeaders.CPU_NAME));
        cpuScore = new SimpleStringProperty(csvRecord.get(CsvHeaders.CPU_SCORE));
        gpuName = new SimpleStringProperty(csvRecord.get(CsvHeaders.GPU_NAME));
        gpuScore = new SimpleStringProperty(csvRecord.get(CsvHeaders.GPU_SCORE));
        diskName = new SimpleStringProperty(csvRecord.get(CsvHeaders.DISK_NAME));
        diskScore = new SimpleStringProperty(csvRecord.get(CsvHeaders.DISK_SCORE));
        ramName = new SimpleStringProperty(csvRecord.get(CsvHeaders.RAM_NAME));
        ramScore = new SimpleStringProperty(csvRecord.get(CsvHeaders.RAM_SCORE));
        totalScore = new SimpleStringProperty(csvRecord.get(CsvHeaders.TOTAL_SCORE));

    }

    /**
     * @return
     */
    public String getId() {
        return id.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty idProperty() {
        return id;
    }

    /**
     * @return
     */
    public String getCpuName() {
        return cpuName.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty cpuNameProperty() {
        return cpuName;
    }

    /**
     * @return
     */
    public String getCpuScore() {
        return cpuScore.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty cpuScoreProperty() {
        return cpuScore;
    }

    /**
     * @return
     */
    public String getGpuName() {
        return gpuName.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty gpuNameProperty() {
        return gpuName;
    }

    /**
     * @return
     */
    public String getGpuScore() {
        return gpuScore.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty gpuScoreProperty() {
        return gpuScore;
    }

    /**
     * @return
     */
    public String getDiskName() {
        return diskName.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty diskNameProperty() {
        return diskName;
    }

    /**
     * @return
     */
    public String getDiskScore() {
        return diskScore.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty diskScoreProperty() {
        return diskScore;
    }

    /**
     * @return
     */
    public String getRamScore() {
        return ramScore.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty ramScoreProperty() {
        return ramScore;
    }

    /**
     * @return
     */
    public String getRamName() {
        return ramName.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty ramNameProperty() {
        return ramName;
    }

    /**
     * @return
     */
    public String getTotalScore() {
        return totalScore.get();
    }

    /**
     * @return
     */
    public SimpleStringProperty totalScoreProperty() {
        return totalScore;
    }
}
