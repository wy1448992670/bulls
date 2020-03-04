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
                        <i class="icon-table"></i>圣诞抽奖次数查询列表
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="url()"> 查询抽奖次数
                        </button>
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="formCount"
                              action="selectChrismasLotteryCount">
                            <div class="form-group col-md-6 pull-right">
                                <input class="form-control keyword" id="phone" name="phone" type="text"
                                       placeholder="请输入用户电话或真实姓名" value="${phone }"/>
                            </div>
                        </form>
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>用户ID</th>
                                <th>真实姓名</th>
                                <th>用户电话</th>
                                <th>系统赠送次数</th>
                                <th>增投获赠次数</th>
                                <th>增投已增次数</th>
                                <th>系统赠送时间</th>
                                <th>今日赠送状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>${map.id}</td>
                                <td>${map.true_name}</td>
                                <td>
                                    <shiro:lacksPermission name="user:adminPhone">
                                        ${map.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                    </shiro:lacksPermission>
                                    <shiro:hasPermission name="user:adminPhone">
                                        ${map.phone }
                                    </shiro:hasPermission>
                                </td>
                                <td>${map.sys_count }</td>
                                <td>${map.ex_count }</td>
                                <td>${map.temp }</td>
                                <td>
                                    <fmt:formatDate value="${map.sys_time}" pattern="yyyy-MM-dd"/>
                                </td>
                                <td>${map.type}</td>
                            </tr>
                            </tbody>
                        </table>
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
                return "chrismasList?status=${status}&date=<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>&orderId=${orderId}&account=${account}&giftId=${giftId}&page=" + page;
            }
        });
    });
</script>
</body>
</html>