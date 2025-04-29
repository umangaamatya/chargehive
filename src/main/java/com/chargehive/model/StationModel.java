package com.chargehive.model;

/**
 * @author Umanga Amatya
 */

public class StationModel {
	private int stationId;
	private String stationName;
	private String stationLocation;
	private String stationAvailability;
	private float stationPrice;
	private int stationPorts;
	private String stationType;
	
	public StationModel(int stationId, String stationName, String stationLocation, String stationAvailability, 
			float stationPrice, int stationPorts, String stationType) {
		this.stationId = stationId;
		this.stationName = stationName;
		this.stationLocation = stationLocation;
		this.stationAvailability = stationAvailability;
		this.stationPrice = stationPrice;
		this.stationPorts = stationPorts;
		this.stationType = stationType;
		
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationLocation() {
		return stationLocation;
	}

	public void setStationLocation(String stationLocation) {
		this.stationLocation = stationLocation;
	}

	public String getStationAvailability() {
		return stationAvailability;
	}

	public void setStationAvailability(String stationAvailability) {
		this.stationAvailability = stationAvailability;
	}

	public float getStationPrice() {
		return stationPrice;
	}

	public void setStationPrice(float stationPrice) {
		this.stationPrice = stationPrice;
	}

	public int getStationPorts() {
		return stationPorts;
	}

	public void setStationPorts(int stationPorts) {
		this.stationPorts = stationPorts;
	}

	public String getStationType() {
		return stationType;
	}

	public void setStationType(String stationType) {
		this.stationType = stationType;
	}

	
	
	

}
