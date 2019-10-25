package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import entities.Device;
import entities.User;
import misc.ApiHelper;
import misc.HttpRequestHelper;
import misc.Security;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Login")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLogin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("login.xhtml");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String uname = request.getParameter("uname");
		String pass = request.getParameter("password");

		//		String s = request.getRequestURL().toString();
		//		s = s.substring(0, s.length() - request.getRequestURI().toString().length());
		//		String url = s + "/openiot/api/users/" + uname;

		//System.out.println(url);
		//		String password = "";
		//		 try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
		//
		//	            //HTTP GET method
		//	            HttpGet httpget = new HttpGet(url);
		//	            System.out.println("Executing request " + httpget.getRequestLine());
		//
		//	            // Create a custom response handler
		//	            ResponseHandler < String > responseHandler = res -> {
		//	                int status = res.getStatusLine().getStatusCode();
		//	                if (status >= 200 && status < 300) {
		//	                    HttpEntity entity = res.getEntity();
		//	                    return entity != null ? EntityUtils.toString(entity) : null;
		//	                } else {
		//	                    throw new ClientProtocolException("Unexpected response status: " + status);
		//	                }
		//	            };
		//	            String responseBody = httpclient.execute(httpget, responseHandler);
		//	            System.out.println("----------------------------------------");
		//	            System.out.println(responseBody);
		//		    	user = ApiHelper.getGson().fromJson(responseBody, User.class);
		////	            JsonObject jobj = new JsonParser().parse(responseBody).getAsJsonObject();
		//	            
		////	            password = jobj.get("password").getAsString();
		//		    	password = user.getPassword();
		//		 }

		String path = HttpRequestHelper.getBasePath(request);
		
		String hashedPass = "";
		try {
			hashedPass = Security.generateHash(pass);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		System.out.println("hashedPass: " + hashedPass);
		String query = "/api/users/" + uname + "/" + pass;
		String responseJson = HttpRequestHelper.httpGetRequest(path, query);
		
		if (responseJson != null && !responseJson.isEmpty()) {

			User user = ApiHelper.getGson().fromJson(responseJson, User.class);
			System.out.println(user.getPassword() + "  " + user.getUname());
			request.getSession().setAttribute("user", user);
			response.sendRedirect("/openiot/welcome");

			//			 System.out.println("Did it boys ");
			//			 Cookie userCookie = new Cookie("loggedInId", uname);
			//			 userCookie.setMaxAge(60*60*24*365); //Store cookie for 1 year
			//			 userCookie.setDomain("localhost:8080");
			//			 response.addCookie(userCookie);
			//			 
			//			 response.setContentType("text/html");
			//				PrintWriter out = response.getWriter();
			//
			//				out.println("<html><body>" );
			//				out.println("<p>Hello " + uname + ". You have been logged in </p> "); 
			//				out.println("</body></html>");
		} else {
			System.out.println("oh no ");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.println("<html><body>" );
			out.println("<p>Password or username was wrong </p> "); 
			out.println("</body></html>");
		}



	}

}