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
    <title>活动列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
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
                运营管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>活动列表
                        <shiro:hasPermission name="activity:sendMessage">
                            <a class="btn btn-sm btn-primary-outline pull-right" id="add-row"
                               href="${basePath}activity/sendMessage"><i class="icon-plus"></i>发布活动</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline col-lg-2 pull-right" id="form" action="list">
                            <div class="row">
                            	<div class="form-group pull-right">
                                    <select class="select2able" name="status" id="status">
                                        <option value="">请选择活动状态</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>未启用</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>启用</option>
                                    </select>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <div class="table-responsive">
	                        <table class="table table-bordered table-hover">
	                            <thead>
	                            <tr>
	                                <th>
	                                    活动标题
	                                </th>
	                                <th>
	                                    活动描述
	                                </th>
	                                <th>
	                                    开始时间
	                                </th>
	                                <th>
	                                    结束时间
	                                </th>
	                                <th>
	                                    状态
	                                </th>
	                                <th>
	                                    详情
	                                </th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach var="i" items="${list }" varStatus="s">
	                                <tr>
	                                    <td>
	                                            ${i.name }
	                                    </td>
	                                    <td>
	                                            ${i.descript}
	                                    </td>
	                                    <td>
	                                        <fmt:formatDate value="${i.start_time}" pattern="yyyy-MM-dd"/>
	                                    </td>
	                                    <td>
	                                        <fmt:formatDate value="${i.end_time}" pattern="yyyy-MM-dd"/>
	                                    </td>
	                                    <td>
	                                        <c:if test="${i.status == 1 }"><span
	                                                class="label label-success">启用</span></c:if>
	                                        <c:if test="${i.status == 0 }"><span
	                                                class="label label-warning">未启用</span></c:if>
	                                    </td>
	                                    <td>
	                                        <shiro:hasPermission name="activity:detail">
	                                            <a class="delete-row" href="${basePath}activity/detail?id=${i.id }">编辑</a>&nbsp;
	                                        </shiro:hasPermission>
	                                        <shiro:hasPermission name="activity:forward">
	                                            <a class="delete-row" href="${basePath}activity/activityForward?id=${i.id }">活动详情</a>
	                                        </shiro:hasPermission>
	                                    </td>
	                                </tr>
	                            </c:forEach>
	                            </tbody>
	                        </table>
                        </div>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    function aa() {
        $("#form").submit();
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2able').select2({width: "150"});
        $(".select2able").change(function () {
            $("#form").submit();
        });
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?keyword=${keyword}&seq=${seq}&type=${type}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });
    });
</script>
</body>
</html>