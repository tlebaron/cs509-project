package trip;

import flight.Flight;
import static java.lang.Math.toIntExact;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import flight.Flights;
import preferences.RetailCustomerPreferences;
import seat.SeatClass;

public class Trip {
	TripType type;
	Trip followingTrip;
	public Flights flights;
	
	public Trip(){
		flights = new Flights();
	}
	
	public void addFlight(Flight flight){
		flights.add(flight);
	}
	
	public void addFlightToBeginning(Flight flight) {
		flights.add(0, flight);
  }
  
	public Flights getFlights(){
		return this.flights;
	}
	
	public Flight getFlight(int index){
		return flights.get(index);
	}
	
	public Trip getfollowingTrip() {
		return this.followingTrip;
	}
	
	/**
	 * @return String of all the flights.toString()
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("List of flights of this trip:\n");
		
		for(Flight flight : flights){
			sb.append(flight.toString());
		}
		
		
		return sb.toString();
	}
	
	/**
	 * @return time between departure of the first flight and the arrival of the last flight in minutes
	 */
	public int getDuration(){
		return toIntExact((getArrivalTimeGMT() - getDepartureTimeGMT())  / (1000 * 60));
	}
	/**
	 * @return String in format HHhMM of the time between the departure of the first flight and 
	 * the arrival of the last flight
	 */
	public String getDurationFormat(){
		String duration;
		int durationMin = this.getDuration();
		String hours = String.valueOf((durationMin - durationMin%60)/60);
		String min = String.valueOf(durationMin%60);
		if (min.length() == 1) min = "0"+min;
		duration = ( hours + "h" + min);
		return duration;
	}
	
	/**
	 * Get total price of the trip by summing the price of all its flight
	 * 
	 * @param seatClass: class of seat for which the search must be made
	 * @return price
	 */
	public double getPrice(SeatClass seatClass){
		double price = 0;
		switch(seatClass){
			case COACH:
				for (Flight flight : flights){
					price += flight.getCoachSeating().getPrice();
				}
				break;
			case FIRSTCLASS:
				for (Flight flight : flights){
					price += flight.getFirstClassSeating().getPrice();
				}
				break;
			default:
				return -1;
		}
		return price;
	}
	
	/**
	 * @return time of departure of the first flight in GMT in milliseconds
	 */
	public long getDepartureTimeGMT(){
		return flights.get(0).getDepartureTimeGMT().getTime().getTime();
	}
	/**
	 * @return time of arrival of the last flight in GMT in milliseconds
	 */
	public long getArrivalTimeGMT(){
		return flights.get(flights.size()-1).getArrivalTimeGMT().getTime().getTime();
	}
	/**
	 * @return time of departure of the first flight in local time in milliseconds
	 */
	public long getDepartureTimeLocal(){
		return flights.get(0).getDepartureTimeLocal().getTime().getTime();
	}
	/**
	 * @return time of arrival of the last flight in local time in milliseconds
	 */
	public long getArrivalTimeLocal(){
		return flights.get(flights.size()-1).getArrivalTimeLocal().getTime().getTime();
	}
	
	/**
	 * Get a string in the format HHhMM from a time in milliseconds
	 * @param time
	 * @return
	 */
	public String getTimeFormat(long time){
		DateFormat formater =  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
		String date = formater.format(new Date(time));
		return date;
	}
	
	/**
	 * @return departure airport code of the first flight
	 */
	public String getDepartureAirportCode(){
		return this.getFlights().get(0).getDepartureAirport().code();
	}
	/**
	 * @return arrival airport code of the last flight
	 */
	public String getArrivalAirportCode(){
		return this.getFlights().get(this.getFlights().size()-1).getArrivalAirport().code();
	}
	
	/**
	 * Check the number of available seat for each flight in a specific class and return the minimum
	 * @param tripClass
	 * @return number of available seat in a trip for a class tripClass
	 */
	public int getAvailableSeats(SeatClass tripClass){
		int availableSeats = -1;
		
		switch(tripClass){
			case COACH:
				availableSeats = this.flights.get(0).getAirplane().getCoachSeats().getNumberOfSeats() - this.flights.get(0).getCoachSeating().getNumberOfSeats();
				for (Flight flight : flights){
					availableSeats = Math.min(availableSeats, flight.getAirplane().getCoachSeats().getNumberOfSeats() - flight.getCoachSeating().getNumberOfSeats());
				}
				break;
			case FIRSTCLASS:
				availableSeats = this.flights.get(0).getAirplane().getFirstClassSeats().getNumberOfSeats() - this.flights.get(0).getFirstClassSeating().getNumberOfSeats();
				for (Flight flight : flights){
					availableSeats = Math.min(availableSeats, flight.getAirplane().getFirstClassSeats().getNumberOfSeats() - flight.getFirstClassSeating().getNumberOfSeats());
				}
				break;
		}
		
		return availableSeats;
	}

	public void setReturnTrip(Trip returnSelectedTrip) {
		// TODO Auto-generated method stub
		this.followingTrip = returnSelectedTrip;
	}
}
