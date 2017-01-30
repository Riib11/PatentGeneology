package com.henry.patentgeneology;

import java.io.IOException;

import com.henry.patentgeneology.geneology.History;

public class Main {

	public static History history;

	public static void main(String[] args) throws IOException {

		// gens, initpatgen, patgenprolifconst, parentpat, colorcount
		GeneologiesManager genManager = new GeneologiesManager(5, 4, 1, 2, 4);

		genManager.iterateStrength("rich", 0.0f, 100.0f, 25.0f, 0.0f, 0.0f);

	}

}
