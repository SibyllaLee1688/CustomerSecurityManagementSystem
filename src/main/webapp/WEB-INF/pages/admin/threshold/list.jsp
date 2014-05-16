<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="threshold.list" /></title>
</head>
<body>
<p align="center"><font size="3"><b><s:text name="threshold.list" /></b></font></p>
  <s:if test="thresholds.size > 0">
	<table class="tablecloth">
		<tr>
			<th>
					<s:text name="threshold.riskType" />
				</th>
				<th>
					<s:text name="threshold.riskValue" />
				</th>
				<th>
					<s:text name="threshold.remark" />
				</th>
				<th>
					<s:text name="operation" />
				</th>
		</tr>
		<s:iterator value="thresholds">
			<tr id="row_<s:property value="id"/>">
				<td>
					<s:property value="type" />
				</td>
				<td>
					<s:property value="value" />
				</td>
				<td>
					<s:property value="remark" />
				</td>
				<td>
					<s:url id="removeUrl" action="remove">
						<s:param name="id" value="id" />
					</s:url>
					<sx:a href="%{removeUrl}" targets="thresholds" loadingText="%{getText('Load')}"
        showLoadingText="true"
><s:text name="remove" /></sx:a>
					<sx:a id="a_%{id}" notifyTopics="/edit"><s:text name="edit" /></sx:a>
				</td>
			</tr>
		</s:iterator>
	</table>
</s:if>
</body>
</html>