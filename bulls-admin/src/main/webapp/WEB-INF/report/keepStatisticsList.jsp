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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>留存统计</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
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
                留存统计
            </h1>
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
                	<shiro:hasPermission name="user:adminPhone">
                    <div class="heading">
                        留存详情
                        <p class="pull-right">
                            总计:充值<label class="label label-success"><fmt:formatNumber value="${sumRecharge}"
                                                                                      pattern="###,###.##"
                                                                                      type="number"/></label>元，
                            提现<label class="label label-warning"><fmt:formatNumber value="${sumWithdraw}"
                                                                                   pattern="###,###.##"
                                                                                   type="number"/></label>元，
                            留存金额<label class="label label-success"><fmt:formatNumber value="${sumAmount}"
                                                                                     pattern="###,###.##"
                                                                                     type="number"/></label>元，
                            <c:if test="${sumRecharge==0}">
                                总留存率<label class="label label-info"><fmt:formatNumber value="100" pattern="#.##"
                                                                                      type="number"/></label>%
                            </c:if>
                            <c:if test="${sumRecharge!=0}">
                                总留存率<label class="label label-info"><fmt:formatNumber
                                    value="${(sumAmount/sumRecharge)*100 }" pattern="#.##" type="number"/></label>%
                            </c:if>
                            &nbsp;&nbsp;
                            <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="export-allCapitalDetail"
                               href="${basePath}report/keepStatisticsExcel?month=<fmt:formatDate value="${month }" pattern="yyyy-MM-dd"/>&year=<fmt:formatDate value="${year }" pattern="yyyy-MM-dd"/>"><i
                                    class="icon-plus"></i>导出Excel</a>
                        </p>
                    </div>
                    </shiro:hasPermission>

                    <div class="widget-content padded clearfix">
                        <form class="form-inline col-lg-2 pull-right" id="form" action="keepStatistics">
                            <div class="form-group col-md-7">
                                <div>
                                    每日留存详情：<input class="form-control month" name="" type="text"
                                                  placeholder="请选择查询的月份"
                                                  value="<fmt:formatDate value="${month }" pattern="yyyy-MM"/>"
                                                  id="datepicker"/>
                                    <input type="hidden" name="month" id="month"  value="<fmt:formatDate value="${month }" pattern="yyyy-MM"/>">
                                </div>
                            </div>
                        </form>
                        <form class="form-inline col-lg-2 pull-right" id="forms" action="keepStatisticsYear">
                            <div class="form-group col-md-7">
                                <div>
                                    每月留存详情：<input class="form-control month" name="" type="text"
                                                  placeholder="请选择查询的年份"
                                                  value="<fmt:formatDate value="${year }" pattern="yyyy"/>"
                                                  id="datepickers"/>
                                    <input type="hidden" name="year" id="year"  value="<fmt:formatDate value="${year }" pattern="yyyy"/>">
                                </div>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>时间</th>
                                <th>充值</th>
                                <th>提现</th>
                                <th>留存</th>
                                <th>留存率</th>
                                <shiro:hasPermission name="user:adminPhone">
                                <th>房东提现</th>
                                </shiro:hasPermission>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="x" varStatus="st">
                                <tr>
                                    <td>${st.count }</td>
                                    <td>${x.timeTotal}</td>
                                    <td><fmt:formatNumber value="${x.rss}"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${x.wss}"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${x.total}"></fmt:formatNumber></td>
                                    <c:if test="${x.rss==0}">
                                        <td><fmt:formatNumber value="100" pattern="#.##" type="number"/>%</td>
                                    </c:if>
                                    <c:if test="${x.rss!=0}">
                                        <td>${(x.proportion) }
                                        </td>
                                    </c:if>
                                    <shiro:hasPermission name="user:adminPhone">
                                    <td>${x.fang}</td>
                                    </shiro:hasPermission>
                                </tr>
                            </c:forEach>
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
                    dateFormat: 'yy-mm-dd',
                    onClose: function (dateText, inst) {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).datepicker('setDate', new Date(year, month, 1));
                        var mon = parseInt(month) +1;
                        $("#month").val(year.toString(10) + "-" +mon.toString(10)+"-01") ;
                        flag = false;
                        $("#form").submit();
                    },
                    beforeShow : function (input) {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).datepicker('setDate', new Date(${y}, 0, 1));
//                        var mon = parseInt(month) +1;
//                        $("#month").val(year.toString(10) + "-" +mon.toString(10)+"-01") ;
//                        flag = false;
//                        $("#form").submit();
                    }
                });
        $("#datepickers").datepicker(
                {
                    changeMonth: false,
                    changeYear: true,
                    showButtonPanel: true,
                    dateFormat: 'yy',
                    onClose: function (dateText, inst) {
                        var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
                        var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
                        $(this).datepicker('setDate', new Date(year, 0, 1));
                        $("#year").val(year+'-01'+"-01") ;
                        flag = false;
                        $("#forms").submit();
                    }
                });
        $.datepicker.regional['zh-CN'] = {
            closeText: '确认',
//            prevText: '<上月',
//            nextText: '下月>',
            currentText: '今天',
            monthNamesShort: ['一', '二', '三', '四', '五', '六',
                '七', '八', '九', '十', '十一', '十二'],
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: true,
            yearSuffix: '年'
        };
        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
        $("#ui-datepicker-div .ui-datepicker-year").val(2011);

        $("#ui-datepicker-div .ui-datepicker-month").val(5);
    });

</script>
</body>
</html>