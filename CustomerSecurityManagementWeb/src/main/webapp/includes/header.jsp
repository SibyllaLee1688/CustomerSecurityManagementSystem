<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>

    <body>
       <p>
       	<font color="black" size="3"><s:text name="system" /></font></p>
       	
    <table width="100%" align="center">
    	<tr align="right">
    	
    		
       <td width="50%">
       <s:if test="%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.username != null}">
       <span>
	
	
	<marquee onmouseover=this.stop(); onmouseout=this.start(); scrollamount=1 height=30 scrolldelay=80 direction=left>

	
       		<font color="blue"><s:text name="welcome" /></font><font color="black">&nbsp;
       			<%-- <security:authentication property="principal.username"></security:authentication>  --%>
       		
       		<s:property value="%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.username }" /></font>
       		
       		
       		 
       
       	<%-- you can define yourself userdetail to display more information in session or custom your own filter to do this. <s:if test="%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.username != null}">
       		&nbsp;&nbsp;&nbsp;<font color="blue"><s:text name="department" /></font><font color="black">&nbsp;<s:property value="%{#session.branch }" /></font>
       		 --%> 
       	
	</marquee>
	</span>
	</s:if>
	</td>
		
			<td width="20%" align="right">
    			<s:include value="/includes/langSelector.jsp" />
    		</td>
    		
		
		
    		
    		<td align="right">
    			<%!
       static java.text.SimpleDateFormat dateformat	= new java.text.SimpleDateFormat("E dd/MM/yy");
   		static java.text.SimpleDateFormat timeformat	= new java.text.SimpleDateFormat("HH:mm:ss"); %>
    	<span style="font-size:80%;font-weight:normal;" title="Time in Bei Jing (GMT +8): <%=new java.util.Date()%>"> 
	    				<s:text name="date" /> <i><b><%=timeformat.format(new java.util.Date())%></b></i>, <i><b><%=dateformat.format(new java.util.Date())%></b></i>
	    			</span>
    		</td>
    	</tr>
    </table>
       	       	<hr size="3" color="blue" />
       <p />
       
    </body>
</html>