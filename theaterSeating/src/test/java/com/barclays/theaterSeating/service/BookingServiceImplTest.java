package com.barclays.theaterSeating.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.theaterSeating.model.TheaterLayout;
import com.barclays.theaterSeating.model.TicketRequest;

public class BookingServiceImplTest {
	
	static BookingService bookingService = null;
	static TheaterService theaterService = null;
	
	@BeforeClass
	public static void setup(){
		bookingService = new BookingServiceImpl();
		theaterService = new TheaterServiceImpl();
	}
	
	
	@Test(expected = NumberFormatException.class)
	public void getTicketRequestsInvalidInput(){
		String ticketRequests = "Smith test\nJones 5\nDavis 6\nWilson 100\nJohnson 3\nWilliams 4\nBrown 8\nMiller 12\n";
		bookingService.getTicketRequests(ticketRequests);
	}
	
	@Test
	public void getTicketRequestsValidInput(){
		String ticketRequestsStr = "Smith 2\nJones 5\nDavis 6\nWilson 100\nJohnson 3\nWilliams 4\nBrown 8\nMiller 12\n";
		List<TicketRequest> ticketRequests = bookingService.getTicketRequests(ticketRequestsStr);
		assertEquals(8, ticketRequests.size());
	}
	
	@Test
	public void processRequestWithExactMatchingSectionRequest(){
		String layout = "6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		TheaterLayout theaterLayout = theaterService.designLayout(layout);
		String ticketRequestsStr = "Smith 6\n";
		List<TicketRequest> ticketRequests = bookingService.getTicketRequests(ticketRequestsStr);
		bookingService.processRequest(theaterLayout, ticketRequests);
		assertEquals(6, theaterLayout.getRows().get(0).getSections().get(0).getBookedSeats());
		assertEquals(74, theaterService.availableSeats(theaterLayout));
		assertEquals(true, ticketRequests.get(0).isProcessedRequest());
	}
	
	@Test
	public void processRequestsWithMatchingSectionRequest(){
		String layout = "6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		TheaterLayout theaterLayout = theaterService.designLayout(layout);
		String ticketRequestsStr = "Smith 2\nJohn 4\n";
		List<TicketRequest> ticketRequests = bookingService.getTicketRequests(ticketRequestsStr);
		bookingService.processRequest(theaterLayout, ticketRequests);
		assertEquals(6, theaterLayout.getRows().get(0).getSections().get(0).getBookedSeats());
		assertEquals(74, theaterService.availableSeats(theaterLayout));
		assertEquals(2, ticketRequests.stream().filter(p -> p.isProcessedRequest()).count());
	}
	
	@Test
	public void printConfirmationTest(){
		String layout = "6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		TheaterLayout theaterLayout = theaterService.designLayout(layout);
		String ticketRequestsStr = "Smith 2\nJones 5\nDavis 6\nWilson 100\nJohnson 3\nWilliams 4\nBrown 8\nMiller 12";
		List<TicketRequest> ticketRequests = bookingService.getTicketRequests(ticketRequestsStr);
		bookingService.processRequest(theaterLayout, ticketRequests);
		String expectedOutput = "Smith Row 1 Section 1\nJones Row 2 Section 2\nDavis Row 1 Section 2\nWilson Sorry, we can't handle your party.\nJohnson Row 2 Section 1\nWilliams Row 1 Section 1\nBrown Row 4 Section 2\nMiller Call to split party.\n";
		assertEquals(expectedOutput, bookingService.printConfirmations(ticketRequests));
	}
	
	

}
