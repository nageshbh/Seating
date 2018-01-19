import java.util.List;
import java.util.Scanner;

import com.barclays.theaterSeating.model.TheaterLayout;
import com.barclays.theaterSeating.model.TicketRequest;
import com.barclays.theaterSeating.service.BookingService;
import com.barclays.theaterSeating.service.BookingServiceImpl;
import com.barclays.theaterSeating.service.TheaterService;
import com.barclays.theaterSeating.service.TheaterServiceImpl;

public class TheaterSeatingSimulator {

	public static void main(String[] args) {

		String line;
		StringBuilder layoutInput = new StringBuilder();
		StringBuilder ticketRequestsInput = new StringBuilder();
		boolean isLayoutFinished = false;

		System.out.println("Please enter Theater Layout and Ticket requests ending with ```.\n");

		Scanner input = new Scanner(System.in);

		while ((line = input.nextLine()) != null && !line.equals("```")) {

			if (line.length() == 0) {
				isLayoutFinished = true;
				continue;
			}

			if (!isLayoutFinished) {
				layoutInput.append(line + System.lineSeparator());

			} else {
				ticketRequestsInput.append(line + System.lineSeparator());
			}

		}

		input.close();

		try {

			TheaterService theaterService = new TheaterServiceImpl();
			TheaterLayout theaterLayout = theaterService.designLayout(layoutInput.toString());

			BookingService bookingService = new BookingServiceImpl();
			List<TicketRequest> ticketRequests = bookingService.getTicketRequests(ticketRequestsInput.toString());

			bookingService.processRequest(theaterLayout, ticketRequests);
			System.out.println(bookingService.printConfirmations(ticketRequests));
			
		} catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}