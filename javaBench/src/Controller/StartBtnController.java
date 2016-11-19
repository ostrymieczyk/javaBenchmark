package Controller;

import Helper.Timer;
import Test.CPU;
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
                            CPU.addInt();
                            CPU.subtractInt();
                            CPU.multiplyInt();
                            CPU.divideInt();
                        }
                    }.start();
			    }
		});
	}
}
