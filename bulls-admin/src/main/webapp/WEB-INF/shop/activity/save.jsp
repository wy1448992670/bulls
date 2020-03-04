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
	<title>商城活动添加</title>
	<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
	<style type="text/css">
		.upload-picture a {
			display: inline-block;
			overflow: hidden;
			border: 0;
			vertical-align: top;
			margin: 0 5px 10px 0;
			background: #fff;
		}

		.gallery-item:hover {
			background: #000;
		}

		#validate-form i.icon-zoom-in {
			width: 36px;
			height: 36px;
			font-size: 18px;
			line-height: 35px;
			margin-top: 0;
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
			<h1>
				添加商城活动
			</h1>
		</div>
		<!-- DataTables Example -->
		<div class="row">
			<div class="col-lg-12">
				<div class="widget-container fluid-height clearfix">
					<div class="heading">
						<i class="icon-table"></i>添加商城活动
					</div>
					<div class="widget-content padded clearfix">
						<form action="${basePath}shop/activity/save" method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
							<div class="form-group">
								<label class="control-label col-md-2">活动名称</label>
								<input type="hidden" name="id" value="${mallActivity.id}"/>
								<input type="hidden" name="target" id="target" value=""/>

								<div class="col-md-7">
									<input class="form-control" name="name" id="name" placeholder="请输入活动名称" type="text">
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">活动类型</label>

								<div class="col-md-7">
									<select class="select2able" name="type" id="type">
										<option value="">请选择</option>
										<option value="1" <c:if test="${mallActivity.type==1}">selected </c:if> >秒杀</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">活动开始时间</label>

								<div class="col-md-7">
									<input class="form-control datepicker" value="${mallActivity.startDate }" name="startDate" id="startDate"
										   type="text" placeholder="请选择活动开始时间"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-2">活动结束时间</label>

								<div class="col-md-7">
									<input class="form-control datepicker" value="${mallActivity.stopDate }" name="stopDate" id="stopDate"
										   type="text" placeholder="请选择活动开始时间"/>
								</div>
							</div>
							<%--<div class="form-group">--%>
								<%--<label class="control-label col-md-2">是否可用</label>--%>

								<%--<div class="col-md-7">--%>
									<%----%>
								<%--</div>--%>
							<%--</div>--%>
							<div class="form-group">
								<label class="control-label col-md-2">备注</label>

								<div class="col-md-7">
                                    <textarea class="form-control" name="remark" id="remark" placeholder="请输入备注" type="text">${mallActivity.remark}</textarea>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-md-2"></label>

								<div class="text-center col-md-7">
									<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
									<button class="btn btn-primary" type="button" onclick="save()" >保存</button>
									<button class="btn btn-info" type="button" onclick="save(1)">保存并添加活动商品</button>
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


<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            showSecond: true,
            format: 'yyyy-mm-dd',
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        $('.select2able').select2();
        validateForm();
    });
    
    function validateForm() {
        $("#validate-form").validate({
            rules: {
                name: {
                    required: true
                },
                startDate: {
                    required: true
                },
                stopDate: {
                    required: true
                },
                type: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "活动名称不能为空"
                },
                startDate: {
                    required: "活动开始时间不能为空"
                },
                stopDate: {
                    required: "活动结束时间不能为空"
                },
                type: {
                    required: "活动类型不能为空"
                }
            }
        });
	}
    
    function save(target) {
        // 后期扩展可用活动类型代替target
        $("#target").val(target);
        var validator = $("#validate-form").validate();
        // console.log("validator.form() " + validator.form());
		if (validator.form()) {
			var startDate = $("#startDate").val();
			var stopDate = $("#stopDate").val();
			if (startDate != "" && stopDate != "") {
				// 活动开始不能小于等于今天
				var d = new Date(Date.parse(startDate .replace(/-/g,"/")));
				var curDate = new Date();
				if(d <= curDate){
					alert("开始时间不能小于等于今天！");
					return false;
				}
				if(stopDate <= startDate){
					alert("活动结束时间不能小于等于活动开始时间！");
					return false;
				}
			} else {
				return false;
			}
			if (confirm("确认保存？")) {
				$("#validate-form").submit();
			}
		}
	}
</script>
</body>
</html>
