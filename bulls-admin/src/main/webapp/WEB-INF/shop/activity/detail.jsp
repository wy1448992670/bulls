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
	<title>商城活动详情</title>
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
				商城活动详情
			</h1>
		</div>
		<!-- DataTables Example -->
		<div class="row">
			<div class="col-lg-4">
				<div class="widget-container fluid-height clearfix">
					<div class="heading">
						<i class="icon-table"></i>商城活动详情
					</div>
					<div class="widget-content padded clearfix">
						<form action="${basePath}shop/activity/save" method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
							<div class="form-group">
								<label class="control-label col-md-4">活动名称</label>

								<div class="col-md-7">
									<p class="form-control-static">
										${mallActivity.name }
									</p>
								</div>
							</div>

							<div class="form-group">
								<label class="control-label col-md-4">活动类型</label>

								<div class="col-md-7">
									<p class="form-control-static">
										${mallActivity.typeMsg }
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-4">活动开始时间</label>

								<div class="col-md-7">
									<p class="form-control-static">
										<fmt:formatDate value="${mallActivity.startDate }" pattern="yyyy-MM-dd"/>
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-4">活动结束时间</label>

								<div class="col-md-7">
									<p class="form-control-static">
										<fmt:formatDate value="${mallActivity.stopDate }" pattern="yyyy-MM-dd"/>
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-4">是否可用</label>

								<div class="col-md-7">
									<p class="form-control-static">
										<c:if test="${mallActivity.enable == 0}">
											<span class="label label-warning">不可用</span>
										</c:if>
										<c:if test="${mallActivity.enable == 1}">
											<span class="label label-success">可用</span>
										</c:if>
									</p>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-4">备注</label>

								<div class="col-md-7">
									<p class="form-control-static">
										${mallActivity.remark }
									</p>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-md-4"></label>

								<div class="text-center col-md-7">
									<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">返回</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			
			<div class="col-lg-8">
				<c:if test="${mallActivity.type == 1}">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>活动项目列表
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
									<tr>
										<th>ID</th>
										<th>开始时间</th>
										<th>秒杀时长（分钟）</th>
										<th>星期</th>
										<th>商品名称</th>
										<th>商品图片</th>
										<th>价格</th>
										<th>会员价格</th>
										<th>库存</th>
										<%--<th>已售出</th>--%>
										<th>每人限购</th>
										<th>是否可卖</th>
										<th>活动描述</th>
										<th>创建时间</th>
										<th>活动期间锁定库存</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach items="${listSecondKill}" var="i" varStatus="st">
										<tr>
											<td>${i.id}</td>
											<td><fmt:formatDate value="${i.startTime}" pattern="HH:mm:ss"/></td>
											<td>${i.killTime}</td>
											<td>${i.weekDay}</td>
											<td>${i.goodsName}</td>
											<td>
												<c:if test="${i.activityImage == null}">
													<img alt="" src="${aPath}images/no-image.gif" height="80px" width="80px">
												</c:if>
												<c:if test="${i.activityImage != null}">
													<img src="${i.activityImage}" height="80px" width="80px">
												</c:if>
											</td>
											<td>${i.price}</td>
											<td>${i.memberPrice}</td>
											<td>${i.stockCount}</td>
											<%--<td>${i.saledCount}</td>--%>
											<td>${i.limitCount}</td>
											<td>
												<c:if test="${i.isOnShelves == 0}">
													<span class="label label-warning">${i.isOnShelvesMsg}</span>
												</c:if>
												<c:if test="${i.isOnShelves == 1}">
													<span class="label label-success">${i.isOnShelvesMsg}</span>
												</c:if>
											</td>
											<td>${i.title}</td>
											<td><fmt:formatDate value="${i.createTime}" pattern="HH:mm:ss"/></td>
											<td>${i.lockStock}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</c:if>
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
        $("#target").val(target);
        var validator = $("#validate-form").validate();
        // console.log("validator.form() " + validator.form());
		if (validator.form()) {
                var startDate = $("#startDate").val();
                var stopDate = $("#stopDate").val();
                if (startDate != "" && stopDate != "") {
                    // 活动开始不能小于今天
                    var d = new Date(Date.parse(startDate .replace(/-/g,"/")));
                    var curDate = new Date();
                    if(d <= curDate){
                        alert("开始时间不能小于今天！");
                        return false;
                    }
                    if(stopDate <= startDate){
                        alert("活动结束时间不能小于活动开始时间！");
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
