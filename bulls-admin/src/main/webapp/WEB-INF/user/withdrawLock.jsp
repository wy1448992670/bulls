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
<title>提现解锁管理</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all"
	rel="stylesheet" type="text/css" />
<style type="text/css">
.table {
	table-layout: fixed;
}

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
				<h1>提现解锁管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>提现解锁管理
						</div>
						<div class="widget-content padded clearfix">
							<form class="form-inline hidden-xs col-lg-3 pull-right" id="form"
								action="withdrawLock">
								<div class="row">
									<div class="form-group col-md-9">
										<input class="form-control keyword" name="likeSearch"
											type="text" placeholder="请输入用户姓名或电话号码" value="${likeSearch }" />
									</div>
									<div class="form-group col-md-3">
										<button class="btn btn-primary pull-right hidden-xs"
											type="button" onclick="aa()">搜索</button>
									</div>

								</div>
							</form>
							<table class="table table-bordered table-hover">
								<thead>
									<tr>

										<th>用户id</th>
										<th>真名</th>
										<th>手机号</th>
										<th>金额</th>
										<th>锁定时间</th>
										<th>锁定原因</th>
										<th>注册时间</th>
										<shiro:lacksPermission name="user:withdrawLock">
											<th width="60">操作</th>
										</shiro:lacksPermission>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="b" items="${list }" varStatus="status">
										<tr>
											<td>${b.id}</td>
											<td>${b.trueName }</td>
											<td><shiro:lacksPermission name="user:adminPhone">
                                            ${b.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission> <shiro:hasPermission
													name="user:adminPhone">
                                            ${b.phone }
                                        </shiro:hasPermission></td>
											<td>${b.balance_amount }</td>
											<td><fmt:formatDate value="${b.lockTime}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${b.reason}</td>
											<td><fmt:formatDate value="${b.create_date}"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>

											<shiro:lacksPermission name="user:withdrawLock">
												<td><a
														href="${basePath}user/withdrawLockDelete?id=${b.id }"
														onClick="return confirm('确定删除?');">删除</a></td>
											</shiro:lacksPermission>
										</tr>
									</c:forEach>
								</tbody>
							</table>
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
		function aa() {
			$("#form").submit();
		};
		$(function() {
			$('#pagination').bootstrapPaginator({
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "withdrawLock?page=" + page
				}
			});
		});
	</script>
</body>
</html>
