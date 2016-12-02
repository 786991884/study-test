<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Thank you for registering for a prize.</h3>
	<p>
		Your registration information:
		<s:property value="personBean" />
		modeldrive
		<s:property value="model" />
	</p>
	<p>
		<a href='<s:url action='index'></s:url>'>Return to home page</a>
	</p>
	<h2>
		<s:text name="thankyou"></s:text>
	</h2>
</body>
</html>