package cn._5iurl.restful;

import javax.servlet.http.HttpServlet;

public class SummerServletBean {

	private String name;
	private String[] urlPatterns;
	private HttpServlet servlet;
	
	public SummerServletBean(String name, String[] urlPatterns,
			HttpServlet servlet) {
		super();
		this.name = name;
		this.urlPatterns = urlPatterns;
		this.servlet = servlet;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getUrlPatterns() {
		return urlPatterns;
	}
	public void setUrlPatterns(String[] urlPatterns) {
		this.urlPatterns = urlPatterns;
	}
	public HttpServlet getServlet() {
		return servlet;
	}
	public void setServlet(HttpServlet servlet) {
		this.servlet = servlet;
	}
	
}
