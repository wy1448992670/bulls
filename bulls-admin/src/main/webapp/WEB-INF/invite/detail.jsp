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
    <title>二维码邀请详情查询</title>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table {
            table-layout: fixed;
        }

        .table .over {
            overflow: hidden;
            width: 40%;
            text-overflow: ellipsis;
            white-space: nowrap;
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
                报表统计
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>二维码邀请详情查询
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    手机号
                                </th>
                                <th>
                                    注册时间
                                </th>
                                <th>
                                    注册/投资
                                </th>
                                <th>
                                    充值金额
                                </th>
                                <th>
                                    投资金额
                                </th>
                                <th>
                                    提现金额
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }">
                                <tr>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.userId}">${i.username }</a>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.userId}">${i.trueName }</a>
                                    </td>
                                    <td>
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.registTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${i.status==0 }">已注册/未投资</c:if>
                                        <c:if test="${i.status==1 }">已注册/已投资</c:if>
                                    </td>
                                    <td>
                                            ${i.rechargeAmount }
                                    </td>
                                    <td>
                                            ${i.investAmount }
                                    </td>
                                    <td>
                                            ${i.withdrawAmount }
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
        <!-- end DataTables Example -->
    </div>
</div>

</body>
</html>