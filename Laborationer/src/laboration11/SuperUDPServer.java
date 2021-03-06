package laboration11;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SuperUDPServer implements Runnable {

	private DatagramPacket packet;
	private DatagramSocket socket;
	private Thread server;

	public SuperUDPServer(int requestPort) {
		System.out.println("Requested port: " + requestPort);
		try {
			socket = new DatagramSocket(requestPort);
			server = new Thread(this);
			server.start();

		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		String request, result;
		byte[] response;
		byte[] data = new byte[128];
		String[] inputArray;
		int nbr1, nbr2;
		double calculationResult = 0;
		char operator;
		try {
			while (true) {
				packet = new DatagramPacket(data, data.length);
				socket.receive(packet);
				request = new String(packet.getData(), 0, packet.getLength());

				inputArray = request.split(",");

				nbr1 = Integer.parseInt(inputArray[0]);
				nbr2 = Integer.parseInt(inputArray[1]);

				switch (operator = inputArray[2].charAt(0)) {
				case '+':
					calculationResult = nbr1 + nbr2;
					break;
				case '-':
					calculationResult = nbr1 - nbr2;
					break;
				case '*':
					calculationResult = nbr1 * nbr2;
					break;
				case '/':
					calculationResult = (double) nbr1 / nbr2;
					break;
				}

				// Creates a byte array with the complete result from the
				// calculation
				result = calculationResult + " , " + nbr1 + " " + operator + " " + nbr2;
				response = result.getBytes();
				System.out.println(packet.getPort());
				// Creates a new DatagramPacket and try to send as a response
				packet = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());

				socket.send(packet);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
