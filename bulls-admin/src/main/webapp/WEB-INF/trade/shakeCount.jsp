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
    <title>摇一摇次数查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table {
            /*table-layout: fixed;*/
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
                摇一摇次数查询
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>摇一摇次数查询
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="url()"> 查询摇一摇次数</button>
                        <div id="myModal">
                            <form class="search1 AppFixed form-inline col-lg-5 pull-right col-xs-11" id="formCount" action="shakeCount">
                                <div class="form-group col-md-6 pull-right">
                                    <input class="form-control keyword" id="phone" name="phone" type="text"
                                           placeholder="请输入用户电话" value="${phone }"/>
                                    <button class="btn btn-primary pull-right hidden-md hidden-sm hidden-lg" type="button" onclick="url()"> 查询摇一摇次数</button>
                                    <button data-dismiss="modal" class="btn hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="widget-content padded clearfix">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        序号
                                    </th>
                                    <th>
                                        真实姓名
                                    </th>
                                    <th>
                                        手机号码
                                    </th>
                                    <th>
                                        每日次数
                                    </th>
                                    <th>额外赠送次数</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>
                                        1
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${map.id}">${map.true_name }</a>
                                    </td>
                                    <td>
                                        ${map.phone}
                                    </td>
                                    <td>
                                        ${map.todayCount}
                                    </td>
                                    <td>
                                        ${map.count}
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
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
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    function url() {
        $("#formCount").submit();
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
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
                return "shakeRecord?keyword=${keyword}&type=${type}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
    });

</script>
</body>
</html>
