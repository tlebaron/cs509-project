package flight;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import airplane.Airplane;
import airport.Airport;
import seat.SeatClass;
import seat.Seats;

public class Flight {
	Airport departureAirport;
	Airport arrivalAirport;
	int flightNumber;
	int flightTime;
	Seats coachSeating;
	Seats firstClassSeating;
	Airplane airplane;
	
	//TODO how to save time? do we separate day, month, time, ...
	Calendar departureTimeGMT;
	Calendar arrivalTimeGMT;
	Calendar departureTimeLocal;
	Calendar arrivalTimeLocal;
	
	public Airport getDepartureAirport() {
		return this.departureAirport;
	}
	public void setDepartureAirport(Airport dAirport){
		this.departureAirport = dAirport;
	}
	
	public Airport getArrivalAirport() {
		return this.arrivalAirport;
	}
	public void setArrivalAirport(Airport aAirport) {
		this.arrivalAirport = aAirport;
	}
	
	public int getFlightNumber(){
		return this.flightNumber;
	}
	public void setFlightNumber(int number){
		this.flightNumber = number;
	}
	
	public int getFlightTime(){
		return this.flightTime;
	}
	public void setFlightTime(int time){
		this.flightTime = time;
	}
	
	public Seats getCoachSeating(){
		return this.coachSeating;
	}
	public void setCoachSeating(Seats cSeating){
		this.coachSeating = cSeating;
	}
	
	public Seats getFirstClassSeating(){
		return this.firstClassSeating;
	}
	public void setFirstClassSeating(Seats fSeating){
		this.firstClassSeating = fSeating;
	}
	
	public Airplane getAirplane(){
		return this.airplane;
	}
	public void setAirplane(Airplane plane){
		this.airplane = plane;
	}
	
	public Calendar getDepartureTimeGMT(){
		return this.departureTimeGMT;
	}
	public void setDepartureTimeGMT(Calendar time){
		this.departureTimeGMT = time;
	}
	
	public Calendar getArrivalTimeGMT(){
		return this.arrivalTimeGMT;
	}
	public void setArrivalTimeGMT(Calendar time){
		this.arrivalTimeGMT = time;
	}
	
	public Calendar getDepartureTimeLocal(){
		if (this.departureTimeLocal != null) return this.departureTimeLocal;
		Calendar timeCalendar = (Calendar) departureTimeGMT.clone();
		timeCalendar.setTimeZone(TimeZone.getTimeZone(this.departureAirport.getTimeZoneID()));
		this.departureTimeLocal = timeCalendar;
		return timeCalendar;
	}
	
	public Calendar getArrivalTimeLocal(){
		if (this.arrivalTimeLocal != null) return this.arrivalTimeLocal;
		Calendar timeCalendar = (Calendar) arrivalTimeGMT.clone();
		timeCalendar.setTimeZone(TimeZone.getTimeZone(this.arrivalAirport.getTimeZoneID()));
		this.arrivalTimeLocal = timeCalendar;
		return timeCalendar;
	}
	
	public void setDepartureTimeLocal(Calendar time){
		this.departureTimeLocal = time;
	}
	public void setArrivalTimeLocal(Calendar time){
		this.arrivalTimeLocal = time;
	}
	
	
	public double getPrice(SeatClass seatClass) {
		switch(seatClass) {
			case COACH:
				return coachSeating.getPrice();
			case FIRSTCLASS:
				return firstClassSeating.getPrice();
			default:
				return -1;
				
		}
	}
	
