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
				 int[] n = new int[100_000_000];
				 for (int i = 0; i<n.length; i++) {
                     n[i] = rand.nextInt(Integer.MAX_VALUE);
                 }
                 Timer t = new Timer();
				 CPU.measure(n, CPU::dzielInt);
                 StartBtn.setText(String.valueOf(t.check()/n.length));
			    }
		});
	}
}
