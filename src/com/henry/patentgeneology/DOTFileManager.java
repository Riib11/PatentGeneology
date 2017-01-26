package com.henry.patentgeneology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DOTFileManager {

	File file;
	FileWriter fw;
	BufferedWriter bw;
	FileReader fr;

	public DOTFileManager() throws IOException {
		file = new File(Parameters.NAME + ".dot");
		file.createNewFile();
		fw = new FileWriter(file);
		bw = new BufferedWriter(fw);

		writeToFile("graph " + Parameters.NAME + " {");
		writeToFile("    label = " + Parameters.NAME + ";");
		writeToFile("    " + Parameters.DOT_PARAMS);

		generateGenerationsSubgraph();
		generateRankingsAttributes();
	}

	private void generateRankingsAttributes() throws IOException {
		ArrayList<Generation> gs = Main.history.generations;
		for (Generation g : gs) {
			String patentIDs = "";
			patentIDs += "Gen" + g.generationNumber + ";";
			for (Patent p : g.patents) {
				patentIDs += p.getID() + ";";
			}
			// {rank=same;A;B;..;}
			writeToFile("    {rank=same;" + patentIDs + "}");
		}
	}

	private void generateGenerationsSubgraph() throws IOException {
		ArrayList<Generation> gs = Main.history.generations;

		writeToFile("    subgraph Generations {");
		writeToFile("        node[color=grey style=filled fontsize=12 shape=cds fontcolor=black fixedsize=false];"
				+ "edge[style=invis]");

		String gens = "";
		for (Generation g : gs) {
			if (g.generationNumber == 0) {
				gens += "Gen0";
			} else {
				gens += " -- Gen" + g.generationNumber;
			}
		}
		gens += ";";

		writeToFile("        " + gens);
		writeToFile("    }");
	}

	public void createRelation(Patent parent, Patent child) throws IOException {
		// A -> B;
		writeToFile("    " + parent.getID() + " -- " + child.getID() + ";");
	}

	void writeToFile(String str) throws IOException {
		bw.write(str);
		bw.write("\n");
	}

	public void endFile() throws IOException {
		writeToFile("}");
		bw.flush();
		bw.close();
	}

	public void createColor(Patent p) throws IOException {
		writeToFile("    " + p.getID() + " [color=" + p.getColor() + "]");
	}
}