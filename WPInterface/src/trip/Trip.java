package trip;

import flight.Flight;
import flight.Flights;

public class Trip {
	TripType type;
	Trip followingTrip;
	Flights flights;
	
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
}
