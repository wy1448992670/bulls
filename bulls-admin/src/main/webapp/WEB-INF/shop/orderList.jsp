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
<title>商城订单查询</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/multSelect.css" media="all" rel="stylesheet" type="text/css" />
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
							订单查询列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />
							<shiro:hasPermission name="report:exportOrderList">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()" href="JavaScript:;">
									<i class="icon-plus"></i>
									导出Excel
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline  col-lg-5 pull-right col-xs-11" id="form" action="orderList">
									<input type="hidden" name="page" value="1" />

									<div class="row">
										<div class="form-group col-md-4  col-xs-12">
											<div>
												<input class="form-control keyword" name="trueName" id="trueName" type="text" placeholder="请输入真实姓名搜索" value="${trueName }" />
											</div>
										</div>
										<div class="form-group col-md-5 col-xs-12">
											<div>
												<input class="form-control keyword" name="keyword" id="keyword" type="text" placeholder="请输入订单号搜索" value="${keyword }" />
											</div>
										</div>
										<div class="form-group col-md-3 pull-right col-xs-12">
											<div id="statusSelect" class="mySelect" placeholder="选择状态" ></div> 
											<input name="status" id="status" type="hidden" value="${status}" />
										</div>
								
									</div>
									<div class="row">
										<div class="form-group col-md-4 col-xs-6">
											<input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime" type="text" placeholder="请选择订单创建起始时间" />
										</div>
										<div class="form-group col-md-4 col-xs-6">
											<input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime" type="text" placeholder="请选择订单创建结束时间" />
										</div>
										<div class="form-group col-md-4 pull-right col-xs-12">
											<select class="select2able" name="expressWay" id="expressWay">
												<option value="">请选择快递方式</option>
												<option value="1" <c:if test="${expressWay == 1 }">selected</c:if>>包邮</option>
												<option value="0" <c:if test="${expressWay == 0 }">selected</c:if>>不包邮</option>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-4 col-xs-6">
											<input class="form-control datepicker" value="${payStartTime }" name="payStartTime" id="payStartTime" type="text" placeholder="请选择付款成功起始时间" />
										</div>
										<div class="form-group col-md-4 col-xs-6">
											<input class="form-control datepicker" value="${payEndTime }" name="payEndTime" id="payEndTime" type="text" placeholder="请选择付款成功结束时间" />
										</div>
										<div class="form-group col-md-4 pull-right col-xs-12">
											<select class="select2able" name="departmentId" id="departmentId">
												<option value="">请选择部门</option>
												<c:forEach var="department" items="${departments}">
													<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-4 col-xs-6">
											<input class="form-control datepicker" value="${refundFinishStartTime }" name="refundFinishStartTime" id="refundFinishStartTime" type="text" placeholder="请选择退单成功起始时间" />
										</div>
										<div class="form-group col-md-4 col-xs-6">
											<input class="form-control datepicker" value="${refundFinishEndTime }" name="refundFinishEndTime" id="refundFinishEndTime" type="text" placeholder="请选择退单成功结束时间" />
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
											<th>商品种类个数</th>
											<th>订单金额(元)</th>
											<th>余额支付金额(元)</th>
											<th>授信支付金额(元)</th>
											<th>红包金额(元)</th>
											<th>第三方支付金额(元)</th>
											<th>代付通道</th>
											<th>订单状态</th>
											<th>快递方式</th>
											<th width="160px">购买时间</th>
											<th width="120">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td>${i.id }</td>
												<td>${i.order_no }</td>
												<td>
													<a href="${basePath}user/detail/app?id=${i.user_id}">${i.true_name }</a>
												</td>
												<td>${i.count }</td>
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
												<td>
													<c:if test="${i.total_money >= 200 }">
														<span class="label label-primary">包邮</span>
													</c:if>
													<c:if test="${i.total_money < 200 }">
														<span class="label label-default">不包邮</span>
													</c:if>
												</td>
												<td>
													<fmt:formatDate value="${i.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<shiro:hasPermission name="shop:orderDetail">
														<a href="${basePath}shop/orderDetail?id=${i.id}">详情</a>&nbsp;
                                    				</shiro:hasPermission>

													<shiro:hasPermission name="shop:sendGoods">
														<c:if test="${i.state == 2 }">
															<a class="btn btn-warning fix" rid="${i.id}" href="${basePath}shop/sendGoods?id=${i.id }">发货</a>
														</c:if>
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
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script src="${basePath}js/multSelect.js" type="text/javascript"></script>
	<script type="text/javascript">

    function search() {
        $("#form").submit();
    };
    
    $(function () {
    	
    	var statusSelect= $("#statusSelect").mySelect({
            mult:true,//true为多选,false为单选
            option:[//选项数据
                {label:"未支付",value:0},
                {label:"支付中",value:1},
                {label:"已支付",value:2},
                {label:"退款中",value:8},
                {label:"拣货中",value:3},
                {label:"已发货",value:4},
                {label:"订单取消",value:5},
                {label:"订单退款",value:6},
                {label:"交易完成",value:7}
            ],
            onChange:function(res){//选择框值变化返回结果
  				var res = res.join(',');
            	console.log(res);
            	$("#status").val(res);
            }
        });
    	statusSelect.setResult([${status}]);

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
                return "orderList?trueName=${trueName}&keyword=${keyword}&status=${status}&startTime=${startTime}&endTime=${endTime}&expressWay=${expressWay}"
                +"&payStartTime=${payStartTime}&payEndTime=${payEndTime}&refundFinishStartTime=${refundFinishStartTime}&refundFinishEndTime=${refundFinishEndTime}"
                +"&departmentId=${departmentId}&payChannel=${payChannel}&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        
        if (${!empty flag}) {
            if (${flag==0}) {
                alert("发货成功");
                window.location.href = "${basePath}shop/orderList";
            } else if(${flag==-1}) {
                alert("发货失败");
            }
        }
    });

</script>
	<script>
	function yyccheck(){
		var flag = false;
    	var trueName = $("#trueName").val();
    	var keyword = $("#keyword").val();
    	var status = $("#status").val();
    	var expressWay = $("#expressWay").val();
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	var payStartTime = $("#payStartTime").val();
    	var payEndTime = $("#payEndTime").val();
    	var refundFinishStartTime = $("#refundFinishStartTime").val();
    	var refundFinishEndTime = $("#refundFinishEndTime").val();
    	var departmentId = $("#departmentId").val();
    	var payChannel = $("#payChannel").val();
		if(startTime!="" && endTime!=""){
			if(startTime>endTime){
    			alert("结束时间必须大于等于开始时间");
    			return;
    		}
    	}
    	location.href="${basePath}report/exportOrderList?trueName="+trueName+"&keyword="+keyword+"&status="+status+"&expressWay="+expressWay+"&startTime="+startTime+"&endTime="+endTime
    	+"&payStartTime="+payStartTime+"&payEndTime="+payEndTime+"&refundFinishStartTime="+refundFinishStartTime+"&refundFinishEndTime="+refundFinishEndTime+"&departmentId="+departmentId+"&payChannel="+payChannel;
    	
	}
</script>
</body>
</html>