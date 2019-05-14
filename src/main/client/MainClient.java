package main.client;


import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.server.JDBC;
import main.server.Server;

public class MainClient extends Application {
	
	private Button start;	
	private TextField port;
	private ClientGui cg;
	private Stage stage;
	public static void main(String[] args) {
		launch();
	}
	@Override
	public void start(Stage stg) throws Exception {

			
		AnchorPane pane = new AnchorPane();
		Scene scene = new Scene(pane);
		stg.setScene(scene);
		stg.setWidth(250);
		stg.setHeight(200);
		stg.setTitle("System ankiet");
		cg = new ClientGui();
		cg.setPane(pane);
		cg.setScene(scene);
		cg.setStage(stg);
		stg.setResizable(false);
		
		Label por = new Label("Wpisz numer portu");
		
		por.setLayoutY(20);
		por.setLayoutX(70);
		
		port = new TextField("3649");
		port.setLayoutX(50);
		port.setLayoutY(50);
		
		start = new Button("Start");
		start.setLayoutX(100);
		start.setLayoutY(100);
		
		pane.getChildren().addAll(start,port,por);
		stg.show();
		this.stage = stg;
		startClient();
		
	}
	
	public void startClient() {
		start.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Client client = new Client();
		        try {
		            client.Connect(Integer.parseInt(port.getText()));
		        } catch (IOException ex) {
		           System.exit(0);
		        }
				stage.setWidth(600);
				stage.setHeight(400);
		        cg.setClient(client);
				cg.initialize();
				
			}
		});
	}
}
