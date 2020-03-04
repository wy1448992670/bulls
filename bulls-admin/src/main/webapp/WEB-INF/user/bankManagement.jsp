<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>银行管理</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="modal-shiftfix">
		<!-- Navigation -->
		<jsp:include page="../common/header.jsp"></jsp:include>
		<!-- End Navigation -->
		<div class="container-fluid main-content">
			<div class="page-title">
				<h1>用户管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>银行管理
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
						</div>
						<div class="widget-content padded clearfix">
						<div id="myModal">
							<form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form"
								action="bankManagement">
								<div class="row">
									<div class="form-group col-md-6 pull-right">
										<shiro:hasPermission name="user:bankManagement:add">
											<a class="btn btn-sm btn-primary-outline pull-right"
											   href="editBankManagement" id="add-row"><i
													class="icon-plus"></i>添加银行信息</a>
										</shiro:hasPermission>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-9">
										<input class="form-control keyword" name="keyword"
												type="text" placeholder="请输入关键字" value="${keyword }" />
									</div>
									<div class="form-group col-md-3">
										<button class="btn btn-primary pull-right hidden-xs"
											type="button" onclick="aa()">搜索</button>
									</div>
								</div>
								<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="aa()">搜索</button>
								<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								<input type="hidden" name="page" value="1" />
							</form>
							</div>
							<div class="table-responsive">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>序号</th>
										<th>银行名称</th>
										<th>银行代码</th>
										<th>富友支付CODE</th>
										<th>富友提现CODE</th>
										<th>首绑最大限额（元）</th>
										<th>绑卡每日最大限额（元）</th>
										<th>绑卡单笔最大限额（元）</th>
										<th>绑卡单笔最小限额（元）</th>
										<shiro:hasPermission name="user:bankManagement:edit">
											<th width="80">操作</th>
										</shiro:hasPermission>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="i" items="${list }" varStatus="s">
										<tr>
											<td>${s.index+1}</td>
											<td>${i.name}</td>
											<td>${i.code}</td>
											<td>${i.fuiouCode}</td>
											<td>${i.fuiouWithdrawCode}</td>
											<td>${i.firstBindMaxAmount}</td>
											<td>${i.bindDailyMaxAmount}</td>
											<td>${i.bindSingleMaxAmount}</td>
											<td>${i.bindSingleMinAmount}</td>
											<shiro:hasPermission name="user:bankManagement:edit">
												<td><a class="btn btn-primary"
													   href="editBankManagement?&id=${i.id}">编辑</a></td>
											</shiro:hasPermission>
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
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script type="text/javascript">
		function aa() {
			$("#form").submit();
		}

		$(function() {
			$('.select2able').select2();
			$(".keyword").keyup(function(e) {
				e = e || window.e;
				if (e.keyCode == 13) {
					$("#form").submit();
				}
			});
			$('#pagination').bootstrapPaginator({
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "bankManagement?keyword=${keyword}&page=" + page;
				}
			});
		});
	</script>
</body>
</html>
