package com.henry.patentgeneology;

import java.util.ArrayList;

public class Parameters {

	/*
	 * Adjustable parameters
	 */

	public static final String NAME = "Test";
	public static final int GENERATIONS = 20;

	public static boolean COLORS = true;

	public static final float STRENGTH_AGE_EFFECT = 10.0f;
	public static final float STRENGTH_RICH_EFFECT = 1.0f;
	public static final float STRENGTH_COLOR_EFFECT = 10000.0f;

	public static final String DOT_PARAMS = "labelloc=tp; rankdir=TD;"
			+ "graph [splines=polyline, nodesep=0.01, ranksep=0.5];"
			+ "node[color=black style=filled shape=box fontcolor=white fixedsize=true width=.2 height=.1 fontsize=4];"
			+ "edge[penwidth=.1];";

	public static int PatentsPerGeneation(Generation g) {
		return 4 + (2 * g.generationNumber);
	}

	public static int ParentsPerPatent(Patent p) {
		return 2;
	}

	static float AgeFactorFunction(float f) {
		// follow equation y = 1/x
		return 1.0f / f;
	}

	static float RichFactorFunction(float f) {
		// follow equation y = x
		return f;
	}

	static float ColorFactorFunction(float f) {
		// folow equation y = 2x
		return 2 * f;
	}
	
	/*
	 * End of adjustable parameters
	 */
	
	// do not change below here

	public static float CalculateFactors(Patent child, Patent parent) {

		ArrayList<Float> effects = new ArrayList<Float>();

		// age effect
		effects.add(CalculateAgeFactor(child, parent));

		// rich effect
		effects.add(CalculateRichFactor(child, parent));

		// color effect
		effects.add(CalculateColorFactor(child, parent));

		return average(effects);
	}

	public static float CalculateAgeFactor(Patent child, Patent parent) {
		int ageDifference = child.getGenNumber() - parent.getGenNumber();

		if (ageDifference == 0) {
			return 0;
		}

		float result = AgeFactorFunction(ageDifference);

		result *= STRENGTH_AGE_EFFECT;
		return result;
	}

	public static float CalculateRichFactor(Patent child, Patent parent) {
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

		result *= STRENGTH_RICH_EFFECT;
		return result;
	}

	public static float CalculateColorFactor(Patent child, Patent parent) {
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

		result *= STRENGTH_COLOR_EFFECT;
		return result;
	}

	private static float average(ArrayList<Float> floats) {
		float t = 0f;
		for (float f : floats) {
			t += f;
		}
		return t / floats.size();
	}
}
