package preferences; 

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import airport.Airport;
import airport.Airports;

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
			}
			tripClass = getTripClass(sc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	private SeatClass getTripClass(Scanner sc) throws IOException {
		System.out.println("What is the Trip Class? (1 - Economy, 2 - First Class)");
		int tripType = sc.nextInt();
		switch (tripType) {
		case 1: return SeatClass.ECONOMY;
		case 2: return SeatClass.FIRSTCLASS;
		default: System.out.println("The Trip Class you entered does not match the two available classes."); 
			throw new IOException("Unknown trip class entered."); 
		}
	}

	private DateType getDateType(Scanner sc) throws IOException {
		System.out.println("What is the Date Type? (1 - Departure date, 2 - Arrival date)");
		int tripType = sc.nextInt();
		switch (tripType) {
		case 1: return DateType.DEPARTURE;
		case 2: return DateType.ARRIVAL;
		default: System.out.println("The Date Type you entered does not match the two available types"); 
			throw new IOException("Unknown trip type entered."); 
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

	private Airport getAirport(Scanner sc, Airports airports, String type) throws IOException {
		System.out.println("What is the " + type + " airport?");
		String departureAirport = sc.next().toUpperCase();
		// validate that airport exists
		for (Airport airport : airports) {
			if(airport.code().equals(departureAirport)) {
				return airport;
			}
		}
		throw new IOException("Invalid " + type + " airport entered.");
	}

	private TripType getTripType(Scanner sc) throws IOException {
		System.out.println("What is yor trip type? (1 - OneWay, 2 - RoundTrip)");
		int tripType = sc.nextInt();
		switch (tripType) {
		case 1: return TripType.ONEWAY;
		case 2: return TripType.ROUNDTRIP;
		default: System.out.println("The Trip Type you entered does not match the two available types"); 
			throw new IOException("Unknown trip type entered."); 
		}
	}
}