package main.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import java.sql.PreparedStatement;

import main.data.Ankieta;
import main.data.Ankiety;
import main.data.Odpowiedzi;
import main.data.Pytania;
import main.data.Pytanie;
import main.data.Statystyka;
import main.data.Statystyki;

public class JDBC {

	private static Connection con;
	private static Statement st;

	static boolean ladujSterownik() {
		System.out.print("Sprawdzanie sterownika:");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return true;
		} catch (Exception e) {
			System.out.println("Blad przy ladowaniu sterownika bazy!");
			return false;
		}
	}

	public static Connection getConnection(String kindOfDatabase, String adres, int port, String userName,
			String password) {

		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		try {
			conn = DriverManager.getConnection(kindOfDatabase + adres + ":" + port + "/", connectionProps);
		} catch (SQLException e) {
			System.out.println("B³¹d po³¹czenia z baz¹ danych! " + e.getMessage() + ": " + e.getErrorCode());
			System.exit(2);
		}
		System.out.println("Po³¹czenie z baz¹ danych: ... OK");
		return conn;
	}

	private static Statement createStatement() {
		try {
			return con.createStatement();
		} catch (SQLException e) {
			System.out.println("B³¹d createStatement! " + e.getMessage() + ": " + e.getErrorCode());
			System.exit(3);
		}
		return null;
	}

	private static int executeUpdate(String sql) {
		try {
			return st.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
		}
		return -1;
	}

	private static ResultSet executeQuery(String sql) {
		try {
			return st.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Zapytanie nie wykonane! " + e.getMessage() + ": " + e.getErrorCode());
		}
		return null;
	}

	public static void closeConnection() {
		System.out.print("\nZamykanie polaczenia z baz¹:");
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Bl¹d przy zamykaniu pol¹czenia z baz¹! " + e.getMessage() + ": " + e.getErrorCode());
			;
			System.exit(4);
		}
		System.out.print(" zamkniêcie OK");
	}

	public static void dodajAnkiete() {
		String sql;
		ResultSet rs;
		if (executeUpdate(
				"CREATE TABLE ankieta ( id INT NOT NULL, nazwaAnkiety VARCHAR(150) NOT NULL,  PRIMARY KEY (id) );") == 0)
			System.out.println("Tabela utworzona");

		else
			System.out.println("Tabela nie utworzona!");

		sql = "SELECT * FROM ANKIETA";
		rs = executeQuery(sql);

		try {
			if (!rs.next()) {
				sql = "INSERT INTO ankieta VALUES(1,'Ankieta na temat komunikacji miejskiej');";
				executeUpdate(sql);

				sql = "INSERT INTO ankieta VALUES(2,'Ankieta na temat sklepu sportowego na ulicy Pawiej 5');";
				executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (executeUpdate(
				"CREATE TABLE pytanie ( id INT NOT NULL, idAnkiety INT, pytanie VARCHAR(80) NOT NULL,odpa VARCHAR(30) "
						+ ",odpb VARCHAR(30),odpc VARCHAR(30),odpd VARCHAR(30) , PRIMARY KEY (id),"
						+ "    FOREIGN KEY (idAnkiety) REFERENCES ankieta(id)  );") == 0)
			System.out.println("Tabela utworzona");
		else
			System.out.println("Tabela nie utworzona!");

		sql = "SELECT * FROM PYTANIE";
		rs = executeQuery(sql);

		try {
			if (!rs.next()) {
				sql = "INSERT INTO pytanie VALUES(1,1,'Jak czesto korzystasz z komunikacji miejskiej?','codziennie','dwa razy w tygodniu','raz w miesiacu','nigdy');";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(2,1,'Jak w skali 1-4 oceniasz komunikacje (1-zle , 4 -bardzo dobrze)','1','2','3','4');";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(3,1,'Czy czestotliwosc jazdy Twojego autobusu jest zbyt niska?','tak','nie',null,null);";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(4,1,'Czy uwazasz ze autobusy jezdza zgodnie z rozkladem?','tak','nie',null,null);";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(5,1,'Czy uwazasz ze kierowcy jezdza zgodnie z przepisami?','tak','nie',null,null);";
				executeUpdate(sql);
				
				
				sql = "INSERT INTO pytanie VALUES(6,2,'Jak czesto odwiedzasz nasz sklep? ','raz na tydzien','raz na miesiac','rzadziej niz raz na miesiac',null);";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(7,2,'Czy jestes zadowolony z oferty sklepu? ','tak','nie',null,null);";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(8,2,'Czy jestes Pan/Pani zadowolony z dokonanych zakupow? ','tak','nie',null,null);";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(9,2,'Jak oceniasz obsluge sklepu? (1-zle, 4-bardzo dobrze)','1','2','3','4');";
				executeUpdate(sql);
				sql = "INSERT INTO pytanie VALUES(10,2,'Czy wszystkie marki sa dostepne w sklepie?','tak','nie',null,null);";
				executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Ankiety getAnkiety() {
		String sql;
		ResultSet rs;
		sql = "SELECT * FROM ANKIETA";
		rs = executeQuery(sql);
		Ankiety ank = new Ankiety();
		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				String nazwa = rs.getString(2);
				Ankieta a = new Ankieta(id, nazwa);
				ank.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ank;
	}

	public static Pytania getPytania(Pytanie object) {
		String sql;
		ResultSet rs;
		sql = "SELECT * FROM PYTANIE WHERE idAnkiety like " +object.getIdAnkiety();
		rs = executeQuery(sql);
		Pytania pytania = new Pytania();
		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				int idAnkiety = rs.getInt(2);
				String que = rs.getString(3);
				String odpa = rs.getString(4);
				String odpb = rs.getString(5);
				String odpc = rs.getString(6);
				String odpd = rs.getString(7);
				if (odpa == null|| odpa == "") {
					odpa = "-1";
				}
				if (odpb == null || odpb == "") {
					odpb = "-1";
				}
				if (odpc == null || odpc == "") {
					odpc = "-1";
				}
				if (odpd == null || odpd == "") {
					odpd = "-1";
				}
				Pytanie pyt = new Pytanie(id, idAnkiety, que, odpa, odpb, odpc, odpd);
				pytania.add(pyt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pytania;
	}

	public static void polaczBaze(String uzytkownik,String haslo) {
		if (ladujSterownik()) {
			System.out.println("Polaczono z baza");
		} else {
			System.exit(1);
		}

		con = getConnection("jdbc:mysql://", "localhost", 3306, "root", "");
		st = createStatement();

		ResultSet rs;
		String sql;

		if (executeUpdate("USE BAZA_DANYCH_ANKIETYZACJA;") == 0)
			System.out.println("Baza wybrana");
		else {
			System.out.println("Baza nie istnieje! Tworzymy bazê: ");
			if (executeUpdate("create Database BAZA_DANYCH_ANKIETYZACJA;") == 1)
				System.out.println("Baza utworzona");
			else
				System.out.println("Baza nieutworzona!");
			if (executeUpdate("USE BAZA_DANYCH_ANKIETYZACJA;") == 0)
				System.out.println("Baza wybrana");
			else
				System.out.println("Baza niewybrana!");
		}
		dodajAnkiete();
	}

	public static void saveStat(Odpowiedzi s) {
		String sql;
		ResultSet rs;
		int idAnkiety = 0;
		int id;
		if (executeUpdate("CREATE TABLE statystyki (id INT NOT NULL, idAnkiety INT NOT NULL, idPytania INT NOT NULL, "
				+ "udzielonaOdpowiedz VARCHAR(30) NOT NULL, PRIMARY KEY(id) , FOREIGN KEY (idAnkiety) REFERENCES ankieta(id));") == 0)
			System.out.println("Tabela utworzona");
		else
			System.out.println("Tabela nie utworzona!");

		sql = "SELECT id from ANKIETA WHERE nazwaAnkiety LIKE '" + s.get(0).getNazwaAnkiety() + "';";
		rs = executeQuery(sql);
		try {
			if (rs.next()) {
				idAnkiety = rs.getInt(1);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		sql = "SELECT max(id) FROM statystyki";
		rs = executeQuery(sql);
		try {
			if (!rs.next()) {
				id = 0;
			} else {
				id = rs.getInt(1);
			}
			for (int i = 0; i < s.size(); i++) {
				sql = "INSERT INTO statystyki VALUES(" + ++id + "," + idAnkiety + "," + s.get(i).getIdPytania() + ",'"
						+ s.get(i).getWybranaOdp() + "');";
				executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Statystyki getStats(Statystyka obj) {

		String sql;
		ResultSet rs;
		int idAnkiety = 0;
		int ileOdpa = 0, ileOdpb = 0, ileOdpc = 0, ileOdpd = 0;
		int liczbaPytan = 0, min =0;
		sql = "SELECT id from ANKIETA WHERE nazwaAnkiety LIKE '" + obj.getNazwaAnkiety() + "';";
		rs = executeQuery(sql);
		try {
			if (rs.next()) {
				idAnkiety = rs.getInt(1);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		sql = "SELECT max(id), min(id) from pytanie WHERE idAnkiety LIKE " + idAnkiety;
		rs = executeQuery(sql);
		try {
			if (rs.next()) {
				liczbaPytan = rs.getInt(1);
				min = rs.getInt(2);
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String odpa = null, odpb = null, odpc = null, odpd = null;

		Statystyki stats = new Statystyki();
		try {
			for (int i=1; i <= liczbaPytan-min+1; i++ ) {
				sql = "SELECT odpa,odpb,odpc,odpd FROM pytanie where idAnkiety like " + idAnkiety + " and id like " + (min+i-1);
				rs = executeQuery(sql);
				while (rs.next()) {
					odpa = rs.getString(1);
					odpb = rs.getString(2);
					odpc = rs.getString(3);
					odpd = rs.getString(4);
					sql = "SELECT COUNT(ID) FROM statystyki WHERE udzielonaOdpowiedz like '" + odpa
							+ "' and idAnkiety like " + idAnkiety + " and idPytania like " + i;
					rs = executeQuery(sql);
					if (rs.next()) {
						ileOdpa = rs.getInt(1);
					}

					sql = "SELECT COUNT(ID) FROM statystyki WHERE udzielonaOdpowiedz like '" + odpb
							+ "' and idAnkiety like " + idAnkiety + " and idPytania like " + i;
					rs = executeQuery(sql);
					if (rs.next()) {
						ileOdpb = rs.getInt(1);
					}

					sql = "SELECT COUNT(ID) FROM statystyki WHERE udzielonaOdpowiedz like '" + odpc
							+ "' and idAnkiety like " + idAnkiety + " and idPytania like " + i;
					rs = executeQuery(sql);
					if (rs.next()) {
						ileOdpc = rs.getInt(1);
					}

					sql = "SELECT COUNT(ID) FROM statystyki WHERE udzielonaOdpowiedz like '" + odpd
							+ "' and idAnkiety like " + idAnkiety + " and idPytania like " + i;
					rs = executeQuery(sql);
					if (rs.next()) {
						ileOdpd = rs.getInt(1);
					}
					Statystyka stat = new Statystyka(obj.getNazwaAnkiety(), i, ileOdpa, ileOdpb, ileOdpc, ileOdpd);
					stats.add(stat);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stats;
	}

	public static String getString(String obj) {
		String x = "-1";
		ResultSet rs;
		try {
			rs = st.executeQuery(obj);
			if (rs.next() != false) {
				x = rs.getString(1);
			}
		} catch (Exception wyjatek) {
			System.out.println(wyjatek.getMessage());
		}
		return x;
	}

	public static void zapiszAnkiete(Ankieta obj) {
		executeUpdate("INSERT INTO ANKIETA VALUES ("+obj.getId()+ ",'"+ obj.getNazwa() +"');");
	}

	public static void zapiszPytania(Pytania obj) {
		
		int id;
		int idAnkiety;
		String pytanie;
		String odpa;
		String odpb;
		String odpc;
		String odpd;
		PreparedStatement pstmt;
		for(int i=0;i<obj.size();i++)
		{
			id=obj.get(i).getId();
			idAnkiety = obj.get(i).getIdAnkiety();
			pytanie = obj.get(i).getPytanie();
			odpa = obj.get(i).getOdpa();
			odpb = obj.get(i).getOdpb();
			odpc = obj.get(i).getOdpc();
			odpd = obj.get(i).getOdpd();
			executeUpdate("INSERT INTO PYTANIE VALUES ("+id+","+ idAnkiety +",'"+ pytanie +"','" + odpa + "','" + odpb + "','"+ odpc +"','" + odpd + "');");
		
		}
	}

	

}