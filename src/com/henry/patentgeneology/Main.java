package com.henry.patentgeneology;

import java.io.IOException;

import com.henry.patentgeneology.geneology.History;

public class Main {

	public static History history;
	public static GeneologiesManager genManager;

	public static void main(String[] args) throws IOException {

		/*
		 * gens, initpatgen, patgenprolifconst, parentpat, colorcount
		 */
		genManager = new GeneologiesManager(10, 7, 1, 3, 2);

		/*
		 * iteratate_var, start_strength, end_strength, increment, control1,
		 * control2
		 * 
		 * order of variables: age, rich, color
		 */
		genManager.iterateStrength("color", 0.0f, 1.0f, 0.1f, 1000.0f, 0.0f);

		/*
		 * age_strength, rich_strength, color_strength
		 */
		// genManager.createGeneology(1000.0f, 0.0f, 0.7f);

	}

	// main method that the UI calls for single
	public static void ui_main_single(int gens, int initpatgen,
			int patgenprolifconst, int parentpat, int colorcount, float aes,
			float res, float ces) throws IOException {
		genManager = new GeneologiesManager(gens, initpatgen,
				patgenprolifconst, parentpat, colorcount);
		genManager.createGeneology(aes, res, ces);
	}

	// main method that the UI calls for iterate
	public static void ui_main_iterate(int gens, int initpatgen,
			int patgenprolifconst, int parentpat, int colorcount,
			String iterate_var, float start_strength, float end_strength,
			float increment, float control1, float control2) throws IOException {
		genManager = new GeneologiesManager(gens, initpatgen,
				patgenprolifconst, parentpat, colorcount);
		genManager.iterateStrength(iterate_var, start_strength, end_strength,
				increment, control1, control2);
	}

}
