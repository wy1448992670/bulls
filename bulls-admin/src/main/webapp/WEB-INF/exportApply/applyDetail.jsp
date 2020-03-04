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
<title>导出申请查询</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">


.table .over {
	overflow: hidden;
	width: 40%;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.numberLine {
	word-break: break-all;
	word-wrap: break-word;
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
				<h1>导出申请</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							导出申请详情
						</div>
						<div class="widget-content padded clearfix">
							<form action="" class="form-horizontal" id="validate-form">
								<c:if test="${path != null}">
									<div class="form-group">
										<label class="control-label col-md-2"></label>
										<div class="col-md-7">
											<p class="form-control-static">
												<img alt="" src="${aPath}upload/${path }" width="300px;" />
											</p>
										</div>
									</div>
								</c:if>

								<div class="form-group">
									<label class="control-label col-md-2">申请ID:</label>
									<div class="col-md-7">
										<p class="form-control-static">${apply.id }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">目标列表:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${apply.targetList == 0 }">网站用户列表</c:if>
										</p>
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">申请原因:</label>
									<div class="col-md-7">
										<p class="form-control-static">${apply.applyReason }</p>
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">申请时间:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${apply.applyTime }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">审核状态:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${apply.applyStatus == 0 }">
												<span class="label label-info">申请中</span>
											</c:if>
											<c:if test="${apply.applyStatus == 1 }">
												<span class="label label-success">通过</span>
											</c:if>
											<c:if test="${apply.applyStatus == 2 }">
												<span class="label label-danger">拒绝</span>
											</c:if>
										</p>
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">审核时间:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${apply.auditTime }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">审核说明:</label>
									<div class="col-md-7">
										<p class="form-control-static">${apply.auditRemark }</p>
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">过期时间:</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${apply.expireTime }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">返回</a>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>

				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							筛选条件
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered" id="investors">
									<thead>
										<tr>
											<th>序号</th>
											<th>条件描述</th>
											<th>条件值</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" varStatus="loop" items="${listCondition }">
											<tr>
												<td>${loop.index+1 }</td>
												<td>${i.propertyName }</td>
												<td>${i.valueName }</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="text-right">
								<ul id="pagination"></ul>
							</div>
						</div>
					</div>
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							导出列表
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered" id="investors">
									<thead>
										<tr>
											<th>序号</th>
											<th>列表名称</th>
											<th>是否加密</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" varStatus="loop" items="${listColumns }">
											<tr>
												<td>${loop.index+1 }</td>
												<td>${i.colName }</td>
												<td>
													<c:if test="${i.isEncrypt }">加密</c:if>
													<c:if test="${!i.isEncrypt }">不加密</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div class="text-right">
								<ul id="pagination"></ul>
							</div>
						</div>
					</div>
					<c:if test="${info.expressNum != null }">
						<div>
							<iframe style="width: 100%; height: 100%; border: 0;" src="https://m.kuaidi100.com/result.jsp?nu=${info.expressNum }"></iframe>
						</div>
					</c:if>
				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script type="text/javascript">
		
	</script>
</body>
</html>