package main.client;

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

public class Client {

	Socket mySocket;
	String temp;
	Pytania pytania;
	Ankiety ankiety;
	private Statystyki stat;
	public void Connect(int i) throws IOException {
		int port = i;
		mySocket = new Socket("127.0.0.1", port);
	}

	public String getString(String statment)
	{
		sendObject(new String(statment));
		while (true) {
			Object object = getObject();
			if (object instanceof String) {
				temp = (String) object;
				break;
			}
		}
		return temp;
	}	
	public Ankiety getAnkiety() {
	    sendObject(new Ankiety());

        while (true) {
            Object object = getObject();
            if (object instanceof Ankiety) {
                ankiety = (Ankiety) object;
                break;
            }
        }
        return ankiety;
	}

	public Pytania getPytania(Pytanie pytanie) {
        sendObject(new Pytanie(pytanie));
        while (true) {
            Object object = getObject();
            if (object instanceof Pytania) {
                pytania = (Pytania) object;
                break;
            }
        }
        return pytania;
    }
	public void sendObject(Object object) {
		try {
			ObjectOutputStream out = null;
			out = new ObjectOutputStream(mySocket.getOutputStream());
			mySocket.setTcpNoDelay(true);
			out.writeObject(object);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Object getObject() {
		Object object = null;

		try {
			ObjectInputStream in = new ObjectInputStream(mySocket.getInputStream());
			object = in.readObject();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return object;
	}

	public void sendStats(Odpowiedzi s) {
		try {
			ObjectOutputStream out = null;
			out = new ObjectOutputStream(mySocket.getOutputStream());
			mySocket.setTcpNoDelay(true);
			out.writeObject(s);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Statystyki getStatystyki(Statystyka obj) {
		sendObject(new Statystyka(obj));
        while (true) {
            Object object = getObject();
            if (object instanceof Statystyki) {
                stat = (Statystyki) object;
                break;
            }
        }
        return stat;
	}

	public void zapiszAnkiete(Ankieta a) {
		try {
			ObjectOutputStream out = null;
			out = new ObjectOutputStream(mySocket.getOutputStream());
			mySocket.setTcpNoDelay(true);
			out.writeObject(a);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void zapiszPytania(Pytania pytania) {
		try {
			ObjectOutputStream out = null;
			out = new ObjectOutputStream(mySocket.getOutputStream());
			mySocket.setTcpNoDelay(true);
			out.writeObject(pytania);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}



}
