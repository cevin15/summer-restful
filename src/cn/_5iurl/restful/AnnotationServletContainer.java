package cn._5iurl.restful;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServlet;

public class AnnotationServletContainer implements ServletContainer {
	private String[] servletPackages;
	private Map<String, SummerServletBean> servlets = new LinkedHashMap<String, SummerServletBean>();

	public AnnotationServletContainer(String[] servletPackages) {
		this.servletPackages = servletPackages;
	}

	public void initContainer() {
		for (String servletPackage : servletPackages) {
			fillContainer(servletPackage);
		}
	}
	
	public SummerServletBean getServlet(String uri){
		SummerServletBean servletBean = servlets.get(uri);
		if(servletBean!=null){
			return servletBean;
		}
		for (String uriInMap : servlets.keySet()) {
			if(Pattern.matches(uriInMap, uri)){
				return servlets.get(uriInMap);
			}
		}
		return null;
	}

	public String[] getParamsInUri(String uri){
		SummerServletBean servletBean = servlets.get(uri);
		if(servletBean!=null){
			return new String[]{};
		}
		for (String uriInMap : servlets.keySet()) {
			Pattern pattern = Pattern.compile(uriInMap);
			Matcher matcher = pattern.matcher(uri);

			String[] params = null;
			if (matcher.matches()) {
				int count = matcher.groupCount();
				params = new String[count];

				for (int i = 1; i <= count; i++) {
					params[i - 1] = matcher.group(i);
				}
				return params;
			}
		}
		return new String[]{};
	}
	
	private void fillContainer(String servletPackage) {
		String packageDirName = servletPackage.replace(".", "/");
		URL url = Thread.currentThread().getContextClassLoader()
				.getResource(packageDirName);
		if(url==null){
			System.out.println("[ERROR] "+servletPackage + " 不是正确的包名");
			throw new RuntimeException(servletPackage + " 不是正确的包名");
		}
		String dir = url.getPath();
		File file = new File(dir);
		List<String> servletClasses = new ArrayList<String>();
		if (file.isDirectory()) {
			String[] fileNames = file.list();
			for (String fileName : fileNames) {
				if (!fileName.endsWith(".class")) {
					continue;
				}
				File tmpFile = new File(dir + "/" + fileName);
				if (tmpFile.isFile()) {
					String newName = fileName.substring(0, fileName.length() - 6);
					servletClasses.add(servletPackage + "." + newName);
				}
			}
		}else{
			throw new RuntimeException(servletPackage + " 不是正确的包名");
		}
		for (String clazzName : servletClasses) {
			try {
				Class<?> clazz = Class.forName(clazzName);
				if(clazz.getSuperclass()!=HttpServlet.class){
					continue;
				}
				if(!clazz.isAnnotationPresent(SummerServlet.class)){
					continue;
				}
				SummerServlet annotation = clazz.getAnnotation(SummerServlet.class);
				String name = annotation.name();
				String[] urlPatterns = annotation.urlPatterns();
				HttpServlet servlet = (HttpServlet)clazz.newInstance();
				SummerServletBean servletBean = new SummerServletBean(name, urlPatterns, servlet);
				for (String urlPattern : urlPatterns) {
					servlets.put(urlPattern.replaceAll("\\*", "([^\\/]\\*)"), servletBean);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new AnnotationServletContainer(new String[]{}).fillContainer("cn._5iurl.restful");
	}
}
