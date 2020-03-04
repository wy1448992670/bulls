<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>微信推广统计</title>
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
            <!-- end DataTables Example -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="widget-container fluid-height clearfix">

                        <div class="heading">
                            微信推广统计
                            <p class="pull-right">
                                总计:注册<label class="label label-success">${map.registCount }</label>人,绑卡<label
                                    class="label label-warning">${map.recCount }</label>人,
                                投资<label class="label label-info">${map.investCount }</label>人,
                                充值<label class="label label-success"><fmt:formatNumber value="${map.rechargeAmount }"
                                                                                       pattern="#.##"
                                                                                       type="number"/></label>元,
                                投资<label class="label label-warning"><fmt:formatNumber value="${map.investAmount }"
                                                                                       pattern="#.##"
                                                                                       type="number"/></label>元,
                                提现<label class="label label-info"><fmt:formatNumber value="${map.withdrawAmount }"
                                                                                    pattern="#.##" type="number"/></label>元
                            </p>
                        </div>
                        <div class="widget-content padded clearfix">
                            <form class="form-inline hidden-xs col-md-6 pull-right" id="form"  action="${basePath}report/wx">
                                <div class="row">
                                    <div class="form-group col-md-8">
                                    	<div>
	                                        <input class="form-control month" name="month" type="text"
                                           placeholder="请选择查询日期例如:2015-03"
                                           value="${month}"
                                           id="datepicker"/>
	                                    </div>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <div>
                                            <input name="codes" type="text" placeholder="请输入渠道号"   class="form-control keyword" value="${codes }"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-1">
                                        <div>
                                            <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()"> 搜索</button>
                                        </div>
                                    </div>

                                </div>
                            </form>
                            <table class="table table-bordered" id="allCapitalDetail">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>链接</th>
                                    <th>注册人数</th>
                                    <th>实名人数</th>
                                    <th>投资人数</th>
                                    <th>投资金额</th>
                                    <th>充值金额</th>
                                    <th>提现金额</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="l" varStatus="st">
                                    <tr>
                                        <td>${st.index+1}</td>
                                        <td>${l.code }</td>
                                        <td>${l.registCount }</td>
                                        <td>${l.recCount }</td>
                                        <td>${l.investCount }</td>
                                        <td><fmt:formatNumber value="${l.investAmount}" pattern="#.##" type="number"/></td>
                                        <td><fmt:formatNumber value="${l.rechargeAmount}" pattern="#.##"
                                                              type="number"/></td>
                                        <td><fmt:formatNumber value="${l.withdrawAmount}" pattern="#.##"
                                                              type="number"/></td>
                                        <td><a href="${basePath}report/wx/detail?code=${l.code}" class="btn btn-primary">查看详情</a>
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
        </div>

</div>

<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "wx?codes=${codes}&month=${month}&page=" + page;
            }
        });
    });
    function bb() {
        $("#form").submit();
    }
</script>

</body>
</html>