package cn._5iurl.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn._5iurl.restful.SummerServlet;

@SummerServlet(name="detailServlet", urlPatterns="/*/detail/*")
public class DetailServlet extends HttpServlet{

	private static final long serialVersionUID = 8268477143225755822L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String category = (String)req.getAttribute("p0");
		String id = (String)req.getAttribute("p1");
		
		req.setAttribute("id", id);
		req.getRequestDispatcher("/WEB-INF/www/"+category+"/detail.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String _method = req.getParameter("_method");
		if(_method!=null){
			if(_method.equalsIgnoreCase("delete")){
				doDelete(req, resp);
			}else if(_method.equalsIgnoreCase("put")){
				doPut(req, resp);
			}
		}else{
			_doPost(req, resp);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("do delete");
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("do put");
	}
	
	protected void _doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("do post");
	}
}
