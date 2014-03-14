package homeAutomation;

import java.util.Date;

/**
 * Home Automation Protocol -
 * 
 * @author Dott. Ing. Carlo Amaglio - BMB S.p.A. - Via Enrico Roselli, 12 - 25125 Brescia
 *         <p>
 * 
 */
public class HomeAutomationProtocol {

	private static final int WAITING = 0;
	private static final int SENTCONNECT = 1;
	private static final int CONNECTED = 2;

	private int state = WAITING;

	public String processInput(String theInput) {
		String theOutput = null;

		if (state == WAITING) {
			theOutput = "Connect";
			state = SENTCONNECT;
		} else if (state == SENTCONNECT) {
			if (theInput.equalsIgnoreCase("carlo")) {
				theOutput = "> ";
				state = CONNECTED;
			} else {
				theOutput = "Huh!";
			}
		} else if (state == CONNECTED) {
			if (theInput.equals("date")) {
				theOutput = new Date().toString();
			} else if (theInput.equals(":q")) {
				theOutput = "Bye.";
				state = WAITING;
			} else {
				theOutput = "Boh!";
			}
		}
		return theOutput;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {}
}
