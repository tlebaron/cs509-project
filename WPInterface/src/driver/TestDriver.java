package driver;

import java.util.Calendar;

import airport.Airport;
import flight.Flight;
import trip.SortTrips;
import trip.Trip;
import trip.Trips;

public class TestDriver {

	public static void main(String[] args) {
		System.out.println("DRIVER FOR TESTS!!!");
		//create trip list
		Airport boston = new Airport();
		Airport chicago = new Airport();
		boston.code("BOS");
		chicago.code("CHC");
		Calendar dep = Calendar.getInstance();
		dep.set(2018, 4, 21);
		Calendar arr1 = Calendar.getInstance();
		arr1.set(2018,  4, 24);
		Calendar arr2 = Calendar.getInstance();
		arr2.set(2018, 4, 23);
		Calendar arr3 = Calendar.getInstance();
		arr3.set(2018,  4, 22);
		Flight flight1 = new Flight();
		flight1.setDepartureAirport(boston);
		flight1.setArrivalAirport(chicago);
		flight1.setDepartureTimeGMT(dep);
		flight1.setArrivalTimeGMT(arr1);
		Flight flight2 = new Flight();
		flight2.setDepartureAirport(boston);
		flight2.setArrivalAirport(chicago);
		flight2.setDepartureTimeGMT(dep);
		flight2.setArrivalTimeGMT(arr2);
		Flight flight3 = new Flight();
		flight3.setDepartureAirport(boston);
		flight3.setArrivalAirport(chicago);
		flight3.setDepartureTimeGMT(dep);
		flight3.setArrivalTimeGMT(arr3);
		Trip trip1 = new Trip();
		trip1.flights.add(flight1);
		Trip trip2 = new Trip();
		trip2.flights.add(flight2);
		Trip trip3 = new Trip();
		trip3.flights.add(flight3);
		
		Trips trips = new Trips();
		trips.add(trip1);
		trips.add(trip3);
		trips.add(trip2);

		//print trip list
		System.out.println(trips);
		//sort
		SortTrips sorter = new SortTrips();
		sorter.sortByTravelTime(trips);
		//print
		System.out.println(trips);
	}

}
