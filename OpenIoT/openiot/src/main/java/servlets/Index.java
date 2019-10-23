package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */

@WebServlet("/Index")
public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Index() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Skal være false, men setter til true for nå bare sånn at jeg kommer inn til index.xhtml
		Boolean foundCookie = true;
		
		/*
		Cookie[] cookies = request.getCookies();

		

		System.out.println(cookies);
		if (cookies != null) {
			for(int i = 0; i < cookies.length; i++) { 
				if (cookies[i].getName().equals("loggedInId")) {
					foundCookie = true;
				}
			}  
		}
*/
		if (foundCookie) {
			RequestDispatcher rd = request.getRequestDispatcher("index.xhtml");
			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("Login");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Navigate to the correct pages



	}

}
