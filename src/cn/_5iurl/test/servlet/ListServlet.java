package cn._5iurl.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn._5iurl.restful.SummerServlet;

@SummerServlet(urlPatterns="/*/list")
public class ListServlet extends HttpServlet{

	private static final long serialVersionUID = -1703574459593330679L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(req.getRequestURI());
		String category = (String)req.getAttribute("p0");
		System.out.println("/WEB-INF/www/"+category+"/list.jsp");
		req.getRequestDispatcher("/WEB-INF/www/"+category+"/list.jsp").forward(req, resp);
		
	}
	
}
