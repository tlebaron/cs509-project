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
import trip.SortTrips;
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
		
		//Get retail customer preferences
		SearchTrip searchTrip = new SearchTrip(retailCustomerPreferences, airports, airplanes);
		Trips onwardTrips = searchTrip.searchOnward(teamName);
		
		while (onwardTrips.size() == 0){
			System.out.println("No trips found for this search. Please make another research");
			retailCustomerPreferences.getRetailCustomerPreferences(airports);
			searchTrip = new SearchTrip(retailCustomerPreferences, airports, airplanes);
			onwardTrips = searchTrip.searchOnward(teamName);
		}
		
		System.out.println("Found " + onwardTrips.size() + " onward trips.");
		
		//wait until the user select to display a trip details
		int reserve = 0;
		Trip onwardSelectedTrip = null;
		Trip returnSelectedTrip = null;
		do{
			int userOption = 0;
			while (userOption != 5) {
				onwardTrips.displayTrips();
				showOptions();
				userOption = getOption();
				handleOption(onwardTrips, userOption);
			}
			
			onwardSelectedTrip = getUserTrip(onwardTrips);
			returnSelectedTrip = null;
			if(retailCustomerPreferences.searchTripType() == TripType.ROUNDTRIP) {
				Trips returnTrips = searchTrip.searchReturn(teamName);
				System.out.println("Found " + returnTrips.size() + " return trips.");
				userOption = 0;
				while (userOption != 5) {
					returnTrips.displayTrips();
					showOptions();
					userOption = getOption();
					handleOption(returnTrips, userOption);
				}
				returnSelectedTrip = getUserTrip(returnTrips);
			}
			
			onwardSelectedTrip.setReturnTrip(returnSelectedTrip);
			showSelectedTripDetails(onwardSelectedTrip, returnSelectedTrip);
			
			do {
				System.out.println("Do you want to reserve this trip? (1 - Yes, 0 - No)");
				Scanner sc = new Scanner(System.in);
				reserve = Integer.parseInt(sc.next());
			} while (reserve != 0 && reserve != 1);
		} while (reserve == 0);
		//System.out.println("RESERVATION");
		//reserveSeat();
		reserveSeat(onwardSelectedTrip.getFlights());
		if(retailCustomerPreferences.searchTripType() == TripType.ROUNDTRIP) {
			System.out.println("\nBooking flights for return selected trip...");
		 	reserveSeat(onwardSelectedTrip.getfollowingTrip().getFlights());
		}
	}
	
	private static void showSelectedTripDetails(Trip onwardSelectedTrip, Trip returnSelectedTrip) {
		// TODO Auto-generated method stub
		System.out.println("Onward Trip Details: " + onwardSelectedTrip.toString());
		if(returnSelectedTrip != null) {
			System.out.println("Return Trip Details: " + returnSelectedTrip.toString());
		}
	}

	private static Trip getUserTrip(Trips trips) {
		// TODO Auto-generated method stub
		System.out.println("Which trip do you want to pick?");
		Scanner sc = new Scanner(System.in);
		int option = -1;
		while(option == -1) {
			String optionString =  sc.next();
			option = Integer.parseInt(optionString);
			if(option < 0 || option >= trips.totalTrips()) {
				option = -1;
				System.out.println("The trip number you entered was wrong, please try again!");
			}
		}
		Trip userTrip = trips.getTripForNum(option);
		return userTrip;
	}

	private static int handleOption(Trips trips, int userOption) {
		SortTrips sorter = new SortTrips();
		switch(userOption) {
		case 0: // sort by price
			trips = sorter.sortByPrice(trips, retailCustomerPreferences.getTripClass());
			break;
		case 1:  // sort by total time
			trips = sorter.sortByTravelTime(trips);
			break;
		case 2: // sort by departure time
			trips = sorter.sortByDepartureDate(trips);
			break;
		case 3: // sort by arrival time
			trips = sorter.sortByArrivalDate(trips);
			break;
		case 4: //change number of stopovers
			trips.getNewMaxStepOver();
			break;
		case 5: // select trip
			return 5;
		default: 
			System.out.println("The option you entered does not work! Please try again!");
			break;
		}
		return 0;
	}

	private static int getOption() {
		System.out.println("What is your option?");
		Scanner sc = new Scanner(System.in);
		int option = -1;
		while(option > 5 || option < 0) {
			try {
				String optionString =  sc.next();
				option = Integer.parseInt(optionString);
				if(option > 5 || option < 0) {
					System.out.println("The option you entered was wrong, please enter again!");
				}
			} catch (Exception e) {
				System.out.println("The option you entered is not valid, please try again.");
				System.out.println("What is your option?");
				continue;
			}
		}
		return option;
	}
	
	/**
	 * Ask the user to input a number representing what they want to do next 
	 * 
	 * @param nothing
	 */
	private static void showOptions() {
		System.out.println("\nChoose your operation by input a number below:");
		System.out.println("0 Sort By Price");
		System.out.println("1 for Sort By Total Time");
		System.out.println("2 for Sort By Departure Time");
		System.out.println("3 for Sort By Arrival Time");
		System.out.println("4 for change number of stopovers\n");
		System.out.println("5 to Choose Trip");
	}
	
	private static void initializeSystem() {
		initializeAirports();
		retailCustomerPreferences.getRetailCustomerPreferences(airports);
		//retailCustomerPreferences.printRetailCustomerPreferences();
		initializeTimeOffsets(true);
		initializeAirplanes();
	}
	
	public static void initializeAirports() {
		// Try to get a list of airports
		airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
	}
	
	public Airports getAirports() {
		return this.airports;
	}
	
	public static void initializeTimeOffsets(boolean callInterface) {
		GMTConversionInterface gmtInterface;
		for(Airport a : airports) {
			gmtInterface = new GMTConversionInterface();
			String timeZone;
			if(callInterface) {
				timeZone = gmtInterface.getTimeZone(a.latitude(), a.longitude(), retailCustomerPreferences.searchDate());
			} else {
				timeZone = "GMT";
			}
			a.setTimeZoneID(timeZone);
		}
	}
	
	public static void initializeAirplanes() {
		//System.out.println("\nPrint airplanes");
		//System.out.println("Manu\tModel\t#coach\t#1stclass");
		// try print out airplanes to check if we are doing everything right
		airplanes = ServerInterface.INSTANCE.getAirplanes(teamName);
	}
	
	private static void reserveSeat(Flights myFlights) {
//		System.out.println("\nReserve seats and return result(s)");
//		ServerInterface.INSTANCE.reserve_ticket(teamName, xmlFlights);

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

	public Airplanes getAirplanes() {
		return this.airplanes;
	}
}
