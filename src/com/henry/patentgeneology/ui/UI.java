package com.henry.patentgeneology.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.henry.patentgeneology.DOTFileManager;
import com.henry.patentgeneology.Main;

public class UI {

	static int width = 470;
	static int height = 550;

	static JPanel container;

	static HashMap<String, JTextField> fields;

	public static void main(String[] args) {

		fields = new HashMap<String, JTextField>();

		JFrame frame = new JFrame();
		frame.setTitle("Patent Geneology Creator");
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		container = new JPanel();
		container.setSize(width, height);
		container.setLayout(new GridLayout(0, 2));

		frame.add(container);

		createLabel("GenManager");
		createInputField("Target Directory: ", "absolutepath");
		createInputField("Generations: ", "gens");
		createInputField("Initial Patents/Gen: ", "ipg");
		createInputField("Pat/Gen Prolif Constant: ", "pgpc");
		createInputField("Parents/Patent: ", "ppp");
		createInputField("Colors Avaliable: ", "colorcount");

		createSeperator();

		createLabel("Single Run");
		createInputField("Age Effect Strength: ", "aes");
		createInputField("Rich Effect Strength: ", "res");
		createInputField("Color Effect Strength: ", "ces");
		createSubmitButtonSingle("Single Geneology");

		createSeperator();

		createLabel("Iterate Run");
		createInputField("Iterate Variable: ", "iterate_var");
		createInputField("Start Strength: ", "start_strength");
		createInputField("End Strength: ", "end_strength");
		createInputField("Increment: ", "increment");
		createInputField("Control Variable 1 Value: ", "control1");
		createInputField("Control Variable 2 Value:", "control2");
		createSubmitButtonIterate("Iterate Geneologies");

		frame.setVisible(true);
	}

	public static void createInputField(String text, String name) {
		JLabel b = new JLabel(text);
		b.setOpaque(true);
		b.setFont(new Font("Arial", Font.PLAIN, 20));

		container.add(b);

		JTextField jtf = new JTextField(20);

		container.add(jtf);

		fields.put(name, jtf);

		if (text.equalsIgnoreCase("Target Directory: ")) {
			jtf.setText(DOTFileManager.my_targetdir);
		}

	}

	static Border labelBorder = BorderFactory.createRaisedBevelBorder();

	public static void createLabel(String text) {
		JLabel b = new JLabel(text);
		b.setOpaque(true);
		b.setFont(new Font("Arial", Font.BOLD, 20));
		b.setBorder(labelBorder);

		container.add(b);

		JLabel c = new JLabel("");
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.PLAIN, 20));

		container.add(c);
	}

	public static void createSeperator() {
		JLabel c = new JLabel("");
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.PLAIN, 20));

		container.add(c);

		JLabel d = new JLabel("");
		d.setOpaque(true);
		d.setFont(new Font("Arial", Font.PLAIN, 20));

		container.add(d);
	}

	public static void createSubmitButtonSingle(String s) {
		JLabel c = new JLabel("");
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.PLAIN, 20));

		container.add(c);

		JButton b = new JButton();
		b.setText(s);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					runSingle();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		container.add(b);
	}

	public static void createSubmitButtonIterate(String s) {
		JLabel c = new JLabel("");
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.PLAIN, 20));

		container.add(c);

		JButton b = new JButton();
		b.setText(s);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					runIterate();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		container.add(b);
	}

	/*
	 * createInputField("Generations: ", "gens");
	 * createInputField("Initial Patents/Gen: ", "ipg");
	 * createInputField("Pat/Gen Prolif Constant: ", "pgpc");
	 * createInputField("Parents/Patent: ", "ppp");
	 */

	public static void runSingle() throws IOException {
		DOTFileManager.outputs_directory = path("absolutepath");

		Main.ui_main_single(i("gens"), i("ipg"), i("pgpc"), i("ppp"),
				i("colorcount"), f("aes"), f("res"), f("ces"));
	}

	public static void runIterate() throws IOException {
		DOTFileManager.outputs_directory = path("absolutepath");

		Main.ui_main_iterate(i("gens"), i("ipg"), i("pgpc"), i("ppp"),
				i("colorcount"), s("iterate_var"), f("start_strength"),
				f("end_strength"), f("increment"), f("control1"), f("control2"));
	}

	public static String s(String s) {
		return fields.get(s).getText();
	}

	public static int i(String s) {
		return Integer.valueOf(fields.get(s).getText());
	}

	public static float f(String s) {
		return Float.valueOf(fields.get(s).getText());
	}

	public static String path(String s) {
		s = String.valueOf(fields.get(s).getText());
		if (s.substring(s.length() - 1, s.length()).equals("/")) {
			return s.substring(0, s.length());
		} else {
			return s;
		}
	}
}
