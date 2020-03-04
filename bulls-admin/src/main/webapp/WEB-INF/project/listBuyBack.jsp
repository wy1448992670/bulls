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
				<h1>牧场管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							回购查询列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />
							<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" href="javascript:;" onclick="report()">
								<i class="icon-plus"></i>
								导出Excel
							</a>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline  col-lg-4 pull-right col-xs-11" id="form" action="listBuyBack">

									<div class="row">
										<div class="form-group col-md-4">
											<input class="form-control keyword" value="${startAge }" name="startAge" id="startAge" type="number" placeholder="起始月龄" />
										</div>
										<div class="form-group col-md-4">
											<input class="form-control keyword" value="${endAge }" name="endAge" id="endAge" type="number" placeholder="结束月龄" />
										</div>
										<div class="form-group col-md-3 col-xs-4">
											<select class="select2able" name="departmentId" id="departmentId">
												<option value="">请选择部门</option>
												<c:forEach var="department" items="${departments}">
													<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${startDate }" name="startDate" id="startDate" type="text" placeholder="起始回购日期段" />
										</div>
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${endDate }" name="endDate" id="endDate" type="text" placeholder="结束回购日期段" />
										</div>
									</div>


									<shiro:hasPermission name="user:export:app">
										<div class="row">
											<%-- 	<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime" type="text" placeholder="还款计划请选择起始时间" />
										</div>
										<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime" type="text" placeholder="还款计划请选择结束时间" />
										</div> --%>
											<div class="form-group col-md-4">
												<!-- 			<a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="#" onclick="report()">
												<i class="icon-plus"></i>
												导出还款计划
											</a> -->
											</div>
										</div>
									</shiro:hasPermission>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="4%">ID</th>
											<th>回购日期</th>
											<th width="9%">订单号</th>
											<th width="4%">客户名称</th>
											<th width="4%">数量</th>
											<th>应付本金</th>
											<th>应付授信</th>
											<th>应付合计</th>
											<th>已使用授信</th>
											<th>回款金额</th>
											<th>回款状态</th>
											<th>订单状态</th>
											<th>饲养期限</th>
											<th>耳标号</th>
											<th width="4%">牛只月龄</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>${g.id}</td>
												<td>
													<fmt:formatDate value="${g.buy_back}" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td style="word-break: break-word">${g.order_no}</td>
												<td>${g.true_name}</td>
												<td>${g.quantity}</td>
												<td>${g.amount}</td>
												<td>${g.interest_amount}</td>
												<td>${g.total}</td>
												<td>${g.use_amount}</td>
												<td>${g.returned_money}</td>
												<td>${g.return_status}</td>
												<td>${g.status}</td>
												<td>${g.limit_days}</td>
												<td>${g.ear_number}</td>
												<td>${g.yue_ling}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<ul id="pagination" style="float: right"></ul>
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
					width : "150"
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
										return "listBuyBack?startAge=${startAge}&endAge=${endAge}&startDate=${startDate}&endDate=${endDate}"
												+"&departmentId=${departmentId}&page="+ page;
									}
								});

				$("#lendBeginTimeStartTime").keyup(function(e) {
					e = e || window.e;
					if (e.keyCode == 13) {
						$("#form").submit();
					}
				});

				$("#lendBeginTimeEndTime").keyup(function(e) {
					e = e || window.e;
					if (e.keyCode == 13) {
						$("#form").submit();
					}
				});
			});
			function report() {
				/* 				if (!checkDate()) {
				 alert('回购日期选择有误');
				 return false;
				 }
				 if (!checkAge()) {
				 alert('月龄填写有误');
				 return false;
				 } */
				var startDate = $.trim($('#startDate').val());
				var endDate = $.trim($('#endDate').val());
				var keyword = $.trim($('#keyword').val());
				var startAge = $.trim($('#startAge').val());
				var endAge = $.trim($('#endAge').val());
				var departmentId = $("#departmentId").val();
				window.location.href = "${basePath}report/export/reportListBuyBack?startDate=" + startDate
						+ "&endDate=" + endDate + "&startAge=" + startAge + "&endAge=" + endAge
						+ "&departmentId=" + departmentId;
			}
			function desc(o) {
				var dc = $("#desc").val();
				if (dc == "asc") {
					$("#desc").val("desc");
				} else {
					$("#desc").val("asc");
				}
				$("#form").submit();
			}

			function checkDate() {
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				if (startDate > endDate) {
					return false;
				} else if (startDate == null || endDate == null || startDate == '' || endDate == '') {
					return false;
				} else {
					return true;
				}
			}
			function checkAge() {
				var startAge = $('#startAge').val();
				var endAge = $('#endAge').val();
				if (startAge > endAge) {
					return false;
				} else if (startAge == null || endAge == null || startAge == '' || endAge == '') {
					alert(222);
					return false;
				} else {
					return true;
				}
			}
		</script>
</body>
</html>