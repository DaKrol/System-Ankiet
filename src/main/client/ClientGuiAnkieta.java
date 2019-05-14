package main.client;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Size;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.data.Odpowiedz;
import main.data.Odpowiedzi;
import main.data.Pytania;
import main.data.Pytanie;
import main.data.Statystyka;
import main.data.Statystyki;

public class ClientGuiAnkieta {

	private Stage stg;
	private Scene scene;
	private Button cofnij;
	private Button potwierdz;
	
	private ScrollPane sp;
	private AnchorPane previous;
	private Pane pane;
	private Label lab;
	private Client client;
	private String nazwaAnkiety;
	private int liczbaPytan;
	private ArrayList<String> odpowiedzi = new ArrayList<String>();

	private ComboBox<String> cb;	
	private Pytania list;
	private Label pytanie;
	private String odpa;
	private String odpb;
	private String odpc;
	private String odpd;


	private int nr = 1;
	
	public void initialize() {
		int idAnkiety;
		pane = new Pane();
		pane.setMaxHeight(1000);
		pane.setMinHeight(1000);
		pane.setMinWidth(500);
		sp = new ScrollPane(pane);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		scene = new Scene(sp);
		stg.setScene(scene);
		idAnkiety = Integer.parseInt(client.getString("SELECT id FROM ANKIETA WHERE nazwaAnkiety LIKE '" + nazwaAnkiety + "';"));

		list = client.getPytania(new Pytanie(idAnkiety));
		liczbaPytan=list.size();
		pytanie = new Label();
		pytanie.setText(list.get(0).getPytanie());
		
		pytanie.setLayoutX(100);
		pytanie.setAlignment(Pos.CENTER);
		pytanie.setLayoutY(30);
		pytanie.setMinWidth(300);
		pytanie.setMaxWidth(400);
		pytanie.setMinHeight(20);
		
		odpa = list.get(0).getOdpa();
		odpb = list.get(0).getOdpb();
		odpc = list.get(0).getOdpc();
		odpd = list.get(0).getOdpd();
		
		cb = new ComboBox<String>();
		
		if(!odpa.equals("-1") && !odpa.equals(""))
		{
			cb.getItems().add(odpa);
		}		
		if(!odpb.equals("-1")&& !odpb.equals(""))
		{
			cb.getItems().add(odpb);
		}		
		if(!odpc.equals("-1")&& !odpc.equals(""))
		{
			cb.getItems().add(odpc);
		}		
		if(!odpd.equals("-1") && !odpd.equals(""))
		{
			cb.getItems().add(odpd);
		}		
		cb.setLayoutX(170);
		cb.setLayoutY(80);
		pane.getChildren().add(cb);
		pane.getChildren().add(pytanie);

		cofnij = new Button("Cofnij");
		cofnij.setLayoutX(60);
		cofnij.setLayoutY(220);

		potwierdz = new Button("Nastêpne pytanie");
		potwierdz.setLayoutX(340);
		potwierdz.setLayoutY(220);

		

		lab = new Label();
		lab.setLayoutX(190);
		lab.setLayoutY(260);
		pane.getChildren().add(cofnij);
		pane.getChildren().add(potwierdz);
		pane.getChildren().add(lab);

		zakoncz();

		cofnij();
	}
	public void cofnij() {
		cofnij.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {	
				ClientGui cg = new ClientGui();
		
				cg.setClient(client);
				cg.setScene(scene);
				cg.setStage(stg);
				cg.initialize();
			}
		});
	}


	public void zakoncz() {
		potwierdz.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {	
				if(cb.getValue() == null)
				{
					lab.setText("Uzupe³nij odpowiedz");
					lab.setVisible(true);
				}
				else if(nr>=liczbaPytan)
				{
					odpowiedzi.add(cb.getValue());
					potwierdz.setVisible(false);
					lab.setText("Zakonczono ankiete");
					lab.setVisible(true);
					Odpowiedzi s = new Odpowiedzi();
					for (int i = 0; i < odpowiedzi.size(); i++) {
						s.add(new Odpowiedz(nazwaAnkiety, i + 1, odpowiedzi.get(i)));
					}
					client.sendStats(s);
				}
				else
				{
					lab.setVisible(false);
					odpowiedzi.add(cb.getValue());
					cb.getItems().clear();
					pytanie.setText(list.get(nr).getPytanie());
					odpa = list.get(nr).getOdpa();
					odpb = list.get(nr).getOdpb();
					odpc = list.get(nr).getOdpc();
					odpd = list.get(nr).getOdpd();
					if(!odpa.equals("-1") && !odpa.equals(""))
					{
						cb.getItems().add(odpa);
					}		
					if(!odpb.equals("-1")&& !odpb.equals(""))
					{
						cb.getItems().add(odpb);
					}		
					if(!odpc.equals("-1")&& !odpc.equals(""))
					{
						cb.getItems().add(odpc);
					}		
					if(!odpd.equals("-1") && !odpd.equals(""))
					{
						cb.getItems().add(odpd);
					}
					nr++;
				}
				
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

	public void setClient(Client client) {
		this.client = client;
	}

	public void setAnkieta(String value) {
		this.nazwaAnkiety = value;
	}

}
