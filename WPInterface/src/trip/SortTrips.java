package trip;

import java.util.Collections;
import java.util.Comparator;

public class SortTrips {
	/**
	 * Sort the input list by duration: fastest first
	 * 
	 * @param listTripsInput list of trip to sort
	 * @return listTripsInput list of trip sorted
	 */
	public Trips sortByDuration(Trips listTripsInput){
		Collections.sort(listTripsInput, new Comparator<Trip>() {
			@Override
			public int compare(Trip trip1, Trip trip2){
				return trip1.getDuration() - trip2.getDuration();
			}
		});
		
		return listTripsInput;
	}
}
