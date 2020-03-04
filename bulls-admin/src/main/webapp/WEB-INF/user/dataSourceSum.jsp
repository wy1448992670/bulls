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
<title>数据来源</title>
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
				<h1>数据来源汇总</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							数据来源汇总

							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />

							<button class="btn btn-primary pull-right  hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline col-lg-4 pull-right col-xs-11" id="form" action="dataSourceSum">
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${userCreateDateStart }" name="userCreateDateStart" id="userCreateDateStart" type="text" placeholder="请选择用户注册起始时间" />
										</div>
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${userCreateDateEnd }" name="userCreateDateEnd" id="userCreateDateEnd" type="text" placeholder="请选择用户注册结束时间" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="departmentId" id="departmentId">
											<option value="">请选择部门</option>
											<c:forEach var="department" items="${departments}">
												<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
											</c:forEach>
										</select>
									</div>
									<input type="hidden" name="page" value="1" />
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="200">数据来源</th>
											<th>设备</th>
											<th>注册人数</th>
											<th>首投用户</th>
											<th>复投用户</th>
											<th>首投金额</th>
											<th>总投资金额</th>
											<th>首投转化率</th>
											<th>复投转化率</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list}" varStatus="v">
											<tr>
												<td>${g.name}</td>
												<td>${g.client}</td>
												<td align="right"><fmt:formatNumber value="${g.registCount}"  pattern="#,###" /></td>
												<td align="right"><fmt:formatNumber value="${g.firstCount}"  pattern="#,###" /></td>
												<td align="right"><fmt:formatNumber value="${g.repeatCount}"  pattern="#,###" /></td>
												<td align="right"><fmt:formatNumber value="${g.firstAmount}"  pattern="#,##0.00" /></td>
												<td align="right"><fmt:formatNumber value="${g.repeatAmount}"  pattern="#,##0.00" /></td>
												<td align="right">
													<c:choose>
														<c:when test="${g.registCount==0}">
															暂无数据
														</c:when>
														<c:otherwise>
															<fmt:formatNumber value="${g.firstCount/g.registCount}"
																pattern="#0.00%" type="number" />
														</c:otherwise>
													</c:choose>
												</td>
												<td align="right">
													<c:choose>
														<c:when test="${g.firstCount==0}">
															暂无数据
														</c:when>
														<c:otherwise>
															<fmt:formatNumber value="${g.repeatCount/g.firstCount}"
																pattern="#0.00%" type="number" />
														</c:otherwise>
													</c:choose>
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


		})
	</script>
</body>
</html>
