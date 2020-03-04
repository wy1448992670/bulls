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
							授信金额查询
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
							<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" href="javascript:;" onclick="report()">
								<i class="icon-plus"></i>
								导出Excel
							</a>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>

						<div class="widget-content padded clearfix">
						 <div id="myModal">
							<form class="search1 AppFixed form-inline col-lg-4 pull-right col-xs-11" id="form" action="${basePath}report/listCreditFunds">
								<div class="row">
									<div class="form-group col-md-6">
										<select class="select2able" name="typeId" id="typeId">
											<!-- 注意 种种 原因   value值 并非一一对应表中的 account_operate_type_id，以java类中逻辑为准-->
											<option value="">---所有---</option>
											<option value="1" <c:if test="${typeId == 1 }">selected</c:if>>购买畜牧</option>
											<option value="2" <c:if test="${typeId == 2 }">selected</c:if>>购买商品</option>
											<option value="3" <c:if test="${typeId == 3 }">selected</c:if>>利息兑付</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6">
										<input class="form-control datepicker" value="${startDate }" name="startDate" id="startDate" type="text" placeholder="起始日期" />
									</div>
									<div class="form-group col-md-6">
										<input class="form-control datepicker" value="${endDate }" name="endDate" id="endDate" type="text" placeholder="结束日期" />
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
											<th>日期</th>
											<th>状态</th>
											<th>客户名称</th>
											<th>操作金额</th>
											<th>本金</th>
											<th>支付订单号</th>
											<th>订单号</th>
											<th>订单类型</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td><fmt:formatDate value="${g.create_date}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
												<td>${g.status}</td>
												<td>${g.user_true_name}</td>
												<td>${g.flow_amount}</td>
												<td>${g.investment_amount}</td>
												<td>${g.recharge_order_no}</td>
												<td>${g.order_no}</td>
												<td>${g.order_type}</td>
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
		<script type="text/javascript">
			function search() {
				if (!checkDate) {
					alert('回购日期有误');
					return false;
				}
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
										return "listCreditFunds?typeId=${typeId}&startDate=${startDate}&endDate=${endDate}&page="
												+ page;
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
				if (!checkDate) {
					alert('回购日期有误');
					return false;
				}

				var startDate = $.trim($('#startDate').val());
				var endDate = $.trim($('#endDate').val());
				var typeId = $.trim($('#typeId').val());
				window.location.href = "${basePath}report/export/reportListCreditFunds?startDate=" + startDate
						+ "&endDate=" + endDate + "&typeId=" + typeId
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

			function isEmpty(obj) {
				if (obj == null || obj == '') {
					return true;
				} else {
					return false;
				}
			}

			function checkDate() {
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
				if (startDate != null && endDate !== null) {
					return true;
				}
				if (startDate > endDate) {
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