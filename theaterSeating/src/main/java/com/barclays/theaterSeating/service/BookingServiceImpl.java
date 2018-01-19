package com.barclays.theaterSeating.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.barclays.theaterSeating.model.Row;
import com.barclays.theaterSeating.model.Section;
import com.barclays.theaterSeating.model.TheaterLayout;
import com.barclays.theaterSeating.model.TicketRequest;

public class BookingServiceImpl implements BookingService {

	//TODO Service injection
	TheaterService theaterService = new TheaterServiceImpl();

	public List<TicketRequest> getTicketRequests(String ticketRequests) {
		List<TicketRequest> requestsList = new ArrayList<TicketRequest>();
		TicketRequest request;

		String[] requests = ticketRequests.split(System.lineSeparator());

		for (int i = 0; i < requests.length; i++) {
			String[] rData = requests[i].split(" ");
			request = new TicketRequest();
			request.setRequestedBy(rData[0]);
			try {
				request.setNoOfTickets(Integer.valueOf(rData[1]));
				request.setRequestOrder(i + 1);
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException( "'" + rData[1] + "'" + " is invalid ticket request. Please correct it.");
			}
			requestsList.add(request);
		}
		return requestsList;
	}

	/**
	 * Process Ticket Requests
	 * 
	 * 
	 *  Starting with closest to screen and until open requests
	 *  	Check section capacity matching request to current request
	 *  		if match then request is processed and move to next section
	 *  	else check for requests matching section capacity
	 *  		assign matching requests with processed true
	 *  
	 *  All pending requests are either Split party or Out of seating capacity
	 */
	public void processRequest(TheaterLayout layout, List<TicketRequest> ticketRequests) {

		ticketRequests = ticketRequests.stream().sorted(Comparator.comparing(TicketRequest::getNoOfTickets))
				.collect(Collectors.toList());

		for (Row row : layout.getRows()) {
			for (Section section : row.getSections()) {

				TicketRequest currentRequest = openRequest(ticketRequests);

				if (currentRequest != null) {
					if (section.getSectionCapacity() == currentRequest.getNoOfTickets()) {
						section.setBookedSeats(currentRequest.getNoOfTickets());
						currentRequest.setProcessedRequest(true);
						currentRequest.setConfirmation(currentRequest.getRequestedBy() + " Row " + row.getRowNumber() + " Section " + section.getSectionNumber());
					} else if (section.getSectionCapacity() >= currentRequest.getNoOfTickets()) {

						// Open ticket requests less than section capacity
						List<TicketRequest> openRequests = ticketRequests.stream().filter(
								p -> !p.isProcessedRequest() && p.getNoOfTickets() <= section.getSectionCapacity())
								.collect(Collectors.toList());
						
						//Get matching ticket requests
						List<TicketRequest> matchingRequests = getSectionMatchingRequests(layout, openRequests, section.getSectionCapacity());
						
						//Update ticket with processed
						for (TicketRequest matchingRequest : matchingRequests) {
							matchingRequest.setConfirmation(matchingRequest.getRequestedBy() + " Row " + row.getRowNumber() + " Section " + section.getSectionNumber());
							matchingRequest.setProcessedRequest(true);
						}
						
						//Update booked tickets
						section.setBookedSeats(matchingRequests.stream().map(TicketRequest::getNoOfTickets).mapToInt(Integer::intValue).sum());
					}
				} else {
					return;
				}
			}
		}

		List<TicketRequest> openRequests = ticketRequests.stream().filter(p -> !p.isProcessedRequest())
				.collect(Collectors.toList());

		for (TicketRequest openRequest : openRequests) {
			if (openRequest.getNoOfTickets() > theaterService.availableSeats(layout)) {
				openRequest.setConfirmation(openRequest.getRequestedBy() + " Sorry, we can't handle your party.");
			} else {
				openRequest.setConfirmation(openRequest.getRequestedBy() + " Call to split party.");
			}
			openRequest.setProcessedRequest(true);
		}

	}
	
	/**
	 * Use stack approach to add & remove request until tickets request matches or exceeds section capacity
	 * 
	 * @param layout
	 * @param openRequests
	 * @param sectionCapacity
	 * @return MatchingTicketRequests
	 */
	private List<TicketRequest> getSectionMatchingRequests(TheaterLayout layout, List<TicketRequest> openRequests,
			int sectionCapacity) {
		int count = 0;
		List<TicketRequest> ticketRequests = new ArrayList<TicketRequest>();
		for (TicketRequest openRequest : openRequests) {
			count = count + openRequest.getNoOfTickets();
			ticketRequests.add(openRequest);
			if (count < sectionCapacity) {
				continue;
			} else if (count == sectionCapacity) {
				break;
			} else {
				//check if it can replaced with previous element if not then remove
				int tempCount = count - ticketRequests.get(ticketRequests.size() - 2).getNoOfTickets();
				if(tempCount != (count - openRequest.getNoOfTickets()) && tempCount <= sectionCapacity) {
					ticketRequests.remove(ticketRequests.size() - 2);
					count = tempCount;
				} else {
					TicketRequest removeRequest = ticketRequests.get(ticketRequests.size() - 1);
					int removeCount = removeRequest.getNoOfTickets();
					ticketRequests.remove(removeRequest);
					count = count - removeCount;
				}
			}
		}
		return ticketRequests;
	}

	private TicketRequest openRequest(List<TicketRequest> ticketRequests) {
		TicketRequest openRequest = null;
		for (TicketRequest ticketRequest : ticketRequests) {
			if (!ticketRequest.isProcessedRequest()) {
				openRequest = ticketRequest;
				break;
			}
		}
		return openRequest;
	}
	
	public String printConfirmations(List<TicketRequest> ticketRequests) {
		StringBuilder output = new StringBuilder();
		ticketRequests = ticketRequests.stream().sorted(Comparator.comparing(TicketRequest::getRequestOrder)).collect(Collectors.toList());
		for(TicketRequest ticketRequest: ticketRequests) {
			output.append(ticketRequest.getConfirmation());
			output.append(System.lineSeparator());
		}
		return output.toString();
	}
	
}
