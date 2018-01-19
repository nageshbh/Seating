package com.barclays.theaterSeating.model;

public class TicketRequest {

	private String requestedBy;
	private int noOfTickets;
	private boolean processedRequest;
	private String confirmation;
	//Stores the order requests were sent
	private int requestOrder;

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public int getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	public String getConfirmation() {
		return confirmation;
	}

	public void setConfirmation(String confirmation) {
		this.confirmation = confirmation;
	}

	public boolean isProcessedRequest() {
		return processedRequest;
	}

	public void setProcessedRequest(boolean processedRequest) {
		this.processedRequest = processedRequest;
	}

	public int getRequestOrder() {
		return requestOrder;
	}

	public void setRequestOrder(int requestOrder) {
		this.requestOrder = requestOrder;
	}

}