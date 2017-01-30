package com.henry.patentgeneology.geneology;

import java.util.ArrayList;

import com.henry.patentgeneology.Main;

public class Generation {

	public ArrayList<Patent> patents;
	public int generationNumber;

	public Generation() {
		this.patents = new ArrayList<Patent>();
		this.generationNumber = Main.history.generationCount;
		Main.history.generationCount++;

		// generate patents
		int members = Main.history.parameters.PatentsPerGeneation(this);
		for (int x = 0; x < members; x++) {
			Patent p = createPatent();

			// add parents, if not gen0
			if (this.generationNumber != 0) {
				int parentsPerPatent = Main.history.parameters
						.ParentsPerPatent(p);
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
