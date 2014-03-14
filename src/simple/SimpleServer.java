package simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SimpleServer {

	public static void main(String[] args) throws Exception {
		ServerSocket server = null;
		try {
			// apertura del socket server
			server = new ServerSocket(12345);

			// dichiarazione del socket client e del buffer di ingresso
			Socket client;
			BufferedReader in;
			PrintWriter out;

			System.out.println("Server ready (CTRL-C quits)\n");

			// chiamata bloccante, in attesa di connessione da parte di un client
			client = server.accept();
			System.out.println("Client connected: " + client);

			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

			// ciclo infinito
			String line = null;
			while (line==null || !line.equals(":q!")) {
				// lettura e stampa del messaggio in ingresso
				line = in.readLine();
				System.out.println(">> " + client.getInetAddress() + ": " + line + "\n");
				String answer;
				if (line.equals("date")) {
					answer = new Date().toString();
				} else if (line.equals("time")) {
					answer = new Date().toString();
				} else if (line.equals(":q!")) {
					answer = "quitting.";
				} else {
					answer = "boh!";
				}
				System.out.println(answer);
				out.println(answer);
			}

			// chiusura del client socket
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server!=null) server.close();
		}
	}
}
