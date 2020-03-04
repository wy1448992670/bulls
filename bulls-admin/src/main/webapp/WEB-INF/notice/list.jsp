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
    <title>公告列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                        <i class="icon-table"></i>公告列表
                        <shiro:hasPermission name="notice:add">
	                        <a class="btn btn-sm btn-primary-outline pull-right" href="${basePath}notice/add"
	                           id="add-row"><i class="icon-plus"></i>发布公告</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline col-lg-2 pull-right" id="form" action="list">
                            <div class="row">
                            	<div class="form-group pull-right">
                                 <select class="select2able" name="status" id="status">
                                     <option value="">请选择公告状态</option>
                                     <option value="0" <c:if test="${status == 0 }">selected</c:if>>草稿</option>
                                     <option value="1" <c:if test="${status == 1 }">selected</c:if>>已发布</option>
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
	                                    ID
	                                </th>
	                                <th>
	                                    标题
	                                </th>
	                                <th>
	                                    创建时间
	                                </th>
	                                <th>
	                                    发布时间
	                                </th>
	                                <th>
	                                    状态
	                                </th>
	                                <th>
	                                    是否显示在滚动栏
	                                </th>
	                                <th>操作</th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach var="b" items="${list }">
	                                <tr>
	                                    <td>
	                                            ${b.id }
	                                    </td>
	                                    <td>
	                                            ${b.title }
	                                    </td>
	                                    <td>
	                                        <fmt:formatDate value="${b.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                    </td>
	                                    <td>
	                                        <fmt:formatDate value="${b.sendDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
	                                    </td>
	                                    <td>
	                                        <c:if test="${b.status == 0 }"><span
	                                                class="label label-warning">草稿</span></c:if>
	                                        <c:if test="${b.status == 1 }"><span
	                                                class="label label-success">已发布</span></c:if>
	                                    </td>
	                                    <td>
	                                        <c:if test="${b.isShow == 0}"><span
	                                                class="label label-warning">否</span></c:if>
	                                        <c:if test="${b.isShow == 1 }"><span
	                                                class="label label-success">是</span></c:if>
	                                    </td>
	                                    <td>
	                                        <%-- <a class="btn btn-primary" target="_blank"
	                                           href="http://m.xinjucai.com/notice?id=${b.id }" id="detail">查看</a> --%>
	                                        <shiro:hasPermission name="notice:update">
	                                        	<a class="btn btn-info" href="${basePath}notice/update?id=${b.id }">编辑</a>
	                                        </shiro:hasPermission>
	                                        <c:if test="${b.isShow == 0}">
	                                            <c:if test="${b.status != 0}">
	                                                <a href="javascript:void(0)" onclick="saveSetRoll(${b.id},${b.status})"
	                                                   class="btn btn-success">设置显示</a>
	                                            </c:if>
	                                        </c:if>
	                                        <c:if test="${b.isShow == 1}">
	                                            <a href="javascript:void(0)" onclick="saveCancelRoll(${b.id})"
	                                               class="btn btn-warning">取消显示</a>
	                                        </c:if>
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
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">

    function saveSetRoll(id, status) {
        if (confirm("是否设置该公告在首页滚动显示？")) {
            $.ajax({
                url: '${basePath}notice/saveSetRoll?noticeId=' + id,
                type: 'post',
                beforeSend: function () {
                },
                success: function (data) {
                    alert("设置成功");
                    window.location.reload();
                },
                error: function () {
                    alert('请求失败，请重试');
                },
                complete: function () {
                    <%--window.location.href="${basePath}notice/list"--%>
                }
            });
        }
    }

    function saveCancelRoll(id) {
        if (confirm("是否取消公告滚动显示？")) {
            $.ajax({
                url: '${basePath}notice/saveCancelRoll?id=' + id,
                type: 'post',
                beforeSend: function () {
                },
                success: function (data) {
                    alert("设置成功");
                    window.location.reload();
                },
                error: function () {
                    alert('请求失败，请重试');
                },
                complete: function () {
                    <%--window.location.href="${basePath}notice/list"--%>
                }
            });
        }
    }
    ;

    $(function () {
        $('.select2able').select2({width: "160"});
        $(".select2able").change(function () {
            $("#form").submit();
        });

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?status=${status}&page=" + page
            }
        });
    });
</script>
</body>
</html>