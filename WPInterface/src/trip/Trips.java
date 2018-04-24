package trip;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import preferences.RetailCustomerPreferences;
import seat.SeatClass;


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
	private int maxStepOver = 2;
	private HashMap<Integer, Trip> tripMap;
	
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
			if (trip.getFlights().size() > maxStopOver+1) continue;
			hashList.put(this.indexOf(trip), trip);
		}
		
		return hashList;
	}
	public HashMap<Integer, Trip> createList(){
		return createList(2);
	}
		
	public void getNewMaxStepOver(){
		Scanner sc = new Scanner(System.in);
		
		System.out.println("What is the maximum number of step over wanted? (max = 2)");
		while(true) {
			try {
				int maxSO = Integer.parseInt(sc.next());
				if (maxSO < 3 && maxSO > -1) {
					maxStepOver = maxSO;
					break;
					}
				else System.out.println("Please pick a number between 0, 1 and 2");
			} catch (Exception e) {
				System.out.println("Please pick a number between 0, 1 and 2");
				continue;
			}
		}
	}
	
	public Trip getTripForNum(Integer tripNum) {
		return tripMap.get(tripNum);
	}
	
	public void displayTrips(){
		RetailCustomerPreferences preferences = RetailCustomerPreferences.getInstance();
		
		
		System.out.println("Available trips:" + this.size());
		System.out.println("ID \t|\t Departure \t|\t Arrival \t|\t "
				+ "Duration \t|     Price \t|\t Dearture Time \t|\t Arrival Time  \t|\t "
				+ "Seats \t|");
		System.out.println("---------------------------------------------------------------------------------------------------"
				+ "-------------------------------------------------");
		
		HashMap<Integer, Trip> hashList = createList(maxStepOver);
		this.tripMap = hashList;
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
			sb.append(trip.getDurationFormat() + " \t\t|     ");
			DecimalFormat df = new DecimalFormat("##.##");
			df.setRoundingMode(RoundingMode.DOWN);
			sb.append(df.format(trip.getPrice(preferences.getTripClass())) + " \t|\t ");
			sb.append(trip.getTimeFormat(trip.getDepartureTimeLocal()) + " |\t ");
			sb.append(trip.getTimeFormat(trip.getArrivalTimeLocal()) + " |\t ");
			sb.append(trip.getAvailableSeats(preferences.getTripClass()) + " \t|");
			System.out.println(sb);
		}
		
		
	}
}