package com.henry.patentgeneology.geneology;

import java.util.ArrayList;

public class Parameters {

	/*
	 * Adjustable parameters
	 */

	public String NAME;
	public int GENERATIONS;

	public boolean COLORS = true;

	public float STRENGTH_AGE_EFFECT;

	public float STRENGTH_RICH_EFFECT;

	public float STRENGTH_COLOR_EFFECT;

	public int INITPATENTSPERGEN;
	public int PATENTSPERGENPROLIFFACTOR;

	public int PARENTSPERPAT;

	public int COLOR_COUNT;

	public static String DOT_PARAMS = "labelloc=tp; rankdir=TD;"
			+ "graph [splines=polyline, nodesep=0.01, ranksep=0.5];"
			+ "node[color=black style=filled shape=box fontcolor=white fixedsize=true width=.2 height=.1 fontsize=4];"
			+ "edge[penwidth=.1];";

	public Parameters(String name, int gens, int initpatgen,
			int patgenprolifconst, int parentpat, int colorcount, float aes,
			float res, float ces) {
		this.NAME = name;
		this.GENERATIONS = gens;
		this.INITPATENTSPERGEN = initpatgen;
		this.PATENTSPERGENPROLIFFACTOR = patgenprolifconst;
		this.PARENTSPERPAT = parentpat;
		this.COLOR_COUNT = colorcount;
		this.STRENGTH_AGE_EFFECT = aes;
		this.STRENGTH_RICH_EFFECT = res;
		this.STRENGTH_COLOR_EFFECT = ces;
	}

	public int PatentsPerGeneation(Generation g) {
		return INITPATENTSPERGEN
				+ (PATENTSPERGENPROLIFFACTOR * g.generationNumber);
	}

	public int ParentsPerPatent(Patent p) {
		return PARENTSPERPAT;
	}

	float AgeFactorFunction(float f) {
		// follow equation y = 1/x
		return STRENGTH_AGE_EFFECT / f;
	}

	float RichFactorFunction(float f) {
		// follow equation y = x
		return STRENGTH_RICH_EFFECT * f;
	}

	float ColorFactorFunction(float f) {
		// folow equation y = x
		return STRENGTH_COLOR_EFFECT * f;
	}

	/*
	 * End of adjustable parameters
	 */

	// do not change below here

	public float CalculateFactors(Patent child, Patent parent) {

		ArrayList<Float> effects = new ArrayList<Float>();

		// age effect
		effects.add(CalculateAgeFactor(child, parent));

		// rich effect
		effects.add(CalculateRichFactor(child, parent));

		// color effect
		// effects.add(CalculateColorFactor(child, parent));

		return sum(effects) + 1;
		// return average(effects);
	}

	public float CalculateAgeFactor(Patent child, Patent parent) {
		int ageDifference = child.getGenNumber() - parent.getGenNumber();

		if (ageDifference == 0) {
			return 0;
		}

		float result = AgeFactorFunction(ageDifference);

		return result;
	}

	public float CalculateRichFactor(Patent child, Patent parent) {
		int childrenCount;
		// only count children of concerned generation
		ArrayList<Patent> validChildren = new ArrayList<Patent>();
		for (Patent c : parent.getChildren()) {
			if (child.getGenNumber() != c.getGenNumber()) {
				validChildren.add(c);
			} else {
				// don't add, because child isn't valid if its in the same
				// generation as the concerned child
			}
		}
		childrenCount = validChildren.size();

		float result = RichFactorFunction(childrenCount);

		return result;
	}

	public float CalculateColorFactor(Patent child, Patent parent) {
		int totalParents = child.getParents().size();

		if (totalParents == 0) {
			return 0;
		}

		int[] colorCounts = new int[Patent.patentColors.size()];
		for (int x = 0; x < Patent.patentColors.size(); x++) {
			for (Patent p : child.getParents()) {
				if (p.getColorIndex() == x) {
					colorCounts[x]++;
				}
			}
		}

		float colorScore = colorCounts[parent.getColorIndex()] / totalParents;

		float result = ColorFactorFunction(colorScore);

		return result;
	}

	private float sum(ArrayList<Float> floats) {
		float t = 0;
		for (float f : floats) {
			t += f;
		}
		return t + 1;
	}
}
