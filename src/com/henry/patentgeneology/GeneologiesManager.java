package com.henry.patentgeneology;

import java.io.IOException;
import java.text.DecimalFormat;

import com.henry.patentgeneology.geneology.History;

public class GeneologiesManager {

	int gens;
	int initpatgen;
	int patgenprolifconst;
	int parentpat;
	int colorcount;

	public boolean iterating = false;
	public String iterate_var;
	public float start_strength;
	public float end_strength;
	public float increment;
	public float control1;
	public float control2;

	public GeneologiesManager(int gens, int initpatgen, int patgenprolifconst,
			int parentpat, int colorcount) {
		this.gens = gens;
		this.initpatgen = initpatgen;
		this.patgenprolifconst = patgenprolifconst;
		this.parentpat = parentpat;
		this.colorcount = colorcount;
	}

	// order of variables: age, rich, color
	public void iterateStrength(String iterate_var, float start_strength,
			float end_strength, float increment, float control1, float control2)
			throws IOException {

		iterating = true;

		this.iterate_var = iterate_var;
		this.start_strength = this.format(start_strength);
		this.end_strength = this.format(end_strength);
		this.increment = this.format(increment);
		this.control1 = this.format(control1);
		this.control2 = this.format(control2);

		for (float f = start_strength; this.format(f) <= end_strength; f += increment) {
			if (iterate_var.equalsIgnoreCase("age")) {
				createGeneology(f, control1, control2);
			} else if (iterate_var.equalsIgnoreCase("rich")) {
				createGeneology(control1, f, control2);
			} else if (iterate_var.equalsIgnoreCase("color")) {
				createGeneology(control1, control2, f);
			}
		}
		System.out.println("Finished Iterating Geneologies");
	}

	public void createGeneology(float aes, float res, float ces)
			throws IOException {
		Main.history = new History(this.gens, this.initpatgen,
				this.patgenprolifconst, this.parentpat, this.colorcount, aes,
				res, ces);

		Main.history.generateHistory();

		Main.history.initDOTFile();

		Main.history.outputData();

		Main.history.dotFileManager.endFile();

		System.out
				.println("Created Geneology: " + Main.history.parameters.NAME);
	}

	static DecimalFormat df = new DecimalFormat("#.######");

	private float format(float f) {
		return Float.valueOf(df.format(f));
	}

}
