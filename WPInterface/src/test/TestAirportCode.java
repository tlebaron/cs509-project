package test;

import static org.junit.Assert.*;

import org.junit.Test;

import airport.Airport;
import airport.Airports;
import driver.Driver;
import preferences.RetailCustomerPreferences;
import trip.Trips;

/**
 * This class tests that the airport is identifiable by the retail customer using a 3 character code.
 * @author kartikvasu
 *
 */
public class TestAirportCode {
	@Test
	public void testAirportCode() {
		Driver driver = new Driver();
		driver.initializeAirports();
		Airports airports = driver.getAirports();
		assertTrue(airportsContains("BOS", airports));
		assertTrue(airportsContains("SFO", airports));
		assertFalse(airportsContains("ABCDED", airports));
	}

	private boolean airportsContains(String string, Airports airports) {
		// TODO Auto-generated method stub
		for(Airport a : airports) {
			if(a.code().equals(string)) {
				return true;
			}
		}
		return false;
	}
	
}
