package com.henry.patentgeneology.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.henry.patentgeneology.Main;

public class UI {

	static int width = 800;
	static int height = 400;

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

		createInputField("Name: ", "name");
		createInputField("Generations: ", "generations");
		createInputField("Initial Patents/Gen: ", "initpatgen");
		createInputField("Patents/Gen proliferation constant: ",
				"patgenprolifconst");
		createInputField("Parents/Patent: ", "parentspat");

		createInputField("Age Effect Strength: ", "ageeffstr");
		createInputField("Age Effect Function Coeff (y = c/x):",
				"ageefffunccoef");

		createInputField("Rich Effect Strength: ", "richeffstr");
		createInputField("Rich Effect Function Coeff (y = c*x):",
				"richafffunccoef");

		createInputField("Color Effect Strength: ", "coloreffstr");
		createInputField("Rich Effect Function Coeff (y = c*x):",
				"colorafffunccoef");

		createSubmitButton("Create Geneology");

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

	}

	public static void createSeperator() {
		// container.add(new JSeparator(), SwingConstants.HORIZONTAL);
	}

	public static void createSubmitButton(String s) {
		JButton b = new JButton();
		b.setText(s);
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					runProgram();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		container.add(b);
	}

	public static void runProgram() throws IOException {
		String name = getStringFromFields("name");
		int gens = getIntFromFields("generations");
		int initpatgen = getIntFromFields("initpatgen");
		int patgenprolifconst = getIntFromFields("patgenprolifconst");
		int parentpat = getIntFromFields("parentspat");

		float aes = getFloatFromFields("ageeffstr");
		float afc = getFloatFromFields("ageefffunccoef");

		float res = getFloatFromFields("richeffstr");
		float rfc = getFloatFromFields("richafffunccoef");

		float ces = getFloatFromFields("coloreffstr");
		float cfc = getFloatFromFields("colorafffunccoef");

		// Main.createGeneology(name, gens, initpatgen, patgenprolifconst,
		//		parentpat, aes, afc, res, rfc, ces, cfc);
	}

	public static String getStringFromFields(String s) {
		return fields.get(s).getText();
	}

	public static int getIntFromFields(String s) {
		return Integer.valueOf(fields.get(s).getText());
	}

	public static float getFloatFromFields(String s) {
		return Float.valueOf(fields.get(s).getText());
	}
}
