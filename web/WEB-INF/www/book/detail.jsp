<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	这里是图书详情页，${id}
	<form action="/cms/book/detail/1" method="post">
		<input type="hidden" name="_method" value="delete"/>
		<input type="submit" />
	</form>
</body>
</html>