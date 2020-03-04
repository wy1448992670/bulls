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
    <title>锁定用户管理</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
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
                锁定用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>锁定用户列表
                        <shiro:hasPermission name="report:exportWlimit">
                            <a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                               href="${basePath}report/export/wlimit" id="add-row"><i class="icon-plus"></i>导出excel</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:activityLock:add">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="${basePath}user/activityLock/add"
                               id="add-row"><i class="icon-plus"></i>添加锁定用户</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="list">
                            <div class="form-group col-md-5 pull-right">
                                <div>
                                    <input class="form-control keyword" name="trueName" type="text"
                                           placeholder="请输入用户姓名搜索" value="${keyword }"/>
                                </div>
                                <input type="hidden" name="page" value="${page }"/>
                            </div>

                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>

                                <th>
                                    序号
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    手机号
                                </th>
                                <th>
                                    金额
                                </th>
                                <th>
                                    锁定时间
                                </th>
                                <th>
                                    锁定天数
                                </th>
                                <shiro:hasPermission name="user:activityLock:edit">
                                    <th width="60">
                                    </th>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="user:activityLock:delete">
                                    <th width="60">
                                    </th>
                                </shiro:hasPermission>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="b" items="${list }" varStatus="status">
                                <tr>
                                    <td>
                                            ${b.id}
                                    </td>
                                    <td>
                                            ${b.name }
                                    </td>
                                    <td>
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${b.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${b.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                            ${b.amount }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${b.lockTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${b.lockDays }
                                    </td>
                                    <shiro:hasPermission name="user:activityLock:edit">
                                        <td>
                                            <a class="edit-row" href="edit?id=${b.id }" id="edit">编辑</a>
                                        </td>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="user:activityLock:delete">
                                        <td>
                                            <a href="${basePath}user/activityLock/delete?id=${b.id }"
                                               onClick="return confirm('确定删除?');">删除</a>
                                        </td>
                                    </shiro:hasPermission>
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
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "190"});
        $(".select2able").change(function () {
            $("#form").submit();
        });

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?page=" + page
            }
        });
    });
</script>
</body>
</html>