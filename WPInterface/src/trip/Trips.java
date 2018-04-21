package trip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import preferences.RetailCustomerPreferences;


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
			string += trip.flights.get(0).getDepartureAirport().code() + " | ";
			string += trip.flights.get(trip.flights.size()-1).getArrivalAirport().code() + " | ";
			string += trip.getDuration() + "mins\n";
		}
		
		return string;
	}
	
	public HashMap<Integer, Trip> createList(int maxStopOver){
		HashMap<Integer, Trip> hashList = new HashMap<Integer, Trip>();
		
		for (Trip trip : this){
			if (trip.getFlights().size() > maxStopOver) continue;
			hashList.put(this.indexOf(trip), trip);
		}
		
		return hashList;
	}
	public HashMap<Integer, Trip> createList(){
		return createList(2);
	}
	
	public void displayTrips(){
		//TODO remove that and deal with it
		int maxStepOver = 2;
		RetailCustomerPreferences preferences = RetailCustomerPreferences.getInstance();
		
		
		System.out.println("Available trips:" + this.size());
		System.out.println("ID \t|\t Departure \t|\t Arrival \t|\t "
				+ "Duration \t|\t Price \t|\t Dearture Time \t|\t Arrival Time  \t|\t "
				+ "Seats \t|");
		
		HashMap<Integer, Trip> hashList = createList(maxStepOver);
		
		// use iterator to run on the map
		Set set = hashList.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()){
			Map.Entry mEntry = (Map.Entry)iterator.next();
			Trip trip = (Trip)mEntry.getValue();
			
			StringBuffer sb = new StringBuffer();
			sb.append(mEntry.getKey() + " \t|\t ");
			sb.append(trip.getDepartureAirportCode() + " \t\t|\t ");
			sb.append(trip.getArrivalAirportCode() + " \t\t|\t ");
			sb.append(trip.getDurationFormat() + " \t\t|\t ");
			sb.append(trip.getPrice(preferences.getTripClass()) + " \t|\t ");
			sb.append(trip.getTimeFormat(trip.getDepartureTimeLocal()) + " |\t ");
			sb.append(trip.getTimeFormat(trip.getArrivalTimeLocal()) + " |\t ");
			sb.append(trip.getAvailableSeats(preferences.getTripClass()) + " \t|");
			System.out.println(sb);
		}
		
		
	}
}