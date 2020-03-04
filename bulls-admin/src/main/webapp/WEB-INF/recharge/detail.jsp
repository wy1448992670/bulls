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
    <title>充值详情</title>
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
                充值记录详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form">

                            <div class="form-group">
                                <label class="control-label col-md-2">真实姓名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${recharge.true_name }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">支付订单</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${recharge.order_no}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">支付金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${recharge.amount }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">支付时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${recharge.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">支付卡号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <shiro:lacksPermission name="user:adminPhone">
									      ${recharge.card_no.replaceAll("(\\d{4})\\d{5}(\\d{4})","$1*****$2")}
									  </shiro:lacksPermission>
									  <shiro:hasPermission name="user:adminPhone">
									      ${recharge.card_no }
									  </shiro:hasPermission>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">支付状态</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${recharge.status == 0 }"><span
                                                class="label label-success">成功</span></c:if>
                                        <c:if test="${recharge.status == 1 }"><span
                                                class="label label-warning">处理中</span></c:if>
                                        <c:if test="${recharge.status == 2 }"><span
                                                class="label label-danger">失败</span></c:if>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">备注信息</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${recharge.remark }
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
                        <%-- <a class="btn btn-sm btn-primary-outline pull-right"
                           href="${basePath}report/export/trade?userId=${draw.id}" id="export-trade"><i
                                class="icon-plus"></i>导出Excel</a> --%>
                    </div>
                    <div class="widget-content padded clearfix">
                        <jsp:include page="../common/tradeRecordFromDetail.jsp" flush="true">
                            <jsp:param name="userId" value="${recharge.user_id}"/>
                            <jsp:param name="businessId" value="${recharge.id}"/>
                            <jsp:param name="tableName" value="recharge"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${sessionScope.user.id ==1}">
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>新浪反馈提现信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <div class="table-resonsive">
                            <table class="table table-bordered table-hover" id="allCapitalDetail">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>订单号</th>
                                    <th>充值ID</th>
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
                                        <td>${x.outTradeNo}</td>
                                        <td>${x.rechargeId}</td>
                                        <td>
                                            <c:if test="${x.type==0}">
                                                代收交易
                                            </c:if>
                                            <c:if test="${x.type==1}">
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
                                        <td>
                                            <fmt:formatDate value="${x.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${x.completeTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>${x.responseMsg}</td>
                                        <td>
                                            <c:if test="${x.tradeStatus=='WAIT_PAY'}">
                                                等待付款(系统不会异步通知)
                                            </c:if>
                                            <c:if test="${x.tradeStatus=='PAY_FINISHED'}">
                                                已付款(系统会异步通知)
                                            </c:if>
                                            <c:if test="${x.tradeStatus=='TRADE_FAILED'}">
                                                交易失败(系统会异步通知)
                                            </c:if>
                                            <c:if test="${x.tradeStatus=='TRADE_FINISHED'}">
                                                交易结束(系统会异步通知)
                                            </c:if>
                                            <c:if test="${x.tradeStatus=='TRADE_CLOSED'}">
                                                交易关闭（合作方通过调用交易取消接口来关闭）(系统会异步通知)
                                            </c:if>
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
        // showTradeDetail(1);
    });

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
