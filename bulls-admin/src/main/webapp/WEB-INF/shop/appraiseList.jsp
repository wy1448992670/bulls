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
<title>商品后台评价列表</title>
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
				<h1>商品后台评价列表</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							后台评价列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />
							&nbsp;
							<shiro:hasPermission name="shop:addAppraise">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="addAppraise?id=${goodId }" id="add-row">
									<i class="icon-plus"></i>
									添加评价
								</a>
							</shiro:hasPermission>
							<a class="btn btn-primary pull-right hidden-xs" onclick="javascript:window.history.go(-1);">取消</a>
						</div>

							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th>评价时间</th>
											<th>评价内容</th>
											<th>评价图片</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>${g.id }</td>
												<td><fmt:formatDate value="${g.createDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td>${g.content }</td>
												<td>
													<c:forEach var="img" items="${g.assessImgs}">
														<a href="${img.upload.cdnPath}" target="_blank" > <img src="${img.upload.cdnPath}" height="80px" width="80px"> </a>
													</c:forEach>
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
			$('#pagination').bootstrapPaginator({
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "appraiseList?goodId=${goodId}&page=" + page;
				}
			});

		});
	</script>
</body>
</html>