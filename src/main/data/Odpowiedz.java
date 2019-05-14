package main.data;

import java.io.Serializable;

public class Odpowiedz implements Serializable{

	private String nazwaAnkiety;
	private int idPytania;
	private String wybranaOdp;
	
	public Odpowiedz(String nazwaAnkiety, int idPytania, String wybranaOdp) {
		super();
		this.nazwaAnkiety = nazwaAnkiety;
		this.idPytania = idPytania;
		this.wybranaOdp = wybranaOdp;
	}
	public String getNazwaAnkiety() {
		return nazwaAnkiety;
	}
	public void setNazwaAnkiety(String nazwaAnkiety) {
		this.nazwaAnkiety = nazwaAnkiety;
	}
	public int getIdPytania() {
		return idPytania;
	}
	public void setIdPytania(int idPytania) {
		this.idPytania = idPytania;
	}
	public String getWybranaOdp() {
		return wybranaOdp;
	}
	public void setWybranaOdp(String wybranaOdp) {
		this.wybranaOdp = wybranaOdp;
	}
	
}
