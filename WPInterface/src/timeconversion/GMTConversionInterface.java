package timeconversion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

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

public class GMTConversionInterface {

	private final String urlBase = "https://maps.googleapis.com/maps/api/timezone/xml?";
	private final String APIKey = "AIzaSyDogRPBsuc3Rp4RYRzXXkLf1p9AXNjsf30";
	BufferedReader reader;
	String line;
	StringBuffer result = new StringBuffer();
	String GMTConversion;
	
	public Float getOffset(Double airportLatitude, Double airportLongitude, Date departureDate) {
		request(airportLatitude, airportLongitude, departureDate);
		Document docTimeDiff = buildDomDoc (GMTConversion);
		NodeList nodes = docTimeDiff.getElementsByTagName("TimeZoneResponse");
		Float rawTimeDiff = null, dayLightSavings = null;
		for(int i = 0; i < nodes.getLength(); i++) {
			Element timeZoneResponse = (Element) nodes.item(i);
			Element rawOffset, dstOffset;
			rawOffset = (Element)timeZoneResponse.getElementsByTagName("raw_offset").item(0);
			dstOffset = (Element)timeZoneResponse.getElementsByTagName("dst_offset").item(0);
			rawTimeDiff = Float.parseFloat(getCharacterDataFromElement(rawOffset));
			dayLightSavings = Float.parseFloat(getCharacterDataFromElement(dstOffset));

		}
		return rawTimeDiff; //+ dayLightSavings;
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
	
	private String request(Double airportLatitude, Double airportLongitude, Date departureDate) {
		Long time = (Long) departureDate.getTime() / 1000;
		String url = urlBase + "location=" + airportLatitude.toString() + "," + airportLongitude.toString() + "&timestamp=" + time.toString() + "&key=" + APIKey;
		System.out.println(url);
		try {
			URL requestURL = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if(responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);
				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		GMTConversion = result.toString();
		System.out.println(GMTConversion);
		return GMTConversion;
	}
}
