<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script> 
<script type="text/javascript">
    if ($.cookie("username") != null && $.cookie("username") != "") {
        $("#j_username").val($.cookie("username"));
        $("#j_password").focus();
    } else {
        $("#j_username").focus();
    }
    
    function saveUsername(theForm) {
        $.cookie("username",theForm.j_username.value, { expires: 30, path: "<c:url value="/"/>"});
    }
    
    function validateForm(form) {                                                               
        var valid = validateRequired(form);
        if (valid == false) {
            $(".control-group").addClass('error');
        }
        return valid;
    }

    function passwordHint() {
        if ($("#j_username").val().length == 0) {
            alert("<s:text name="errors.requiredField"><s:param><s:text name="label.username"/></s:param></s:text>");
            $("#j_username").focus();
        } else {
            location.href="<c:url value="/passwordHint"/>?username=" + $("#j_username").val();
        }
    }
    
    function required () { 
        this.aa = new Array("j_username", "<s:text name="errors.requiredField"><s:param><s:text name="label.username"/></s:param></s:text>", new Function ("varName", " return this[varName];"));
        this.ab = new Array("j_password", "<s:text name="errors.requiredField"><s:param><s:text name="label.password"/></s:param></s:text>", new Function ("varName", " return this[varName];"));
    }
</script>

<title>Sign On Money laundering system</title>
</head>
<body>

<font color="red"><s:actionerror />&nbsp;&nbsp;&nbsp;
<s:property value="%{#request.tip}" /> </font>

<sec:authorize  ifAnyGranted="ROLE_ADMIN,ROLE_USER">
	<table class="tablecloth">
		<tr>
			<td><s:form action="changePassword.action" namespace="/selfService" validate="true">
				<s:textfield key="user.name" name="username" value="%{#session.SPRING_SECURITY_CONTEXT.authentication.principal.username }" />
				<s:password key="user.password" name="password" />
				<s:password key="user.newPassword" name="newPassword" />
				<s:password key="user.reNewPassword" name="reNewPassword" />
				<s:submit key="form.submit"/>
			</s:form></td>
		</tr>
	</table>
</sec:authorize>
<sec:authorize  ifNotGranted="ROLE_ADMIN,ROLE_USER">
	<table class="tablecloth">
		<tr><td>
			<table class="wwFormTable">
			<form onsubmit="saveUsername(this);"<%-- return validateForm(this)" --%> method="post" id="loginForm" action="<c:url value='/j_security_check'/>"
   autocomplete="off">
			<s:textfield key="user.name" name="j_username" id="j_username" />
				<s:password key="user.password" name="j_password" id="j_password" />
				<s:checkbox name="_spring_security_remember_me" key="login.rememberme"></s:checkbox>
				<s:submit key="login.submit" />
			
		</form>
		</table>
		</td></tr>
	</table>
</sec:authorize>
</body>
</html>
