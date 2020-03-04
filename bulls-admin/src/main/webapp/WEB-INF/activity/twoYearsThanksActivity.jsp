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
    <title>感恩2周年，加息券、体验金人人大狂欢！</title>
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
                活动列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>感恩2周年，加息券、体验金人人大狂欢！
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" method="get"
                              action="twoYearsThanksActivity">
                            <input type="hidden" name="activityId" value="${activityId}">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" id="keyword" name="keyword" type="text"
                                           placeholder="请输入用户电话,用户名，用户昵称" value="${keyword }"/>
                                </div>
                                <div class="form-group col-md-3">
                                    <shiro:hasPermission name="user:export:app">
                                        <a style="margin-left: 10px;"
                                           class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                           href="${basePath}report/twoYearsThanksActivityReport?activityId=${activityId}&keyword=${keyword}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>"
                                           id="add-row"><i class="icon-plus"></i>导出excel</a>
                                    </shiro:hasPermission>
                                </div>
                                <div class="form-group col-md-3">
                                    <button class="btn btn-primary pull-right hidden-xs" type="submit"
                                            style="margin-left: 15px;">
                                        查询
                                    </button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" id="startTime" name="startTime" type="text"
                                           placeholder="请选择投资开始时间"
                                           value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" id="endTime" name="endTime" type="text"
                                           placeholder="请选择投资结束时间"
                                           value="<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>"/>
                                </div>
                            </div>


                        </form>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>用户姓名</th>
                                <th>昵称</th>
                                <th>手机号码</th>
                                <th>注册时间</th>
                                <th>渠道</th>
                                <th>投资金额</th>
                                <th>体验金额</th>
                                <th>红包金额</th>
                                <th>加息利率</th>
                                <th>计息开始时间</th>
                                <th>计息结束时间</th>
                                <th>体验金收益</th>
                                <th>项目名称</th>
                                <th>项目期限</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.count}</td>
                                    <td>
                                            ${i.true_name}
                                    </td>
                                    <td>
                                            ${i.username }
                                    </td>
                                    <td>
                                            ${i.phone }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.register_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${i.code }
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.invAmount}" pattern="#" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.amount}" pattern="#" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.hbAmount}" pattern="#" type="number"/>
                                    </td>
                                    <td>
                                            ${i.rate}
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.begin_time }" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.end_time }" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td>
                                        <c:if test="${i.actual_income != null }">${i.actual_income }</c:if>
                                        <c:if test="${i.actual_income == null }">${i.income }</c:if>
                                    </td>

                                    <td>
                                            ${i.title }
                                    </td>
                                    <td>
                                            ${i.limit_days }
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
        $('.select2able').select2();
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
                return "twoYearsThanksActivity?activityId=${activityId}&keyword=${keyword}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });


    });
</script>
</body>
</html>