package search;

import java.util.Calendar;
import java.util.Date;

import airport.Airport;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import preferences.DateType;
import preferences.RetailCustomerPreferences;
import trip.Trip;
import trip.TripType;
import trip.Trips;

public class SearchTrip {
	RetailCustomerPreferences retailCustomerPreferences;
	Trips onwardTripList;
	
	public SearchTrip(RetailCustomerPreferences retailCustomerPreferences) {
		this.retailCustomerPreferences = retailCustomerPreferences;
	}
	
	public Trips search(String teamName) {
		Trips trips;
		Calendar date = retailCustomerPreferences.searchDate();
		if(retailCustomerPreferences.searchDateType() == DateType.ARRIVAL) {
			SearchTripByArrival search = new SearchTripByArrival(this.retailCustomerPreferences);
			trips = search.searchTripsByArrivalAirport(teamName, date);
		} else {
			SearchTripByDeparture search = new SearchTripByDeparture(this.retailCustomerPreferences);
			trips = search.searchTripsByDepartureAirport(teamName, date);
		}
		
		return trips;
	}
	
	private Trips searchTripsByArrivalAirport(String teamName, Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Flights searchArrivingFlightsOn(String teamName, Airport arrivalAirport, Calendar arrivalDate) {
		Flights flights = ServerInterface.INSTANCE.getArrivingFlights(arrivalAirport.code(), arrivalDate, teamName);
		return flights;
	}
}
