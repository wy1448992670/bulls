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
    <title>安卓渠道引流统计</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet"
          type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all"
          rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet"
          type="text/css"/>
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
            <h1>报表统计</h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>安卓渠道引流统计列表
                        <!-- <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索 </button> -->
                        <shiro:hasPermission name="report:export:wx">
                            满足权限显示激活统计
                        </shiro:hasPermission>
                    </div>


                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>机构名称</th>
                                <th>引流源代码</th>
                                <th>总引流数量</th>
                                <th width="220">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="countitem" items="${adChannelCountList }"
                                       varStatus="s">
                                <tr>
                                    <td>${countitem.trueName}</td>
                                    <td>${countitem.code}</td>
                                    <td>${countitem.count }</td>
                                    <td>
                                        <a href="${basePath}report/adChannelDailyReport?code=${countitem.code}"
                                           class="btn btn-primary">查看详情</a>
                                        <a href="${basePath}report/adChannelDailyReport"
                                           class="btn btn-primary">查看全部</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js"
        type="text/javascript"></script>
<script type="text/javascript"
        src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">

    function aa() {
        $("#form").submit();
    }
    ;

</script>
</body>
</html>