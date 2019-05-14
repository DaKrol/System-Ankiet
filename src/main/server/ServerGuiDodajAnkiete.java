package main.server;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.client.ClientGui;
import main.data.Ankieta;
import main.data.Pytania;
import main.data.Pytanie;

public class ServerGuiDodajAnkiete {

	private Stage stg;
	private Scene scene;
	private AnchorPane previous;
	private Button cofnij;
	private Button potwierdz;
	private String nazwaAnkiety;
	private Button dodajPytanie;
	private int liczbaPytan = 0;
	private Label infoLabel;
	private Button koniecPytan;
	private Pane pane;
	
	Stage stage = new Stage();
	ArrayList<DodajPytanie> pytaniaCallable = new ArrayList<DodajPytanie>();
	ArrayList<FutureTask<Pytanie>> pytania = new ArrayList<FutureTask<Pytanie>>();

	public void initialize() {
	
		ScrollPane sp;
		Label nazwa = new Label();
		int y = 0;
		pane = new Pane();
		pane.setMaxHeight(1000);
		pane.setMinHeight(1000);
		pane.setMinWidth(500);
		sp = new ScrollPane(pane);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		scene = new Scene(sp);
		stg.setScene(scene);

		nazwa.setLayoutX(150);
		nazwa.setLayoutY(30);
		nazwa.setText(nazwaAnkiety);

		dodajPytanie = new Button("Dodaj pytanie");
		dodajPytanie.setLayoutX(140);
		dodajPytanie.setLayoutY(90);

		cofnij = new Button("Cofnij");
		cofnij.setLayoutX(30);
		cofnij.setLayoutY(120);

		potwierdz = new Button("Zakoñcz dodawanie ankiety");
		potwierdz.setLayoutX(300);
		potwierdz.setLayoutY(120);

		infoLabel = new Label("Zanim zakoñczysz dodaj pytanie!");
		infoLabel.setLayoutX(120);
		infoLabel.setLayoutY(180);
		infoLabel.setVisible(false);

		pane.getChildren().add(infoLabel);
		pane.getChildren().add(nazwa);
		pane.getChildren().add(cofnij);
		pane.getChildren().add(dodajPytanie);
		pane.getChildren().add(potwierdz);

		koniecPytan = new Button("Zakoncz dodawanie wszystkich pytan");
		
		stage.setX(500);
		stage.setY(70);
		stage.setResizable(false);
		stage.setScene(new Scene(koniecPytan));
		stage.show();
		dodajPytanie();
		zakoncz();
		koniecDodawaniaPytan();
		cofnij();

	}

	public void dodajPytanie() {
		dodajPytanie.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					stage.show();
					pytaniaCallable.add(new DodajPytanie(++liczbaPytan));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	public void koniecDodawaniaPytan() {
		koniecPytan.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (liczbaPytan == 0) {
					infoLabel.setVisible(true);
				} else {
					infoLabel.setVisible(false);

					ExecutorService exec = Executors.newFixedThreadPool(20);

					Label lab = new Label();
					lab.setLayoutX(150);
					lab.setLayoutY(150);
					lab.setText("Lista pytañ: ");
					pane.getChildren().add(lab);

					for (int i = 0; i < pytaniaCallable.size(); i++) {
						pytania.add(new FutureTask<Pytanie>(pytaniaCallable.get(i)));
						exec.submit(pytania.get(i));
					}
					for (int i = 0; i < pytania.size(); i++) {
						Label l = new Label();
						
						try {
							l.setText("Pytanie " + (i + 1) + " " + pytania.get(i).get().toString());
						} catch (InterruptedException | ExecutionException e1) {
							e1.printStackTrace();
						}
						l.setLayoutX(110);
						l.setLayoutY((i + 1) * 30 + 150);
						pane.getChildren().add(l);
					}
					dodajPytanie.setVisible(false);

					stage.close();
				}

			}
		});
	}

	public void zakoncz() {
		potwierdz.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (liczbaPytan == 0) {
					infoLabel.setVisible(true);
				} else {
					infoLabel.setVisible(false);

				Integer id = Integer.parseInt(JDBC.getString("Select max(id) from ankieta")) + 1;
				Ankieta a = new Ankieta(id,nazwaAnkiety);
				JDBC.zapiszAnkiete(a);
				Integer idPytania = Integer.parseInt(JDBC.getString("Select max(id) from pytanie")) + 1;
				String pytanie = null, odpa = null, odpb = null, odpc = null, odpd = null;
				Pytania p = new Pytania();
				for (int i = 0; i < pytania.size(); i++) {
					try {
							pytanie = pytania.get(i).get().getPytanie();
							odpa = pytania.get(i).get().getOdpa();
							odpb = pytania.get(i).get().getOdpb();
							odpc = pytania.get(i).get().getOdpc();
							odpd = pytania.get(i).get().getOdpd();
						
					} catch (InterruptedException | ExecutionException e1) {
						e1.printStackTrace();
					}
					
					if (pytanie.length() !=0 && odpa.length()!=0 || odpb.length()!=0 ||odpc.length()!=0||odpd.length()!=0) {
					p.add(new Pytanie(idPytania++, id, pytanie, odpa, odpb, odpc, odpd));
					}
				}
				JDBC.zapiszPytania(p);
				}
			}
		});
	}

	public void cofnij() {
		cofnij.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {	
				ServerGui sg = new ServerGui();
				
				sg.setScene(scene);
				sg.setStage(stg);
				sg.initialize();
			}
		});
	}

	public void setStage(Stage stg) {
		this.stg = stg;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setPrevious(AnchorPane wybor) {
		this.previous = wybor;
	}

	public void setAnkieta(String value) {
		nazwaAnkiety = value;
	}

}
