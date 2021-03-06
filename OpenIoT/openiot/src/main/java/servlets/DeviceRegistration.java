package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

import entities.Device;
import entities.Feedback;
import entities.User;
import misc.ApiHelper;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/DeviceRegistration")
public class DeviceRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeviceRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(request.getSession().getAttribute("user") == null) {
			response.sendRedirect("/openiot/Login");
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("deviceRegistration.xhtml");
			rd.forward(request, response);		
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String name = request.getParameter("name");
		String data = request.getParameter("data");
		String imageUrl = request.getParameter("ImageURL");
		Boolean publicDevice = Boolean.parseBoolean(request.getParameter("PublicDevice"));
		String status = request.getParameter("Status");

		System.out.println(name + " " + " " + data + " " + imageUrl + " " + publicDevice + " " + status);

		Device d = new Device();
		d.setName(name);
		d.setData(data);
		d.setImageUrl(imageUrl);
		d.setPublicDevice(publicDevice); 
		d.setStatus(status);
		
		//List<Feedback> l = new ArrayList<>();
		//l.add(new Feedback());
		//d.setFeedback(l);

		String s = request.getRequestURL().toString();
		s = s.substring(0, s.length() - request.getRequestURI().toString().length());
		
		User user = (User) request.getSession().getAttribute("user");
		
		String url = s + "/openiot/api/devices/" + user.getId();

		System.out.println(url);

		String str = ApiHelper.getGson().toJson(d);
		
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			HttpPost req = new HttpPost(url);
			req.setHeader("User-Agent", "Java client");
			req.setHeader("Accept", "application/json");
			req.setHeader("Content-type", "application/json");
			req.setEntity(new StringEntity(str));

			CloseableHttpResponse res = client.execute(req);
		    System.out.println(res.getStatusLine().getStatusCode());
		    client.close();
        }

		response.sendRedirect("/openiot/welcome");
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//
//		out.println("<html><body>" );
//		out.println("<p>Hello. The device has been registrered </p> "); 
//		out.println("</body></html>");

	}

}
