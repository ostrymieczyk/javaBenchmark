package Controller;

import Helper.CsvHeaders;
import Helper.Record;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by robert.ostaszewski on 02.05.2017.
 */
public class ScoreTabController implements Initializable {

    @FXML
    private TableView<Record> tableView;
    @FXML
    private TableColumn<Record, String> id;
    @FXML
    private TableColumn<Record, String> cpuScore;
    @FXML
    private TableColumn<Record, String> gpuScore;
    @FXML
    private TableColumn<Record, String> diskScore;
    @FXML
    private TableColumn<Record, String> ramScore;
    @FXML
    private TableColumn<Record, String> totalScore;
    @FXML
    private TextFlow textFlow;
    @FXML
    private AnchorPane anchorPane;

    private List<Record> recordList = new ArrayList<>();
    public static Path defaultPath = Paths.get("./score.csv");

    public void loadCSV(){

        if (Files.notExists(defaultPath)) {
            try {
                Files.write( defaultPath,
                    Arrays.stream(CsvHeaders.class.getDeclaredFields())
                        .map(field -> {
                            try {
                                return field.get(field).toString();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .collect(Collectors.joining(","))
                        .getBytes() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(Reader in = Files.newBufferedReader(defaultPath);
            CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in))
        {
            for(CSVRecord csvRecord : records){
                recordList.add(new Record(csvRecord));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.getColumns().stream().forEach(column -> column.setStyle("-fx-alignment: CENTER"));
        id.setCellValueFactory(new PropertyValueFactory<Record,String>("id"));
        cpuScore.setCellValueFactory(new PropertyValueFactory<Record,String>("cpuScore"));
        gpuScore.setCellValueFactory(new PropertyValueFactory<Record,String>("gpuScore"));
        diskScore.setCellValueFactory(new PropertyValueFactory<Record,String>("diskScore"));
        ramScore.setCellValueFactory(new PropertyValueFactory<Record,String>("ramScore"));
        totalScore.setCellValueFactory(new PropertyValueFactory<Record,String>("totalScore"));
        loadCSV();
        tableView.getItems().setAll(recordList);
        anchorPane.addEventHandler(Tab.SELECTION_CHANGED_EVENT, event ->
            {
                recordList.clear();
                loadCSV();
                tableView.getItems().setAll(recordList);
            }
        );
    }

    @FXML
    public void clickItem(MouseEvent event)
    {
        SelectionModel selectionModel = tableView.getSelectionModel();
        if(!selectionModel.isEmpty()) {
            Record record = (Record) selectionModel.getSelectedItem();
            String cpuModel = record.getCpuName();
            String gpuModel = record.getGpuName();
            String ramModel = record.getRamName();
            String diskModel = record.getDiskName();

            Text toShow = new Text("CPU: " + cpuModel + "\n" +
                    "GPU: " + gpuModel + "\n" +
                    "Disk: " + diskModel + "\n" +
                    "RAM: " + ramModel);

            textFlow.getChildren().clear();
            textFlow.getChildren().add(toShow);
        }
    }


}
