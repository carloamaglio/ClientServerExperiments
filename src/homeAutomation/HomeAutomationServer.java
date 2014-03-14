package homeAutomation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Home Automation Server -
 * 
 * @author Dott. Ing. Carlo Amaglio - BMB S.p.A. - Via Enrico Roselli, 12 - 25125 Brescia
 *         <p>
 * 
 */
public class HomeAutomationServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("Usage: java -jar HAServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);

		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

			String inputLine, outputLine;

			// Initiate conversation with client
			HomeAutomationProtocol protocol = new HomeAutomationProtocol();
			outputLine = protocol.processInput(null);
			out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				outputLine = protocol.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals("Bye.")) break;
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
