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
<title>商品列表</title>
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
				<h1>商城分类列表</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							分类列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />
							 
							
					
							<shiro:hasPermission name="shop:addCategory">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="addCategory" id="add-row">
									<i class="icon-plus"></i>
									添加分类
								</a>
							</shiro:hasPermission>	&nbsp; 
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class=" search1 AppFixed  form-inline  col-lg-4 pull-right col-xs-11" id="form" action="listCategory">
									<div class="row">
										<div class="form-group col-md-6">
										</div>
									</div>
									
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control keyword" name="categoryName" type="text" placeholder="请输入分类名称" value="${categoryName }" id="categoryName" />
										</div>
										<input type="hidden" name="page" value="${page }" />
									</div>

									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th>分类名称</th>
											<th>banner图</th>
											<!-- <th>logo图</th> -->
											<th>创建时间</th>
											<th>修改时间</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>${g.id}</td>
												<td>${g.categoryName}</td>
												<td><img alt="" style="width: 180px; height: 70px;" src="${g.adUploadPath }"></td>
												<%-- <td><img alt="" style="width: 180px; height: 70px;" src="${g.logoUploadPath }"></td> --%>
												<td>
													<fmt:formatDate value="${g.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<fmt:formatDate value="${g.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<shiro:hasPermission name="shop:goodsEdit">
														<a class="edit-row" href="editCategory?categoryId=${g.id }">编辑</a>
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
			$('#pagination').bootstrapPaginator({
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "listCategory?categoryName=${categoryName}&page=" + page;
				}
			});

		});
	 
	</script>
</body>
</html>