package Controller;

import Helper.HardwareDetails;
import Helper.WindowsHardwareDetails;
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

    private HardwareDetails hardwareDetails = new WindowsHardwareDetails();

    public String SystemInfo() {
        String info = "# CPU: " + hardwareDetails.getFormatedCpu() + "\n" +
                    "# GPU: " + hardwareDetails.getFormatedGpu() + "\n" +
                    "# Disk: " + hardwareDetails.getFormatedDisk() + "\n" +
                    "# Ram: " + hardwareDetails.getFormatedRam() + "\n";

        return info ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFlow.getChildren().add(new Text(SystemInfo()));
    }
}
