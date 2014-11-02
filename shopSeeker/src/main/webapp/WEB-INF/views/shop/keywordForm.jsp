<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>关键词管理</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/keyword/${action}" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${keyword.id}"/>
		<fieldset>
			<legend><small>管理关键词</small></legend>
			<div class="control-group">
				<label for="keyword_value" class="control-label">关键词名称:</label>
				<div class="controls">
					<input type="text" id="keyword_value" name="value"  value="${keyword.value}" class="input-large required" minlength="3"/>
				</div>
			</div>	
			<div class="control-group">
				<label for="keyword_sku" class="control-label">SKU:</label>
				<div class="controls">
					<input type="text" id="keyword_sku" name="sku" class="input-large" value="${keyword.sku}" />
				</div>
			</div>
            <div class="control-group">
                <label for="keyword_active" class="control-label">状态:</label>
                <div class="controls">
                    <select id="keyword_active" name="active" class="input-large">
                        <option value="1">enable</option>
                        <option value="0">disable</option>
                    </select>
                </div>
            </div>
            <div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#keyword_value").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
