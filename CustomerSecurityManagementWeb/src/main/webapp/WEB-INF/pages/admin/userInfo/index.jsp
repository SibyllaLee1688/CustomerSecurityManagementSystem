<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<html>
<head>
<title><s:text name="userInfo.list" /></title>

<sx:head debug="false" cache="true"/>
		<script type="text/javascript">

			function fillUserInfo(data){
				var id = data.split("_")[1];
				var tr = dojo.byId("row_"+id);
				var tds = tr.getElementsByTagName("td");
				dojo.byId("id").value = id;
				/* dojo.byId("userId").value = dojo.string.trim(dojo.dom.textContent(tds[0])); */
					dojo.byId("name").value = dojo.string.trim(dojo.dom.textContent(tds[1]));
					dojo.byId("branch").value = dojo.string.trim(dojo.dom.textContent(tds[2]));
			
				}
				
			function clearUserInfo(){
				dojo.byId("id").value = "";
				/* dojo.byId("userId").value = ""; */
					dojo.byId("name").value = "";
					dojo.byId("branch").value = "";
			
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
			    <s:textfield id="id" name="userInfo.id" cssStyle="display:none"/>
				<s:textfield id="name" key="userInfo.name" name="userInfo.name"/>
				<s:textfield id="realname" key="userInfo.realname" name="userInfo.realname"/>
				<s:select id="branch" key="userInfo.branch" list="#application.branchList" name="userInfo.branch" />
				<s:textfield name="condition.username" value="%{condition.username}" cssStyle="display:none"></s:textfield>
				<s:textfield name="condition.searchBranch" value="%{condition.searchBranch}" cssStyle="display:none"></s:textfield>
				<s:textfield name="currentPage" value="%{currentPage}" cssStyle="display:none" />
				<s:token />
				<s:submit id="userInfoSubmit" key="form.submit" notifyTopics="/save"/>
				<s:reset type="button" key="form.clear"/>
			</s:form>
		</div>
</body>
</html>
