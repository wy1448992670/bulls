<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>安卓渠道详情统计列表</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
        }
    </style>
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
                        安卓渠道详情统计列表
                        <shiro:hasPermission name="report:export:wx">
                            <a class="btn btn-sm btn-primary-outline pull-right" id=""
                               href="${basePath}report/androidChannelByCodeExport?code=${code}&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/> "><i
                                    class="icon-plus"></i>导出Excel</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>手机号</th>
                                <th>昵称</th>
                                <th>真实姓名</th>
                                <th>充值金额</th>
                                <th>提现金额</th>
                                <th>账户余额</th>
                                <th>在投金额</th>
                                <th>注册时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="l" varStatus="st">
                                <tr>
                                    <td>${st.index+1 }</td>
                                    <td><a href="${basePath }user/detail/app?id=${l.uid}">${l.phone }</a></td>
                                    <td><a href="${basePath }user/detail/app?id=${l.uid}">${l.username }</a></td>
                                    <td><a href="${basePath }user/detail/app?id=${l.uid}">${l.trueName }</a></td>
                                    <td>${l.rechargeAmount }</td>
                                    <td>${l.withdrawAmount }</td>
                                    <td>${l.balance }</td>
                                    <td>${l.investAmount }</td>
                                    <td><fmt:formatDate value="${l.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "gotoAndroidChannelDetailFromCode?code=${code}&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });
    });
</script>
</body>
</html>
