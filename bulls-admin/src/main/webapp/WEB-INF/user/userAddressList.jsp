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
<title>用户地址管理</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all"
	rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="modal-shiftfix">
		<!-- Navigation -->
		<jsp:include page="../common/header.jsp"></jsp:include>
		<!-- End Navigation -->
		<div class="container-fluid main-content">
			<div class="page-title">
				<h1>用户地址管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>用户地址明细
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
						</div>
						<div class="widget-content padded clearfix">
						<div id="myModal">
							<form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form"
								action="${basePath}user/userAddressList">
								<div class="row">
									<div class="form-group col-md-6 pull-right">
										<shiro:hasPermission name="user:userAddressAdd">
											<a class="btn btn-sm btn-primary-outline pull-right"
											   href="userAddressAdds" id="add-row"><i class="icon-plus"></i>新增用户地址</a>
										</shiro:hasPermission>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-9">
										<input name="keyword" type="text"
											placeholder="请输入用户昵称、真实姓名、手机号搜索" class="form-control keyword"
											value="${keyword }" />
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
										<th>序号</th>
										<th>用户真实姓名</th>
										<th>用户电话</th>
										<th>收件人姓名</th>
										<th>收件人电话</th>
										<th>省市县/区</th>
										<th>详细地址</th>
										<th>邮编</th>
										<th>创建时间</th>
										<th>更新时间</th>
										<th>是否默认地址</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="i" items="${list }" varStatus="x">
										<tr>
											<td>${x.count}</td>
											<td>${i.trueName }</td>
											<td>
												<shiro:lacksPermission name="user:adminPhone">
													${i.userPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
												</shiro:lacksPermission> 
												<shiro:hasPermission name="user:adminPhone">
													${i.userPhone }
												</shiro:hasPermission>
											</td>
											<td>${i.name}</td>
											<td>
												<shiro:lacksPermission name="user:adminPhone">
													${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
												</shiro:lacksPermission> 
												<shiro:hasPermission name="user:adminPhone">
													${i.phone }
												</shiro:hasPermission>
											</td>
											<td>${i.provinceName} ${i.cityName} ${i.areaName}</td>
											<td>${i.detail}</td>
											<td>${i.postcode }</td>
											<td><fmt:formatDate value="${i.createDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td><fmt:formatDate value="${i.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td style="color: fuchsia;">
												<c:if test="${i.reserve==1}">默认地址</c:if>
											</td>
											<td>
												<shiro:hasPermission name="user:userAddressEdit">
													<a href="${basePath}user/userAddressAdds?id=${i.id}">编辑</a>
												</shiro:hasPermission>
												<shiro:hasPermission name="user:userAddressDelete">
													<a href="${basePath}user/deleteUserAddress?id=${i.id}"
													   onClick="return confirm('确定删除?');">删除</a>
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
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script type="text/javascript">
		function bb() {
			$("#form").submit();
		}

		$(function() {
			$(".datepicker").datepicker({
				format : 'yyyy-mm-dd'
			});
			$(".datepicker").datepicker({
				showSecond : true,
				timeFormat : "hh:mm:ss",
				dateFormat : "yy-mm-dd"
			});
			$('.select2able').select2({
				width : "150"
			});
			$('.active').click(
					function() {
						var userId = $(this).attr('userId');
						$.getJSON("${basePath}user/active/sina?id=" + userId,
								null, function(data) {
									alert(data.resultMsg);
								});
					});
			$("#user-list a:eq(0)").addClass("current");
			$("#user-list a:eq(13)").addClass("current");
			$('#pagination').bootstrapPaginator({
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "userAddressList?keyword=${keyword}&page=" + page;
				}
			});
		});
	</script>
</body>
</html>
