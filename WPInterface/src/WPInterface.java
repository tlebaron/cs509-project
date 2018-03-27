import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import airport.Airport;
import seat.SeatClass;
import utils.QueryFactory;

public class WPInterface {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//call the database to get the airports.
		Airport depart = new Airport(); //BOS
		depart.code("BOS");
		//Date date = new Date(2017,05,12);

		RetailCustomerPreferences customer = new RetailCustomerPreferences();
		customer.departureAirport = depart;
		//customer.searchDate = date;
		customer.searchDateType = DateType.DEPARTURE;
		customer.tripType = TripType.ONEWAY;
			
		//String urlString = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem?team=Team1&action=list&list_type=airplanes";
		String urlString = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem?team=Team1&action=list&list_type=departing&airport=BOS&day=2018_05_12";
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("User-Agent", "Team1");
		int responseCode = connection.getResponseCode();
		
		String line;
		StringBuffer result = new StringBuffer();
		BufferedReader reader;
		if (responseCode >= HttpURLConnection.HTTP_OK) {
			InputStream inputStream = connection.getInputStream();
			String encoding = connection.getContentEncoding();
			encoding = (encoding == null ? "UTF-8" : encoding);

			reader = new BufferedReader(new InputStreamReader(inputStream));
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
		}
		String xml = result.toString();
		System.out.println(xml);
		
	}

}
