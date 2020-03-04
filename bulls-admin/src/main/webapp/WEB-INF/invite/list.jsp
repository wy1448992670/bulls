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
    <title>二维码邀请查询</title>
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
                报表统计
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>二维码邀请查询
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="forms" action="qrcodes">
                            <div class="row">
                                <div class="form-group col-md-8">
                                    <input class="form-control keyword" name="keywords" type="text"
                                           placeholder="姓名或者手机号查询" value="${keywords }"/>
                                </div>
                                <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aaa()">
                                    用户邀请者查询
                                </button>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="qrcode">
                            <div class="row">
                                <div class="form-group col-md-10">
                                    <input class="form-control keyword" name="keyword" type="text"
                                           placeholder="请输入用户名称或者手机号搜索" value="${keyword }"/>
                                </div>
                                <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索
                                </button>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    手机号
                                </th>
                                <th>
                                    分享次数
                                </th>
                                <th>
                                    邀请注册人数
                                </th>
                                <th>
                                    邀请投资人数
                                </th>
                                <th>
                                    奖励红包个数
                                </th>
                                <th width="90">
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }">
                                <tr>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.userId}">${i.username }</a>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.userId}">${i.trueName }</a>
                                    </td>
                                    <td>
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                            ${i.shareNum }
                                    </td>
                                    <td>
                                            ${i.count }
                                    </td>
                                    <td>
                                            ${i.investNum }
                                    </td>
                                    <td>
                                            ${i.hbNum }
                                    </td>
                                    <td>
                                        <a href="${basePath }invite/detail?userId=${i.userId}">查看详情</a>
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
    function aa() {
        $("#form").submit();
    }
    function aaa() {
        $("#forms").submit();
    }
    $(function () {
        $('.select2able').select2({width: "150"});
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
                return "qrcode?keyword=${keyword}&page=" + page;
            }
        });

    });

</script>
</body>
</html>