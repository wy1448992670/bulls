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
<title>Icon分组查询</title>
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
							<i class="icon-table"></i>Icon分组查询
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline col-lg-4 pull-right col-xs-11" id="form"
									action="groups">
									<div class="row">
										<div class="form-group col-md-6"></div>
										<div class="form-group col-md-9  pull-right">
											<input type="hidden" name="page" value="${page }" /> <a
												class="btn btn-sm btn-primary-outline hidden-xs  pull-right"
												href="${basePath}icon/add?page=${page}" id="add-row"><i
												class="icon-plus"></i>添加ICON</a> <a
												class="btn btn-sm btn-primary-outline pull-right hidden-xs"
												href="${basePath}icon/add/group"><i class="icon-plus"></i>添加分组</a>
											<button class="btn btn-primary pull-right hidden-xs  pull-right"
												type="button" onclick="aa()">搜索</button>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-5"></div>
										<div class="form-group col-md-4">
											<select class="select2able" name="type">
												<option value=""
													<c:if test="${type == null }">selected</c:if>>选择类型</option>
												<option value="0" <c:if test="${type == 0 }">selected</c:if>>首页</option>
												<option value="1" <c:if test="${type == 1 }">selected</c:if>>个人主页</option>
												<option value="2" <c:if test="${type == 2 }">selected</c:if>>tabbarIcon</option>
												<option value="3" <c:if test="${type == 3 }">selected</c:if>>tabbarIconGray</option>
												<option value="4" <c:if test="${type == 4 }">selected</c:if>>账户中心</option>
												<option value="5" <c:if test="${type == 5 }">selected</c:if>>关于我们</option>
												<option value="6" <c:if test="${type == 6 }">selected</c:if>>商城导航</option>
												<option value="7" <c:if test="${type == 7 }">selected</c:if>>商城首页-ICON</option>
												<option value="8" <c:if test="${type == 8 }">selected</c:if>>商城首页-活动</option>
											</select>
										</div>
										<div class="form-group col-md-3">
											<select class="select2able" name="status">
												<option value=""
													<c:if test="${status == null }">selected</c:if>>选择状态</option>
												<option value="0"
													<c:if test="${status == 0 }">selected</c:if>>未启用</option>
												<option value="1"
													<c:if test="${status == 1 }">selected</c:if>>启用</option>
											</select>
										</div>
									</div>
	
									<div class="row">
										<div class="form-group col-md-2"></div>
										<div class="form-group col-md-5">
											<input class="form-control keyword" id="title" name="title"
												type="text" placeholder="请输入标题搜索" value="${title }" />
										</div>
										<div class="form-group col-md-5">
											<input class="form-control keyword" id="version"
												name="version" type="text" placeholder="请输入版本号搜索"
												value="${version }" />
										</div>
									</div>
									<input type="hidden" name="page" value="${page }" />
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="aa()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="widget-content padded clearfix">
								<div class="table-responsive">
									<table class="table table-bordered table-hover">
										<thead>
											<tr>
												<th width="50">ID</th>
												<th>标题</th>
												<th width="650">icon</th>
												<th>类型</th>
												<th>对应版本</th>
												<th>状态</th>
												<th></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="i" items="${list }">
												<tr>
													<td>${i.id }</td>
													<td>${i.title }</td>
													<td><c:forEach var="ii" items="${i.pathList }">
															<div style="display: inline-block; margin-right: 10px;">
																<a target="_blank" href="${basePath}icon/add?id=${ii.id}">
																	<img alt="" src="${aPath }upload/${ii.path}" width="60" />
																</a>
	
																<p style="margin: 0; text-align: center;">${ii.title }</p>
															</div>
														</c:forEach></td>
													<td><c:choose>
															<c:when test="${i.type==0 }">首页</c:when>
															<c:when test="${i.type==1 }">个人主页</c:when>
															<c:when test="${i.type==2 }">tabbarIcon</c:when>
															<c:when test="${i.type==3 }">tabbarIconGray</c:when>
															<c:when test="${i.type==4 }">账户中心</c:when>
															<c:when test="${i.type==5 }">关于我们</c:when>
															<c:when test="${i.type==6 }">商城导航</c:when>
															<c:when test="${i.type==7 }">商城首页-ICON</c:when>
															<c:when test="${i.type==8 }">商城首页-活动</c:when>
															<c:otherwise>--</c:otherwise>
														</c:choose></td>
													<td>${i.version }</td>
													<td><c:if test="${i.status==0 }">
															<span class="label label-danger">未启用</span>
														</c:if> <c:if test="${i.status==1 }">
															<span class="label label-success">启用</span>
														</c:if></td>
													<td width="220"><c:if test="${i.status==0 }">
															<a href="${basePath }icon/update?groupId=${i.id}&status=1"
																class="btn btn-primary">启用</a>
														</c:if> <c:if test="${i.status==1 }">
															<a href="${basePath }icon/update?groupId=${i.id}&status=0">弃用</a>
														</c:if> &nbsp;&nbsp; <a
														href="${basePath}icon/add/group?groupId=${i.id}"
														class="btn btn-primary">编辑</a></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<ul id="pagination" style="float: right"></ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end DataTables Example -->
			</div>
		</div>
		<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
		<script src="${basePath}js/select2.js" type="text/javascript"></script>
		<script src="${basePath}js/comm.js" type="text/javascript"></script>
		<script type="text/javascript">
			function aa() {
				$("#form").submit();
			}
			$(function() {
				$('.select2able').select2({
					width : ""
				});

				$('#pagination')
						.bootstrapPaginator(
								{
									currentPage : parseInt('${page}'),
									totalPages : parseInt('${pages}'),
									bootstrapMajorVersion : 3,
									alignment : "right",
									pageUrl : function(type, page, current) {
										return "list?status=${status}&type=${type}&page="
												+ page;
									}
								});
			});
		</script>
</body>
</html>