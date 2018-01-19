package com.barclays.theaterSeating.model;

import java.util.List;

public class Row {
	
	private int rowNumber;
	private List<Section> sections;
	
	public int getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}
	public List<Section> getSections() {
		return sections;
	}
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
}
