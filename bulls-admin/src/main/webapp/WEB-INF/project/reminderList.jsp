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
    <title>温馨提示</title>
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
                温馨提示
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>温馨提示
                        <!-- <a class="btn btn-sm btn-primary-outline pull-right" href="add" id="add-row"><i class="icon-plus"></i>创建项目</a> -->
                        <shiro:hasPermission name="project:reminderAdd">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="reminderAdd" id="add-row"><i
                                    class="icon-plus"></i>新增温馨提示</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th width="50px;">
                                    序号
                                </th>
                                <th width="200px;">
                                    提示标题(所属页)
                                </th>
                                <th>
                                    提示条目
                                </th>
                                <th width="100px;">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }" varStatus="s">
                                <tr>
                                    <td>
                                            ${s.index+1}
                                    </td>
                                    <td>
                                            ${g.title }
                                    </td>
                                    <td>
                                            ${g.context }
                                    </td>
                                    <th><a class="edit-row" href="reminderEdit?id=${g.id}&page=${page}">编辑</a>&nbsp;<a
                                            class="edit-row" href="reminderCozyDelete?id=${g.id }">删除</a></th>
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
        $("#project-list a:eq(0)").addClass("current");
        $("#project-list a:eq(5)").addClass("current");
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