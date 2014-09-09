/*****
 * 
 * Exercise 1 : Margita Vasileva Boyadzhieva F.N 71701
 * 
 *****/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ColorSlider extends JFrame implements ActionListener,
		ChangeListener {

	JSlider redSlider = new JSlider(0, 255, 0);
	JTextField redTxt = new JTextField(10);

	JSlider greenSlider = new JSlider(0, 255, 0);
	JTextField greenTxt = new JTextField(10);

	JSlider blueSlider = new JSlider(0, 255, 0);
	JTextField blueTxt = new JTextField(10);

	JButton btnColor = new JButton("Color ");
	JButton btnReset = new JButton("Reset");

	public static void main(String[] args) {
		ColorSlider s = new ColorSlider();
	}

	public ColorSlider() {
		JFrame frame = new JFrame("ColorSlider");
		Dimension frameD = new Dimension(900, 500);
		frame.setMinimumSize(frameD);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container pane = frame.getContentPane();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		// RED Panel
		JPanel redPanel = new JPanel();
		redPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane.add(redPanel);
		redPanel.add(redSlider);
		redSlider.setMajorTickSpacing(10);
		redSlider.setMinorTickSpacing(1);
		redSlider.setPaintTicks(true);
		redSlider.setPaintLabels(true);
		redSlider.addChangeListener(this);
		redSlider.setPreferredSize(new Dimension(700, 80));
		redPanel.add(new JLabel("RED"));
		redPanel.add(redTxt);
		redTxt.setText("0");
		redTxt.setEditable(false);
		redTxt.setHorizontalAlignment(JTextField.RIGHT);

		// GREEN Panel
		JPanel greenPanel = new JPanel();
		greenPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane.add(greenPanel);
		greenPanel.add(greenSlider);
		greenSlider.setMajorTickSpacing(10);
		greenSlider.setMinorTickSpacing(1);
		greenSlider.setPaintTicks(true);
		greenSlider.setPaintLabels(true);
		greenSlider.addChangeListener(this);
		greenSlider.setPreferredSize(new Dimension(700, 80));
		greenPanel.add(new JLabel("GREEN"));
		greenPanel.add(greenTxt);
		greenTxt.setText("0");
		greenTxt.setEditable(false);
		greenTxt.setHorizontalAlignment(JTextField.RIGHT);

		// BLUE Panel
		JPanel bluePanel = new JPanel();
		bluePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane.add(bluePanel);
		bluePanel.add(blueSlider);
		blueSlider.setMajorTickSpacing(10);
		blueSlider.setMinorTickSpacing(1);
		blueSlider.setPaintTicks(true);
		blueSlider.setPaintLabels(true);
		blueSlider.addChangeListener(this);
		blueSlider.setPreferredSize(new Dimension(700, 80));
		bluePanel.add(new JLabel("BLUE"));
		bluePanel.add(blueTxt);
		blueTxt.setText("0");
		blueTxt.setEditable(false);
		blueTxt.setHorizontalAlignment(JTextField.RIGHT);

		// Buttons Panel
		JPanel buttonsPanel = new JPanel();
		pane.add(buttonsPanel);
		buttonsPanel.add(btnColor);
		btnColor.setMinimumSize(new Dimension(100, 150));
		buttonsPanel.add(new JLabel(" "));
		buttonsPanel.add(btnReset);

		// Register listener
		btnColor.addActionListener(this);
		btnReset.addActionListener(this);

		frame.pack();
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnReset) {
			reset();
		}
		if (e.getSource() == btnColor) {
			int redGrade = redSlider.getValue();
			int greenGrade = greenSlider.getValue();
			int blueGrade = blueSlider.getValue();
			btnColor.setBackground(new Color(redGrade, greenGrade, blueGrade));
			btnColor.setText(redGrade + " " + greenGrade + " " + blueGrade);
		}
	}

	public void stateChanged(ChangeEvent e) {
		int redGrade = redSlider.getValue();
		redTxt.setText("" + redGrade);

		int greenGrade = greenSlider.getValue();
		greenTxt.setText("" + greenGrade);

		int blueGrade = blueSlider.getValue();
		blueTxt.setText("" + blueGrade);
	}

	private void reset() {
		redTxt.setText("0");
		redSlider.setValue(0);

		greenTxt.setText("0");
		greenSlider.setValue(0);

		blueTxt.setText("0");
		blueSlider.setValue(0);

		btnColor.setBackground(new Color(255, 255, 255));
		btnColor.setText("Color ");
	}
}