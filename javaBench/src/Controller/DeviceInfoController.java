package Controller;

import GUI.Main;
import Helper.HardwareDetailsManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 */
public class DeviceInfoController implements Initializable{

    /**
     *
     */
    @FXML
    private TextFlow textFlow;

    /**
     *
     */
    private final HardwareDetailsManager hardwareDetailsManager = Main.getHardwareDetailsManager();

    /**
     * @return
     */
    private String SystemInfo() {

        return "# CPU: " + hardwareDetailsManager.getFormattedCpuDetails() + "\n" +
                    "# GPU: " + hardwareDetailsManager.getFormattedGpuDetails() + "\n" +
                    "# Disk: " + hardwareDetailsManager.getFormattedDiskDetails() + "\n" +
                    "# Ram: " + hardwareDetailsManager.getFormattedRamDetails() + "\n";
    }

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFlow.getChildren().add(new Text(SystemInfo()));
    }
}
