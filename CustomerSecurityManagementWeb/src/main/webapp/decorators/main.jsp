<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><decorator:title default="Welcome to Customer Security Management System" /></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/includes/customerSecurityManagementSystem.css">
	<decorator:head />
</head>
<body bgcolor="#FFFFFF">
	<%@ include file="/includes/header.jsp"%>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="10" nowrap> </td>
	</tr>
	<tr>
		<td width="1%" nowrap> </td>
		<td width="200" valign="top" nowrap>
			<%@ include file="/includes/navigation.jsp"%>
		</td>
		<td width="1%" nowrap> </td>
		<td valign="top">
			<div class="docBody"><decorator:body/></div>
		</td>
		<td width="1%" nowrap> </td>
	</tr>
	<tr>
		<td height="10" nowrap> </td>
	</tr>
	</table>
	
	<%@ include file="/includes/footer.jsp"%>
	<%@ include file="/includes/copyright.jsp" %>
</body>
</html>