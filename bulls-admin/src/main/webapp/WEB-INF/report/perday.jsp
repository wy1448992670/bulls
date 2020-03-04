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
    <title>每日报单列表</title>
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
                每日报单管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>每日报单列表
                        <!-- <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索 </button> -->
                    </div>
                    <div class="widget-content padded clearfix">
                        <%--                         	<form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="list">
                                                        <input type="hidden" name="page" value="1" />
                                                        <div class="row">
                                                            <div class="form-group col-md-6 col-md-offset-6">
                                                                <div>
                                                                    <input class="form-control keyword" name="keyword" type="text" placeholder="请输入用户名称或者手机号搜索" value="${keyword }"/>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </form> --%>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th width="80">
                                    序号
                                </th>
                                <th width="440">
                                    报单内容
                                </th>
                                <th width=60>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    1
                                </td>
                                <td>
                                    账户异常 ①最近一周内，连续两日及以上最高额（3W）提现 ②白日取出晚上再充值进账户投资 ③最近连续3天，每日提取收益 ④刚充值则提现的用户（充值提现时间间隔小于1天）
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                       href="${basePath}report/dailyReportExportAbnormalAccount"><i
                                            class="icon-plus"></i>导出Excel</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    2
                                </td>
                                <td>
                                    每日新增投资用户（指当日时间晚于用户注册日期）
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                       href="${basePath}report/tradeAdd"><i class="icon-plus"></i>导出Excel</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    3
                                </td>
                                <td>
                                    每日拉取当天生日用户名单
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                       href="${basePath}report/userBirthday"><i class="icon-plus"></i>导出Excel</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    4
                                </td>
                                <td>
                                    查询--账户有余额资金未投资（方便客服随时查询随时呼出）以上数据需每日拉取名单，方便客服呼出，而非现有情况单个发现个案问题查询核实呼出。
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                       href="${basePath}report/assetsInvestment"><i class="icon-plus"></i>导出Excel</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    5
                                </td>
                                <td>
                                    <a href="${basePath}report/investmentDetails">查询--每日用户投资，充值，提现明细</a>
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                       href="${basePath}report/export/investmentDetailsDay"><i class="icon-plus"></i>导出Excel</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    6
                                </td>
                                <td>
                                    定期回款到用户余额后，用户未对进行投资、提现的用户名单
                                </td>
                                <td>
                                    <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                       href="${basePath}report/export/noOperationAfterBackPay"><i class="icon-plus"></i>导出Excel</a>
                                </td>
                            </tr>
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
    function aa() {
        $("#form").submit();
    }
    ;
</script>
</body>
</html>
