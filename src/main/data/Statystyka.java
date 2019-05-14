package main.data;

import java.io.Serializable;

public class Statystyka implements Serializable {
	private String nazwaAnkiety;
	private int idPytania;
	private int ileOdpa;
	private int ileOdpb;
	private int ileOdpc;
	
	public Statystyka(String nazwaAnkiety, int idPytania, int ileOdpa, int ileOdpb, int ileOdpc, int ileOdpd) {
		super();
		this.nazwaAnkiety = nazwaAnkiety;
		this.idPytania = idPytania;
		this.ileOdpa = ileOdpa;
		this.ileOdpb = ileOdpb;
		this.ileOdpc = ileOdpc;
		this.ileOdpd = ileOdpd;
	}
	public Statystyka(String nazwaAnkiety) {
		this.nazwaAnkiety = nazwaAnkiety;
	}
	public Statystyka(Statystyka obj) {
		this.nazwaAnkiety = obj.nazwaAnkiety;
		this.idPytania = obj.idPytania;
		this.ileOdpa = obj.ileOdpa;
		this.ileOdpb = obj.ileOdpb;
		this.ileOdpc = obj.ileOdpc;
		this.ileOdpd = obj.ileOdpd;
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
	public int getIleOdpa() {
		return ileOdpa;
	}
	public void setIleOdpa(int ileOdpa) {
		this.ileOdpa = ileOdpa;
	}
	public int getIleOdpb() {
		return ileOdpb;
	}
	public void setIleOdpb(int ileOdpb) {
		this.ileOdpb = ileOdpb;
	}
	public int getIleOdpc() {
		return ileOdpc;
	}
	public void setIleOdpc(int ileOdpc) {
		this.ileOdpc = ileOdpc;
	}
	public int getIleOdpd() {
		return ileOdpd;
	}
	public void setIleOdpd(int ileOdpd) {
		this.ileOdpd = ileOdpd;
	}
	private int ileOdpd;
	
	
}
