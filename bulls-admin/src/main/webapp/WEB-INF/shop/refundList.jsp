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
<title>退款审核</title>
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

	<div class="modal fade" id="auditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">退款确认</h4>
				</div>
				<div class="modal-body">
					<textarea id="remark" class="form-control" placeholder="请输入审核说明"></textarea>
				</div>
				<div class="modal-footer">
					<!-- data-dismiss="modal" -->
					<button type="button" onclick="auditConfirm(3)" class="btn btn-primary">拒绝退款</button>
					<button type="button" onclick="auditConfirm(2)" class="btn btn-primary">同意退款</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>


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
				<input type="hidden" id="aid" />
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							退款审核列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />

							<shiro:hasPermission name="report:exportRefundList">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()" href="JavaScript:;">
									<i class="icon-plus"></i>
									导出Excel
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline  col-lg-5 pull-right col-xs-11" id="form" action="refundList">
									<input type="hidden" name="page" value="1" />

									<div class="row">
										<div class="form-group col-md-3 col-xs-12">
											<div>
												<input class="form-control keyword" name="trueName" id="trueName" type="text" placeholder="请输入真实姓名搜索" value="${trueName }" />
											</div>
										</div>
										<div class="form-group col-md-3 ">
											<div>
												<input class="form-control keyword" name="keyword" id="keyword" type="text" placeholder="请输入订单号搜索" value="${keyword }" />
											</div>
										</div>
										<div class="form-group col-md-3 col-xs-12">
											<select class="select2able" name="auditStatus" id="auditStatus">
												<option value="">请选择审核状态</option>
												<option value="0" <c:if test="${auditStatus == 0 }">selected</c:if>>提交申请</option>
												<option value="1" <c:if test="${auditStatus == 1 }">selected</c:if>>审核中</option>
												<option value="2" <c:if test="${auditStatus == 2 }">selected</c:if>>通过</option>
												<option value="3" <c:if test="${auditStatus == 3 }">selected</c:if>>打回</option>
											</select>
										</div>
										<div class="form-group col-md-3 col-xs-3">
											<select class="select2able" name="departmentId" id="departmentId">
												<option value="">请选择部门</option>
												<c:forEach var="department" items="${departments}">
													<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime" type="text" placeholder="请选择申请起始时间" />
										</div>
										<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime" type="text" placeholder="请选择申请结束时间" />
										</div>
										<div class="form-group col-md-4 pull-right col-xs-12">
											<select class="select2able" name="payChannel" id="payChannel">
                                                <option value="" <c:if test="${payChannel == null }">selected</c:if>>代付通道</option>
                                                <c:forEach var="item" items="${payChannels }">
                                                    <option value="${item.featureName}" <c:if test="${payChannel == item.featureName }">selected</c:if>>${item.description}</option>
                                                </c:forEach>
                                            </select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${auditStartTime }" name="auditStartTime" id="auditStartTime" type="text" placeholder="请选择审核起始时间" />
										</div>
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${auditEndTime }" name="auditEndTime" id="auditEndTime" type="text" placeholder="请选择审核结束时间" />
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
											<th width="50">ID</th>
											<th width="190px">订单号</th>
											<th>真实姓名</th>
											<th>订单金额(元)</th>
											<th>余额支付金额(元)</th>
											<th>授信支付金额(元)</th>
											<th>红包金额(元)</th>
											<th>第三方支付金额(元)</th>
											<th>代付通道</th>
											<th>订单状态</th>
											<th>申请原因</th>
											<th width="160px">申请时间</th>
											<th>审核状态</th>
											<th>审核人员</th>
											<th>审核说明</th>
											<th width="160px">审核时间</th>
											<th width="120">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td>${i.id }</td>
												<td>
													<a href="${basePath}shop/orderDetail?id=${i.order_id}">${i.order_no }</a>
												</td>
												<td>
													<a href="${basePath}user/detail/app?id=${i.user_id}">${i.user_name }</a>
												</td>
												<td>${i.total_money }</td>
												<td>${i.balance_pay_money }</td>
												<td>${i.credit_pay_money }</td>
												<td>${i.hongbao_money }</td>
												<td>${i.real_pay_money }</td>
												<td>
		                                            <c:forEach var="item" items="${payChannels }">
		                                                <c:if test="${i.pay_channel == item.featureName }">${item.description}</c:if>
		                                            </c:forEach>
		                                        </td>
												<td>
													<c:if test="${i.state == 0 }">
														<span class="label label-default">未支付</span>
													</c:if>
													<c:if test="${i.state == 1 }">
														<span class="label label-info">支付中</span>
													</c:if>
													<c:if test="${i.state == 2 }">
														<span class="label label-success">已支付</span>
													</c:if>
													<c:if test="${i.state == 3 }">
														<span class="label label-info">拣货中</span>
													</c:if>
													<c:if test="${i.state == 4 }">
														<span class="label label-primary">已发货</span>
													</c:if>
													<c:if test="${i.state == 5 }">
														<span class="label label-danger">订单取消</span>
													</c:if>
													<c:if test="${i.state == 6 }">
														<span class="label label-warning">订单退款</span>
													</c:if>
													<c:if test="${i.state == 7 }">
														<span class="label label-success">交易完成</span>
													</c:if>
													<c:if test="${i.state == 8 }">
														<span class="label label-info">退款中</span>
													</c:if>
												</td>
												<td>${i.reason }</td>
												<td>
													<fmt:formatDate value="${i.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<c:if test="${i.status == 0 }">
														<span class="label label-warning">提交申请</span>
													</c:if>
													<c:if test="${i.status == 1 }">
														<span class="label label-info">审核中</span>
													</c:if>
													<c:if test="${i.status == 2 }">
														<span class="label label-success">通过</span>
													</c:if>
													<c:if test="${i.status == 3 }">
														<span class="label label-danger">打回</span>
													</c:if>
												</td>
												<td>
													<a href="${basePath}user/detail/app?id=${i.audit_user_id}">${i.audit_user_name }</a>
												</td>
												<td class="numberline">${i.audit_remark }</td>
												<td>
													<fmt:formatDate value="${i.update_date }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<shiro:hasPermission name="shop:refundAudit">
														<a>
															<c:if test="${i.status == 0||i.status == 1 }">
																<button class="btn btn-primary btn-xs" onclick="audit('${i.id}')" data-toggle="modal" data-target="#auditModal">审核</button>
															</c:if>
														</a>
													</shiro:hasPermission>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<form id="refundAudit" action="${basePath}shop/refundAudit" method="post">
								<input name="auditRemark" type="hidden" />
								<input name="id" type="hidden" />
								<input name="auditStatus" type="hidden" />
							</form>
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
	function auditConfirm(i){
		{
			  var id = $('#aid').val();
			  if(i == 2){
				  var r=confirm("确认同意退款吗?");
			  } else{
				  var r=confirm("确认拒绝退款吗?");
			  }
			  var status = i==2?2:3;
			  if (r)
			    {
				  	$("[name=auditRemark]").val($('#remark').val());
				  	$("[name=id]").val(id);
				  	$("[name=auditStatus]").val(status);

				  	$("#refundAudit").submit();
			    }
			  }
	}

	function audit(id){
		console.log(id)
		$('#aid').val(id);
	}
    function search() {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var auditStartTime = $("#auditStartTime").val();
        var auditEndTime = $("#auditEndTime").val();
        if(startTime!="" && endTime!=""){
            if(startTime > endTime){
                alert("申请结束时间必须大于等于开始时间");
                return;
            }
        }
        if(auditStartTime!="" && auditEndTime!=""){
            if(auditStartTime > auditEndTime){
                alert("审核结束时间必须大于等于开始时间");
                return;
            }
        }
        $("#form").submit();
    };
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
                return "refundList?trueName=${trueName}&keyword=${keyword}&auditStatus=${auditStatus}"
					+"&startTime=${startTime}&endTime=${endTime}&auditStartTime=${auditStartTime}"
					+"&auditEndTime=${auditEndTime}&departmentId=${departmentId}&payChannel=${payChannel}&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });

        if (${!empty flag}) {
            if (${flag==0}) {
                alert("审核成功");
                window.location.href = "${basePath}shop/refundList";
            } else if(${flag==-1}) {
                alert("审核失败");
            }
        }
    });

