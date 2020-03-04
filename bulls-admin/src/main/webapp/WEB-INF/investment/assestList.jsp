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
    <title>用户资产表</title>
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
                用户资产表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户资产表
                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                           href="${basePath}report/export/assestList?status=${status}&startTime=${startTime}&endTime=${endTime}&type=${type}&source=${source}">
                            <i investTyp class="icon-plus"></i>导出Excel</a>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">

                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="assestList">
                            <div class="row">
                                <div class="form-group col-md-6">  </div>

                                <div class="form-group col-md-3">
                                    <select class="select2able" name="source">
                                        <option value="0" >投资金额</option>
                                    </select>
                                </div>

                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="type">
                                            <option value="" <c:if test="${type == null }">selected</c:if>>全部</option>
                                            <option value="1" <c:if test="${type == 1 }">selected</c:if>>仅活期</option>
                                            <option value="2" <c:if test="${type == 2 }">selected</c:if>>仅散标</option>
                                        </select>
                                    </div>
                                </div>


                            </div>

                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="${startTime }" name="startTime"
                                           type="text" placeholder="请选择投资起始时间"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="${endTime }" name="endTime"
                                           type="text" placeholder="请选择投资结束时间"/>
                                </div>

                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>排名</th>

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
                                    注册时间
                                </th>
                                <th>
                                    散标投资金额
                                </th>

                                <th>
                                    活期投资金额
                                </th>

                                <th>
                                    投资总金额
                                </th>

                            </tr>
                            </thead>
                            <tbody>
                            <%--<c:set var="orderNum" value="0"></c:set>--%>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <%--<c:set var="orderNum" value="${orderNum+1}"></c:set>--%>
                                <tr>
                                    <td>${i.rownum}</td>

                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.username }</a>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.true_name }</a>
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
                                        <fmt:formatDate value="${i.register_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.dingqi}" pattern="###,###.##" type="number"/>元
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.huoqi}" pattern="###,###.##" type="number"/>元
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.amount}" pattern="###,###.##" type="number"/>元
                                    </td>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <%--<B>投资总额:</B>${totalAmount }--%>
                        <%--<B>投资人数:</B>${sum }--%>
                        <ul id="pagination" style=" float: right">
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
                    return "assestList?type=${type}&endTime=${endTime}&source=${source}&startTime=${startTime}&page=" + page;
                }
            });
        });

        function isUserable() {
            var flag = $('select[name="type"]').val();
            if (flag == 0 || flag == 4) {
                $('select[name="investType"]').attr("disabled", false);
            } else {
                $('select[name="investType"]').attr("disabled", true);
            }
        }
        isUserable();

    </script>
</body>
</html>