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
    <link href="${basePath}css/datetimepicker/datetimepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <script src="${basePath}js/97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <title>推送模板</title>
</head>
<body>
<div class="modal-shiftfix">
	<!-- Navigation -->
	<jsp:include page="../common/header.jsp"></jsp:include>
	<!-- End Navigation -->
	<div class="container-fluid main-content">
		<div class="page-title"><h1>用户管理</h1></div>
		<!-- DataTables Example -->
		<div class="row">
			<div class="col-lg-12">
				<div class="widget-container fluid-height clearfix">
					<div class="heading"><i class="icon-table"></i>推送模板</div>
					<div class="widget-content padded clearfix">
						<form onsubmit="return saveForm()" method="post" class="form-horizontal" id="validate-form" enctype="multipart/form-data" action="${basePath}user/editMessagePush">
						<input type="hidden" name="status" id="status" value="${messagePush.status}"/>
						<input type="hidden" name="id" id="id" value="${messagePush.id}"/>
						<div class="form-group">
							<label class="control-label col-md-2"><font color="red">*</font>推送标题</label>
							<div class="col-md-7">
								<input class="form-control" name="title" id="title" placeholder="推送标题" value="${messagePush.title}">
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-2"><font color="red">*</font>推送内容</label>
							<div class="col-md-7">
								<textarea rows="10" class="form-control" name="content" id="content"  placeholder="请输入推送内容">${messagePush.content}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-2">推送类型</label>
							<div class="col-md-7">
								<label class="radio-inline">
									<input type="radio" name="uploadType" value="1"  checked onchange="changeUploadType(1)"><span>用户ID</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="uploadType" value="2" onchange="changeUploadType(2)"><span>device_token</span>
								</label>
							</div>
						</div>
						<div class="form-group" id="userId_file">
							<label class="control-label col-md-2">上传名单(用户ID)</label>
							<div class="col-md-7">
								<input type="file" name="file" id="file">
								<font color="red">不上传默认发送所有人</font>
							</div>
						</div>
						
						<div class="form-group" id="deviceToken_file">
							<label class="control-label col-md-2">上传名单(device_token)</label>
							<div class="col-md-7">
								<input type="file" name="deviceFile" id="device_file">
								<font color="red">不上传默认发送所有人</font>
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-2">跳转类型</label>
							<div class="col-md-7">
								<label class="radio-inline">
									<input type="radio" name="type" value="1"  checked onchange="changeUrlType(1)"><span>H5</span>
								</label>
								<label class="radio-inline">
									<input type="radio" name="type" value="2" onchange="changeUrlType(2)"><span>原生</span>
								</label>
							</div>
							 
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-2">跳转URL</label>
							<div class="col-md-7">
								<input class="form-control" name="url" id="url" placeholder="请输入跳转链接" value="${messagePush.url}">
							</div>
						</div>
						
						<div class="form-group" id="json">
							<label class="control-label col-md-2">参数(JSON)</label>
							<div class="col-md-7">
								<input class="form-control" name="params" id="params" placeholder="请输入原生参数" value="">
							</div>
						</div>
						
						<div class="form-group">
							<label class="control-label col-md-2">定时发送</label>
							<div class="col-md-7"> 
								<input class="form-control" style="width:300px;" id="sendTime" name="sendTime" 
								 placeholder="未设置表示立即发送" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
							</div>
						</div>
						<div class="col-md-3" id="temp">
                               <a class="btn btn-success pull-right hidden-xs" href="${basePath}template/messagepush.txt" download="下载用户ID推送管理模版">下载用户ID推送管理模版(.txt格式)</a>
                        </div> 
                        <div class="col-md-3" id="temp_token">
                               <a class="btn btn-success pull-right hidden-xs" href="${basePath}template/messagepush_token.txt" download="下载device_token推送管理模版">下载device_token推送管理模版(.txt格式)</a>
                        </div> 
						<div class="form-group">
							<label class="control-label col-md-2"></label>
							<div class="text-center col-md-7">
								<button class="btn btn-success" type="button" style="background-color:#cccccc;" onclick="javascript:window.history.go(-1);">取消</button>
								<input class="btn btn-success" type="submit"  id="submit" value="提交" />
							</div>
						</div> 
						
						</form>  
					</div>
				</div>
			</div>
		</div>
		<!-- end DataTables Example -->
	</div>
</div>

<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath }js/select2.js" type="text/javascript"></script>

<script type="text/javascript">

	function changeUrlType(flag){
		if(flag == 1){
			$("#json").hide();
		}else{
			$("#json").show();
		}
	}
	
	function changeUploadType(flag){
		if(flag == 1){
			$("#deviceToken_file").hide();
			$("#userId_file").show();
		}else{
			$("#deviceToken_file").show();
			$("#userId_file").hide();
		}
	}
    $(function () {
    	$("#json").hide();
    	$("#deviceToken_file").hide();
	});
	
	function saveForm(){
		var title = $("#title").val();
		if(title == null || title == ""){
			alert("标题不能为空");
			return false;
		}
		var content = $("#content").val();
		if(content == null || content == ""){
			alert("内容不能为空");
			return false;
		}
		
		var file = $("#file").val(); 
		var device_file = $("#device_file").val();
		if((file == null || file == "") && (device_file == null || device_file == "")){
			if(confirm("确认推送全部用户？！")){
				$("#submit").attr("disabled", true);
				return true;
			} else {
				return false;
			}
		}
		
		
		$("#submit").attr("disabled", true);
		return true;
		 
	}
</script>
</body>
</html>