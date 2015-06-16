package cn._5iurl.restful;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * 自定义ServletConfig
 * @author yangyingqiang
 * @time 2015年6月11日 下午2:45:44
 *
 */
public class SummerServletConfig implements ServletConfig{

	private String servletName;
	private ServletContext servletContext;
	private Map<String, String> params;
	
	public SummerServletConfig(String servletName, ServletContext servletContext, 
			Map<String, String> params) {
		this.servletName = servletName;
		this.servletContext = servletContext;
		this.params = params;
	}
	
	@Override
	public String getServletName() {
		return servletName;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public String getInitParameter(String name) {
		return params.get(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		Vector<String> v = new Vector<String>();
		Set<String> set = params.keySet();
		for (String tmp : set) {
			v.add(tmp);
		}
		return v.elements();
	}

}
