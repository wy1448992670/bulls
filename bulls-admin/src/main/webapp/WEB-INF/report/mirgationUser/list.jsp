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
<title>迁移用户统计</title>
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
				<h1>报表统计</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							迁移用户统计
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath}images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
							<%--<shiro:hasPermission name="report:mirgationUser:export">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" href="javascript:;" onclick="report()">
									<i class="icon-plus"></i>
									导出Excel
								</a>
							</shiro:hasPermission>--%>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
							<shiro:hasPermission name="user:migrationImport">
								<span   class="pull-right btn btn-success fileinput-button clickFile">
						                    <i class="glyphicon glyphicon-plus"></i>
						                    <span>迁移数据导入</span>
						        </span>
					        </shiro:hasPermission>
					        <input style="display:none;" type="file" name="file" id="zy_fileupload" >
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline col-lg-4 col-xs-11 pull-right" id="form"  action="${basePath}report/mirgationUser/list">
									<input type="hidden" name="page" value="1"/>					
									<div class="row">
										<div class="form-group col-md-6">
										</div>
										<div class="form-group col-md-6">
											<input class="form-control datepicker" value="${mirgationTime}" name="mirgationTime" id="mirgationTime" type="text" placeholder="转移日期" />
										</div>
									</div>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							  </div>	
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>真实姓名</th>
											<th>手机号</th>
											<th>身份证</th>
											<th>转移时间</th>								
											<th>转移的余额</th>								
											<th>转移的投资数</th>
											<th>转移的投资总本金</th>
											<th>转移的投资待收本金</th>								
											<th>转移的投资待收利息</th>						
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td><a href="${basePath}user/detail/app?id=${i.id}">${i.true_name}</a></td>
												<td>${i.phone}</td>
												<td>${i.identity_card}</td>
												<td>${i.migration_time}</td>
												<td>${i.migration_balance_amount}</td>
												<td>${i.count_investment}</td>
												<td>${i.sum_investment_amount}</td>
												<td>${i.sum_receive_corpus}</td>
												<td>${i.sum_receive_interest}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<ul id="pagination" style="float: right"></ul>
						</div>
					</div>
				</div>
				<!-- end DataTables Example -->
			</div>
		</div>
		<script src="${basePath}js/jquery-1.10.2.min.js"></script>
		<script src="${basePath}js/jquery-ui.js"></script>
		<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
		<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
		<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
		<script src="${basePath}js/bootstrap-paginator.min.js"></script>
		<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
		<script src="${basePath}js/select2.js" type="text/javascript"></script>
		<script type="text/javascript">
			function search() {
                if(!checkDate()){
                    alert('迁移时间不能大于当前时间');
                    return false;
                }
				$("#form").submit();
			}
			
            function checkDate() {
                var mirgationTime = $('#mirgationTime').val();
                var now = new Date();
                if(mirgationTime!= ''){
                    var date=new Date(mirgationTime.replace("/-/g", "/"));
                    if (date > now) {
                        return false;
                    }
                }
                return true;
            }

			$(function() {
				$('.clickFile').click(function(){
					$('#zy_fileupload').click();
				});
				$('#zy_fileupload').change(function(){
					if($('#zy_fileupload').val() == '')return false;
					var fileName = $('#zy_fileupload').val();
					var extName="";
					if(fileName.lastIndexOf(".")!=-1){
						extName= fileName.substr(fileName.lastIndexOf(".") + 1).toLowerCase();
					}
					if(extName != "mujson" ) {
						alert('只能迁移.mujson的文件');
						return;
					}
					var formData = new FormData();
					formData.append('file',$('#zy_fileupload').get(0).files[0]);
					$.ajax({
						url: "${basePath}user/migrationImport",
						type: "POST",
						data: formData,
						processData: false,
						contentType: false,
						success: function (response) {
							var data = JSON.parse(response);
        					alert(data.message);
        					if (data.status == "error"){
        					}else if (data.status == "success"){
        					}
        				}
					});
				});
				
                $(".datepicker").datepicker({
                    format: 'yyyy-mm-dd',
                    showSecond: true,
                    timeFormat: "hh:mm:ss",
                    dateFormat: "yy-mm-dd"
                });
                $('.select2able').select2();
				$(".keyword").keyup(function(e) {
					e = e || window.e;
					if (e.keyCode == 13) {
						$("#form").submit();
					}
				});

				$("#mirgationTime").keyup(function(e) {
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
                        return "mirgationUser/list?&mirgationTime=${mirgationTime}&page=" + page;
                    }
                });

			});
			function report() {
				if(!checkDate()){
                    alert('迁移时间不能大于当前时间');
					return false;
				}
				
				var startDate = $.trim($('#startDate').val());
				var endDate = $.trim($('#endDate').val());
				window.location.href = "${basePath}report/reportInvestStatement?startDate=" + startDate + "&endDate=" + endDate;
			}

			function isEmpty(obj){
				if(obj==null||obj==''){
					return true;
				}else{
					return false;
				}
			}
		</script>
</body>
</html>
