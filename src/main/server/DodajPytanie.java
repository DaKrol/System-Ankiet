package main.server;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.data.Pytanie;

public class DodajPytanie implements Callable {

	private int nrPytania;
	private Button end = new Button("Zakoncz");
	private boolean flag;
	private Label infoLabel = new Label("Uzupe³nij pole z pytaniem");
	private TextField pytanie = new TextField();
	private ArrayList<TextField> textOdp = new ArrayList<TextField>(4);
	private Stage stage;

	public DodajPytanie(int liczbaPytan) {
		nrPytania = liczbaPytan;
		stage = new Stage();
		stage.setX(nrPytania * 10);
		stage.setY(nrPytania * 10);
		AnchorPane ap = new AnchorPane();
		Scene scene = new Scene(ap);
		stage.setTitle("Dodaj pytanie " + (nrPytania));
		stage.setWidth(450);
		stage.setHeight(500);
		Label lab = new Label("Treœæ pytania");
		pytanie.setLayoutX(150);
		pytanie.setLayoutY(70);
		lab.setLayoutX(190);
		lab.setLayoutY(30);
		ArrayList<Label> odp = new ArrayList<Label>();

		odp.add(0, new Label("Odpowiedz A"));
		odp.add(1, new Label("Odpowiedz B"));
		odp.add(2, new Label("Odpowiedz C"));
		odp.add(3, new Label("Odpowiedz D"));

		ap.getChildren().add(pytanie);
		for (int i = 0; i < 4; i++) {
			textOdp.add(new TextField());
			textOdp.get(i).setLayoutX(150);
			textOdp.get(i).setLayoutY((i + 1) * 50 + 90);
			odp.get(i).setLayoutX(190);
			odp.get(i).setLayoutY((i + 1) * 50 + 70);
			ap.getChildren().add(odp.get(i));
			ap.getChildren().add(textOdp.get(i));
		}
		end.setLayoutX(190);
		end.setLayoutY(330);
		infoLabel.setLayoutX(150);
		infoLabel.setLayoutY(370);
		infoLabel.setVisible(false);
		ap.getChildren().add(end);
		ap.getChildren().add(lab);
		stage.setScene(scene);
		stage.show();
		ap.getChildren().add(infoLabel);

		zakoncz();

	}

	@Override
	public Pytanie call() throws Exception {
		return new Pytanie(pytanie.getText(), textOdp.get(0).getText(), textOdp.get(1).getText(),
				textOdp.get(2).getText(), textOdp.get(3).getText());
	}

	public void zakoncz() {
		end.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (pytanie.getText().trim().isEmpty()) {
					infoLabel.setVisible(true);
				} else {
					stage.close();
					infoLabel.setVisible(false);
				}
			}
		});
	}

}
