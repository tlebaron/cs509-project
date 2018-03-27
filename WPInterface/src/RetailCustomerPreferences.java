import java.util.Date;
import airport.Airport;
import seat.SeatClass;

public class RetailCustomerPreferences {
	public SeatClass tripClass;
	public Airport departureAirport;
	public Airport arrivalAirport;
	public Date searchDate;
	public DateType searchDateType;
	public TripType tripType;
	// Do we have this here ?? Shouldn't be better to have a "default parameters" for the layover and
	//the sorting? It doesn't make sense to store it here.
	public int maxLayover;
	
	public void getRetailCustomerPreferences(){
		/* Ask the following information, in this order:
		* Trip type
		* Departure airport: waits for a three character code, need to be in the list
		* Arrival airport: same
		* Date
		* Date Type
		* Class
		*/
		
		System.out.println("What are your preferences??");
	}
}
