package com.barclays.theaterSeating.service;

import com.barclays.theaterSeating.model.TheaterLayout;

public interface TheaterService {

	public TheaterLayout designLayout(String layout);
	
	public int theaterCapacity(TheaterLayout layout);
	
	public int availableSeats(TheaterLayout layout);
}
