package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Device;

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
				
		RequestDispatcher rd = request.getRequestDispatcher("deviceRegistration.xhtml");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String name = request.getParameter("name");
		String data = request.getParameter("data");
		String imageUrl = request.getParameter("ImageURL");
		String publicDevice = request.getParameter("PublicDevice");
		String status = request.getParameter("Status");
		
		System.out.println(name + " " + " " + data + " " + imageUrl);
		
		Device d = new Device();
		d.setName(name);
		d.setData(data);
		d.setImageUrl(imageUrl);
		d.setPublicDevice(true);
		d.setStatus(status);
	
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>" );
		out.println("<p>Hello. The device has been registrered </p> "); 
		out.println("</body></html>");
		
	}
	 
}
