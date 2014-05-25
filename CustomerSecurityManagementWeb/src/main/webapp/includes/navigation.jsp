<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>

</head>
<body>
<div class="rounded" align="center">
<div id="sitemesh">
<table cellspacing="0" width="100%" border="0">


<sec:authorize  ifAnyGranted="ROLE_ADMIN,ROLE_USER">
	<tr>
			<td style="padding: 1px;"><a href="<%=request.getContextPath() %>/selfService/execute.action"><s:text name="user.changePassword" /></a></td>
		</tr>
		<tr>
			<td style="padding: 1px;"><a href="<%=request.getContextPath() %>/customerInfo/search.action"><s:text name="customerInfo.service" /></a></td>
		</tr>
 </sec:authorize> 
<sec:authorize   ifNotGranted="ROLE_ADMIN,ROLE_USER">

		<td style="padding: 1px;"><a href="<%=request.getContextPath() %>/selfService/execute.action"><s:text name="login.service" /></a></td>
</sec:authorize>

<sec:authorize  ifAnyGranted="ROLE_ADMIN">

		<tr>
			<td style="padding: 1px;"><a
				href="<%=request.getContextPath() %>/userInfo/list.action"><s:text name="userInfo.service" /></a></td>
		</tr>

		<tr>
			<td style="padding: 1px;"><a
				href="<%=request.getContextPath() %>/threshold/execute.action"><s:text name="threshold.service" /></a></td>
		</tr>
		<tr>
			<td style="padding: 1px;"><a
				href="<%=request.getContextPath() %>/riskRank/execute.action"><s:text name="riskRank.service" /></a></td>
		</tr>
</sec:authorize>

<sec:authorize  ifAnyGranted="ROLE_ADMIN,ROLE_USER">
	
		<tr>
			<td style="padding: 1px;">
			<%!
			// Need to restore locale after logout  
				java.util.Locale locale = java.util.Locale.getDefault();
			%>
			<%-- <s:url action="logout" namespace="/login" id="logoutUrl">
				<s:param name="request_locale" value="<%=locale.toString() %>" />
			</s:url> --%>
			<%-- <a href="<%=request.getContextPath() %>/selfService/logout.action?request_locale=<%=locale.toString() %>"><s:text name="login.logout" /></a> --%>
			
			<a href="<%=request.getContextPath() %>/j_security_logout"><s:text name="login.logout" /></a>
			
			</td>
		</tr>
	</sec:authorize>
		
</table>
</div>
</div>
</body>
</html>