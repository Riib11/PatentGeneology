package com.henry.patentgeneology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class History {

	ArrayList<Generation> generations;
	ArrayList<Patent> patents;

	public History() {
		this.generations = new ArrayList<Generation>();
		this.patents = new ArrayList<Patent>();
	}

	public void generateHistory() {
		Patent.generatePatentColors();
		// repeat for each generation
		for (int i = 0; i < Parameters.GENERATIONS; i++) {
			addGeneration();
		}
	}

	private Patent chooseParentFromFactors(Patent child,
			ArrayList<Patent> parents) {
		float totalFactorPoints = 0;
		ArrayList<Float> factorPoints = new ArrayList<Float>();
		for (Patent p : parents) {

			float fp = p.getFactorPoints(child);

			factorPoints.add(fp + totalFactorPoints);
			totalFactorPoints += fp;
		}

		// Includes 0. Doesn't include max
		float x = randFloat(0.0f, totalFactorPoints);
		while (x == totalFactorPoints) {
			x = randFloat(0.0f, totalFactorPoints);
		}

		for (int i = 0; i < factorPoints.size(); i++) {
			if (x < factorPoints.get(i)) {
				return parents.get(i);
			}
		}

		return null;
	}

	public Patent chooseParent(Patent child) {
		ArrayList<Patent> avaliableParents = new ArrayList<Patent>();
		for (Patent p : this.patents) {
			if (child.isValidParent(p)) {
				avaliableParents.add(p);
			}
		}

		Patent parent = chooseParentFromFactors(child, avaliableParents);

		return parent;
	}

	void addGeneration() {
		this.generations.add(new Generation());
	}

	void addPatent(Patent p) {
		this.patents.add(p);
	}

	ArrayList<Patent> getPatents() {
		return this.patents;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static float randFloat(float min, float max) {
		Random rand = new Random();
		return rand.nextFloat() * (max - min) + min;
	}

	// TODO Make Generate file in graphviz format
	public void outputData() throws IOException {
		if (Parameters.COLORS) {
			for (Patent p : this.patents) {
				Main.dotfile.createColor(p);
			}
		}
		for (Patent p : this.patents) {
			// System.out.print("patent - " + p.getID() + "; children - ");
			for (Patent child : p.getChildren()) {
				// System.out.print(child.getID() + ", ");
				Main.dotfile.createRelation(p, child);
			}
			// System.out.print("end; parents - ");
			// for (Patent parent : p.getParents()) {
			// System.out.print(parent.getID() + ", ");
			// }
			// System.out.println("end");
		}

		// System.out.println();

		System.out.println("Generations: " + this.generations.size());
		System.out.println("Patents: " + this.patents.size());

	}

}
