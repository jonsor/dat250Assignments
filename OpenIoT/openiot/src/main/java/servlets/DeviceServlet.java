package servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.reflect.TypeToken;

import entities.Device;
import entities.Feedback;
import entities.User;
import misc.ApiHelper;
import misc.HttpRequestHelper;

/**
 * Servlet implementation class DeviceServlet
 */
@WebServlet("/device")
public class DeviceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeviceServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = HttpRequestHelper.getBasePath(request);
		String deviceId = (String) request.getSession().getAttribute("deviceId");
		String query = "/api/devices/" + deviceId;
    	String responseJson = HttpRequestHelper.httpGetRequest(path, query);
    	Device device = ApiHelper.getGson().fromJson(responseJson, Device.class);
    	
    	String reqistrationsQuery = "/api/devices/"+ deviceId + "/registrations";
    	String registrationsJson = HttpRequestHelper.httpGetRequest(path, reqistrationsQuery);
    	List<String> registeredUsers = ApiHelper.getGson().fromJson(registrationsJson, new TypeToken<List<String>>(){}.getType());
    	
    	System.out.println("Num registered to device: " + registeredUsers.size());
    	request.setAttribute("registeredUsers", registeredUsers);
    	request.setAttribute("device", device);
	    request.getRequestDispatcher("device.xhtml").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String feedback = request.getParameter("feedback");
		String rating = request.getParameter("rating");
		System.out.println(feedback);
		System.out.println(rating);
		if(feedback != null && rating != null) {
			addFeedbackToDevice(request, feedback, rating);
			doGet(request, response);
		}
	}

	private void addFeedbackToDevice(HttpServletRequest request, String feedback, String rating)
			throws UnsupportedEncodingException, IOException, ClientProtocolException {
		String feedbackJson = ApiHelper.getGson().toJson(new Feedback(feedback, Integer.parseInt(rating)));
		
		String deviceId = (String) request.getSession().getAttribute("deviceId");
		
		String path = HttpRequestHelper.getBasePath(request);
		String query = "/api/devices/" + deviceId + "/feedback";
		
		String url = path + query;
		System.out.println(url);
		
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			HttpPost req = new HttpPost(url);
			req.setHeader("User-Agent", "Java client");
			req.setHeader("Accept", "application/json");
			req.setHeader("Content-type", "application/json");
			req.setEntity(new StringEntity(feedbackJson));

			CloseableHttpResponse res = client.execute(req);
		    System.out.println(res.getStatusLine().getStatusCode());
		    client.close();
        }
	}

}
