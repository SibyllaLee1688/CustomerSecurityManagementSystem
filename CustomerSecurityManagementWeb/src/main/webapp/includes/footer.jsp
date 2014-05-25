<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
    <body>
    	<hr size="3" color="blue" />
		<table width="100%" align="center">
			<tr>
				<td align="right"> 
			<%-- 	<a href="<% response.encodeURL("/selfService/start.action"); %>"><s:text name="homepage" /></a> --%>
				
					<a href="<%=request.getContextPath() %>/selfService/start.action"><s:text name="homepage" /></a> 
				</td>
			</tr>
		</table>    
    </body>
</html>