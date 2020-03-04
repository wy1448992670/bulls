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
    <title>实名认证申请列表</title>
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
                用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>实名认证申请列表
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    用户名
                                </th>
                                <th>
                                    真实姓名
                                </th>
                                <th>
                                    身份证号码
                                </th>
                                <th>
                                    申请时间
                                </th>
                                <th width="75"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="user" items="${list }">
                                <tr>
                                    <td>
                                            ${user.username }
                                    </td>
                                    <td>
                                            ${user.truename }
                                    </td>
                                    <td>
                                            ${user.card }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="">审核</a>
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
<script type="text/javascript">
    $(function () {
        $(".keyword").keyup(function (e) {
            if (e.which == 13) {
                $("#form").submit();
            }
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list/icard?page=" + page;
            }
        });
    });
</script>
</body>
</html>