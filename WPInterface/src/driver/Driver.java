/**
 * 
 */
package driver;

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
import timeconversion.GMTConversionInterface;

/**
 * @author blake
 *
 */
public class Driver {

	static RetailCustomerPreferences userPreferences;
	static GMTConversionInterface gmtInterface;
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
		
		userPreferences = new RetailCustomerPreferences();
		gmtInterface = new GMTConversionInterface();
		
		//String teamName = args[0];
		String teamName = "Team1";
		// Try to get a list of airports
		Airports airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		
		for (Airport airport : airports) {
			System.out.println(airport.toString());
		}
		
		userPreferences.getRetailCustomerPreferences(airports);
		
		System.out.println("Print User Preferences");
		userPreferences.printRetailCustomerPreferences();
		
		/**
		for(Airport a : airports) {
			gmtInterface.getOffset(a.latitude(), a.longitude(), userPreferences.getSearchDate());
		}
		**/
		System.out.println(gmtInterface.getOffset(airports.get(0).latitude(), airports.get(0).longitude(), userPreferences.getSearchDate()));

		System.out.println("Print airplanes");
		// try print out airplanes to check if we are doing everything right
		Airplanes airplanes = ServerInterface.INSTANCE.getAirplanes(teamName);
//		Collections.sort(airplanes);
		for (Airplane airplane : airplanes) {
			System.out.println(airplane.toString());
		}
		
		System.out.println("\nprint flights leaving BOS on 2018 May 12");
		Calendar departureDate = Calendar.getInstance();
		departureDate.set(2018,5,12);
		System.out.println("date : "+departureDate.get(1)+"_"+departureDate.get(2)+"_"+departureDate.get(5));
		Flights flights = ServerInterface.INSTANCE.getFlights("BOS", departureDate, teamName);
		for (Flight flight : flights){
			System.out.println(flight.toString());
		}
	}
}
