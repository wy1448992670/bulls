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
    <title>批量创建散标项目</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
               标的查看
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">

                    <div class="heading">
                        <i class="icon-table"></i>标的查看【${id}】
                    </div>
                    <div class="widget-content padded clearfix">
							<table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                            	<th>标的ID</th>
                            	<th>名称</th>
                                <th>期限</th>
                                <th>项目募集金额</th>
                                <th>已投金额</th>
                                <th>创建时间</th>
                                <th>开始时间</th>
                                <th>截至时间</th>
                                <th>状态</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${list}" varStatus="i">
                                <tr>
                                	<td style="text-align: center;">
                                		${item.id}
                                    </td>
                                	<td style="text-align: center;">
                                		${item.title}
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.limitDays}
                                    </td>
                                    
                                    <td style="text-align: center;">
                                    	${item.totalAmount}
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.investedAmount}
                                    </td>
                                    
                                    <td style="text-align: center;">
                                    	<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td style="text-align: center;">
                                    	<fmt:formatDate value="${item.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td style="text-align: center;">
                                    	<fmt:formatDate value="${item.deadline}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>

                                    <td style="text-align: center;">
                                    	<c:if test="${item.status=='0'}">
                                    		<c:if test="${item.statusIsShow == true}">
                                    			创建
                                    		</c:if>
                                    		<c:if test="${item.statusIsShow == false}">
                                    			待发布
                                    		</c:if>
                                    	</c:if>
                                    	<c:if test="${item.status=='1'}">预购</c:if>
                                    	<c:if test="${item.status=='2'}">投资中</c:if>
                                    	<c:if test="${item.status=='3'}">投资完成</c:if>
                                    	<c:if test="${item.status=='4'}">还款中</c:if>
                                    	<c:if test="${item.status=='5'}">还款成功</c:if>
                                    	<c:if test="${item.status=='6'}">还款失败</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                    
                    <div class="heading">
                        <i class="icon-table"></i>还款计划
                    </div>
                    <div class="widget-content padded clearfix">
							<table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                            	<th>期数</th>
                            	<th>还款日期</th>
                                <th>本金</th>
                                <th>利息</th>
                                <th>服务费</th>
                                <th>还款总额</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${interest}" varStatus="i">
                                <tr>
                                	<td style="text-align: center;">
                                		${i.index+1}
                                    </td>
                                	<td style="text-align: center;">
                                		${item.date}
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.capitalAmount}
                                    </td>
                                    
                                    <td style="text-align: center;">
                                    	${item.interestAmount}
                                    </td>
                                    
                                    <td style="text-align: center;">
                                    	${item.fff}
                                    </td>
                                    <td style="text-align: center;">
										${item.capInterAmount}
                                    </td>
                                    <td style="text-align: center;">
                                    	<c:if test="${item.hasDividended=='0'}">未还款</c:if>
                                    	<c:if test="${item.hasDividended=='1'}">已还款</c:if>
                                    </td>
                                    <td style="text-align: center;">
                                    	<a href="">催收</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                            	<td colspan=2 style="text-align: right;">合计：</td>
                            	<td style="text-align: center;">${capAmount}</td>
                            	<td style="text-align: center;">${interestAmount}</td>
                            	<td style="text-align: center;">${fffAmount}</td>
                            	<td style="text-align: center;">${totleAmount}</td>
                            	<td></td>
                            	<td></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    
                    <div class="heading">
                        <i class="icon-table"></i>债转
                    </div>
                    <div class="widget-content padded clearfix">
							<table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                            	<th>序号</th>
                            	<th>项目名称</th>
                                <th>剩余天数</th>
                                <th>发起时间</th>
                                <th>转让人</th>
                                <th>转让金额</th>
                                <th>受让人</th>
                                <th>受让金额</th>
                                <th>受让时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${orderChanges}" varStatus="i">
                                <tr>
                                	<td style="text-align: center;">
                                		${i.index+1}
                                    </td>
                                	<td style="text-align: center;">
                                		${item.title}
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.syDay}
                                    </td>
                                    
                                    <td style="text-align: center;">
                                    	<fmt:formatDate value="${item.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.sellUser}
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.sendAmount}
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.buyUser}
                                    </td>
                                    <td style="text-align: center;">
                                    	${item.buyAmount}
                                    </td>
                                    <td style="text-align: center;">
                                    	<fmt:formatDate value="${item.buyTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
</body>
</html>