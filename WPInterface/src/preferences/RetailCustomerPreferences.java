package preferences; 

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import airport.Airport;
import airport.Airports;
import trip.TripType;

public class RetailCustomerPreferences {
	
	SeatClass tripClass;
	Airport departureAirport;
	Airport arrivalAirport;
	Date searchDate;
	DateType searchDateType;
	TripType tripType;
	DateType searchReturnDateType;
	Date returnDate;
	// Do we have this here ?? Shouldn't be better to have a "default parameters" for the layover and
	//the sorting? It doesn't make sense to store it here.
	int maxLayover;
	
	public RetailCustomerPreferences() {
		
	}
	
	public Date getSearchDate() {
		return searchDate;
	}
	
	public void printRetailCustomerPreferences() {
		System.out.println("Trip Class: " + tripClass.toString());
		System.out.println("Departure Airport: " + departureAirport.toString());
		System.out.println("Arrival Airport: " + arrivalAirport.toString());
		System.out.println("Search Date: " + searchDate.toString());
		System.out.println("Search Date Type: " + searchDateType.toString());
		System.out.println("Trip Type: " + tripType.toString());
		if (tripType == TripType.ROUNDTRIP) {
			System.out.println("Return date type: " + searchReturnDateType.toString() );
			System.out.println("Return date: " + returnDate.toString() );
		}
	}
	
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private SeatClass getTripClass(Scanner sc) throws IOException {
		System.out.println("What is the Trip Class? (1 - Economy, 2 - First Class)");
		int tripClass = sc.nextInt();
		while(true) {
			switch (tripClass) {
			case 1: return SeatClass.ECONOMY;
			case 2: return SeatClass.FIRSTCLASS;
			default: {
				System.out.println("The Trip Class you entered does not match the two available classes.");
				System.out.println("What is the Trip Class? (1 - Economy, 2 - First Class)");
				tripClass = sc.nextInt();
				}
			}
		}
	}

	private DateType getDateType(Scanner sc)  {
		System.out.println("What is the Date Type? (1 - Departure date, 2 - Arrival date)");
		int dateType = sc.nextInt();
		while(true) {
			switch (dateType) {
			case 1: return DateType.DEPARTURE;
			case 2: return DateType.ARRIVAL;
			default: {
				System.out.println("The Date Type you entered does not match the two available types, try again.");
				System.out.println("What is the Date Type? (1 - Departure date, 2 - Arrival date)");
				dateType = sc.nextInt();
				}
			}
		}
	}

	private Date getDate(Scanner sc) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Please input the date in the following format: MM-dd-yyyy");
		String dateString = sc.next();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Date searchDate = null;
		try {
			searchDate = dateFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return searchDate;
	}

	private Airport getAirport(Scanner sc, Airports airports, String type) {
		System.out.println("What is the " + type + " airport?");
		String departureAirport = sc.next().toUpperCase();
		// validate that airport exists
		while(true) {
			for (Airport airport : airports) {
				if(airport.code().equals(departureAirport)) {
					return airport;
				}
			}
			System.out.println("The airport you entered does not match any airport on the database.");
			System.out.println("What is the " + type + " airport?");
			departureAirport = sc.next().toUpperCase();
		}
	}

	private TripType getTripType(Scanner sc) {
		System.out.println("What is yor trip type? (1 - OneWay, 2 - RoundTrip)");
		int tripType = sc.nextInt();
		while(true) {
			switch (tripType) {
			case 1: return TripType.ONEWAY;
			case 2: return TripType.ROUNDTRIP;
			default: {
				System.out.println("The Trip Type you entered does not match the two available types, try again.");
				System.out.println("What is your trip type? (1 - OneWay, 2 - RoundTrip)");
				tripType = sc.nextInt();
				}
			}
		}
	}
}