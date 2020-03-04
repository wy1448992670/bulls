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
    <title>提现记录详情</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        strong.money {
            color: #007aff;
            font-size: 18px;
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
                提现记录详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form">

                            <div class="form-group">
                                <label class="control-label col-md-2">提现者真实姓名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.trueName }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">提现金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.amount}
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">实际到帐金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.realAmount }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">提现方式</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.withdrawals}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">提现申请时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${draw.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">提现卡号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                    	<shiro:lacksPermission name="user:adminPhone">
									      ${draw.cardNo.replaceAll("(\\d{4})\\d{5}(\\d{4})","$1*****$2")}
									  </shiro:lacksPermission>
									  <shiro:hasPermission name="user:adminPhone">
									      ${draw.cardNo }
									  </shiro:hasPermission>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">操作人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.techOperateUserName }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">操作时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${draw.techOperateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">操作备注</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.techRemark }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">提现状态</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${draw.status == 0 }"><span
                                                class="label label-warning">待审核</span></c:if>
                                        <c:if test="${draw.status == 1 }"><span
                                                class="label label-success">提现成功</span></c:if>
                                        <c:if test="${draw.status == 2 }"><span
                                                class="label label-danger">提现失败</span></c:if>
                                        <c:if test="${draw.status == 3 }"><span
                                                class="label label-default">取消</span></c:if>
                                        <c:if test="${draw.status == 4 }"><span
                                                class="label label-info">银行处理中</span></c:if>
                                        <c:if test="${draw.status == 5 }"><span
                                                class="label label-inverse">挂起(状态未知）</span></c:if>
                                        <c:if test="${draw.status == 6 }"><span
                                                class="label label-danger">拒绝提现</span></c:if>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户资金明细表
                        <%--<a class="btn btn-sm btn-primary-outline pull-right"--%>
                           <%--href="${basePath}report/export/trade?userId=${draw.id}" id="export-trade"><i--%>
                                <%--class="icon-plus"></i>导出Excel</a>--%>
                    </div>

                    <div class="widget-content padded clearfix">
                        <jsp:include page="../common/tradeRecordFromDetail.jsp" flush="true">
                            <jsp:param name="userId" value="${draw.userId}"/>
                            <jsp:param name="businessId" value="${draw.id}"/>
                            <jsp:param name="tableName" value="withdraw"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${sessionScope.user.id==1}">
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>新浪反馈提现信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover" id="allCapitalDetail">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>订单号</th>
                                    <th>提现ID</th>
                                    <th>交易类型</th>
                                    <th>交易金额</th>
                                    <th>账户商户号</th>
                                    <th>支付ID</th>
                                    <th>付款人ID</th>
                                    <th>收款人ID</th>
                                    <th>收款人本系统中ID</th>
                                    <th>付款人身份类型</th>
                                    <th>收款人身份类型</th>
                                    <th>收款人账户类型</th>
                                    <th>支付方式</th>
                                    <th>描述</th>
                                    <th>订单创建时间</th>
                                    <th>订单完成时间</th>
                                    <th>订单完成返回信息</th>
                                    <th>订单完成返回状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="x" varStatus="st">
                                    <tr>
                                        <td>${st.count }</td>
                                        <td>${x.tradeNo}</td>
                                        <td>${x.withdrawId}</td>
                                        <td>
                                            <c:if test="${x.tradeType==0}">
                                                代收交易
                                            </c:if>
                                            <c:if test="${x.tradeType==1}">
                                                代付交易
                                            </c:if>
                                        </td>
                                        <td><fmt:formatNumber value="${x.amount}"></fmt:formatNumber></td>
                                        <td>${x.partnerId}</td>
                                        <td>${x.payerId}</td>
                                        <td>${x.payerUserId}</td>
                                        <td>${x.payeeIdentityId}</td>
                                        <td>${x.payeeUserId}</td>
                                        <td>${x.payerIdentityType}</td>
                                        <td>${x.payeeIdentityType}</td>
                                        <td>${x.accountType}</td>
                                        <td>${x.payMethod}</td>
                                        <td>${x.summary}</td>
                                        <td>${x.createTime}</td>
                                        <td>${x.completeTime}</td>
                                        <td>${x.completeTimeMsg}</td>
                                        <td>
                                                ${x.tradeStatus}
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "100"});
        $("#investment-list a:eq(0)").addClass("current");
        // $("#investment-list a:eq(3)").addClass("current");
        // showAllAssets(1);
    });


    function showAllAssets(page) {
        $.ajax({
            url: "${basePath}withdraw/allAssets",
            type: "get",
            dataType: "json",
            data: "page=" + page,
            success: function (obj) {
                $("#allAssets tbody").empty();
                for (var i = 0; i < obj.list.length; i++) {
                    var assets = obj.list[i];
                    var tr = "<tr><td>" + (i + 1) + "</td>"
                        + "<td>" + assets.username + "</td>"
                        + "<td>" + (assets.true_name == undefined ? "未认证" : assets.true_name) + "</td>"
                        + "<td>" + (assets.rechargeAmount == undefined ? 0 : assets.rechargeAmount) + "</td>"
                        + "<td>" + assets.total_investment_amount + "</td>"
                        + "<td>0</td>"
                        + "<td>" + assets.frozen_amount + "</td>"
                        + "<td>" + assets.total_income + "</td>"
                        + "<td>" + (assets.capitalAmount == undefined ? 0 : assets.capitalAmount) + "</td>"
                        + "<td>" + assets.uncollect_interest + "</td>"
                        + "<td>0</td>"
                        + "<td>" + (assets.withdrawAmount == undefined ? 0 : assets.withdrawAmount) + "</td>"
                        + "<td>" + ((assets.available_balance + assets.uncollect_capital + assets.uncollect_interest + assets.frozen_amount).toFixed(2)) + "</td>"
                        + "<td>" + assets.available_balance + "</td></tr>";
                    $("#allAssets tbody").append(tr);
                }
                if (obj.pages > 0) {
                    $('#allAssets-pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showAllAssets(" + page + ")";
                        }
                    });
                }
            }
        });
    }
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(),    //day
            "h+": this.getHours(),   //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }
</script>
</body>
</html>
