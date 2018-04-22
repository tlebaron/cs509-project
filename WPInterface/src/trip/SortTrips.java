package trip;

import java.util.Collections;
import java.util.Comparator;

import seat.SeatClass;

public class SortTrips {
	/**
	 * Sort the input trip list by duration: fastest first
	 * 
	 * @param listTripsInput list of trip to sort
	 * @return listTripsInput list of trip sorted
	 */
	public Trips sortByTravelTime(Trips listTripsInput){
		Collections.sort(listTripsInput, new Comparator<Trip>() {
			@Override
			public int compare(Trip trip1, Trip trip2){
				return trip1.getDuration() - trip2.getDuration();
			}
		});
		
		return listTripsInput;
	}
	

	/**
	 * Sort the input trip list by price: cheapest first
	 * 
	 * @param listTripsInput list of trip to sort
	 * @param seatClass class of the flight searched by the retail customer
	 * @return listTripsInput list of trip sorted
	 */
	public Trips sortByPrice(Trips listTripsInput, SeatClass seatClass){
		Collections.sort(listTripsInput, new Comparator<Trip>() {
			@Override
			public int compare(Trip trip1, Trip trip2){
				return (int) (trip1.getPrice(seatClass) - trip2.getPrice(seatClass));
			}
		});
		
		return listTripsInput;
	}
	
	/**
	 * Sort the input trip list by departure date: earliest first
	 * 
	 * @param listTripsInput list of trip to sort
	 * @return listTripsInput list of trip sorted
	 */
	public Trips sortByDepartureDate(Trips listTripsInput){
		Collections.sort(listTripsInput, new Comparator<Trip>() {
			@Override
			public int compare(Trip trip1, Trip trip2){
				return (int) (trip2.getDepartureTimeGMT() - trip1.getDepartureTimeGMT());
			}
		});
		
		return listTripsInput;
	}
	
	/**
	 * Sort the input trip list by arrival date: earliest first
	 * 
	 * @param listTripsInput list of trip to sort
	 * @return listTripsInput list of trip sorted
	 */
	public Trips sortByArrivalDate(Trips listTripsInput){
		Collections.sort(listTripsInput, new Comparator<Trip>() {
			@Override
			public int compare(Trip trip1, Trip trip2){
				return (int) (trip2.getArrivalTimeGMT() - trip1.getArrivalTimeGMT());
			}
		});
		
		return listTripsInput;
	}
}
