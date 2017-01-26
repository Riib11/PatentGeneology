package com.henry.patentgeneology;

import java.util.ArrayList;

public class Patent {

	ArrayList<Patent> parents;
	ArrayList<Patent> children;

	Generation generation;

	private int[] patentID;
	private static int[] patentIDCount;

	public static Patent null_patent;

	public Patent(Generation g) {
		if (patentIDCount == null) {
			patentIDCount = new int[Parameters.GENERATIONS];
		}

		if (null_patent == null) {
			gen_null_patent();
		}

		this.generation = g;
		this.patentID = new int[2];
		this.patentID[0] = this.generation.generationNumber;
		this.patentID[1] = patentIDCount[this.generation.generationNumber];

		patentIDCount[this.generation.generationNumber]++;

		this.parents = new ArrayList<Patent>();
		this.children = new ArrayList<Patent>();

		// do any of the things needed to initialize a patent
	}

	public boolean isValidParent(Patent parent) {
		// parent isn't already a parent
		if (this.parents.contains(parent)) {
			return false;
		}

		// for (Patent p : this.parents) {
		// if (p.getID() == parent.getID()) {
		// return false;
		// }
		// }

		// parent isn't from the same generation
		if (this.getGenNumber() == parent.getGenNumber()) {
			return false;
		}

		return true;
	}

	public void addParent(Patent p) {
		this.parents.add(p);
	}

	public ArrayList<Patent> getParents() {
		return this.parents;
	}

	public void addChild(Patent p) {
		this.children.add(p);
	}

	public ArrayList<Patent> getChildren() {
		return this.children;
	}

	public String getID() {
		return String.valueOf("G" + this.patentID[0]) + "P"
				+ String.valueOf(this.patentID[1]);
	}

	public int getGenNumber() {
		return this.patentID[0];
	}

	static void gen_null_patent() {
		null_patent = new Patent("null");
	}

	public Patent(String str) {

	}

	public float getFactorPoints(Patent child) {

		ArrayList<Float> effects = new ArrayList<Float>();

		// age effect
		effects.add(Parameters.CalculateAgeFactor(child, this));

		// rich effect
		effects.add(Parameters.CalculateRichFactor(child, this));

		return average(effects);
	}

	private float average(ArrayList<Float> floats) {
		float t = 0f;
		for (float f : floats) {
			t += f;
		}
		return t / floats.size();
	}
}
