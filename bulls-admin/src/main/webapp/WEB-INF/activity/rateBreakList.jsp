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
    <title>圣诞抽奖活动列表</title>
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
                活动管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>新年加息活动详情
                    </div>
                    <div class="widget-content padded clearfix">
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="url()"> 查询个人领取特权
                        </button>
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="formCount" action="selectRateBreak"
                              method="get">
                            <div class="form-group col-md-6 pull-right">
                                <input class="form-control keyword" id="keyword" name="keyword" type="text"
                                       placeholder="请输入用户电话或姓名" value="${keyword }"/>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>姓名</th>
                                <th>电话号码</th>
                                <th>再投金额</th>
                                <th>已领取的特权</th>
                                <th>领取时间</th>
                                <th>使用状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.count}</td>
                                    <td>${i.true_name}</td>
                                    <td>
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>${i.huo_investment_amount }</td>
                                    <td>${i.pai }</td>
                                    <td>
                                        <fmt:formatDate value="${i.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>${i.tai }</td>
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
    function url() {
        $("#formCount").submit();
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
                return "rateBreakList?status=${status}&date=<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>&orderId=${orderId}&account=${account}&giftId=${giftId}&page=" + page;
            }
        });
    });
</script>
</body>
</html>