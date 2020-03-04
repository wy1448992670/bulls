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
    <title>APP版本管理</title>
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
                        <i class="icon-table"></i>APP版本管理
                        <shiro:hasPermission name="version:versionAdd">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="versionAdd" id="add-row"><i class="icon-plus"></i>新增版本管理</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
	                    <div class="table-responsive">
	                        <table class="table table-bordered table-hover">
	                            <thead>
	                            <tr>
	                                <th width="50px;">
	                                    序号
	                                </th>
	                                <th>
	                                    版本更新标题
	                                </th>
	                                <th>
	                                    版本号
	                                </th>
	                                <th>
	                                    终端
	                                </th>
	                                <th>
	                                    操作
	                                </th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                            <c:forEach var="g" items="${list }" varStatus="s">
	                                <tr>
	                                    <td>
	                                            ${s.count}
	                                    </td>
	                                    <td>
	                                            ${g.title }
	                                    </td>
	                                    <td>
	                                            ${g.version }
	                                    </td>
	                                    <td>
	                                        	${g.client }
	                                    </td>
	                                    <th>
	                                    	<shiro:hasPermission name="version:versionAdd">
		                                    	<a class="edit-row" href="versionAdd?id=${g.id}&page=${page}">编辑</a>
		                                    </shiro:hasPermission>
	
		                                    <shiro:hasPermission name="version:versionDelete">
		                                    	<a class="edit-row" href="versionDelete?id=${g.id }" onClick="return confirm('确定要删除此版本号吗？');" >删除</a>
		                                    </shiro:hasPermission>
	                                    </th>
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
    $(function () {
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
        // $("#project-list a:eq(0)").addClass("current");
        $("#project-list a:eq(7)").addClass("current");
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "reminderList?page=" + page;
            }
        });

    });
</script>
</body>
</html>
