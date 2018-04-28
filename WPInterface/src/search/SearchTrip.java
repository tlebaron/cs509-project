package search;

import java.util.Calendar;
import java.util.Date;

import airplane.Airplanes;
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
	Airports airports;
	Airplanes airplanes;
	
	public SearchTrip(RetailCustomerPreferences retailCustomerPreferences, Airports airports, Airplanes airplanes) {
		this.retailCustomerPreferences = retailCustomerPreferences;
		this.airports = airports;
		this.airplanes = airplanes;
	}
	
	public Trips searchOnward(String teamName) {
		Trips trips;
		Calendar date = retailCustomerPreferences.searchDate();
		if(retailCustomerPreferences.searchDateType() == DateType.ARRIVAL) {
			SearchTripByArrival search = new SearchTripByArrival(retailCustomerPreferences.departureAirport(), retailCustomerPreferences.arrivalAirport(), date, this.airports, this.airplanes);
			trips = search.searchTripsByArrivalAirport(teamName);
		} else {
			SearchTripByDeparture search = new SearchTripByDeparture(retailCustomerPreferences.departureAirport(), retailCustomerPreferences.arrivalAirport(), date, this.airports, this.airplanes);
			trips = search.searchTripsByDepartureAirport(teamName);
		}
		
		return trips;
  }

	public Trips searchReturn(String teamName) {
		Trips trips;
		if(retailCustomerPreferences.searchDateType() == DateType.ARRIVAL) {
			SearchTripByArrival search = new SearchTripByArrival(retailCustomerPreferences.arrivalAirport(), retailCustomerPreferences.departureAirport(), retailCustomerPreferences.searchReturnDate(), this.airports, this.airplanes);
			trips = search.searchTripsByArrivalAirport(teamName);
		} else {
			SearchTripByDeparture search = new SearchTripByDeparture(retailCustomerPreferences.arrivalAirport(), retailCustomerPreferences.departureAirport(), retailCustomerPreferences.searchReturnDate(), this.airports, this.airplanes);
			trips = search.searchTripsByDepartureAirport(teamName);
		}
		
		return trips;
	}
}
