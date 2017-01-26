package com.henry.patentgeneology;

import java.io.IOException;

public class Main {

	static History history;
	static DOTFileManager dotfile;

	public static void main(String[] args) throws IOException {

		history = new History();
		history.generateHistory();

		dotfile = new DOTFileManager();

		history.outputData();
		
		dotfile.endFile();

	}

}
