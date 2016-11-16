package Controller;

import Test.CPU;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartBtnController {
	
	@FXML
	private Button StartBtn;
	
	
	public StartBtnController(){
		//setHandler();
	}
	
	public void setHandler(){
		StartBtn.setOnAction(new EventHandler<ActionEvent>(){
			int f = 88;
			 @Override
			    public void handle(ActionEvent e) {
				 	CPU.measure(f);
			    }
		});
	}
}
