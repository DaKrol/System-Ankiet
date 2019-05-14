package main.server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static ServerSocket serverSocket;
    public static void Start(int i) {
        int port = i;
         serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Port Serwera = " + port + "\n");
            while (true) {
                Socket socket = serverSocket.accept();
                (new ServerTCPThread(socket)).start();
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (serverSocket != null)
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    public static void Stop()
    {
    	
    	try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}