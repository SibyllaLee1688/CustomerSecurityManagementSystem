<%@ taglib prefix="s" uri="/struts-tags" %>

<s:set name="SESSION_LOCALE" value="#session['WW_TRANS_I18N_LOCALE']"/>

<s:bean id="locales" name="com.elulian.CustomerSecurityManagementSystem.common.Locales">
	<s:param name="current" value="#SESSION_LOCALE == null ? locale : #SESSION_LOCALE"/> 
</s:bean>

<form action="<s:url includeParams="get" encode="true"/>" id="langForm">
    <s:text name="Language" />  <s:select 
        list="#locales.locales" listKey="value"    listValue="key"
        value="#SESSION_LOCALE == null ? locale : #SESSION_LOCALE"
        name="request_locale" id="langSelecter" 
        onchange="document.getElementById('langForm').submit();" theme="simple"/>
</form>