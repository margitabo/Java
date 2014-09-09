import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MultiThreadRestaurantServer extends JFrame {

	private JTextArea jta = new JTextArea(); // Text area for displaying
												// contents
	private ObjectInputStream inputFromClientObj;
	private ObjectOutputStream out;
	private DataOutputStream toClient;
	private Restaurant restaurant;
	public int clientNo = 1; // Number a client
	private volatile int number = 1;
	private ServerSocket serverSocket;

	public static void main(String[] args) {
		new MultiThreadRestaurantServer();
	}

	public MultiThreadRestaurantServer() {
		// Place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		setTitle("MultiThreadRestaurantServer");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // It is necessary to show the frame here!

		try {
			// Create a server socket
			serverSocket = new ServerSocket(8000);
			jta.append("MultiThreadRestaurantServer started at " + new Date() + '\n');

			restaurant = new Restaurant();
			while (true) {
				Socket socket = serverSocket.accept(); // Listen for a new connection request

				// Create an input stream from the socket
				DataInputStream inputFromClient;
				inputFromClient = new DataInputStream(socket.getInputStream());
				boolean manyClients = inputFromClient.readBoolean();

				if (manyClients) {
					for (int i = 0; i < 2; i++) {

						jta.append("Starting thread for client " + clientNo
								+ " at " + new Date() + '\n'); // Display the
																// client number

						// Find the client's host name and IP address
						InetAddress inetAddress = socket.getInetAddress();
						jta.append("Client " + clientNo + "'s host name is "
								+ inetAddress.getHostName() + "\n");
						jta.append("Client " + clientNo + "'s IP Address is "
								+ inetAddress.getHostAddress() + "\n");

						// Create a new thread for the connection
						HandleMultiClient task1 = new HandleMultiClient(socket);
						// Start the new thread
						new Thread(task1).start();
						++clientNo;
					}
				} else {
					jta.append("Starting thread for client " + clientNo
							+ " at " + new Date() + '\n'); // Display the client
															// number

					// Find the client's host name and IP address
					InetAddress inetAddress = socket.getInetAddress();
					jta.append("Client " + clientNo + "'s host name is "
							+ inetAddress.getHostName() + "\n");
					jta.append("Client " + clientNo + "'s IP Address is "
							+ inetAddress.getHostAddress() + "\n");

					// Create a new thread for the connection
					HandleAClient task = new HandleAClient(socket);
					// Start the new thread
					new Thread(task).start();
					++clientNo;
				}
				// socket.close();
				// serverSocket.close();
				// serverSocket.close();
			}

		} catch (EOFException e) {
			// System.err.println(e);
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	// Inner class
	// Define the thread class for handling new connection
	class HandleAClient extends Thread {
		private Socket socket; // A connected socket

		/** Construct a thread */
		public HandleAClient(Socket socket) {
			super();
			this.socket = socket;
			System.out.println("Receive client ");
		}

		@Override
		// Run a thread
		public void run() {
			try {
				// Create an input stream from the socket
				inputFromClientObj = new ObjectInputStream(
						socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());

				// Continuously serve the client
				while (true) {
					// Read from input
					ClientInfo object = (ClientInfo) inputFromClientObj
							.readObject();

					System.out.println("Client is " + object.getName() + " "
							+ object.getLastName());
					jta.append("Client name is: " + object.getName() + " "
							+ object.getLastName() + '\n');

					boolean success = restaurant.getTable(object);
					try {
						sleep((int) (Math.random() * 10000));
					} catch (InterruptedException e) {
					}
					if (success) {
						try {
							sleep((int) (Math.random() * 5000));
						} catch (InterruptedException e) {
						}
						out.writeUTF("Success!");
						out.flush();
						restaurant.releaseTable(object);

					} else {
						out.writeUTF("Not success!");
						out.flush();
					}
					out.flush();
				}
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			} catch (EOFException e) {
				// System.err.println(e);
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				try {
					//inputFromClientObj.close();
					//out.close();
					//socket.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// Define the thread class for handling new connection for many clients
	class HandleMultiClient extends Thread {

		private Socket socket; // A connected socket

		/** Construct a thread */
		public HandleMultiClient(Socket socket) {
			super();
			System.out.println("Making client ");
			this.socket = socket;
		}

		@Override
		// Run a thread
		public void run() {
			try {
				toClient = new DataOutputStream(socket.getOutputStream());
				ClientInfo object = new ClientInfo(
						(int) (1 + Math.random() * 5), "Client ", "Random");

				object.setName(number);
				++number;
				boolean success = restaurant.getTable(object);

				try {
					sleep((int) (Math.random() * 10000));
				} catch (InterruptedException e) {
				}

				if (success) {
					restaurant.releaseTable(object);
					toClient.writeUTF("Success!");
					toClient.flush();
				} else {
					toClient.writeUTF("Not success!");
					toClient.flush();
				}
			}

			catch (EOFException e) {
				// System.err.println(e);
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				try {
					//socket.close();
					//toClient.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
