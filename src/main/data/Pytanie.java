package main.data;

import java.io.Serializable;

public class Pytanie implements Serializable{
	private int id;
	private int idAnkiety;
	private String pytanie;
	private String odpa;
	private String odpb;
	private String odpc;
	private String odpd;

	public Pytanie(int id, int idAnkiety, String pytanie, String odpa, String odpb, String odpc, String odpd) {
		super();
		this.id = id;
		this.idAnkiety = idAnkiety;
		this.pytanie = pytanie;
		this.odpa = odpa;
		this.odpb = odpb;
		this.odpc = odpc;
		this.odpd = odpd;
	}
	public Pytanie( String pytanie, String odpa, String odpb, String odpc, String odpd) {
		this.pytanie = pytanie;
		this.odpa = odpa;
		this.odpb = odpb;
		this.odpc = odpc;
		this.odpd = odpd;
	}
	public Pytanie(String pytanie, String odpa) {
		this.pytanie = pytanie;
		this.odpa = odpa;
	}

	public Pytanie(Pytanie pyt) {
		this.id = pyt.id;
		this.idAnkiety = pyt.idAnkiety;
		this.pytanie = pyt.pytanie;
		this.odpa = pyt.odpa;
		this.odpb = pyt.odpb;
		this.odpc = pyt.odpc;
		this.odpd = pyt.odpd;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAnkiety() {
		return idAnkiety;
	}

	public void setIdAnkiety(int idAnkiety) {
		this.idAnkiety = idAnkiety;
	}

	public Pytanie(int idAnkiety) {
		this.idAnkiety=idAnkiety;
	}

	public String getPytanie() {
		return pytanie;
	}

	public void setPytanie(String pytanie) {
		this.pytanie = pytanie;
	}

	public String getOdpa() {
		return odpa;
	}
	public String getOdpb() {
		return odpb;
	}
	public String getOdpc() {
		return odpc;
	}
	public String getOdpd() {
		return odpd;
	}

	public void setOdpa(String odpa) {
		this.odpa = odpa;
	}
	public String toString()
	{
		return pytanie +" "+odpa+" "+odpb+" "+odpc+" "+odpd;
	}



}
