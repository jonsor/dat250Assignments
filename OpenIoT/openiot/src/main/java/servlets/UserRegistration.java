package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import entities.User;
import misc.ApiHelper;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd = request.getRequestDispatcher("userRegistration.xhtml");
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String uname = request.getParameter("uname");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String pass = request.getParameter("password");

		System.out.println(fname + " " + " " + pass);

		User u = new User();
		u.setUname(uname);
		u.setPassword(pass);
		
		if (!fname.isEmpty())
			u.setFname(fname);
		if (!lname.isEmpty())
			u.setLname(lname);
		
		String s = request.getRequestURL().toString();
		s = s.substring(0, s.length() - request.getRequestURI().toString().length());
		String url = s + "/openiot/api/users";

		//System.out.println(url);

		String str = ApiHelper.getGson().toJson(u);
		
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html><body>" );
		out.println("<p>Hello " + uname + ". Your account has been registrered </p> "); 
		out.println("</body></html>");

	}

}
