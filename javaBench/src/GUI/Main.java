package GUI;


import Helper.HardwareDetailsManager;
import Helper.LinuxHardwareDetailsManager;
import Helper.WindowsHardwareDetailsManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private TabPane rootLayout;

    private static HardwareDetailsManager hardwareDetailsManager;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Java Benchmark");

        this.primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        initRootLayout();

        showTestTab();
        showRankingTab();
        showInfoTab();
    }

    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("TabRoot.fxml"));
            rootLayout = (TabPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showTestTab() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("TestTab.fxml"));
            AnchorPane testTab = (AnchorPane) loader.load();
            rootLayout.getTabs().get(0).setContent(testTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRankingTab(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ScoreTab.fxml"));
            AnchorPane scoreTab = loader.load();
            rootLayout.getTabs().get(1).setContent(scoreTab);
            rootLayout.getTabs().get(1).setOnSelectionChanged(event -> {
                if (rootLayout.getTabs().get(1).isSelected()) {
                    scoreTab.fireEvent(event);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showInfoTab(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("InfoTab.fxml"));
            AnchorPane infoTab = (AnchorPane) loader.load();
            rootLayout.getTabs().get(2).setContent(infoTab);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main(){
        if(System.getProperty("os.name")
                .toLowerCase().startsWith("windows")){
            hardwareDetailsManager = new WindowsHardwareDetailsManager();
        }
        else {
            hardwareDetailsManager = new LinuxHardwareDetailsManager();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static HardwareDetailsManager getHardwareDetailsManager() {
        return hardwareDetailsManager;
    }
}