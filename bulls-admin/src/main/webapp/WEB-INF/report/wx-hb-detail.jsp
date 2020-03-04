<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>微信抢红包统计</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
        }
    </style>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="${basePath}css/style.css">

</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        微信抢红包统计
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>手机号</th>
                                <th>昵称</th>
                                <th>真名</th>
                                <th>抢到金额</th>
                                <th>抢到时间</th>
                                <th>过期时间</th>
                                <th>红包状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="l" varStatus="st">
                                <tr>
                                    <td>${st.index+1 }</td>
                                    <td><a href="${basePath}user/detail/app?id=${l.userId}">${l.phone }</a></td>
                                    <td><a href="${basePath}user/detail/app?id=${l.userId}">${l.username }</a></td>
                                    <td><a href="${basePath}user/detail/app?id=${l.userId}">${l.true_name }</a></td>
                                    <td>${l.amount }</td>
                                    <td><fmt:formatDate value="${l.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatDate value="${l.over_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <c:if test="${l.status==0 }">未领取</c:if>
                                        <c:if test="${l.status==1 }">已领取</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <p class="text-center">
                            <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>