package com.henry.patentgeneology.geneology;

import java.util.ArrayList;

public class Parameters {

	/*
	 * Adjustable parameters
	 */

	public static String DOT_PARAMS = "labelloc=tp; rankdir=TD;"
			+ "graph [splines=polyline, nodesep=0.01, ranksep=0.5];"
			+ "node[color=black style=filled shape=box fontcolor=white fixedsize=true width=.2 height=.1 fontsize=4];"
			+ "edge[penwidth=.1];";

	public int PatentsPerGeneation(Generation g) {
		return INITPATENTSPERGEN
				+ (PATENTSPERGENPROLIFFACTOR * g.generationNumber);
	}

	public int ParentsPerPatent(Patent p) {
		return PARENTSPERPAT;
	}

	float AgeFactorFunction(float f) {
		// follow equation y = 1/x^a
		// System.out.println(1 / (f * STRENGTH_AGE_EFFECT));
		return (float) (1 / Math.pow(f, STRENGTH_AGE_EFFECT));
	}

	float RichFactorFunction(float f) {
		// follow equation y = ax
		return STRENGTH_RICH_EFFECT * f;
	}

	float ColorFactorFunction(float f) {
		// folow equation y = ax
		return STRENGTH_COLOR_EFFECT * f;
	}

	/*
	 * End of adjustable parameters
	 */

	// do not change below here

	public String NAME;
	public String FILE_NAME;
	public int GENERATIONS;

	public boolean COLORS = true;

	public float STRENGTH_AGE_EFFECT;

	public float STRENGTH_RICH_EFFECT;

	public float STRENGTH_COLOR_EFFECT;

	public int INITPATENTSPERGEN;
	public int PATENTSPERGENPROLIFFACTOR;

	public int PARENTSPERPAT;

	public int COLOR_COUNT;

	public Parameters(int gens, int initpatgen, int patgenprolifconst,
			int parentpat, int colorcount, float aes, float res, float ces) {
		this.GENERATIONS = gens;
		this.INITPATENTSPERGEN = initpatgen;
		this.PATENTSPERGENPROLIFFACTOR = patgenprolifconst;
		this.PARENTSPERPAT = parentpat;
		this.COLOR_COUNT = colorcount;
		this.STRENGTH_AGE_EFFECT = aes;
		this.STRENGTH_RICH_EFFECT = res;
		this.STRENGTH_COLOR_EFFECT = ces;

		this.NAME = createName();
		this.FILE_NAME = createFileName();
	}

	public float CalculateFactors(Patent child, Patent parent) {

		ArrayList<Float> effects = new ArrayList<Float>();

		// age effect
		effects.add(CalculateAgeFactor(child, parent));

		// rich effect
		effects.add(CalculateRichFactor(child, parent));

		// color effect
		effects.add(CalculateColorFactor(child, parent));

		// System.out.println("C:" + child.getID() + "; P: " + parent.getID()
		// + "; Age Score: " + sum(effects));
		// System.out.println("C:" + child.getID() + "; P: " + parent.getID()
		// + "; Age Score: " + (effects.get(0)));

		return sum(effects);
	}

	public float CalculateAgeFactor(Patent child, Patent parent) {
		int ageDifference = child.getGenNumber() - parent.getGenNumber();

		if (ageDifference == 0f) {
			return 0f;
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
				/*
				 * don't add, because child isn't valid if its in the same
				 * generation as the concerned child
				 */
			}
		}
		childrenCount = validChildren.size();

		float result = RichFactorFunction(childrenCount);

		return result;
	}

	public float CalculateColorFactor(Patent child, Patent parent) {
		float colorScore = 0f;
		if (child.getColor().equalsIgnoreCase(parent.getColor())) {
			colorScore = 1f;
		}

		float result = ColorFactorFunction(colorScore);

		return result;
	}

	private float sum(ArrayList<Float> floats) {
		float t = 0;
		for (float f : floats) {
			t += f;
		}
		return t;
	}

	private String createName() {
		return "\"Age=" + String.valueOf(this.STRENGTH_AGE_EFFECT) + ", Rich="
				+ String.valueOf(this.STRENGTH_RICH_EFFECT) + ", Color="
				+ String.valueOf(this.STRENGTH_COLOR_EFFECT) + "\"";
	}

	private String createFileName() {
		return "A" + s(this.STRENGTH_AGE_EFFECT) + "_R"
				+ s(this.STRENGTH_RICH_EFFECT) + "_C"
				+ s(this.STRENGTH_COLOR_EFFECT);
	}

	private String s(float f) {
		String s = String.valueOf(f);
		s = s.replace(".", "");
		return s;
	}
}
