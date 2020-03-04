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
<title>商城订单查询</title>
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
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							商城订单详情
						</div>
						<div class="widget-content padded clearfix">
							<form action="" class="form-horizontal" id="validate-form">
								<c:if test="${path != null}">
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-7">
											<p class="form-control-static">
												<img alt="" src="${aPath}upload/${path }" width="300px;" />
											</p>
										</div>
									</div>
								</c:if>

								<div class="form-group">
									<label class="control-label col-md-2">订单号:</label>
									<div class="col-md-7">
										<p class="form-control-static">${info.orderNo }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">订单金额:</label>
									<div class="col-md-7">
										<p class="form-control-static">${info.totalMoney }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品种类个数:</label>
									<div class="col-md-7">
										<p class="form-control-static">${info.count }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">实际支付金额:</label>
									<div class="col-md-7">
										<p class="form-control-static">${info.realPayMoney }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">余额支付金额:</label>
									<div class="col-md-7">
										<p class="form-control-static">${info.balancePayMoney }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">授信支付金额:</label>
									<div class="col-md-7">
										<p class="form-control-static">${info.creditPayMoney }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">红包金额:</label>
									<div class="col-md-7">
										<p class="form-control-static">${info.hongbaoMoney }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">订单状态:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${info.state == 0 }">
												<span class="label label-default">未支付</span>
											</c:if>
											<c:if test="${info.state == 1 }">
												<span class="label label-info">支付中</span>
											</c:if>
											<c:if test="${info.state == 2 }">
												<span class="label label-success">已支付</span>
											</c:if>
											<c:if test="${info.state == 3 }">
												<span class="label label-info">拣货中</span>
											</c:if>
											<c:if test="${info.state == 4 }">
												<span class="label label-primary">已发货</span>
											</c:if>
											<c:if test="${info.state == 5 }">
												<span class="label label-danger">订单取消</span>
											</c:if>
											<c:if test="${info.state == 6 }">
												<span class="label label-warning">订单退款</span>
											</c:if>
											<c:if test="${info.state == 7 }">
												<span class="label label-success">交易完成</span>
											</c:if>
											<c:if test="${info.state == 8 }">
												<span class="label label-info">退款中</span>
											</c:if>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">快递方式:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${info.totalMoney >= 200 }">
												<span class="label label-primary">包邮</span>
											</c:if>
											<c:if test="${info.totalMoney < 200 }">
												<span class="label label-default">不包邮</span>
											</c:if>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">购买时间:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${info.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">返回</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>

				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							已购商品信息
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered" id="investors">
									<thead>
										<tr>
											<th>商品编号</th>
											<th>商品名称</th>
											<th>购买数量</th>
											<th>购买时间</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" varStatus="loop" items="${goodsList }">
											<tr>
												<td>${i.goodsNo }</td>
												<td>
													<a href="${basePath}shop/goodsDetail?id=${i.id}">${i.goodsName }</a>
												</td>
												<td>${orderDetailList[loop.count-1].count}</td>
												<td>
													<fmt:formatDate value="${info.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="text-right">
								<ul id="pagination"></ul>
							</div>
						</div>
					</div>
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							商品物流信息
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered" id="investors">
									<thead>
										<tr>
											<th>快递公司</th>
											<th>物流编号</th>
											<th>快递费</th>
											<th>发货时间</th>
											<th>收件人姓名</th>
											<th>收件人电话</th>
											<th>详细地址</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>${info.express }</td>
											<td class="numberline">${info.expressNum }</td>
											<td>${info.expressFee }</td>
											<td>
												<fmt:formatDate value="${info.deliveryDate }" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
											<td>${info.addresseeName }</td>
											<td>${info.addresseePhone }</td>
											<td class="numberline">${info.addresseeDetail }</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="text-right">
								<ul id="pagination"></ul>
							</div>
						</div>
					</div>
					<c:if test="${info.expressNum != null }">
						<div>
							<iframe style="width: 100%; height: 100%; border: 0;" src="https://m.kuaidi100.com/result.jsp?nu=${info.expressNum }"></iframe>
						</div>
					</c:if>
				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script type="text/javascript">
		
	</script>
</body>
</html>