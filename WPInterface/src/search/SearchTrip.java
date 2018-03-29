package search;

import java.util.Calendar;
import java.util.Date;

import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import preferences.RetailCustomerPreferences;
import trip.Trips;

public class SearchTrip {
	RetailCustomerPreferences retailCustomerPreferences;
	Trips onwardTripList;
	
	public SearchTrip(RetailCustomerPreferences retailCustomerPreferences) {
		this.retailCustomerPreferences = retailCustomerPreferences;
	}
	
	public void doSearch(String teamName) {
		System.out.println("\nPrint flights leaving from " + retailCustomerPreferences.departureAirport().toString());
		Calendar departureDate = retailCustomerPreferences.searchDate();
		Flights flights = ServerInterface.INSTANCE.getFlights(retailCustomerPreferences.departureAirport().code(), departureDate, teamName);
		for (Flight flight : flights){
			System.out.println(flight.toString());
		}
	}
}
