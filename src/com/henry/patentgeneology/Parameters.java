package com.henry.patentgeneology;

import java.util.ArrayList;

public class Parameters {

	/*
	 * Adjustable parameters
	 */

	public static final String NAME = "Test";
	public static final int GENERATIONS = 20;

	public static final float STRENGTH_AGE_EFFECT = 1.0f;
	public static final float STRENGTH_RICH_EFFECT = 10000000000.0f;

	public static final String DOT_PARAMS = "labelloc=tp; rankdir=TD;"
			+ "graph [splines=line, nodesep=0.1, ranksep=0.5];"
			+ "node[color=black style=filled shape=box fontcolor=white fixedsize=true width=.2 height=.1 fontsize=4];"
			+ "edge[penwidth=.1];";

	public static int PatentsPerGeneation(Generation g) {
		return 4;
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

}
