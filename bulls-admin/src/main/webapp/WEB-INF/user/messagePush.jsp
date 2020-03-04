<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>推送管理</title>
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
				<h1>运营管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>推送管理
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
							<shiro:hasPermission name="user:toEditMessagePush">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
									href="toEditMessagePush" id="add-row"><i class="icon-plus"></i>添加推送</a>
							</shiro:hasPermission>
							
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search();">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline col-lg-3 pull-right col-xs-11" id="form" action="messagePush">
									<div class="row">
										<div class="form-group col-md-2"></div>
										<div class="form-group col-md-6">
											<input class="form-control keyword" name="keyword"
												type="text" placeholder="输入关键词搜索" value="${keyword }" />
										</div>
										<div class="form-group col-md-4">
											<select class="select2able" name="status">
												<option value=""
													<c:if test="${status == null }">selected</c:if>>所有</option>
												<option value="0"
													<c:if test="${status == 0 }">selected</c:if>>未推送</option>
												<option value="1"
													<c:if test="${status == 1 }">selected</c:if>>已推送</option>
											</select>
										</div>
									</div>
									<input type="hidden" name="page" value="1" />
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
								<div class="table-responsive">
									<table class="table table-bordered table-hover">
										<thead>
											<tr>
		
												<th>序号</th>
												<th>标题</th>
												<th>推送内容</th>
												<th>发送时间</th>
												<th>跳转URL</th>
												<th>推送状态</th>
												<th>安卓推送数</th>
												<th>IOS推送数</th>
												<th>创建时间</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="i" items="${list}" varStatus="s">
												<tr>
													<td>${i.id }</td>
													<td>${i.title}</td>
													<td>${i.content}</td>
													<td><fmt:formatDate value="${i.sendTime}"
															pattern="yyyy-MM-dd HH:mm:ss" /></td>
													<td>${i.url}</td>
													<td><c:if test="${i.status==0}">
		                                        	未推送
		                                        	</c:if> <c:if test="${i.status==1}">
		                                        	已推送
		                                        	</c:if></td>
													<td>${i.androidSuccess}</td>
													<td>${i.iosSuccess}</td>
													<td><fmt:formatDate value="${i.createDate}"
															pattern="yyyy-MM-dd HH:mm:ss" /></td>
													<%-- <td> 
															<a class="edit-row" href="toEditMessagePush?id=${i.id}">查询</a>
														  <c:if test="${i.status==0}">
															<a class="edit-row" href="toEditMessagePush?id=${i.id}">编辑</a>
														</c:if></td> --%>
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
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>
	<script type="text/javascript"
		src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script type="text/javascript">
		function search(){
			$("#form").submit();
		}
		
		$(function() {

			$('.select2able').select2({
				width : "145"
			});

			$('#pagination')
					.bootstrapPaginator(
							{
								currentPage : parseInt('${page}'),
								totalPages : parseInt('${pages}'),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current) {
									return "messagePush?keyword=${keyword}&status=${status}&page="
											+ page;
								}
							});
		});
	</script>
</body>
</html>