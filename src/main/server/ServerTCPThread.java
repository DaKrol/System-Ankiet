package main.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import main.data.Ankieta;
import main.data.Ankiety;
import main.data.Odpowiedzi;
import main.data.Pytania;
import main.data.Pytanie;
import main.data.Statystyka;
import main.data.Statystyki;

public class ServerTCPThread extends Thread {
	Socket mySocket;

	public ServerTCPThread(Socket socket) {
		super();
		mySocket = socket;
	}

	public void run() {
		System.out.println("NEW (" + mySocket.getPort() + ") CONNECTED");
		while (true) {
			System.out.println("GET (" + mySocket.getPort() + ") WAITING");
			if (getObject(mySocket)) {
				System.out.println("GET (" + mySocket.getPort() + ") OK");
			} else {
				System.out.println("GET: (" + mySocket.getPort() + ") FALSE, CONNECTION LOST");
				break;
			}
		}
	}

	private boolean getObject(Socket mySocket) {

		ObjectInputStream in;
		Object object = null;

		try {
			in = new ObjectInputStream(mySocket.getInputStream());
			object = in.readObject();

			while (true) {
				if (object instanceof Ankiety) {
					if (sendAnkieta(mySocket)) {
						System.out.println("SEND (" + mySocket.getPort() + ") OK");
					} else {
						System.out.println("SEND (" + mySocket.getPort() + ") FALSE");
					}
					break;
				}
				if (object instanceof Ankieta) {
					if (sendAnkieta(mySocket,object)) {
						System.out.println("SEND (" + mySocket.getPort() + ") OK");
					} else {
						System.out.println("SEND (" + mySocket.getPort() + ") FALSE");
					}
					break;
				}
				if (object instanceof Pytanie) {
					if (sendPytania(mySocket,object)) {
						System.out.println("SEND (" + mySocket.getPort() + ") OK");
					} else {
						System.out.println("SEND (" + mySocket.getPort() + ") FALSE");
					}
					break;
				}
				if (object instanceof Pytania) {
					if (zapiszPytania(mySocket,object)) {
						System.out.println("SEND (" + mySocket.getPort() + ") OK");
					} else {
						System.out.println("SEND (" + mySocket.getPort() + ") FALSE");
					}
					break;
				}
				if (object instanceof Odpowiedzi) {
					if (saveStats(mySocket, object)) {
						System.out.println("SEND (" + mySocket.getPort() + ") OK");
					} else {
						System.out.println("SEND (" + mySocket.getPort() + ") FALSE");
					}
					break;
				}
				if (object instanceof Statystyka) {
					if (sendStats(mySocket, object)) {
						System.out.println("SEND (" + mySocket.getPort() + ") OK");
					} else {
						System.out.println("SEND (" + mySocket.getPort() + ") FALSE");
					}
					break;
				}

				if (object instanceof String) {

					if (sendString(mySocket, object.toString())) {
						System.out.println("SEND (" + mySocket.getPort() + ") OK");
					} else {
						System.out.println("SEND (" + mySocket.getPort() + ") FALSE");
					}
					break;
				}

			}
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return true;

	}

	private boolean zapiszPytania(Socket mySocket2, Object object) {
		JDBC.zapiszPytania((Pytania)object);
		return true;
	}

	private boolean sendAnkieta(Socket mySocket2, Object object) {
		JDBC.zapiszAnkiete((Ankieta) object);
		return true;
	}

	private boolean sendString(Socket mySocket2, String obj) {
		try {
			String u = JDBC.getString(obj);
			ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
			out.writeObject(u);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean sendStats(Socket mySocket2, Object object) {
		try {
			Statystyki u = JDBC.getStats((Statystyka) object);
			ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
			out.writeObject(u);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean saveStats(Socket mySocket2, Object object) {
		JDBC.saveStat((Odpowiedzi) object);
		return true;
	}

	private boolean sendAnkieta(Socket mySocket) {
		try {
			Ankiety u = JDBC.getAnkiety();
			ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
			out.writeObject(u);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean sendPytania(Socket mySocket, Object object) {
		try {
			Pytania u = JDBC.getPytania((Pytanie) object);
			ObjectOutputStream out = new ObjectOutputStream(mySocket.getOutputStream());
			out.writeObject(u);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
