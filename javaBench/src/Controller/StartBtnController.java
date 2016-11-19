package Controller;

import Test.CPU.DoubleTest;
import Test.CPU.IntTest;
import Test.CPU.LongTest;
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
                            DoubleTest.measureAll();
                            LongTest.measureAll();
                            IntTest.measureAll();
                        }
                    }.start();
			    }
		});
	}
}
