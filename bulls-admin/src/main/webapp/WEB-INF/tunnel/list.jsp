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
    <title>通道列表</title>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                通道管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>通道列表
                        <a class="btn btn-sm btn-primary-outline pull-right" href="${basePath}tunnel/add"
                           id="add-row"><i class="icon-plus"></i>添加通道</a>
                    </div>
                    <div class="widget-content padded clearfix">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        ID
                                    </th>
                                    <th>
                                        通道名称
                                    </th>
                                    <th>
                                        通道类型
                                    </th>
                                    <th>
                                        权重
                                    </th>
                                    <th>
                                        状态
                                    </th>
                                    <th width="80"></th>
                                    <th width="80"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="b" items="${list }">
                                    <tr>
                                        <td>
                                                ${b.id }
                                        </td>
                                        <td>
                                                ${b.name }
                                        </td>
                                        <td>
                                            <c:if test="${b.type == 0 }"><span class="label label-success">充值</span></c:if>
                                            <c:if test="${b.type == 1 }"><span class="label label-warning">提现</span></c:if>
                                        </td>
                                        <td>
                                                ${b.weight }
                                        </td>
                                        <td>
                                            <c:if test="${b.status == 0 }"><span
                                                    class="label label-success">激活</span></c:if>
                                            <c:if test="${b.status == 1 }"><span class="label label-danger">屏蔽</span></c:if>
                                        </td>
                                        <td>
                                            <a class="delete-row" href="${basePath}tunnel/edit?id=${b.id }"
                                               id="update">编辑</a>
                                        </td>
                                        <td>
                                            <a class="delete-row" href="${basePath}tunnel/delete?id=${b.id }"
                                               onClick="return confirm('确定要删除此通道吗？');" id="delete">删除</a>
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
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
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
