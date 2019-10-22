package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.reflect.TypeToken;

import entities.Device;
import misc.ApiHelper;

/**
 * Servlet implementation class WelcomePageServlet
 */
@WebServlet(name = "WelcomePageServlet", urlPatterns = "/welcome")
public class WelcomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WelcomePageServlet() {
        super();
        System.out.println("Welcome servlet entered!");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String servletUrl = request.getRequestURL().toString();
		String path = servletUrl.replace(request.getServletPath().toString(),"");
		String query = "/api/devices";
	    URL url = new URL(path + query);
	    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	    conn.setRequestProperty("Accept", "application/json");
	    conn.setRequestMethod("GET");
	    int responseMessage = conn.getResponseCode();
	  	System.out.println(responseMessage);
	  	
    	request.setAttribute("user", "UserName");
      	
	    if(responseMessage == HttpURLConnection.HTTP_OK) {
	      	System.out.println(responseMessage);
	        BufferedReader rd = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(), "UTF-8")
	            );
	    	String responseJson = rd.lines().collect(Collectors.joining());
	    	List<Device> devices = ApiHelper.getGson().fromJson(responseJson, new TypeToken<List<Device>>(){}.getType());
	    	request.setAttribute("devices", devices);
	    	
	    	for(Device device : devices) {
	    		System.out.println("name: " + device.getName() +  " id: " + device.getId());
	    	}
	    }
	    
	    request.getRequestDispatcher("welcome.xhtml").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
