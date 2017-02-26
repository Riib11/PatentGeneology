package com.henry.patentgeneology.charts;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.henry.patentgeneology.ui.UI;

public class LineChart {

	@SuppressWarnings("rawtypes")
	public static void createLineChart(String name, HashMap<?, Integer> input,
			String ylabel, String xlabel, String location, boolean display)
			throws IOException {

		DefaultCategoryDataset line_chart_dataset = new DefaultCategoryDataset();

		Iterator<?> it = input.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			line_chart_dataset.addValue((Integer) pair.getValue(), ylabel,
					String.valueOf(pair.getKey()));
			it.remove(); // avoids a ConcurrentModificationException
		}

		JFreeChart lineChartObject = ChartFactory.createLineChart(name, xlabel,
				ylabel, line_chart_dataset, PlotOrientation.VERTICAL, true,
				true, false);

		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */

		if (location != "none") {
			File lineChart = new File(location + ylabel + ".jpeg");
			ChartUtilities.saveChartAsJPEG(lineChart, lineChartObject, width,
					height);
		}

		ChartPanel chartPanel = new ChartPanel(lineChartObject);
		chartPanel.setPreferredSize(new Dimension(400, 400));

		if (display) {
			UI.setChart(chartPanel);
		}
		System.out.println("Created Chart: " + name);

	}
}
