package com.barclays.theaterSeating.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.barclays.theaterSeating.model.Row;
import com.barclays.theaterSeating.model.TheaterLayout;

public class TheaterServiceImplTest {
	
	static TheaterService theaterService = null;
	
	@BeforeClass
	public static void setup(){
		theaterService = new TheaterServiceImpl();
	}
	
	
	@Test(expected = NumberFormatException.class)
	public void designLayoutInvalidInput(){
		String layout = "6 test\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		theaterService.designLayout(layout);
	}
	
	@Test
	public void designLayoutValidInput(){
		String layout = "6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		TheaterLayout theaterLayout = theaterService.designLayout(layout);
		assertEquals(5, theaterLayout.getRows().size());
		assertEquals(16, theaterLayout.getRows().stream().map(Row::getSections).mapToInt(List::size).sum());
	}
	
	@Test
	public void theaterCapacityTest(){
		String layout = "6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		TheaterLayout theaterLayout = theaterService.designLayout(layout);
		assertEquals(80, theaterService.theaterCapacity(theaterLayout));
	}
	
	@Test
	public void availableSeatsBeforeBookingTest(){
		String layout = "6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		TheaterLayout theaterLayout = theaterService.designLayout(layout);
		assertEquals(80, theaterService.availableSeats(theaterLayout));
	}
	
	@Test
	public void availableSeatsAfterBookingTest(){
		String layout = "6 6\n3 5 5 3\n4 6 6 4\n2 8 8 2\n6 6\n";
		TheaterLayout theaterLayout = theaterService.designLayout(layout);
		theaterLayout.getRows().get(0).getSections().get(0).setBookedSeats(6);
		assertEquals(74, theaterService.availableSeats(theaterLayout));
	}
	
}
