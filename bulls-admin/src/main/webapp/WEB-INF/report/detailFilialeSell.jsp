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
<title>分公司售牛详情 </title>
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
				<h1>分公司售牛详情 </h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							分公司售牛详情
							<shiro:hasPermission name="invest:export:filialeSellDetail">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" href="javascript:;" onclick="report()">
									<i class="icon-plus"></i>
									导出Excel
								</a> 
							</shiro:hasPermission>
							<shiro:hasPermission name="user:addRelationUser">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="#" 
								   data-toggle="modal" data-target="#addRelationDiv" >
									<i class="icon-plus"></i>
									添加关联用户
								</a>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="filialeSellDetail">
								<div class="row">
									<div class="form-group col-md-6">
										<input class="form-control keyword" name="keyword" type="text" placeholder="请输入姓名/手机号" value="${keyword }"/>
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
								<input type="hidden" value="${empId }" name="empId"/>
								<input type="hidden" id="page" name="page" value="1"/>
							</form>
							<table class="xxx table table-bordered table-hover">
								<thead>
									<tr>
										<th>用户ID</th>
										<th>投资ID</th>
										<th>项目名称</th>
										<th>用户名/手机号</th>
										<th width="8%">姓名</th>
										<th width="13%">领养时间</th>
										<th>领养只数</th>
										<th>领养期限</th>
										<th>领养金额</th>
										<th>到期否</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="g" items="${list }" varStatus="v">
										<tr>
											<td>${g.userId }</td>
											<td>${g.investId }</td>
											<td>${g.title}</td>
											<td>${g.mobile}</td>
											<td>${g.realName}</a></td>
											<td> <fmt:formatDate value="${g.createDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
											<td>${g.number}</td>
											<td>${g.limitDays}</td>
											<td>${g.amount}</td>
											<td>
												<c:if test="${g.isExpire == 1}">是</c:if>
												<c:if test="${g.isExpire == 0}">否</c:if>
											</td>
											 
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<ul id="pagination" style="float: right"></ul>
							<div>
								<span style="font-size: 18px">售牛总金额：</span>
								<span style="font-size: 18px;color: red">${totalAmount }</span>&nbsp;元
							</div>
						</div>
						<div class="form-group">
                                <label class="control-label col-md-2"></label>
                                 <a  class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">返回</a>
                            </div>
					</div>
				</div>
				<!-- end DataTables Example -->
			</div>
		</div>
		<div class="modal fade" id="addRelationDiv" style="width:70%; height: 80%;margin: auto;" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-content">
					<div class="widget-content padded clearfix">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">添加销售人员</h4>
					</div>
						<div class="modal-body">
							<form method="post" class="form-horizontal" action="${basePath}user/addRelationUser" id="validate-form">
								 <div class="heading">
									<i class="icon-table"></i>
									用户
									<!--  -->
									<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/>
									<button class="btn btn-primary pull-right hidden-xs searchUser" type="button" >搜索</button>
									<div id="myModal">
										<form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="formRelation"  action="${basePath}user/list/addrelation">
											<input type="hidden" name="page" value="1" />
											 
											<div class="row">
												<div class="form-group col-md-5">
												</div>
												<div class="form-group col-md-6">
													<div>
														<input name="keyword" id="keyword" type="text" placeholder="请输入真实姓名、手机号搜索" class="form-control keyword" value="${keyword }" />
													</div>
												</div>
											</div>
											<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg " onclick="bb()" type="button">搜索</button>
											<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
										</form>
									</div>
									<div class="table-responsive">
									<table class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>
													<input type="checkbox" id="allcheck" onclick="checkdis();" style="display: block; float: left;">
												</th>
												<th>ID</th>
												<th>真实姓名</th>
												<th>手机号</th>
												<th>性别</th>
												<th>注册时间</th>
												<th>最后登录时间</th>
												<th>会员等级</th>
											</tr>
										</thead>
										
										<tbody class="tableContent">
										</tbody>
									</table>
								</div>
							</form>
						</div>
					</div>
					<div class="modal-footer">
						<!-- data-dismiss="modal" -->
						<button type="button" onclick="submitAddRelation()" class="btn btn-primary">确认</button>
						<button type="button" onclick="cancelAdd()" class="btn">取消</button>
					</div>
				</div>
				<!-- /.modal-content -->
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
			}

			$(function() {
				 $('.searchUser').click(function(){
					 var keyword = $("#keyword").val();
					 if(keyword == '' || keyword == null){
						 alert("请输入手机号/姓名搜索");
						 return;
					 }
				     $.ajax({
		                 url: '${basePath}user/list/addrelation?keyword='+keyword,
		                 type: "GET",
		                 dataType: "html",
		                 async: false,
		                 success: function (data) {
		                	var jsonData = JSON.parse(data);
		                	if(jsonData.status == 'error'){
		                		alert(jsonData.message);
		                		return;
		                	}
		                 	var str = "";
		                 	$.each(JSON.parse(data).users,function(i,ele){
		                 		var sex = '';
		                 		if(ele.sex === 1){
		                 			sex = '男';
		                 		}else if(ele.sex === 0){
		                 			sex = '女';
		                 		}else{
		                 			sex = '未知';
		                 		}
		                 		str+="<tr>"+
		                 		"<td><input class='checknum' name='choice' onclick='setAll()' type='checkbox' value='"+ele.id+"'  style='display: block; float: left;'></td>"+
		                 		"<td>"+ele.id+"</td>"+
		                 		"<td>"+ele.trueName+"</td>"+
		                 		"<td>"+ele.phone+"</td>"+
		                 		"<td>"+sex+"</td>"+
		                 		"<td>"+getLocalTime(ele.create_date)+"</td>"+
		                 		"<td>"+getLocalTime(ele.last_login_time)+"</td>"+
		                 		"<td>"+ele.LEVEL+"</td>"+
		                 		
		                 		"</tr>";
		                 	})
		                 	$(".tableContent").html(str);
		                 }
		             }); 
				 })
				
				
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
										return "filialeSellDetail?empId=${empId}&keyword=${keyword}&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>&page="
												+ page;
									}
								});
 
			});
			function report() {
				var startDate = $.trim($('#startDate').val());
				var endDate = $.trim($('#endDate').val()); 
				var endAge = $.trim($('#endAge').val());
				window.location.href = "${basePath}report/export/filialeSellDetail?empId=${empId}&keyword=${keyword}&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>";
				
			}
			function getLocalTime(nS) {  
				 return new Date(parseInt(nS)).toLocaleString().replace(/:\d{1,2}$/,' ');  
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
	 
			function cancelAdd(){
				 $('#addRelationDiv').find('.close').click();
			}
			
			function setAll(){
			    if(!$(".checknum").checked){
			    	$("#allcheck").prop("checked",false); // 子复选框某个不选择，全选也被取消
			    }
			    var choicelength=$("input[type='checkbox'][class='checknum']").length;
			    var choiceselect=$("input[type='checkbox'][class='checknum']:checked").length;

			    if(choicelength==choiceselect){
			   		 $("#allcheck").prop("checked",true);   // 子复选框全部部被选择，全选也被选择；1.对于HTML元素我们自己自定义的DOM属性，在处理时，使用attr方法；2.对于HTML元素本身就带有的固有属性，在处理时，使用prop方法。
			    }
		    }
			 //点击全选，子复选框被选中
		    function checkdis(){
		   		 var allcheck=document.getElementById("allcheck");
		   	     var choice=document.getElementsByName("choice");
		         for(var i=0;i<choice.length;i++){
		        	 if(choice[i].disabled==false){
		             	choice[i].checked=allcheck.checked;
		        	 }
		          }
		    }
			function submitAddRelation(){
				//获取input类型是checkBox并且 name="choice"选中的checkBox的元素
	            var userIds = $('input:checkbox[name="choice"]:checked').map(function () {
	                return $(this).val();
	            }).get().join(",");
				console.log(userIds);
	            //弹出结果
				if(userIds.length == 0){
					alert("请选择需要分配的用户");
					return;
				}
				if (confirm("确认分配?")) {
					$.ajax({
	                    url: '${basePath}user/addRelationUser',
	                    type: "POST",
	                    data: "userIds="+userIds+"&empId="+${empId}, 
	                    success: function (data) {
	                    	var result = $.parseJSON(data);
	                        if (result.status == 'error') {
	                            alert(result.message);
	                        } else {
	                            alert("操作成功");
	                            window.location.href="filialeSellDetail?empId=${empId}"; 
	                          //  $('#addRelationUser').find('.close').click();
	                        }
	                    }
	                });
				}
			} 
		</script>
</body>
</html>