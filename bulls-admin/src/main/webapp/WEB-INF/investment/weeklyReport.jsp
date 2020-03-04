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
    <title>投资周报表</title>
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
                投资管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>投资报表
                        <p class="pull-right">
                            总计:注册用户<label class="label label-success"><fmt:formatNumber value="${indexMap.registerCount}"
                                                                                        pattern="###,###"
                                                                                        type="number"/></label>人，
                            实名注册<label class="label label-warning"><fmt:formatNumber value="${indexMap.cardCount}"
                                                                                     pattern="###,###"
                                                                                     type="number"/></label>人，
                            绑卡人数<label class="label label-success"><fmt:formatNumber value="${indexMap.bankCount}"
                                                                                     pattern="###,###"
                                                                                     type="number"/></label>人，
                            新增投资人数<label class="label label-success"><fmt:formatNumber value="${indexMap.investmentCount}"
                                                                                       pattern="###,###"
                                                                                       type="number"/></label>人
                        </p>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form method="get" action="weeklyReport" class="form-inline hidden-xs col-lg-5 pull-right" id="form">
                            <div class="pull-right">
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>" name="startDate"
                                           type="text" placeholder="请选择起始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>" name="endDate"
                                           type="text" placeholder="请选择结束时间"/>
                                </div>
                                <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                                   href="${basePath}report/export/weeklyReport?startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>"><i
                                        investTyp class="icon-plus"></i>导出Excel</a>

                                <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="export-allCapitalDetail"
                                   href="#" onclick="aa()"><i class="icon-plus"></i>查询</a>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th colspan="7" class="text-center">新用户投资</th>
                            </tr>
                            <tr>
                                <th>
                                    投资类型
                                </th>
                                <th>
                                    投资总额
                                </th>
                                <th>
                                    PC投资总额
                                </th>
                                <th>
                                    安卓投资总额
                                </th>
                                <th>
                                    IOS投资总额
                                </th>
                                <th>
                                    WAP投资总额
                                </th>
                                <th>
                                    项目天数
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${newList }" varStatus="s">
                                <c:if test="${i.limit_days != null }">

                                    <tr>
                                        <td>
                                                ${i.investmentType}
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmount}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmountPc}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmountAd}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmountIOS}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>

                                        </td>
                                        <td>
                                                ${i.daysProject}
                                        </td>
                                    </tr>


                                </c:if>

                            </c:forEach>



                            <c:forEach var="i" items="${newUserHuoTotalAmount }" varStatus="s">
                                <tr>
                                    <td>
                                        活期
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmount}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmountPc}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmountAd}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmountIOS}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                </tr>

                            </c:forEach>

                            </tbody>
                        </table>

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th colspan="7" class="text-center">新老用户投资</th>
                            </tr>
                            <tr>
                                <th>
                                    投资类型
                                </th>
                                <th>
                                    投资总额
                                </th>
                                <th>
                                    PC投资总额
                                </th>
                                <th>
                                    安卓投资总额
                                </th>
                                <th>
                                    IOS投资总额
                                </th>
                                <th>
                                    WAP投资总额
                                </th>
                                <th>
                                    项目天数
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="totalInvest"></c:set>
                            <c:set var="totalInvestPc"></c:set>
                            <c:set var="totalInvestAd"></c:set>
                            <c:set var="totalInvestIOS"></c:set>
                            <c:forEach var="i" items="${newoldList }" varStatus="s">
                                <%--<c:if test="${i.limit_days != null }">--%>
                                    <tr>
                                        <td>
                                                ${i.investmentType}
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmount}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmountPc}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmountAd}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.totalAmountIOS}" pattern="###,###.##" type="number"/>
                                        </td>
                                        <td>
                                        </td>
                                        <td>
                                                ${i.daysProject}
                                        </td>
                                    </tr>
                                    <c:set var="totalInvest" value="${totalInvest+ i.totalAmount}"></c:set>
                                    <c:set var="totalInvestPc" value="${totalInvestPc+ i.totalAmountPc}"></c:set>
                                    <c:set var="totalInvestAd" value="${totalInvestAd+ i.totalAmountAd}"></c:set>
                                    <c:set var="totalInvestIOS" value="${totalInvestIOS+ i.totalAmountIOS}"></c:set>
                                <%--</c:if>--%>
                            </c:forEach>
                            <tr>
                                <td>
                                    汇总：
                                </td>
                                <td>
                                    <fmt:formatNumber value="${totalInvest}" pattern="###,###.##" type="number"/>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${totalInvestPc}" pattern="###,###.##" type="number"/>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${totalInvestAd}" pattern="###,###.##" type="number"/>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${totalInvestIOS}" pattern="###,###.##" type="number"/>
                                </td>
                                <td>
                                </td>
                                <td>
                                </td>
                            </tr>
                            <c:forEach var="i" items="${oldUserHuoTotalAmount }" varStatus="s">
                                <tr>
                                    <td>
                                        活期
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmount}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmountPc}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmountAd}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.totalAmountIOS}" pattern="###,###.##" type="number"/>
                                    </td>
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                </tr>

                            </c:forEach>

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
                return "list?keyword=${keyword}&seq=${seq}&type=${type}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });
    });

    function isUserable() {
        var flag = $('select[name="type"]').val();
        if (flag == 0) {
            $('select[name="investType"]').attr("disabled", false);
        } else {
            $('select[name="investType"]').attr("disabled", true);
        }
    }
    isUserable();

</script>
</body>
</html>