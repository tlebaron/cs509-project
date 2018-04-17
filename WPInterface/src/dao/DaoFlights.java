/**
 * 
 */
package dao;

import java.io.IOException;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import airplane.Airplane;
import airport.Airport;
import flight.Flight;
import flight.Flights;
import seat.SeatClass;
import seat.Seats;

/**
 * @author tlb
 *
 */
public class DaoFlights {
	/**
	 * Builds collection of flights from Flights described in XML
	 * 
	 * Parses an XML string to read each of the flights and adds each valid flight
	 * to the collection. The method uses Java DOM (Document Object Model) to convert
	 * from XML to Java primitives. 
	 * 
	 * Method iterates over the set of Flight nodes in the XML string and builds
	 * an Flight object from the XML node string and add the Flight object instance to
	 * the Flights collection.
	 * 
	 * @param xmlFlights XML string containing set of Flights 
	 * @return [possibly empty] collection of Flights in the xml string
	 * @throws NullPointerException included to keep signature consistent with other addAll methods
	 * 
	 * @pre the xmlFlights string adheres to the format specified by the server API
	 * @post the [possibly empty] set of Flights in the XML string are added to collection
	 */
	public static Flights addAll (String xmlFlights) throws NullPointerException {
		Flights Flights = new Flights();
		
		// Load the XML string into a DOM tree for ease of processing
		// then iterate over all nodes adding each Flight to our collection
		Document docFlights = buildDomDoc (xmlFlights);
		NodeList nodesFlights = docFlights.getElementsByTagName("Flight");
		
		for (int i = 0; i < nodesFlights.getLength(); i++) {
			Element elementFlight = (Element) nodesFlights.item(i);
			Flight Flight = buildFlight (elementFlight);
			
			//if (Flight.isValid()) {
				Flights.add(Flight);
			//}
		}
		
		return Flights;
	}
	
	/**
	 * Convert time from a string to Calendar object
	 * 
	 * Read a String in the format:
	 * YYYY Mmm DD HH:MM GMT
	 * Where Mmm is the three first letter of the month
	 * 
	 * @param String time in the attended format
	 * @return time recorded in Calendar object
	 * 
	 */
	static private Calendar timeStringToCal(String timeString){
		Calendar timeCalendar = Calendar.getInstance();
		
		String[] stringPart = timeString.split(" ");
		
		int monthNum = 0;
		switch(stringPart[1]){
		case "Jan":
			monthNum = 0;
			break;
		case "Feb":
			monthNum = 1;
			break;
		case "Mar":
			monthNum = 2;
			break;
		case "Apr":
			monthNum = 3;
			break;
		case "May":
			monthNum = 4;
			break;
		case "Jun":
			monthNum = 5;
			break;
		case "Jul":
			monthNum = 6;
			break;
		case "Aug":
			monthNum = 7;
			break;
		case "Sep":
			monthNum = 8;
			break;
		case "Oct":
			monthNum = 9;
			break;
		case "Nov":
			monthNum = 10;
			break;
		case "Dec":
			monthNum = 11;
			break;
		}
		
		timeCalendar.set(Integer.parseInt(stringPart[0]),
				monthNum,
				Integer.parseInt(stringPart[2]), 
				Integer.parseInt(stringPart[3].split(":")[0]), 
				Integer.parseInt(stringPart[3].split(":")[1]));
		
		return timeCalendar;
	}
	