	/**
	 * Determine if object instance has valid attribute data
	 * 
	 * Airplane needs to be valid
	 * Flight time needs to be a positive integer
	 * Number needs to be a positive integer
	 * Departure and arrival code need to be a three characters
	 * Departure and arrival time need to be valid
	 * Seating prices need to be positive double
	 * Seating number needs to be positive integer
	 * 
	 * @return true if object passes above validation checks
	 * 
	 */
	public boolean isValid() {
		
		if(!airplane.isValid()) return false;
		if(flightTime < 0) return false;
		if(flightNumber < 0) return false;
		if(!departureAirport.isValid()) return false;
		if(!arrivalAirport.isValid()) return false;
		//if(departureTimeGMT)
		//if(arrivalTimeGMT)
		if(coachSeating.getNumberOfSeats() < 0) return false;
		if(firstClassSeating.getNumberOfSeats() < 0) return false;
		if(coachSeating.price < 0) return false;
		if(firstClassSeating.price < 0) return false;
		
		return true;
	}
	
	/**
	 * Convert object to printable string of format "Code, (lat, lon), Name"
	 * 
	 * @return the object formatted as String to display
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		String departureHour = Integer.toString(departureTimeGMT.get(Calendar.HOUR));
		if (departureHour.length()<2) departureHour = "0"+departureHour;
		String departureMinute = Integer.toString(departureTimeGMT.get(Calendar.MINUTE));
		if (departureMinute.length()<2) departureMinute = "0"+departureMinute;
		String arrivalHour = Integer.toString(arrivalTimeGMT.get(Calendar.HOUR));
		if (arrivalHour.length()<2) arrivalHour = "0"+arrivalHour;
		String arrivalMinute = Integer.toString(arrivalTimeGMT.get(Calendar.MINUTE));
		if (arrivalMinute.length()<2) arrivalMinute = "0"+arrivalMinute;
		
		Calendar departureTimeLocal = this.getDepartureTimeLocal();
		Calendar arrivalTimeLocal = this.getArrivalTimeLocal();
		
		//sb.append(manufacturer).append(", ");
		//sb.append(Integer.toString(coachSeats.numberOfSeats)).append(", ");
		sb.append("Flight number: "+Integer.toString(flightNumber) + "\n");
		sb.append("Departure: "+departureAirport.code() + "\n");
		sb.append("Arrival: "+arrivalAirport.code() + "\n");
		sb.append("Duration: "+Integer.toString(flightTime)+" minutes\n");
		sb.append("Number of first class seats booked: "+Integer.toString(firstClassSeating.getNumberOfSeats())+ " at $" + Double.toString(firstClassSeating.price)  + "\n");
		sb.append("Number of economy seats booked: "+Integer.toString(coachSeating.getNumberOfSeats())+ " at $" + Double.toString(coachSeating.price)  + "\n");
		sb.append("Departure Time (GMT): "+departureTimeGMT.get(Calendar.MONTH)+"/"+departureTimeGMT.get(Calendar.DAY_OF_MONTH)+"/"+departureTimeGMT.get(Calendar.YEAR)+" at "+departureHour+":"+departureMinute + "\n");
		sb.append("Arrival Time (GMT): "+arrivalTimeGMT.get(Calendar.MONTH)+"/"+arrivalTimeGMT.get(Calendar.DAY_OF_MONTH)+"/"+arrivalTimeGMT.get(Calendar.YEAR)+" at "+arrivalHour+":" + arrivalMinute + "\n");
		sb.append("Departure Time (Local): "+departureTimeLocal.get(Calendar.MONTH)+"/"+departureTimeLocal.get(Calendar.DAY_OF_MONTH)+"/"+departureTimeLocal.get(Calendar.YEAR)+" at "+departureTimeLocal.get(Calendar.HOUR)+":"+departureTimeLocal.get(Calendar.MINUTE) + "\n");
		sb.append("Arrival Time (Local): "+arrivalTimeLocal.get(Calendar.MONTH)+"/"+arrivalTimeLocal.get(Calendar.DAY_OF_MONTH)+"/"+arrivalTimeLocal.get(Calendar.YEAR)+" at "+arrivalTimeLocal.get(Calendar.HOUR)+":"+arrivalTimeLocal.get(Calendar.MINUTE) + "\n");
		sb.append("----------");

		return sb.toString();
	}
	
}
