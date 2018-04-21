package search;

import java.util.Calendar;

import airport.Airport;
import dao.ServerInterface;
import flight.Flight;
import flight.Flights;
import preferences.RetailCustomerPreferences;
import trip.Trip;
import trip.Trips;

public class SearchTripByArrival {
	
	RetailCustomerPreferences retailCustomerPreferences;
	
	public SearchTripByArrival(RetailCustomerPreferences customerPreferences) {
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
			Flight secondFlight = trip.getFlight(0);
			Airport arrivalAirport = secondFlight.getDepartureAirport();
			Calendar date = secondFlight.getDepartureTimeGMT();
			Flights thirdLegCandidateFlights = searchArrivingFlightsOn(teamName, arrivalAirport, date);
			for (Flight f : thirdLegCandidateFlights) {
				if (isValidConnection(f, secondFlight) && f.departureAirport.code().equals(this.retailCustomerPreferences.departureAirport().code())) {
					Trip t = new Trip();
					t.addFlight(trip.getFlight(0));
					t.addFlight(trip.getFlight(1));
					t.addFlightToBeginning(f);
					thirdLegTrips.add(t);
				}
 			}
		}
		return thirdLegTrips;
	}
	
	private Trips findTwoLegTrips(Trips secondLegPossibleTrips) {
		Trips finalTrips = new Trips();
		for (Trip t : secondLegPossibleTrips) {
			if (t.getFlight(0).departureAirport.code().equals(this.retailCustomerPreferences.departureAirport().code())) {
				finalTrips.add(t);
			}
		}
		return finalTrips;
	}

	private Trips searchSecondLegTrips(String teamName, Flights firstLegFlights) {
		Trips secondLegTrips = new Trips();
		for (Flight flight : firstLegFlights) {
			Airport departureAirport = flight.getDepartureAirport();
			Calendar date = flight.getDepartureTimeGMT();
			Flights secondLegCandidateFlights = searchArrivingFlightsOn(teamName, departureAirport, date);
			for (Flight f : secondLegCandidateFlights) {
				if (isValidConnection(f, flight)) {
					Trip t = new Trip();
					t.addFlightToBeginning(flight);
					t.addFlightToBeginning(f);
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

	private Flights searchFirstLegFlights(String teamName, Calendar arrivalDate) {
		return searchArrivingFlightsOn(teamName, this.retailCustomerPreferences.arrivalAirport(), arrivalDate);
	}
	
	private Trips findOneLegTrips(Flights firstLegFlights, Airport departureAirport, Airport arrivalAirport) {
		// TODO Auto-generated method stub
		Trips oneLegTrips = new Trips();
		for (Flight flight : firstLegFlights) {
			if (flight.getDepartureAirport().code().equals(departureAirport.code())) {
				Trip newTrip = new Trip();
				newTrip.addFlight(flight);
				oneLegTrips.add(newTrip);
			}
		}
		return oneLegTrips;
	}

	public Trips searchTripsByArrivalAirport(String teamName, Calendar date) {
		// TODO Auto-generated method stub
		Flights firstLegFlights = searchFirstLegFlights(teamName, date);
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
	
	public Flights searchArrivingFlightsOn(String teamName, Airport arrivalAirport, Calendar arrivalDate) {
		Flights flights = ServerInterface.INSTANCE.getArrivingFlights(arrivalAirport.code(), arrivalDate, teamName);
		return flights;
	}

}
