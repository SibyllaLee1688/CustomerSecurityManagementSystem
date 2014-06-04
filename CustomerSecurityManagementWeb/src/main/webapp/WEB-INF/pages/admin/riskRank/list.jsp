<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="riskRank.list" /></title>
</head>
<body>
<s:property value="%{#request.tip}" /> 

<p align="center"><font size="3"><b><s:text name="riskRank.list" /></b></font></p>
  <s:if test="riskRanks.size > 0">
	<table class="tablecloth">
		<tr>
			<th>
					<s:text name="riskRank.rankType" />
				</th>
				<th>
					<s:text name="riskRank.minvalue" />
				</th>
				<th>
					<s:text name="riskRank.maxvalue" />
				</th>
				<th style="display:none;"><s:text name="riskRankVersion" /></th>
				<th>
					<s:text name="operation" />
				</th>
		</tr>
		<s:iterator value="riskRanks">
			<tr id="row_<s:property value="id"/>">
				<td>
					<s:property value="rankType" />
				</td>
				<td>
					<s:property value="minValue" />
				</td>
				<td>
					<s:property value="maxValue" />
				</td>
				<td style="display:none;">
                    <s:property value="version" />
                </td>
				<td>
					<s:url id="removeUrl" action="remove">
						<s:param name="id" value="id" />
					</s:url>
					<s:a href="javascript:void(0);" onclick="javascript:removeRiskRank('%{removeUrl}')"><s:text name="remove" /></s:a>
					<s:a href="javascript:void(0);" onclick="javascript:fillRiskRank('a_%{id}')" ><s:text name="edit" /></s:a>
				</td>
			</tr>
		</s:iterator>
	</table>
</s:if>
</body>
</html>