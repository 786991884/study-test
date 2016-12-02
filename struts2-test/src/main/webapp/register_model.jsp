<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模型驱动法</title>
<s:head />
</head>
<body>
	<h3>Register for a prize by completing this form.</h3>
	<s:form action="register">
		<s:textfield name="firstName" label="First name"></s:textfield>
		<s:textfield name="lastName" label="Last name"></s:textfield>
		<s:textfield name="email" label="Email"></s:textfield>
		<s:textfield name="age" label="Age"></s:textfield>
		<s:submit></s:submit>
	</s:form>
</body>
</html>