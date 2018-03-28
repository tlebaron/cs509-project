package timeconversion;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

public class GMTConversionInterface {

	private final String urlBase = "https://maps.googleapis.com/maps/api/timezone/xml?";
	private final String APIKey = "AIzaSyDogRPBsuc3Rp4RYRzXXkLf1p9AXNjsf30";
	BufferedReader reader;
	String line;
	StringBuffer result = new StringBuffer();
	String xmlFlights;
	
	public float getOffset(Double airportLatitude, Double airportLongitude, Date departureDate) {
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
		xmlFlights = result.toString();
		System.out.println(xmlFlights);
		return -1;
	}
}
