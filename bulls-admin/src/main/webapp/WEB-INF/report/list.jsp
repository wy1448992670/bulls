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

    <title>鑫聚财月资金流水明细</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }
    </style>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="${basePath}css/style.css">

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

            <div class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <strong>提示：</strong>

                <p>1. 散标投资（包含新手标+VIP标+定期普通标+安鑫赚）--指直接账户余额投资</p>

                <p>2. 活转定（包含活期转散标投资散标标的+活期转散标投资债权+活期转安鑫赚），只要活转定，一律不区分散标和债权</p>

                <p>3. 回收本金（包含散标投资到期回收本金+债权回购回收本金+安鑫赚退出成功的金额）</p>
            </div>
            <c:if test="${!empty message }">
                <div class="alert alert-danger text-center" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only"></span>
                        ${message }
                </div>
            </c:if>
        </div>

        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>
                        鑫聚财资金明细总表
                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="export-allCapitalDetail"
                           href="${basePath}report/export/allCapitalDetailByMonth?month=${month }&source=${source}&type=${type}"><i
                                class="icon-plus"></i>导出Excel</a>
                    </div>

                    <div class="widget-content padded clearfix">
                        <form class="form-inline col-lg-3 pull-right" id="form" action="listCurrent">
                            <div class="form-group col-md-3"></div>
                            <div class="form-group col-md-9">
                                <div>
                                    <input class="form-control month" name="month" type="text" placeholder="请选择查询日期例如:2015-03" value="${month }" id="datepicker"/>
                                </div>
                            </div>
                        </form>

                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th rowspan="2">序号</th>
                                <th rowspan="2">时间</th>
                                <th colspan="3">
                                    投资
                                </th>
                                <th rowspan="2">充值</th>
                                <th rowspan="2">提现</th>
                                <th colspan="2">收益</th>
                                <th rowspan="2">回收本金</th>
                            </tr>
                            <tr>
                                <th>智投</th>
                                <th>散标</th>
                                <th>认购债权</th>
                                <th>智投收益</th>
                                <th>散标收益</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="capitalDetail" varStatus="st">
                                <tr>
                                    <td>${st.count }</td>
                                    <td><fmt:formatDate value="${capitalDetail.time}" pattern="d"/></td>
                                    <td><fmt:formatNumber value="${capitalDetail.yyyInvest }"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${capitalDetail.dingInvest }"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${capitalDetail.bondInvest }"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${capitalDetail.recharge }"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${capitalDetail.withdraw }"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${capitalDetail.yyyEarnings }"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${capitalDetail.dingEarnings }"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${capitalDetail.receive }"></fmt:formatNumber></td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td>总计:</td>
                                <td><fmt:formatDate value="${summarizing.time}" pattern="yyyy-MM"/></td>
                                <td colspan="3"><fmt:formatNumber value="${summarizing.investment }"></fmt:formatNumber></td>
                                <td><fmt:formatNumber value="${summarizing.recharge }"></fmt:formatNumber></td>
                                <td><fmt:formatNumber value="${summarizing.withdraw }"></fmt:formatNumber></td>
                                <td><fmt:formatNumber value="${totalYyyEarnings }"></fmt:formatNumber></td>
                                <td><fmt:formatNumber value="${totalDingEarnings }"></fmt:formatNumber></td>
                                <td><fmt:formatNumber value="${summarizing.receive }"></fmt:formatNumber></td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="text-right">
                            <ul id="allCapitalDetail-pagination"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $("#datepicker").datepicker(
                {
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    dateFormat: 'yy-mm',
                    onClose: function (dateText, inst) {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).datepicker('setDate', new Date(year, month, 1));
                        flag = false;
                        $("#form").submit();
                    }
                });
        $.datepicker.regional['zh-CN'] = {
            closeText: '确认',
            prevText: '<上月',
            nextText: '下月>',
            currentText: '今天',
            monthNamesShort: ['一', '二', '三', '四', '五', '六',
                '七', '八', '九', '十', '十一', '十二'],
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: true,
            yearSuffix: '年'
        };
        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
    });
    $('.select2able').select2({width: "150"});
    function zaiquan() {
        $("#form").submit();
    }
</script>
</body>
</html>