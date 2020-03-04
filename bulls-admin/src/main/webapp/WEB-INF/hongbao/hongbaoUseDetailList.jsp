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
    <title>红包使用详情列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
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
                红包详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>红包详情列表
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="hongbaoUseDetailList">
                            <div class="row">
                                <div class="form-group col-md-10">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text" placeholder="请输入用户(昵称，姓名，手机号码)" value="${keyword }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <input class="form-control keyword" name="redeemId" type="text" placeholder="兑换码ID" value="${redeemId }"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <select class="select2" name="isUse" id="isUse">
                                        <option value="">是否使用</option>
                                        <option value="0" <c:if test="${isUse == 0 }">selected</c:if>>未使用</option>
                                        <option value="1" <c:if test="${isUse == 1 }">selected</c:if>>已使用</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-4">
                                    <select class="select2" name="type" id="type">
                                        <option value="0" <c:if test="${type == 0 }">selected</c:if>>利息红包</option>
                                        <option value="1" <c:if test="${type == 1 }">selected</c:if>>现金红包</option>
                                        <option value="2" <c:if test="${type == 2 }">selected</c:if>>投资红包</option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime"
                                           type="text" placeholder="选择投资起始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime"
                                           type="text" placeholder="选择投资结束时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="export-allCapitalDetail"
                                       onclick="bb()">
                                        <i class="icon-plus"></i>导出Excel</a>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>红包类型(兑换码标题)</th>
                                <th>红包限制金额</th>
                                <th>红包发送时间</th>
                                <th>红包有效期限</th>
                                <th>红包使用时间</th>
                                <th>兑换码ID</th>
                                <th>兑换码</th>
                                <th>用户姓名</th>
                                <th>用户电话</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.count}</td>
                                    <td>${i.descript}</td>
                                    <td>${i.limit_amount}</td>
                                    <td>
                                        <fmt:formatDate value="${i.send_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.expire_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.use_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>${i.redeemId}</td>
                                    <td>${i.redeem_code}</td>
                                    <td>${i.true_name}</td>
                                    <td><a class="delete-row"
                                           href="${basePath}user/detail/app?id=${i.userId}">
                                           <shiro:lacksPermission name="user:adminPhone">
                                                ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                            </shiro:lacksPermission>
                                            <shiro:hasPermission name="user:adminPhone">
                                                ${i.phone }
                                            </shiro:hasPermission>
                                           </a></td>
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
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">

    function aa() {
        $("#form").submit();
    }
    function bb() {
        var start = $("#startTime").val();
        var end = $("#endTime").val();
        window.location.href = "${basePath}report/hongbaoUseExcel?startTime=" + start + "&endTime=" + end;
    }

    $(function () {
        $('.select2').select2();
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "hongbaoUseDetailList?keyword=${keyword}&page=" + page;
            }
        });
    });
</script>
</body>
</html>