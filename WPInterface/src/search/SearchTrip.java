package search;

import java.util.Calendar;
import java.util.Date;

import airport.Airport;
import airport.Airports;
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
	Airports airports;
	
	public SearchTrip(RetailCustomerPreferences retailCustomerPreferences, Airports airports) {
		this.retailCustomerPreferences = retailCustomerPreferences;
		this.airports = airports;
	}
	
	public Trips search(String teamName) {
		Trips trips;
		Calendar date = retailCustomerPreferences.searchDate();
		if(retailCustomerPreferences.searchDateType() == DateType.ARRIVAL) {
			SearchTripByArrival search = new SearchTripByArrival(this.retailCustomerPreferences, this.airports);
			trips = search.searchTripsByArrivalAirport(teamName, date);
		} else {
			SearchTripByDeparture search = new SearchTripByDeparture(this.retailCustomerPreferences, this.airports);
			trips = search.searchTripsByDepartureAirport(teamName, date);
		}
		
		return trips;
  }
}
