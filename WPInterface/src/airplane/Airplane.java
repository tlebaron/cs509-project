package airplane;
import seat.SeatClass;
import seat.Seats;
import java.util.Comparator;
import utils.Saps;

/**
 * This class holds values pertaining to a single Airplane. Class member attributes
 * are the same as defined by the CS509 server API and store values after conversion from
 * XML received from the server to Java primitives. Attributes are accessed via getter and 
 * setter methods.
 * 
 * @author mgy
 * @version 1.0
 * @since 2018-03-27
 *
 */

public class Airplane {
	
	/**
	 * Airplane attributes as defined by the CS509 server interface XML
	 */
	public String manufacturer;
	public String model;
	public Seats coachSeats;
	public Seats firstClassSeats;
	
	/**
	 * Default constructor
	 * 
	 * Constructor without params. Requires object fields to be explicitly
	 * set using setter methods
	 * 
	 * @pre None
	 * @post member attributes are initialized to invalid default values
	 */	
	public Airplane () {
		manufacturer = "";
		model = "";
		
		coachSeats = new Seats();
		coachSeats.numberOfSeats = 0;
		coachSeats.seatClass = SeatClass.COACH;
		coachSeats.price = 0;
		
		firstClassSeats = new Seats();
		firstClassSeats.numberOfSeats = 0;
		firstClassSeats.seatClass = SeatClass.FIRSTCLASS;
		firstClassSeats.price = 0;
	}
	
	/**
	 * Initializing constructor.
	 * 
	 * All attributes are initialized with input values
	 *  
	 * @param manufacturer The manufacturer of the airplane
	 * @param model The model of the airplane
	 * @param num_coachSeats The number of coach seats on the airplane 
	 * @param num_firstClassSeats The number of first class seats on the airplane
	 * 
	 * @pre manufacturer and model are not null, num_coachSeats and num_firstClassSeats are non negative integers
	 * @post member attributes are initialized with input parameter values
	 * @throws IllegalArgumentException is any parameter is invalid
	 */
	public Airplane (String manufacturer1, String model1, int num_coachSeats1, int num_firstClassSeats1) {
		if (!isValidManufacturer(manufacturer1))
			throw new IllegalArgumentException(manufacturer1);
		if (!isValidModel(model1)) 
			throw new IllegalArgumentException(model1);
		if (!isValidNum_coachSeats(num_coachSeats1))
			throw new IllegalArgumentException(Integer.toString(num_coachSeats1));
		if (!isValidNum_firstClassSeats(num_firstClassSeats1))
			throw new IllegalArgumentException(Integer.toString(num_firstClassSeats1));
		
		manufacturer = manufacturer1;
		model = model1;
		coachSeats.numberOfSeats = num_coachSeats1;
		firstClassSeats.numberOfSeats = num_firstClassSeats1;
	}
	
	/**
	 * Initializing constructor with all params as type String. Converts num_coachSeats1 and 
	 * num_firstClassSeats1 values to required double format.
	 * 
	 * @param manufacturer The manufacturer of the airplane
	 * @param model The model of the airplane
	 * @param num_coachSeats The number of coach seats on the airplane 
	 * @param num_firstClassSeats The number of first class seats on the airplane
	 * 
	 * @pre manufacturer and model are not null, num_coachSeats and num_firstClassSeats are strings
	 * @post member attributes are initialized with input parameter values
	 * @throws IllegalArgumentException is any parameter is invalid
	 * 
	 */
	public Airplane (String manufacturer1, String model1, String num_coachSeats1, String num_firstClassSeats1) {
		int temp_num_coachSeats1, temp_num_firstClassSeats1;
		try {
			temp_num_coachSeats1 = Integer.parseInt(num_coachSeats1);
		} catch (NullPointerException | NumberFormatException ex) {
			throw new IllegalArgumentException ("Number of coachSeats must be a non negative integer!", ex);
		}
		
		try {
			temp_num_firstClassSeats1 = Integer.parseInt(num_firstClassSeats1);
		} catch (NullPointerException | NumberFormatException ex) {
			throw new IllegalArgumentException ("Number of firstClassSeats must be a non negative integer!", ex);
		}
		
		manufacturer = manufacturer1;
		model = model1;
		coachSeats.numberOfSeats = temp_num_coachSeats1;
		firstClassSeats.numberOfSeats = temp_num_firstClassSeats1;
	}

	/**
	 * Convert object to printable string of format "Code, (lat, lon), Name"
	 * 
	 * @return the object formatted as String to display
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append(manufacturer).append("\t");
		sb.append(model).append("\t");
		sb.append(Integer.toString(coachSeats.numberOfSeats)).append("\t");
		sb.append(Integer.toString(firstClassSeats.numberOfSeats));

		return sb.toString();
	}
	
	/**
	 * Set the airplane manufacturer
	 * 
	 * @param manufacturer The manufacturer of the airplane
	 * @throws IllegalArgumentException is name is invalid
	 */
	public void manufacturer (String manufacturer1) {
		if (isValidManufacturer (manufacturer1))
			manufacturer = manufacturer1;
		else
			throw new IllegalArgumentException (manufacturer1);
	}
	
	/**
	 * get the airplane manufacturer
	 * 
	 * @return Airplane manufacturer
	 */
	public String manufacturer () {
		return manufacturer;
	}
	
	/**
	 * set the airplane model
	 * 
	 * @param model The model of the airplane
	 * @throws IllegalArgumentException is code is invalid
	 */
	public void model (String model1) {
		if (isValidModel(model1))
			model = model1;
		else
			throw new IllegalArgumentException (model1);
	}
	
