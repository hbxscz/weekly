<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>




<div id="header">
	<div id="title">
            <h4>
            <c:if test="${pageContext.request.requestURI != '/login'}">
            <div class="navbar">
                <div>
                    <ul class="nav">
                        <li>
                            <a class="dropdown-toggle"
                               data-toggle="dropdown"
                               href="#">
                                eBay
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a tabindex="-1" href="keyword">Keyword</a></li>
                                <li><a tabindex="-1" href="item">Items</a></li>
                            </ul>
                        </li>
                        <li><a href="#">About</a></li>
                        <!--
                        <li class="dropdown">
                            <a class="dropdown-toggle"
                               data-toggle="dropdown"
                               href="#">
                                Dropdown
                                <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a tabindex="-1" href="#">Action</a></li>
                                <li><a tabindex="-1" href="#">Another action</a></li>
                                <li><a tabindex="-1" href="#">Something else here</a></li>
                                <li class="divider"></li>
                                <li><a tabindex="-1" href="#">Separated link</a></li>
                            </ul>
                        </li>
                        -->
                    </ul>
                </div>
            </div>
</c:if>
            <%--<small>演示</small>--%>
	    <shiro:user>
			<div class="btn-group pull-right">
				<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
					<i class="icon-user"></i> <shiro:principal property="name"/>
					<span class="caret"></span>
				</a>
			
				<ul class="dropdown-menu">
					<shiro:hasRole name="admin">
						<li><a href="${ctx}/admin/user">Admin Users</a></li>
						<li class="divider"></li>
					</shiro:hasRole>
					<li><a href="${ctx}/profile">Edit Profile</a></li>
					<li><a href="${ctx}/logout">Logout</a></li>
				</ul>
			</div>
		</shiro:user>
		</h4>
	</div>
</div>
