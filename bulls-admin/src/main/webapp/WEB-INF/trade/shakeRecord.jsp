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
    <title>摇一摇记录</title>
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
                摇一摇记录
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>摇一摇记录
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <%--<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="url()"> 查询摇一摇次数</button>--%>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()">搜索</button>
                        <%--<form class="form-inline hidden-xs col-lg-4 pull-right" id="formCount" action="shakeCount">
                            <div class="form-group col-md-6 pull-right">
                                <input class="form-control keyword" id="phone" name="phone" type="text"
                                       placeholder="请输入用户电话" value="${phone }"/>
                            </div>
                        </form>--%>
                    </div>
                    <div class="widget-content padded clearfix">
                        <div id="myModal">
                            <form class="search1 AppFixed form-inline col-lg-5 pull-right col-xs-11" id="form" action="shakeRecord">
                                <input type="hidden" name="page" value="1"/>

                                <div class="row">
                                    <div class="form-group col-md-6 ">
                                        <div>
                                            <select class="select2able" name="type">
                                                <option value="" <c:if test="${type == null }">selected</c:if>>选择类型</option>
                                                <c:forEach var="item" items="${recordTypes}">
                                                    <option value="${item.code}" <c:if test="${type == item.code }">selected</c:if>>${item.description}</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <div>
                                            <input class="form-control keyword" name="keyword" type="text"
                                                   placeholder="请输入用户名称或者手机号搜索" value="${keyword }"/>
                                        </div>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${startTime }" name="startTime"
                                               type="text" placeholder="请选择起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${endTime }" name="endTime"
                                               type="text" placeholder="请选择结束时间"/>
                                    </div>
                                </div>
                                <input type="hidden" name="page" value="${page }"/>
                                <button class="btn btn-primary pull-right hidden-md hidden-sm hidden-lg" type="button" onclick="aa()">搜索</button>
                                <button data-dismiss="modal" class="btn hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
                            </form>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                        ID
                                    </th>
                                    <th>
                                        真实姓名
                                    </th>
                                    <th>
                                        手机号码
                                    </th>
                                    <th>
                                        中奖类型
                                    </th>
                                    <th>中奖数额</th>
                                    <th>
                                        摇一摇时间
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${list }" varStatus="x">
                                    <tr>
                                        <td>
                                                ${i.id}
                                        </td>
                                        <td>
                                            <a href="${basePath}user/detail/app?id=${i.id}">${i.true_name }</a>
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
                                                <%--0没抽中1现金红包2活期加息券3积分4体验金5散标加息券6投资红包--%>
                                            <label class="label label-default"><c:if
                                                    test="${i.type == 0 }">没中奖</c:if></label>
                                            <label class="label label-success"><c:if
                                                    test="${i.type == 1 }">现金红包</c:if></label>
                                            <label class="label label-primary"><c:if
                                                    test="${i.type == 2 }">加息券</c:if></label>
                                            <label class="label label-info"><c:if
                                                    test="${i.type == 3 }">投资红包</c:if></label>
                                            <label class="label label-danger"><c:if
                                                    test="${i.type == 4 }">提现券</c:if></label>
                                                <%--<label class="label label-warning"><c:if
                                                        test="${i.type == 5 }">散标加息券</c:if></label>
                                                <label class="label label-success"><c:if
                                                        test="${i.type == 6 }">投资红包</c:if></label>--%>
                                        </td>
                                        <td>
                                                ${i.rate }
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
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
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    function aa() {
        $("#form").submit();
    }
    function url() {
        $("#formCount").submit();
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
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
                return "shakeRecord?keyword=${keyword}&type=${type}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });
    });

</script>
</body>
</html>