	/**
	 * Get the airplane model
	 * 
	 * @return The Airplane model
	 */
	public String model () {
		return model;
	}
	
	/**
	 * Set the number of coach seats on the airplane
	 * 
	 * @param num_coachSeats The number of coach seats on the airplane 
	 * @throws IllegalArgumentException is latitude is invalid
	 */
	public void num_coachSeats (int num_coachSeats1) {
		if (isValidNum_coachSeats(num_coachSeats1))
			coachSeats.numberOfSeats = num_coachSeats1;
		else
			throw new IllegalArgumentException (Integer.toString(num_coachSeats1));
	}
	
	public void num_coachSeats (String num_coachSeats1) {
		if (isValidNum_coachSeats(num_coachSeats1))
			coachSeats.numberOfSeats = Integer.parseInt(num_coachSeats1);
		else
			throw new IllegalArgumentException (num_coachSeats1);
	}
	
	/**
	 * Get the number of coach seats on the airplane
	 * 
	 * @return The number of coach seats 
	 */
	public double num_coachSeats () {
		return coachSeats.numberOfSeats;
	}
	
	/**
	 * Set the number of first class seats on the airport
	 * 
	 * @param num_firstClassSeats The number of first class seats on the airplane
	 * @throws IllegalArgumentException is longitude is invalid
	 */
	public void num_firstClassSeats (int num_firstClassSeats1) {
		if (isValidNum_firstClassSeats(num_firstClassSeats1))
			firstClassSeats.numberOfSeats = num_firstClassSeats1;
		else
			throw new IllegalArgumentException (Integer.toString(num_firstClassSeats1));
	}
	
	public void num_firstClassSeats (String num_firstClassSeats1) {
		if (isValidNum_firstClassSeats(num_firstClassSeats1))
			firstClassSeats.numberOfSeats = Integer.parseInt(num_firstClassSeats1);
		else
			throw new IllegalArgumentException (num_firstClassSeats1);
	}
	
	/**
	 * get the number of first class seats on the airport
	 * 
	 * @return the number of first class seats
	 */
	public double num_firstClassSeats () {
		return firstClassSeats.numberOfSeats;
	}

	/**
	 * Determine if object instance has valid attribute data
	 * 
	 * Verifies the manufacturer and model is not null and not an empty string. 
	 * Verifies num_coachSeats and num_firstClassSeats are non negative integers.
	 * 
	 * @return true if object passes above validation checks
	 * 
	 */
	public boolean isValid() {
		
		// If the manufacturer or model isn't valid, the object isn't valid
		if ((manufacturer == null) || (manufacturer == "" || model == null || model == ""))
			return false;
		
		// Verify num_coachSeats and num_firstClassSeats are within range
		if (coachSeats.numberOfSeats < 0 || firstClassSeats.numberOfSeats < 0) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Check for invalid manufacturer
	 * 
	 * @param manufacturer1 The manufacturer of the airplane
	 * @return false if null or empty string, else assume valid and return true
	 */
	public boolean isValidManufacturer (String manufacturer1) {
		// If manufacturer1 is null or empty then it is not valid
		if ((manufacturer1 == null) || (manufacturer1 == ""))
			return false;
		return true;
	}
	
	/**
	 * Check for invalid model.
	 * 
	 * @param model1 The model of the airplane
	 * @return false if null or empty string, else assume valid and return true
	 */
	public boolean isValidModel (String model1) {
		// If the name is null or empty it can't be valid
		if ((model1 == null) || (model1 == ""))
			return false;
		return true;
	}
	
	/**
	 * Check if number of coach seats is valid
	 * 
	 * @param num_coachSeats1 The number of coach seats on the airplane 
	 * @return true if within valid range 
	 */
	public boolean isValidNum_coachSeats (int num_coachSeats1) {
		// Verify num_coachSeats1 is within valid range
		if (num_coachSeats1 < 0)
			return false;
		return true;
	}
	
	/**
	 * Check if number of coach seats is valid.
	 * 
	 * @param num_coachSeats1 The number of coach seats on the airplane to validate represented as a String
	 * @return true if within valid range
	 */
	public boolean isValidNum_coachSeats (String num_coachSeats1) {
		int temp_num;
		try {
			temp_num = Integer.parseInt(num_coachSeats1);
		} catch (NullPointerException | NumberFormatException ex) {
			return false;
		}
		return isValidNum_coachSeats (temp_num);
	}

	/**
	 * Check if number of first class seats is valid
	 * 
	 * @param num_firstClassSeats1 is the number of first class seats on the airplane to validate
	 * @return true if within valid range
	 */
	public boolean isValidNum_firstClassSeats (int num_firstClassSeats1) {
		// Verify num_firstClassSeats1 is within valid range
		if (num_firstClassSeats1 < 0)
			return false;
		return true;
	}
	
	/**
	 * Check if number of first class seats is valid
	 * 
	 * @param num_firstClassSeats1 is the number of first class seats on the airplane to validate represented as a String
	 * @return true if within valid range
	 */
	public boolean isValidNum_firstClassSeats (String num_firstClassSeats1) {
		int temp_num;
		try {
			temp_num = Integer.parseInt(num_firstClassSeats1);
		} catch (NullPointerException | NumberFormatException ex) {
			return false;
		}
		return isValidNum_firstClassSeats (temp_num);
	}
}