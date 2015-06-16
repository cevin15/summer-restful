package cn._5iurl.restful;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

public class SummerFilter implements Filter{

	private final static String SERVLET_PACKAGE = "servletPackage";
	
	private String[] servletPackages = null;
	private ServletContainer servletContainer;
	private ServletContext servletContext;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("----------------SummerFilter init----------------");
		String servletPackageStr = filterConfig.getInitParameter(SERVLET_PACKAGE);
		System.out.println(servletPackageStr);
		if(servletPackageStr==null){
			throw new RuntimeException("SummerFilter过滤器初始化参数［servletPackage］不能为空");
		}
		servletPackages = servletPackageStr.split(",");
		servletContainer = new AnnotationServletContainer(servletPackages);
		servletContainer.initContainer();
		servletContext = filterConfig.getServletContext();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest)request;
		String uri = httpReq.getServletPath();
		SummerServletBean servletBean = servletContainer.getServlet(uri);
		if(servletBean==null){
			chain.doFilter(request, response);
		}else{
			HttpServlet servlet = servletBean.getServlet();
			if(servlet.getServletConfig()==null){	//第一次要调用servlet的init方法，注入自定义的ServletConfig
				servlet.init(new SummerServletConfig(servletBean.getName(), servletContext, new HashMap<String, String>()));
			}
			
			//初始化rest参数
			String[] params = servletContainer.getParamsInUri(uri);
			for (int i=0, l=params.length; i<l; i++) {
				String p = params[i];
				request.setAttribute("p"+i, p);
			}
			servlet.service(request, response);
		}
	}

	@Override
	public void destroy() {
		System.out.println("----------------SummerFilter destroy----------------");
	}

}
