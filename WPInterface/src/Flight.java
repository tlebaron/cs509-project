import java.util.Date;
import airport.Airport;

public class Flight {
	public Airport departureAirport;
	public Airport arrivalAirport;
	public int flightNumber;
	public double flightTime;
	public Seats coachSeating;
	public Seats firstClassSeating;
	public Airplane airplane;
	
	//TODO how to save time? do we separate day, month, time, ...
	public Date departureTimeGMT;
	public Date arrivalTimeGMT;
	public Date departureTimeLocal;
	public Date arrivalTimeLocal;
	
}
