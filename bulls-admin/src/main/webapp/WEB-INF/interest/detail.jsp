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
    <title>用户利息详情</title>
    <style type="text/css">
        strong.money {
            color: #007aff;
            font-size: 18px;
        }
    </style>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                用户利息详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户利息详情
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered" id="trade">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>派发时间</th>
                                <th>类型（活期/散标）</th>
                                <th>利息金额</th>
                                <th>状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="i">
                                <tr>
                                    <td>${i.id }</td>
                                    <td><fmt:formatDate value="${i.date }" pattern="yyyy-MM-dd"/></td>
                                    <td>
                                        <c:if test="${i.investmentId == null }"><label
                                                class="label label-danger">活期</label></c:if>
                                        <c:if test="${i.investmentId != null }"><label
                                                class="label label-success">散标</label></c:if>
                                    </td>
                                    <td>${i.interestAmount }</td>
                                    <td>
                                        <c:if test="${i.hasDividended == 0 }"><label
                                                class="label label-danger">未发放</label></c:if>
                                        <c:if test="${i.hasDividended == 1 }"><label
                                                class="label label-success">已发放</label></c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="text-right">
                            <ul id="pagination"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "detail?userId=${userId}&page=" + page;
            }
        });
    });
</script>