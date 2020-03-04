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
<title>支付对账查询</title>
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
				<h1>业务管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							支付对账查询
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />
	                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="repairData(0);">生成全部账单</button>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
							<div>
								<div class="form-group col-md-4 col-xs-12">
									<div class="form-group col-md-4">
										<input class="form-control datepicker" value="${repairDate }" name="repairDate" id="repairDate" type="text" placeholder="请选择修复时间" />
									</div>
									<div class="form-group col-md-4"><button class="btn btn-primary pull-right hidden-xs" type="button" onclick="repairData(1);">修复数据</button></div>
								</div>
							</div>
							
							
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline  col-lg-5 pull-right col-xs-11" id="form" action="checkPaymentList">
									<input type="hidden" name="page" value="1" />
									
									<div class="row">
										<div class="form-group col-md-4 col-xs-12">
											<div>
												<input class="form-control keyword" name="orderNo" id="orderNo" type="text" placeholder="请输入订单号搜索" value="${orderNo }" />
											</div>
										</div>
										<div class="form-group col-md-4 col-xs-12">
											<select class="select2able" name="channel" id="channel">
												<option value="">请选择支付渠道</option>
												<c:forEach var="item" items="${channels}">
													<option value="${item.featureName}" <c:if test="${channel == item.featureName }">selected</c:if>>${item.description}</option>
												</c:forEach>
											</select>
										</div>
										<div class="form-group col-md-4 pull-right  col-xs-12">
											<select class="select2able" name="warningType" id="warningType">
												<option value="" <c:if test="${warningType == null }">selected</c:if>>数据是否有误</option>
												<option value="1" <c:if test="${warningType == 1 }">selected</c:if>>是</option>
												<option value="0" <c:if test="${warningType == 0 }">selected</c:if>>否</option>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${startDate }" name=startDate id="startDate" type="text" placeholder="请选择支付起始时间" />
										</div>
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${endDate }" name="endDate" id="endDate" type="text" placeholder="请选择支付结束时间" />
										</div>
									</div>
									<input type="hidden" name="page" value="${page }" />
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>订单号</th>
											<th>平台数据是否丢失</th>
											<th>通道数据是否丢失</th>
											<th>平台渠道</th>
											<th>通道渠道</th>
											<th width="160px">平台创建时间</th>
											<th width="160px">通道创建时间</th>
											<th>平台状态</th>
											<th>通道状态</th>
											<th width="160px">平台支付时间</th>
											<th width="160px">通道支付时间</th>
											<th>平台金额</th>
											<th>通道金额</th>
											<th>手续费</th>
											<th>退款状态</th>
											<th>退款金额</th>
											<th>退款手续费</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td>${i.orderNo }</td>
												<td <c:if test="${i.rechargeLoseWarning == 1 }"> style="color:red;" </c:if> >
													<c:if test="${i.rechargeLoseWarning == 0 }">否</c:if>
													<c:if test="${i.rechargeLoseWarning == 1 }">是</c:if>
												</td>
												<td <c:if test="${i.checkLoseWarning == 1 }"> style="color:red;" </c:if> >
													<c:if test="${i.checkLoseWarning == 0 }">否</c:if>
													<c:if test="${i.checkLoseWarning == 1 }">是</c:if>
												</td>
												<td <c:if test="${i.payChannelWarning == 1 }"> style="color:red;" </c:if> >
													${i.rechargePayChannel }
												</td>
												<td <c:if test="${i.payChannelWarning == 1 }"> style="color:red;" </c:if> >
													${i.checkPayChannel }
												</td>
												<td <c:if test="${i.createDateWarning == 1 }"> style="color:red;" </c:if> >
													<fmt:formatDate value="${i.rechargeCreateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td <c:if test="${i.createDateWarning == 1 }"> style="color:red;" </c:if> >
													<fmt:formatDate value="${i.checkCreateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td <c:if test="${i.statusWarning == 1 }"> style="color:red;" </c:if> >
													<c:if test="${i.rechargeStatus == 1 }">申请中</c:if>
													<c:if test="${i.rechargeStatus == 0 }">通过</c:if>
													<c:if test="${i.rechargeStatus == 2 }">失败</c:if>
												</td>
												<td <c:if test="${i.statusWarning == 1 }"> style="color:red;" </c:if> >
													<c:if test="${i.rechargeStatus == 1 }">申请中</c:if>
													<c:if test="${i.rechargeStatus == 0 }">通过</c:if>
													<c:if test="${i.rechargeStatus == 2 }">失败</c:if>
												</td>
												<td <c:if test="${i.updateDateWarning == 1 }"> style="color:red;" </c:if> >
													<fmt:formatDate value="${i.rechargeUpdateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td <c:if test="${i.updateDateWarning == 1 }"> style="color:red;" </c:if> >
													<fmt:formatDate value="${i.checkUpdateDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td <c:if test="${i.amountWarning == 1 }"> style="color:red;" </c:if> >
													${i.rechargeAmount }
												</td>
												<td <c:if test="${i.amountWarning == 1 }"> style="color:red;" </c:if> >
													${i.checkAmount }
												</td>
												<td>${i.fee }</td>
												<td>
													<c:if test="${i.refundStatus == 1 }">
														<span class="label label-info">申请中</span>
													</c:if>
													<c:if test="${i.refundStatus == 0 }">
														<span class="label label-success">通过</span>
													</c:if>
													<c:if test="${i.refundStatus == 2 }">
														<span class="label label-danger">失败</span>
													</c:if>
												</td>
												<td>${i.refundAmount }</td>
												<td>${i.refundFee }</td>
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
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script type="text/javascript">
    function search() {
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        if(startDate!="" && endDate!=""){
            if(startDate > endDate){
                alert("支付结束时间必须大于等于开始时间");
                return;
            }
        }
        $("#form").submit();
    };
	
    function repairData(type){
    	var repairDate = $("#repairDate").val();
    	if(type == 1){
        	if(repairDate == ''){
        		alert("请选择修复日期");
                return;
        	}
    	} 
    	$.ajax({
            url: "${basePath}trade/repairCheckPayment?repairDate="+repairDate,
            type : "get",
            success: function (data) {
            	alert(data);
            	window.loaction.reload();
            }
        });
    }
     
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2able').select2({width: ""});
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "checkPaymentList?orderNo=${orderNo}&channel=${channel}&warningType=${warningType}"
					+"&startDate=${startDate}&endDate=${endDate}&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
    });

</script>
</body>
</html>
