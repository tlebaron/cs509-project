package trip;

import flight.Flight;
import static java.lang.Math.toIntExact;
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
	
	public void getFlight(int index){
		flights.get(index);
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("List of flights of this trip:");
		
		for(Flight flight : flights){
			sb.append(flight.toString());
		}
		
		return sb.toString();
	}
	
	public int getDuration(){
		return toIntExact((flights.get(flights.size()-1).arrivalTimeGMT.getTime().getTime() - 
				flights.get(0).departureTimeGMT.getTime().getTime())  / (1000 * 60));
	}
	
	public double getPrice(SeatClass seatClass){
		double price = 0;
		for (Flight flight : flights){
			price += flight.getPrice(seatClass);
		}
		return price;
	}
}
