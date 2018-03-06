package signalFlowgraph;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InterfaceGUI {
	public JFrame frame;
	private JLabel label;
	private JButton button;
	private JTextField textfield;
	int numberOfNode = 0;
	String node;
	int flag = 0;

	public InterfaceGUI() {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(500, 500);
		frame.setTitle("Signal Flow Graph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		label = new JLabel("Enter number of nodes : ");
		frame.add(label);
		textfield = new JTextField(15);
		frame.add(textfield);
		button = new JButton("Enter");
		frame.add(button);
		event e = new event();
		button.addActionListener(e);
	}

	public class event implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (textfield.getText().equals("")) {
				JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel,
						"Invalid (Enter number of node)", "Error",
						JOptionPane.ERROR_MESSAGE);
				textfield.setText("");
			}
			flag = 0;
			node = textfield.getText();
			isInteger(node);
			if(flag == 0){
				JPanel panel = new JPanel();
				JOptionPane.showMessageDialog(panel,
						"Invalid (Enter integer number of node)", "Error",
						JOptionPane.ERROR_MESSAGE);
				textfield.setText("");
			}
			else {
				node = textfield.getText();
				numberOfNode = Integer.parseInt(node);
				if (numberOfNode < 1) {
					JPanel panel = new JPanel();
					JOptionPane.showMessageDialog(panel,
							"Invalid (Enter positive number of node)", "Error",
							JOptionPane.ERROR_MESSAGE);
					textfield.setText("");
				} else {
					InterfaceSFG gui = new InterfaceSFG(numberOfNode);
					frame.dispose();
				}
			}
		}
	}

	public boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			flag = 1;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
