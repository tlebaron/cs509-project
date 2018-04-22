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

public class TestSort {

	public Trips Initialize(){
		Trips trips = new Trips();
		trips.add(new Trip());
		trips.add(new Trip());
				
		trips.get(0).addFlight(new Flight());
		Flight flight = trips.get(0).flights.get(0);
		flight.setDepartureTimeGMT(Calendar.getInstance());
		flight.getDepartureTimeGMT().set(2018, 04, 22, 9, 00);
		flight.setArrivalTimeGMT(Calendar.getInstance());
		flight.getArrivalTimeGMT().set(2018, 04, 22, 15, 00);
		flight.setDepartureTimeLocal(Calendar.getInstance());
		flight.getDepartureTimeLocal().set(2018, 04, 22, 9, 00);
		flight.setArrivalTimeLocal(Calendar.getInstance());
		flight.getArrivalTimeLocal().set(2018, 04, 22, 15, 00);
		flight.setDepartureAirport(new Airport());
		flight.getDepartureAirport().code("BOS");
		flight.setArrivalAirport(new Airport());
		flight.getArrivalAirport().code("ATL");
		flight.setAirplane(new Airplane());
		flight.getAirplane().num_coachSeats(15);
		flight.setCoachSeating(new Seats());
		flight.getCoachSeating().setNumberOfSeats(10);
		flight.getCoachSeating().setPrice(10);
		
		trips.get(1).addFlight(new Flight());
		flight = trips.get(1).flights.get(0);
		flight.setDepartureTimeGMT(Calendar.getInstance());
		flight.getDepartureTimeGMT().set(2018, 04, 22, 9, 00);
		flight.setArrivalTimeGMT(Calendar.getInstance());
		flight.getArrivalTimeGMT().set(2018, 04, 22, 13, 00);
		flight.setDepartureTimeLocal(Calendar.getInstance());
		flight.getDepartureTimeLocal().set(2018, 04, 22, 6, 00);
		flight.setArrivalTimeLocal(Calendar.getInstance());
		flight.getArrivalTimeLocal().set(2018, 04, 22, 16, 00);
		flight.setDepartureAirport(new Airport());
		flight.getDepartureAirport().code("BOS");
		flight.setArrivalAirport(new Airport());
		flight.getArrivalAirport().code("ATL");
		flight.setAirplane(new Airplane());
		flight.getAirplane().num_coachSeats(15);
		flight.setCoachSeating(new Seats());
		flight.getCoachSeating().setNumberOfSeats(10);
		flight.getCoachSeating().setPrice(20);
		
		return trips;
	}
	
	@Test
	public void test() {
		Trips trips = Initialize();
		RetailCustomerPreferences preferences = RetailCustomerPreferences.getInstance();
		preferences.setTripClass(SeatClass.COACH);
		trips.displayTrips();
		SortTrips sorter = new SortTrips();
		
		trips = sorter.sortByTravelTime(trips);
		System.out.println("Sorted by duration");
		trips.displayTrips();
		
		trips = sorter.sortByPrice(trips, SeatClass.COACH);
		System.out.println("Sorted by price");
		trips.displayTrips();
		
		trips = sorter.sortByDepartureDate(trips);
		System.out.println("Sorted by departure date");
		trips.displayTrips();
		
		trips = sorter.sortByArrivalDate(trips);
		System.out.println("Sorted by arrival date");
		trips.displayTrips();
	}

}
