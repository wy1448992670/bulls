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
    <title>用户加息券查询</title>
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
                投资管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>加息券查询
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="list">
                            <input type="hidden" name="page" value="1"/>

                            <div class="row">
                                <div class="form-group col-md-4 ">
                                    <div>
                                        <select class="select2able" name="type">
                                            <option value="" <c:if test="${type == null }">selected</c:if>>选择类型</option>
                                            <option value="2" <c:if test="${type == 2 }">selected</c:if>>活期加息券</option>
                                            <option value="3" <c:if test="${type == 3 }">selected</c:if>>散标加息券</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-4 ">
                                    <div>
                                        <input class="form-control " name="days" type="text"
                                               placeholder="请输入加息券天数" value="${days }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-4">
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
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    ID
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    手机号
                                </th>
                                <th>
                                    利率
                                </th>
                                <th>
                                    类型
                                </th>
                                <th>
                                    加息券种类
                                </th>
                                <th>
                                    加息天数
                                </th>
                                <th>
                                    获得时间
                                </th>
                                <th>
                                    使用时间
                                </th>
                                <th>
                                    过期时间
                                </th>
                                <th>
                                    派发人
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }">
                                <tr>
                                    <td>
                                        ${i.id}
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.trueName }</a>
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
                                        <fmt:formatNumber value="${i.rate }" type="percent"
                                                          minFractionDigits="2"></fmt:formatNumber>
                                    </td>
                                    <td>
                                        <c:if test="${i.type == 0 }"><span class="label label-warning" style="font-size: 14px;">日加息券</span></c:if>
                                        <c:if test="${i.type == 1 }"><span class="label label-danger" style="font-size: 14px;">月加息券</span></c:if>
                                        <c:if test="${i.type == 2 }"><span class="label label-success" style="font-size: 14px;">活期加息券</span></c:if>
                                        <c:if test="${i.type == 3 }"><span class="label label-danger" style="font-size: 14px;">散标加息券</span></c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.rate_coupon_type == 'unlimited'}">无限制加息券</c:if>
                                        <c:if test="${i.rate_coupon_type == 'limited'}">有限制加息券</c:if>
                                    </td>
                                    <td>
                                            ${i.days }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.use_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.expire_time }" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td>
                                            ${i.adminName }
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

                        <B>总加息券数:</B>${count }
                        <B>使用人数:</B>${sum }


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
        <%--console.log("*************************");--%>
        <%--console.log(${count});--%>
        <%--console.log('${list}');--%>

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
                return "list?keyword=${keyword}&type=${type}&endTime=${endTime}&startTime=${startTime}&days=${days}&page=" + page;
            }
        });
    });

</script>
</body>
</html>
