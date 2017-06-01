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
 * Kontroler zachowania zakladki Score w stworzonej aplikacji
 */
public class ScoreTabController implements Initializable {

    /**
     * Obiekt generujacy tabele z elementow typu {@link Record}
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TableView<Record> tableView;

    /**
     * Kolumna zawierajace dane o nazwie testowanych urzadzen
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TableColumn<Record, String> name;

    /**
     * Kolumna przedstawiajaca zapisane wyniki testow procesorow
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TableColumn<Record, String> cpuScore;

    /**
     * Kolumna przedstawiajaca zapisane wyniki testow kart graficznych
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TableColumn<Record, String> gpuScore;

    /**
     * Kolumna przedstawiajaca zapisane wyniki testow dyskow komputerowych
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TableColumn<Record, String> diskScore;

    /**
     * Kolumna przedstawiajaca zapisane wyniki pamieci RAM
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TableColumn<Record, String> ramScore;

    /**
     * Kolumna zawierajace sume wynikow testowanych urzadzen
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TableColumn<Record, String> totalScore;

    /**
     * Element odpowiedzialny za wyswitlenie informacji na temat testowanego komputera,
     * ktory osiagnal wskazany przez uzytkownika wynik
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private TextFlow textFlow;

    /**
     * Element do ktorego zostaja doczepiane pozostale kontrolki
     * Inicjalizacja tego obiektu odbywa sie za pomoca pliku fxml.
     */
    @FXML
    private AnchorPane anchorPane;

    /**
     * Lista zawierajaca wszystkie rekordy zapisane w pliku
     */
    private final List<Record> recordList = new ArrayList<>();

    /**
     * Sciezka do pliku z zapisanymi wynikami.
     */
    private static final Path defaultPath = Paths.get("./score.csv");

    /**
     * Funkcja wczytujaca dane z pliku csv.
     * Jesli plik nie istnieje, to jest tworzony
     */
    private void loadFromCSV(){

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
        tableView.getColumns().forEach(column -> column.setStyle("-fx-alignment: CENTER"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        cpuScore.setCellValueFactory(new PropertyValueFactory<>("cpuScore"));
        gpuScore.setCellValueFactory(new PropertyValueFactory<>("gpuScore"));
        diskScore.setCellValueFactory(new PropertyValueFactory<>("diskScore"));
        ramScore.setCellValueFactory(new PropertyValueFactory<>("ramScore"));
        totalScore.setCellValueFactory(new PropertyValueFactory<>("totalScore"));
        loadFromCSV();
        tableView.getItems().setAll(recordList);
        anchorPane.addEventHandler(Tab.SELECTION_CHANGED_EVENT, event -> {
            recordList.clear();
            loadFromCSV();
            tableView.getItems().setAll(recordList);
        });
    }

    /**
     * Odpowiada za wyswietlenie danych o sprzecie na wyniki ktorego uzytkownik nacisnal.
     * Zmienna
     *
     * @param event
     */
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
