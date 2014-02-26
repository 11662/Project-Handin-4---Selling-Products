package filters;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

public class LoginFilter implements Filter {

	ServletContext context;

	public void init(FilterConfig c) throws ServletException {
		context = c.getServletContext();
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		String uri = request.getRequestURI();
		HttpSession session = request.getSession(false);

		if (uri.endsWith("login.jsf") || uri.endsWith("LoginServlet") || session != null) {
			chain.doFilter(request, response);
		}
		
		else {
			System.out.println("Redirected to login");
			response.sendRedirect("login.jsf");
		}
	}
}