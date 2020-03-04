<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.goochou.p2b.constant.Constants" %>
<%@ page import="com.goochou.p2b.constant.TestEnum" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String httpScheme="https";
    if(TestEnum.DEBUG.getFeatureName().equals(Constants.TEST_SWITCH)){
    	httpScheme = "http";
    }
	request.setAttribute("scheme", httpScheme+ "://");
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>渠道列表</title>
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
				<h1>渠道列表</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							渠道列表
							<shiro:hasPermission name="banner:add">
								<a class="btn btn-sm btn-primary-outline pull-right"
								   href="${basePath}advertisementChannel/add" id="add-row"><i class="icon-plus"></i>添加推广渠道</a>
							</shiro:hasPermission>
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />

							<button class="btn btn-primary pull-right  hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline col-lg-4 pull-right col-xs-11" id="form" action="list">
									<div class="row">
										<div class="form-group col-md-6 ">
											<select class="select2able" name="status" id="status">
												<option value="">全部</option>
												<option value="0" <c:if test="${status == 0 }">selected</c:if>>关闭</option>
												<option value="1" <c:if test="${status == 1 }">selected</c:if>>正常</option>
											</select>
										</div>
										<div class="form-group col-md-6 ">
											<select class="select2able" name="channelType" id="channelType">
												<option value="">请选择渠道类型</option>
												<option value="1" <c:if test="${channelType == 1 }">selected</c:if>>cps</option>
												<option value="2" <c:if test="${channelType == 2 }">selected</c:if>>cpc</option>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6 ">
											<input class="form-control keyword" name="channelNo" type="text" placeholder="渠道号" value="${channelNo }" id="channelNo" />
										</div>
										<div class="form-group col-md-6 ">
											<input class="form-control keyword" name="channelName" type="text" placeholder="渠道名称" value="${channelName }" id="channelName" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${startDate }" name="startDate" id="startDate" type="text" placeholder="请选择申请起始时间" />
										</div>
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${endDate }" name="endDate" id="endDate" type="text" placeholder="请选择申请结束时间" />
										</div>
									</div>

									<input type="hidden" name="page" value="1" />
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">编号</th>
											<th width="200">渠道类型</th>
											<th>渠道号</th>
											<th>渠道名称</th>
											<th>添加时间 </th>
											<th>链接</th>
											<th>banner图</th>
											<th>状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list}" varStatus="v">
											<tr>
												<td>${g.id }</td>
												<td>
													<c:if test="${g.channelType==1}"><span class="label label-info">cps</span></c:if>
													<c:if test="${g.channelType==2}"><span class="label label-warning">cpc</span></c:if>
												</td>
												<td>${g.channelNo}</td>
												<td>${g.channelName}</td>
												<td><fmt:formatDate value="${g.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
												<td><a href="${scheme}${g.url}" target="_blank" >${scheme}${g.url}</a></td>
												<td ><img style="width: 250px; height: 130px" src="${g.topImagePath}" /></td>
												<td>
													<c:if test="${g.status==0}"><span class="label label-default">关闭</span></c:if>
													<c:if test="${g.status==1}"><span class="label label-success">正常</span></c:if>
												</td>
												<td>
													<shiro:hasPermission name="advertisement:detail"><a href="detail?id=${g.id}&type=0">详情</a></shiro:hasPermission>
													<shiro:hasPermission name="advertisement:edit"><a href="detail?id=${g.id}&type=1">修改</a></shiro:hasPermission>
													<shiro:hasPermission name="advertisement:update">
														<c:if test="${g.status == 0 }">
															<a class="delete-row"
																href="${basePath}advertisementChannel/update?id=${g.id }&status=1"
																onClick="return confirm('确定要开启此渠道吗？');" id="update">开启</a>
														</c:if>
														<c:if test="${g.status == 1 }">
															<a class="delete-row"
																href="${basePath}advertisementChannel/update?id=${g.id }&status=0"
																onClick="return confirm('确定要关闭此渠道吗？');" id="update">关闭</a>
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
					return "list?channelNo=${channelNo}&channelName=${channelName}&channelType=${channelType}&startDate=${startDate}&endDate=${endDate}&status=${status}&page=" + page;
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
