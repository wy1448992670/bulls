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
<title>退款审核</title>
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
							订单运费列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />

							<shiro:hasPermission name="report:exportCarriageList">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()" href="JavaScript:;">
									<i class="icon-plus"></i>
									导出Excel
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline  col-lg-5 pull-right col-xs-11" id="form" action="carriageList">
									<input type="hidden" name="page" value="1" />

									<div class="row">
										<div class="form-group col-md-4 col-xs-12">
											<div>
												<input class="form-control keyword" name="orderNo" id="orderNo" type="text" placeholder="请输入订单号搜索" value="${orderNo }" />
											</div>
										</div>
										<div class="form-group col-md-4 ">
											<div>
												<input class="form-control keyword" name="skuCode" id="skuCode" type="text" placeholder="请输入SKU码搜索" value="${skuCode }" />
											</div>
										</div>
										<div class="form-group col-md-4 col-xs-12">
											<select class="select2able" name="orderStatus" id="orderStatus">
												<option value="">请选择订单状态</option>
												<option value="2" <c:if test="${orderStatus == 2 }">selected</c:if>>已支付</option>
												<option value="3" <c:if test="${orderStatus == 3 }">selected</c:if>>拣货中</option>
												<option value="4" <c:if test="${orderStatus == 4 }">selected</c:if>>已发货</option>
												<option value="5" <c:if test="${orderStatus == 5 }">selected</c:if>>订单取消</option>
												<option value="8" <c:if test="${orderStatus == 8 }">selected</c:if>>退款中</option>
												<option value="6" <c:if test="${orderStatus == 6 }">selected</c:if>>退款完成</option>
												<option value="7" <c:if test="${orderStatus == 7 }">selected</c:if>>交易完成</option>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${payStartTime }" name="payStartTime" id="payStartTime" type="text" placeholder="请选择支付起始时间" />
										</div>
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${payEndTime }" name="payEndTime" id="payEndTime" type="text" placeholder="请选择支付结束时间" />
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
											<th width="190px">订单号</th>
											<th>SKU码</th>
											<th>商品名称(个数)</th>
											<th>姓名</th>
											<th>手机号</th>
											<th>地址</th>
											<th>订单金额(元)</th>
											<th>订单状态</th>
											<th>总重量(KG)</th>
											<th>快递费(元)</th>
											<th>实际邮费(元)</th>
											<th width="160px">付款时间</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td>${i.id }</td>
												<td>
													<a href="${basePath}shop/orderDetail?id=${i.id}">${i.order_no }</a>
												</td>
												<td>${i.sku_code }</td>
												<td>${i.name_and_count }</td>
												<td>
													<a href="${basePath}user/detail/app?id=${i.user_id}">${i.true_name }</a>
												</td>
												<td>${i.phone }</td>
												<td>${i.addressee_detail }</td>
												<td>${i.total_money }</td>
												<td>
													<c:if test="${i.state == 0 }">
														<span class="label label-default">未支付</span>
													</c:if>
													<c:if test="${i.state == 1 }">
														<span class="label label-info">支付中</span>
													</c:if>
													<c:if test="${i.state == 2 }">
														<span class="label label-success">已支付</span>
													</c:if>
													<c:if test="${i.state == 3 }">
														<span class="label label-info">拣货中</span>
													</c:if>
													<c:if test="${i.state == 4 }">
														<span class="label label-primary">已发货</span>
													</c:if>
													<c:if test="${i.state == 5 }">
														<span class="label label-danger">订单取消</span>
													</c:if>
													<c:if test="${i.state == 6 }">
														<span class="label label-warning">订单退款</span>
													</c:if>
													<c:if test="${i.state == 7 }">
														<span class="label label-success">交易完成</span>
													</c:if>
													<c:if test="${i.state == 8 }">
														<span class="label label-info">退款中</span>
													</c:if>
												</td>
												<td>${i.weight_sum }</td>
												<td>${i.express_fee }</td>
												<td>${i.real_express_fee }</td>
												<td>
													<fmt:formatDate value="${i.pay_time }" pattern="yyyy-MM-dd HH:mm:ss" />
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
	        var payStartTime = $("#payStartTime").val();
	        var payEndTime = $("#payEndTime").val();
	        if(payStartTime!="" && payEndTime!=""){
	            if(payStartTime > payEndTime){
	                alert("付款结束时间必须大于等于开始时间");
	                return;
	            }
	        }
	        $("#form").submit();
	    };
	    
	    $(function () {
	        $(".datepicker").datepicker({
	            format: 'yyyy-mm-dd'
	        });
	        $('.select2able').select2({width: ""});
	        $(".keyword").keyup(function (e) {
	            e = e || window.e;
	            if (e.keyCode == 13) {
	                $("#form").submit();
	            }
	        });
	        $('#pagination').bootstrapPaginator({
	            currentPage: parseInt('${page}'),
	            totalPages: parseInt('${pages}'),
	            bootstrapMajorVersion: 3,
	            alignment: "right",
	            pageUrl: function (type, page, current) {
	                return "carriageList?orderNo=${orderNo}&skuCode=${skuCode}&orderStatus=${orderStatus}"
						+"&payStartTime=${payStartTime}&payEndTime=${payEndTime}&page=" + page;
	            }
	        });
	
	        $(".datepicker").datepicker({
	            showSecond: true,
	            timeFormat: "hh:mm:ss",
	            dateFormat: "yy-mm-dd"
	        });
	
	    });
	</script>
	
	<script>
		function yyccheck(){
			var flag = false;
	    	var orderNo = $("#orderNo").val();
	    	var skuCode = $("#skuCode").val();
	    	var orderStatus = $("#orderStatus").val();
	    	var payStartTime = $("#payStartTime").val();
	    	var payEndTime = $("#payEndTime").val();
			if(payStartTime!="" && payEndTime!=""){
				if(payStartTime <= payEndTime){
	    			flag = true;
	    		}else{
	    			alert("申请结束时间必须大于等于开始时间");
	    			return;
	    		}
	    	}
			if(flag){
	    		location.href="${basePath}report/exportCarriageList?orderNo="+orderNo+"&skuCode="+skuCode+"&orderStatus="+orderStatus
					+"&payStartTime="+payStartTime+"&payEndTime="+payEndTime;
	    	}else{
	    		alert("时间区间为必选项");
	    	}
		}
	</script>
</body>
</html>
