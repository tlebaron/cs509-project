
public class RetailCustomerPreferences {
	//TODO ?? int to specify class and type of date?
	//As long as there is no class for that,  0=coach and 1=first class, 0=departure and 1=arrival
	public int tripClass;
	
	public Airport departureAirport;
	public Airport arrivalAirport;
	
	//TODO how to save time??
	public double searchDate;
	public int searchDateType;
	
	// Do we have this here ?? Shouldnt be better to have a "default parameters" for the layover and
	//the sorting? It doesn't make sense to store it here.
	public int maxLayover;
	
	//TODO ?? int still, but would be clean to have an enumeration (like for date type and trip class
	//until there, 0 = one way, 1 = roundtrip
	public int typeType;
}
