package Helper;

import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.csv.CSVRecord;

/**
 * Created by robert.ostaszewski on 02.05.2017.
 */
public class Record {

    private SimpleStringProperty id;
    private SimpleStringProperty cpuName;
    private final SimpleStringProperty cpuScore;
    private final SimpleStringProperty gpuName;
    private final SimpleStringProperty gpuScore;
    private final SimpleStringProperty diskName;
    private final SimpleStringProperty diskScore;
    private final SimpleStringProperty ramName;
    private final SimpleStringProperty ramScore;
    private final SimpleStringProperty totalScore;

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

    public String getId() {
        return id.get();
    }

    public SimpleStringProperty idProperty() {
        return id;
    }

    public String getCpuName() {
        return cpuName.get();
    }

    public SimpleStringProperty cpuNameProperty() {
        return cpuName;
    }

    public String getCpuScore() {
        return cpuScore.get();
    }

    public SimpleStringProperty cpuScoreProperty() {
        return cpuScore;
    }

    public String getGpuName() {
        return gpuName.get();
    }

    public SimpleStringProperty gpuNameProperty() {
        return gpuName;
    }

    public String getGpuScore() {
        return gpuScore.get();
    }

    public SimpleStringProperty gpuScoreProperty() {
        return gpuScore;
    }

    public String getDiskName() {
        return diskName.get();
    }

    public SimpleStringProperty diskNameProperty() {
        return diskName;
    }

    public String getDiskScore() {
        return diskScore.get();
    }

    public SimpleStringProperty diskScoreProperty() {
        return diskScore;
    }

    public String getRamScore() {
        return ramScore.get();
    }

    public SimpleStringProperty ramScoreProperty() {
        return ramScore;
    }

    public String getRamName() {
        return ramName.get();
    }

    public SimpleStringProperty ramNameProperty() {
        return ramName;
    }

    public String getTotalScore() {
        return totalScore.get();
    }

    public SimpleStringProperty totalScoreProperty() {
        return totalScore;
    }
}
