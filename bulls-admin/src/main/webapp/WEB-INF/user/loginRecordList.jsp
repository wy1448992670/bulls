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
<title>登录记录</title>
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
				<h1>登录记录</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>登录记录明细
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
						</div>
						
						<div class="widget-content padded clearfix">
						<div id="myModal">
							<form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form"
								action="${basePath}user/loginRecordList">
								<div class="row">
									<div class="form-group col-md-6">
										<div>
											<input name="keyword" type="text"
												placeholder="请输入用户昵称、真实姓名、手机号搜索"
												class="form-control keyword" value="${keyword }" />
										</div>
									</div>
									<button class="btn btn-primary pull-right hidden-xs"
										type="button" onclick="bb()">搜索</button>
								</div>
								<div class="row">
									<div class="form-group col-md-6">
										<div>
											<input class="form-control datepicker"
												value="<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>"
												id="startTime" name="startDate" type="text"
												placeholder="请选择起始时间" />
										</div>
									</div>
									<div class="form-group col-md-6">
										<div>
											<input class="form-control datepicker"
												value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"
												id="endTime" name="endDate" type="text"
												placeholder="请选择结束时间" "/>
										</div>
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
										<th>真实姓名</th>
										<th>电话号码</th>
										<th>系统</th>
										<th>版本</th>
										<th>IP地址</th>
										<th>登录时间</th>
										<th>设备</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="i" items="${list }" varStatus="x">
										<tr>
											<td>${x.count}</td>
											<td><c:if test="${i.true_name==null}">(未实名)</c:if> <c:if
													test="${i.true_name !=null}">${i.true_name}</c:if></td>
											<td><shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission> <shiro:hasPermission
													name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission></td>
											<td>${i.client}</td>
											<td>${i.version}</td>
											<td>${i.ip}</td>
											<td><fmt:formatDate value="${i.create_date }"
													pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${i.equipment }</td>
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
		function aa(id) {
			if (confirm("您确定给该用户重置支付密码吗?!")) {
				jQuery.ajax({
					url : '${basePath}user/editpayPassword',
					type : "GET",
					data : "id=" + id,
					dataType : "json",
					success : function(data) {
						if (data.status == "1") {
							alert("重置支付密码成功!...");
							window.location.href = "${basePath}user/list/app";
						}
						if (data.status == "0") {
							alert("重置支付密码!..");
						}
					}
				});
			}

		};

		function freeze(id) {
			if (confirm("您确定冻结该账户吗?!")) {
				jQuery.ajax({
					url : '${basePath}user/freeze',
					type : "GET",
					data : "type=0&id=" + id,
					dataType : "json",
					success : function(data) {
						if (data.status == "1") {
							alert("冻结成功");
							window.location.reload();
						}
						if (data.status == "0") {
							alert("冻结失败");
						}
					}
				});
			}

		};

		function unfreeze(id) {
			if (confirm("您确定给该账户解除冻结状态吗?!")) {
				jQuery.ajax({
					url : '${basePath}user/freeze',
					type : "GET",
					data : "type=2&id=" + id,
					dataType : "json",
					success : function(data) {
						if (data.status == "1") {
							alert("解除冻结成功");
							window.location.reload();
						}
						if (data.status == "0") {
							alert("解除冻结失败");
						}
					}
				});
			}

		};
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
			$('#pagination')
					.bootstrapPaginator(
							{
								currentPage : parseInt('${page}'),
								totalPages : parseInt('${pages}'),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current) {
									return "loginRecordList?keyword=${keyword}&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&page="
											+ page;
								}
							});
		});
	</script>
</body>
</html>
