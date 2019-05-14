package main.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.data.Ankiety;

public class ClientGui {
	

	private ComboBox<String> cb;
	private Button wybierzAnkiete;
	private AnchorPane wybor;
	private Scene scene;
	private Stage stg;
	private Client client;

	public void initialize() {
		wybor = new AnchorPane();
		scene = new Scene(wybor);
		stg.setScene(scene);
		cb = new ComboBox<String>();

		Ankiety ank = client.getAnkiety();

		for (int i = 0; i < ank.size(); i++) {
			cb.getItems().add(ank.get(i).getNazwa());
		}
		wybierzAnkiete = new Button("Wybór ankiety");
		
	
		wybierzAnkiete.setLayoutX(220);
		wybierzAnkiete.setLayoutY(110);
		cb.setLayoutX(100);
		cb.setLayoutY(30);
		
		wybor.getChildren().add(wybierzAnkiete);
		wybor.getChildren().add(cb);
		wybierz();
	
	}

	public void wybierz() {
		wybierzAnkiete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (cb.getValue() != null) {
					ClientGuiAnkieta cga = new ClientGuiAnkieta();
					cga.setAnkieta(cb.getValue());
					cga.setScene(scene);
					cga.setStage(stg);
					cga.setPrevious(wybor);
					cga.setClient(client);
					cga.initialize();
				} else {
					Label l = new Label("Wybierz ankiete!");
					l.setLayoutX(223);
					l.setLayoutY(155);
					wybor.getChildren().add(l);
				}
			}
		});
	}

	


	public AnchorPane getPane() {
		return wybor;
	}

	public void setPane(AnchorPane pane) {
		this.wybor = pane;
	}

	public Button getWybierzAnkiete() {
		return wybierzAnkiete;
	}

	public void setWybierzAnkiete(Button wybierzAnkiete) {
		this.wybierzAnkiete = wybierzAnkiete;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setStage(Stage stg) {
		this.stg = stg;

	}

	public void setClient(Client client) {
		this.client = client;
	}

}
