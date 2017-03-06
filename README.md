# 前言

Servlet自从上了3.0版本之后，用起来已经是相当舒服了。注解的加入，让你基本可以抛弃web.xml，零配置写web。

不过，用了之后，还是有些遗憾。就是REST风格URL的支持。很久之前用过SpringMVC之后，对于REST风格的URL就喜欢得不得了。上网查了下，发现了个项目servletrest，项目托管在google code上：[http://code.google.com/p/servletrest/](http://code.google.com/p/servletrest/)

源码就几个类，很快就看完了，也试用了一下。确实是可以支持，但是bug也比较明显。最明显的一个就是，支持了REST的servlet，是无法获取到ServletContext。原因是servletrest在初始化servlet的时候，没有调用servlet的init方法，即没有初始化ServletConfig。还有一个问题，就是获取URI中的参数时，太过繁琐。而且基于第一个问题，参数其实是获取不到的。

花了点时间，重新写了个新的实现，叫summer-restful。主要作用，就是让普通的servlet支持rest风格的url，并且也修复中servletrest的明显bug。

# 实现原理

定义一个Filter。

## init

在容器启动的时候，对所有已通过注解标记的servlet进行扫描验证。这里提到的注解是我们自定义的，叫@SummerServlet，参数有两个，name，UrlPatterns。符合规则的servlet初始化，并用他们的urlPatterns作为该servlet的key，存入一个Map中进行维护。说明下，我们自定义的@SummerServlet中的urlPatterns，跟原生servlet的UrlPatterns有些区别。自定义的urlPatterns中，是支持类似这种格式的url：`/*/detail/* </span>`的，也通过这里，让普通的servlet支持了REST风格的url。

## doFilter

过滤器拦截所有请求。分析请求的servletPath，跟Map中的key进行比对，若匹配，则返回key对应的servlet，并且会注入url中的参数到request；否则，执行默认的动作。

# 使用

有两个步骤。

## 配置全局的Filter

通过web.xml配置的话，例子如下

```
<filter>
	<filter-name>summerFilter</filter-name>
	<filter-class>cn._5iurl.restful.SummerFilter</filter-class>
	<init-param>
		<param-name>servletPackage</param-name>
		<param-value>cn._5iurl.test.servlet</param-value>
	</init-param>
</filter>
<filter-mapping>
	<filter-name>summerFilter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
```

也可以通过注解来配置。这时候需要新建一个Filter继承SummerFilter，例子如下

```
@WebFilter(filterName="restFilter", initParams=@WebInitParam(name="servletPackage", value="cn._5iurl.test.servlet"), urlPatterns="/*")
public class CRestFilter extends SummerFilter{

}
```

## 配置Servlet

在需要Rest风格URL的servlet类中，不再配置到xml或者用@WebServlet注解，而是改用@SummerServlet注解。例子如下

```
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

}
```

## 获取URL中的参数

获取url中的参数。啥意思呢？还是上面的例子。假设用户访问的是：http://localhost:8015/cms/user/detail/1，可以看到，是符合我们定义的url规则：

```
/*/detail/*
```

那我们怎么获取中隐藏其中的参数值：user和1呢。还是看例子

```
String category = (String)req.getAttribute("p0");
String id = (String)req.getAttribute("p1");
```
很简单吧，源码也在这里了。Make yourself at home.