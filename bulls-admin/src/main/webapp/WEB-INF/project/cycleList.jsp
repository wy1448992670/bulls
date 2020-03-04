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
    <title>周期标列表</title>
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
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">

                    <div class="heading">
                        <i class="icon-table"></i>周期标项目列表
                        <shiro:hasPermission name="project:add">
                            <a class="btn btn-sm btn-primary-outline pull-right" style="margin-left:10px;" href="cycleAdd?type=1" id="add-row1"><i
                                    class="icon-plus"></i>创建个人周期标项目</a>
                            <span style="width: 10px;">  </span>
                            <a class="btn btn-sm btn-primary-outline pull-right" style="margin-right:10px;"    href="cycleAdd?type=0" id="add-row"><i
                                    class="icon-plus"></i>创建周期标项目</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="cycleList">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control keyword" name="title1" type="text" placeholder="产品名称搜索" value="${title1 }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="status">
                                            <option value=""  <c:if test="${status == null }">selected</c:if>>项目状态</option>
                                            <option value="0" <c:if test="${status == 0 }">selected</c:if>>草稿</option>
                                            <option value="1" <c:if test="${status == 1 }">selected</c:if>>创建</option>
                                            <option value="2" <c:if test="${status == 2 }">selected</c:if>>投资中</option>
                                            <option value="3" <c:if test="${status == 3 }">selected</c:if>>投资完成</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                                </div>
                                <input type="hidden" name="page" value="${page }"/>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text" placeholder="项目名称搜索" value="${keyword }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control keyword" name="title" type="text" placeholder="资产包名称搜索" value="${title }"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>" name="startTime" id="startTime"
                                           type="text" placeholder="请选择创建起始时间"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>" name="endTime" id="endTime"
                                           type="text" placeholder="请选择创建结束时间"/>
                                </div>
                                </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>" name="startDate" id="startDate"
                                           type="text" placeholder="请选择到期日时间"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>" name="endDate" id="endDate"
                                           type="text" placeholder="请选择到期日时间"/>
                                </div>
                            </div>
                        </form>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th style="width: 50px;">
                                    序号
                                </th>
                                <th>
                                    名称
                                </th>
                                <th style="width: 50px;">
                                    年化
                                </th>
                                <%--<th style="width: 50px;">--%>
                                    <%--加息--%>
                                <%--</th>--%>
                                <th style="width: 100px;">
                                    所属资产包
                                </th>
                                <th style="width: 100px;">
                                    所属产品
                                </th>
                                <th style="width: 80px;">
                                    期限/天
                                </th>
                                <th style="width: 100px;">
                                    已融资金额(元)
                                </th>
                                <th style="width: 50px;">
                                    进度
                                </th>
                                <th style="width: 100px;">
                                    总额(元)
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    开始时间
                                </th>
                                <th>
                                    截止时间
                                </th>
                                <th>
                                    到期日
                                </th>
                                <th style="width: 80px;">
                                    状态
                                </th>
                                <th width="60">操作</th>
                                <th width="60">详情</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }" varStatus="i">
                                <tr>
                                    <td style="text-align: center;">
                                        ${i.count}
                                    </td>
                                    <td>
                                        <c:out value="${g.title }" escapeXml="false"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.annualized }" type="percent" maxFractionDigits="3"
                                                          groupingUsed="false"/>
                                    </td>
                                    <%--<td>--%>
                                        <%--<fmt:formatNumber value="${g.increaseAnnualized }" type="percent" maxFractionDigits="5"--%>
                                                          <%--groupingUsed="false"/>--%>
                                    <%--</td>--%>
                                    <td>
                                            ${g.projectPackage.title }
                                    </td>
                                    <td>
                                            ${g.product.name }
                                    </td>
                                    <td>
                                            ${g.limitDays }
                                    </td>
                                    <td>
                                                <fmt:formatNumber value="${g.investedAmount}"  pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.investedAmount/g.totalAmount }"
                                                          maxFractionDigits="2" type="percent"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.totalAmount}"  pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.deadline }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.repaymentTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>

                                        <c:if test="${g.projectType ==5 }">
                                            <c:if test="${g.status == 0 }"><span class="label label-warning">草稿</span></c:if>
                                            <c:if test="${g.status == 1 }"><span class="label label-success">创建</span></c:if>
                                            <c:if test="${g.status == 2 }"><span class="label label-primary">投资中</span></c:if>
                                            <c:if test="${g.status == 3 }"><span class="label label-info">投资完成</span></c:if>
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 0 }">
                                            <a class="edit-row" href="edit?id=${g.id }">编辑</a>
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
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
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
                return "cycleList?keyword=${keyword}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>&title=${title}&title1=${title1}&status=${status}&page=" + page;
            }
        });
    });
    function report() {
        var startTime = $.trim($('#startTime').val());
        var endTime = $.trim($('#endTime').val());
        window.location.href = "${basePath}report/paymentExcel?startTime=" + startTime + "&endTime=" + endTime;
    }
    function aa() {
        $("#form").submit();
    }
</script>
</body>
</html>