	/**
	 * Creates an Flight object from a DOM node
	 * 
	 * Processes a DOM Node that describes an Flight and creates an Flight object from the information
	 * @param nodeFlight is a DOM Node describing an Flight
	 * @return Flight object created from the DOM Node representation of the Flight
	 * 
	 * @pre nodeFlight is of format specified by CS509 server API
	 */
	static private Flight buildFlight (Node nodeFlight) {
		/**
		 * Instantiate an empty Flight object
		 */
		Flight Flight = new Flight();
		
		Airplane airplane;
		int flightTime;
		int flightNumber;
		Airport departureAirport = new Airport();
		Airport arrivalAirport = new Airport();
		Calendar departureTime;
		Calendar arrivalTime;
		
		Seats coachSeat = new Seats();
		coachSeat.seatClass = SeatClass.COACH;
		Seats firstClassSeat = new Seats();
		firstClassSeat.seatClass = SeatClass.FIRSTCLASS;
		
		String xmlTime;
		String xmlSeat;
		
		
		// The Flight element has attributes of Manufacturer and model
		Element elementFlight = (Element) nodeFlight;
		airplane = new Airplane();
		airplane.model = elementFlight.getAttributeNode("Airplane").getValue();
		flightTime = Integer.parseInt(elementFlight.getAttributeNode("FlightTime").getValue());
		flightNumber = Integer.parseInt(elementFlight.getAttributeNode("Number").getValue());
		
		//Departure child element
		Element elementDeparture;
		elementDeparture = (Element)elementFlight.getElementsByTagName("Departure").item(0);
		
		//code and time of departure
		Element elementDCode;
		elementDCode = (Element)elementDeparture.getElementsByTagName("Code").item(0);
		departureAirport.code(getCharacterDataFromElement(elementDCode));
		Element elementDTime;
		elementDTime = (Element)elementDeparture.getElementsByTagName("Time").item(0);
		xmlTime = getCharacterDataFromElement(elementDTime);
		//System.out.println(xmlTime);
		departureTime = timeStringToCal(xmlTime);
		
		//Arrival child element
		Element elementArrival;
		elementArrival = (Element)elementFlight.getElementsByTagName("Arrival").item(0);
		
		//code and time of departure
		Element elementACode;
		elementACode = (Element)elementArrival.getElementsByTagName("Code").item(0);
		arrivalAirport.code(getCharacterDataFromElement(elementACode));
		Element elementATime;
		elementATime = (Element)elementArrival.getElementsByTagName("Time").item(0);
		xmlTime = getCharacterDataFromElement(elementATime);
		arrivalTime = timeStringToCal(xmlTime);
		
		//seat element
		Element elementSeat;
		elementSeat = (Element)elementFlight.getElementsByTagName("Seating").item(0);
		
		//First class
		Element elementSFC;
		elementSFC = (Element)elementSeat.getElementsByTagName("FirstClass").item(0);
		//price
		//ex : flightTime = Integer.parseInt(elementFlight.getAttributeNode("FlightTime").getValue());
		xmlSeat = elementSFC.getAttributeNode("Price").getValue();
		xmlSeat = xmlSeat.substring(1);
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		Number n = null;
		try {
			n = format.parse(xmlSeat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		firstClassSeat.price = n.doubleValue();
		//number
		firstClassSeat.setNumberOfSeats(Integer.parseInt(getCharacterDataFromElement(elementSFC)));
		
		//First class
		Element elementSC;
		elementSC = (Element)elementSeat.getElementsByTagName("Coach").item(0);
		//price
		xmlSeat = elementSC.getAttributeNode("Price").getValue();
		xmlSeat = xmlSeat.substring(1);
		coachSeat.price = Double.parseDouble(xmlSeat);
		//number
		coachSeat.setNumberOfSeats(Integer.parseInt(getCharacterDataFromElement(elementSC)));
		
		
		

		/**
		 * Update the Flight object with values from XML node
		 */
		Flight.setAirplane(airplane);
		Flight.setFlightTime(flightTime);
		Flight.setFlightNumber(flightNumber);
		Flight.setDepartureAirport(departureAirport);
		Flight.setArrivalAirport(arrivalAirport);
		Flight.setDepartureTimeGMT(departureTime);
		Flight.setArrivalTimeGMT(arrivalTime);
		Flight.setFirstClassSeating(firstClassSeat);
		Flight.setCoachSeating(coachSeat);
		
		return Flight;
	}

	/**
	 * Builds a DOM tree from an XML string
	 * 
	 * Parses the XML file and returns a DOM tree that can be processed
	 * 
	 * @param xmlString XML String containing set of objects
	 * @return DOM tree from parsed XML or null if exception is caught
	 */
	static private Document buildDomDoc (String xmlString) {
		/**
		 * load the xml string into a DOM document and return the Document
		 */
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xmlString));
			
			return docBuilder.parse(inputSource);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retrieve character data from an element if it exists
	 * 
	 * @param e is the DOM Element to retrieve character data from
	 * @return the character data as String [possibly empty String]
	 */
	private static String getCharacterDataFromElement (Element e) {
		Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	        CharacterData cd = (CharacterData) child;
	        return cd.getData();
	      }
	      return "";
	}
}
