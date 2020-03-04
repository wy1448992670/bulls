<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛只订单列表</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.table .over {
	overflow: hidden;
	width: 40%;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>
<script>
	function yyccheck() {
		var keyword = $.trim($("#keyword").val());
		var payStatus = $("#payStatus").val();
		var orderStatus = $("#orderStatus").val();
		var orderNo = $("#orderNo").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var startDueTime = $("#startDueTime").val();
		var endDueTime = $("#endDueTime").val();
		var startBuyBackTime = $("#startBuyBackTime").val();
		var endBuyBackTime = $("#endBuyBackTime").val();
		var yueLing = $("#yueLing").val();
		var departmentId = $("#departmentId").val();
		
		if (isNaN(yueLing)) {
			alert("月龄请输入数字！");
			return;
		}
		if (startDate != "" && endDate != "") {
			if (startDate > endDate) {
				alert("饲养结束时间必须大于等于饲养开始时间");
				return;
			}
		}
		location.href = "${basePath}investment/orderListReport?startDate=" + startDate + "&endDate=" + endDate
				+ "&payStatus=" + payStatus + "&orderStatus=" + orderStatus + "&keyword=" + keyword + "&orderNo="
				+ orderNo + "&yueLing=" + yueLing + "&startDueTime="+startDueTime + "&endDueTime=" + endDueTime 
				+ "&startBuyBackTime=" + startBuyBackTime + "&endBuyBackTime=" + endBuyBackTime +"&departmentId="+departmentId;
	}
</script>
</head>
<body>
	<div class="modal-shiftfix">
		<!-- Navigation -->
		<jsp:include page="../common/header.jsp"></jsp:include>
		<!-- End Navigation -->
		<div class="container-fluid main-content">
			<div class="page-title">
				<h1>牧场管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							订单查询列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
							<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()" href="JavaScript:;">
								<i class="icon-plus"></i>
								导出Excel
							</a>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()">搜索</button>
						</div>

						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline  col-lg-5 pull-right col-xs-11" id="form" action="orderList">
									<input type="hidden" name="page" value="1" />
									<div class="row">
										<div class="form-group col-md-6 col-xs-6">
											<select class="select2able" name="orderStatus" id="orderStatus">
												<option value="" <c:if test="${orderStatus == null }">selected</c:if>>订单状态</option>
												<c:forEach var="item" items="${investmentState}">
													<option value="${item.code}" <c:if test="${orderStatus == item.code }">selected</c:if>>${item.description}</option>
												</c:forEach>
											</select>
										</div>
										<div class="form-group col-md-6 col-xs-6">
											<select class="select2able" name="payStatus" id="payStatus">
												<option value="" <c:if test="${payStatus == null }">selected</c:if>>支付状态</option>
												<c:forEach var="item" items="${investPayState}">
													<option value="${item.code}" <c:if test="${payStatus == item.code }">selected</c:if>>${item.description}</option>
												</c:forEach>
											</select>
										</div>

									</div>
									<div class="row">
										<div class="form-group col-md-6 col-xs-12">
											<input class="form-control keyword" name="orderNo" id="orderNo" type="text" placeholder="请输入订单号" value="${orderNo }" />
										</div>
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control keyword" name="keyword" id="keyword" type="text" placeholder="请输入用户昵称、真实姓名、手机号搜索" value="${keyword }" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control datepicker" value="${startDate}" name="startDate" id="startDate" type="text" placeholder="请选择支付起始时间" />
										</div>
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control datepicker" value="${endDate}" name="endDate" id="endDate" type="text" placeholder="请选择支付结束时间" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control datepicker" value="${startDueTime}" name="startDueTime" id="startDueTime" type="text" placeholder="请选择到期起始时间" />
										</div>
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control datepicker" value="${endDueTime}" name="endDueTime" id="endDueTime" type="text" placeholder="请选择到期结束时间" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control datepicker" value="${startBuyBackTime}" name="startBuyBackTime" id="startBuyBackTime" type="text" placeholder="请选择回购起始时间" />
										</div>
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control datepicker" value="${endBuyBackTime}" name="endBuyBackTime" id="endBuyBackTime" type="text" placeholder="请选择回购结束时间" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control keyword" value="${yueLing}" name="yueLing" id="yueLing" type="number" placeholder="月龄" />
										</div>
										<div class="form-group col-md-3 col-xs-6">
											<select class="select2able" name="departmentId" id="departmentId">
												<option value="">请选择部门</option>
												<c:forEach var="department" items="${departments}">
													<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="aa()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover  order-no-break">
									<thead>
										<tr>
											<th>ID</th>
											<th width="180">订单号</th>
											<th>客户名称</th>
											<th width="120">饲养日期</th>
											<th>数量</th>
											<th width="80">管理费</th>
											<th width="80">饲养费</th>
											<th width="80">购牛款（元）</th>
											<th width="80">合计金额（元）</th>
											<th width="80">余额支付金额（元）</th>
											<th width="80">红包金额（元）</th>
											<th width="80">需支付金额（元）</th>
											<th>加息总金额（元）</th>
											<th width="70">支付状态</th>
											<th width="70">订单状态</th>
											<th>饲养期限(天)</th>
											<th width="100">支付日期</th>
											<th width="100">到期日期</th>
											<th width="100">回购日期</th>
											<th width="100">耳标号</th>
											<th>牛只月龄</th>
											<th width="120">上次回购日期</th>
											<th>上次回购价款</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }" varStatus="s">
											<tr>
												<td>${i.id}</td>
												<td>${i.orderNo}</td>
												<td>
													<a href="${basePath}user/detail/app?id=${i.userId}">${i.trueName }</a>
												</td>
												<td>
													<fmt:formatDate value="${i.deadline}" pattern="yyyy-MM-dd" />
												</td>
												<td>1</td>
												<td>${i.manageFee}</td>
												<td>${i.raiseFee}</td>
												<td>${i.cowAmount}</td>
												<td>${i.totalAmount}</td>
												<td>${i.balancePayMoney}</td>
												<td>${i.hongbaoMoney}</td>
												<td>${i.remainAmount}</td>
												<td>${i.addInterest}</td>
												<td>
													<%--<c:forEach var="item" items="${investPayState }">
													<c:if test="${i.payStatus == item.code }">${item.description}</c:if>
												</c:forEach>--%>
													<c:if test="${i.payStatus == 0 }">
														<span class="label label-default">未支付</span>
													</c:if>
													<c:if test="${i.payStatus == 1 }">
														<span class="label label-warning">支付中</span>
													</c:if>
													<c:if test="${i.payStatus == 2 }">
														<span class="label label-success">已支付</span>
													</c:if>
												</td>
												<td>
													<%--<c:forEach var="item" items="${investmentState }">
													<c:if test="${i.orderStatus == item.code }">${item.description}</c:if>
												</c:forEach>--%>
													<c:if test="${i.orderStatus == 0 }">
														<span class="label label-default">未饲养</span>
													</c:if>
													<c:if test="${i.orderStatus == 1 }">
														<span class="label label-warning">饲养期</span>
													</c:if>
													<c:if test="${i.orderStatus == 2 }">
														<span class="label label-success">已卖牛</span>
													</c:if>
													<c:if test="${i.orderStatus == 3 }">
														<span class="label label-danger">已取消</span>
													</c:if>
												</td>
												<td>${i.limitDays}</td>
												<%-- <td><fmt:formatDate value="${i.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
												<td>
													<fmt:formatDate value="${i.payTime}" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<fmt:formatDate value="${i.dueTime}" pattern="yyyy-MM-dd" />
												</td>
												<td>
													<fmt:formatDate value="${i.buyBackTime}" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>${i.earNumber}</td>
												<td>${i.yueLing}</td>
												<td>
													<fmt:formatDate value="${i.parentBuyBackTime}" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>${i.parentBuyBackAmount}</td>
												<td>
													<shiro:hasPermission name="investment:orderDetail">
														<a href="${basePath}investment/orderDetail?id=${i.id}">详情</a>
														<%--<a href="${basePath}recharge/detail?id=${i.id}">详情</a>--%>
													</shiro:hasPermission>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
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
	<script src="${basePath}js/comm.js" type="text/javascript"></script>


	<script type="text/javascript">
		function aa() {
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var yueLing = $("#yueLing").val();
			if (isNaN(yueLing)) {
				alert("月龄请输入数字！");
				return;
			}
			if (startDate != "" && endDate != "") {
				if (startDate > endDate) {
					alert("饲养结束时间必须大于等于饲养开始时间");
					return;
				}
			}

			$("#form").submit();

		}

		$(function() {
			$(".datepicker").datepicker({
				format : 'yyyy-mm-dd',
				showSecond : true,
				timeFormat : "hh:mm:ss",
				dateFormat : "yy-mm-dd"
			});
			$('.select2able').select2();
			$(".keyword").keyup(function(e) {
				e = e || window.e;
				if (e.keyCode == 13) {
					$("#form").submit();
				}
			});
			$('#pagination')
					.bootstrapPaginator(
							{
								currentPage : parseInt('${page}'),
								totalPages : parseInt('${pages}'),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current) {
									return "orderList?keyword=${keyword}&startDate=${startDate}&endDate=${endDate}&payStatus=${payStatus}&orderStatus=${orderStatus}"
											+ "&orderNo=${orderNo}&yueLing=${yueLing}&startDueTime=${startDueTime}&endDueTime=${endDueTime}"
												+ "&startBuyBackTime=${startBuyBackTime}&endBuyBackTime=${endBuyBackTime}&departmentId=${departmentId}&page=" + page;
								}
							});
		});
	</script>
</body>
</html>
