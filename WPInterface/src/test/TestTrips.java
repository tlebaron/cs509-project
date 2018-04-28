package test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Test;

import airplane.Airplanes;
import airport.Airport;
import airport.Airports;
import driver.Driver;
import preferences.DateType;
import preferences.RetailCustomerPreferences;
import search.SearchTrip;
import seat.SeatClass;
import trip.Trip;
import trip.TripType;
import trip.Trips;
/**
 * This contains various trip testing functions
 * @author kartikvasu
 *
 */
public class TestTrips {

	@Test
	public void testDate() {
		Driver d = new Driver();
		d.initializeAirports();
		Airports a = d.getAirports();
		RetailCustomerPreferences c = initRetailCustomerPreferences(a);
		d.initializeTimeOffsets(false);
		d.initializeAirplanes();
		Airplanes airplanes = d.getAirplanes();
		
		SearchTrip s = new SearchTrip(c, a, airplanes);
		Trips trips = s.searchOnward("Team1");
		
		for (Trip t : trips) {
			assertEquals(t.getArrivalTimeGMTTest().get(Calendar.DAY_OF_MONTH), 16, 2);
			assertEquals(t.getArrivalTimeGMTTest().get(Calendar.MONTH), Calendar.MAY);
			assertEquals(t.getArrivalTimeGMTTest().get(Calendar.YEAR), 2018);
		}
	}
	
	@Test
	public void testLayoverTimes() {
		Driver d = new Driver();
		d.initializeAirports();
		Airports a = d.getAirports();
		RetailCustomerPreferences c = initRetailCustomerPreferences(a);
		d.initializeTimeOffsets(false);
		d.initializeAirplanes();
		Airplanes airplanes = d.getAirplanes();
		
		SearchTrip s = new SearchTrip(c, a, airplanes);
		Trips trips = s.searchOnward("Team1");
		for(Trip t : trips) {
			if(t.getFlights().size() == 2) {
				long layovertime = (t.getFlight(1).getDepartureTimeGMT().getTimeInMillis() - t.getFlight(0).getArrivalTimeGMT().getTimeInMillis()) / 60000;
				assertTrue(layovertime >= 30);
				assertTrue(layovertime<= 180);
			}
			if(t.getFlights().size() == 3) {
				long layovertime = (t.getFlight(1).getDepartureTimeGMT().getTimeInMillis() - t.getFlight(0).getArrivalTimeGMT().getTimeInMillis()) / 60000;
				assertTrue(layovertime >= 30);
				assertTrue(layovertime <= 180);
				layovertime = (t.getFlight(2).getDepartureTimeGMT().getTimeInMillis() - t.getFlight(1).getArrivalTimeGMT().getTimeInMillis()) / 60000;
				assertTrue(layovertime >= 30);
				assertTrue(layovertime <= 180);
			}
		}
	}

	private RetailCustomerPreferences initRetailCustomerPreferences(Airports a) {
		RetailCustomerPreferences customer = new RetailCustomerPreferences();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dateFormat.setLenient(false);
		Calendar searchDate = Calendar.getInstance();
		try {
			searchDate.setTime(dateFormat.parse("05-15-2018"));
		} catch (ParseException e) {
			System.out.println("The date you entered was invalid, please enter again.");
			return null;
		}
		customer.setSearchDate(searchDate);
		customer.setTripType(TripType.ONEWAY);
		customer.setTripClass(SeatClass.COACH);
		customer.setSearchDateType(DateType.DEPARTURE);
		customer.setArrivalAirport(findAirport("BOS", a));
		customer.setDepartureAirport(findAirport("JFK", a));
		return customer;
	}

	private Airport findAirport(String string, Airports a) {
		// TODO Auto-generated method stub
		for(Airport air : a) {
			if (air.code().equals(string)) {
				return air;
			}
		}
		return null;
	}
}
