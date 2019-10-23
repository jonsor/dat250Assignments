package misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import javax.servlet.ServletException;

public class HttpRequestHelper {

	public static String httpGetRequest(String path, String query) throws ServletException, IOException {
		String responseJson = "";
	    URL url = new URL(path + query);
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestProperty("Accept", "application/json");
	    conn.setRequestMethod("GET");
	    int responseMessage = conn.getResponseCode();
	    if(responseMessage == HttpURLConnection.HTTP_OK) {
	      	System.out.println(responseMessage);
	        BufferedReader rd = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(), "UTF-8")
	            );
	    	responseJson = rd.lines().collect(Collectors.joining());
	    }
	    return responseJson;
	}
}
