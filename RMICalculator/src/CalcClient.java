import java.rmi.*;
import java.rmi.registry.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CalcClient extends JFrame implements ActionListener {

	double n1 = 0.0;
	double n2 = 0.0;
	double result;

	JButton jb[] = new JButton[21];
	JButton btnPlus = new JButton("+");
	JButton btnMin = new JButton("-");
	JButton btnMul = new JButton("*");
	JButton btnDiv = new JButton("/");
	JButton btnClear = new JButton("clear");
	JButton btnComma = new JButton(".");
	JButton btnEqual = new JButton("=");

	JTextField textfield;
	Container con;
	char math_operator;
	String str;
	String num = "";
	JPanel textPanel, buttonPanel; // declaring 2 panels for textfield and
									// buttons
	String pattern = "[-+\\*\\/]";

	public CalcClient() {

		JFrame frame = new JFrame("Calculator");
		Dimension frameD = new Dimension(350, 350);
		frame.setMinimumSize(frameD);

		con = frame.getContentPane();

		textPanel = new JPanel();
		buttonPanel = new JPanel();
		textfield = new JTextField(22);
		textfield.setEditable(false);

		buttonPanel.setLayout(new GridLayout(4, 4));
		textPanel.add(textfield);
		con.add(textPanel, "North"); // placing the textfield in the north
		for (int i = 0; i < 10; i++) // inserting and numbering buttons
		{
			jb[i] = new JButton("" + i);
			jb[i].addActionListener(this);
			buttonPanel.add(jb[i]);

		}
		// Add operation buttons

		buttonPanel.add(btnPlus);
		buttonPanel.add(btnMin);
		buttonPanel.add(btnMul);
		buttonPanel.add(btnDiv);
		buttonPanel.add(btnClear);
		buttonPanel.add(btnEqual);

		// Register listener for operations
		btnPlus.addActionListener(this);
		btnMin.addActionListener(this);
		btnMul.addActionListener(this);
		btnDiv.addActionListener(this);
		btnClear.addActionListener(this);
		btnEqual.addActionListener(this);

		con.add(buttonPanel, "Center"); // placing the panel with the buttons in
										// the center

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent ae) {
		str = ae.getActionCommand(); // get the text on the button
		System.out.println(str);
		for (int i = 0; i < 10; i++) {
			if (ae.getSource() == jb[i]) {
				num = num + str; // if the button clicked is a number,
				textfield.setText(num + " "); // concatenate it to the string
												// “num”
			}
		}

		if ((ae.getSource()) == btnPlus) {
			String button_text = btnPlus.getText();
			getOperator(button_text);

			processOperations();
			num = str + math_operator;
			textfield.setText(num);

		}

		if ((ae.getSource()) == btnMin) {
			String button_text = btnMin.getText();
			getOperator(button_text);

			processOperations();
			num = str + math_operator;
			textfield.setText(num);
		}

		if ((ae.getSource()) == btnMul) {
			String button_text = btnMul.getText();
			getOperator(button_text);
			processOperations();
			num = str + math_operator;
			textfield.setText(num);
		}
		if ((ae.getSource()) == btnDiv) {
			String button_text = btnDiv.getText();
			getOperator(button_text);
			processOperations();
			num = str + math_operator;
			textfield.setText(num);
		}

		if (ae.getSource() == btnEqual) // if the button pressed is “=”
		{
			if (!(textfield.getText().equals(""))) {
				processOperations();

			} else {
				textfield.setText("");
			}
		}
		if (ae.getSource() == btnClear) // if button pressed is “CLEAR”
		{
			textfield.setText("");
			num = "";
			n1 = 0.0;
			n2 = 0.0;
			result = 0;
		}
	}

	private void getOperator(String btnText) {
		math_operator = btnText.charAt(0);
	}

	private void processOperations() {

		boolean goMath = false;
		for (int i = 0; i < num.length(); i++) {
			if (pattern.contains(String.valueOf(num.charAt(i)))) {
				goMath = true;
				break;
			}
		}

		if (goMath) {
			try {
				// set the security manager for the client
				//System.setProperty("java.security.policy", "client.policy");
				//System.setSecurityManager(new SecurityManager());

				String url = "rmi://localhost/calcServer";
				System.out.println("url: " + url);
				CalcInterface remoteObject = (CalcInterface) Naming.lookup(url);
				System.out.println("Got remote object");

				String[] splitString = null;
				String prev_operator = "";
				for (int i = 0; i < num.length(); i++) {
					if (pattern.contains(String.valueOf(num.charAt(i)))) {
						splitString = num.split(pattern); // SPLIT CALL
						prev_operator = String.valueOf(num.charAt(i));
					}
				}

				String part1 = splitString[0];
				String part2 = splitString[1];
				n1 = Double.parseDouble(part1);
				n2 = Double.parseDouble(part2);

				switch (prev_operator) {
				case "+":
					result = remoteObject.add(n1, n2);
					break;
				case "-":
					result = remoteObject.subt(n1, n2);
					break;
				case "/":
					result = remoteObject.div(n1, n2);
					break;
				case "*":
					result = remoteObject.mult(n1, n2);
					break;
				}

				str = String.valueOf(result); // convert the //value to string
				textfield.setText(str); // print the value
			} catch (RemoteException exc) {
				System.out.println("Error in lookup: " + exc.toString());
			} catch (java.net.MalformedURLException exc) {
				System.out.println("Malformed URL: " + exc.toString());
			} catch (java.rmi.NotBoundException exc) {
				System.out.println("NotBound: " + exc.toString());
			}
		} else {
			str = num;
			textfield.setText(str);
		}
	}

	public static void main(String args[]) {
		CalcClient c = new CalcClient();
	}
}
