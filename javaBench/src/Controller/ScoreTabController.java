package Controller;

import Helper.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.swing.text.TabableView;
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
    private TableColumn<Record, String> cpuModel;
    @FXML
    private TableColumn<Record, String> cpuScore;
    @FXML
    private TableColumn<Record, String> gpuModel;
    @FXML
    private TableColumn<Record, String> gpuScore;
    @FXML
    private TableColumn<Record, String> diskModel;
    @FXML
    private TableColumn<Record, String> diskScore;
    @FXML
    private TableColumn<Record, String> ramModel;
    @FXML
    private TableColumn<Record, String> ramScore;
    @FXML
    private TableColumn<Record, String> totalScore;

    private List<Record> recordList = new ArrayList<>();

    public void loadCSV(){

        Reader in = null;
        try {
            in = new FileReader("C:\\Users\\robert.ostaszewski\\Desktop\\score.csv");

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
        id.setCellValueFactory(new PropertyValueFactory<Record,String>("id"));
        cpuModel.setCellValueFactory(new PropertyValueFactory<Record,String>("cpuName"));
        cpuScore.setCellValueFactory(new PropertyValueFactory<Record,String>("cpuScore"));
        gpuModel.setCellValueFactory(new PropertyValueFactory<Record,String>("gpuName"));
        gpuScore.setCellValueFactory(new PropertyValueFactory<Record,String>("gpuScore"));
        diskModel.setCellValueFactory(new PropertyValueFactory<Record,String>("diskName"));
        diskScore.setCellValueFactory(new PropertyValueFactory<Record,String>("diskScore"));
        ramModel.setCellValueFactory(new PropertyValueFactory<Record,String>("ramName"));
        ramScore.setCellValueFactory(new PropertyValueFactory<Record,String>("ramScore"));
        totalScore.setCellValueFactory(new PropertyValueFactory<Record,String>("totalScore"));
        loadCSV();
        tableView.getItems().setAll(recordList);
    }

}
