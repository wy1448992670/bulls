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
<title>明细查询</title>
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

.numberLine {
	word-break: break-all;
	word-wrap: break-word;
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
				<h1>商城管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<input type="hidden" id="aid" />
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							明细查询列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />

							<shiro:hasPermission name="report:exportOrderDetailList">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()" href="JavaScript:;">
									<i class="icon-plus"></i>
									导出Excel
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline  col-lg-5 pull-right col-xs-11" id="form" action="orderDetailList">
									<input type="hidden" name="page" value="1" />

									<div class="row">
										<div class="form-group col-md-4 ">
											<div>
												<input class="form-control keyword" name="trueName" id="trueName" type="text" placeholder="请输入真实姓名搜索" value="${trueName }" />
											</div>
										</div>
										<div class="form-group col-md-3 ">
											<div>
												<input class="form-control keyword" name="keyword" id="keyword" type="text" placeholder="请输入订单号搜索" value="${keyword }" />
											</div>
										</div>
										<div class="form-group col-md-4 col-xs-3">
											<select class="select2able" name="departmentId" id="departmentId">
												<option value="">请选择部门</option>
												<c:forEach var="department" items="${departments}">
													<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-4 hidden-xs"></div>
										<div class="form-group col-md-4 hidden-xs">
											<input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime" type="text" placeholder="请选择起始销售日期" />
										</div>
										<div class="form-group col-md-4 hidden-xs">
											<input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime" type="text" placeholder="请选择结束销售日期" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-4 hidden-xs"></div>
										<div class="form-group col-md-4 col-xs-12">
											<select class="select2able" name="goodsCategory" id="goodsCategory">
												<option value="">请选择商品类别</option>
												<option value="牛肉" <c:if test="${goodsCategory == '牛肉' }">selected</c:if>>牛肉</option>
												<option value="红酒" <c:if test="${goodsCategory == '红酒' }">selected</c:if>>红酒</option>
											</select>
										</div>
										<%-- <div class="form-group col-md-4">
									<select class="select2able" name="payStatus" id="payStatus">
										<option value="">请选择支付状态</option>
										<option value="0" <c:if test="${payStatus == 0 }">selected</c:if>>成功</option>
										<option value="1" <c:if test="${payStatus == 1 }">selected</c:if>>处理中</option>
										<option value="2" <c:if test="${payStatus == 2 }">selected</c:if>>失败</option>
									</select>
								</div> --%>
										<div class="form-group col-md-4 pull-right col-xs-12">
											<select class="select2able" name="orderStatus" id="orderStatus">
												<option value="">请选择订单状态</option>
												<option value="0" <c:if test="${orderStatus == 0 }">selected</c:if>>未支付</option>
												<option value="1" <c:if test="${orderStatus == 1 }">selected</c:if>>支付中</option>
												<option value="2" <c:if test="${orderStatus == 2 }">selected</c:if>>已支付</option>
												<option value="8" <c:if test="${orderStatus == 8 }">selected</c:if>>退款中</option>
												<option value="3" <c:if test="${orderStatus == 3 }">selected</c:if>>拣货中</option>
												<option value="4" <c:if test="${orderStatus == 4 }">selected</c:if>>已发货</option>
												<option value="5" <c:if test="${orderStatus == 5 }">selected</c:if>>订单取消</option>
												<option value="6" <c:if test="${orderStatus == 6 }">selected</c:if>>订单退款</option>
												<option value="7" <c:if test="${orderStatus == 7 }">selected</c:if>>交易完成</option>
											</select>
										</div>
									</div>

									<input type="hidden" name="page" value="${page }" />
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th width="160px">销售日期</th>
											<th width="170px">订单号</th>
											<th>真实姓名</th>
											<th>商品类别</th>
											<th width="160px">商品名称</th>
											<th width="50px">数量</th>
											<th>单价(元)</th>
											<th>合计金额(元)</th>
											<th>余额支付(元)</th>
											<th>现金支付(元)</th>
											<th>授信支付(元)</th>
											<th>红包支付(元)</th>
											<th width="70px">现金支付状态</th>
											<th width="160px">现金支付日期</th>
											<th width="80px">订单状态</th>
											<th width="160px">订单支付日期</th>
											<th width="160px">退款日期</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td>${i.id }</td>
												<td>
													<fmt:formatDate value="${i.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<a href="${basePath}shop/orderDetail?id=${i.order_id}">${i.order_no }</a>
												</td>
												<td>
													<a href="${basePath}user/detail/app?id=${i.user_id}">${i.true_name }</a>
												</td>
												<td>${i.category_name }</td>
												<td>${i.goods_name }</td>
												<td>${i.count }</td>
												<td>${i.sale_price }</td>
												<td>${i.total_amount }</td>
												<td>${i.balance_pay_money }</td>
												<td>${i.cash_pay_money }</td>
												<td>${i.credit_pay_money }</td>
												<td>${i.hongbao_money }</td>
												<td>
													<c:if test="${i.pay_status == 0 }">
														<span class="label label-success">成功</span>
													</c:if>
													<c:if test="${i.pay_status == 1 }">
														<span class="label label-info">处理中</span>
													</c:if>
													<c:if test="${i.pay_status == 2 }">
														<span class="label label-danger">失败</span>
													</c:if>
												</td>
												<td>
													<fmt:formatDate value="${i.pay_date }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<c:if test="${i.order_status == 0 }">
														<span class="label label-default">未支付</span>
													</c:if>
													<c:if test="${i.order_status == 1 }">
														<span class="label label-info">支付中</span>
													</c:if>
													<c:if test="${i.order_status == 2 }">
														<span class="label label-success">已支付</span>
													</c:if>
													<c:if test="${i.order_status == 3 }">
														<span class="label label-info">拣货中</span>
													</c:if>
													<c:if test="${i.order_status == 4 }">
														<span class="label label-primary">已发货</span>
													</c:if>
													<c:if test="${i.order_status == 5 }">
														<span class="label label-danger">订单取消</span>
													</c:if>
													<c:if test="${i.order_status == 6 }">
														<span class="label label-warning">订单退款</span>
													</c:if>
													<c:if test="${i.order_status == 7 }">
														<span class="label label-success">交易完成</span>
													</c:if>
													<c:if test="${i.order_status == 8 }">
														<span class="label label-info">退款中</span>
													</c:if>
												</td>
												<td>
													<fmt:formatDate value="${i.pay_time }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<fmt:formatDate value="${i.refund_finish_time }" pattern="yyyy-MM-dd HH:mm:ss" />
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
		function search() {
			$("#form").submit();
		};
		$(function() {
			$(".datepicker").datepicker({
				format : 'yyyy-mm-dd'
			});
			$('.select2able').select2({
			//	width : ""
			});
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
									return "orderDetailList?trueName=${trueName}&keyword=${keyword}&orderStatus=${orderStatus}"
											+"&payStatus=${payStatus}&goodsCategory=${goodsCategory}&startTime=${startTime}"
											+"&endTime=${endTime}&departmentId=${departmentId}&page="+ page;
								}
							});

			$(".datepicker").datepicker({
				showSecond : true,
				timeFormat : "hh:mm:ss",
				dateFormat : "yy-mm-dd"
			});

		});
	</script>
	<script>
		function yyccheck() {
			var flag = false;
			var trueName = $("#trueName").val();
			var keyword = $("#keyword").val();
			var orderStatus = $("#orderStatus").val();
			var goodsCategory = $("#goodsCategory").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
			var departmentId = $("#departmentId").val();
			if (startTime != "" && endTime != "") {
				if (startTime <= endTime) {
					flag = true;
				} else {
					alert("结束时间必须大于等于开始时间");
					return;
				}
			}
			if (flag) {
				location.href = "${basePath}report/exportOrderDetailList?trueName=" + trueName + "&keyword=" + keyword
						+ "&orderStatus=" + orderStatus + "&goodsCategory=" + goodsCategory + "&startTime=" + startTime
						+ "&endTime=" + endTime + "&departmentId=" + departmentId;
			} else {
				alert("时间区间为必选项");
			}
		}
	</script>
</body>
</html>
