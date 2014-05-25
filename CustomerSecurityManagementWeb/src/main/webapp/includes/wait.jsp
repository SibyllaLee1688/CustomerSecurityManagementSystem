<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<metahttp-equiv="Content-Type"content="text/html;charset=UTF-8">
<metahttp-equiv="refresh"content="5;url=<s:urlincludeParams="all"/>"/>
<title><s:text name="title"></s:text></title>
</head>
<body>
<h1>Processing, please wait......</h1>
<a href="<s:urlincludeParams="all"/>">Click here</a>If not automatic jump.
</body>
</html>