package Controller;

import GUI.Main;
import Helper.HardwareDetailsManager;
import Test.CPU.*;
import Test.GPU.Window;
import Test.HardDrive.MeasureIOPerformance;
import Test.RAM.TestMemoryAccessPatterns;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

public class TestTabController implements Initializable{

    /**
     *
     */
    @FXML
    private Button startBtn;

    /**
     *
     */
    @FXML
    private Button cancelButton;

    /**
     *
     */
    @FXML
    private ProgressBar progressBar;
    /**
     *
     */
    @FXML
    private Label workingOn;
    /**
     *
     */
    @FXML
    private CheckBox cpu;
    /**
     *
     */
    @FXML
    private CheckBox gpu;
    /**
     *
     */
    @FXML
    private CheckBox disk;
    /**
     *
     */
    @FXML
    private CheckBox ram;
    /**
     *
     */
    @FXML
    private TextFlow textFlow;

    /**
     *
     */
    private double progressPick = 0.0;

    /**
     *
     */
    private Thread first;
    /**
     *
     */
    private Window cube;
    /**
     *
     */
    private final HardwareDetailsManager hardwareDetailsManager = Main.getHardwareDetailsManager();

    /**
     *
     */
    @FXML
    public void setStartBtn() {
        first = new Thread(() -> {
            resetProgressBar();
            countNumOfTests();
            diableControlls();
            ResultController.reset();
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
            if (cube != null){
                cube.clearCubes();
            }
//            if(cpu.isSelected()){
//                increaseProgressAndChangeText("CPU: Int test...");
//                IntTest.measureAll();
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: Long test...");
//                LongTest.measureAll();
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: Double test...");
//                DoubleTest.measureAll();
//            }
            if(cpu.isSelected()) {
                increaseProgressAndChangeText("CPU: Quicksort test...");
                Quicksort.warmAndTest();
            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: Prime Number test...");
//                PrimeNumberTest.warmAndTest();
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: compress test...");
//                CompressTest.warmAndTest();
//            }
//            if(cpu.isSelected()) {
//                increaseProgressAndChangeText("CPU: encryption test...");
//                DataEncryption.warmAndTest();
//            }

            if(disk.isSelected()){
                increaseProgressAndChangeText("DISK: write, read speed test...");
                new MeasureIOPerformance().run();
            }

            if(ram.isSelected()){
                increaseProgressAndChangeText("RAM test...");
                try {
                    new TestMemoryAccessPatterns().run();
                } catch (OutOfMemoryError e){
                    setText("java.lang.OutOfMemoryError: Java heap space");
                    throw new OutOfMemoryError(e.getMessage());
                }

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
                                        System.lineSeparator() + hardwareDetailsManager.getFormattedName(),
                                        hardwareDetailsManager.getFormattedCpuDetails(),
                                        getFormatedResult(ResultController.getCpuScore()),
                                        hardwareDetailsManager.getFormattedGpuDetails(),
                                        getFormatedResult(ResultController.getGpuScore()),
                                        hardwareDetailsManager.getFormattedDiskDetails(),
                                        getFormatedResult(ResultController.getDiskScore()),
                                        hardwareDetailsManager.getFormattedRamDetails(),
                                        getFormatedResult(ResultController.getRamScore()),
                                        getFormatedResult(ResultController.getTotalScore()))
                                .getBytes(),
                        StandardOpenOption.APPEND);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            displayResults();
        });
        first.start();
    }

    /**
     * @param l
     * @return
     */
    private String getFormatedResult(Long l){
        return (l != Long.MIN_VALUE) ? Long.toString(l) : "-";
    }

    /**
     *
     */
    private void diableControlls(){
        changeDisableStatus(true);
    }

    /**
     *
     */
    private void enableControlls(){
        changeDisableStatus(false);
    }

    /**
     * @param value
     */
    private void changeDisableStatus(boolean value){
        cancelButton.setDisable(!value);
        startBtn.setDisable(value);
        cpu.setDisable(value);
        gpu.setDisable(value);
        ram.setDisable(value);
        disk.setDisable(value);
    }

    /**
     *
     */
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

    /**
     *
     */
    private void resetProgressBar(){
        progressBar.setProgress(0.0);
    }

    /**
     * @param text
     */
    private void increaseProgressAndChangeText(String text){

        double progress = progressBar.getProgress() + progressPick;
        progressBar.setProgress(progress);
        setText(text);
    }

    /**
     *
     */
    private void displayResults(){
        Platform.runLater(() -> {
            Text toShow = new Text(
                    "Name: " + hardwareDetailsManager.getFormattedName() + System.lineSeparator() +
                    "Total score: " +getFormatedResult(ResultController.getTotalScore()) + System.lineSeparator() +
                    "CPU: " + getFormatedResult(ResultController.getCpuScore()) + System.lineSeparator() +
                    "GPU: " + getFormatedResult(ResultController.getGpuScore()) + System.lineSeparator() +
                    "DISK: " + getFormatedResult(ResultController.getDiskScore()) + System.lineSeparator() +
                    "RAM: " + getFormatedResult(ResultController.getRamScore()));


            textFlow.getChildren().clear();
            textFlow.getChildren().add(toShow);
        });
    }

    /**
     * @param text
     */
    private void setText(String text){
        Platform.runLater(() -> workingOn.setText(text));
    }

    /**
     *
     */
    @FXML
    private void closeThread() {
        setText("Cancelling..");
        cancelButton.setDisable(true);
        cpu.setSelected(false);
        gpu.setSelected(false);
        ram.setSelected(false);
        disk.setSelected(false);
        resetProgressBar();
    }

    /**
     *
     */
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
