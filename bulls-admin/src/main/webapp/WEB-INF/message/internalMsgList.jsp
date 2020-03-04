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
<title>站内信管理</title>
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
				<h1>站内信管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>站内信管理
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
							<shiro:hasPermission name="send:internalMsg">
								<a class="btn btn-sm btn-primary-outline pull-right"
									href="${basePath}sms/send/internal/msg"><i
									class="icon-edit"></i>发送站内信</a>
							</shiro:hasPermission>
						</div>
						<div class="widget-content padded clearfix">
						<div id="myModal">
							<form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form"
								action="${basePath}sms/internal/msg/list">
								<div class="row">
									<div class="form-group col-md-9">
										<input name="keyword" type="text"
											placeholder="请输入消息标题、用户名、真名、手机号进行搜索"
											class="form-control keyword" value="${keyword }" />
									</div>
									<div class="form-group col-md-3">
										<button class="btn btn-primary pull-right hidden-xs"
								type="button" onclick="bb()">搜索</button>
									</div>
								</div>
								<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="bb()">搜索</button>
								<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								<input type="hidden" name="page" value="1" />
							</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>ID</th>
											<th>标题</th>
											<th>内容</th>
											<th>用户名</th>
											<th>真名</th>
											<th>手机</th>
											<th>发送时间</th>
											<th>接收时间</th>
											<th>阅读时间</th>
											<th>状态</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td>${i.id}</td>
												<td>${i.title}</td>
												<td>${i.content}</td>
												<td>${i.username}</td>
												<td>${i.trueName}</td>
												<td><shiro:lacksPermission name="user:adminPhone">
                                                ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                            </shiro:lacksPermission> <shiro:hasPermission
														name="user:adminPhone">
                                                ${i.phone }
                                            </shiro:hasPermission></td>
												<td><fmt:formatDate value="${i.createTime}"
														pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
												<td><fmt:formatDate value="${i.receiverTime}"
														pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
												<td><fmt:formatDate value="${i.readTime}"
														pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></td>
												<td><c:if test="${i.status == 0}">
														<span class="label label-warning">未读</span>
													</c:if> <c:if test="${i.status == 1}">
														<span class="label label-success">已读</span>
													</c:if> <c:if test="${i.status == 2}">
														<span class="label label-danger">已删除</span>
													</c:if></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>

						</div>
						<ul id="pagination" style="float: right"></ul>
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
		$(function() {
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
									return "${basePath}sms/internal/msg/list?keyword=${keyword}&page="
											+ page;
								}
							});
		});
		function bb() {
			$("#form").submit();
		}
	</script>
</body>
</html>