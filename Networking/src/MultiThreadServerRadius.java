import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MultiThreadServerRadius extends JFrame {

	private JTextArea jta = new JTextArea(); // Text area for displaying
												// contents

	public static void main(String[] args) {
		new MultiThreadServerRadius();
	}

	public MultiThreadServerRadius() {
		// Place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		setTitle("MultiThreadServer");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // It is necessary to show the frame here!

		try {
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(8000);
			jta.append("MultiThreadServer started at " + new Date() + '\n');
			
			int clientNo = 1; // Number a client

			while (true) {
				Socket socket = serverSocket.accept();  // Listen for a new connection request
				jta.append("Starting thread for client " + clientNo + " at " + new Date() + '\n'); // Display the client number

				// Find the client's host name and IP address
				InetAddress inetAddress = socket.getInetAddress();
				jta.append("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
				jta.append("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");

				// Create a new thread for the connection
				HandleAClient task = new HandleAClient(socket);
				// Start the new thread
				new Thread(task).start();
				clientNo++;  // Increment clientNo
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	// Inner class
	// Define the thread class for handling new connection
	class HandleAClient implements Runnable {

		private Socket socket; // A connected socket
		
		/** Construct a thread */
		public HandleAClient(Socket socket) {
			super();
			this.socket = socket;
		}

		@Override
		// Run a thread
		public void run() {
			try {
				// Create data input and output streams
				DataInputStream inputFromClient = new DataInputStream(
						socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(
						socket.getOutputStream());
				// Continuously serve the client
				while (true) {
					double radius = inputFromClient.readDouble(); // Receive
																	// radius
																	// from
																	// client
					double area = radius * radius * Math.PI; // Compute area
					outputToClient.writeDouble(area); // Send area back to the client
					jta.append("radius received from client: " + radius + '\n');
					jta.append("Area found: " + area + '\n');
				}
			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}

}
