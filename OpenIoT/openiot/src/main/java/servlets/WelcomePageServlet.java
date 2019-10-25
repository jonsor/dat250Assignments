package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.reflect.TypeToken;

import entities.Device;
import misc.ApiHelper;
import misc.HttpRequestHelper;

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

		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/openiot/Login");
			return;
		}
		
		String path = HttpRequestHelper.getBasePath(request);
		String query = "/api/devices";
    	String responseJson = HttpRequestHelper.httpGetRequest(path, query);
    	List<Device> devices = ApiHelper.getGson().fromJson(responseJson, new TypeToken<List<Device>>(){}.getType());
    	
    	request.setAttribute("devices", devices);
		String search = request.getParameter("search");
		System.out.println("search: " + search);
		
		if(search != null && !search.isEmpty())
			devices.removeIf(d -> !d.getName().contains(search));
	    
	    request.getRequestDispatcher("welcome.xhtml").forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doPost!");
		String search = request.getParameter("search");
		String deviceSelected = request.getParameter("deviceSelected");
		String selectedDeviceId = request.getParameter("selectedDeviceId");
		
		String registerNew = request.getParameter("registerNewDevice");
		
		if(deviceSelected != null && selectedDeviceId != null) {
			request.getSession().setAttribute("deviceId", selectedDeviceId);
			response.sendRedirect("/openiot/device");
		}else if (registerNew != null && registerNew.equals("Register new Device")) {
            response.sendRedirect("/openiot/DeviceRegistration");
        }else if(search != null) {
        	doGet(request, response);        	
        }
	}

}
