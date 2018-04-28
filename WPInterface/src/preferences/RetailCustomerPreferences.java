package preferences; 

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import airport.Airport;
import airport.Airports;
import seat.SeatClass;
import trip.TripType;

public class RetailCustomerPreferences {
	
	private static RetailCustomerPreferences instance = null;
	
	public SeatClass tripClass;
	Airport departureAirport;
	Airport arrivalAirport;
	Calendar searchDate;
	DateType searchDateType;
	TripType tripType;
	DateType searchReturnDateType;
	Calendar returnDate;
	int maxLayover;
	
	public RetailCustomerPreferences() {
		
	}
	
	public static RetailCustomerPreferences getInstance(){
		if(instance == null){
			instance = new RetailCustomerPreferences();
		}
		return instance;
	}
	
	public SeatClass getTripClass(){
		return this.tripClass;
	}
	public void setTripClass(SeatClass sClass){
		this.tripClass = sClass;
	}
	
	public Calendar searchDate() {
		return searchDate;
	}
	
	public void setSearchDate(Calendar date) {
		this.searchDate = date;
	}
	
	public Calendar searchReturnDate() {
		return returnDate;
	}
	
	public DateType searchDateType() {
		return searchDateType;
	}
	
	public void setSearchDateType(DateType t) {
		this.searchDateType = t;
	}
	
	public TripType searchTripType() {
		return tripType;
	}
	
	public void setTripType(TripType t) {
		this.tripType = t;
	}
	
	public Airport departureAirport() {
		return departureAirport;
	}
	public void setDepartureAirport(Airport a) {
		this.departureAirport = a;
	}
	
	public Airport arrivalAirport() {
		return arrivalAirport;
	}
	
	public void setArrivalAirport(Airport a) {
		this.arrivalAirport = a;
	}
	
	/**
	 * Test function used to print the preferences of a retail customer
	 */
	public void printRetailCustomerPreferences() {
		System.out.println("Trip Class: " + tripClass.toString());
		System.out.println("Departure Airport: " + departureAirport.toString());
		System.out.println("Arrival Airport: " + arrivalAirport.toString());
		System.out.println("Search Date: " + searchDate.getTime().toString());
		System.out.println("Search Date Type: " + searchDateType.toString());
		System.out.println("Trip Type: " + tripType.toString());
		if (tripType == TripType.ROUNDTRIP) {
			System.out.println("Return date type: " + searchReturnDateType.toString() );
			System.out.println("Return date: " + returnDate.getTime().toString() );
		}
	}
	
