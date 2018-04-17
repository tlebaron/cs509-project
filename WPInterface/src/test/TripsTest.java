package test;

import org.junit.Test;

import flight.Flight;
import trip.Trips;
import trip.Trip;

public class TripsTest {
	
	public Trips Initialize(){
		Trips tripList = new Trips();
		
		//create one trip with one leg
		Trip trip1 = new Trip();
		Flight flight1 = new Flight();
		return tripList;
	}

	@Test
	public void testToString() {
		
	}

}
