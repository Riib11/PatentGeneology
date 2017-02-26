package com.henry.patentgeneology.ui;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.ui.ApplicationFrame;

import com.henry.patentgeneology.DOTFileManager;
import com.henry.patentgeneology.Main;

public class UI {

	public static ApplicationFrame app;
	public static GridBagConstraints appcon;

	public static int chartNumber = 2;

	static int appwidth = 870;
	static int appheight = 800;

	static int width = 470;
	static int height = 730;

	static int largeFontSize = 20;

	static JPanel container;
	static GridBagConstraints con;

	static HashMap<String, JTextField> fields;

	public static void main(String[] args) {

		app = new ApplicationFrame("Patent Geneology Creator");
		GridBagLayout appgrid = new GridBagLayout();
		appcon = new GridBagConstraints();
		appcon.anchor = GridBagConstraints.LINE_START;
		app.setLayout(appgrid);
		charts = new ArrayList<ChartPanel>();

		fields = new HashMap<String, JTextField>();
		JPanel frame = new JPanel();
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setResizable(false);

		container = new JPanel();
		container.setSize(width, height);
		GridBagLayout gbl = new GridBagLayout();
		con = new GridBagConstraints();
		con.anchor = GridBagConstraints.LINE_START;
		gridy = 0;
		container.setLayout(gbl);

		frame.add(container);

		createLabel("GenManager", "Have a '/' at end of path.");
		createInputField("Target Directory: ", "absolutepath");
		createInputField("Generations: ", "gens");
		createInputField("Initial Patents/Gen: ", "ipg");
		createInputField("Pat/Gen Prolif Constant: ", "pgpc");
		createInputField("Parents/Patent: ", "ppp");
		createInputField("Colors Avaliable: ", "colorcount");

		createSeperator();

		createLabel("Stepping", "Fill in to allow stepping with +/-");
		createInputField("Stepper Custom Name: ", "customname");
		createInputField("Step Size: ", "stepsize");

		createSeperator();

		createLabel("Single Run", "+/- buttons will step.");
		createStepInputField("Age Effect Strength: ", "aes");
		createStepInputField("Rich Effect Strength: ", "res");
		createStepInputField("Color Effect Strength: ", "ces");
		createSubmitButtonSingle("Single Geneology");

		createSeperator();

		createLabel("Iterate Run",
				"Control 1 and 2 are variables not being iterated.");
		createInputField("Iterate Variable: ", "iterate_var");
		createInputField("Start Strength: ", "start_strength");
		createInputField("End Strength: ", "end_strength");
		createInputField("Increment: ", "increment");
		createInputField("Control Variable 1 Value: ", "control1");
		createInputField("Control Variable 2 Value:", "control2");
		createSubmitButtonIterate("Iterate Geneologies");

		frame.setSize(new Dimension(width, height));
		appcon.gridx = 0;
		appcon.gridy = 0;
		appcon.gridheight = chartNumber;
		appcon.anchor = GridBagConstraints.NORTHWEST;
		app.add(frame, appcon);

		app.setDefaultCloseOperation(ApplicationFrame.EXIT_ON_CLOSE);
		app.setSize(new Dimension(appwidth, appheight));
		app.setVisible(true);
		app.setResizable(false);
	}

	static int gridy = 0;

	public static void createInputField(String text, String name) {
		JLabel b = new JLabel(text);
		b.setOpaque(true);
		b.setFont(new Font("Arial", Font.PLAIN, largeFontSize));

		con.gridy = gridy;
		con.gridx = 0;
		// con.anchor = GridBagConstraints.LINE_END;
		container.add(b, con);
		// con.anchor = GridBagConstraints.LINE_START;

		JTextField jtf = new JTextField(5);

		con.gridx = 1;
		container.add(jtf, con);

		fields.put(name, jtf);

		if (text.equalsIgnoreCase("Target Directory: ")) {
			jtf.setText(DOTFileManager.my_targetdir);
		}
		if (text.equalsIgnoreCase("Generations: ")) {
			jtf.setText("10");
		}
		if (text.equalsIgnoreCase("Initial Patents/Gen: ")) {
			jtf.setText("5");
		}
		if (text.equalsIgnoreCase("Pat/Gen Prolif Constant: ")) {
			jtf.setText("1");
		}
		if (text.equalsIgnoreCase("Parents/Patent: ")) {
			jtf.setText("3");
		}
		if (text.equalsIgnoreCase("Colors Avaliable: ")) {
			jtf.setText("2");
		}

		if (text.equalsIgnoreCase("Step Size: ")) {
			jtf.setText("0.5");
		}
		if (text.equalsIgnoreCase("Custom Name: ")) {
			jtf.setText("none");
		}

		gridy++;

	}

