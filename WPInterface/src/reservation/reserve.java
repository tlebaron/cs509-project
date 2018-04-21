package reservation;

import flight.Flights;
import flight.Flight;
import dao.ServerInterface;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class generally lock the DB, book a seat and release the DB
 * 
 * @author mgy
 * @version 1.2
 * @since 2018-04-14
 * 
 * 
 *
 */

public class reserve {
	/** 
	 * Booking attributes as defined by the CS509 server interface XML
	 */
	private Flights flightList;

	/**
	 * default constructor
	 *
	 * The default constructor initializes the object instance to default / invalid values.
	 *
	 * precondition none
	 * postcondition attributes are initialized with valid structures
	 */
	public reserve () {
		this.flightList = new Flights();
	}
	/**
	 * constructor with all required field values supplied
	 *
	 * This constructor will create a valid ReservationOption object.
	 * The constructor takes an ArrayList to initiate the object.
	 *
	 * @param flightList is a list of flights that make up the option.
	 *
	 * precondition valid flights
	 * postcondition attributes are initialized with valid values
	 */
	public reserve (Flights flightList) {
		this.flightList = flightList;
	}
	/**
	 * get an individual Flight from the ArrayList based on index
	 * aligns with which leg of the overall trip (layovers)
	 *
	 * @param index of Flight in list
	 * @return the Flight object at the index
	 */
	public Flight getFlight(int index) {
		Flight flight;
		try {
			flight = this.flightList.get(index);
		} catch (Exception ex) {
			flight = null;
		}
		return flight;
	}
	/**
	 * get total number of Flights in the reservation option
	 *
	 * @return the number of flights
	 */
	public int getNumFlights() {
		try {
			return this.flightList.size();
		} catch (Exception ex) {
			return 0;
		}
	}
	/**
	 * get number of layovers in the reservation option
	 *
	 * @return the number of layovers
	 */
	public int getNumLayovers() {
		try {
			if (this.flightList.size() == 0) {
				return 0;
			} else {
				return this.flightList.size()-1;
			}
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * Reserves all flights associated with this reservation.
	 *
	 * @param mSeatPreference is the seating type preference to reserve: 'Coach' or 'FirstClass'
	 * @return true or false based on if the reservation was successful
	 */
	public boolean reserveFlights(String mSeatPreference, String teamName){
		//get the lock to the DB
		ServerInterface mSI = ServerInterface.INSTANCE;
		if(!mSI.lock(teamName)){
			System.out.println("Lock not available.Try again Later");
			return false;
		}
		//creating a XML string of flight data
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			Flight flight;
			Element flight_xml;
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document message = db.newDocument();
			Element root = message.createElement("Flights");
			message.appendChild(root);
			for(int j=0;j<this.getNumFlights();j++){
				flight=this.getFlight(j);
				flight_xml=message.createElement("Flight");
				flight_xml.setAttribute("number",Integer.toString(flight.getFlightNumber()));
				flight_xml.setAttribute("seating",mSeatPreference);
				root.appendChild(flight_xml);
			}
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(message), new StreamResult(writer));

			if(mSI.reserve_ticket(teamName, writer.toString())){
				mSI.unlock(teamName);
				return true;
			} else{
				mSI.unlock(teamName);
				return false;
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			mSI.unlock(teamName);
			return false;
		} catch (TransformerException e) {
			e.printStackTrace();
			mSI.unlock(teamName);
			return false;
		}
	}

}

