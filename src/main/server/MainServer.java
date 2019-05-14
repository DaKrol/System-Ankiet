package main.server;

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
import main.client.Client;
import main.client.ClientGui;

public class MainServer extends Application {
		private Button start;		
		private Stage stage;
		private TextField login;
		private TextField haslo;
		private TextField port;
	
		private AnchorPane pane;
		private ServerGui sg;
		private Scene scene;
		public static void main(String[] args) {
			launch();
		}
		@Override
		public void start(Stage stg) throws Exception {
			
			pane = new AnchorPane();
			 scene = new Scene(pane);
			Label log = new Label("Login");
			Label has = new Label("Haslo");
			Label por = new Label("Port");
			stg.setScene(scene);
			stg.setWidth(300);
			stg.setHeight(200);
			stg.setTitle("Server");
			
			log.setLayoutY(10);
			log.setLayoutX(10);
			has.setLayoutY(40);
			has.setLayoutX(10);
			por.setLayoutY(70);
			por.setLayoutX(10);
			
			
			login = new TextField("root");
			login.setLayoutX(100);
			login.setLayoutY(10);
			
			haslo = new TextField("");
			haslo.setLayoutX(100);
			haslo.setLayoutY(40);
			
			port = new TextField("3649");
			port.setLayoutX(100);
			port.setLayoutY(70);
			
			start = new Button("Start serwera");
			start.setLayoutX(40);
			start.setLayoutY(110);
			
		
			
			pane.getChildren().addAll(log,login,port,haslo,start,has,por);
			stg.setResizable(false);

		    startServer();
		
	
		    stg.show();
			this.stage = stg;
			sg = new ServerGui();
			sg.setPane(pane);
			sg.setScene(scene);
			sg.setStage(stg);
		
		}
		
		public void startServer() {
			start.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					JDBC.polaczBaze(login.getText(),haslo.getText());
					new Thread()
					{
					    public void run() {
							Server.Start(Integer.parseInt(port.getText()));
					    }
					}.start();
					stage.setWidth(600);
					stage.setHeight(400);
			    	sg.initialize();
				}
			});
		}
		
		
		public void setScene(Scene scene2) {
			this.scene=scene2;
		}

}
