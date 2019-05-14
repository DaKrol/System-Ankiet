package main.data;

import java.io.Serializable;

public class Ankieta implements Serializable {
	private int id;
	private String nazwa;
	public Ankieta(int id, String nazwa) {
		this.id=id;
		this.nazwa=nazwa;
	}
	
	@Override
	public String toString() {
		return "Ankieta " + id + " " + nazwa;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNazwa() {
		return nazwa;
	}
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	
}
