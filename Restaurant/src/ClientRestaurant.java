import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientRestaurant extends JFrame {

	private JTextField jtf = new JTextField(); // Text field for receiving
												// places to reserve
	private JTextArea jta = new JTextArea(); // Text area to display contents

	private ObjectInputStream fromServerObj;
	private DataOutputStream toData; // IO streams
	//private DataInputStream outData; // IO streams

	private JTextField clientName = new JTextField(32); // Text field for
														// clients name
	private JTextField clientLastName = new JTextField(32); // Text field for
															// clients name
	// Button for sending a reservation to the server
	private JButton buttonTable = new JButton("Take a table");
	private JButton buttonTableMany = new JButton("Generate many clients");
	// Host name or IP address
	String host = "localhost";
	Socket socket;

	public static void main(String[] args) {
		new ClientRestaurant();
	}

	public ClientRestaurant() {
		// Panel p to hold the label and text field
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(5, 2));

		p.add(new JLabel("Enter your first name:"));
		p.add(clientName, BorderLayout.CENTER);
		clientName.setHorizontalAlignment(JTextField.RIGHT);
		p.add(new JLabel("Enter your last name:"));
		p.add(clientLastName, BorderLayout.CENTER);
		clientLastName.setHorizontalAlignment(JTextField.RIGHT);

		p.add(new JLabel("Enter places to reserve the table:"), BorderLayout.WEST);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.RIGHT);

		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);

		p.add(new JLabel(""));
		p.add(new JLabel(""));
		p.add(buttonTable, BorderLayout.CENTER);
		p.add(buttonTableMany, BorderLayout.CENTER);
		// Register listener
		buttonTable.addActionListener(new ButtonListener());
		buttonTableMany.addActionListener(new ButtonListener());

		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);// It is necessary to show the frame here!
	}

	/** Handle button action */
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == buttonTable) {

				try {
					// Establish connection with the server. Create a socket to
					// connect to the server
					Socket socket = new Socket(host, 8000);
					// Create an output stream to the server

					toData = new DataOutputStream(socket.getOutputStream());
					boolean manyClients = false;
					toData.writeBoolean(manyClients);
					toData.flush();

					ObjectOutputStream toServer = new ObjectOutputStream(
							socket.getOutputStream());
					// Get text field
					String name = clientName.getText().trim();
					String lastname = clientLastName.getText().trim();
					int places = Integer.parseInt(jtf.getText().trim());

					// Create a Client object and send it to the server
					ClientInfo s = new ClientInfo(places, name, lastname);
					toServer.writeObject(s);
					toServer.flush();
					// get the reply from the server Create an input stream to
					// receive data from the server
					fromServerObj = new ObjectInputStream(
							socket.getInputStream());
					
					System.out.println("Get reply from the server.");
					String response = fromServerObj.readUTF();
					System.out.println(response);
					jta.append(response + '\n');
					
					/*String outputString;
					  while (((outputString = fromServerObj.readUTF()) != null)) {
						 System.out.println("The total result is: " + outputString + 'n');
					}*/

					// toServer.close();
					// fromServerObj.close();
					//socket.close();

				} catch (IOException ex) {
					System.err.println(ex);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			if (e.getSource() == buttonTableMany) {

				try {

					// Establish connection with the server. Create a socket
					// to connect to the server
					Socket socket = new Socket(host, 8000);
					// Create an output stream to the server
					toData = new DataOutputStream(socket.getOutputStream());
					boolean manyClients = true;
					toData.writeBoolean(manyClients);
					toData.flush();

					// get the reply from the server Create an input stream
					// to receive data from the server
					/*outData = new DataInputStream(
							socket.getInputStream());
					System.out.println("Get reply from the server.");
					String outputString;
					  while (((outputString = outData.readUTF()) != null)) {
						 System.out.println("The total result is: " + outputString);
					}*/
					
					//outData.close();
					// toData.close();
					//socket.close();
				}
				catch (IOException ex) {
					System.err.println(ex);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}

	}
}
