package test;

import java.util.Calendar;

import org.junit.Test;

import airplane.Airplane;
import airport.Airport;
import flight.Flight;
import preferences.RetailCustomerPreferences;
import trip.Trips;
import trip.Trip;
import seat.SeatClass;
import seat.Seats;

public class TestTrips {
	
	public Trips Initialize(){
		Trips tripList = new Trips();
		
		//create one trip with one leg
		Trip trip1 = new Trip();
		Flight flight = new Flight();
		trip1.addFlight(flight);
		flight.setDepartureAirport(new Airport());
		flight.getDepartureAirport().code("BOS");
		flight.setArrivalAirport(new Airport());
		flight.getArrivalAirport().code("ATL");
		flight.setDepartureTimeLocal(Calendar.getInstance());
		flight.getDepartureTimeLocal().set(2018, 04, 22, 9, 00);
		flight.setArrivalTimeLocal(Calendar.getInstance());
		flight.getArrivalTimeLocal().set(2018, 04, 22, 11, 00);
		flight.setAirplane(new Airplane());
		flight.setCoachSeating(new Seats());
		flight.getCoachSeating().setNumberOfSeats(10);
		flight.getCoachSeating().setPrice(10);
		
		tripList.add(trip1);
		
		return tripList;
	}

	@Test
	public void testToString() {
		Trips trips = Initialize();
		RetailCustomerPreferences preferences = RetailCustomerPreferences.getInstance();
		preferences.setTripClass(SeatClass.COACH);
		trips.displayTrips();
	}

}
