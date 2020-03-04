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
    <title>投资详情</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                投资详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>【${investUser.trueName}】投资详情
                    </div>
                    <div class="widget-content padded clearfix">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>到期日</label>

                                        <p class="form-control-static">
                                            <c:if test="${list[list.size-1].date==null}">——</c:if><c:if test="${list[list.size-1].date!=null}"><fmt:formatDate value="${list[list.size-1].date}" pattern="yyyy-MM-dd"></fmt:formatDate></c:if>
                                                                                    </p>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>投资金额（元）</label>

                                        <p class="form-control-static">
                                            <fmt:formatNumber value="${investment.amount}" maxFractionDigits="2"></fmt:formatNumber>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>转让金额（元）</label>

                                        <p class="form-control-static">
                                            <fmt:formatNumber value="${transferAmount}" maxFractionDigits="2"></fmt:formatNumber>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>预期总收益（元）</label>

                                        <p class="form-control-static">
                                            <fmt:formatNumber value="${sum+cashHb+investHb}" maxFractionDigits="2"></fmt:formatNumber>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-6">
                                <h3>投资信息</h3>

                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">下期回款日</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <c:if test="${nextDate==null}">——</c:if><c:if test="${nextDate!=null}"><fmt:formatDate value="${nextDate}" pattern="yyyy-MM-dd"></fmt:formatDate></c:if>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">投资时间</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatDate value="${investment.time}" pattern="yyyy-MM-dd"></fmt:formatDate>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">现金红包</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${cashHb}" maxFractionDigits="2"></fmt:formatNumber>元
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">投资红包</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${investHb}" maxFractionDigits="2"></fmt:formatNumber>元
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">预期收益</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${sum}" maxFractionDigits="2"></fmt:formatNumber>元
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">实际支付</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${realAmount}" maxFractionDigits="2"></fmt:formatNumber>元
                                            </p>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="col-sm-6">
                                <h3>项目概览</h3>

                                <form class="form-horizontal">
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">项目状态</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <c:if test="${project.projectType == 0 }">
                                                    <c:if test="${project.status == 0 }"><span
                                                            class="label label-default">创建</span></c:if>
                                                    <c:if test="${project.status == 1 }"><span
                                                            class="label label-warning">预购中</span></c:if>
                                                    <c:if test="${project.status == 2 }"><span class="label label-info">投资中</span></c:if>
                                                    <c:if test="${project.status == 3 }"><span
                                                            class="label label-success">投资完成</span></c:if>
                                                    <c:if test="${project.status == 4 }"><span
                                                            class="label label-primary">还款中</span></c:if>
                                                    <c:if test="${project.status == 5 }"><span
                                                            class="label label-success">还款成功</span></c:if>
                                                    <c:if test="${project.status == 6 }"><span
                                                            class="label label-danger">还款失败</span></c:if>
                                                </c:if>
                                                <c:if test="${project.projectType ==3 }">
                                                    <c:if test="${project.status == 0 }"><span class="label label-info">投资中</span></c:if>
                                                    <c:if test="${project.status == 1 }"><span
                                                            class="label label-success">投资完成</span></c:if>
                                                </c:if>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">项目规模</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${project.totalAmount>=10000?project.totalAmount/10000:project.totalAmount }" maxFractionDigits="2"/>
                                                <c:if test="${project.totalAmount>=10000 }">万</c:if>元
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">项目期限</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                ${project.limitDays}天
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">年化收益</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${project.annualized }" type="percent" maxFractionDigits="3" groupingUsed="false"/>
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">计息方式</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                按日计息
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">还款方式</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                按月付息，到期还本
                                            </p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-12">
                                <h3>回款计划</h3>

                                <div class="col-sm-12">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr>
                                            <th>日期</th>
                                            <th>本金（元）</th>
                                            <th>收益（元）</th>
                                            <th>状态</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${list}" var="i">
                                            <tr>
                                                <td class="jr-break"><c:if test="${i.date!=null}"><fmt:formatDate value="${i.date}" pattern="yyyy-MM-dd"></fmt:formatDate></c:if>
                                                    <c:if test="${i.date==null}">${i.stageString}</c:if>
                                                </td>
                                                <td class="jr-break"><fmt:formatNumber value="${i.capitalAmount}" maxFractionDigits="2"></fmt:formatNumber></td>
                                                <td class="jr-break"><fmt:formatNumber value="${i.interestAmount}" maxFractionDigits="2"></fmt:formatNumber></td>
                                                <td class="jr-break">
                                                    <c:if test="${i.hasDividended == 0}">
                                                        未还款
                                                    </c:if>
                                                    <c:if test="${i.hasDividended == 1}">
                                                        已还款
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
            </div>

            <%--<div class="col-lg-6">--%>
            <%--<div class="widget-container fluid-height clearfix">--%>
            <%--<div class="heading">--%>
            <%--<i class="icon-table"></i>加息券--%>
            <%--</div>--%>
            <%--<div class="widget-content padded clearfix">--%>
            <%--<div class="row">--%>
            <%--<div class="col-sm-12">--%>
            <%--<c:set var="allowedApply" value="false"/>--%>
            <%--<c:set var="allowedAudit" value="false"/>--%>
            <%--<shiro:hasPermission name="coupon:apply">--%>
            <%--<c:set var="allowedApply" value="true"/>--%>
            <%--<c:if test="${empty investment.couponId && empty audit}">--%>
            <%--<form class="form-horizontal" action="${basePath}coupon/otherAdd?type=0" method="post">--%>
            <%--<input type="hidden" name="investmentId" value="${investment.id}"/>--%>
            <%--<input type="hidden" name="days" value="${project.limitDays}"/>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">加息券利率</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<input type="text" class="form-control" name="rate" placeholder="请输入加息利率 如输入8代表 8%">--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">备注信息</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<textarea rows="3" class="form-control" placeholder="请输入额外备注信息" name="applyCustomerRemark"></textarea>--%>
            <%--</div>--%>
            <%--</div>--%>


            <%--<div class="form-group">--%>
            <%--<div class="col-sm-offset-2 col-sm-9">--%>
            <%--<input type="submit" class="btn btn-primary" value="提交申请">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</form>--%>
            <%--</c:if>--%>
            <%--</shiro:hasPermission>--%>

            <%--<shiro:hasPermission name="coupon:audit">--%>
            <%--<c:set var="allowedAudit" value="true"/>--%>
            <%--<c:if test="${empty investment.couponId && !empty audit && audit.status==0}">--%>
            <%--<form class="form-horizontal" action="${basePath}coupon/otherAdd?type=1" method="post">--%>
            <%--<input type="hidden" value="${audit.id}" name="id"/>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">加息券利率</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--<fmt:formatNumber value="${coupon.rate }" type="percent" maxFractionDigits="3" groupingUsed="false"/>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">申请人</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--${audit.applyCustomerName }--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">申请时间</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--<fmt:formatDate value="${audit.applyTime }" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">申请备注</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<textarea rows="3" class="form-control" placeholder="请输入额外备注信息" name="applyCustomerRemark" readonly>--%>
            <%--${audit.applyCustomerRemark}--%>
            <%--</textarea>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">审核备注</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<textarea rows="3" class="form-control" placeholder="请输入审核备注信息" name="auditRemark"></textarea>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<div class="col-sm-offset-2 col-sm-9">--%>
            <%--<input type="submit" class="btn btn-primary" value="审核通过">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</form>--%>
            <%--</c:if>--%>
            <%--</shiro:hasPermission>--%>

            <%--<!--无相关权限-->--%>
            <%--<c:if test="${(!allowedApply && !allowedAudit) || (allowedApply && !allowedAudit && !empty audit) || (!allowedApply && allowedAudit && !empty investment.couponId) || (allowedApply && allowedAudit && !empty investment.couponId)}">--%>
            <%--<form class="form-horizontal">--%>
            <%--<c:if test="${!empty coupon}">--%>
            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">加息券利率</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--<fmt:formatNumber value="${coupon.rate }" type="percent" maxFractionDigits="3" groupingUsed="false"/>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">使用时间</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--<fmt:formatDate value="${coupon.useTime }" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</c:if>--%>

            <%--<c:if test="${!empty audit}">--%>
            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">申请人</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--${audit.applyCustomerName }--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">申请时间</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--<fmt:formatDate value="${audit.applyTime }" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">申请备注</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--${audit.applyCustomerRemark }--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">审核人</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--${audit.auditName }--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">审核时间</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--<fmt:formatDate value="${audit.auditOverTime }" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">审核备注</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--${audit.auditRemark }--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
            <%--<label class="col-sm-2  control-label">审核状态</label>--%>

            <%--<div class="col-sm-9">--%>
            <%--<p class="form-control-static">--%>
            <%--<c:if test="${audit.status == 0}">--%>
            <%--<span class="label label-warning">审核中</span>--%>
            <%--</c:if>--%>
            <%--<c:if test="${audit.status == 1}">--%>
            <%--<span class="label label-success">审核通过</span>--%>
            <%--</c:if>--%>
            <%--<c:if test="${audit.status == 2}">--%>
            <%--<span class="label label-danger">审核拒绝</span>--%>
            <%--</c:if>--%>
            <%--</p>--%>
            <%--</div>--%>
            <%--</div>--%>

            <%--</c:if>--%>
            <%--</form>--%>
            <%--</c:if>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>

        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();
    });
</script>
</body>
</html>