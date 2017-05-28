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

    /**
     *
     */
    private Stage primaryStage;
    /**
     *
     */
    private TabPane rootLayout;

    /**
     *
     */
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

    /**
     *
     */
    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/TabRoot.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     */
    private void showTestTab() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/TestTab.fxml"));
            AnchorPane testTab = loader.load();
            rootLayout.getTabs().get(0).setContent(testTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    private void showRankingTab(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/ScoreTab.fxml"));
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

    /**
     *
     */
    private void showInfoTab(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/GUI/InfoTab.fxml"));
            AnchorPane infoTab = loader.load();
            rootLayout.getTabs().get(2).setContent(infoTab);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public Main(){
        if(System.getProperty("os.name")
                .toLowerCase().startsWith("windows")){
            hardwareDetailsManager = new WindowsHardwareDetailsManager();
        }
        else {
            hardwareDetailsManager = new LinuxHardwareDetailsManager();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return
     */
    public static HardwareDetailsManager getHardwareDetailsManager() {
        return hardwareDetailsManager;
    }
}