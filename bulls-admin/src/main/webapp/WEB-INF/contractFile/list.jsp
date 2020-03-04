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
    <title>合同管理</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>   
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
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
            <h1>
                企业管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>合同管理
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="list">
                        	<input type="hidden" name="page" id="page" value="${page }"/>
                        	<div class="row">
								<div class="form-group col-md-6"></div>
								<div class="form-group col-md-6">
									<input class="form-control keyword" name="keyword" type="text"
                                           placeholder="用户姓名/姓名/手机号" value="${keyword }"/>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-6"></div>
								<div class="form-group col-md-6">
									<div>
										<select class="select2able" name="showStatus">
	                                		<option value="" <c:if test="${showStatus == null }">selected</c:if>>全部</option>
		                                    <option value="waiting" <c:if test="${showStatus == 'waiting' }">selected</c:if>>待签署</option>
		                                    <option value="signed" <c:if test="${showStatus == 'signed' }">selected</c:if>>已签署,待审核</option>
		                                    <option value="channelSigned" <c:if test="${showStatus == 'channelSigned' }">selected</c:if>>签署完成</option>
		                                    <option value="channelAuditPass" <c:if test="${showStatus == 'channelAuditPass' }">selected</c:if>>审核通过</option>
		                                    <option value="channelAuditNotPass" <c:if test="${showStatus == 'channelAuditNotPass' }">selected</c:if>>审核失败</option>
	                                	</select>
									</div>
								</div>
							</div>
                            <!-- <div class="form-group col-md-12" style="float:left">
                            	<form id="importContract" class="form-inline hidden-xs col-lg-3 pull-left"
                             				enctype="multipart/form-data">
                             				<input type="file" name="file" id="file"><input onclick="importContract()" type="button" class="btn btn-success" value="开始导入"/>
                             	</form>
                        	</div> -->
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th width="5%">
                                    借款人ID
                                </th>
                                <th width="8%">
                                    用户名
                                </th>
                                <th width="5%">
                                    手机号
                                </th>
                                <th width="5%">
                                    姓名
                                </th>
                                <th width="7%">
                                    身份证号码
                                </th>
                                <th width="10%">
                                    文件名称
                                </th>
                                <th width="5%">
                                    文件发送时间
                                </th>
                                <th width="5%">
                                    渠道方签署时间
                                </th>
                                <th width="5%">
                                状态
                                </th>
                                <th width="5%">
                                操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="file" items="${list}">
                                <tr>
                                    <td>
                                            ${file.id}
                                    </td>
                                    <td>
                                        ${file.username}
                                    </td>
                                    <td>
                                    	<shiro:lacksPermission name="user:adminPhone">
                                            ${file.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${file.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                            ${file.trueName}
                                    </td>
                                    <td>
                                    	<shiro:lacksPermission name="user:adminPhone">
                                            ${file.identityCard.replaceAll("(\\d{6})\\d{5}(\\w{4})","$1*****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${file.identityCard }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                            ${file.contractId}
                                    </td>
                                    <td>
                                            <fmt:formatDate type="date" value="${file.createDate}" />
                                    </td>
                                    <td>
                                    		<c:if test="${file.showStatus == 'channelSigned'}">
                                            	<fmt:formatDate type="date" value="${file.updateDate}" />
                                    		</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${file.showStatus ==  'signed' }">待审核</c:if>
                                        
                                        <c:if test="${file.showStatus !=  'signed' }">${file.showStatusStr}</c:if>
                                    </td>
                                    <td>
                                        <a class="edit-row" href="${file.contractViewUrl}">查看</a>
                                        <c:if test="${file.showStatus == 'signed'}">
                                        	<a class="edit-row" href="javascript:void(0);" onclick="channelSign('${file.contractId}', '${file.id}');">&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;签署</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
    
    
    
    
    <!-- 按钮触发模态框 -->
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel"></h4>
	            </div>
	            <div class="modal-body"></div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
</div>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
    	$('.select2able').select2({width: "150"});
        $(".select2able").change(function () {
            $("#form").submit();
        });
    	
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            $("#page").val(1);
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
                return "list?keyword=${keyword}&page=" + page;
            }
        });
    });
    
    function channelSign(contractId, landlordId){
    	
    	$.ajax({
    		url: "${basePath}contractFile/contract/sign?contractId=" + contractId + "&landlordId=" + landlordId + "&page=${page}",
            dataType: "json",
            type: 'post',
            success: function (oData) {
            	if (oData.wrong_vcode == "nologin") {
                    noLogin("../landlord/login.html");
                } else if (oData.wrong_vcode == "success") {
                    window.location.href = oData.signUrl;
                } else {
                    alert(oData.msg);
                }
            }
        });
    }
    
    function importContract(){
    	//用formDate对象上传
        var fd = new FormData($('#importContract')[0]);
    	$.ajax({
            type: "post",
            url: "${basePath}contractFile/contract/batch/send",
            async: false,
            data: fd,
            processData: false,  //必须false才会避开jQuery对 formdata 的默认处理
            contentType: false,  //必须false才会自动加上正确的Content-Type
            beforeSend: function () {
                click = false;
            },
            success: function (oData) {
                if (oData.status == "ok") {
                    //alert(oData.msg + ", " + oData.randomNumber);
                    initBar(oData.randomNumber);
                } else {
                    alert(oData.msg);
                }
            },
            complete: function () {
                click = true;
            }
        });
    }
    
    function initBar(randomNumber) {
    	
    	$(".btn-success").attr("disabled", true);
    	$(".btn-success").val("已导入0%");
    	$("#file").attr("contenteditable", true);
		var bar = setInterval(function(){
			$.ajax({
				type:'post',
				url:'${basePath}contractFile/progressBar',
				data:{"progressKey" : "contractBatchSend" + randomNumber},
				dataType : "json",
				success:function(data) {
					console.log(data.errorList);
					if(!data.perMap){
						clearInterval(bar);
						return;
					}
					
					var per = parseInt(data.perMap.per);
					console.log(per);
					$(".btn-success").val("已导入" + per + "%");
					if(per == 100) {
						
						$(".btn-success").removeAttr("disabled");
						$(".btn-success").val("开始导入");
						$("#file").attr("contenteditable", false);
						
						clearInterval(bar);
						
						var errorList = data.errorList;
						if(!errorList){
							$('#myModal').modal('show');
							$(".modal-body").html("合同发送成功");
							$("#myModalLabel").html("提示");
						}else{
							var errorUser = "";
							for(var i=0; i<errorList.length; i++){
								errorUser += errorList[i] + "\n";
							}
							$('#myModal').modal('show');
							$(".modal-body").html(errorUser);
							$("#myModalLabel").html("合同发送失败信息");
						}
						
						return;
					} 
				}
			});
		},8000);
	} 
    
    $('#myModal').on('hide.bs.modal', function () {
    	  window.location.href="${basePath}contractFile/list?keyword=${keyword}&page=1";
    });
</script>
</body>
</html>