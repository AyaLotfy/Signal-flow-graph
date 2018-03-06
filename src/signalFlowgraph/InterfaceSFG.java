package signalFlowgraph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfaceSFG {
	private JFrame frame1 = new JFrame("Signal Flow Graph");
	private JPanel panel1 = new JPanel();
	private JLabel label;
	private JButton buttonNext;
	private JButton buttonSolve;
	private JTextField textfieldInput;
	private JTextField textfieldOutput;
	private JTextField textfieldGain;
	String input;
	String output;
	String gain;
	ArrayList<Integer> inputNode = new ArrayList<Integer>();
	ArrayList<Integer> outputNode = new ArrayList<Integer>();
	ArrayList<Double> gainArray = new ArrayList<Double>();
	int nodeNumber = 0;
	Draw object;
	// DrawArrow arrow;
	int flag = 0;
	int flag1 = 0;
	int flag2 = 0;
	int flag3 = 0;

	public InterfaceSFG(int numberOfNode) {
		initialization(numberOfNode);
	}

	public void initialization(int numberOfNode) {

		nodeNumber = numberOfNode;
		object = new Draw(numberOfNode, inputNode, outputNode, gainArray);
		// arrow = new DrawArrow(numberOfNode, inputNode, outputNode,
		// gainArray);
		panel1.setBackground(Color.GREEN);
		label = new JLabel("Enter from node : ");
		panel1.add(label);
		textfieldInput = new JTextField(15);
		panel1.add(textfieldInput);
		label = new JLabel("Enter to node : ");
		panel1.add(label);
		textfieldOutput = new JTextField(15);
		panel1.add(textfieldOutput);
		label = new JLabel("Enter gain : ");
		panel1.add(label);
		textfieldGain = new JTextField(15);
		panel1.add(textfieldGain);
		buttonNext = new JButton("next");
		panel1.add(buttonNext);
		buttonSolve = new JButton("solve");
		panel1.add(buttonSolve);//////////////////////////// Aia
		// event e = new event();
		// buttonNext.addActionListener(e);
		frame1.setSize(1000, 1000);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setVisible(true);
		frame1.add(panel1, BorderLayout.PAGE_START);
		//System.out.println(frame1.getSize());
		frame1.add(object);
		buttonNext.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				input = textfieldInput.getText();
				output = textfieldOutput.getText();
				gain = textfieldGain.getText();
				if ((input.equals("")) || (gain.equals(""))
						|| (output.equals(""))) {
					JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel,
							"Invalid (Enter From Node & To Node & gain)",
							"Error", JOptionPane.ERROR_MESSAGE);
					textfieldInput.setText("");
					textfieldOutput.setText("");
					textfieldGain.setText("");

				}
				flag1 = 0;
				flag2 = 0;
				flag3 = 0;
				isInteger(input, 1);
				isInteger(output, 2);
				isInteger(gain, 3);
				if (flag1 == 1 && flag2 == 1 && flag3 == 1) {
					if (Integer.parseInt(input) < 1
							|| Integer.parseInt(input) > nodeNumber
							|| Integer.parseInt(output) < 1
							|| Integer.parseInt(output) > nodeNumber) {
						JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel,
								"Invalid (value of node should be greater than 0 and less than or equal number of node)",
								"Error", JOptionPane.ERROR_MESSAGE);
						textfieldInput.setText("");
						textfieldOutput.setText("");
						textfieldGain.setText("");
					} else if (Integer.parseInt(gain) == 0) {
						JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel,
								"Invalid (value of gain should be not equal 0 )",
								"Error", JOptionPane.ERROR_MESSAGE);
						textfieldInput.setText("");
						textfieldOutput.setText("");
						textfieldGain.setText("");
					} else if ((Integer.parseInt(input) == nodeNumber)) {
						JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel,
								"Invalid (node " + nodeNumber
										+ " is output node)",
								"Error", JOptionPane.ERROR_MESSAGE);
						textfieldInput.setText("");
						textfieldOutput.setText("");
						textfieldGain.setText("");
					} else if (Integer.parseInt(output) == 1) {
						JPanel panel = new JPanel();
						JOptionPane.showMessageDialog(panel,
								"Invalid (node 1 is input node)", "Error",
								JOptionPane.ERROR_MESSAGE);
						textfieldInput.setText("");
						textfieldOutput.setText("");
						textfieldGain.setText("");
					} else {
						inputNode.add(Integer.parseInt(input));
						textfieldInput.setText("");

						outputNode.add(Integer.parseInt(output));
						textfieldOutput.setText("");

						gainArray.add(Double.parseDouble(gain));
						textfieldGain.setText("");
						flag = 1;
						frame1.repaint();
					}
				} else if (flag1 == 0 || flag2 == 0 || flag3 == 0) {
					JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel,
							"Invalid (Enter numeric node & gain", "Error",
							JOptionPane.ERROR_MESSAGE);
					textfieldInput.setText("");
					textfieldOutput.setText("");
					textfieldGain.setText("");
				}
			}
		});

	}

	public boolean isInteger(String input, int i) {
		try {
			Integer.parseInt(input);
			if (i == 1) {
				flag1 = 1;
			}
			if (i == 2) {
				flag2 = 1;
			}
			if (i == 3) {
				flag3 = 1;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
