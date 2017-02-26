package com.henry.patentgeneology;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.jfree.ui.ApplicationFrame;

import com.henry.patentgeneology.charts.LineChart;
import com.henry.patentgeneology.geneology.History;
import com.henry.patentgeneology.geneology.Patent;

public class DataFileManager {

	File file;
	FileWriter fw;
	BufferedWriter bw;

	String location = "none";
	String outfiledir;

	public DataFileManager(File f, String outfiledir) throws IOException {

		this.outfiledir = outfiledir;
		if (outfiledir != "none") {
			this.file = new File(f + "/" + "data.txt");
			this.location = f + "/";
			file.createNewFile();
		}
	}

	/*
	 * Data -
	 */

	// Mean, median, mode and STD for:
	// - Patents with n children versus # children
	// - Patents with n parents versus # parents
	// - Children with parent of color c versus colors
	// - Parent-Child relationships n generations apart versus n generations

	// Curve of distributions for:
	// - Patents with n children versus # children
	// - Patents with n parents versus # parents
	// - Children with parent of color c versus colors
	// - Parent-Child relationships n generations apart versus n generations

	public void createData(History h) throws IOException {
		HashMap<Integer, Integer> children = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> parents = new HashMap<Integer, Integer>();
		HashMap<String, Integer> colors = new HashMap<String, Integer>();
		HashMap<Integer, Integer> gendiffs = new HashMap<Integer, Integer>();

		// count : total
		float[] averageChildren = { 0, 0 };
		float[] averageParents = { 0, 0 };
		float[] percentHomogeneos = { 0, 0 };
		float[] averageGenDiff = { 0, 0 };

		for (Patent p : h.patents) {

			// Patents with n children versus # children
			int childcount = p.getChildren().size();
			if (children.containsKey(childcount)) {
				// add one to entry childcount
				children.put(childcount, children.get(childcount) + 1);
			} else {
				children.put(childcount, 1);
			}
			averageChildren[0]++;
			averageChildren[1] += childcount;

			// Patents with n parents versus # parents
			int parentcount = p.getParents().size();
			if (parents.containsKey(parentcount)) {
				// add one to entry parentcount
				parents.put(parentcount, parents.get(parentcount) + 1);
			} else {
				parents.put(parentcount, 1);
			}
			averageParents[0]++;
			averageParents[1] += parentcount;

			// Patents of color c versus colors
			String color = p.getColor();
			if (colors.containsKey(color)) {
				// add children.size() to this color's entry
				colors.put(color, colors.get(color) + p.getChildren().size());
			} else {
				colors.put(color, p.getChildren().size());
			}

			// Parent-Child relationships n generations apart versus n
			// generations
			for (Patent child : p.getChildren()) {
				int gendiff = child.getGenNumber() - p.getGenNumber();
				if (gendiffs.containsKey(gendiff)) {
					// add one to entry gendiff
					gendiffs.put(gendiff, gendiffs.get(gendiff) + 1);
				} else {
					gendiffs.put(gendiff, 1);
				}
				averageGenDiff[0]++;
				averageGenDiff[1] += gendiff;

				// test color too, while iterating
				if (child.getColor().equalsIgnoreCase(p.getColor())) {
					percentHomogeneos[0]++;
				}
				percentHomogeneos[1]++;
			}
		}

		// create all the line charts
		LineChart.createLineChart("Children Distributions", children,
				"Patents with n Children", "Children", location, true);

		LineChart.createLineChart("Parent Distributions", parents,
				"Patents with n Parents", "Parents", location, false);

		LineChart.createLineChart("Color Popularities", colors,
				"Relationships that have Parent of Color c", "Color", location,
				false);

		LineChart.createLineChart("Parent-Child Relations n Generations Apart",
				gendiffs, "Parent-Child relationships n generations apart",
				"Generations Apart", location, true);

		// TOOD Mean, median, mode and STD for them all now

		if (outfiledir != "none") {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
		}

		writeToFile("# - Data - - - - - - - - - - - - - - - - - - - - - - - - # ");

		/*
		 * count : total averageChildren averageParents percentHomogeneos
		 * averageGenDiff
		 */
		float aChildren = averageChildren[1] / averageChildren[0];
		float aParents = averageParents[1] / averageParents[0];
		float pHomo = percentHomogeneos[0] / percentHomogeneos[1] * 100;
		float aGenDiff = averageGenDiff[1] / averageGenDiff[0];
		writeToFile("");
		writeToFile("Average Children: " + aChildren);
		writeToFile("Average Parents: " + aParents);
		writeToFile("Percent Homogeneous Relationships: " + pHomo + "%");
		writeToFile("Average Parent-Child Generation Difference: " + aGenDiff);

		endFile();
	}

	void writeToFile(String str) throws IOException {
		if (outfiledir != "none") {
			bw.write(str);
			bw.write("\n");
		}
	}

	public void endFile() throws IOException {
		if (outfiledir != "none") {
			bw.flush();
			bw.close();
		}
	}
}
