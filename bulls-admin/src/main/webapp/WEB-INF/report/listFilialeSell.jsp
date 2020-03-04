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
<title>分公司售牛统计</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.table {
	table-layout: fixed;
}

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
				<h1>分公司售牛统计</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							分公司售牛统计
							<shiro:hasPermission name="invest:export:filialeSell">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" href="javascript:;" onclick="report()">
									<i class="icon-plus"></i>
									导出Excel
								</a> 
							</shiro:hasPermission>
							<shiro:hasPermission name="user:addSalesman">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="#" onclick="addSalesman()"
								   data-toggle="modal" data-target="#addSalesman">
									<i class="icon-plus"></i>
									添加销售人员 
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="filialeSell">
								<div class="row">
									<div class="form-group col-md-6">
										<input class="form-control keyword" name="keyword" type="text" placeholder="请输入手机号" value="${keyword }"/>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6">
										<input class="form-control datepicker" value="<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>"  name="startDate" id="startDate" type="text" placeholder="起始日期段" />
									</div>
									<div class="form-group col-md-6">
										<input class="form-control datepicker"  value="<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>" name="endDate" id="endDate" type="text" placeholder="结束日期段" />
									</div>
								</div>
 								<input type="hidden" id="page" name="page" value="1"/>
							</form>
							<table class="xxx table table-bordered table-hover">
								<thead>
									<tr>
										<th rowspan="2">序号</th>
										<th rowspan="2">所属公司</th>
										<th rowspan="2" width="8%">姓名</th>
										<th rowspan="2" width="13%">手机号</th>
										<th style="text-align:center;" colspan="8">售出牛只数</th>
										<th rowspan="2">合计售牛数(只)</th>
										<th rowspan="2">合计金额(元)</th>
									</tr>
									<tr>
										<th>30天</th>
										<th>30天总金额</th>
										<th>90天</th>
										<th>90天总金额</th>
										<th>180天</th>
										<th>180天总金额</th>
										<th>360天</th>
										<th>360天总金额</th>
									</tr>
									
									
								</thead>
								<tbody>
									<c:forEach var="g" items="${list }" varStatus="v">
										<tr>
											<td>${g.empId }</td>
											<td>${g.company}</td>
											<td><a href="filialeSellDetail?empId=${g.empId}&keyword=${keyword }&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>">${g.realName}</a></td>
											<td>${g.mobile}</td>
											<td >${g.limitDays30}</td>
											<td >${g.investTotalAmount30}</td>
											
											<td>${g.limitDays90}</td>
											<td>${g.investTotalAmount90}</td>
											
											<td>${g.limitDays180}</td>
											<td>${g.investTotalAmount180}</td>
											
											<td>${g.limitDays360}</td> 
											<td>${g.investTotalAmount360}</td>
											<td>${g.limitDays30 + g.limitDays90 +g.limitDays180 + g.limitDays360}</td>
											<td>${g.investTotalAmount30 + g.investTotalAmount90 +g.investTotalAmount180 + g.investTotalAmount360}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<ul id="pagination" style="float: right"></ul>
						</div>
					</div>
				</div>
				<!-- end DataTables Example -->
			</div>
		</div>
		
		
		<div class="modal fade" id="addSalesman" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="widget-content padded clearfix"><div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">添加销售人员</h4>
					</div>
						<div class="modal-body">
							<form method="post" class="form-horizontal" action="${basePath}user/addSalesman" id="validate-form">
								<div class="form-group">
									<label class="control-label col-md-4">所属公司</label>
									<div class="col-md-7">
										<select class="select2able" name="company" id="company" required />
											<option value="">请选择</option>
											<option value="温州">温州</option>
											<option value="宁德">宁德</option>
											<option value="枣庄">枣庄</option>
											<option value="上海">上海</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">姓名</label>
									<div class="col-md-7">
										<input class="form-control vip-dividend" name="name" id="name" placeholder="请输入销售人员姓名" type="text" />
									 
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-4">手机号</label>
									<div class="col-md-7">
										<input class="form-control vip-dividend" name="mobile" id="mobile" placeholder="请输入手机号" type="text" />
									</div>
								</div>
							</form>
							</div>
						</div>
					<div class="modal-footer">
						<!-- data-dismiss="modal" -->
						<button type="button" onclick="submitSalesman('OK')" class="btn btn-primary">确认</button>
						<button type="button" onclick="cancel()" class="btn">取消</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
						
						
		<script src="${basePath}js/jquery-1.10.2.min.js"></script>
		<script src="${basePath}js/jquery-ui.js"></script>
		<script src="${basePath}js/bootstrap-paginator.min.js"></script>
		<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
		<script src="${basePath}js/select2.js" type="text/javascript"></script>
		<script type="text/javascript">
			function search() {
				if(!checkDate()){
					alert("请选择正确时间");
					return;
				}
				$("#form").submit();
			};

			$(function() {
				/* $(".datepicker").datepicker({
			          language: "zh-CN",
			          todayHighlight: true,
			          format: 'yyyy-mm-dd',
			          autoclose: true, 
			          startView: 'months',
			          maxViewMode:'years',
			          minViewMode:'months'
				});*/
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
				$('#pagination')
						.bootstrapPaginator(
								{
									currentPage : parseInt('${page}'),
									totalPages : parseInt('${pages}'),
									bootstrapMajorVersion : 3,
									alignment : "right",
									pageUrl : function(type, page, current) {
										return "filialeSell?startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>&page="
												+ page;
									}
								});
 
			});
			function report() {
				var startDate = $.trim($('#startDate').val());
				var endDate = $.trim($('#endDate').val()); 
				var endAge = $.trim($('#endAge').val());
				window.location.href = "${basePath}report/export/filialeSell?startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>";
				
			}
	 
			function checkDate() {
				var startDate = $('#startDate').val();
				var endDate = $('#endDate').val();
		 
				if(startDate!= '' && endDate!=  ''){
					if (startDate > endDate) {
						return false;
					} 
				}	
				return true;
			}
			
			function cancel(){
				 $('#addSalesman').find('.close').click();
			}
			function submitSalesman(opt){
				var name = $("#name").val();
				var mobile = $("#mobile").val();
				var company = $("#company").val();
				if(name == null || name == ''){
					alert("请输入姓名");
					return false;
				}
				if(mobile == null || mobile == ''){
					alert("请输入手机号");
					return false;
				}
				if(company == null || company == ''){
					alert("请选择公司");
					return false;	
				}
				//$("#validate-form").submit();
				if (confirm("确认添加?")) {
					$.ajax({
	                    url: '${basePath}user/addSalesman',
	                    type: "POST",
	                    data: $('#validate-form').serialize(), 
	                    success: function (data) {
	                    	var result = $.parseJSON(data);
	                        if (result.status == 'error') {
	                            alert(result.message);
	                        } else {
	                            alert("操作成功");
	                            $('#addSalesman').find('.close').click();
	                        }
	                    }
	                });
				}
			} 
		</script>
</body>
</html>