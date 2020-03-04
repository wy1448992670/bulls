<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>评价详细信息</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.upload-picture a {
	display: inline-block;
	overflow: hidden;
	border: 0;
	vertical-align: top;
	margin: 0 5px 10px 0;
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
				<h1>评价管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix" style="height: 1000px">
						<div class="heading">
							<i class="icon-table"></i>

							<Table class="table table-bordered table-hover">
								<c:forEach items="${assessDetails}" var="assess">
									<c:if test="${assess.type==0 }">

										<tr>
											<table class="table table-bordered table-hover">
												<tr>
													<td>买家：${assess.true_name}</td>
													<td>商品：${assess.goods_name }</td>
													<td>
														<c:if test="${empty assess.parent_id }">
															<c:if test="${assess.is_top==0 }">
																<a href="javascript:void(0);" name="top" value="${assess.is_top}" id="${ assess.id}">未置顶</a>
															</c:if>
															<c:if test="${assess.is_top==1 }">
																<a href="javascript:void(0);" name="top" value="${assess.is_top }" id="${assess.id }">已置顶</a>
															</c:if>
														</c:if>
													</td>
													<td>
														<c:if test="${assess.state!=1 and assess.type!=1 }">
															<a href="javascript:void(0);" id="del_${assess.id }" name="delete" value="${assess.id }" state=${assess.state }>删除</a>
														</c:if>
														<c:if test="${assess.state==1}">
																已删除												
														</c:if>
													</td>
													<td>
														<fmt:formatDate value="${assess.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
													</td>
												</tr>
												<tr>
													<td colspan="5">评价：${assess.content }</td>
												</tr>
												<!-- 如果评价有图片 -->
												<c:if test="${!empty assess.imgPath}">
													<tr>
														<td colspan="5">
															<c:forEach items="${assess.imgPath }" var="img">
																<img src="${img.upload.cdnPath }" />
															</c:forEach>
														</td>
													</tr>
												</c:if>
											</table>
									</c:if>

									<c:if test="${assess.type==1 }">
										<!-- 卖家回复区域 -->
										<tr>

											<table class="table table-bordered table-hover">
												<tr style="color: red">
													<td>管理员：</td>
													<td>
														<fmt:formatDate value="${assess.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
													</td>
												</tr>
												<tr>
													<td colspan="2" style="color: red">回复：${assess.content }</td>
												</tr>
											</table>

										</tr>
									</c:if>
								</c:forEach>


							</Table>

						</div>
						<div class="widget-content padded clearfix"></div>
					</div>
				</div>


				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							评价回复
						</div>
						<form action="addReply" id="addReply" name="addReply" method="post">
							<input type="hidden" id="goodsId" name="goodsId" value="${goodsId}" />
							<input type="hidden" id="orderId" name="orderId" value="${orderId }" />
							<input type="hidden" id="userId" name="userId" value="${userId }" />
							<input type="hidden" id="type" name="type" value="1" />
							<input type="hidden" id="parentId" name="parentId" value="${parentId}" />
							<div class="widget-content padded clearfix">
								<textarea rows="15" class="form-control" placeholder="请输入评价回复" name="content" id="content"></textarea>
							</div>

							<div class="text-center col-md-7">
								<a class="btn btn-default-outline" id="goBack">返回评价列表</a>
								<a class="btn btn-default-outline" id="goBackGoodDetail">返回商品明细</a>
								<c:if test="${buyer.isForbidComment==0 or empty buyer.isForbidComment}">
									<button class="btn btn-primary" type="button" onclick="checkPost();" id="replyBtn">回复</button>
									<button class="btn btn-primary" type="button" onclick="banned();" id="banned" va="${buyer.isForbidComment }">禁言此用户</button>
								</c:if>
								<c:if test="${buyer.isForbidComment==1 }">
									<font color=" red">该买家已被禁言，不能回复此用户</font>
								</c:if>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>

	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$('.select2able').select2();

			$(".fancybox").fancybox({
				maxWidth : 700,
				height : 'auto',
				fitToView : false,
				autoSize : true,
				padding : 15,
				nextEffect : 'fade',
				prevEffect : 'fade',
				helpers : {
					title : {
						type : "outside"
					}
				}
			});

		});

		function checkPost() {
			var reply = $('#content').val();
			if (comm.isEmpty(reply)) {
				alert('回复内容不能为空');
				return;
			}
			$('#addReply').submit();
		}

		//置顶操作
		$("a[name=top]").click(function() {
			var con;
			con = confirm("确定置顶吗?");
			if (con) {
				var value;
				var assessId = $(this).attr("id");
				var text = "已置顶";
				if ($(this).attr("value") == 0) {
					value = 1;
					text = "已置顶";
				} else {
					value = 0;
					text = "未置顶";
				}
				$(this).attr("value", value);
				$.ajax({
					url : "setTopAjax",
					data : {
						assessId : assessId,
						isTop : value
					},
					type : "post",
					success : function(result) {
						if (result.code == 1) {
							$('#' + assessId).html(text);
						} else {
							alert(result.msg);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// 状态码
						console.log(XMLHttpRequest.status);
						// 状态
						console.log(XMLHttpRequest.readyState);
						// 错误信息   
						console.log(textStatus);
					}
				});
			}

		});
		//======================================================
		//删除操作
		$("a[name=delete]").click(function() {
			var con;
			con = confirm("确定删除吗?");
			if (con) {
				var assessId = $(this).attr("value");
				var text = "删除";
				var state = 0;
				if ($(this).attr("state") == 1) {
					text = "删除";
					$(this).attr("state", 0);
				} else {
					text = "已删除";
					$(this).attr("state", 1);
				}
				$.ajax({
					url : "delAssessAjax",
					data : {
						assessId : assessId,
						state : $(this).attr("state")
					},
					dataType : "json",
					type : "post",
					success : function(result) {
						if (result.code == 1) {
							$('#del_' + assessId).html(text);
							$('#del_' + assessId).removeAttr('href');
							$('#del_' + assessId).unbind();
						} else {
							alert(result.msg);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// 状态码
						console.log(XMLHttpRequest.status);
						// 状态
						console.log(XMLHttpRequest.readyState);
						// 错误信息   
						console.log(textStatus);
					}
				});
			}
		});

		$('#banned').click(function() {
			//var IsForbidComment=$(this).attr("va");		
			var con;
			con = confirm("确定禁言此买家吗?");
			if (con) {
				$.ajax({
					url : "${basePath}/user/bannedUserAjax",
					data : {
						userId : $("#userId").val(),
						isForbidComment : 1
					},
					dataType : "json",
					type : "post",
					success : function(result) {
						if (result.code == 1) {
							$('#banned').remove();
							$('#replyBtn').remove();
							alert("禁言成功");
						} else {
							alert(result.msg);
						}
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						// 状态码
						console.log(XMLHttpRequest.status);
						// 状态
						console.log(XMLHttpRequest.readyState);
						// 错误信息   
						console.log(textStatus);
					}
				})
			}
		})

		$('#goBack').click(function() {
			window.location.href = "${basePath}shop/assessList";
		})
		$('#goBackGoodDetail').click(function() {
			window.location.href = "${basePath}shop/goodsDetail?id=${goodsId}";
		})
	</script>
</body>
</html>