package hr.fer.zemris.java.gui.charts;

import java.util.List;

public class BarChart {

	private List<XYValue> xyvs;
	private String xLabel;
	private String yLabel;
	private int minY;
	private int maxY;
	private int distance;
	
	public BarChart(List<XYValue> xyvs, String xLabel, String yLabel, int minY, int maxY, int distance) {
		if (minY < 0 || maxY <= minY)
			throw new IllegalArgumentException();
		
		for (XYValue v : xyvs) {
			if (v.getY() < minY)
				throw new IllegalArgumentException();
		}
		this.xyvs = xyvs;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.minY = minY;
		this.maxY = maxY;
		this.distance = distance;
		
		
	}
	
}
