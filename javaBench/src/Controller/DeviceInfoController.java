package Controller;

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

    public String SystemInfo() {
        String info = "# OS: "+ System.getProperty("os.name") +", " + System.getProperty("os.version") + ", " + System.getProperty("os.arch") + "\n " +  "# JVM: " + System.getProperty("java.vendor") + ", " + System.getProperty("java.version");
// The processor identifier works only on MS Windows:
        System.out.printf("# CPU: %s; %d \"procs\"%n",
                System.getenv("PROCESSOR_IDENTIFIER"),
                Runtime.getRuntime().availableProcessors());
        java.util.Date now = new java.util.Date();
//        System.out.printf("# Date: %s%n",
//                new java.text.SimpleDateFormat("yyyy-MM-dd’T’HH:mm:ssZ").format(now));
//        String info = " elo ";
//        textFlow.getChildren().add(new Text(info));

        return info ;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFlow.getChildren().add(new Text(SystemInfo()));
    }
}
