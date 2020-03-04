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
<title>企业管理列表</title>
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
				<h1>企业管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							企业列表
							<!-- <a class="btn btn-sm btn-primary-outline pull-right" href="add" id="add-row"><i class="icon-plus"></i>添加企业</a> -->
							<shiro:hasPermission name="enterprise:add">
								<a class="btn btn-sm btn-primary-outline pull-right" href="add" id="add-row">
									<i class="icon-plus"></i>
									添加企业/个人
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="list">
								<div class="row">
									<div class="form-group col-md-6"></div>
									<div class="form-group col-md-6">
										<input class="form-control keyword" name="keyword" type="text" placeholder="请输入企业/个人名称搜索" value="${keyword }" />
									</div>
								</div>
								<input type="hidden" name="page" value="${page }" />
							</form>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>ID</th>
											<th>企业编号</th>
											<th>用户类型</th>

											<th>企业名称</th>
											<th>企业简介</th>
											<th>企业背景</th>
											<th>营业范围</th>
											<th>经营状况</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="enterprise" items="${list }">
											<tr>
												<td>${enterprise.id }</td>
												<td>${enterprise.no }</td>
												<td>
													<c:if test="${enterprise.type ==0 }">
														<span class="label label-success">企业用户</span>
													</c:if>
													<c:if test="${enterprise.type ==1 }">
														<span class="label label-warning">个人用户</span>
													</c:if>
												</td>
												<td>${enterprise.name }</td>
												<td class=" over">${enterprise.intro }</td>
												<td class="over">${enterprise.background }</td>
												<td class=" over">${enterprise.scope }</td>
												<td class="over">${enterprise.conditionState }</td>
												<td>
													<shiro:hasPermission name="enterprise:edit">
														<a class="edit-row" href="edit?id=${enterprise.id }">编辑</a>
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
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script type="text/javascript">
		function search() {
			$("#form").submit();
		};

		$(function() {
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
					return "list?keyword=${keyword}&page=" + page;
				}
			});
		});
	</script>
</body>
</html>