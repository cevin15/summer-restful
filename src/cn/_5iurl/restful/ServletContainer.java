package cn._5iurl.restful;

public interface ServletContainer {

	public void initContainer();
	
	public SummerServletBean getServlet(String uri);
	
	public String[] getParamsInUri(String uri);
}
