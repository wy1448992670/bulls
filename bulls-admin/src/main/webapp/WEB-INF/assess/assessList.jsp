<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>评价列表</title>
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
				<h1>商城管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							评价列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />

							<button class="btn btn-primary pull-right  hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline col-lg-4 pull-right col-xs-11" id="form" action="assessList">
									<input type="hidden" name="desc" id="desc" value="${desc}" />
									<div class="row">
										<div class="form-group col-md-6  ">
											<select class="select2able" name="replyStatus" id="replyStatus">
												<option value="-1">所有</option>
												<option value="0" <c:if test="${replyStatus == 0 }">selected</c:if>>未回复</option>
												<option value="1" <c:if test="${replyStatus == 1 }">selected</c:if>>已回复</option>
											</select>
										</div>
										<div class="form-group col-md-6 ">
											<input class="form-control keyword" name="keyword" type="text" placeholder="商品名称，用户名称的模糊查询" value="${keyword }" id="keyword" />
										</div>
										<input type="hidden" name="page" value="${page }" />
									</div>



									<shiro:hasPermission name="user:export:app">
										<div class="row"></div>
									</shiro:hasPermission>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th width="200">商品名称</th>
											<th>姓名</th>
											<th>买家评价内容</th>
											<th>
												<font style="color: red; font-weight: bold;">卖家最后回复</font>
											</th>
											<th>是否匿名</th>
											<th>是否置顶</th>
											<th>是否删除</th>
											<th>是否禁言</th>
											<th>创建时间</th>
											<th>操作</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>${g.id}</td>
												<td>${g.goods_name}</td>
												<td>${g.true_name }</td>
												<c:choose>
													<c:when test="${fn:length(g.content)>=20 }">
														<td title="具体请点击详情查看">${fn:substring(g.content,0,20)}.....</td>
													</c:when>
													<c:otherwise>
														<td>${g.content}</td>
													</c:otherwise>
												</c:choose>
												<td>
													<font style="color: red; font-weight: bold;">${g.last_reply}</font>
												</td>
												<td>
													<c:if test="${g.is_anonymous==0}">否</c:if>
													<c:if test="${g.is_anonymous==1}">是</c:if>
												</td>
												<td>
													<c:if test="${g.is_top==0}">否</c:if>
													<c:if test="${g.is_top==1}">是</c:if>
												</td>
												<td>
													<c:if test="${g.state==0}">否</c:if>
													<c:if test="${g.state==1}">是</c:if>
												</td>
												<td>
													<c:if test="${g.is_forbid_comment==0 or  empty g.is_forbid_comment}">否</c:if>
													<c:if test="${g.is_forbid_comment==1}">是</c:if>
												</td>
												<td>
													<fmt:formatDate value="${g.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<shiro:hasPermission name="shop:assessDetail">
														<a class="delete-row" href="assessDetail?goodsId=${g.goods_id }&userId=${g.user_id}&orderId=${g.order_id}&parentId=${g.id}" id="assessDetail">详细</a>
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
			//	width : "150"
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
					return "assessList?goodsName=${goodsName}&trueName=${trueName}&page=" + page;
				}
			});

			function report() {
				var startTime = $.trim($('#startTime').val());
				var endTime = $.trim($('#endTime').val());
				var keyword = $.trim($('#keyword').val());
				window.location.href = "${basePath}report/paymentExcel?startTime=" + startTime + "&endTime=" + endTime
						+ "&keyword=" + keyword;
			}

		})
	</script>
</body>
</html>