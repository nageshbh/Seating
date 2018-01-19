package com.barclays.theaterSeating.model;

public class Section {

	private int sectionNumber;
	private int sectionCapacity;
	private int bookedSeats;

	public int getSectionNumber() {
		return sectionNumber;
	}

	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
	}

	public int getSectionCapacity() {
		return sectionCapacity;
	}

	public void setSectionCapacity(int sectionCapacity) {
		this.sectionCapacity = sectionCapacity;
	}

	public int getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

}
