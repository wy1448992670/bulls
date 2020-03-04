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
    <title>质押应收账款清单</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
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
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>质押应收账款清单
                        <a class="btn btn-sm btn-primary-outline pull-right" href="add" id="add-row"><i
                                class="icon-plus"></i>创建清单</a>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>债权人信息</th>
                                <th>交易合同名称</th>
                                <th>交易合同编号</th>
                                <th>交易合同签署日期</th>
                                <th>应收账款基础交易</th>
                                <th>应收账款金额</th>
                                <th>应收账款到期日</th>
                                <th>状态</th>
                                <th width="120">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="p" items="${list }" varStatus="st">
                                <tr>
                                    <td>${p.id }</td>
                                    <td>${p.userInfo }</td>
                                    <td>${p.name }</td>
                                    <td>${p.orderNo }</td>
                                    <td>
                                        <fmt:formatDate value="${p.signDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>${p.basicAmount }</td>
                                    <td>${p.amount }</td>
                                    <td>
                                        <fmt:formatDate value="${p.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${p.status==0}">启用</c:if>
                                        <c:if test="${p.status==1}">未启用</c:if>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="${basePath }project/account/edit?id=${p.id}"
                                           id="edit">编辑</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        $('.select2able').select2({width: "150"});
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?page=" + page;
            }
        });
    });
</script>
</body>
</html>