<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css"
	media="all" rel="stylesheet" type="text/css" />
<title>添加icon</title>
</head>
<body>
	<div class="modal-shiftfix">
		<!-- Navigation -->
		<jsp:include page="../common/header.jsp"></jsp:include>
		<!-- End Navigation -->
		<div class="container-fluid main-content">
			<div class="page-title">
				<h1>icon管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>添加icon
						</div>
						<div class="widget-content padded clearfix">
							<form action="add" method="post" class="form-horizontal"
								id="validate-form">
								<input type="hidden" id="linkId" name="linkId"
									value="<c:if test="${icon != null}">${icon.linkId }</c:if>" />

								<div class="form-group">
									<label class="control-label col-md-2">icon标题</label>

									<div class="col-md-7">
										<input class="form-control" name="title" id="title"
											placeholder="请输入标题" type="text"
											<c:if test="${icon != null}">value="${icon.title }"</c:if>>
									</div>
								</div>
								<input type="hidden" id="id" name="id"
									value="<c:if test="${icon != null}">${icon.iconId }</c:if>" />
								<input type="hidden" name="page" id="page" value="${page}">

								<div class="form-group">
									<label class="control-label col-md-2">icon图片</label>

									<div class="col-md-7">
										<span class="btn btn-success fileinput-button"> <i
											class="glyphicon glyphicon-plus"></i> <span>上传</span> <input
											type="file" name="file" id="fileupload">
										</span> <img
											<c:if test="${icon != null}">src="${aPath }upload/${icon.path }"</c:if>
											id="target" width="200px;" /> <input type="hidden"
											name="picture"
											value="<c:if test="${icon != null}">${icon.uploadId }</c:if>"
											id="picture" />
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">key参考值</label>

									<div class="col-md-7">
										<table class="table table-bordered table-hover">
											<tbody>
												<tr>
													<td>index<br />首页</td>
													<td>bulls<br />养牛</td>
													<td>shop<br />商城</td>
													<td>user<br />牧场主</td>
													<td>passWordLogin<br />密码登陆</td>
													<td>mobileSMSLogin<br />手机短信登陆</td>
												</tr>
												<tr>
													<td>forgotPassword<br />忘记密码</td>
													<td>registerApp<br />注册</td>
													<td>cattleDetail<br />牛只详情</td>
													<td>goodsDetail<br />商品详情</td>
													<td>shoppingCar<br />购物车</td>
													<td>myAccountCenter<br />账户中心</td>
												</tr>
												<tr>
													<td>myRealName<br />实名认证</td>
													<td>myAddress<br />收货地址</td>
													<td>myBouns<br />我的红包</td>
													<td>myTrans<br />交易记录</td>
													<td>about<br />关于我们</td>
													<td>orderCattleDetail<br />订单牛只详情</td>
												</tr>
												<tr>
													<td>orderGoodsDetail<br />订单商品详情</td>
													<td>share<br />H5分享 - 使用原生分享控件</td>
													<td>shareToWeChatSession<br />shareToWeChatTimeLine<br />shareToCopyText<br />H5分享 - H5不传参，但响应原生分享控件的点击事件</td>
													<td>shareTo<br />H5分享 - H5直接传参，不使用原生分享控件</td>
													<td>openInExplorer<br />系统浏览器</td>
													<td>nativeBack1<br />关闭 webView</td>
												</tr>
												<tr>
													<td>nativeBack2<br />webView 返回上个链接</td>
													<td><br /></td>
													<td><br /></td>
													<td><br /></td>
													<td><br /></td>
													<td><br /></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">key</label>

									<div class="col-md-7">
										<input class="form-control" name="key" id="key"
											placeholder="请输入key值" type="text"
											<c:if test="${icon != null}">value="${icon.key }"</c:if>>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">链接地址</label>

									<div class="col-md-7">
										<input class="form-control" name="link" id="link"
											placeholder="请输入链接地址,无则不输入" type="text"
											<c:if test="${icon != null}">value="${icon.link }"</c:if>>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">序号</label>

									<div class="col-md-7">
										<input class="form-control" name="seq" id="seq"
											placeholder="请输入序号" type="text"
											<c:if test="${icon != null}">value="${icon.seq }"</c:if>>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline"
											onclick="javascript:window.history.go(-1);">取消</a>
										<button class="btn btn-primary" type="submit">创建/保存</button>
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
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-fileupload.js"
		type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js"
		type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$('.select2able').select2();

			$("#fileupload").fileupload(
					{
						url : "${basePath}icon/upload",
						autoUpload : true, //不自动上传
						add : function(e, data) {
							for (var i = 0; i < data.files.length; i++) {
								var file = data.files[i];
								if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i)
										.test(file.type)) {
									alert("错误的图片类型");
									return false;
								}
								if (file.size > 10000000) {//1M
									alert("图片大于1M");
									return false;
								}
								var reader = new FileReader();
								reader.onload = function(e) {
									$('#target').attr('src', e.target.result)
											.show();
								};
								reader.readAsDataURL(file);
								data.submit();
							}
						},
						done : function(e, result) {
							var data = result.result;
							if (data.status == "error") {
								alert('上传失败');
							} else {
								$('#picture').val(data.id);
							}
						}
					});

			$('#type').change(function() {
				var val = $(this).val();
				if (val == 0) {
					$('#imgs').show();
					$('#imgs2').hide();
				} else {
					$('#imgs2').show();
					$('#imgs').hide();
				}
			});

			$("#validate-form").validate({
				ignore : '',
				rules : {
					picture : {
						required : true
					},
					title : {
						required : true
					},
					key : {
						required : true
					},
					link : {
						url : true
					},
					seq : {
						required : true,
						digits : true
					}
				},
				messages : {
					picture : {
						required : "请选择icon图片"
					},
					title : {
						required : "请输入icon标题"
					},
					key : {
						required : "请输入icon key值"
					},
					link : {
						url : "请输入合法的URL"
					},
					seq : {
						required : "请输入序号",
						digits : "请输入合法的整数"
					}
				}
			});
		});
	</script>
</body>
</html>