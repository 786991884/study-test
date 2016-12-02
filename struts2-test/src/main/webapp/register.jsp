<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<s:head />
</head>
<body>
	<h3>Register for a prize by completing this form.</h3>
	<s:form action="register">
		<s:textfield name="personBean.firstName" label="First name"></s:textfield>
		<s:textfield name="personBean.lastName" label="Last name"></s:textfield>
		<!-- 我们想通过key来指定值，必须经过Action转发过来才会生效。用key的值作为name的值，label的值是根据key对应的属性文件中查找到的，若指定了label则用你指定的 -->
		<!-- 要支持该特性Action一定要实现两个接口TextProvider, -->
		<!--<s:textfield key="personBean.firstName"></s:textfield>-->
		<s:textfield name="personBean.email" label="Email"></s:textfield>
		<s:textfield name="personBean.age" label="Age"></s:textfield>
		<s:submit></s:submit>
	</s:form>
</body>
</html>