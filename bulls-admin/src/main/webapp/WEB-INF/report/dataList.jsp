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
    <title>推广数据分析</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                推广数据分析
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>推广数据分析

                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="dataAnalysis">

                            <div class="row">
                                <div class="form-group col-md-10"></div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()"> 搜索</button>
                                    </div>
                                </div>
                            </div>



                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker"  value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>" name="startTime" id="startTime"
                                               type="text" placeholder="选择起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker"  value="<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>" name="endTime" id="endTime"
                                               type="text" placeholder="选择结束时间"/>
                                    </div>
                                </div>
                            <input type="hidden" id="page" name="page" value="${page }"/>
                        </form>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                   渠道代码
                                </th>
                                <th>
                                    投资用户人数
                                </th>
                                <th>
                                    复投人数
                                </th>
                                <th>
                                    投资金额（元）
                                </th>
                                <th>
                                    复投金额（元）
                                </th>
                                <th>
                                    复投率
                                </th>


                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }">
                                <tr>
                                    <td>
                                       ${g.code}
                                    </td>
                                    <td>
                                        <c:if test="${g.countOne  == null }" >
                                           0
                                        </c:if>
                                        <c:if test="${g.countOne  != null }" >
                                            ${g.countOne}
                                        </c:if>

                                    </td>

                                    <td>
                                        <c:if test="${g.countMore  == null  }" >
                                            0
                                        </c:if>
                                        <c:if test="${g.countMore  != null }" >
                                            ${g.countMore}
                                        </c:if>

                                    </td>

                                    <td>
                                        <c:if test="${g.sumAll  == null  }" >
                                            0
                                        </c:if>
                                        <c:if test="${g.sumAll  != null }" >
                                            <fmt:formatNumber value="${g.sumAll}" pattern="#.##" type="number"/>
                                        </c:if>



                                    </td>

                                    <td>
                                        <c:if test="${(g.sumAll-g.sumFirst) != 0 }" >
                                            <fmt:formatNumber value="${g.sumAll-g.sumFirst}" pattern="#.##" type="number"/>
                                        </c:if>

                                        <c:if test="${(g.sumAll-g.sumFirst) == null  or (g.sumAll-g.sumFirst) == 0 }" >
                                           0
                                        </c:if>


                                    </td>

                                    <td>
                                        <fmt:formatNumber
                                                value="${(g.countMore/g.countOne)*100 }" pattern="#.##" type="number"/>%
                                    </td>
                                   <%-- <td>
                                        <fmt:formatNumber value="${g.annualized }" type="percent" maxFractionDigits="3"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.increaseAnnualized }" type="percent" maxFractionDigits="5"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.deadline }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${g.limitDays }
                                    </td>
                                    <td>
                                            ${g.investedAmount }
                                    </td>
                                    <td>
                                        <c:if test="${g.trueAmount==null }">
                                            0元 </c:if>
                                        <c:if test="${g.trueAmount!=null }">
                                            ${g.trueAmount }元</c:if>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.investedAmount/g.totalAmount }"
                                                          maxFractionDigits="2" type="percent"/>
                                    </td>
                                    <td>
                                            ${g.totalAmount / 10000} 万
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 0 }"><span
                                                class="label label-default">创建</span></c:if>
                                        <c:if test="${g.status == 1 }"><span
                                                class="label label-warning">预购中</span></c:if>
                                        <c:if test="${g.status == 2 }"><span class="label label-info">投资中</span></c:if>
                                        <c:if test="${g.status == 3 }"><span
                                                class="label label-success">投资完成</span></c:if>
                                        <c:if test="${g.status == 4 }"><span
                                                class="label label-primary">还款中</span></c:if>
                                        <c:if test="${g.status == 5 }"><span
                                                class="label label-success">还款成功</span></c:if>
                                        <c:if test="${g.status == 6 }"><span
                                                class="label label-danger">还款失败</span></c:if>
                                    </td>
                                    <td>
                                        <c:if test="${g.status < 3 }">
                                            <a class="edit-row" href="edit?id=${g.id }">编辑</a>
                                        </c:if>
                                        <c:if test="${g.status == 2 }">
                                            <a class="edit-row" href="invest?id=${g.id }">投资</a>
                                        </c:if>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="detail?id=${g.id }" id="delete">详细</a>
                                    </td>--%>
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
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
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
                return "dataAnalysis?endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });
    });
    function report() {
        var startTime = $.trim($('#startTime').val());
        var endTime = $.trim($('#endTime').val());
        window.location.href = "${basePath}report/paymentExcel?startTime=" + startTime + "&endTime=" + endTime;
    }

    function search() {
        $("#form").submit();
    }
</script>
</body>
</html>