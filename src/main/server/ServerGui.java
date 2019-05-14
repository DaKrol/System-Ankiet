package main.server;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.client.Client;
import main.client.ClientGui;
import main.data.Ankiety;
import main.data.Pytania;
import main.data.Pytanie;
import main.data.Statystyka;
import main.data.Statystyki;

public class ServerGui {
	private AnchorPane pane;
	private Button wyswietl;
	private Button stop;
	private Scene scene;
	private Stage stg;
	private TextField nazwaAnkiety;
	private Button dodajAnkiete;
	private Button cofnij;
	private Pytania list;
	private int liczbaPytan;
	private Label infoLabel;

	private ComboBox<String> cb;
	
	public void initialize()
	{
		pane = new AnchorPane();
		scene = new Scene(pane);
		stg.setScene(scene);
	
		cofnij = new Button("Cofnij");
		cofnij.setLayoutX(60);
		cofnij.setLayoutY(220);
		
		dodajAnkiete = new Button("Dodaj ankiete");
		dodajAnkiete.setLayoutX(100);
		dodajAnkiete.setLayoutY(250);
		
		nazwaAnkiety = new TextField();
		nazwaAnkiety.setLayoutX(80);
		nazwaAnkiety.setLayoutY(200);
		
		wyswietl = new Button("Wyswietl statystyki");
		wyswietl.setLayoutX(170);
		wyswietl.setLayoutY(100);
		
		infoLabel = new Label("Cos poszlo nie tak");
		infoLabel.setLayoutX(130);
		infoLabel.setLayoutY(140);
		infoLabel.setVisible(false);
		cb = new ComboBox<String>();
		cb.setLayoutX(100);
		cb.setLayoutY(30);
		
		Ankiety ank = JDBC.getAnkiety();

		for (int i = 0; i < ank.size(); i++) {
			cb.getItems().add(ank.get(i).getNazwa());
		}
		
		stop = new Button("Stop serwera");
		stop.setLayoutX(380);
		stop.setLayoutY(250);
		
		pane.getChildren().addAll(nazwaAnkiety,dodajAnkiete,wyswietl,cb,stop,infoLabel);
		
	    dodaj();
	    wyswietlStatystyki();
	    stopServer();
	}
	public void stopServer() {
		stop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				JDBC.closeConnection();
				stg.close();
				System.exit(0);
				Server.Stop();
			}
		});
	}
	
	public void wyswietlStatystyki() {
		wyswietl.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try
				{
				int idAnkiety = Integer.parseInt(JDBC.getString("SELECT ID FROM ANKIETA WHERE nazwaAnkiety LIKE '" +cb.getValue()+"'"));
				liczbaPytan = Integer.parseInt(JDBC.getString("Select max(id) - min(id) + 1 from pytanie where idAnkiety like " + idAnkiety));
				Statystyki s = 	JDBC.getStats(new Statystyka(cb.getValue()));
				for (int j = 0; j < liczbaPytan; j++) {
					final int i = j;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							Stage stage = new Stage();
							stage.setX(i * 10);
							stage.setY(i * 10);
							Scene scene = new Scene(new Group());
							stage.setTitle("Statystyka odpowiedzi pytania nr " + (i + 1));
							stage.setWidth(700);
							stage.setHeight(500);

							ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
							
							list = JDBC.getPytania(new Pytanie(idAnkiety));

							if (!list.get(i).getOdpa().equals("-1")) {
								if (s.get(i).getIleOdpa() != 0)
									pieChartData.add(
											new PieChart.Data(list.get(i).getOdpa(), s.get(i).getIleOdpa()));
							}
							if (!list.get(i).getOdpb().equals("-1")) {
								if (s.get(i).getIleOdpb() != 0)
									pieChartData.add(
											new PieChart.Data(list.get(i).getOdpb(), s.get(i).getIleOdpb()));
							}
							if (!list.get(i).getOdpc().equals("-1")) {

								if (s.get(i).getIleOdpc() != 0)
									pieChartData.add(
											new PieChart.Data(list.get(i).getOdpc(), s.get(i).getIleOdpc()));
							}
							if (!list.get(i).getOdpd().equals("-1")) {
								if (s.get(i).getIleOdpd() != 0)
									pieChartData.add(
											new PieChart.Data(list.get(i).getOdpd(), s.get(i).getIleOdpd()));
							}
							final PieChart chart = new PieChart(pieChartData);
							chart.setTitle(list.get(i).getPytanie());

							((Group) scene.getRoot()).getChildren().add(chart);
							stage.setScene(scene);
							stage.show();
						}
					});
				}

			}catch(Exception ex)
			{
				infoLabel.setVisible(true);
			}
			
			}
		});
	}


	public void dodaj() {
		dodajAnkiete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!nazwaAnkiety.getText().trim().isEmpty()) {
					ServerGuiDodajAnkiete cga = new ServerGuiDodajAnkiete();
					cga.setAnkieta(nazwaAnkiety.getText());
					cga.setScene(scene);
					cga.setStage(stg);
					cga.setPrevious(pane);
					cga.initialize();
				} else {
					Label l = new Label("Wpisz nazwe ankiety!");
					l.setLayoutX(210);
					l.setLayoutY(290);
					pane.getChildren().add(l);
				}
			}
		});
	}
	public AnchorPane getPane() {
		return pane;
	}

	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void setStage(Stage stg) {
		this.stg = stg;

	}




}
