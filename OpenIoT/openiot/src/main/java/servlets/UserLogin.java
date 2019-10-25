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