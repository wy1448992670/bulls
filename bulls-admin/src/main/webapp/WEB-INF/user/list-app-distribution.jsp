<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>分配网站用户列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
    .loading{
		    position: relative;
		    left:40%;
		    top:40%;
            text-align:center;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            border: 5px solid #BEBEBE;
            border-left: 5px solid #498aca;
            animation: load 1s linear infinite;
            -moz-animation:load 1s linear infinite;
            -webkit-animation: load 1s linear infinite;
            -o-animation:load 1s linear infinite;
        }
        @-webkit-keyframes load
        {
            from{-webkit-transform:rotate(0deg);}
            to{-webkit-transform:rotate(360deg);}
        }
        @-moz-keyframes load
        {
            from{-moz-transform:rotate(0deg);}
            to{-moz-transform:rotate(360deg);}
        }
        @-o-keyframes load
        {
            from{-o-transform:rotate(0deg);}
            to{-o-transform:rotate(360deg);}
        }
    .confirm_modal {top: 50%!important;margin-top: -60px;}
	.confirm_modal .modal-body{padding: 20px 10px;text-align: center;}
	.confirm_modal .modal-footer{text-align: center;}
	.confirm_modal.modal{width: 360px;margin-left: -180px;}
	
	.modal-footer .btn{min-width: 80px;}
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
                奔富牧业用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>分配网站用户列表
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-md-6 pull-right" id="form"
                              action="${basePath}user/list/app/distribute">
                            <div class="row">
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="status">
                                            <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
                                            <option value="0" <c:if test="${status == 0 }">selected</c:if>>可用</option>
                                            <option value="1" <c:if test="${status == 1 }">selected</c:if>>不可用</option>
                                            <option value="2" <c:if test="${status == 2 }">selected</c:if>>已删除</option>
                                            <option value="3" <c:if test="${status == 3 }">selected</c:if>>测试帐号</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="type">
                                            <option value="" <c:if test="${type == null }">selected</c:if>>筛选条件</option>
                                            <option value="0" <c:if test="${type == 0 }">selected</c:if>>仅注册</option>
                                            <option value="1" <c:if test="${type == 1 }">selected</c:if>>仅实名认证</option>
                                            <option value="2" <c:if test="${type == 2 }">selected</c:if>>仅绑卡</option>
                                            <option value="3" <c:if test="${type == 3 }">selected</c:if>>账户余额大于0
                                            </option>
                                            <option value="4" <c:if test="${type == 4 }">selected</c:if>>账户余额大于50
                                            </option>
                                            <option value="5" <c:if test="${type == 5 }">selected</c:if>>活期账户余额大于0
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="level">
                                            <option value="" <c:if test="${level == null }">selected</c:if>>所有</option>
                                            <option value="0" <c:if test="${level == 0 }">selected</c:if>>普通用户</option>
                                            <option value="1" <c:if test="${level == 1 }">selected</c:if>>VIP1</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input name="keyword" type="text" placeholder="请输入用户昵称、真实姓名、手机号搜索"
                                               class="form-control keyword" value="${keyword }"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input name="startAmount" type="text" placeholder="请输入搜索用户投资额下限"
                                               class="form-control keyword" value="${startAmount }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input name="endAmount" type="text" placeholder="请输入搜索用户投资额上限"
                                               class="form-control keyword" value="${endAmount }"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>"
                                               id="startTime" name="startTime" type="text" placeholder="请选择注册起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
                                               id="endTime" name="endTime" type="text" placeholder="请选择注册结束时间"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${investStartTime }" pattern="yyyy-MM-dd"/>"
                                               id="investStartTime" name="investStartTime" type="text"
                                               placeholder="请选择充值起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="<fmt:formatDate value="${investndTime}" pattern="yyyy-MM-dd"/>"
                                               id="investndTime" name="investndTime" type="text"
                                               placeholder="请选择充值结束时间"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control" value="${codes}" id="codes" name="codes" type="text"
                                               placeholder="请输入渠道号"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control" value="${customerName}"
                                               name="customerName"
                                               type="text"
                                               placeholder="请输入坐席姓名"/>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                	<shiro:hasPermission name="user:adminfenpei">
                                    <th>
                                       <input type="checkbox" id="allcheck"  onclick="checkdis()" style="display: block;float: left;"> 全选
                                    </th>
                                    </shiro:hasPermission>
                                    <th>
                                        ID
                                    </th>
                                    <th>
                                        用户名
                                    </th>
                                    <th>
                                        真实姓名
                                    </th>
                                    <th>
                                        渠道来源
                                    </th>
                                    <th>
                                        账户余额
                                    </th>

                                    <th>
                                        活期投资
                                    </th>
                                    <th>
                                        散标投资
                                    </th>
                                    <th>
                                        绑卡状态
                                    </th>
                                    <th>
                                        手机号
                                    </th>
                                    <th>
                                        性别
                                    </th>
                                    <th>
                                        注册时间
                                    </th>
                                    <th>
                                        注册IP
                                    </th>

                                    <th>
                                        状态
                                    </th>
                                    <th>
                                        所属坐席
                                    </th>
                                    <th>
                                        推荐人用户名
                                    </th>
                                    <shiro:hasPermission name="user:editpayPassword">
                                        <th width="75">重置支付密码</th>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="user:edit:app">
                                        <th width="75"></th>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="user:freeze">
                                        <th width="75">冻结/解除冻结</th>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="user:unbund">
                                        <th width="75">操作</th>
                                    </shiro:hasPermission>
                                    <th width="75"></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${users }">
                                    <tr>
                                    	<shiro:hasPermission name="user:adminfenpei">
                                        <td>
                                                <input class="checknum"  name="choice"  <c:if test="${not empty user.tag or user.nodistri=='1'}">disabled="disabled" title="已分配和黑名单不能分配" </c:if> type="checkbox" value="${user.id}" style="display: block;" />
                                        </td>
                                        </shiro:hasPermission>
                                        <td>
                                                ${user.id }
                                        </td>
                                        <td>
                                            <c:if test="${user.level == 0}"><span
                                                    class="label label-default">V0</span></c:if>
                                            <c:if test="${user.level == 1}"><span
                                                    class="label label-danger">V1</span></c:if>
                                                ${user.username }

                                        </td>
                                        <td>
                                                ${user.trueName }
                                        </td>
                                        <td>
                                                ${user.code }
                                        </td>
                                        <td>
                                                ${user.availableBalance}
                                        </td>
                                        <td>
                                                ${user.huoInvestmentAmount}
                                        </td>
                                        <td>
                                                ${user.uncollectCapital}
                                        </td>
                                        <td>
                                            <c:if test="${user.bc_type ==0 }">
                                                <span class="label label-success">是</span>
                                            </c:if>

                                            <c:if test="${user.bc_type !=0 }">
                                                <span class="label label-danger">否</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <shiro:lacksPermission name="user:adminPhone">
                                                ${user.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                            </shiro:lacksPermission>
                                            <shiro:hasPermission name="user:adminPhone">
                                                ${user.phone }
                                            </shiro:hasPermission>
                                        </td>
                                        <td>
                                            <c:if test="${user.sex == 0 }">
                                                女
                                            </c:if>
                                            <c:if test="${user.sex == 1 }">
                                                男
                                            </c:if>
                                            <c:if test="${user.sex == 2 }">
                                                保密
                                            </c:if>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${user.registerTime }"
                                                            pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                                ${user.registerIp }
                                        </td>


                                        <td>
                                            <c:if test="${user.status == 0 }">
                                                <span class="label label-success">可用</span>
                                            </c:if>
                                            <c:if test="${user.status == 1 }">
                                                <span class="label label-warning">已冻结</span>
                                            </c:if>
                                            <c:if test="${user.status == 2 }">
                                                <span class="label label-danger">已删除</span>
                                            </c:if>
                                            <c:if test="${user.status == 3 }">
                                                <span class="label label-danger">测试帐号</span>
                                            </c:if>
                                        </td>
                                        <td>
                                                ${user.tag }
                                        </td>
                                        <td>
                                                ${user.uiUserName }
                                        </td>

                                        <shiro:hasPermission name="user:editpayPassword">
                                            <td>
                                                <a class="delete-row" href="javascript:void(0)"
                                                   onclick="aa(${user.id })">重置支付密码</a>
                                            </td>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="user:edit:app">
                                            <td>
                                                <c:if test="${user.status == 3 }">
                                                    <a class="delete-row"
                                                       href="${basePath}user/edit/app?id=${user.id }">编辑</a>
                                                </c:if>
                                            </td>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="user:freeze">
                                            <td>
                                                <c:if test="${user.status == 0 }">
                                                    <a class="delete-row" href="javascript:void(0)"
                                                       onclick="freeze(${user.id })">冻结</a>
                                                </c:if>
                                                <c:if test="${user.status == 1 }">
                                                    <a class="delete-row" href="javascript:void(0)"
                                                       onclick="unfreeze(${user.id })">解冻</a>
                                                </c:if>
                                            </td>

                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="user:unbund">
                                            <td>
                                                <a class="delete-row active"
                                                   href="${basePath}user/unbund?userId=${user.id}">解绑</a>
                                            </td>
                                        </shiro:hasPermission>
                                        <td>
                                            <a class="delete-row"
                                               href="${basePath}user/detail/app?id=${user.id }">详情</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            
                        </div>
                        <ul id="pagination" style="float: right"></ul>
                        <shiro:hasPermission name="user:adminfenpei">
                        	<button class="btn btn-primary  hidden-xs" type="button" onclick="distribution()" style="float: left;margin: 10px 10px 0 60px;">分配坐席</button>
                        </shiro:hasPermission>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<div class="modal fade" id="myModal2">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
                <h4 class="modal-title">
                                分配坐席
                </h4>
            </div>
            <div class="modal-body">
			            <div class="row">
                  				<div class="form-group col-md-2">
                                    <div>
                                                                            选择坐席:
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                         <select id="custom" style="width:200px;" class="form-control">
			                               <c:forEach var="me" items="${mlist}">
										     <c:if test="${me.uin ne '1000' && me.uin ne '1001'}">
								        		<option value ="${me.uin}">${me.nickname}</option>
								     		 </c:if>
			                               </c:forEach>
						              </select>
                                    </div>
                                </div>
			            </div>
             		<div class="modal-footer">
               	<button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
               	<button class="btn btn-primary" id="add-picture2" onclick="submitDis()">提交</button>
       					</div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal">
            <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">请选择Excel文件</h4>
                </div>
                  <div class="modal-body">
                  <form id="callImport"  method="post" enctype="multipart/form-data">
                  			<div class="row">
                  				<div class="form-group col-md-2">
                                    <div>
                                                                            选择文件:
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                         <input type="file" name="file" id="file"  class="file-loading form-control" />
                                    </div>
                                </div>
                            </div>
	                    <div class="row">
		                    <div class="form-group col-md-2">
	                                    <div>
	                                                                            分配坐席:
	                                    </div>
	                        </div>
                          <div class="form-group col-md-6">
                              <div>
                                  <select id="customimportSelect" name="uin" style="width:237px;" class="form-control">
		                            <c:forEach var="me" items="${mlist}">
		                            	<c:if test="${me.uin ne '1000' && me.uin ne '1001'}">
						        		<option value ="${me.uin}">${me.nickname}</option>
						               </c:if>
		                            </c:forEach>
                     			  </select>
                              </div>
                          </div>
                     </div>
	                </form>
                  </div>
                <div class="modal-footer">
		                	<button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
		                	<button class="btn btn-primary" id="add-picture" onclick="submitFileDis()">提交</button>
          	    </div></div>
           </div>
        </div>
    </div>
</div>
<div class="modal fade" tabindex="-1" role="dialog" id="resultModal">
  <div class="modal-dialog modal-lg" role="document" style="width:800px; height:600px;text-align: center;">
    <div class="modal-content">
      <div class="modal-header">	
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">分配结果</h4>
      </div>
      <div class="modal-body" style="padding:85px;font-size: 20px;">
        <p id="result">请等待,正在分配中...
         <div class="loading">
		</div></p>
      </div>
    </div>
  </div>
</div>
<div class="modal fade" tabindex="-1" role="dialog" id="checkResultModal">
  <div class="modal-dialog modal-lg" role="document" style="width:800px; height:600px;text-align: center;">
    <div class="modal-content">
      <div class="modal-header">	
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">分配结果</h4>
      </div>
      <div class="modal-body" style="padding:85px;font-size: 20px;">
        <p id="checkresult"></p>
      </div>
    </div>
  </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    var timer;
	function getResult(){
		$.ajax({
			url: '${basePath}call/getResult',
	        type: "POST",
	        async: false,
	        processData: false,  //必须false才会避开jQuery对 formdata 的默认处理
	        contentType: false,  //必须false才会自动加上正确的Content-Type
	        success: function (data) {
	          if (data.msg == "ok") {
	     	    var failuids="";
	     	  	var failJson=data.map.failuids;
	       	    for(var i=0;i<failJson.length;i++){
	       		   failuids+=failJson[i];
	       	    }
	       	   var text="总计:"+data.map.totalNum+"条,成功:"+data.map.succNum+"条,不分配:"+data.map.failNum+"条,更新用户:"+data.map.updateNum+"条";
	       	   if(failuids!=''){
	       		   text+="分配失败uid集合"+failuids; 
	       	   }
	       	   $("#result").text(text);
	       	    clearInterval(timer);
	       	    $(".loading").removeClass();
	       	    $("#add-picture").removeAttr("disabled");
	           }
	        }
		});
	}
	function submitFileDis(){
		var clientName=$("#customimportSelect").find("option:selected").text();
		var uin=$("#customimportSelect").val();
		var file = document.querySelector('[type=file]');
		var callData = new FormData($('#callImport')[0]);
		 callData.append('file',file);
		 callData.append('clientName',clientName);
		if (confirm("您确定给所选用户给坐席"+clientName+"么?")) {
			$("#add-picture").attr("disabled","disabled"); 
	       jQuery.ajax({
	           url: '${basePath}call/callImport',
	           type: "POST",
	           async: false,
	           data: callData,
	           processData: false,  //必须false才会避开jQuery对 formdata 的默认处理
	           contentType: false,  //必须false才会自动加上正确的Content-Type
	           success: function (data) {
	              if (data.msg == "ok") {
	            	  timer=setInterval("getResult()",10000);
	                  $("#myModal").modal("hide");
	                  $("#resultModal").modal("show");
	              }
	              if (data.msg == "fail") {
	                  alert("分配失败!..");
	                  $("#myModal").modal("hide");
	              }
	           },complete: function(XMLHttpRequest, textStatus) {
	        	   
	        	   
	           }
	       });
	   }
		
	}
	function submitDis(){
		var clientName=$("#custom").find("option:selected").text();
		var uin=$("#custom").val();
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
		if (confirm("您确定给所选用户给坐席"+clientName+"么?")) {
		 $("#add-picture2").attr("disabled","disabled"); 
	       jQuery.ajax({
	           url: '${basePath}call/saveUserCustom',
	           type: "POST",
	           data: "uin=" + uin+"&uids="+uids+"&clientName="+clientName,
	           dataType: "json",
	           success: function (data) {
	        	   var failJson=data.retInfo.failuids;
	        	   var failuids="";
	        	   for(var i=0;i<failJson.length;i++){
	        		   failuids+=failJson[i];
	        	   }
	               if (data.msg == "ok") {
	            	   $("#add-picture2").removeAttr("disabled");
	                   $("#myModal2").modal("hide");
	            	   $("#checkResultModal").modal("show");
	                   $("#checkresult").text("总计:"+data.retInfo.totalNum+"个,成功:"+data.retInfo.succNum+"个,不分配:"+data.retInfo.failNum+"个,更新:"+data.retInfo.updateNum+"条,分配失败uid集合"+failuids);
	               }
	               if (data.status == "fail") {
	            	   $("#add-picture2").removeAttr("disabled");
	                   $("#myModal2").modal("hide");
	            	   $("#checkResultModal").modal("show");
	                   $("#checkresult").text("分配失败");
	               }
	           }
	       });
	   }}
	function distribution(){
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
		$("#myModal2").modal("show");
	}
	function distribution2(){
		$("#myModal").modal("show");
	}
	function compare(start,end){
		start =start.getTime();
		end =end.getTime();
		var time =0
		if(start>end){
		time =start-end;
		}else{
		time =end-start;
		}
		return Math.floor(time/86400000)
		}
    function bb() {
    	var startTime=new Date(($("#startTime").val()).replace(/-/g,"/"));
    	var endTime=new Date(($("#endTime").val()).replace(/-/g,"/"));
    	var result=compare(startTime,endTime);
    	if(result>7){
    		alert("日期间隔大于7天,请重新选择");
    		return;
    	}
        $("#form").submit();
    }
    function aa(id) {
        if (confirm("您确定给该用户重置支付密码吗?!")) {
            jQuery.ajax({
                url: '${basePath}user/editpayPassword',
                type: "GET",
                data: "id=" + id,
                dataType: "json",
                success: function (data) {
                    if (data.status == "1") {
                        alert("重置支付密码成功!...");
                        window.location.href = "${basePath}user/list/app";
                    }
                    if (data.status == "0") {
                        alert("重置支付密码!..");
                    }
                }
            });
        }

    }

    function freeze(id) {
        if (confirm("您确定冻结该账户吗?!")) {
            jQuery.ajax({
                url: '${basePath}user/freeze',
                type: "GET",
                data: "type=0&id=" + id,
                dataType: "json",
                success: function (data) {
                    if (data.status == "1") {
                        alert("冻结成功");
                        window.location.reload();
                    }
                    if (data.status == "0") {
                        alert("冻结失败");
                    }
                }
            });
        }

    }

    function unfreeze(id) {
        if (confirm("您确定给该账户解除冻结状态吗?!")) {
            jQuery.ajax({
                url: '${basePath}user/freeze',
                type: "GET",
                data: "type=2&id=" + id,
                dataType: "json",
                success: function (data) {
                    if (data.status == "1") {
                        alert("解除冻结成功");
                        window.location.reload();
                    }
                    if (data.status == "0") {
                        alert("解除冻结失败");
                    }
                }
            });
        }

    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        $('.select2able').select2({width: "150"});
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('.active').click(function () {
            var userId = $(this).attr('userId');
            $.getJSON("${basePath}user/active/sina?id=" + userId, null, function (data) {
                alert(data.resultMsg);
            });
        });

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "distribute?keyword=${keyword}&level=${level}&status=${status}&codes=${codes}&type=${type}&startAmount=${startAmount}&endAmount=${endAmount}&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&investStartTime=<fmt:formatDate value="${investStartTime }" pattern="yyyy-MM-dd"/>&investndTime=<fmt:formatDate value="${investndTime }" pattern="yyyy-MM-dd"/>&customerName=${customerName}&page=" + page;
            }
        });
    })
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
