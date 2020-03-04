<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>体验金查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet"
          type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all"
          rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet"
          type="text/css"/>
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
            <h1>体验金查询</h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>体验金查询
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-6 pull-right" id="form"
                              action="expMoneyList">
                            <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()">搜索
                            </button>
                            <input type="hidden" name="page" value="1"/>

                            <div class="row">
                                <div class="form-group col-md-4">
                                    <input class="form-control keyword" name="keyword" type="text"
                                           placeholder="真实用户名或电话" value="${keyword}"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${startTime }" name="startTime"
                                               type="text" placeholder="请选择起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${endTime }" name="endTime"
                                               type="text" placeholder="请选择结束时间"/>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page}"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>用户ID</th>
                                <th>真实名称</th>
                                <th>手机号</th>
                                <th>体验金金额</th>
                                <th>获得时间</th>
                                <th>过期时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="x">
                                <tr>
                                    <td>${x.count}</td>
                                    <td>${i.id}</td>
                                    <td><a href="${basePath}user/detail/app?id=${i.id}">${i.true_name}</a></td>
                                    <td>
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td><fmt:formatNumber value="${i.amount}" pattern="###,###.##"
                                                          type="number"/></td>
                                    <td><fmt:formatDate
                                            value="${i.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td><fmt:formatDate
                                            value="${i.expire_time }" pattern="yyyy-MM-dd"/></td>
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
<script src="${basePath}js/bootstrap-datepicker.js"
        type="text/javascript"></script>
<script type="text/javascript"
        src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    function aa() {
        $("#form").submit();
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2able').select2({
            width: "150"
        });
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('#pagination')
                .bootstrapPaginator(
                        {
                            currentPage: parseInt('${page}'),
                            totalPages: parseInt('${pages}'),
                            bootstrapMajorVersion: 3,
                            alignment: "right",
                            pageUrl: function (type, page, current) {
                                return "expMoneyList?keyword=${keyword}&endTime=${endTime}&startTime=${startTime}&page="
                                        + page;
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