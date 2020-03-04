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
    <title>用户投资列表</title>
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
                用户投资管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>投资详情列表
                    </div>
                    <div class="widget-content padded clearfix">

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>
                                    资产包名称
                                </th>
                                <th>
                                    项目
                                </th>
                                <th>
                                    类型
                                </th>
                                <th>
                                    年化
                                </th>
                                <th>
                                    期限
                                </th>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    金额
                                </th>

                                <th>
                                    时间
                                </th>

                                <th>
                                    投资合同
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${i.id}</td>
                                    <td>${i.title3}</td>
                                    <td>
                                        <c:if test="${i.type != 1 }">
                                            <a href="${basePath}project/detail?id=${i.project_id}">
                                                <c:choose>
                                                    <c:when test="${empty i.title }">${i.title2}</c:when>
                                                    <c:otherwise>${i.title}</c:otherwise>
                                                </c:choose>
                                            </a>
                                        </c:if>
                                        <c:if test="${i.type == 1 }">
                                         <a href="#">
                                             <c:choose>
                                                 <c:when test="${empty i.title }">${i.title2}</c:when>
                                                 <c:otherwise>${i.title}</c:otherwise>
                                             </c:choose>
                                         </a>
                                        </c:if>
                                        <%--<a href="${basePath}project/detail?id=${i.project_id}">--%>
                                            <%--<c:choose>--%>
                                                <%--<c:when test="${empty i.title }">${i.title2}</c:when>--%>
                                                <%--<c:otherwise>${i.title}</c:otherwise>--%>
                                            <%--</c:choose>--%>
                                        <%--</a>--%>
                                    </td>
                                    <td>
                                        <c:if test="${i.project_type == 5 }"><span class="label label-success">新标</span></c:if>
                                        <c:if test="${i.project_type == 6 }"><span class="label label-success">周期债转</span></c:if>
                                            <%--<c:choose>--%>
                                            <%--<c:when test="${empty i.title }"><span--%>
                                            <%--class="label label-warning">债权</span></c:when>--%>
                                            <%--<c:otherwise><span--%>
                                            <%--class="label label-success">散标</span></c:otherwise>--%>
                                            <%--</c:choose>--%>
                                    </td>
                                    <td>
                                        <c:if test="${i.project_type == 5 }"> ${i.annualized }</c:if>
                                        <c:if test="${i.project_type == 6 }"> ${i.annualized1 }</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.project_type == 5 }"> ${i.limit_days }</c:if>
                                        <c:if test="${i.project_type == 6 }"> ${i.limit_days1 }</c:if>天
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.username }</a>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.trueName }</a>
                                    </td>
                                    <td>
                                            ${i.amount}元
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                        <td>
                                            <a target="_blank" href="${basePath}investment/contractNew?investmentId=${i.id}&userId=${i.user_id}">查看合同</a>
                                        </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <%--<B>投资总额:</B>${totalAmount }--%>
                        <%--<B>投资人数:</B>${sum }--%>
                        <ul id="pagination" style=" float: right">
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
                    return "list?keyword=${keyword}&seq=${seq}&type=${type}&endTime=${endTime}&investType=${investType}&source=${source}&startTime=${startTime}&page=" + page;
                }
            });
        });

        function isUserable() {
            var flag = $('select[name="type"]').val();
            if (flag == 0 || flag == 4) {
                $('select[name="investType"]').attr("disabled", false);
            } else {
                $('select[name="investType"]').attr("disabled", true);
            }
        }
        isUserable();

    </script>
</body>
</html>