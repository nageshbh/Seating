package com.barclays.theaterSeating.service;

import java.util.ArrayList;
import java.util.List;

import com.barclays.theaterSeating.model.Row;
import com.barclays.theaterSeating.model.Section;
import com.barclays.theaterSeating.model.TheaterLayout;

public class TheaterServiceImpl implements TheaterService {

	public TheaterLayout designLayout(String layout) {
		TheaterLayout theaterLayout = new TheaterLayout();
		List<Section> sectionsList = null;
		int sectionCapacity = 0;
		String[] rows = layout.split(System.lineSeparator());
		List<Row> layoutRows = new ArrayList<Row>();
		for (int i = 0; i < rows.length; i++) {
			Row row = new Row();
			row.setRowNumber(i + 1);

			String[] sections = rows[i].split(" ");
			sectionsList = new ArrayList<Section>();
			for (int j = 0; j < sections.length; j++) {

				try {
					sectionCapacity = Integer.valueOf(sections[j]);
				} catch (NumberFormatException nfe) {
					throw new NumberFormatException("'" + sections[j] + "'" + " is invalid section capacity. Kindly correct it.");
				}

				Section section = new Section();
				//section.setRowNumber(i + 1);
				section.setSectionNumber(j + 1);
				section.setSectionCapacity(sectionCapacity);
				sectionsList.add(section);
			}
			row.setSections(sectionsList);
			layoutRows.add(row);
		}
		theaterLayout.setRows(layoutRows);
		return theaterLayout;

	}

	public int theaterCapacity(TheaterLayout layout) {
		int theaterCapacity = 0;
		for(Row row: layout.getRows()) {
			for (Section section : row.getSections()) {
				theaterCapacity = theaterCapacity + section.getSectionCapacity();
			}
		}
		return theaterCapacity;
	}

	public int availableSeats(TheaterLayout layout) {
		int bookedSeats = 0;
		for(Row row: layout.getRows()) {
			for (Section section : row.getSections()) {
				bookedSeats = bookedSeats + section.getBookedSeats();
			}
		}
		return theaterCapacity(layout) - bookedSeats;
	}

}
