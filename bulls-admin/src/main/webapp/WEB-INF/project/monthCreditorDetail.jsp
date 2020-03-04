<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>生成债权</title>
    <style type="text/css">
    .form-group [class*="col-"] {
      padding-top: 6px;
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
            <h1>
                		生成债权
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>未匹配资金的债权情况
                    </div>
                    <div class="row">
	                    <div class="col-xs-12 col-md-6" style="text-align: center;margin-top: 50px;">
						    	<h2 class="post-title">
						    	   资产池的债权资金量
						    	</h2>
						    	<h3 class="post-title">
						    		￥${totalAmount}(元)
						    	</h2>
				       </div>
				       <form action="getProjectCreditor" id="form" method="get">
				        <div class="col-xs-5 pull-right">
							<div class="col-md-5 pull-left">
								<input class="form-control datepicker" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>"
									name="startTime" id="startDate" type="text"
									placeholder="开始时间" /> 
							</div>
							<div class="col-md-5 pull-left" >
									<input class="form-control datepicker" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
									name="endTime" id="endDate" type="text"
									placeholder="截止时间" />
							</div>
				            <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()"> 搜索</button>
						</div>
						</form>
				       <div class="col-xs-12 col-md-6" style="text-align: center;margin-top: 20px;">
					    	<h2 class="post-title">
					    		借款项目到期金额
					    	</h2>
					        <h3 class="post-title" >
	                         	  <c:if test="${not empty  amount}">
	                         	               ￥${amount}
	                         	  </c:if>(元)
					        </h3>
					   </div>
				   </div>
				   
            </div>
            <div class="widget-container fluid-height ">
                    <div class="heading">
                        <i class="icon-table"></i>待发布借款项目列表
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered trade" id="trade">
                            <thead>
                            <tr>
                             	<th><input type="checkbox" id="allcheck"  onclick="checkdis()" style="display: block;float: left;"> 全选</th>
                                <th>序号</th>
                               <!--  <th>ID</th> -->
                                <th>标题</th>
                                <th>总金额(元)</th>
                                <th>期限(天)</th>
                                <th>年化</th>
                                <th>申请人</th>
                                <th>申请时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${projectList }" var="pro" varStatus="index">
	                             <tr>
	                              <td>
                                     <input class="checknum"  name="choice"  type="checkbox" value="${pro.id}" style="display: block;" />
                                  </td>
	                              <td>${index.index+1}</td>
	                              <%-- <td>${pro.id}</td> --%>
	                              <td>${pro.title}</td>
	                              <td>${pro.totalAmount}</td>
	                              <td>${pro.limitDays}</td>
	                              <td>${pro.annualized}</td>
	                              <td>${pro.userName}</td>
	                              <td><fmt:formatDate value="${pro.createTime}" pattern="yyyy-MM-dd  hh:mm:ss"/></td>
	                             </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <button class="btn btn-primary hidden-xs" type="button"  style="margin-left:100px;" onclick="generateCreditor()"> 生成债权</button>
                    </div>
                </div>
        </div>
    </div>
    <!-- end DataTables Example -->
</div>
</div>
<div class="modal fade" tabindex="-1" role="dialog" id="resultModal">
  <div class="modal-dialog modal-lg" role="document" style="width:400px; height:300px;text-align: center;">
    <div class="modal-content">
      <div class="modal-header">	
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">确认生成债权</h4>
      </div>
      <div class="modal-footer">
      	<button class="btn btn-primary" id="add-picture" onclick="submit()">提交</button>
      	<button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
   	    </div>
      </div>
    </div>
  </div>
</div>
<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
function bb() {
    $("#form").submit();
}
function submit(){
	var projecIds="";
	 $('input:checkbox').each(function() {
	        if ($(this).is(':checked') ==true&&$(this).prop("disabled")==false&&$(this).attr("ID")!='allcheck') {
	        	projecIds+=","+$(this).val();
	        }
		});
	if(projecIds==''){
		alert("请至少一位勾选");
		return;
	}
	if (confirm("您确定生成债权么?")) {
	 $("#add-picture2").attr("disabled","disabled"); 
       jQuery.ajax({
           url: '${basePath}project/generateProjectCreditor',
           type: "POST",
           data: "projectIdsStr="+projecIds,
           dataType: "json",
           success: function (data) {
               if (data == "1") {
                   alert("生成债权成功");
               }else{
            	   alert("生成债权失败"); 
               }
               $("#add-picture2").removeAttr("disabled");
        	   $("#resultModal").modal("hide");
        	   location.reload();
           }
       });
   }}
	function generateCreditor(){
		 var uids="";
		 $('input:checkbox').each(function() {
		        if ($(this).is(':checked') ==true&&$(this).prop("disabled")==false&&$(this).attr("ID")!='allcheck') {
		             uids+=","+$(this).val();
		        }
			});
		if(uids==''){
			alert("请至少一位勾选用户");
			return;
		}
		$("#resultModal").modal("show");
	}
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
      //点击全选，子复选框被选中
        
    });
    function checkdis(){
  		 var allcheck=document.getElementById("allcheck");
  	     var choice=document.getElementsByName("choice");
        for(var i=0;i<choice.length;i++){
       	 if(choice[i].disabled==false){
             choice[i].checked=allcheck.checked;
       	 }
         }
   }
   //点击子复选框,全选框 选中、取消
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

</script>
</body>
</html>