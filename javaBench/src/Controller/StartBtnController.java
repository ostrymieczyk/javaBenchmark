package Controller;

import Test.CPU.Quicksort;
import Test.CPU.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Random;

public class StartBtnController {
    Random rand = new Random();
	
	@FXML
	private Button StartBtn;
	
	
	public StartBtnController(){
		//setHandler();
	}
	
	public void setHandler(){
		StartBtn.setOnAction(new EventHandler<ActionEvent>(){
			 @Override
			    public void handle(ActionEvent e) {
				    new Thread(){
								@Override
								public void run() {
									super.run();
							IntTest.measureAll(100, 300, 2_500_000);
							LongTest.measureAll(50, 300, 1_250_000);
                            DoubleTest.measureAll(25, 200, 1_250_000);
                            CompressTest.warmupAndTest(5, 10);
                            DataEncryptior.warmupAndTest(20, 140);
                            Quicksort.warmupAndTest(10,40);
                            PrimeNumberTest.warmupAndTest(5,10);
                        }
                    }.start();
			    }
		});
	}
}
