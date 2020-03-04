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
<title>迁移用户投资列表</title>
<style type="text/css">
strong.money {
	color: #007aff;
	font-size: 18px;
}

.qaCs {
	font-weight: bold;
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
				<h1>用户管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-7">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							迁移用户投资列表
						</div>
						<div class="widget-content padded clearfix">
							<form method="post" class="form-horizontal">
								<div class="widget-content padded clearfix">
									<div class="table-responsive">
										<div class="table-responsive">
											<table class="table table-bordered">
												<thead>
													<tr>
														<td>ID</td>
														<td>标的名称</td>
														<td>还款类型</td>
														<td>借款期限</td>
														<td>投资金额(元)</td>
														<td>红包金额(元)</td>
														<td>放款时间</td>
														<td>年利率</td>
														<td>利息(元)</td>
														<td>加息利率</td>
														<td>加息利息(元)</td>
														<td>结清时间</td>
														<td>操作</td>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="i" items="${list }">
														<tr>
															<td>${i.id }</td>
															<td>${i.bidTitle}</td>
															<td>
																<c:if test="${i.repaymentType == 1 }">
																	<span class="label label-warning">按月还款、等额本息</span>
																</c:if>
																<c:if test="${i.repaymentType == 2 }">
																	<span class="label label-info">按月付息、到期还本</span>
																</c:if>
																<c:if test="${i.repaymentType == 3 }">
																	<span class="label label-danger">一次性还款</span>
																</c:if>
															</td>
															<td>${i.period }</td>
															<td>${i.amount }</td>
															<td>${i.redAmount }</td>
															<td>${i.loanTime }</td>
															<td>${i.apr }</td>
															<td>${i.interest }</td>
															<td>${i.increaseRate }</td>
															<td>${i.increaseRateInterest }</td>
															<td>${i.finishTime }</td>
															<td class="detailflag" onclick="showMigrationInvestmentBill('${i.id }','${i.bidTitle}',this)"><a style="cursor:pointer;">查看回款账单</a></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
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
				<div class="col-lg-5">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							回款账单
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered" id="migrationInvestmentBill">
									<thead>
										<tr>
											<td>标的名称</td>
											<td>期数</td>
											<td>应收本金(元)</td>
											<td>应收利息(元)</td>
											<td>加息利息(元)</td>
											<td>应收时间</td>
											<td>实际收款时间</td>
											<td>回款状态</td>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>

<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
	$(function () {
	    //迁移用户投资
	    //showMigrationInvestmentBill();
	    
	});

	//迁移用户投资
	function showMigrationInvestmentBill(id,bidTitle,_this) {
		$('.detailflag').css('background','');
		$(_this).css('background','#CCCCCC');
	    $.ajax({
	        url: "${basePath}user/listMigrationInvestmentBillAjax",
	        type: "get",
	        dataType: "json",
	        data: "migrationInvestmentId="+id,
	        success: function (obj) {
	            $("#migrationInvestmentBill tbody").empty();
	            for (var i = 0; i < obj.list.length; i++) {
	            	var bill = obj.list[i];
	                var tr = "<tr><td>"+bidTitle+
	                		"</td><td>"+bill.periods+
	                		"</td><td>"+bill.receiveCorpus+
	                		"</td><td>"+bill.receiveInterest+
	                		"</td><td>"+bill.increaseInterest+
	                		"</td><td>"+bill.receiveTime+
	                		"</td><td>"+bill.realReceiveTime+
	                		"</td><td>"+bill.status+"</td></tr>";
	                $("#migrationInvestmentBill tbody").append(tr);
	            }
	        }
	    });
	}
</script>
</body>
</html>
