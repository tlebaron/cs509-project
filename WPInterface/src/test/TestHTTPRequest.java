package test;

import static org.junit.Assert.*;

import org.junit.Test;

import airport.Airport;
import airport.Airports;
import dao.ServerInterface;
import seat.SeatClass;

/**
 * Tests that the HTTP Request is working by making a request for airports and asserting that the result is non-null and airports are filled.
 * This also tests that the XML is parsed by our system and that all fields are filled in correctly.
 * @author kartikvasu
 *
 */
public class TestHTTPRequest {

	@Test
	public void testGetAirports() {
		Airports airports = ServerInterface.INSTANCE.getAirports("Team1");
		assertNotNull(airports);
		assertTrue(airports.size() > 0);
		for(Airport a : airports) {
			assertNotNull(a.code());
			assertNotNull(a.latitude());
			assertNotNull(a.longitude());
		}
	}
	
	@Test 
	public void lockAndUnlock() {
		assertTrue(ServerInterface.INSTANCE.lock("Team1"));
		assertTrue(ServerInterface.INSTANCE.unlock("Team1"));
	}
}
