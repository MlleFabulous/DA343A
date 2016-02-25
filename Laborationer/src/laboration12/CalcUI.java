package laboration12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalcUI extends JPanel implements ActionListener {
	private JButton btnCalculate = new JButton("Beräkna");
	private JLabel lblTitle = new JLabel("Inmatning av matematiska uttryck");
	private JTextArea taResult = new JTextArea("");
	private JLabel lblOp1 = new JLabel("Operand 1:");
	private JLabel lblOp2 = new JLabel("Operand 2:");
	private JTextField tfNbr1 = new JTextField();
	private JTextField tfNbr2 = new JTextField();
	private JRadioButton rbAdd = new JRadioButton("+");
	private JRadioButton rbSub = new JRadioButton("-");
	private JRadioButton rbMul = new JRadioButton("*");
	private JRadioButton rbDiv = new JRadioButton("/");
	private ButtonGroup group = new ButtonGroup();
	private CalcController controller;

	/** Creates new form UserInput */
	public CalcUI(CalcController controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		controller.setGUI(this);

		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
		taResult.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		taResult.setPreferredSize(new Dimension(400, 40));
		btnCalculate.addActionListener(this);

		add(lblTitle, BorderLayout.NORTH);
		add(operationPanel(), BorderLayout.EAST);
		add(centerPanel(), BorderLayout.CENTER);
		add(taResult, BorderLayout.SOUTH);
	}

	private JPanel centerPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(operandPanel(), BorderLayout.CENTER);
		panel.add(btnCalculate, BorderLayout.SOUTH);
		return panel;
	}

	private JPanel operandPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Operander"));
		tfNbr1.setHorizontalAlignment(JTextField.RIGHT);
		tfNbr1.setText("0");
		tfNbr2.setHorizontalAlignment(JTextField.RIGHT);
		tfNbr2.setText("0");
		panel.add(lblOp1);
		panel.add(tfNbr1);
		panel.add(lblOp2);
		panel.add(tfNbr2);
		return panel;
	}

	private JPanel operationPanel() {
		JPanel panel = new JPanel(new GridLayout(4, 1));
		panel.setPreferredSize(new Dimension(60, 50));
		panel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
		rbAdd.setSelected(true);
		group.add(rbAdd);
		group.add(rbSub);
		group.add(rbMul);
		group.add(rbDiv);
		panel.add(rbAdd);
		panel.add(rbSub);
		panel.add(rbMul);
		panel.add(rbDiv);
		return panel;
	}

	public void actionPerformed(ActionEvent e) {
		String operation;
		if (rbAdd.isSelected()) {
			operation = "+";
		} else if (rbSub.isSelected()) {
			operation = "-";
		} else if (rbMul.isSelected()) {
			operation = "*";
		} else {
			operation = "/";
		}
		controller.newCalculation(tfNbr1.getText(), tfNbr2.getText(), operation);
	}

	public void setResult(String result) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				taResult.setText(result);
			}
		});
	}

	public static void main(String[] args) {
		// CalcUI ui = new CalcUI(new CalcControllerA());
//		CalcUI ui = new CalcUI(new CalcControllerB());
//		CalcUI ui = new CalcUI(new CalcControllerC());
		System.out.println(1);
		CalcUI ui = new CalcUI(new CalcControllerD());
		ui.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		System.out.println(2);
		JOptionPane.showMessageDialog(null, ui);
		System.out.println(3);
	}
}