<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

		<sx:head debug="false" cache="true"/>
		<script type="text/javascript">
			dojo.event.topic.subscribe("/save", function(data, type, request) {
			    if(type == "before" && validateForm_save()) {
					dojo.byId("submit").disabled = true;
				}
			if(type == "load") {
					dojo.byId("id").value = "";
					dojo.byId("rankType").value = "";
					dojo.byId("minValue").value = "";
					dojo.byId("maxValue").value = "";
					dojo.byId("submit").disabled = false;
				}
			});

			dojo.event.topic.subscribe("/edit", function(data, type, request) {
			    if(type == "before") {
					var id = data.split("_")[1];

					var tr = dojo.byId("row_"+id);
					var tds = tr.getElementsByTagName("td");

					dojo.byId("id").value = id;
					dojo.byId("rankType").value = dojo.string.trim(dojo.dom.textContent(tds[0]));
					dojo.byId("minValue").value = dojo.string.trim(dojo.dom.textContent(tds[1]));
					dojo.byId("maxValue").value = dojo.string.trim(dojo.dom.textContent(tds[2]));
				}
			});
		</script>
	</head>
	<body>
	    <s:url namespace="/riskRank" action="list" id="descrsUrl"/>

        <div class="roundedlist">
        	<div style="text-align: right;">
    			<sx:a notifyTopics="/refresh"><s:text name="refresh" /></sx:a>
    		</div>
    		<sx:div id="riskRanks" theme="ajax" href="%{descrsUrl}" loadingText="%{getText('Load')}" showLoadingText="true"  listenTopics="/refresh, /remove"/>
        </div>

        <br/>

		<div class="roundedsave">
			<p><s:text name="riskRank.data" /></p>
			<s:form action="save" validate="true" namespace="/riskRank" method="post">
			    <s:textfield id="id" name="riskRank.id" cssStyle="display:none" />
				<s:textfield id="rankType" name="riskRank.rankType" key="riskRank.rankType"/>
				<s:textfield id="minValue" key="riskRank.minvalue" name="riskRank.minValue"/>
				<s:textfield id="maxValue" key="riskRank.maxvalue" name="riskRank.maxValue"/>
				<sx:submit id="submit" key="form.submit" targets="riskRanks" notifyTopics="/save" loadingText="%{getText('Load')}"
        showLoadingText="true"/>
				<s:reset type="button" key="form.clear"/>
			</s:form>
		</div>
	</body>
</html>
