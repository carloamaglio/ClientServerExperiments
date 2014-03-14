package simple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SimpleClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// verifica correttezza dei parametri
		if (args.length != 1 && args.length != 2) {
			System.out.println("Usage: java -jar SimpleClient.jar server [\"message to send\"]");
			return;
		}

		try {
			// preparazione dell'indirizzo del server
			InetAddress address = InetAddress.getByName(args[0]);

			// creazione socket
			Socket client = new Socket(address, 12345);

			System.out.println("Client ready.\n");

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			// creazione buffer di scrittura
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

			if (args.length == 2) {
				System.out.println("Buffer ready, sending message \"" + args[1] + "\"...\n");

				// scrittura del messaggio (passato come parametro) nel buffer in uscita
				out.println(args[1]);

				System.out.println("Message sent.\n");
			} else {
				String msg = null;
				BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
				while (msg == null || !msg.startsWith(":q!")) {
					System.out.println("Buffer ready, write the message you want to send (\":q!\" to quit): ");
					msg = console.readLine();

					// scrittura del messaggio nel buffer in uscita
					out.println(msg);
					System.out.println("Message sent.\n");

					String answer = in.readLine();
					System.out.println("answer is: '" + answer + "'");
				}
			}

			// chiusura socket
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
