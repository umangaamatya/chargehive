package com.chargehive.model;

import java.time.LocalDate;

/**
 * @author Umanga Amatya
 */

public class BookingModel {
	private int bookingId;
	private int userId;
	private int stationId;
	private LocalDate bookingDate;
	
	public BookingModel() {
		
	}

	public BookingModel(int bookingId, int userId, int stationId, LocalDate bookingDate) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.stationId = stationId;
		this.bookingDate = bookingDate;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	
	
	

}
