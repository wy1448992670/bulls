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
<title>新闻列表</title>
<link href="${basePath}css/font-awesome.css" media="all"
	rel="stylesheet" type="text/css" />
<link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
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
							<i class="icon-table"></i>新闻列表
							<shiro:hasPermission name="news:add">
								<a class="btn btn-sm btn-primary-outline pull-right" href="add"
									id="add-row"><i class="icon-plus"></i>发布新闻</a>
							</shiro:hasPermission>
						</div>
						<div class="widget-content padded clearfix">
							<form class="form-inline col-lg-5 pull-right" id="form"
								action="list">
								<div class="row">
									<div class="form-group col-md-3"></div>
									<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="type">
											<option value=""
												<c:if test="${type == null }">selected</c:if>>全部</option>
											<option value="1" <c:if test="${type == 1 }">selected</c:if>>媒体报道</option>
											<option value="2" <c:if test="${type == 2 }">selected</c:if>>公司动态</option>
										</select>
									</div>
									<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="status">
											<option value=""
												<c:if test="${status == null }">selected</c:if>>是否发布</option>
											<option value="0"
												<c:if test="${status == 0 }">selected</c:if>>草稿</option>
											<option value="1"
												<c:if test="${status == 1 }">selected</c:if>>已发布</option>
										</select>
									</div>
									<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="isTop">
											<option value=""
												<c:if test="${isTop == null }">selected</c:if>>是否置顶</option>
											<option value="0" <c:if test="${isTop == 0 }">selected</c:if>>否</option>
											<option value="1" <c:if test="${isTop == 1 }">selected</c:if>>是</option>
										</select>
									</div>
								</div>
							</form>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>ID</th>
											<th>类型</th>
											<th>标题</th>
											<th>新闻来源</th>
											<th>创建时间</th>
											<th>发布时间</th>
											<th>状态</th>
											<th>是否置顶</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>${g.id }</td>
												<td><c:if test="${g.type==1}">
	                                            媒体报道
	                                        </c:if> <c:if test="${g.type==2}">
	                                            公司动态
	                                        </c:if></td>
												<td
													style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
													${g.title }</td>
												<td
													style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">
													${g.source }</td>
												<td><fmt:formatDate value="${g.createDate }"
														pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td><fmt:formatDate value="${g.releaseDate }"
														pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td><c:if test="${g.status==0}">
	                                            草稿
	                                        </c:if> <c:if test="${g.status==1}">
	                                            已发布
	                                        </c:if></td>
												<td><c:if test="${g.isTop==0}">
	                                            否
	                                        </c:if> <c:if test="${g.isTop==1}">
	                                            置顶
	                                        </c:if></td>
												<td>
													<a class="btn btn-primary" target="_blank" href="${newsUrl}${g.id }" id="detail">查看</a>
													
													<shiro:hasPermission name="news:edit">
														<a class="btn btn-info" href="edit?id=${g.id }">编辑</a>
													</shiro:hasPermission>
													
													<shiro:hasPermission name="news:delete">
														<a class="btn btn-warning" href="delete?id=${g.id }" id="delete">删除</a>
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
			$('#pagination').bootstrapPaginator({
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "list?page=" + page;
				}
			});
		});
	</script>
</body>
</html>