	public static void createStepInputField(String text, String name) {
		JLabel b = new JLabel(text);
		b.setOpaque(true);
		b.setFont(new Font("Arial", Font.PLAIN, largeFontSize));

		con.gridy = gridy;
		con.gridx = 0;
		container.add(b, con);

		final JTextField jtf = new JTextField(5);

		JButton minus = new JButton("-");
		minus.setPreferredSize(new Dimension(40, 15));
		minus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fieldMinus(jtf);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		JButton plus = new JButton("+");
		plus.setPreferredSize(new Dimension(40, 15));
		plus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fieldPlus(jtf);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JPanel pan = new JPanel();
		FlowLayout layout = new FlowLayout();
		pan.setLayout(layout);
		layout.setAlignment(FlowLayout.LEFT);
		pan.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		pan.add(jtf);
		pan.add(minus);
		pan.add(plus);

		con.gridx = 1;
		container.add(pan, con);

		fields.put(name, jtf);

		if (text.equalsIgnoreCase("Age Effect Strength: ")) {
			jtf.setText("0");
		}
		if (text.equalsIgnoreCase("Rich Effect Strength: ")) {
			jtf.setText("0");
		}
		if (text.equalsIgnoreCase("Color Effect Strength: ")) {
			jtf.setText("0");
		}

		gridy++;

	}

	static Border labelBorder = BorderFactory.createRaisedBevelBorder();
	static Border noteBorder = BorderFactory
			.createEtchedBorder(EtchedBorder.LOWERED);

	public static void createLabel(String text, String note) {
		JLabel b = new JLabel(text);
		b.setOpaque(true);
		b.setFont(new Font("Arial", Font.BOLD, largeFontSize));
		b.setBorder(labelBorder);

		con.gridy = gridy;
		con.gridx = 0;
		con.fill = GridBagConstraints.BOTH;
		container.add(b, con);

		JLabel c = new JLabel(note);
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.ITALIC, 10));
		c.setBorder(noteBorder);

		con.gridx = 1;
		container.add(c, con);

		con.fill = GridBagConstraints.NONE;

		gridy++;
	}

	public static void createSeperator() {
		JLabel c = new JLabel("");
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.PLAIN, largeFontSize));

		con.gridy = gridy;
		con.gridx = 0;
		con.gridwidth = 2;
		container.add(c, con);

		con.gridwidth = 1;
		//
		// JLabel d = new JLabel("");
		// d.setOpaque(true);
		// d.setFont(new Font("Arial", Font.PLAIN, largeFontSize));
		//
		// container.add(d);

		gridy++;
	}

	public static void createSubmitButtonSingle(String s) {
		JLabel c = new JLabel("");
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.PLAIN, largeFontSize));

		con.gridy = gridy;
		con.gridx = 0;
		container.add(c, con);

		JButton b = new JButton();
		b.setText(s);
		b.setMaximumSize(new Dimension(10, 10));
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					runSingle();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		con.gridx = 1;
		container.add(b, con);

		gridy++;
	}

	public static void createSubmitButtonIterate(String s) {
		JLabel c = new JLabel("");
		c.setOpaque(true);
		c.setFont(new Font("Arial", Font.PLAIN, largeFontSize));

		con.gridy = gridy;
		con.gridx = 0;
		container.add(c, con);

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

		con.gridx = 1;
		container.add(b, con);

		gridy++;
	}

	/*
	 * createInputField("Generations: ", "gens");
	 * createInputField("Initial Patents/Gen: ", "ipg");
	 * createInputField("Pat/Gen Prolif Constant: ", "pgpc");
	 * createInputField("Parents/Patent: ", "ppp");
	 */

	public static void runSingle() throws IOException {
		if (s("customname").equals("")) {

			DOTFileManager.outputs_directory = path("absolutepath");

			Main.ui_main_single(i("gens"), i("ipg"), i("pgpc"), i("ppp"),
					i("colorcount"), f("aes"), f("res"), f("ces"));
		} else {
			runStep();
		}
	}

	public static void runIterate() throws IOException {
		DOTFileManager.outputs_directory = path("absolutepath");

		Main.ui_main_iterate(i("gens"), i("ipg"), i("pgpc"), i("ppp"),
				i("colorcount"), s("iterate_var"), f("start_strength"),
				f("end_strength"), f("increment"), f("control1"), f("control2"));
	}

	public static void runStep() throws IOException {

		DOTFileManager.outputs_directory = path("absolutepath");

		Main.ui_main_single_step(s("customname"), i("gens"), i("ipg"),
				i("pgpc"), i("ppp"), i("colorcount"), f("aes"), f("res"),
				f("ces"));
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
		return s;
		// if (s.substring(s.length() - 1, s.length()).equals("/")) {
		// return s.substring(0, s.length());
		// } else {
		// return s;
		// }
	}

	public static void fieldMinus(JTextField jtf) throws IOException {
		if (!jtf.getText().equals("none")) {
			float current = Float.valueOf(jtf.getText());
			if (current - 0.5f >= 0f) {
				jtf.setText(String.valueOf(current - 0.5f));
				runStep();
			}
		}
	}

	public static void fieldPlus(JTextField jtf) throws IOException {
		if (!jtf.getText().equals("none")) {
			float current = Float.valueOf(jtf.getText());
			float step = f("stepsize");
			if (current + step >= 0f) {
				jtf.setText(String.valueOf(current + step));
				runStep();
			}
		}
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - //

	static ArrayList<ChartPanel> charts;

	static int chartCount = 0;

	public static void setChart(ChartPanel pan) {

		if (chartCount >= chartNumber) {
			// restarting charts, so remove all of them
			for (ChartPanel p : charts) {
				app.remove(p);
			}
			chartCount = 0;
		}

		charts.add(pan);

		appcon.gridheight = 1;
		appcon.gridy = chartCount;
		appcon.gridx = 1;
		app.add(pan, appcon);

		if (chartCount == chartNumber - 1) {
			app.pack();
		}

		chartCount++;
	}
}
