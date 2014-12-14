<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>关键词管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>名称：</label> <input type="text" name="search_LIKE_value" class="input-medium" value="${param.search_LIKE_value}">
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	    <tags:sort/>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
            <tr>
                <th>ItemID</th>
                <th>SKU</th>
                <th>标题</th>
                <th>图片</th>
                <th>价格</th>
                <th>物品所在地</th>
                <th>店铺</th>
                <th>管理</th>
            </tr>
        </thead>
		<tbody>
		<c:forEach items="${items.content}" var="item">
			<tr>
                <td><a href="${ctx}/item/showPurchase/${item.itemId}" target="_blank">${item.itemId}</a></td>
				<td><a href="${ctx}/item/update/${item.id}">${item.sku}</a></td>
                <td style="width: 200px;"><a href="http://www.ebay.com/itm/${item.itemId}">${item.title}</a></td>
                <td style="width: 100px; height: 100px"><img src="${item.imageUrl}"/></td>
                <td><a href="${ctx}/item/update/${item.id}">${item.price}</a></td>
                <td><a href="${ctx}/item/update/${item.id}">${item.location}</a></td>
                <td><a href="${ctx}/item/update/${item.id}">${item.memberId}</a></td>
				<td><a href="${ctx}/item/delete/${item.id}">删除</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<tags:pagination page="${items}" paginationSize="5"/>

	<div><a class="btn" href="${ctx}/item/create">创建关键词</a></div>
</body>
</html>
