package com.henry.patentgeneology.geneology;

import java.util.ArrayList;
import java.util.Random;

import com.henry.patentgeneology.Main;

public class Patent {

	String color;

	ArrayList<Patent> parents;
	ArrayList<Patent> children;

	Generation generation;

	private int[] patentID;

	public static Patent null_patent;

	public Patent(Generation g) {

		if (null_patent == null) {
			gen_null_patent();
		}

		this.generation = g;
		this.patentID = new int[2];
		this.patentID[0] = this.generation.generationNumber;
		this.patentID[1] = Main.history.patentIDCount[this.generation.generationNumber];

		Main.history.patentIDCount[this.generation.generationNumber]++;

		this.parents = new ArrayList<Patent>();
		this.children = new ArrayList<Patent>();

		this.setColor(getNextPatentColor());
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
		if (Main.history.parameters.STRENGTH_AGE_EFFECT == 0
				&& Main.history.parameters.STRENGTH_COLOR_EFFECT == 0
				&& Main.history.parameters.STRENGTH_RICH_EFFECT == 0) {
			return 0f;
		}
		return Main.history.parameters.CalculateFactors(child, this);

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

	static String[] rawPatentColors = { "red", "indigo", "blue", "green",
			"purple", "cyan", "tan", "darkgreen" };

	public static void generatePatentColors(int colorcount) {
		patentColors = new ArrayList<String>();

		for (int x = 0; x < colorcount && x < rawPatentColors.length; x++) {
			patentColors.add(rawPatentColors[x]);
		}
	}

	static String getNextPatentColor() {
		return patentColors.get(randInt(0, patentColors.size() - 1));
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}
