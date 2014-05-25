<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
<!--清理缓存开始-->
<meta content="no-cache" http-equiv="Pragma"/>
<meta content="no-cache" http-equiv="Cache-Control"/>
<meta content="0" http-equiv="Expires"/>
<!--清理缓存结束-->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/ext/resources/css/ext-all.css" />
<link rel="Stylesheet" type="text/css" href="<%=request.getContextPath() %>/js/ext/resources/css/xtheme-slate.css" /> 
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext/adapter/ext/ext-base.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/ext/ext-lang-zh_CN.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/js/customerInfos.js"></script>
</head>
<body>
<table width="100%">
	<tr valign="top"><td width="30%">
	<div class="roundedsearch">
	<p><s:text name="condition.search" /></p>
  <s:form action="search" validate="true" namespace="/customerInfo" method="post">
		<s:textfield id="customerId" name="condition.customerId" key="condition.customerId"/>
		<s:textfield id="riskMinValue" key="condition.riskMinValue" name="condition.riskMinValue"/>
		<s:textfield id="riskMaxValue" key="condition.riskMaxValue" name="condition.riskMaxValue"/>
		<s:if test="%{#session.branch == \"ALL\" }">
		<s:select id="branch" key="condition.branch" list="#application.branchList" name="condition.searchBranch" />
		</s:if>
		<s:else>
			<s:textfield cssStyle="display:none" id="searchBranch" name="condition.searchBranch" value="%{#session.branch}"/>
		</s:else>
		<s:submit id="submit" key="form.search" />
		<s:reset type="button" key="form.clear"/>
		</s:form>
		</div>
  </td>
  <td>
<div class="roundedsave">
<table><tr><td>
<img align="center" border="0" alt="<s:text name="image" />" id="indicator" src="
<s:url action='chart' namespace='/chart'>
						
			<s:param name='condition.customerId' value='%{condition.customerId}'></s:param>
			<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param>
	<s:param name='p'><%=request.hashCode() %></s:param>
</s:url>
" />

  <%-- 
	//fix the cache issue. It is passed in as a different url everytime and so it will display the new
image other wise if the url is the same then you will get displayed the
cached image. 
	
	<%=request.getContextPath() %>/jfreechart/customerInfoJFreeChart.action?p=<%=request.hashCode()%>" /> 
	
	--%>
</td><td valign="top">
<table><tr><th><s:text name="report" /></th></tr><tr><td>
<a target="_blank" href="<s:url action='HTML' namespace='/report'>
						
			<s:param name='condition.customerId' value='%{condition.customerId}'></s:param>
			<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param></s:url>">HTML</a>
<br><a target="_blank" href="<s:url action='PDF' namespace='/report'>
						
			<s:param name='condition.customerId' value='%{condition.customerId}'></s:param>
			<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param></s:url>">PDF</a>
<br><a target="_blank" href="<s:url action='XML' namespace='/report'>
						
			<s:param name='condition.customerId' value='%{condition.customerId}'></s:param>
			<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param></s:url>">XML</a>
<br><a target="_blank" href="<s:url action='CSV' namespace='/report'>
						
			<s:param name='condition.customerId' value='%{condition.customerId}'></s:param>
			<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param></s:url>">CSV</a>
<br><a target="_blank" href="<s:url action='XLS' namespace='/report'>
						
			<s:param name='condition.customerId' value='%{condition.customerId}'></s:param>
			<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param></s:url>">XLS</a>
</td></tr></table>
</td></tr></table>
    </div>
    </td>
  </tr>
   <%--
  <tr><td colspan="2">
  <s:url namespace="/customerInfo" action="list" id="descrsUrl">
  	<s:param name="condition.customerId" value="%{condition.customerId}"></s:param>
	<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param>
  </s:url>
 
  <div class="roundedlist"> 
  <sx:div id="customerInfos" href="%{descrsUrl}" showLoadingText="true" loadingText="%{getText('Load')}"/>
  </div>
  
  </td>
</tr> --%>
<tr><td colspan="2">
<div style="display:none">
	<a id="example" href="<s:url namespace='/example' action='JSONExample'>
  	<s:param name='condition.customerId' value='%{condition.customerId}'></s:param>
	<s:param name='condition.searchBranch' value='%{condition.searchBranch}'></s:param>
	<s:param name='condition.riskMinValue' value='%{condition.riskMinValue}'></s:param>
	<s:param name='condition.riskMaxValue' value='%{condition.riskMaxValue}'></s:param>
  </s:url>" /a>

</div>
</td></tr>
  </table>
  <div id="customers">
</div>
<sec:authorize  ifAnyGranted="ROLE_ADMIN">
<div id = "customersSource">
</div>
</sec:authorize>
</body>
</html>