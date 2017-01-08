package GUI;

import Controller.DeviceInfoController;
import Controller.StartBtnController;
import Controller.TestTabController;
import Model.Person;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private TabPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        initRootLayout();

        showTestTab();
        showRankingTab();
        showInfoTab();
    }

    /**
     * Initializes the root layout.
     */
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

            // Set person overview into the center of root layout.
            rootLayout.getTabs().get(0).setContent(testTab);

//            TestTabController controller = loader.getController();
//            controller.printWelcome();
//            controller.setStartBtn();
//            StartBtnController start = loader.getController();
//            start.setHandler();

            // Give the controller access to the main app.
//            PersonOverviewController controller = loader.getController();
//            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRankingTab(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ScoreTab.fxml"));
            AnchorPane scoreTab = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.getTabs().get(1).setContent(scoreTab);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showInfoTab(){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("InfoTab.fxml"));
            AnchorPane infoTab = (AnchorPane) loader.load();
            // Set person overview into the center of root layout.
            rootLayout.getTabs().get(2).setContent(infoTab);
//            DeviceInfoController controller = new DeviceInfoController();
//            controller.SystemInfo();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public Main() {
        // Add some sample data
//        personData.add(new Person("Hans", "Muster"));
//        personData.add(new Person("Ruth", "Mueller"));
//        personData.add(new Person("Heinz", "Kurz"));
//        personData.add(new Person("Cornelia", "Meier"));
//        personData.add(new Person("Werner", "Meyer"));
//        personData.add(new Person("Lydia", "Kunz"));
//        personData.add(new Person("Anna", "Best"));
//        personData.add(new Person("Stefan", "Meier"));
//        personData.add(new Person("Martin", "Mueller"));
    }

    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }


    public static void main(String[] args) {
        launch(args);
    }
}