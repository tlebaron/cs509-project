/**
 * 
 */
package driver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import airport.Airport;
import airport.Airports;
import airplane.Airplane;
import airplane.Airplanes;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import preferences.RetailCustomerPreferences;
import search.SearchTrip;
import timeconversion.GMTConversionInterface;
import trip.Trip;
import trip.Trips;
import reservation.reserve;

/**
 * @author blake
 *
 */
public class Driver {

	private static final String teamName = "Team1";
	
	static RetailCustomerPreferences retailCustomerPreferences = RetailCustomerPreferences.getInstance();
	static Airports airports;
	static Airplanes airplanes;
	
	/**
	 * Entry point for CS509 sample code driver
	 * 
	 * This driver will retrieve the list of airports from the CS509 server and print the list 
	 * It will also retrieve the list of airplanes from the CS509 server and print the list
	 * to the console sorted by 3 character airport code
	 * 
	 * @param args is the arguments passed to java vm in format of "CS509.sample teamName" where teamName is a valid team
	 */
	public static void main(String[] args) {
		initializeSystem();
		//printBOSFlights();
		SearchTrip searchTrip = new SearchTrip(retailCustomerPreferences);
		Trips trips = searchTrip.search(teamName);
		System.out.println("Size of trips: " + trips.size());
		for (Trip t : trips) {
			System.out.println(t.toString());
		}
		//reserveSeat();
	}
	
	private static void printBOSFlights() {
		System.out.println("\nprint flights leaving BOS on 2018 May 12");
		Calendar departureDate = Calendar.getInstance();
		departureDate.set(2018,5,12);
		System.out.println("Date : "+departureDate.get(1)+"_"+departureDate.get(2)+"_"+departureDate.get(5));
		Flights flights = ServerInterface.INSTANCE.getFlights("BOS", departureDate, teamName);
		for (Flight flight : flights){
			System.out.println(flight.toString()); 
		}
	}
	
	private static void initializeSystem() {
		initializeAirports();
		retailCustomerPreferences.getRetailCustomerPreferences(airports);
		retailCustomerPreferences.printRetailCustomerPreferences();
		//initializeTimeOffsets();
		initializeAirplanes();
	}
	
	private static void initializeAirports() {
		// Try to get a list of airports
		airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		
		for (Airport airport : airports) {
			System.out.println(airport.toString());
		}
	}
	
	private static void initializeTimeOffsets() {
		GMTConversionInterface gmtInterface;
		for(Airport a : airports) {
			gmtInterface = new GMTConversionInterface();
			Float offset = gmtInterface.getOffset(a.latitude(), a.longitude(), retailCustomerPreferences.searchDate());
			a.gmtOffset(offset);
		}
	}
	
	private static void initializeAirplanes() {
		System.out.println("\nPrint airplanes");
		System.out.println("Manu\tModel\t#coach\t#1stclass");
		// try print out airplanes to check if we are doing everything right
		Airplanes airplanes = ServerInterface.INSTANCE.getAirplanes(teamName);
		for (Airplane airplane : airplanes) {
			System.out.println(airplane.toString());
		}
	}
	
	private static void reserveSeat() {
		System.out.println("\nReserve seats and return result(s)");
//		ServerInterface.INSTANCE.reserve_ticket(teamName, xmlFlights);
		Flight myFlight = new Flight();
		myFlight.setFlightNumber(3809);
		Flights myFlights = new Flights();
		myFlights.add(myFlight);
		String seattype = new String("Coach");// FirstClass
		
		reserve nowreserve = new reserve(myFlights);
		boolean status = nowreserve.reserveFlights(seattype, teamName);
		if (status) {
			System.out.println("Reservation succeed!");
		}
		else {
			System.out.println("Something went wrong, unable to book the seat, plz try again later.");
		}
	}
}
