package laboration12;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class QuizClient {

	private Socket socket;
	private String ipAddress;
	private int port;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private QuizUI gui;

	public QuizClient(String ipAddress, int port, QuizUI gui) {
		this.ipAddress = ipAddress;
		this.port = port;
		this.gui = gui;
	}

	// Connect to specified ipAddres and port. Create input/output streams from
	// socket and display welcome message. Start the listener thread.
	public void connect() throws IOException {
		socket = new Socket(ipAddress, port);
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream = new DataOutputStream(socket.getOutputStream());
		gui.setText("Connected to: " + ipAddress + "\n" + inputStream.readUTF());
		gui.connected();
		// System.out.println("Connected to: " + ipAddress);
		// System.out.println(inputStream.readUTF());
		new Listener().start();
	}

	// Closes the socket and thereby ending the communication between client and
	// server
	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.disconnected();
	}

	private void request(String request) throws IOException {
		outputStream.writeInt(Integer.parseInt(request));
	}

	public void question(String question) throws IOException {
		request(question);
	}

	public void awnser(String awnser) throws IOException {
		request(awnser);
	}

	// Listener that listens from responses from the server untill the program
	// is terminated or Exception.
	private class Listener extends Thread {
		public void run() {
			try {
				while (true) {
					gui.setText(inputStream.readUTF());
				}
			} catch (Exception e) {
				gui.setText(e.toString());
			}
			try {
				socket.close();
			} catch (Exception e) {
			}
			socket = null;
		}
	}

}
