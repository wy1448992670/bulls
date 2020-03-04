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
<title>商城活动列表</title>
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
		<jsp:include page="../../common/header.jsp"></jsp:include>
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
							商城活动列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />
							
							&nbsp;
							<shiro:hasPermission name="shop:activity:save">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="/shop/activity/save" id="add-row">
									<i class="icon-plus"></i>
									添加商城活动
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class=" search1 AppFixed  form-inline  col-lg-4 pull-right col-xs-11" id="form" action="/shop/activity/list">
									<div class="row">
										<div class="form-group col-md-6">
											
										</div>
										<div class="form-group col-md-6">
											<input class="form-control keyword" name="name" type="text" placeholder="请输入活动名称" value="${name }" id="name" />
										</div>
										<input type="hidden" name="page" value="${page }" />
									</div>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th>活动名称</th>
											<th>开始日期</th>
											<th>结束日期</th>
											<th>活动类型</th>
											<th>创建人</th>
											<th>创建时间</th>
											<th>参与活动项目数量</th>
											<th>备注</th>
											<th>是否可用</th>
											<th>详情</th>
											<th>操作</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="item" items="${list }">
											<tr>

												<td>${item.id}</td>
												<td>${item.name}</td>
												<td>
													<fmt:formatDate value="${item.startDate }" pattern="yyyy-MM-dd" />
												</td>
												<td>
													<fmt:formatDate value="${item.stopDate }" pattern="yyyy-MM-dd" />
												</td>
												<td>${item.typeMsg }</td>
												<td>${item.userName }</td>
												<td>
													<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>${item.count }</td>
												<td>${item.remark }</td>
												<td>
													<c:if test="${item.enable == 1 }">
														<span class="label label-success">可用</span>
													</c:if>
													<c:if test="${item.enable == 0 }">
														<span class="label label-danger">不可用</span>
													</c:if>
												</td>
												<td>
													<a href="${basePath}/shop/activity/detail?id=${item.id}&activityType=${item.type}">详情</a>
												</td>
												<td>
													<shiro:hasPermission name="shop:activity:enable">
														<c:if test="${item.enable == 1 }">
															<button class="btn btn-danger btn-xs" onclick="updateEnableStatus('${item.id}', 0)">禁用</button>
														</c:if>
														</shiro:hasPermission>
														<c:if test="${item.enable == 0 }">
															<button class="btn btn-primary btn-xs" onclick="updateEnableStatus('${item.id}', 1)">启用</button>
														</c:if>
													<shiro:hasPermission name="shop:activity:save">
														<button class="btn btn-warning btn-xs" onclick="gotoEdit('${item.id}', '${item.type}')">编辑</button>
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
				width : "150"
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
					return "shop/activity/list?name=${name}&page=" + page;
				}
			});
			
		});
		function report() {
			var startTime = $.trim($('#startTime').val());
			var endTime = $.trim($('#endTime').val());
			var keyword = $.trim($('#keyword').val());
			window.location.href = "${basePath}report/paymentExcel?startTime=" + startTime + "&endTime=" + endTime
					+ "&keyword=" + keyword;
		}
		
		function updateEnableStatus(id, enable) {
		    var msg = "";
		    console.log("enable", enable);
		    if (enable == 0) {
                msg = "确认禁用活动？！";
			} else{
                msg = "确认启用活动？！";
			}
			if (confirm(msg)) {
                $.ajax({
                    url: "${basePath}shop/activity/updateActivityEnableStatus",
                    type:"POST",
                    data: {
                        id: id,
                        enableStatus: enable
                    },
                    dataType:"json",
                    success:function(res){
                        if (res.code == 1) {
                            alert(res.msg)
                            window.location.href='${basePath}shop/activity/list';
                        } else {
                            alert(res.msg)
                        }
                    }
                });
			}
		}
		
		function gotoEdit(id, type) {
            window.location.href='${basePath}shop/activity/detail?optType=1&activityType=' + type + '&id=' + id;
		}
		
		
	</script>
</body>
</html>
