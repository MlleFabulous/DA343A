package laboration11;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class UDPCalculator extends JPanel implements ActionListener {
	private SuperUDPClient client;
	private JPanel operatorPanel, inputPanel, resultPanel;
	private JRadioButton addBtn, subBtn, mulBtn, divBtn;
	private ButtonGroup operatorChoiceGroup;
	private JTextField nbr1, nbr2;
	private JLabel result;
	private JButton calculate;

	public UDPCalculator(SuperUDPClient client) {
		this.client = client;
		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(450, 100));
		createOperatorPanel();
		createInputPanel();
		createResultPanel();

		add(operatorPanel, BorderLayout.EAST);
		add(inputPanel, BorderLayout.WEST);
		add(resultPanel, BorderLayout.SOUTH);

	}

	private void createResultPanel() {
		resultPanel = new JPanel(new BorderLayout());
		result = new JLabel();

		calculate = new JButton("Calculate");
		calculate.addActionListener(this);

		resultPanel.add(result, BorderLayout.EAST);
		resultPanel.add(calculate, BorderLayout.WEST);

	}

	private void createInputPanel() {
		inputPanel = new JPanel(new GridLayout(2, 2));
		inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
		nbr1 = new JTextField();
		nbr2 = new JTextField();

		nbr1.setHorizontalAlignment(JTextField.RIGHT);
		nbr1.setText("0");
		nbr2.setHorizontalAlignment(JTextField.RIGHT);
		nbr2.setText("0");

		inputPanel.add(new JLabel("First Number: "));
		inputPanel.add(nbr1);
		inputPanel.add(new JLabel("Second Number: "));
		inputPanel.add(nbr2);
	}

	private void createOperatorPanel() {
		operatorPanel = new JPanel();
		operatorPanel.setBorder(BorderFactory.createTitledBorder("Operator"));
		operatorChoiceGroup = new ButtonGroup();
		addBtn = new JRadioButton("+");
		subBtn = new JRadioButton("-");
		mulBtn = new JRadioButton("*");
		divBtn = new JRadioButton("/");

		operatorChoiceGroup.add(addBtn);
		operatorChoiceGroup.add(subBtn);
		operatorChoiceGroup.add(mulBtn);
		operatorChoiceGroup.add(divBtn);

		operatorPanel.add(addBtn);
		operatorPanel.add(subBtn);
		operatorPanel.add(mulBtn);
		operatorPanel.add(divBtn);
	}

	public void setResult(String result) {
		this.result.setText(result);
	}

	public void actionPerformed(ActionEvent e) {
		String operation;

		if (addBtn.isSelected()) {
			operation = "+";
		} else if (subBtn.isSelected()) {
			operation = "-";
		} else if (mulBtn.isSelected()) {
			operation = "*";
		} else {
			operation = "/";
		}
		client.newCalculation(nbr1.getText(), nbr2.getText(), operation);
	}
}
