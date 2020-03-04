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
    <title>月月盈投资详情</title>
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
                        <i class="icon-table"></i>【${trueName}】投资详情
                    </div>
                    <div class="widget-content padded clearfix">
                        <div class="row">
                            <div class="col-sm-12">
	                               <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>
                                        	   <c:if test="${projectStats == 0 }">可申请退出时间</c:if>
                                               <c:if test="${projectStats == 1 }">申请退出时间</c:if>
                                               <c:if test="${projectStats == 2 }">退出时间</c:if>
                                        </label>
                                        <p class="form-control-static">
                                         ${lastQuitDate}
                                        </p>
                                    </div>
                                   </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>投资金额（元）</label>
                                        <p class="form-control-static">
                                            <fmt:formatNumber value="${amount}" maxFractionDigits="2"></fmt:formatNumber>
                                        </p>
                                    </div>
                                </div>
                                <!-- 退出中和已退出显示 -->
                                <c:if test="${projectStats ne 0 }">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>转让金额（元）</label>
                                        <p class="form-control-static">
                                            <fmt:formatNumber value="${assignedAmount}" maxFractionDigits="2"></fmt:formatNumber>
                                        </p>
                                    </div>
                                </div>
                                </c:if>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label>
	                                        <c:if test="${projectStats == 0 }">预期收益（元）</c:if>
	                                        <c:if test="${projectStats == 1 }">已收到收益（元）</c:if>
	                                        <c:if test="${projectStats == 2 }">累计收益</c:if>
                                        </label>
                                        <p class="form-control-static">
                                           <fmt:formatNumber value="${invexpertInterest}" maxFractionDigits="2"/>
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
                                <label class="col-sm-3  control-label">投资状态</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <c:if test="${projectStats == 0 }"><span
                                                        class="label label-info">持有中</span></c:if>
                                                <c:if test="${projectStats == 1 }"><span
                                                        class="label label-default">退出中</span></c:if>
                                                <c:if test="${projectStats == 2 }"><span class="label label-warning">已退出</span></c:if>
                                            </p>
                                        </div>
                                        </div>
                                    
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">投资时间</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                               ${addTime}
                                            </p>
                                        </div>
                                    </div>
                                     <div class="form-group">
                                        <label class="col-sm-3  control-label">已持有天数</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${holdDays}" maxFractionDigits="2"></fmt:formatNumber>天
                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">历史参考利率</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                ${rate}%
                                            </p>
                                        </div>
                                    </div>
                                    <%-- <div class="form-group">
                                        <label class="col-sm-3  control-label">预期收益</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${invexpertInterest}" maxFractionDigits="2"></fmt:formatNumber>元
                                            </p>
                                        </div>
                                    </div> --%>
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">实际支付</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${amount}" maxFractionDigits="2"></fmt:formatNumber>元
                                            </p>
                                        </div>
                                    </div>
                                </form>
                            </div>

                            <div class="col-sm-6">
                                <h3>项目概览</h3>
                                <form class="form-horizontal">
                                <div class="form-group">
                                        <label class="col-sm-3  control-label">项目名称</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                              ${name}
                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">项目状态</label>
                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                    <c:if test="${packgeStats == 'mujizhong' }"><span class="label label-info">募集中</span></c:if>
                                                    <c:if test="${packgeStats == 'mujiwancheng' }"><span
                                                            class="label label-success">募集完成</span></c:if>
                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">项目规模</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                <fmt:formatNumber value="${totalAmount>=10000?totalAmount/10000:totalAmount }" maxFractionDigits="2"/>
                                                <c:if test="${totalAmount>=10000 }">万</c:if>元
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">授权服务期</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                 ${limitDays}天
                                            </p>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">年化收益</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                               ${rate}%
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">计息方式</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                	 出借完成当日计收益，每日计收益直至成功退出。成功退出后，一次性归还本金及获得约定收益。暂不支持利息复投。
                                            </p>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-3  control-label">还款方式</label>

                                        <div class="col-sm-9">
                                            <p class="form-control-static">
                                                	授权服务期结束后出借人可随时申请退出。当出借人所持有相关标的或债权全部转让完毕，方可视为成功。成功退出日一次性归还本金及获得约定收益。
                                            </p>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
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