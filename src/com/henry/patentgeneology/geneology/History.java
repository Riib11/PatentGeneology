package com.henry.patentgeneology.geneology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.henry.patentgeneology.DOTFileManager;

public class History {

	public ArrayList<Generation> generations;
	ArrayList<Patent> patents;

	public DOTFileManager dotFileManager;

	public Parameters parameters;

	public int generationCount;
	public int[] patentIDCount;

	public History(String name, int gens, int initpatgen,
			int patgenprolifconst, int parentpat, int colorcount, float aes,
			float res, float ces) throws IOException {
		this.generations = new ArrayList<Generation>();
		this.patents = new ArrayList<Patent>();

		this.generationCount = 0;
		this.patentIDCount = new int[gens];

		this.parameters = new Parameters(name, gens, initpatgen,
				patgenprolifconst, parentpat, colorcount, aes, res, ces);

		Patent.generatePatentColors(this.parameters.COLOR_COUNT);
	}

	public void generateHistory() {

		// repeat for each generation
		for (int i = 0; i < parameters.GENERATIONS; i++) {
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

		// Include 0. Don't include max
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
		if (parameters.COLORS) {
			for (Patent p : this.patents) {
				this.dotFileManager.createColor(p);
			}
		}
		for (Patent p : this.patents) {
			// System.out.print("patent - " + p.getID() + "; children - ");
			for (Patent child : p.getChildren()) {
				// System.out.print(child.getID() + ", ");
				this.dotFileManager.createRelation(p, child);
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

	void resetHistory() {

	}

	public void initDOTFile() throws IOException {
		this.dotFileManager = new DOTFileManager();
	}

}
