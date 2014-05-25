<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="CustomerInfo" /></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/includes/moneyLaunderingRank.css">
</head>
<body>

<table class="tablecloth">
<tr><td colspan="2"><s:text name="customerInfo.customerId" /></td><td colspan="2"><s:property value="customerInfo.customerId"/></td></tr>
			<tr><td><s:text name="customerInfo.customerName" /></td><td><s:property value="customerInfo.customerName" /></td>
			<td><s:text name="customerInfo.nationality" /></td><td><s:property value="customerInfo.nationality" /></td></tr>
			<tr><td><s:text name="customerInfo.certificateType" /></td><td><s:property value="customerInfo.certificateType" /></td>
			<td><s:text name="customerInfo.certificateId" /></td><td><s:property value="customerInfo.certificateId" /></td></tr>
			<tr><td><s:text name="customerInfo.certificateBeginDate" /></td><td><s:property value="customerInfo.certificateBeginDate" /></td>
			<td><s:text name="customerInfo.certificateEndDate" /></td><td><s:property value="customerInfo.certificateEndDate" /></td></tr>
			<tr><td><s:text name="customerInfo.mobileNumber" /></td><td><s:property value="customerInfo.mobileNumber" /></td>
		<td><s:text name="customerInfo.phoneNumber" /></td><td><s:property value="customerInfo.phoneNumber" /></td></tr>
		<tr><td><s:text name="customerInfo.foreignFlag" /></td><td><s:if test="customerInfo.foreignFlag">
					<s:text name="true" />
				</s:if><s:else>
					<s:text name="false" />
				</s:else></td>
				<td><s:text name="customerInfo.sex" /></td><td><s:property value="customerInfo.sex" /></td></tr>
				<tr><td><s:text name="customerInfo.zipcode" /><td><s:property value="customerInfo.zipcode" /></td><td><s:text name="customerInfo.certificateAddress" /></td><td><s:property value="customerInfo.certificateAddress" /></td></tr>
			<tr><td><s:text name="customerInfo.address" /></td><td><s:property value="customerInfo.address" /></td><td><s:text name="customerInfo.birthday" /></td><td><s:property value="customerInfo.birthday" /></td></tr>
			<tr><td><s:text name="customerInfo.relationcertificateId" /></td><td><s:property value="customerInfo.relationcertificateId" /></td><td><s:text name="customerInfo.relationcertificateType" /></td><td><s:property value="customerInfo.relationcertificateType" /></td></tr>
			<tr><td><s:text name="customerInfo.instrperName" /></td><td><s:property value="customerInfo.instrperName" /></td><td><s:text name="customerInfo.customerType" /></td><td><s:property value="customerInfo.customerType" /></td></tr>
			<tr><td><s:text name="customerInfo.professionCode" /></td><td><s:property value="customerInfo.professionCode" /></td><td><s:text name="customerInfo.branch" /></td><td><s:property value="customerInfo.branch" /></td></tr>
		<tr><td><s:text name="customerInfo.riskValue" /></td><td><s:property value="customerInfo.riskValue" /></td><td><s:text name="customerInfo.riskType" /></td><td><s:property value="customerInfo.riskType" /></td></tr>
			<tr><td><s:text name="customerInfo.remark" /></td><td colspan="3"><s:property value="customerInfo.remark" /></td></tr>
			<tr><td colspan="4"><input type="button" value="close" onclick="window.close()"></td></tr>
</table>
</body>
</html>