package laboration12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class CalcControllerB implements CalcController {

	private Socket socket;
	private String ipAddress;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private int port;
	private CalcUI gui;

	public CalcControllerB() {
		ipAddress = "localhost";
		port = 3451;

		try {
			socket = new Socket(ipAddress, port);
			inputStream = new DataInputStream(socket.getInputStream());
			outputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Listener().start();
	}

	public void setGUI(CalcUI gui) {
		this.gui = gui;
	}

	public void newCalculation(String nbr1, String nbr2, String operator) {

		try {
			outputStream.writeUTF(nbr1 + "," + nbr2 + "," + operator);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class Listener extends Thread {
		public void run() {
			String response;
			try {
				while (true) {
					response = inputStream.readUTF();
					gui.setResult(response);
				}
			} catch (IOException e) {}
			try{
				socket.close();
			} catch(IOException e){}
			gui.setResult("Disconneting from server");
		}
	}
}
