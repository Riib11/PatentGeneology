package com.henry.patentgeneology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.jfree.ui.ApplicationFrame;

import com.henry.patentgeneology.geneology.Generation;
import com.henry.patentgeneology.geneology.Parameters;
import com.henry.patentgeneology.geneology.Patent;

public class DOTFileManager {

	public static String outputs_directory = "";

	public static String my_targetdir = "/Users/Henry/Documents/PatentGeneology/DOT/outputs/";

	File file;
	FileWriter fw;
	BufferedWriter bw;
	FileReader fr;
	public DataFileManager dfm;

	static File directory;

	public DOTFileManager() throws IOException {

		if (outputs_directory != "none") {
			System.out.println("outputs_directory: " + outputs_directory);
			System.out.println(createDirName());
			directory = new File(outputs_directory + createDirName());
			boolean successful = directory.mkdir();
			System.out.println(directory.getAbsolutePath());

			if (successful) {
				System.out.println("directory created: " + directory.getName());
			} else {
				System.out.println("directory " + directory.getName()
						+ " not created");
			}
		}

		dfm = new DataFileManager(directory.getAbsoluteFile(),outputs_directory);

		if (outputs_directory != "none") {
			file = new File(directory.getAbsoluteFile() + "/"
					+ Main.history.parameters.FILE_NAME + ".dot");

			file.createNewFile();
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);

			writeToFile("graph " + Main.history.parameters.NAME + " {");
			writeToFile("    label = <<FONT POINT-SIZE=\"80\">"
					+ Main.history.parameters.NAME + "</FONT>>;");
			writeToFile("    " + Parameters.DOT_PARAMS);

			generateGenerationsSubgraph();
			generateRankingsAttributes();
		}
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

	public void createRelation(Patent parent, Patent child,
			ArrayList<Patent> pats) throws IOException {
		int totalPatents = pats.size();

		int maxChildren = (int) (totalPatents / 2);

		// has what percent of maxChildren?
		float percentChildren = (float) ((float) parent.getChildren().size() / (float) maxChildren);

		float penwidth = percentChildren * 30f;

		// A -- B [color=color of parent];
		writeToFile("    " + parent.getID() + " -- " + child.getID()
				+ "[color=" + parent.getColor() + " penwidth=" + penwidth
				+ "];");
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

	public void createNodeSize(Patent p, ArrayList<Patent> pats)
			throws IOException {
		int totalPatents = pats.size();

		int maxChildren = (int) (totalPatents / 2);

		// has what percent of maxChildren?
		float percentChildren = (float) ((float) p.getChildren().size() / (float) maxChildren);

		float size = percentChildren * 8f;

		if (size < 0.3f) {
			size = 0.3f;
		}

		int fontsize = (int) (size * 20);
		writeToFile("    " + p.getID() + " [width=" + size + " fontsize="
				+ fontsize + "]");
	}

	public String createDirName() {
		if (Main.genManager.iterating) {
			return "it-" + Main.genManager.iterate_var + "("
					+ s(Main.genManager.start_strength) + "-"
					+ s(Main.genManager.end_strength) + ")_by-"
					+ s(Main.genManager.increment) + ",cont1="
					+ s(Main.genManager.control1) + ",cont2="
					+ s(Main.genManager.control2);
		} else {
			return Main.history.parameters.FILE_NAME;
		}
	}

	private String s(float f) {
		String s = String.valueOf(f);
		s = s.replace(".", "");
		return s;
	}

}