package search;

import java.util.Calendar;

import airport.Airport;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import preferences.RetailCustomerPreferences;
import trip.Trip;
import trip.Trips;

public class SearchTripByDeparture {
	RetailCustomerPreferences retailCustomerPreferences; 
	
	public SearchTripByDeparture(RetailCustomerPreferences customerPreferences) {
		this.retailCustomerPreferences = customerPreferences;
	}
	
	Trips searchTripsByDepartureAirport(String teamName, Calendar departureDate) {
		// TODO Auto-generated method stub
		Flights firstLegFlights = searchFirstLegFlights(teamName, departureDate);
		//System.out.println("First leg flights: " + firstLegFlights.size());
		Trips collectOneLeg = findOneLegTrips(firstLegFlights, this.retailCustomerPreferences.departureAirport(), this.retailCustomerPreferences.arrivalAirport());
		System.out.println("First leg trips: " + collectOneLeg.size());
		Trips secondLegPossibleTrips = searchSecondLegTrips(teamName, firstLegFlights);
		//System.out.println("second leg poss trips: " + secondLegPossibleTrips.size());
		Trips collectTwoLeg = findTwoLegTrips(secondLegPossibleTrips);
		System.out.println("Two leg trips: " + collectTwoLeg.size());
		Trips collectThreeLeg = searchThirdLegTrips(teamName, secondLegPossibleTrips);
		System.out.println("Three leg trips: " + collectThreeLeg.size());
		Trips allTrips = new Trips();
		allTrips.addAll(collectOneLeg);
		allTrips.addAll(collectTwoLeg);
		allTrips.addAll(collectThreeLeg);
		return allTrips;
	}

	private Trips searchThirdLegTrips(String teamName, Trips secondLegPossibleTrips) {
		Trips thirdLegTrips = new Trips();
		for (Trip trip : secondLegPossibleTrips) {
			Flight secondFlight = trip.getFlight(1);
			Airport arrivalAirport = secondFlight.getArrivalAirport();
			Calendar date = secondFlight.getArrivalTimeGMT();
			Flights thirdLegCandidateFlights = searchDepartingFlightsOn(teamName, arrivalAirport, date);
			for (Flight f : thirdLegCandidateFlights) {
				if (isValidConnection(secondFlight, f) && f.arrivalAirport.code().equals(this.retailCustomerPreferences.arrivalAirport().code())) {
					Trip t = new Trip();
					t.addFlight(trip.getFlight(0));
					t.addFlight(trip.getFlight(1));
					t.addFlight(f);
					thirdLegTrips.add(t);
				}
 			}
		}
		return thirdLegTrips;
	}
	
	private Trips findTwoLegTrips(Trips secondLegPossibleTrips) {
		Trips finalTrips = new Trips();
		for (Trip t : secondLegPossibleTrips) {
			if (t.getFlight(1).arrivalAirport.code().equals(this.retailCustomerPreferences.arrivalAirport().code())) {
				finalTrips.add(t);
			}
		}
		return finalTrips;
	}

	private Trips searchSecondLegTrips(String teamName, Flights firstLegFlights) {
		Trips secondLegTrips = new Trips();
		for (Flight flight : firstLegFlights) {
			Airport arrivalAirport = flight.getArrivalAirport();
			Calendar date = flight.getArrivalTimeGMT();
			Flights secondLegCandidateFlights = searchDepartingFlightsOn(teamName, arrivalAirport, date);
			for (Flight f : secondLegCandidateFlights) {
				if (isValidConnection(flight, f)) {
					Trip t = new Trip();
					t.addFlight(flight);
					t.addFlight(f);
					secondLegTrips.add(t);
				}
 			}
		}
		return secondLegTrips;
	}
	
	private boolean isValidConnection(Flight firstFlight, Flight secondFlight) {
		boolean ret = firstFlight.arrivalAirport.code().equals(secondFlight.departureAirport.code());
		long diff = secondFlight.getDepartureTimeGMT().getTimeInMillis() - firstFlight.getArrivalTimeGMT().getTimeInMillis();
		diff = diff / 60000;
		ret = ret && diff <= 180 && diff >= 30;
		ret = ret && !firstFlight.departureAirport.code().equals(secondFlight.arrivalAirport.code());
		return ret;
	}

	private Flights searchFirstLegFlights(String teamName, Calendar departureDate) {
		return searchDepartingFlightsOn(teamName, this.retailCustomerPreferences.departureAirport(), departureDate);
	}
	
	private Trips findOneLegTrips(Flights firstLegFlights, Airport departureAirport, Airport arrivalAirport) {
		// TODO Auto-generated method stub
		Trips oneLegTrips = new Trips();
		for (Flight flight : firstLegFlights) {
			if (flight.getArrivalAirport().code().equals(arrivalAirport.code())) {
				Trip newTrip = new Trip();
				newTrip.addFlight(flight);
				oneLegTrips.add(newTrip);
			}
		}
		return oneLegTrips;
	}

	public Flights searchDepartingFlightsOn(String teamName, Airport departureAirport, Calendar departureDate) {
		Flights flights = ServerInterface.INSTANCE.getDepartingFlights(departureAirport.code(), departureDate, teamName);
		return flights;
	}
}