	/**
	 * Get the information needed to fill the RetailCustomer object
	 * 
	 * @param airports: list of recorded airport, used to check if the airport code provided 
	 * correspond to a valid airport
	 */
	public void getRetailCustomerPreferences(Airports airports){
		/* Ask the following information, in this order:
		* Trip type
		* Departure airport: waits for a three character code, need to be in the list
		* Arrival airport: same
		* Date
		* Date Type
		* Class
		*/
		Scanner sc = new Scanner(System.in);
		
		try {
			tripType = getTripType(sc);
			departureAirport = getAirport(sc, airports, "departure");
			arrivalAirport = getAirport(sc, airports, "arrival");
			searchDateType = getDateType(sc);
			searchDate = getDate(sc);
			if (tripType == TripType.ROUNDTRIP) {
				System.out.println("Now please enter the return trip details.");
				searchReturnDateType = getDateType(sc);
				returnDate = getDate(sc);
				while(returnDate.compareTo(searchDate) < 0) {
					System.out.println("Your return date is before the departure date, please enter again!");
					returnDate = getDate(sc);
				}
			}
			tripClass = getTripClass(sc);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Record the trip class in the preferences
	 * 
	 * @param sc: Scanner
	 * @return SeatClass chosen by the retail customer
	 * @throws IOException
	 */
	private SeatClass getTripClass(Scanner sc) throws IOException {
		System.out.println("What is the Trip Class? (1 - Economy, 2 - First Class)");
		while(true) {
			try {
				String tripClassString = sc.next();
				Integer tripClass = Integer.parseInt(tripClassString);
				switch (tripClass) {
				case 1: return SeatClass.COACH;
				case 2: return SeatClass.FIRSTCLASS;
				default: {
					System.out.println("The Trip Class you entered does not match the two available classes.");
					System.out.println("What is the Trip Class? (1 - Economy, 2 - First Class)");
					}
				}
			} catch (Exception e) {
				System.out.println("The Trip Class you entered does not match the two available classes.");
				System.out.println("What is the Trip Class? (1 - Economy, 2 - First Class)");
				continue;
			}
		}
	}

	/**
	 * Get the date type  wanted by the customer. If departure, the date provided by the customer
	 * is used as a departure date, if arrival, the date provided by the customer is used as a
	 * arrival date
	 * 
	 * @param sc: Scanner
	 * @return DateType chosen by the retail customer: departure or arrival
	 */
	private DateType getDateType(Scanner sc)  {
		System.out.println("What is the Date Type? (1 - Departure date, 2 - Arrival date)");
		while(true) {
			try {
				String dateTypeString =  sc.next();
				Integer dateType = Integer.parseInt(dateTypeString);
				switch (dateType) {
				case 1: return DateType.DEPARTURE;
				case 2: return DateType.ARRIVAL;
				default: {
					System.out.println("The Date Type you entered does not match the two available types, try again.");
					System.out.println("What is the Date Type? (1 - Departure date, 2 - Arrival date)");
					}
				}
			} catch (Exception e) {
				System.out.println("The Date Type you entered does not match the two available date types.");
				System.out.println("What is the Trip Class? (1 - Departure date, 2 - Arrival date)");
				continue;
			}
		}
	}

	/**
	 * Get the date of the trip wanted by the customer. See the result of getDateType() to see if
	 * this date is a departure date or an arrival date
	 * 
	 * @param sc: Scanner
	 * @return Calendar date provided by the customer
	 * @throws IOException
	 */
	private Calendar getDate(Scanner sc) throws IOException {
		System.out.println("Please input the date in the following format: MM-dd-yyyy");
		String dateString = sc.next();
		
		while(true) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
			dateFormat.setLenient(false);
			Calendar searchDate = Calendar.getInstance();
			try {
				searchDate.setTime(dateFormat.parse(dateString));
			} catch (ParseException e) {
				System.out.println("The date you entered was invalid, please enter again.");
				System.out.println("Please input the date in the following format: MM-dd-yyyy");
				dateString = sc.next();
				continue;
			}
			return searchDate;
		}
	}

	/**
	 * Get an airport from the customer. Check if the code provided is linked to a known airport, 
	 * ask for another code is not.
	 * 
	 * @param sc: Scanner
	 * @param airports: list of airports recorded in the initialization
	 * @param type: departure or arrival
	 * @return the airport provided by the customer
	 */
	private Airport getAirport(Scanner sc, Airports airports, String type) {
		System.out.println("What is the " + type + " airport?");
		String selectedAirport = sc.next().toUpperCase();
		// validate that airport exists
		while(true) {
			for (Airport airport : airports) {
				if(airport.code().equals(selectedAirport)) {
					if (type == "arrival" && departureAirport.code().equals(selectedAirport)) break;
					return airport;
				}
			}
			System.out.println("The airport you entered does not match any airport on the database.");
			System.out.println("What is the " + type + " airport?");
			selectedAirport = sc.next().toUpperCase();
		}
	}

	/**
	 * Get the type of trip wanted by the customer
	 * 
	 * @param sc: Scanner
	 * @return TripType ONEWAY or ROUNDTRIP
	 */
	private TripType getTripType(Scanner sc) {
		System.out.println("What is yor trip type? (1 - OneWay, 2 - RoundTrip)");
		while(true) {
			try {
				String tripTypeString =  sc.next();
				Integer tripType = Integer.parseInt(tripTypeString);
				switch (tripType) {
				case 1: return TripType.ONEWAY;
				case 2: return TripType.ROUNDTRIP;
				default: {
					System.out.println("The Trip Type you entered does not match the two available types, try again.");
					System.out.println("What is your trip type? (1 - OneWay, 2 - RoundTrip)");
					}
				}
			} catch (Exception e) {
				System.out.println("The Trip Type you entered does not match the two available types, try again.");
				System.out.println("What is your trip type? (1 - OneWay, 2 - RoundTrip)");
				continue;
			}
		}
	}
}