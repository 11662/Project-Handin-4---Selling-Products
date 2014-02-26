package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String user1 = "admin";
	private final String pass1 = "pass";
	private final int expireTime = 30*60; //30 minutes

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username.equals(user1) && password.equals(pass1)){
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(expireTime); 
			System.out.println("login succesfull");
	
			Cookie userCookie = new Cookie("user", username);
			userCookie.setMaxAge(expireTime);
//			response.addCookie(userCookie);
			response.sendRedirect("Admin/adminProductList.jsf");
//			response.sendRedirect("http://www.google.com");
			
		}
		else{
			System.out.println("Wrong Login");
		}
	}
}