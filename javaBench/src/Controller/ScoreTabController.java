package Controller;

import Helper.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    private List<Record> recordList = new ArrayList<>();

    public void loadCSV(){

        Reader in = null;
        try {
            in = new FileReader("C:\\Users\\Robert Ostaszewski\\IdeaProjects\\javaBenchmark\\javaBench\\src\\GUI\\score.csv");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CSVParser records = null;
        try {
            records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);


        } catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<CSVRecord> data = FXCollections.observableArrayList();
        for(CSVRecord csvRecord : records){
            recordList.add(new Record(csvRecord));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (TableColumn tableColumn : tableView.getColumns()){
            tableColumn.setStyle("-fx-alignment: CENTER");
        }
        id.setCellValueFactory(new PropertyValueFactory<Record,String>("id"));
        cpuScore.setCellValueFactory(new PropertyValueFactory<Record,String>("cpuScore"));
        gpuScore.setCellValueFactory(new PropertyValueFactory<Record,String>("gpuScore"));
        diskScore.setCellValueFactory(new PropertyValueFactory<Record,String>("diskScore"));
        ramScore.setCellValueFactory(new PropertyValueFactory<Record,String>("ramScore"));
        totalScore.setCellValueFactory(new PropertyValueFactory<Record,String>("totalScore"));
        loadCSV();
        tableView.getItems().setAll(recordList);
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
