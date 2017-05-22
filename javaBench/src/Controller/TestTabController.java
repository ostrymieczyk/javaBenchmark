package Controller;

import GUI.Main;
import Helper.HardwareDetailsManager;
import Test.CPU.*;
import Test.GPU.Window;
import Test.HardDrive.MeasureIOPerformance;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

/**
 * Created by robert.ostaszewski on 28.12.2016.
 */
public class TestTabController implements Initializable{

    @FXML
    private Button startBtn;
    @FXML
    private Button cancelButton;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label workingOn;
    @FXML
    private CheckBox cpu;
    @FXML
    private CheckBox gpu;
    @FXML
    private CheckBox disk;
    @FXML
    private CheckBox ram;

    private double progressPick = 0.0;

    Thread first;
    Window cube;
    HardwareDetailsManager hardwareDetailsManager = Main.getHardwareDetailsManager();

    @FXML
    public void setStartBtn() {
        first = new Thread(() -> {
            resetProgressBar();
            countNumOfTests();
            diableControlls();
            if(gpu.isSelected()) {
                increaseProgressAndChangeText("GPU test...");
                cube = new Window();
                cube.addCube(0f, 0f, 0f);
                try {
                    cube.addCubes();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            if(cpu.isSelected()){
//                increaseProgressAndChangeText("CPU: Int test...");
//                IntTest.measureAll(100, 300, 2_500_000);
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: Long test...");
//                LongTest.measureAll(50, 300, 1_250_000);
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: Double test...");
//                DoubleTest.measureAll(25, 200, 1_250_000);
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: Quicksort test...");
//                Quicksort.warmupAndTest(10, 40);
//            }
            if(cpu.isSelected()) {
                increaseProgressAndChangeText("CPU: Prime Number test...");
                PrimeNumberTest.warmupAndTest(5, 10);
            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: compress test...");
//                CompressTest.warmupAndTest(5, 10);
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: encryption test...");
//                DataEncryptior.warmupAndTest(20, 140);
//            }

            if(disk.isSelected()){
                new MeasureIOPerformance().run();
            }
            if (cube != null){
                cube.closeWindow();
                cube.clearCubes();
            }
            enableControlls();
            changeButtonsStatus();
            if(cpu.isSelected() || ram.isSelected() || gpu.isSelected() || disk.isSelected()) {
                increaseProgressAndChangeText("Done!");
            } else {
                setText("Aborted");
            }
            try {
                Files.write(
                        Paths.get("./score.csv"),
                        String
                                .join(",",
                                        System.lineSeparator(),
                                        hardwareDetailsManager.getFormatedCpuDetails(),
                                        "0",
                                        hardwareDetailsManager.getFormatedGpuDetails(),
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        "")
                                .getBytes(),
                        StandardOpenOption.APPEND);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        first.start();
    }

    private void diableControlls(){
        changeDisableStatus(true);
    }

    private void enableControlls(){
        changeDisableStatus(false);
    }

    private void changeDisableStatus(boolean value){
        cancelButton.setDisable(!value);
        startBtn.setDisable(value);
        cpu.setDisable(value);
        gpu.setDisable(value);
        ram.setDisable(value);
        disk.setDisable(value);
    }

    private void countNumOfTests(){
        int numOfTests = 0;

        if(cpu.isSelected())
            numOfTests += 7;
        if(gpu.isSelected())
            numOfTests += 1;
        if(ram.isSelected())
            numOfTests += 1;
        if(disk.isSelected())
            numOfTests += 1;

        progressPick = 1.0/((double)(numOfTests + 1));
        System.out.println(progressPick);

    }

    private void resetProgressBar(){
        progressBar.setProgress(0.0);
    }

    private void increaseProgressAndChangeText(String text){

        double progress = progressBar.getProgress() + progressPick;
        progressBar.setProgress(progress);
        setText(text);
    }

    private void setText(String text){
        Platform.runLater(() -> workingOn.setText(text));
    }

    @FXML
    private void closeThread() throws InterruptedException {
        setText("Cancelling..");;
        cancelButton.setDisable(true);
        cpu.setSelected(false);
        gpu.setSelected(false);
        ram.setSelected(false);
        disk.setSelected(false);
        resetProgressBar();
    }

    @FXML
    private void changeButtonsStatus(){
        if(cpu.isSelected() || ram.isSelected() || gpu.isSelected() || disk.isSelected())
            startBtn.setDisable(false);
        else {
            startBtn.setDisable(true);
            cancelButton.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeButtonsStatus();
        setText("Choose parts to test");
    }
}
