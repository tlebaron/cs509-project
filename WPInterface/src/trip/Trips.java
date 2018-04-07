package trip;

import java.util.ArrayList;

/**
 * This class aggregates a number of Trips. The aggregate is implemented as an ArrayList.
 * Airplanes can be added to the aggregate using the ArrayList interface. Objects can 
 * be removed from the collection using the ArrayList interface.
 * 
 * @author tlb
 *
 */
public class Trips extends ArrayList<Trip> {
	private static final long serialVersionUID = 1L;
	
	public String toString(){
		String string = "List of trips:\n";
		
		for (Trip trip : this){
			string += trip.flights.get(0).departureAirport.code() + " | ";
			string += trip.flights.get(trip.flights.size()-1).arrivalAirport.code() + " | ";
			string += trip.getDuration() + "mins\n";
		}
		
		return string;
	}
	
}