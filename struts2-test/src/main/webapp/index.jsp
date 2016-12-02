<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Basic Struts 2 Application - Welcome</title>
</head>
<body>
	<h1>Welcome to Struts 2!</h1>
	<p>
		<a href='<s:url action="hello"></s:url>'>Hello World</a>

	</p>
	<s:url action="hello" var="helloLink" scheme="http">
		<s:param name="userName">Bruce Phillps</s:param>
	</s:url>
	<p>
		<a href="${helloLink }">Hello Bruce Phillips</a>
	</p>
	<p>Get your own personal hello by filling out and submitting this
		form.</p>
	<s:form action="hello" focusElement="nameId">
		<s:textfield name="userName" id="nameId" label="yourname"></s:textfield>
		<s:submit value="submit"></s:submit>
	</s:form>
	<p>
		<a href="register.jsp">Please register</a>for our prize drawing
	</p>
	<s:url action="registerInput" var="registerInputLink">
		<s:param name="request_locale">zh_CN</s:param>
	</s:url>
	<p>
		<a href="${registerInputLink }">Please register</a>for our prize
		drawing
	</p>
	<hr>
	<s:text name="contact"></s:text>
</body>
</html>