/**
 * 
 */
package driver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
import trip.TripType;
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
	 * Entry point for CS509 code driver
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
		SearchTrip searchTrip = new SearchTrip(retailCustomerPreferences, airports);
		Trips trips = searchTrip.search(teamName);
		System.out.println("Size of trips: " + trips.size());
		for (Trip t : trips) {
			int count = 0;
			System.out.println("index = "+count+" :");
			System.out.println(t.toString());
			count ++;
		}
		askForOperation();
		Scanner sc = new Scanner(System.in);
		try {
			if(OperationJudgement(sc)) {
				System.out.println("\nEverything done now! Wish you a good day!");
			}else {
				System.out.println("Something is not functioning well. System shutting down.");
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//reserveSeat();
	}
	private static Boolean OperationJudgement(Scanner sc) throws IOException {
		while(true) {
			try {
				int userInput =  Integer.parseInt(sc.next());
				switch (userInput) {
				case 0:return true;
				case 1:return true;
				case 2:return true;
				case 3:return true;
				case 4:{
					// first we should display detail informations
					System.out.println("\ny -- reserve, n -- go back to the list");
					// not finished yet
					return true;
				}
				case 5:return true;
				default:{
					System.out.println("\nPlease input number from 0-5, try again now!");
				}
				}
			} catch (Exception e) {
				System.out.println("\\nPlease input number from 0-5, try again now!");
				continue;
			}
		}
	}
	
	/**
	 * Ask the user to input a number representing what they want to do next 
	 * 
	 * @param nothing
	 */
	private static void askForOperation() {
		System.out.println("\nChoose your operation by input a number below:");
		System.out.println("0 for sort in price");
		System.out.println("1 for sort in total time");
		System.out.println("2 for sort in departure time");
		System.out.println("3 for sort in arrival time");
		System.out.println("4 for checking trip details");
		System.out.println("5 for restart a new search\n");
	}
	
	private static void printBOSFlights() {
		System.out.println("\nprint flights leaving BOS on 2018 May 12");
		Calendar departureDate = Calendar.getInstance();
		departureDate.set(2018,5,12);
		System.out.println("Date : "+departureDate.get(1)+"_"+departureDate.get(2)+"_"+departureDate.get(5));
		Flights flights = ServerInterface.INSTANCE.getDepartingFlights("BOS", departureDate, teamName, airports);
		for (Flight flight : flights){
			System.out.println(flight.toString()); 
		}
	}
	
	private static void initializeSystem() {
		initializeAirports();
		retailCustomerPreferences.getRetailCustomerPreferences(airports);
		//retailCustomerPreferences.printRetailCustomerPreferences();
		initializeTimeOffsets();
		initializeAirplanes();
	}
	
	private static void initializeAirports() {
		// Try to get a list of airports
		airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
	}
	
	private static void initializeTimeOffsets() {
		GMTConversionInterface gmtInterface;
		for(Airport a : airports) {
			gmtInterface = new GMTConversionInterface();
			String timeZone = gmtInterface.getTimeZone(a.latitude(), a.longitude(), retailCustomerPreferences.searchDate());
			a.setTimeZoneID(timeZone);
		}
	}
	
	private static void initializeAirplanes() {
		//System.out.println("\nPrint airplanes");
		//System.out.println("Manu\tModel\t#coach\t#1stclass");
		// try print out airplanes to check if we are doing everything right
		Airplanes airplanes = ServerInterface.INSTANCE.getAirplanes(teamName);
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
