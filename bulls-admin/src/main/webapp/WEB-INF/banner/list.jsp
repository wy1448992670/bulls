<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Banner列表</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all"
	rel="stylesheet" type="text/css" />
<style type="text/css">

.table .over {
	overflow: hidden;
	width: 40%;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.spinner {
	width: 100px;
}

.spinner input {
	text-align: right;
}

.input-group-btn-vertical {
	position: relative;
	white-space: nowrap;
	width: 1%;
	vertical-align: middle;
	display: table-cell;
}

.input-group-btn-vertical>.btn {
	display: block;
	float: none;
	width: 100%;
	max-width: 100%;
	padding: 8px;
	margin-left: -1px;
	position: relative;
	border-radius: 0;
}

.input-group-btn-vertical>.btn:first-child {
	border-top-right-radius: 4px;
}

.input-group-btn-vertical>.btn:last-child {
	margin-top: -2px;
	border-bottom-right-radius: 4px;
}

.input-group-btn-vertical i {
	position: absolute;
	top: 0;
	left: 4px;
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
				<h1>运营管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>Banner列表
							<shiro:hasPermission name="banner:add">
								<a class="btn btn-sm btn-primary-outline pull-right"
									href="${basePath}banner/add" id="add-row"><i
									class="icon-plus"></i>添加Banner</a>
							</shiro:hasPermission>
						</div>
						<div class="widget-content padded clearfix">
							<form class="form-inline col-lg-5 pull-right" id="form"
								action="list">
								<div class="row">
									<div class="form-group col-md-3"></div>
									<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="status" id="status">
											<option value="">请选择Banner状态</option>
											<option value="0" <c:if test="${status == 0 }">selected</c:if>>启用</option>
											<option value="1" <c:if test="${status == 1 }">selected</c:if>>未启用</option>
										</select>
									</div>
									<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="type" id="type">
											<option value="">请选择类型</option>
											<option value="0" <c:if test="${type == 0 }">selected</c:if>>APP</option>
											<option value="1" <c:if test="${type == 1 }">selected</c:if>>PC</option>
										</select>
									</div>
									<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="source" id="source">
											<option value="">请选择banner归属</option>
											<option value="0" <c:if test="${source == 0 }">selected</c:if>>首页</option>
											<option value="1" <c:if test="${source == 1 }">selected</c:if>>首页-活动区</option>
											<option value="2" <c:if test="${source == 2 }">selected</c:if>>商城-首页</option>
											<option value="3" <c:if test="${source == 3 }">selected</c:if>>首页-开机广告</option>
											<option value="4" <c:if test="${source == 4 }">selected</c:if>>首页-下区</option>
										</select>
									</div>
								</div>
								<input type="hidden" name="page" value="${page }" />
							</form>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>主题</th>
											<th>地址</th>
											<th>创建时间</th>
											<th>排序位置</th>
											<th>确认排序</th>
											<th>终端类型</th>
											<th>所属页</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="b" items="${bannerList }">
											<tr>
												<td>${b.name }</td>
												<td style="word-wrap: break-word;">${b.link }</td>
												<td><fmt:formatDate value="${b.createTime }"
														pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<form action="${basePath}banner/updateSeniority" method="get">
													<td>
														<div class="input-group spinner">
															<input type="text" class="form-control"
																value="${b.seniority}" name="seniority">
	
															<div class="input-group-btn-vertical">
																<button class="btn btn-default" type="button">
																	<i class="glyphicon glyphicon-circle-arrow-up"></i>
																</button>
																<button class="btn btn-default" type="button">
																	<i class="glyphicon glyphicon-circle-arrow-down"></i>
																</button>
															</div>
														</div>
													</td>
													<td><input type="hidden" name="id" value="${b.id}">
														<input type="hidden" name="type" value="${type}"> <input
														type="hidden" name="status" value="${status}">
														<button type="submit" class="btn btn-primary btn-sm">
															确认</button></td>
												</form>
												<td><c:if test="${b.type== 0 }">
														<span class="label label-info">APP</span>
													</c:if> <c:if test="${b.type == 1 }">
														<span class="label label-danger">PC</span>
													</c:if></td>
												<td><c:if test="${b.source== 0 }">
														<span class="label label-warning">首页</span>
													</c:if>
													<c:if test="${b.source== 1 }">
														<span class="label label-warning">首页-活动区</span>
													</c:if>
													<c:if test="${b.source== 2 }">
														<span class="label label-primary">商城-首页</span>
													</c:if>
													<c:if test="${b.source== 3 }">
														<span class="label label-primary">首页-开机广告</span>
													</c:if>
													<c:if test="${b.source== 4 }">
														<span class="label label-primary">首页-下区</span>
													</c:if></td>
												<td><c:if test="${b.status == 0 }">
														<span class="label label-success">启用</span>
													</c:if> <c:if test="${b.status == 1 }">
														<span class="label label-default">未启用</span>
													</c:if></td>
												<td>
													<shiro:hasPermission name="banner:detail">
														<a class="delete-row"
															href="${basePath}banner/detail?id=${b.id }&type=${type}&status=${status}&source=${source}"
															id="detail">编辑</a>
													</shiro:hasPermission>
													
													<shiro:hasPermission name="banner:update">
														<c:if test="${b.status == 0 }">
															<a class="delete-row"
																href="${basePath}banner/update?id=${b.id }&status=1"
																onClick="return confirm('确定要弃用此BANNER吗？');" id="delete">弃用</a>
														</c:if> 
														<c:if test="${b.status == 1 }">
															<a class="delete-row"
																href="${basePath}banner/update?id=${b.id }&status=0"
																onClick="return confirm('确定要启用此BANNER吗？');" id="update">启用</a>
														</c:if>
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
	<script type="text/javascript"
		src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$('.select2able').select2({
				width : ""
			});
			$(".select2able").change(function() {
				$("#form").submit();
			});

			$('#pagination')
					.bootstrapPaginator(
							{
								currentPage : parseInt('${page}'),
								totalPages : parseInt('${pages}'),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current) {
									return "list?status=${status}&type=${type}&source=${source}&page="
											+ page
								}
							});
		});
		(function($) {
			$('.spinner .btn:first-of-type').on(
					'click',
					function() {
						//            $('.spinner input').val( parseInt($('.spinner input').val(), 10) + 1);\
						var parent = $(this).parents(".spinner");
						parent.find("input").val(
								parseInt(parent.find("input").val(), 10) + 1);
					});
			$('.spinner .btn:last-of-type').on(
					'click',
					function() {
						//            $('.spinner input').val( parseInt($('.spinner input').val(), 10) - 1);
						var parent = $(this).parents(".spinner");
						if (parent.find("input").val() <= 0) {
							alert("不能小于零");
							return;
						}
						parent.find("input").val(
								parseInt(parent.find("input").val(), 10) - 1);
					});
		})(jQuery);
	</script>
</body>
</html>
