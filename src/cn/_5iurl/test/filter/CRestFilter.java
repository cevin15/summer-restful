package cn._5iurl.test.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import cn._5iurl.restful.SummerFilter;

@WebFilter(filterName="restFilter", initParams=@WebInitParam(name="servletPackage", value="cn._5iurl.test.servlet"), urlPatterns="/*")
public class CRestFilter extends SummerFilter{

}
