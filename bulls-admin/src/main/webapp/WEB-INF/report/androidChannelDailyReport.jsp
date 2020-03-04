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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>安卓渠道引流详情统计</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
        }
    </style>
    <link rel="stylesheet" href="${basePath}css/style.css">

</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>报表统计</h1>
        </div>
        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        安卓渠道引流详情统计
                        <!-- <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索 </button> -->
                    </div>

                    <div class="widget-content padded clearfix">
                        <%-- <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="list">
                               <input type="hidden" name="page" value="1" />
                               <div class="row">
                                   <div class="form-group col-md-6 col-md-offset-6">
                                       <div>
                                           <input class="form-control keyword" name="keyword" type="text" placeholder="请输入用户名称或者手机号搜索" value="${keyword }"/>
                                       </div>
                                   </div>

                               </div>
                               <div class="row">
                                   <div class="form-group col-md-6">
                                          <input class="form-control datepicker"  value = "${startTime }" name="startTime" type="text" placeholder="请选择起始时间" />
                                   </div>
                                   <div class="form-group col-md-6">
                                       <input class="form-control datepicker"  value="${endTime }" name="endTime" type="text" placeholder="请选择结束时间"/>
                                   </div>
                               </div>
                               <input type="hidden" name="page" value="${page }" />
                           </form> --%>
                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>机构名称</th>
                                <th>引流源代码</th>
                                <th>每日引流数量</th>
                                <th>统计日期</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${detailList }" var="i" varStatus="st">
                                <tr>
                                    <td>${i.trueName}</td>
                                    <td>${i.code}</td>
                                    <td>${i.count }</td>
                                    <td>${i.time }</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
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
                return "adChannelDailyReport?code=${code}&page=" + page;
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