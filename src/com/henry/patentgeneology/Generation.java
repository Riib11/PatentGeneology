package com.henry.patentgeneology;

import java.util.ArrayList;

public class Generation {

	static int generationCount = 0;

	ArrayList<Patent> patents;
	int generationNumber;

	public Generation() {
		this.patents = new ArrayList<Patent>();
		this.generationNumber = generationCount;
		generationCount++;

		// generate patents
		int members = Parameters.PatentsPerGeneation(this);
		for (int x = 0; x < members; x++) {
			Patent p = createPatent();

			// add parents, if not gen0
			if (this.generationNumber != 0) {
				int parentsPerPatent = Parameters.ParentsPerPatent(p);
				Patent parent;
				for (int y = 0; y < parentsPerPatent; y++) {
					parent = Main.history.chooseParent(p);
					if (!(parent == Patent.null_patent)) {
						createCitation(p, parent);
					}

				}
			}
		}
	}

	public void createCitation(Patent child, Patent parent) {
		child.addParent(parent);
		parent.addChild(child);
	}

	public Patent createPatent() {
		Patent p = new Patent(this);

		Main.history.addPatent(p);
		this.addPatent(p);
		return p;
	}

	void addPatent(Patent p) {
		this.patents.add(p);

	}

	public Patent getPatent(int i) {
		return this.patents.get(i);
	}

	public int getGenerationNumber() {
		return this.generationNumber;
	}

}
