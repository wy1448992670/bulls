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
    <title>项目列表</title>
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
                        <i class="icon-table"></i>散标项目列表
                        <a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                           href="${basePath}report/export/zzproject?bondDayDiff=${bondDayDiff}&keyword=${keyword}&amount=${amount}&status=${status}&endTime=${endTime}&startTime=${startTime}"
                           id="add-row"><i class="icon-plus"></i>导出excel</a>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="zzlist">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <select class="select2able" name="amount">
                                        <option value="" <c:if test="${amount == null }">selected</c:if>>金额无限制</option>
                                        <option value="0-1000" <c:if test="${amount == '0-1000' }">selected</c:if>>0-1000元</option>
                                        <option value="1000-2000" <c:if test="${amount == '1000-2000' }">selected</c:if>>1000-2000元</option>
                                        <option value="2000-5000" <c:if test="${amount ==  '2000-5000'}">selected</c:if>>2000-5000元</option>
                                        <option value="5000-10000" <c:if test="${amount == '5000-10000' }">selected</c:if>>5000-10000元</option>
                                        <option value="10000-" <c:if test="${amount == '10000-' }">selected</c:if>>10000元以上</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control bondDayDiff" name="bondDayDiff" type="text" placeholder="剩余期限≤${amount}" value="${bondDayDiff }" id="bondDayDiff"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <select class="select2able" name="status">
                                        <option value="" <c:if test="${status == null }">selected</c:if>>所有项目状态 ${startTime} ${endTime}</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>转让中</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>已转出</option>
                                        <option value="2" <c:if test="${status == 2 }">selected</c:if>>已取消</option>
                                        <option value="3" <c:if test="${status == 3 }">selected</c:if>>已转让</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" name="keyword" type="text" placeholder="请输入用户姓名、昵称、id" value="${keyword }" id="keyword"/>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="${startTime}" name="startTime" id="startTime"
                                           type="text" placeholder="转让起始时间 ${startTime}"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="${endTime}" name="endTime" id="endTime"
                                           type="text" placeholder="转让结束时间"/>
                                </div>
                                <input type="hidden" name="page" value="${page }"/>
                            </div>
                        </form>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    用户ID
                                </th>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真名
                                </th>
                                <th width="12%">
                                    项目名称
                                </th>
                                <th width="10%">
                                    转让时间/已被取消时间
                                </th>
                                <th>
                                    转让金额
                                </th>
                                <th>
                                    成交金额
                                </th>
                                <th>
                                    利率
                                </th>
                                <th>
                                    剩余期限
                                </th>
                                <th>
                                    服务费率
                                </th>
                                <th>
                                    客户端类型
                                </th>
                                <th>
                                    状态
                                </th>
                                <th>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }">
                                <tr>
                                    <td>
                                            ${g.userId}
                                    </td>
                                    <td>
                                        <a href="../user/detail/app?id=${g.userId}"> ${g.userName}</a>
                                    </td>
                                        <%--<td>--%>
                                        <%--<fmt:formatNumber value="${g.increaseAnnualized }" type="percent" maxFractionDigits="5"--%>
                                        <%--groupingUsed="false"/>--%>
                                        <%--</td>--%>
                                        <%--<td>--%>
                                        <%--<fmt:formatDate value="${g.deadline }" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
                                        <%--</td>--%>
                                    <td>
                                        <a href="../user/detail/app?id=${g.userId}"> ${g.trueName}</a>
                                    </td>

                                    <td>
                                        <a href="detail?id=${g.id}">${g.title}</a>
                                    </td>

                                    <td>
                                        <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${g.totalAmount}元
                                    </td>
                                    <td>
                                            ${g.investedAmount}元
                                            <%--<fmt:formatNumber value="${g.investedAmount/g.totalAmount }"
                                                              maxFractionDigits="2" type="percent"/>--%>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.annualized }" type="percent" maxFractionDigits="3"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>
                                        <c:if test="${g.bondDayDiff >= 0 }">${g.bondDayDiff}</c:if>
                                        <c:if test="${g.bondDayDiff < 0 }">0</c:if>
                                            <%-- <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
                                    </td>
                                    <td>
                                            ${g.bondManagementRate }
                                    </td>
                                    <td>
                                        未知
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 0 }"><span class="label label-default">转让中</span></c:if>
                                        <c:if test="${g.status == 1 }"><span class="label label-warning">已转让</span></c:if>
                                        <c:if test="${g.status == 2 }"><span class="label label-info">已取消</span></c:if>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="../investment/detail?investmentId=${g.investmentId}" id="delete">详细</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div>转让总额：${map.totalAmount}元 &nbsp;&nbsp;成交总额：${map.investedAmount}元 &nbsp;&nbsp;转让人数：${map.countUser}人</div>
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
        $(".bondDayDiff").keyup(function (e) {
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
                return "zzlist?bondDayDiff=${bondDayDiff}&keyword=${keyword}&amount=${amount}&status=${status}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });

        $("#startTime").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });

        $("#endTime").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
    });
</script>
</body>
</html>