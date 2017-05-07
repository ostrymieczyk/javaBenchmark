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
 * Created by robert.ostaszewski on 28.12.2016.
 */
public class DeviceInfoController implements Initializable{

    @FXML
    private TextFlow textFlow;

    private HardwareDetailsManager hardwareDetailsManager = Main.getHardwareDetailsManager();

    public String SystemInfo() {
        String info = "# CPU: " + hardwareDetailsManager.getFormatedCpuDetails() + "\n" +
                    "# GPU: " + hardwareDetailsManager.getFormatedGpuDetails() + "\n" +
                    "# Disk: " + hardwareDetailsManager.getFormatedDiskDetails() + "\n" +
                    "# Ram: " + hardwareDetailsManager.getFormatedRamDetails() + "\n";

        return info ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFlow.getChildren().add(new Text(SystemInfo()));
    }
}
