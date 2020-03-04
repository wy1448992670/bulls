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
    <title>债券列表</title>
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
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>债券列表
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="bond">


                            <div class="row">

                                <div class="form-group col-md-6">
                                    <div>
                                        <select class="select2able" name="status">
                                            <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
                                            <option value="0" <c:if test="${status == 0 }">selected</c:if>>转让中</option>
                                            <option value="1" <c:if test="${status == 1 }">selected</c:if>>转让完成</option>
                                        </select>
                                    </div>
                                </div>


                                <div class="form-group col-md-5">

                                    <div>
                                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()"> 搜索</button>
                                    </div>

                                </div>


                            </div>

                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>"
                                               id="startTime" name="startTime" type="text" placeholder="请选择转让开始起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
                                               id="endTime" name="endTime" type="text" placeholder="请选择转让结束结束时间"/>
                                    </div>
                                </div>
                            </div>


                            <input type="hidden" name="page" value="${page }"/>
                        </form>


                        <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    转让者
                                </th>
                                <th>
                                    转让金额
                                </th>
                                <th>
                                    实际转让金额
                                </th>
                                <th>
                                    项目名称
                                </th>
                                <th>
                                    年化收益
                                </th>
                                <th>
                                    转让截至时间
                                </th>
                                <th>
                                    投资期限/天
                                </th>
                                <th>
                                    总融资金额
                                </th>
                                <th>
                                    转让时间
                                </th>
                                <th>
                                    状态
                                </th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }">
                                <tr>
                                    <td>
                                            ${g.user.username }
                                    </td>
                                    <td>
                                            ${g.totalAmount }
                                    </td>
                                    <td>
                                            ${g.investedAmount }
                                    </td>
                                    <td>
                                            ${g.project.title }
                                    </td>
                                    <td>
                                            ${g.project.annualized }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.deadline }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${g.project.limitDays }
                                    </td>
                                    <td>
                                            ${g.project.totalAmount / 10000} 万
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 0 }"><span
                                                class="label label-default">转让中</span></c:if>
                                        <c:if test="${g.status == 1 }"><span
                                                class="label label-success">转让成功</span></c:if>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="${basePath}project/detail/bond?id=${g.id }">详细</a>
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
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "150"});
//        $(".select2able").change(function () {
//            $("#form").submit();
//        });
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });


        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "${basePath}project/list/bond?status=${status}&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });
    });

    function search() {
        $("#form").submit();
    }
</script>
</body>
</html>