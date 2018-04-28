package test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import airplane.Airplane;
import airport.Airport;
import flight.Flight;
import preferences.RetailCustomerPreferences;
import seat.SeatClass;
import seat.Seats;
import trip.SortTrips;
import trip.Trip;
import trip.Trips;

public class TestFilterByStopOvers {
	
	@Test
	public void testFilterByStopOvers() {
		Trips trips = initializeTrips();
		assertEquals(3, trips.createList(2).size());
		assertEquals(2, trips.createList(1).size());
		assertEquals(1, trips.createList(0).size());
	}
	
	private Trips initializeTrips() {
		Trips trips = new Trips();
		
		Trip tripA = new Trip();
		tripA.addFlight(new Flight());
		tripA.addFlight(new Flight());
		tripA.addFlight(new Flight());
		
		Trip tripB = new Trip();
		tripB.addFlight(new Flight());
		
		Trip tripC = new Trip();
		tripC.addFlight(new Flight());
		tripC.addFlight(new Flight());
		
		trips.add(tripA);
		trips.add(tripB);
		trips.add(tripC);
		return trips;
	}
}
