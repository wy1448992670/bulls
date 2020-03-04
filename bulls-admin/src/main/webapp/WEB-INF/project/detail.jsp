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
<title>项目详细信息</title>
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
				<h1>项目管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							项目详细信息
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
									<label class="control-label col-md-2">项目小图</label>

									<div class="col-md-7">
										<div class="form-control-static upload-picture">
											<c:forEach var="pic" items="${project.pictures }">
												<c:if test="${pic.type==1}">
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
									<label class="control-label col-md-2">项目大图</label>

									<div class="col-md-7">
										<div class="form-control-static upload-picture">
											<c:forEach var="pic" items="${project.pictures }">
												<c:if test="${pic.type==13}">
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
									<label class="control-label col-md-2">项目名称</label>

									<div class="col-md-7">
										<p class="form-control-static">${project.title }</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">性别</label>

									<div class="col-md-7">
										<p class="form-control-static">${project.sexName }</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">状态</label>

									<div class="col-md-7">
										<p class="form-control-static">${project.projectStatusName }</p>
									</div>
								</div>
								<%-- 	<div class="form-group">
									<label class="control-label col-md-2">项目风险等级</label>

									<div class="col-md-7">
										<p class="form-control-static">${projectRiskDescription}</p>
									</div>
								</div> --%>

								<%-- 							<div class="form-group">
									<label class="control-label col-md-2">项目类型</label>

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
									<label class="control-label col-md-2">年化收益</label>

									<div class="col-md-7">
										<p class="form-control-static">${project.annualized }</p>
									</div>
								</div>
								<%-- 	<div class="form-group">
									<label class="control-label col-md-2">已融资金额</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${project.investedAmount==null }">
                                            0</c:if>
											<c:if test="${project.investedAmount!=null }">
                                            ${project.investedAmount}</c:if>
											元
										</p>
									</div>
								</div> --%>
								<%-- 		<c:if test="${project.projectType ==0 }">
									<div class="form-group">
										<label class="control-label col-md-2">真实投资金额</label>

										<div class="col-md-7">
											<p class="form-control-static">
												<c:if test="${project.trueAmount==null }">
                                                0元 </c:if>
												<c:if test="${project.trueAmount!=null }">
                                                ${project.trueAmount }元</c:if>

											</p>
										</div>
									</div>
								</c:if> --%>
								<%-- 								<c:if test="${project.projectType ==1 }">
									<div class="form-group">
										<label class="control-label col-md-2">真实投资金额</label>

										<div class="col-md-7">
											<p class="form-control-static">
												<c:if test="${project.trueAmount==null }">
                                                0元 </c:if>
												<c:if test="${project.trueAmount!=null }">
                                                ${project.trueAmount }元</c:if>

											</p>
										</div>
									</div>
								</c:if> --%>
								<%-- 			<c:if test="${project.projectType ==2 }">
									<div class="form-group">
										<label class="control-label col-md-2">真实投资金额</label>

										<div class="col-md-7">
											<p class="form-control-static">
												<c:if test="${project.trueAmount==null }">
                                                0元 </c:if>
												<c:if test="${project.trueAmount!=null }">
                                                ${project.trueAmount }元</c:if>
											</p>
										</div>
									</div>
								</c:if> --%>
								<div class="form-group">
									<label class="control-label col-md-2">总融资金额</label>

									<div class="col-md-7">
										<p class="form-control-static">${project.totalAmount }元</p>
									</div>
								</div>

								<%-- 								<div class="form-group">
									<label class="control-label col-md-2">融资进度</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatNumber value="${project.investedAmount/project.totalAmount }" maxFractionDigits="2" type="percent" />
										</p>
									</div>
								</div>
 --%>
								<div class="form-group">
									<label class="control-label col-md-2">创建时间</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${project.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">开标时间</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${project.startTime }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>

								<c:if test="${project.projectType !=2 }">
									<%-- 		<div class="form-group">
										<label class="control-label col-md-2">投资截至时间</label>

										<div class="col-md-7">
											<p class="form-control-static">
												<fmt:formatDate value="${project.deadline }" pattern="yyyy-MM-dd HH:mm:ss" />
											</p>
										</div>
									</div>
 --%>
									<div class="form-group">
										<label class="control-label col-md-2">还款期限</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.limitDays }天</p>
										</div>
									</div>
									<%-- 				<c:if test="${project.transferable gt 0 }">
										<div class="form-group">
											<label class="control-label col-md-2">转让规则</label>
											<div class="col-md-7">
												<p class="form-control-static" style="color: red;">持有${project.transferable}天&nbsp;可以转让</p>
											</div>
										</div>
									</c:if> --%>
									<%-- 	<c:if test="${project.transferable eq 0 }">
										<div class="form-group">
											<label class="control-label col-md-2">转让规则</label>
											<div class="col-md-7">
												<p class="form-control-static" style="color: red;">当前项目不可转让</p>
											</div>
										</div>
									</c:if> --%>
									<div class="form-group">
										<label class="control-label col-md-2">限制天数</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.rateCouponDays}天</p>
										</div>
									</div>


									<div class="form-group">
										<label class="control-label col-md-2">还款方式</label>

										<div class="col-md-7">
											<p class="form-control-static">
												按月还息,到期还本
												<%-- 												<c:if test="${project.repaymentMethod == 1 }">按月还息+本金</c:if> --%>
											</p>
										</div>
									</div>

									<%-- 				<div class="form-group">
										<label class="control-label col-md-2">项目地址</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.address }</p>
										</div>
									</div> --%>

									<div class="form-group">
										<label class="control-label col-md-2">饲养费</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.raiseFee}</p>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-2">管理费</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.manageFee}</p>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-2">耳标号</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.earNumber}</p>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-2">保险编号</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.safeNumber}</p>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-md-2">牧场</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.tmDict.tName}</p>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-2">GPS设备编号</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.gpsNumber}</p>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-2">排序</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.sort}</p>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-2">限制天数</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.rateCouponDays}天</p>
										</div>
									</div>

									<div class="form-group">
										<label class="control-label col-md-2">项目详情</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.projectDescription }</p>
										</div>
									</div>

									<%-- 		<div class="form-group">
										<label class="control-label col-md-2">月租金收入</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.rentalIncome }</p>
										</div>
									</div> --%>

									<%-- 		<div class="form-group">
										<label class="control-label col-md-2">资金用途</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.useOfFunds }</p>
										</div>
									</div> --%>

									<%-- 		<div class="form-group">
										<label class="control-label col-md-2">还款来源</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.repaymentSource }</p>
										</div>
									</div> --%>
									<%-- 
									<div class="form-group">
										<label class="control-label col-md-2">抵押物信息</label>

										<div class="col-md-7">
											<p class="form-control-static">${project.collateralInfo }</p>
										</div>
									</div> --%>
								</c:if>
								<%-- 
								<c:if test="${period > 0}">
									<div class="form-group">
										<label class="control-label col-md-2">期数</label>

										<div class="col-md-7">
											<p class="form-control-static">${period }</p>
										</div>
									</div>

								</c:if> --%>
								<%-- 
								<div class="form-group">
									<label class="control-label col-md-2">排序</label>

									<div class="col-md-7">
										<p class="form-control-static">${project.sort}</p>
									</div>
								</div> --%>

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

				<%-- 			<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							还款计划
						</div>
						<div class="widget-content padded clearfix">
							<table class="table">
								<thead>
									<tr>
										<th>序号</th>
										<th>还款时间</th>
										<th>应还本息</th>
										<th>应还本金</th>
										<th>应还利息</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${interests }" varStatus="status" var="interest">
										<tr>
											<td>${status.index+1 }</td>
											<td>
												<fmt:formatDate value="${interest.date }" pattern="yyyy-MM-dd" />
											</td>
											<td>
												<fmt:formatNumber value="${interest.capitalAmount+interest.interestAmount }" maxFractionDigits="2" />
											</td>
											<td>
												<fmt:formatNumber value="${interest.capitalAmount }" maxFractionDigits="2" />
											</td>
											<td>
												<fmt:formatNumber value="${interest.interestAmount }" maxFractionDigits="2" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div> --%>

				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							项目投资信息
						</div>
						<div class="widget-content padded clearfix">
						<div class="table-responsive">
							<table class="table" id="investors">
								<thead>
									<tr>
										<th>投资用户</th>
										<th>投资金额</th>
										<th>投资时间</th>
										<th>投资方式</th>
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
				</div>

				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							历史交易记录
						</div>
						<div class="widget-content padded clearfix">
						<div class="table-responsive">
							<table class="table" id="investors_buyBack">
								<thead>
									<tr>
										<th>成交人</th>
										<th>手机号</th>
										<th>交易时间</th>
										<th>回购时间</th>
										<th>饲养周期(天)</th>
										<th>体重(KG)</th>
										<th>月龄(月)</th>
										<th>总金额(元)</th>
										<th>饲养费(元)</th>
										<th>管理费(元)</th>
										<th>服务费(元)</th>
										<th>状态</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							</div>
							<div class="text-right">
								<ul id="pagination_buyBack"></ul>
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
	<script src="${basePath}js/comm.js"></script>

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
			showPorjectInfo(1);
			listBuyBackRecordAjax(1);
		});

		function showPorjectInfo(currentPage) {

			var projectId = '${project.id}';
			$.ajax({
				url : "${basePath}project/listInvestmentAjax",
				type : "get",
				dataType : "json",
				data : {
					id : projectId,
					page : currentPage
				},
				success : function(obj) {
					if (obj.code == 1) {
						if (obj.list.length) {
							$("#investors tbody").empty();
							var html = "";
							$.each(obj.list, function(index, ele) {
								html += "<tr><td>" + ele.true_name + "</td><td>" + ele.amount + "</td><td>"
										+ comm.timeFormat(ele.create_date, "yyyy-MM-dd HH:mm:ss") + "</td><td>"
										+ ele.client + "</td></tr>";
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
		
		function listBuyBackRecordAjax(currentPage) {

			var earNumber = '${project.earNumber}';
			$.ajax({
				url : "${basePath}project/listBuyBackRecordAjax",
				type : "get",
				dataType : "json",
				data : {
					earNumber : earNumber,
					page : currentPage
				},
				success : function(obj) {
					if (obj.code == 1) {
						if (obj.list.length) {
							$("#investors_buyBack tbody").empty();
							var html = "";
							$.each(obj.list, function(index, ele) {
								html += "<tr><td>" + ele.true_name + "</td><td>" + ele.phone + "</td><td>"
										+ comm.timeFormat(ele.deal_time, "yyyy-MM-dd HH:mm:ss") + "</td><td>"
										+ comm.timeFormat(ele.buy_back, "yyyy-MM-dd HH:mm:ss") + "</td><td>" + ele.limit_days + "</td><td>" + ele.weight + "</td><td>"
										+ ele.yue_ling + "</td><td>"
										+ ele.amount + "</td><td>" + ele.raise_fee + "</td><td>" + ele.manage_fee + "</td><td>"
										+ ele.service_free+ "</td><td>"
										+ ele.status + "</td></tr>";
							});
							$("#investors_buyBack tbody").append(html);
							$('#pagination_buyBack').bootstrapPaginator({
								currentPage : parseInt(obj.page),
								totalPages : parseInt(obj.pages),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current, limit) {
									return "javascript:listBuyBackRecordAjax(" + page + ")";
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