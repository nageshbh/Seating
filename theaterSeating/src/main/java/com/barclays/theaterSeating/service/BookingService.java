package com.barclays.theaterSeating.service;

import java.util.List;

import com.barclays.theaterSeating.model.TheaterLayout;
import com.barclays.theaterSeating.model.TicketRequest;

public interface BookingService {

	public void processRequest(TheaterLayout layout, List<TicketRequest> ticketRequests);
	
	public List<TicketRequest> getTicketRequests(String ticketRequests);
	
	public String printConfirmations(List<TicketRequest> ticketRequests);

}