</script>
	<script>
	function yyccheck(){
		var flag = false;
    	var trueName = $("#trueName").val();
    	var keyword = $("#keyword").val();
    	var auditStatus = $("#auditStatus").val();
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
        var auditStartTime = $("#auditStartTime").val();
        var auditEndTime = $("#auditEndTime").val();
        var departmentId = $("#departmentId").val();
        var payChannel = $("#payChannel").val();
		if(startTime!="" && endTime!=""){
			if(startTime<=endTime){
    			flag = true;
    		}else{
    			alert("申请结束时间必须大于等于开始时间");
    			return;
    		}
    	}
        if(auditStartTime!="" && auditEndTime!=""){
            if(auditStartTime<=auditEndTime){
                flag = true;
            }else{
                alert("审核结束时间必须大于等于开始时间");
                return;
            }
        }
		if(flag){
    		location.href="${basePath}report/exportRefundList?trueName="+trueName+"&keyword="+keyword+"&auditStatus="+auditStatus
				+"&startTime="+startTime+"&endTime="+endTime+"&auditStartTime="+auditStartTime+"&auditEndTime="+auditEndTime
				+"&departmentId=" + departmentId +"&payChannel=" + payChannel;
    	}else{
    		alert("时间区间为必选项");
    	}
	}
</script>
</body>
</html>
