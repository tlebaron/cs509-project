package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import preferences.RetailCustomerPreferences;
import seat.SeatClass;
import trip.TripType;

public class TestSeatType {
	@Test
	public void testSeatType() {
		RetailCustomerPreferences customerPreferences = new RetailCustomerPreferences();
		assertNull(customerPreferences.getTripClass());
		customerPreferences.setTripClass(SeatClass.COACH);
		assertEquals(customerPreferences.getTripClass(), SeatClass.COACH);
		customerPreferences.setTripClass(SeatClass.FIRSTCLASS);
		assertNotEquals(customerPreferences.getTripClass(), SeatClass.COACH);
		assertEquals(customerPreferences.getTripClass(), SeatClass.FIRSTCLASS);

	}
}
