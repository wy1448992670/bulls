<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>

    <title>PV查询</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
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
        <div class="page-title">
            <h1>
                PV查询
            </h1>
        </div>

        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>PV统计查询
                        <shiro:hasPermission name="export:pv">
                            <a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                               href="${basePath}report/pv/export?channel=${channel}&url=${requestUrl}" id="add-row"><i class="icon-plus"></i>导出excel</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline col-lg-3 pull-right" id="form" action="${basePath}report/pv">
                            <div class="form-group col-md-3">
                                <div>
                                    <input class="form-control " name="channel" type="text" placeholder="请输入渠道名称" value="${channel }"/>
                                </div>
                            </div>
                            <div class="form-group col-md-6">
                                <div>
                                    <input class="form-control " name="url" type="text" placeholder="请输入请求地址" value="${requestUrl }"/>
                                </div>
                            </div>
                            <div class="form-group col-md-3">
                                <div>
                                    <input class="btn btn-primary" type="submit" value="搜索"/>
                                </div>
                            </div>
                        </form>

                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>渠道</th>
                                <th>用户名</th>
                                <th>
                                    真名
                                </th>
                                <th>ip地址</th>
                                <th>浏览器</th>
                                <th>浏览器版本</th>
                                <th>操作系统</th>
                                <th>来源</th>
                                <th>请求地址</th>
                                <th>请求时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="l" varStatus="st">
                                <tr>
                                    <td>${st.index+1}</td>
                                    <td>${l.channel}</td>
                                    <td>${l.username}</td>
                                    <td>${l.trueName}</td>
                                    <td>${l.ip}</td>
                                    <td>${l.browser}</td>
                                    <td>${l.browserVersion}</td>
                                    <td>${l.operateSystem}</td>
                                    <td>${l.referer}</td>
                                    <td>${l.requestUrl}</td>
                                    <td><fmt:formatDate value="${l.requestTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="text-right">
                            <ul id="pagination"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "pv?channel=${channel}&page=" + page;
            }
        });
    });
</script>
</body>
</html>