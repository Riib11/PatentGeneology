package com.henry.patentgeneology;

import java.io.IOException;

import com.henry.patentgeneology.geneology.History;

public class GeneologiesManager {

	int gens;
	int initpatgen;
	int patgenprolifconst;
	int parentpat;
	int colorcount;

	public GeneologiesManager(int gens, int initpatgen, int patgenprolifconst,
			int parentpat, int colorcount) {
		this.gens = gens;
		this.initpatgen = initpatgen;
		this.patgenprolifconst = patgenprolifconst;
		this.parentpat = parentpat;
		this.colorcount = colorcount;
	}

	// order of variables: age, rich, color
	public void iterateStrength(String iteratate_var, float start_strength,
			float end_strength, float increment, float control1, float control2)
			throws IOException {

		for (float f = start_strength; f <= end_strength; f += increment) {
			if (iteratate_var.equalsIgnoreCase("age")) {
				String name = "A" + s(f) + "R" + s(control1) + "C"
						+ s(control2);
				createGeneology(name, f, control1, control2);
			} else if (iteratate_var.equalsIgnoreCase("rich")) {
				String name = "A" + s(control1) + "R" + s(f) + "C"
						+ s(control2);
				createGeneology(name, control1, f, control2);
			} else if (iteratate_var.equalsIgnoreCase("color")) {
				String name = "A" + s(control1) + "R" + s(control2) + "C"
						+ s(f);
				createGeneology(name, control1, control2, f);
			}
		}

	}

	public void createGeneology(String name, float aes, float res, float ces)
			throws IOException {

		Main.history = new History(name, this.gens, this.initpatgen,
				this.patgenprolifconst, this.parentpat, this.colorcount, aes,
				res, ces);
		Main.history.generateHistory();

		Main.history.initDOTFile();

		Main.history.outputData();

		Main.history.dotFileManager.endFile();
	}

	public String s(float f) {
		String s = String.valueOf(f);
		s = s.replace(".", "");
		return s;
	}

}
