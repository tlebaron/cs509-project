package test;

import static org.junit.Assert.*;

import org.junit.Test;

import preferences.RetailCustomerPreferences;
import trip.TripType;

/**
 * Used to test that the trip type can be set by the retail customer and is persisted to the retailcustomerpreferences data.
 * @author kartikvasu
 *
 */
public class TestTripType {

	@Test
	public void testTripType() {
		RetailCustomerPreferences customerPreferences = new RetailCustomerPreferences();
		assertNull(customerPreferences.searchTripType());
		customerPreferences.setTripType(TripType.ONEWAY);
		assertEquals(customerPreferences.searchTripType(), TripType.ONEWAY);
		customerPreferences.setTripType(TripType.ROUNDTRIP);
		assertNotEquals(customerPreferences.searchTripType(), TripType.ONEWAY);
		assertEquals(customerPreferences.searchTripType(), TripType.ROUNDTRIP);
	}
}
