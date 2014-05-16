<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="customerInfo.list" /></title>
</head>
<body>

<p align="center"><font size="3"><b><s:text name="customerInfo.list" /></b></font></p>
<s:if test="customerInfos.size > 0">
	<table class="tablecloth">
		<tr>
			<th><s:text name="customerInfo.customerId" /></th>
			<th><s:text name="customerInfo.customerName" /></th>
			<th><s:text name="customerInfo.nationality" /></th>
			<th><s:text name="customerInfo.certificateType" /></th>
			<th><s:text name="customerInfo.certificateId" /></th>
			<th><s:text name="customerInfo.certificateBeginDate" /></th>
			<th><s:text name="customerInfo.certificateEndDate" /></th>
			<th><s:text name="customerInfo.mobileNumber" /></th>
			<th><s:text name="customerInfo.phoneNumber" /></th>
			<th><s:text name="customerInfo.foreignFlag" /></th>
			<%-- <th><s:text name="operation" /></th>
			--%>
			
		</tr>
		<s:iterator value="customerInfos">
			<tr id='row_<s:property value="id"/>'>
			
				<td style="cursor:pointer;" onclick="window.showModalDialog('<%=request.getContextPath() %>/customerInfo/detailInfo.action?id=<s:property value="id"/>');"><s:property value="customerId" /></td>
				<td><s:property value="customerName" /></td>
				<td><s:property value="nationality" /></td>
				<td><s:property value="certificateType" /></td>
				<td><s:property value="certificateId" /></td>
				<td><s:property value="certificateBeginDate" /></td>
				<td><s:property value="certificateEndDate" /></td>
				<td><s:property value="mobileNumber" /></td>
				<td><s:property value="phoneNumber" /></td>
				<td><s:if test="foreignFlag">
					<s:text name="true" />
				</s:if><s:else>
					<s:text name="false" />
				</s:else></td>
				<%-- 
				<td><s:url id="removeUrl" action="remove" namespace="/customerInfo">
					<s:param name="id" value="id" />
				</s:url> <sx:a href="%{removeUrl}" targets="customerInfos"><s:text name="remove" /></sx:a>
				<sx:a id="a_%{id}" notifyTopics="/edit"><s:text name="edit" /></sx:a></td>
				--%>
			</tr><tr id="row2_<s:property value="id"/>" style="display: none"><td colspan="11"><table><tr><th><s:text name="customerInfo.sex" /></th>
			<th><s:text name="customerInfo.zipcode" /></th>
			<th><s:text name="customerInfo.certificateAddress" /></th>
			<th><s:text name="customerInfo.address" /></th>
			<th><s:text name="customerInfo.birthday" /></th>
			<th><s:text name="customerInfo.relationcertificateId" /></th>
			<th><s:text name="customerInfo.relationcertificateType" /></th>
			<th><s:text name="customerInfo.instrperName" /></th>
			<th><s:text name="customerInfo.customerType" /></th>
			<th><s:text name="customerInfo.professionCode" /></th>
			<th><s:text name="customerInfo.branch" /></th>
			<th><s:text name="customerInfo.riskValue" /></th>
			<th><s:text name="customerInfo.riskType" /></th>
			<th><s:text name="customerInfo.remark" /></th></tr><tr>
				<td><s:property value="sex" /></td>
				<td><s:property value="zipcode" /></td>
				<td><s:property value="certificateAddress" /></td>
				<td><s:property value="address" /></td>
				<td><s:property value="birthday" /></td>
				<td><s:property value="relationcertificateId" /></td>
				<td><s:property value="relationcertificateType" /></td>
				<td><s:property value="instrperName" /></td>
				<td><s:property value="customerType" /></td>
				<td><s:property value="professionCode" /></td>
				<td><s:property value="branch" /></td>
				<td><s:property value="riskValue" /></td>
				<td><s:property value="riskType" /></td>
				<td><s:property value="remark" /></td>
				</tr></table></td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="11">
			<table>
				<tr>
					<td><s:text name="show"></s:text> <s:property value="currentPage" /> / <s:property
						value="totalPage" /> <s:text name="page" /></td>
					<s:if test="%{currentPage != 1}"><td>
						<s:url action="list" namespace="/customerInfo" id="firstUrl">
							<s:param name="pageNum" value="%{1}"></s:param>
								<s:param name="condition.customerId" value="%{condition.customerId}" />
							<s:param name="condition.searchBranch" value="%{condition.searchBranch}" />
							<s:param name="condition.riskMinValue" value="%{condition.riskMinValue}" />
							<s:param name="condition.riskMaxValue" value="%{condition.riskMaxValue}" />
						</s:url>
						<sx:a href="%{firstUrl}" key="first" cssClass="first" targets="customerInfos" loadingText="%{getText('Load')}"
        showLoadingText="true"
/>
						</td>
					</s:if> <s:if test="%{hasPrevious}"><td>
						<s:url action="list" namespace="/customerInfo" id="previousUrl">
							<s:param name="pageNum" value="%{currentPage - 1}"></s:param>
								<s:param name="condition.customerId" value="%{condition.customerId}"></s:param>
							<s:param name="condition.searchBranch" value="%{condition.searchBranch}"></s:param>
							<s:param name="condition.riskMinValue" value="%{condition.riskMinValue}"></s:param>
							<s:param name="condition.riskMaxValue" value="%{condition.riskMaxValue}"></s:param>
						</s:url>
						<sx:a href="%{previousUrl}" key="previous"  targets="customerInfos" cssClass="prev" loadingText="%{getText('Load')}"
        showLoadingText="true"
/>
							</td>
					</s:if><s:if test="%{hasNext}"><td>
						<s:url action="list" namespace="/customerInfo" id="nextUrl">
							<s:param name="pageNum" value="%{currentPage + 1}"></s:param>
							<s:param name="condition.customerId" value="%{condition.customerId}"></s:param>
							<s:param name="condition.searchBranch" value="%{condition.searchBranch}"></s:param>
							<s:param name="condition.riskMinValue" value="%{condition.riskMinValue}"></s:param>
							<s:param name="condition.riskMaxValue" value="%{condition.riskMaxValue}"></s:param>
						</s:url>
						<sx:a href="%{nextUrl}" key="next" targets="customerInfos" cssClass="next" loadingText="%{getText('Load')}"
        showLoadingText="true"
 />
						</td>
					</s:if> <s:if test="%{currentPage != totalPage}"><td>

						<s:url action="list" namespace="/customerInfo" id="lastUrl">
							<s:param name="pageNum" value="%{totalPage}"></s:param>
							<s:param name="condition.customerId" value="%{condition.customerId}"></s:param>
							<s:param name="condition.searchBranch" value="%{condition.searchBranch}"></s:param>
							<s:param name="condition.riskMinValue" value="%{condition.riskMinValue}"></s:param>
							<s:param name="condition.riskMaxValue" value="%{condition.riskMaxValue}"></s:param>
						</s:url>
						<sx:a href="%{lastUrl}" key="last" targets="customerInfos" cssClass="last" loadingText="%{getText('Load')}"
        showLoadingText="true"
/>
						</td>
					</s:if>


				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:if>
</body>

</html>