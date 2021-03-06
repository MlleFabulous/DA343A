package laboration13;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import laboration12.Expression;
import laboration12.Calculation;

public class CalcServerD implements Runnable {
	private Calculator calculator;
	private ServerSocket serverSocket;

	public CalcServerD(Calculator calculator, int port) {
		try {
			this.calculator = calculator;
			serverSocket = new ServerSocket(port);
			new Thread(this).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Server D started");

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
			Expression request;
			double answer;
			Calculation response;

			try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
					ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());) {

				while (true) {
					System.out.println("1");
					request = (Expression) inputStream.readObject();
					System.out.println("2");
					answer = calculator.calculate(request);
					System.out.println("3");
					
					response = new Calculation(answer, request);

					outputStream.writeObject(response);
					outputStream.flush();
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new CalcServerD(new Calculator(), 3453);
	}
}