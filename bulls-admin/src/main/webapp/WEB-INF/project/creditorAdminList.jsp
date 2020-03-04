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
    <title>债权列表</title>
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
                债权列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>债权列表
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    序号
                                </th>
                                <th>
                                    债权名称
                                </th>
                                <th>
                                    预期年化
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    截止时间
                                </th>
                                <th>
                                    剩余收益天数
                                </th>
                                <th>
                                    债权总额
                                </th>
                                <th>
                                    投资金额
                                </th>
                                <th>
                                    真实投资金额
                                </th>
                                <th>
                                    进度
                                </th>
                                <th>
                                    状态
                                </th>
                                <th width="60">操作</th>
                                <th width="60">详细</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }" varStatus="x">
                                <tr>
                                    <td>
                                            ${x.count }
                                    </td>
                                    <td>
                                            ${g.title }
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.annualized }" type="percent" maxFractionDigits="3"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.deadline }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${g.dateDiff }
                                    </td>
                                    <td>
                                            ${g.totalAmount}元
                                    </td>
                                    <td>
                                            ${g.investedAmount }元
                                    </td>
                                    <td>
                                        <c:if test="${g.trueAmount==null }">
                                            0元 </c:if>
                                        <c:if test="${g.trueAmount!=null }">
                                            ${g.trueAmount }元</c:if>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.investedAmount/g.totalAmount }"
                                                          maxFractionDigits="2" type="percent"/>
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 0 }"><span
                                                class="label label-info">转让中</span></c:if>
                                        <c:if test="${g.status == 1 }"><span
                                                class="label label-warning">已转让</span></c:if>
                                        <c:if test="${g.status == 2 }"><span class="label label-default">已取消</span></c:if>
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 0 }">
                                            <a class="edit-row" href="invest?id=${g.id }">投资</a>
                                        </c:if>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="detail?id=${g.id }" id="delete">详细</a>
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
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "creditorList?keyword=${keyword}&status=${status}&page=" + page;
            }
        });
    });
</script>
</body>
</html>