package com.henry.patentgeneology;

import java.util.ArrayList;

public class Patent {

	String color;

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
		return Parameters.CalculateFactors(child, this);
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String s) {
		this.color = s;
	}

	public int getColorIndex() {
		return patentColors.indexOf(this.getColor());
	}

	public boolean isColor(String s) {
		return s.equalsIgnoreCase(this.getColor());
	}

	static ArrayList<String> patentColors;
	static int currentPatentColor = 0;

	public static void generatePatentColors() {
		patentColors = new ArrayList<String>();

		patentColors.add("red");
		patentColors.add("indigo");
		patentColors.add("blue");
		patentColors.add("green");
		patentColors.add("purple");
		patentColors.add("cyan");
		patentColors.add("tan");
		patentColors.add("darkgreen");
	}

	static String getNextPatentColor() {
		String s = patentColors.get(currentPatentColor);
		currentPatentColor++;
		if (currentPatentColor > patentColors.size() - 1) {
			currentPatentColor = 0;
		}
		return s;
	}

	public void calculateColor() {
		int[] colorCounts = new int[patentColors.size()];
		for (int x = 0; x < patentColors.size(); x++) {
			for (Patent p : this.getParents()) {
				if (p.getColorIndex() == x) {
					colorCounts[x]++;
				}
			}
		}

		int maxCount = 0;
		int maxCountIndex = 0;
		for (int x = 0; x < colorCounts.length; x++) {
			if (colorCounts[x] > maxCount) {
				maxCount = colorCounts[x];
				maxCountIndex = x;
			}
		}

		this.setColor(patentColors.get(maxCountIndex));
	}
}
