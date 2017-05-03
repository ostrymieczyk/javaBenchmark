package Controller;

import Test.CPU.*;
import Test.GPU.Window;
import Test.HardDrive.MeasureIOPerformance;
import Test.RAM.TestMemoryAccessPatterns;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by robert.ostaszewski on 28.12.2016.
 */
public class TestTabController{

    @FXML
    private Button StartBtn;

    public void setStartBtn() throws InterruptedException {
        Thread first = new Thread(){
            @Override
            public void run() {
                super.run();
                Window cube = new Window();
                cube.addCube(0f, 0f, 0f);
                try {
                    cube.addCubes();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CompressTest.warmupAndTest(5, 10);
                DataEncryptior.warmupAndTest(20, 140);

                LongTest.measureAll(50, 300, 1_250_000);
                IntTest.measureAll(100, 300, 2_500_000);
                DoubleTest.measureAll(25, 200, 1_250_000);



                Quicksort.warmupAndTest(10,40);
                PrimeNumberTest.warmupAndTest(5,10);
            }
        };
        first.start();
        first.join();
        Thread second = new Thread(new MeasureIOPerformance());
        second.start();
        second.join();
        Thread third = new Thread(new TestMemoryAccessPatterns());
        third.start();
        third.join();
    }

}
