<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="userInfo.list" /></title>

		<script type="text/javascript">

			function fillUserInfo(data){
				var id = data.split("_")[1];
				var tr = document.getElementById("row_"+id);
				var tds = tr.getElementsByTagName("td");
				document.getElementById("id").value = id;
				/* dojo.byId("userId").value = dojo.string.trim(dojo.dom.textContent(tds[0])); */
					document.getElementById("name").value = tds[0].innerHTML.trim();
					document.getElementById("realname").value = tds[1].innerHTML.trim();
					document.getElementById("branch").value =tds[2].innerHTML.trim();
					document.getElementById("version").value =tds[3].innerHTML.trim();
				}
				
			function clearUserInfo(){
				document.getElementById("id").value = "";
				document.getElementById("name").value = "";
				document.getElementById("realname").value = "";
				document.getElementById("branch").value = "";
				document.getElementById("version").value = "";
			}
		</script>
	</head>

<body>
<div>
<table width="100%">
	<tr valign="top"><td width="25%">
	<div class="roundedsearch">
		<p><s:text name="condition.search" /></p>
		<s:form action="list" validate="true" namespace="/userInfo" >
				<s:textfield key="condition.realname" name="condition.realname"/>
				<s:textfield key="condition.username" name="condition.username"/>
				<s:select key="condition.searchBranch" name="condition.searchBranch" list="#application.branchList"/>
				<s:submit id="listSubmit" key="form.search"/>
		</s:form>
		</div>	
	</td><td width="75%">
<%@ include file="./list.jspf"%>
</td></tr></table>
</div>
	<br />

<div class="roundedsave">
			<p><s:text name="userInfo.data" /></p>
			<s:form action="save" validate="true" namespace="/userInfo" method="post">
			    <s:token />
			    <s:textfield id="id" name="userInfo.id" cssStyle="display:none"/>
			    <s:textfield id="version" name="userInfo.version" cssStyle="display:none"/>
				<s:textfield id="name" key="userInfo.name" name="userInfo.username"/>
				<s:textfield id="realname" key="userInfo.realname" name="userInfo.realname"/>
				<s:select id="branch" key="userInfo.branch" list="#application.branchList" name="userInfo.branch" />
				<%-- <s:textfield  id="branch" key="userInfo.branch" name="userInfo.branch" ></s:textfield> --%>
				<s:textfield name="condition.username" value="%{condition.username}" cssStyle="display:none"></s:textfield>
				<s:textfield name="condition.searchBranch" value="%{condition.searchBranch}" cssStyle="display:none"></s:textfield>
				<s:textfield name="currentPage" value="%{currentPage}" cssStyle="display:none" />
				<s:submit id="userInfoSubmit" key="form.submit" notifyTopics="/save"/>
				<s:reset type="button" key="form.clear"/>
			</s:form>
		</div>
		
		<div>
		  <s:if test="hasActionErrors()">
			    <s:actionerror/>
			</s:if>
		</div>
</body>
</html>
