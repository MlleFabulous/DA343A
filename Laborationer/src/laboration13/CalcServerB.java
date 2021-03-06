package laboration13;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CalcServerB implements Runnable {
	private Calculator calculator;
	private ServerSocket serverSocket;
	private Thread serverThread;

	public CalcServerB(Calculator calculator, int port) {
		try {
			this.calculator = calculator;
			serverSocket = new ServerSocket(port);
			serverThread = new Thread(this);
			serverThread.start();
		} catch (IOException e) {
		}
	}

	public void run() {
		System.out.println("Server B started");

		// creates a new thread (ClientListener) for each accepted connection
		// made
		try {
			Socket socket = serverSocket.accept();
			new ClientListener(socket).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// A tread that listens for requests without closing the connection between
	// requests.
	private class ClientListener extends Thread {
		private Socket socket;

		public ClientListener(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			String[] request;
			double nbr1, nbr2, answer;
			char operation;
			String response = "";

			try (DataInputStream inputStream = new DataInputStream(socket.getInputStream());
					DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream())) {

				while (true) {
					request = inputStream.readUTF().split(",");
					if (request.length == 3) {
						try {
							nbr1 = Double.parseDouble(request[0]);
							nbr2 = Double.parseDouble(request[1]);
							operation = request[2].charAt(0);

							answer = calculator.calculate(nbr1, nbr2, operation);
							response = answer + "\n" + request[0] + request[2] + request[1] + "=" + answer;
						} catch (Exception e) {
							response = e.toString() + "\n" + request[0] + request[2] + request[1];
						}
					}

					outputStream.writeUTF(response);
					outputStream.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {
		new CalcServerB(new Calculator(), 3451);
	}
}