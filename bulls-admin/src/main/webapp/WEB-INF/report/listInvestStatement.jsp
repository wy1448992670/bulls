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
<title>物权资产</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<style type="text/css">

.table .over {
	overflow: hidden;
	width: 40%;
	text-overflow: ellipsis;
	white-space: nowrap;
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
				<h1>报表统计</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							认养统计
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
							<shiro:hasPermission name="invest:reportInvestStatement">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" href="javascript:;" onclick="report()">
									<i class="icon-plus"></i>
									
									导出Excel
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline col-lg-4 col-xs-11 pull-right" id="form"  action="${basePath}report/investStatement">						
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${startDate}" name="startDate" id="startDate" type="text" placeholder="起始认养日期段" />
										</div>
										<div class="form-group col-md-6"> 
											<input class="form-control datepicker" value="${endDate}" name="endDate" id="endDate" type="text" placeholder="结束认养日期段" />
										</div>
									</div>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							  </div>	
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>项目</th>
											<th>认养数量（只）</th>
											<th>购牛款</th>
											<th>管理费</th>								
											<th>饲养费</th>								
											<th>认养金额合计</th>								
											<th>余额支付</th>								
											<th>现金支付</th>								
											<th>红包支付</th>								
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>   
													<c:if test="${g.noob== 0 }">牛犊6个月以下包含6个月</c:if>
													<c:if test="${g.noob== 1}">基础牛群6个月以上</c:if> 
												</td>
												<td>${g.totalCount}</td>
												<td>${g.totalCowMoney}</td>
												<td>${g.totalManageFee}</td>
												<td>${g.totalRaiseFee}</td>
												<td>${g.totalAmount}</td>
												<td>${g.totalBalancePayMoney}</td>
												<td>${g.totalRemainAmount}</td>
												<td>${g.totalHongbaoMoney}</td>
											</tr>
										</c:forEach>
											<tr>
												<td>   
													合计
												</td>
												<td>${total.count}</td>
												<td>${total.cowMoney}</td>
												<td>${total.manageFee}</td>
												<td>${total.raiseFee}</td>
												<td>${total.amount}</td>
												<td>${total.balancePayMoney}</td>
												<td>${total.remainAmount}</td>
												<td>${total.hongbaoMoney}</td>
											</tr>
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
		<script type="text/javascript">
			function search() {
				if(!checkDate()){
					alert('认养日期有误');
					return false;
				}
				$("#form").submit();
			}

			$(function() {
				$(".datepicker").datepicker({
					format : 'yyyy-mm-dd'
				});
				$('.select2able').select2({
					width : "150"
				});
				$(".keyword").keyup(function(e) {
					e = e || window.e;
					if (e.keyCode == 13) {
						$("#form").submit();
					}
				});
			/* 	$('#pagination')
						.bootstrapPaginator(
								{
									currentPage : parseInt('${page}'),
									totalPages : parseInt('${pages}'),
									bootstrapMajorVersion : 3,
									alignment : "right",
									pageUrl : function(type, page, current) {
										return "investStatement?startDate=${startDate}&endDate=${endDate}";
									}
								}); */

				$("#startDate").keyup(function(e) {
					e = e || window.e;
					if (e.keyCode == 13) {
						$("#form").submit();
					}
				});

				$("#endDate").keyup(function(e) {
					e = e || window.e;
					if (e.keyCode == 13) {
						$("#form").submit();
					}
				});
			});
			function report() {
				if(!checkDate()){
					alert('认养日期有误');
					return false;
				}
				
				var startDate = $.trim($('#startDate').val());
				var endDate = $.trim($('#endDate').val());
				window.location.href = "${basePath}report/reportInvestStatement?startDate=" + startDate + "&endDate=" + endDate;
			}

			function isEmpty(obj){
				if(obj==null||obj==''){
					return true;
				}else{
					return false;
				}
			}
			
			function checkDate() {
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				if(startDate!= '' && endDate!=  ''){
					if (startDate > endDate) {
						return false;
					} 
				}	
				return true;
			}
		</script>
</body>
</html>