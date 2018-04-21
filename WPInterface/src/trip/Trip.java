package trip;

import flight.Flight;
import static java.lang.Math.toIntExact;

import java.text.DateFormat;
import java.util.Date;

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
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("List of flights of this trip:\n");
		
		for(Flight flight : flights){
			sb.append(flight.toString());
		}
		
		
		return sb.toString();
	}
	
	public int getDuration(){
		return toIntExact((getArrivalTimeGMT() - getDepartureTimeGMT())  / (1000 * 60));
	}
	public String getDurationFormat(){
		String duration;
		int durationMin = this.getDuration();
		String hours = String.valueOf((durationMin - durationMin%60)/60);
		String min = String.valueOf(durationMin%60);
		if (min.length() == 1) min = "0"+min;
		duration = ( hours + "h" + min);
		return duration;
	}
	
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
	
	public long getDepartureTimeGMT(){
		return flights.get(0).getDepartureTimeGMT().getTime().getTime();
	}
	
	public long getArrivalTimeGMT(){
		return flights.get(flights.size()-1).getArrivalTimeGMT().getTime().getTime();
	}
	
	public long getDepartureTimeLocal(){
		return flights.get(0).getDepartureTimeLocal().getTime().getTime();
	}
	
	public long getArrivalTimeLocal(){
		return flights.get(flights.size()-1).getArrivalTimeLocal().getTime().getTime();
	}
	
	public String getTimeFormat(long time){
		DateFormat formater =  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		String date = formater.format(new Date(time));
		return date;
	}
	
	public String getDepartureAirportCode(){
		return this.getFlights().get(0).getDepartureAirport().code();
	}
	
	public String getArrivalAirportCode(){
		return this.getFlights().get(this.getFlights().size()-1).getArrivalAirport().code();
	}
	
	public int getAvailableSeats(SeatClass tripClass){
		int availableSeats = -1;
		
		switch(tripClass){
			case COACH:
				availableSeats = this.flights.get(0).getCoachSeating().getNumberOfSeats();
				for (Flight flight : flights){
					availableSeats = Math.min(availableSeats, flight.getCoachSeating().getNumberOfSeats());
				}
				break;
			case FIRSTCLASS:
				availableSeats = this.flights.get(0).getFirstClassSeating().getNumberOfSeats();
				for (Flight flight : flights){
					availableSeats = Math.min(availableSeats, flight.getFirstClassSeating().getNumberOfSeats());
				}
				break;
		}
		
		return availableSeats;
	}
}
