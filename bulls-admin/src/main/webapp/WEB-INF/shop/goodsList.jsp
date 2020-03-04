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
<title>商品列表</title>
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
							商品列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />
							<%-- 	<a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="${basePath}report/export/project?limitDays=${limitDays}&status=${status}&keyword=${keyword}" id="add-row">
								<i class="icon-plus"></i>
								导出excel
							</a> --%>
							&nbsp;
							<%-- <a class="btn btn-sm btn-primary-outline pull-right" onclick="desc(this)">
								<c:if test="${desc == 'desc' }">
                        	降序 ↓
                        	</c:if>
								<c:if test="${desc == 'asc' }">
                        	升序 ↑
                        	</c:if>
							</a> --%>
							&nbsp;
							<shiro:hasPermission name="shop:addGoods">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="addGoods" id="add-row">
									<i class="icon-plus"></i>
									创建商品
								</a>
								<!--     <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="addLoan" style="margin-left: 10px;"><i
                                    class="icon-plus"></i>创建个人借贷商品</a> -->
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class=" search1 AppFixed  form-inline  col-lg-4 pull-right col-xs-11" id="form" action="goodsList">
									<input type="hidden" name="desc" id="desc" value="${desc}" />
									<div class="row">
										<div class="form-group col-md-6">
											<%-- <select class="select2able" name="status">
											<option value="" <c:if test="${status == null }">selected</c:if>>所有商品状态</option>
											<option value="0" <c:if test="${status == 0 }">selected</c:if>>创建</option>
											<option value="1" <c:if test="${status == 1 }">selected</c:if>>预购中</option>
											<option value="2" <c:if test="${status == 2 }">selected</c:if>>投资中</option>
											<option value="3" <c:if test="${status == 3 }">selected</c:if>>投资完成</option>
											<option value="4" <c:if test="${status == 4 }">selected</c:if>>还款中</option>
											<option value="5" <c:if test="${status == 5 }">selected</c:if>>还款成功</option>
											<option value="6" <c:if test="${status == 6 }">selected</c:if>>还款失败</option>
										</select> --%>
										</div>
										<%-- 		<div class="form-group col-md-6">
										<select class="select2able" name="noob">
											<option value="" <c:if test="${noob == null }">selected</c:if>>所有类型</option>
											<option value="0" <c:if test="${noob == 0 }">selected</c:if>>普通散标</option>
											<option value="1" <c:if test="${noob == 1 }">selected</c:if>>新手标</option>
											<option value="2" <c:if test="${noob == 2 }">selected</c:if>>VIP标</option>
											<option value="3" <c:if test="${noob == 3 }">selected</c:if>>智投</option>
										</select>
									</div> --%>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<%-- 	<select class="select2able" name="limitDays">
											<option value="" <c:if test="${limitDays == null }">selected</c:if>>所有商品期限</option>
											<option value="15" <c:if test="${limitDays == 15 }">selected</c:if>>15</option>
											<option value="30" <c:if test="${limitDays == 30 }">selected</c:if>>30</option>
											<option value="60" <c:if test="${limitDays == 60 }">selected</c:if>>60</option>
											<option value="90" <c:if test="${limitDays == 90 }">selected</c:if>>90</option>
											<option value="120" <c:if test="${limitDays == 120 }">selected</c:if>>120</option>
											<option value="180" <c:if test="${limitDays == 180 }">selected</c:if>>180</option>
											<option value="240" <c:if test="${limitDays == 240 }">selected</c:if>>240</option>
											<option value="270" <c:if test="${limitDays == 270 }">selected</c:if>>270</option>
											<option value="360" <c:if test="${limitDays == 360 }">selected</c:if>>360</option>
										</select> --%>
											<input class="form-control keyword" name="skuCode" type="text" placeholder="请输入SKU码" value="${skuCode }" id="skuCode" />
										</div>
										<div class="form-group col-md-6">
											<input class="form-control keyword" name="keyword" type="text" placeholder="请输入商品名称" value="${keyword }" id="keyword" />
										</div>
										<input type="hidden" name="page" value="${page }" />
									</div>

									<%-- 		<div class="row">
									<div class="form-group col-md-6">
										<input class="form-control datepicker" value="${lendBeginTimeStartTime }" name="lendBeginTimeStartTime" id="lendBeginTimeStartTime" type="text" placeholder="支持发标时间选择起始时间" />
									</div>
									<div class="form-group col-md-6">
										<input class="form-control datepicker" value="${lendBeginTimeEndTime }" name="lendBeginTimeEndTime" id="lendBeginTimeEndTime" type="text" placeholder="支持发标时间选择结束时间" />
									</div>
								</div> --%>


									<shiro:hasPermission name="user:export:app">
										<div class="row">
											<%-- 	<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime" type="text" placeholder="还款计划请选择起始时间" />
										</div>
										<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime" type="text" placeholder="还款计划请选择结束时间" />
										</div> --%>
											<!-- <div class="form-group col-md-4">
											<a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="#" onclick="report()">
												<i class="icon-plus"></i>
												导出还款计划
											</a>
										</div> -->
										</div>
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
											<th>SKU码</th>
											<th>商品名称</th>
											<th>图片</th>
											<th>所属品牌</th>
											<th>编号</th>
											<th>商品重量(KG)</th>
											<th>进货价格</th>
											<th>销售价格</th>
											<th>会员价格</th>
											<th>库存</th>
											<th>是否上架</th>
											<th>创建时间</th>
											<th>更新时间</th>
											<th>操作</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>

												<td>${g.id}</td>
												<td>${g.skuCode}</td>
												<td>${g.goodsName}</td>
												<td width="10%">
													<img src="${g.goodsPictures[0].upload.cdnPath}" height="80px" width="80px">
												</td>
												<td>${g.brandName}</td>
												<td>${g.goodsNo}</td>
												<td>${g.weight}</td>
												<td>${g.buyingPrice}</td>
												<td>${g.salingPrice}</td>
												<td>${g.memberSalingPrice}</td>
												<td>${g.stock}</td>
												<td>
												<c:if test="${g.upDown == 1}"><span style="color:blue">已上架</span></c:if>
												<c:if test="${g.upDown == 0}"><span style="color:red">未上架</span></c:if>
												</td>
												<td>
													<fmt:formatDate value="${g.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<fmt:formatDate value="${g.updateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>

												<%-- 	<td>${g.sort}</td> --%>
												<td>
													<shiro:hasPermission name="shop:goodsEdit">
														<a class="edit-row" href="goodsEdit?id=${g.id }">编辑</a>
													</shiro:hasPermission>

													<shiro:hasPermission name="shop:goodsDetail">
														<a class="delete-row" href="goodsDetail?id=${g.id }" id="delete">详细</a>
													</shiro:hasPermission>

													<shiro:hasPermission name="shop:appraiseList">
														<a class="delete-row" href="appraiseList?goodId=${g.id }">评价列表</a>
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
					return "goodsList?keyword=${keyword}&status=${status}&page=" + page;
				}
			});

			$("#lendBeginTimeStartTime").keyup(function(e) {
				e = e || window.e;
				if (e.keyCode == 13) {
					$("#form").submit();
				}
			});

			$("#lendBeginTimeEndTime").keyup(function(e) {
				e = e || window.e;
				if (e.keyCode == 13) {
					$("#form").submit();
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
		function desc(o) {
			var dc = $("#desc").val();
			if (dc == "asc") {
				$("#desc").val("desc");
			} else {
				$("#desc").val("asc");
			}
			$("#form").submit();
		}
	</script>
</body>
</html>