<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品详细信息</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.upload-picture a {
	display: inline-block;
	overflow: hidden;
	border: 0;
	vertical-align: top;
	margin: 0 5px 10px 0;
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
				<h1>商品管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							商品详细信息
							<input type="hidden" id="projectType" value="${project.projectType }" />
						</div>
						<div class="widget-content padded clearfix">
							<form action="" method="post" class="form-horizontal" id="validate-form">
								<%-- 							<div class="form-group">
									<label class="control-label col-md-2">借款人</label>

									<div class="col-md-7">
										<div class="form-control-static upload-picture">${lender }</div>
									</div>
								</div> --%>


								<div class="form-group">
									<label class="control-label col-md-2">商品封面图</label>

									<div class="col-md-7">
										<div class="form-control-static upload-picture">
											<c:forEach var="pic" items="${good.goodsPictures }">
												<c:if test="${pic.type==14}">
													<a class="gallery-item fancybox" rel="g1" href="${aPath}upload/${pic.upload.path }" title="${pic.name }">
														<img src="${aPath}upload/${pic.upload.path }" />

														<div class="actions">
															<i class="icon-zoom-in"></i>
														</div>
													</a>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品详细图</label>

									<div class="col-md-7">
										<div class="form-control-static upload-picture">
											<c:forEach var="pic" items="${good.goodsPictures}">
												<c:if test="${pic.type==12}">
													<a class="gallery-item fancybox" rel="g1" href="${aPath}upload/${pic.upload.path }" title="${pic.name }">
														<img src="${aPath}upload/${pic.upload.path }" />

														<div class="actions">
															<i class="icon-zoom-in"></i>
														</div>
													</a>
												</c:if>
											</c:forEach>
										</div>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品名称</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.goodsName }</p>
									</div>
								</div>
								<%-- 	<div class="form-group">
									<label class="control-label col-md-2">商品风险等级</label>

									<div class="col-md-7">
										<p class="form-control-static">${projectRiskDescription}</p>
									</div>
								</div> --%>

								<%-- 							<div class="form-group">
									<label class="control-label col-md-2">商品类型</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${project.noob==1}">
												<span class="label label-primary">新手标</span>
											</c:if>
											<c:if test="${project.noob==2}">
												<span class="label label-danger">VIP标</span>
											</c:if>
											<c:if test="${project.projectType==0}">
												<span class="label label-danger">散标</span>
											</c:if>
											<c:if test="${project.projectType==3}">
												<span class="label label-danger">超级标</span>
											</c:if>
											<c:if test="${project.projectType==5}">
												<span class="label label-danger">周期标</span>
											</c:if>
										</p>
									</div>
								</div> --%>
								<div class="form-group">
									<label class="control-label col-md-2">商品编号</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.goodsNo }</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">进货价格</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.buyingPrice }</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">销售价格</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.salingPrice }</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">会员价格</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.memberSalingPrice }</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">库存</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.stock }</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">库存单位</label>
									<div class="col-md-7">
										<p class="form-control-static">${good.stockUnit }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">排序</label>
									<div class="col-md-7">
										<p class="form-control-static">${good.sort }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品介绍</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.introduction}</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">已售出</label>

									<div class="col-md-7">
										<p class="form-control-static">${good.sellStock}</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">是否上架</label>

									<div class="col-md-7">
										<p class="form-control-static"><c:if test="${good.upDown == 0}">未上架</c:if><c:if test="${good.upDown == 1}">已上架</c:if></p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">创建时间</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${good.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="col-md-7 text-center">
										<a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
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
							商品购买信息
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table" id="investors">
									<thead>
										<tr>
											<th>购买用户</th>
											<th>购买金额</th>
											<th>购买时间</th>
											<th>购买方式</th>
										</tr>
									</thead>
									<tbody>
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
							商品评价信息
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table" id="assess">
									<thead>
										<tr>
											<th>姓名</th>
											<th>评价内容</th>
											<th>时间</th>
											<th>操作</th> 
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="text-right">
								<ul id="pagination2"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>

	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-paginator.min.js"></script>
	<%-- <script src="${basePath}js/comm.js" type="text/javascript"></script> --%>
	<script type="text/javascript">
		$(function() {
			$('.select2able').select2();

			$(".fancybox").fancybox({
				maxWidth : 700,
				height : 'auto',
				fitToView : false,
				autoSize : true,
				padding : 15,
				nextEffect : 'fade',
				prevEffect : 'fade',
				helpers : {
					title : {
						type : "outside"
					}
				}
			});
			showPorjectInfo(1);//加载商品购买记录分页数据
			listGoodsAssessPageAjax(1);//加载评价分页数据
		});

		function showPorjectInfo(currentPage) {

			var goodsId = '${good.id}';
			$.ajax({
				url : "${basePath}shop/listShopAjax",
				type : "get",
				dataType : "json",
				data : {
					id : goodsId,
					page : currentPage
				},
				success : function(obj) {
					if (obj.code == 1) {
						if (obj.list.length) {
							$("#investors tbody").empty();
							var html = "";
							$.each(obj.list, function(index, ele) {
								html += "<tr><td>" + ele.true_name + "</td><td>" + ele.buy_price + "</td><td>"
										+ ele.create_date + "</td><td>" + ele.client + "</td></tr>";
							});
							$("#investors tbody").append(html);
							$('#pagination').bootstrapPaginator({
								currentPage : parseInt(obj.page),
								totalPages : parseInt(obj.pages),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current, limit) {
									return "javascript:showPorjectInfo(" + page + ")";
								}
							});
						}
					} else {
						alert("失败,请刷新重试！");
					}

				}
			});

		}

		//评价分页数据
		function listGoodsAssessPageAjax(currentPage) {

			var goodsId = '${good.id}';
			$.ajax({
				url : "${basePath}shop/listGoodsAssessPageAjax",
				type : "get",
				dataType : "json",
				data : {
					id : goodsId,
					page : currentPage
				},
				success : function(obj) {
					if (obj.code == 1) {
						if (obj.list.length) {
							$("#assess tbody").empty();
							var html = "";
							$.each(obj.list, function(index, ele) {
								html += "<tr><td>" + ele.true_name + "</td><td title='具体内容请点击详情'>"
										+ ele.content.substr(0, 18) + "...</td><td>" + ele.create_date
										+ "</td><td><a href='${basePath}shop/assessDetail?goodsId=${good.id}&userId="
										+ ele.user_id + "&orderId=" + ele.order_id + "&parentId=" + ele.assess_id
										+ "'>详情</a></td></tr>";
							});
							$("#assess tbody").append(html);
							$('#pagination2').bootstrapPaginator({
								currentPage : parseInt(obj.page),
								totalPages : parseInt(obj.pages),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current, limit) {
									return "javascript:listGoodsAssessPageAjax(" + page + ")";
								}
							});
						}
					} else {
						alert("失败,请刷新重试！");
					}

				}
			});

		}
	</script>
</body>
</html>