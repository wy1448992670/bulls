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
<title>创建商品商品</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
<SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.config.js"></SCRIPT>
<SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.all.js"></SCRIPT>
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

.flag {
	color: red;
}

.selectHTML {
	
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
				<h1>商品管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							发布商品
						</div>
						<div class="widget-content padded clearfix">
							<form action="addGoods" method="post" class="form-horizontal" id="validate-form" onsubmit="return CheckPost();">
								<input type="hidden" name="token" value="${sessionScope.token }" />
								<div class="form-group">
									<label class="control-label col-md-2">
										商品封面图
										<br>
										<font color="red">(图片尺寸：350px*350px)</font>
									</label>
									<input type="hidden" id="little_img" />
									<div class="col-md-7 upload-picture">
										<a data-toggle="modal" href="#myModal" id="enterprise-picture">
											<i class="iconfont" style="font-size: 150px; cursor: pointer;">&#xe602;</i>
										</a>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">
										商品详细图
										<br>
										<font color="red">(图片尺寸：750px*750px)</font>
									</label>
									<input type="hidden" id="big_img" />
									<div class="col-md-7 upload-picture">
										<a data-toggle="modal" href="#myModal2" id="borrow-picture">
											<i class="iconfont" style="font-size: 150px; cursor: pointer;">&#xe602;</i>
										</a>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品名称</label>

									<div class="col-md-7">
										<input class="form-control" name="goodsName" id="goodsName" placeholder="请输入商品名称" type="text" value="">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品编号</label>

									<div class="col-md-7">
										<input class="form-control" name="goodsNo" id="goodsNo" type="text" value="S-201985005749">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">所属品牌</label>

									<div class="col-md-7">
										<select class="select2able" id="brandId" name="brandId">
											<option value="">请选择</option>
											<c:if test="${!empty goodsBrands}">
												<c:forEach items="${goodsBrands}" var="goodsBrand">
													<option value="${goodsBrand.id}">${goodsBrand.brandName}</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">进货价格</label>

									<div class="col-md-7">
										<input class="form-control" id="buyingPrice" name="buyingPrice" type="text" value="200">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">销售价</label>

									<div class="col-md-7">
										<input class="form-control" id="salingPrice" name="salingPrice" type="number" value="300">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">会员价</label>

									<div class="col-md-7">
										<input class="form-control" name="memberSalingPrice" id="memberSalingPrice" type="number" value="280">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">库存</label>

									<div class="col-md-7">
										<input class="form-control" name="stock" id="stock" type="number" value="800">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">库存单位</label>

									<div class="col-md-7">
										<select class="select2able" id="stockUnit" name="stockUnit">
											<option value="-1">---请选择---</option>
											<c:forEach items="${stockUnit }" var="stockUnit">
												<option value="${stockUnit.tValue }">${stockUnit.tValue }</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">商品类别选择</label>
									<div class="col-md-7">
										<select class="select2able" onchange="listGoodsProperty();" id="categoryId" name="categoryId">
											<option value="">---请选择---</option>
											<c:if test="${!empty goodsCategoryList}">
												<c:forEach items="${goodsCategoryList}" var="goodsCategory">
													<option value="${goodsCategory.id}">${goodsCategory.categoryName}</option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>


								<!-- 类目显示 -->
								<div class="selectHTML"></div>
								
								<div class="form-group">
									<label class="control-label col-md-2">排序</label>
									<div class="col-md-7">
										<input class="form-control" name="sort" id="sort" type="number" value="99" onblur="sortNumber();">
										<br>
										<font color="red">最大值99，数值越小，排名越靠前</font>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">SKU码</label>

									<div class="col-md-7">
										<input class="form-control" name="skuCode" id="skuCode" placeholder="请输入SKU码" type="text" value="">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">商品重量(KG)</label>

									<div class="col-md-7">
										<input class="form-control" name="weight" id="weight" placeholder="请输入商品重量" type="number" value="">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">商品介绍</label>

									<div class="col-md-7">
										<!-- 加载编辑器的容器 -->
										<script id="container" name="container" type="text/plain" height="500"></script>
										<textarea rows="" cols="" hidden="true" name="introductionHtml" id="htmlContent"></textarea>
										<textarea rows="" cols="" hidden="true" name="introduction" id="textContent"></textarea>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">已出售</label>
									<div class="col-md-7">
										<input class="form-control" name="sellStock" id="sellStock" type="number" value="">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">是否上架</label>
									<div class="col-md-7">
										<select class="select2able" name="upDown" id="upDown">
											<option value="">---请选择---</option>
											<option value="1">已上架</option>
											<option value="0">未上架</option>
										</select>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
										<button class="btn btn-primary" type="submit">马上创建</button>
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

	<div class="modal fade" id="myModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
					<h4 class="modal-title">商品图片上传</h4>
				</div>
				<div class="modal-body">
					<form action="" class="form-horizontal" id="picture-form">
						<div class="form-group">
							<label class="control-label col-md-2">图片名称</label>

							<div class="col-md-7">
								<input class="form-control" name="picName" id="picName" placeholder="请输入图片名称" type="text" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-2">商品封面图</label>

							<div class="col-md-3">
								<span class="btn btn-success fileinput-button">
									<i class="glyphicon glyphicon-plus"></i>
									<span>上传</span>
									<input type="file" name="file" id="fileupload">
								</span>
								<img src="" id="target" width="200px;" />

								<div class="alert alert-danger" style="display: none; width: 300px;">
									<button class="close" data-dismiss="alert" type="button">&times;</button>
									<span class="alert-content"></span>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
					<button class="btn btn-primary" id="add-picture" disabled>添加</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal2">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
					<h4 class="modal-title">商品详细图片</h4>
				</div>
				<div class="modal-body">
					<form action="" class="form-horizontal" id="picture-form2">
						<div class="form-group">
							<label class="control-label col-md-2">图片名称</label>

							<div class="col-md-7">
								<input class="form-control" name="picName" id="picName2" placeholder="请输入图片名称" type="text" />
							</div>
						</div>

						<div class="form-group">
							<label class="control-label col-md-2">图片</label>

							<div class="col-md-3">
								<span class="btn btn-success fileinput-button">
									<i class="glyphicon glyphicon-plus"></i>
									<span>上传</span>
									<input type="file" name="file" id="fileupload2">
								</span>
								<img src="" id="target2" width="200px;" />

								<div class="alert alert-danger" style="display: none; width: 300px;">
									<button class="close" data-dismiss="alert" type="button">&times;</button>
									<span class="alert-content"></span>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default-outline" data-dismiss="modal" type="button">关闭</button>
					<button class="btn btn-primary" id="add-picture2" disabled>添加</button>
				</div>
			</div>
		</div>
	</div>

	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<%-- <script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script> --%>
	<script type="text/javascript">
		var ue = UE.getEditor('container', {
			initialFrameHeight : 500
		});

		ue.ready(function() {
			ue.execCommand('insertHtml', $('#htmlContent').text());
		});
		$(function() {
			$(".datepicker").datepicker({
				format : 'yyyy-mm-dd'
			});
			$("#selectUsers").hide();
			/*  var ue = UE.getEditor('container', {
			    initialFrameHeight: 100
			}); */
			$('#selectEnterprise').select2({
				placeholder : "请输入企业名称搜索",
				minimumInputLength : 0,
				ajax : {
					url : "${basePath}enterprise/list/usable?type=0",
					dataType : 'json',
					quietMillis : 100,
					data : function(term) {
						return {
							keyword : term, //search term
						};
					},
					results : function(data) {
						return {
							results : data
						};
					}
				},
				formatResult : function(object, container, query) {
					return object.name;
				},
				formatSelection : function(object, container) {
					//选中时触发
					return object.name;
				},
				escapeMarkup : function(m) {
					return m;
				} // we do not want to escape markup since we are displaying html in results
			});

			$(".upload-picture").on("click", ".icon-trash", function() {
				var $this = $(this);
				if (confirm("您确定要删除该图片吗?,图片一旦删除，将不可恢复!")) {
					var picId = $this.parent().parent().attr("picId");
					$.ajax({
						url : "delete/picture?id=" + picId,
						type : "post",
						dataType : "json",
						success : function(data) {
							if (data.status == "success") {
								$("#picture-" + picId).remove();
								$this.parent().parent().remove();
							} else {
								alert("服务器忙,请稍后重试");
							}
						}
					});
				}
				return false;
			});
			//进来清空
			$(".upload-picture").click(function() {
				$("#picName").val("");
				$("#target").attr("src", "");
				$("#add-picture").attr("disabled", true);

				$("#picName2").val("");
				$("#target2").attr("src", "");
				$("#add-picture2").attr("disabled", true);
			});
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
			$("#fileupload")
					.fileupload(
							{
								url : "upload?type=14",
								autoUpload : false, //不自动上传
								formData : new FormData().append("picName", $.trim($("#picName").val())),
								add : function(e, data) {
									var file = data.files[0];
									if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
										$(".alert-danger .alert-content").text("错误的图片类型");
										$(".alert-danger").fadeIn().delay(2000).fadeOut();
										return false;
									}
									if (file.size > 512000 * 6) {//10M
										$(".alert-danger .alert-content").text("图片大于3M");
										$(".alert-danger").fadeIn().delay(2000).fadeOut();
										return false;
									}
									var reader = new FileReader();
									reader.onload = function(e) {
										$('#target').attr('src', e.target.result);
									};
									reader.readAsDataURL(file);
									data.context = $("#add-picture").unbind("click").bind("click", function() {
										if ($.trim($("#picName").val()) == '') {
											alert("请输入图片名称");
											return;
										}
										$('#little_img').val('1');
										data.submit();
									});
									$("#add-picture").attr("disabled", false);
								},
								added : function(e, data) {
									console.log(data.files);
								},
								done : function(e, result) {
									var data = JSON.parse(result.result);
									console.log(data);
									if (data.status == "error") {
										$(".alert-danger .alert-content").text(data.message);
										$(".alert-danger").fadeIn().delay(2000).fadeOut();
									} else {
										var $html = $('<a class="gallery-item fancybox" rel="g1" title="" picId="">'
												+ '<img src="" />' + '<div class="actions">'
												+ '<i class="icon-trash"></i><i class="icon-zoom-in"></i>' + '</div>'
												+ '</a>');
										var path = '${aPath}upload/' + data.object.picturePath;
										$html.attr("href", path).attr("title", data.object.name).attr("picId",
												data.object.id);
										$html.find("img").attr("src", path);
										$html.insertBefore($("#enterprise-picture"));
										$("#myModal").modal("hide");
										//添加隐藏输入框 保存当前的图片ID
										$("#validate-form")
												.append(
														'<input id="picture-' + data.object.id + '" type="hidden" name="picture" value="' + data.object.id + '"/>');
									}
								}
							});

			$("#fileupload2")
					.fileupload(
							{
								url : "upload?type=12", //大图
								autoUpload : false, //不自动上传
								formData : new FormData().append("picName", $.trim($("#picName2").val())),
								add : function(e, data) {
									var file = data.files[0];
									if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
										$(".alert-danger .alert-content").text("错误的图片类型");
										$(".alert-danger").fadeIn().delay(2000).fadeOut();
										return false;
									}
									if (file.size > 512000 * 6) {//10M
										$(".alert-danger .alert-content").text("图片大于3M");
										$(".alert-danger").fadeIn().delay(2000).fadeOut();
										return false;
									}
									var reader = new FileReader();
									reader.onload = function(e) {
										$('#target2').attr('src', e.target.result);
									};
									reader.readAsDataURL(file);
									data.context = $("#add-picture2").unbind("click").bind("click", function() {
										if ($.trim($("#picName2").val()) == '') {
											alert("请输入图片名称");
											return;
										}
										$('#big_img').val('1');
										data.submit();
									});
									$("#add-picture2").attr("disabled", false);
								},
								added : function(e, data) {
									console.log(data.files);
								},
								done : function(e, result) {
									var data = JSON.parse(result.result);
									console.log(data);
									if (data.status == "error") {
										$(".alert-danger .alert-content").text(data.message);
										$(".alert-danger").fadeIn().delay(2000).fadeOut();
									} else {
										var $html = $('<a class="gallery-item fancybox" rel="g1" title="" picId="">'
												+ '<img src="" />' + '<div class="actions">'
												+ '<i class="icon-trash"></i><i class="icon-zoom-in"></i>' + '</div>'
												+ '</a>');
										var path = '${aPath}upload/' + data.object.picturePath;
										$html.attr("href", path).attr("title", data.object.name).attr("picId",
												data.object.id);
										$html.find("img").attr("src", path);
										$html.insertBefore($("#borrow-picture"));
										$("#myModal2").modal("hide");
										//添加隐藏输入框 保存当前的图片ID
										$("#validate-form")
												.append(
														'<input id="picture-' + data.object.id + '" type="hidden" name="picture2" value="' + data.object.id + '"/>');
									}
								}
							});
			$('.select2able').select2();

			var option = {
				placeholder : "请输入用户昵称、手机号或者真实姓名搜索",
				minimumInputLength : 0,
				multiple : true,
				ajax : {
					url : "${basePath}user/useSuperInvestor",
					dataType : 'json',
					quietMillis : 100,
					data : function(term) {
						return {
							username : term, //search term
						};
					},
					results : function(data) {
						return {
							results : data
						};
					}
				},
				formatResult : function(object, container, query) {
					return object.username + "(" + object.phone + ")";
				},
				formatSelection : function(object, container) {
					//选中时触发

					$.ajax({
						type : "GET",
						url : "${basePath}project/userAssetsBalance",
						data : {
							userId : object.id,
							totalAmount : $("#totalAmount").val()
						},
						dataType : "json",
						success : function(adata) {
							if (adata) {
								alert("超级投资人余额不足");
							}
						}
					});
					return object.username + "(" + object.phone + ")";
				},
				escapeMarkup : function(m) {
					return m;
				}, // we do not want to escape markup since we are displaying html in results
			};
			$('#selectUser').select2(option);

			$("#validate-form").validate({
				rules : {
					title : {
						required : true,
						maxlength : 128,
						remote : {
							url : "checkName", //后台处理程序
							type : "get",
							dataType : "json",
							data : { //要传递的数据
								username : function() {
									return $("#title").val();
								}
							}
						}
					},
					annualized : {
						required : true,
						range : [ 0, 1 ]
					},
					deadline : {
						date : true
					},
					limitDays : {
						required : true,
						digits : true
					},
					totalAmount : {
						required : true,
						digits : true
					},
					projectDescription : {
						maxlength : 1024
					},
					useOfFunds : {
						maxlength : 1024
					},
					repaymentSource : {
						maxlength : 1024
					},
					investorsNum : {
						digits : true
					},
					collateralInfo : {
						maxlength : 1024
					},
					transferable : {
						digits : true,
						range : [ 0, 365 ]
					}
				},
				messages : {
					goodsName : {
						required : "请输入商品名",
						maxlength : "商品名不超过128个字符",
						remote : "商品名称已存在"
					},
					annualized : {
						required : "请输入利率",
						range : "请输入0-1之间的小数"
					},
					deadline : {
						date : "请输入正确的时间格式"
					},
					limitDays : {
						required : "请输入商品期限",
						digits : "请输入正确的整数"
					},
					totalAmount : {
						required : "请输入总募集金额",
						digits : "请输入正确的整数"
					},
					projectDescription : {
						maxlength : "不能超过1024个字符"
					},
					useOfFunds : {
						maxlength : "不能超过1024个字符"
					},
					repaymentSource : {
						maxlength : "不能超过1024个字符"
					},
					investorsNum : {
						digits : "请输入正确的整数"
					},
					collateralInfo : {
						maxlength : "不能超过1024个字符"
					},
					riskControlMethod : {
						maxlength : "不能超过1024个字符"
					},
					involvingLawsuitInfo : {
						maxlength : "不能超过1024个字符"
					},
					suggestion : {
						maxlength : "不能超过1024个字符"
					},
					transferable : {
						digits : "请输入正确的整数",
						rang : "请输入0-365之间的小数"
					}
				//                userId: {
				//                    required: "请选择超级投资用户",
				//                    remote: "超级投资人账户余额不够"
				//                }
				}
			});

			var detailOption = {
				placeholder : "请选债权模版",
				minimumInputLength : 0,
				ajax : {
					url : "${basePath}project/getContractTitleList",
					dataType : 'json',
					quietMillis : 100,
					data : function(term) {
						return {
							username : term, //search term
						};
					},
					results : function(data) {
						return {
							results : data
						};
					}
				},
				initSelection : function(element, callback) {
					//初始化赋值
					callback({
						id : '${map.detailId}',
						title : '${map.detailTitle}'
					});//调用formatSelection
				},
				formatResult : function(object, container, query) {
					return object.title;
				},
				formatSelection : function(object, container) {
					//选中时触发
					var id = object.id;
					$('#detailId').val(id);
					return object.title;
				},
				escapeMarkup : function(m) {
					return m;
				} // we do not want to escape markup since we are displaying html in results
			};
			$('#detailId').select2(detailOption);

		});

		function goToPage(obj) {
			if (obj.value == 1) {
				$('#tag').val('新手专享');
			} else if (obj.value == 2) {
				$('#tag').val('VIP专享');
			} else {
				$('#tag').val(null);
			}
		}
		function goToPage2(obj) {
			if (obj.value == 3) {
				var totalAmount = $('#totalAmount').val();
				if (totalAmount == null || totalAmount == '') {
					alert("总投资金额为空");
				}
				$('#selectUsers').show();
				$('#noob').hide();
			} else {
				$('#selectUsers').hide();
			}
		}
		function transabledclick() {
			if ($("#transferablecheck").is(':checked') == true) {
				$("#transferablecheckfor").show();
			} else {
				$('#transferable').val(0);
				$("#transferablecheckfor").hide();
			}

		}
		function clearCheck() {
			var limitDays = $('#limitDays').val();
			console.log(limitDays)
			if (parseInt(limitDays) <= 90) {
				$("#transferablecheck").prop("checked", false);
				$('#transferable').val(0);
				$("#transferablecheckfor").hide();
			}
			if (parseInt(limitDays) > 90) {
				$("#transferablecheck").prop("checked", true);
				$("#transferablecheckfor").show();
			}
		}
		function clearResult() {
			var limitDays = $('#limitDays').val();
			var checked = $("#transferablecheck").is(':checked')
			var transferable = $('#transferable').val();
			if (checked == true) {
				if (parseInt(transferable) <= 1) {
					$("#result").text("输入的天数必须大于1");
					return false;
				} else if (parseInt(transferable) <= parseInt(limitDays)) {
					$("#result").text("");
					return true;
				}
			}
		}

		function CheckPost() {
			clearResult();

			if (comm.isEmpty($('#goodsName').val())) {
				alert("请输入商品名称");
				return false;
			}
			if (comm.isEmpty($('#buyingPrice').val())) {
				alert("请输入进货价");
				return false;
			}
			if (comm.isEmpty($('#salingPrice').val())) {
				alert("请输入销售价");
				return false;
			}
			if (comm.isEmpty($('#memberSalingPrice').val())) {
				alert("请输入会员价");
				return false;
			}
			if (comm.isEmpty($('#stock').val())) {
				alert("请输入库存");
				return false;
			}
			if (comm.isEmpty($('#sellStock').val())) {
				alert("请输入已出售数量");
				return false;
			}
			if (comm.isEmpty($('#goodsNo').val())) {
				alert("请输入商品编号");
				return false;
			}
			var sellStock = $("#sellStock").val();
			 if(sellStock < 0){
				 alert('售出数量不能小于0');
				 return false;
			 }
			var totalAmount = $.trim($("#totalAmount").val());
			/* if (!/^[1-9]\d*00$/.test(totalAmount)) {
				alert("商品募集金额必须是100的整数倍");
				return false;
			} */
			var annualized = $('#annualized').val();
			var increaseAnnualized = $('#increaseAnnualized').val();
			if (parseFloat(annualized) + parseFloat(increaseAnnualized) >= 1) {
				alert("年化收益+年化收益加息要小于1！");
				return false;
			}
			var limitDays = $('#limitDays').val();
			var checked = $("#transferablecheck").is(':checked')
			var transferable = $('#transferable').val();
			if (checked == true) {
				if (parseInt(transferable) > parseInt(limitDays)) {
					alert("输入的天数必须小于商品期限！");
					$("#result").text("输入的天数必须小于商品期限！");
					return false;
				}
			}

			var categoryId = $('#categoryId').val();
			if (categoryId == null || categoryId == '') {
				alert("请选择商品的类别");
				return false;
			}
			var little_img = $('#little_img').val();
			if (little_img == null || little_img == '') {
				alert("请选择商品封面图");
				return false;
			}
			/* 			var big_img = $('#big_img').val();
			 if (big_img == null || big_img == '') {
			 alert("请选择商品详细图");
			 return false;
			 } */

			var stockUnit = $('#stockUnit').val();
			if (stockUnit == '-1') {
				alert('请选择库存单位');
				return false;
			}

			var upDown = $("#upDown").val();
			if(upDown == '' || upDown == null){
				alert('请选择是否上架');
				return false;
			}
			
			$('#htmlContent').text(ue.getContent());
			$('#textContent').text(ue.getContentTxt());
			
			
		}

		//切换商品属性
		function listGoodsProperty() {

			var categoryId = $('#categoryId').val(); //选中的值

			$.ajax({
				url : "${basePath}shop/listGoodsPropertyAjax",
				data : {
					categoryId : categoryId
				},
				dataType : "json",
				success : function(result) {
					packagePropertyHtml(result);
				}
			});
		}
		//组装属性html
		function packagePropertyHtml(obj) {
			console.log(obj);
			var html = '';
			if (obj.length) {
				$.each(obj, function(index, ele) {
					console.log(ele.propertyName);
					html += '<div class="form-group">' + '<label class="control-label col-md-2 flag">'
							+ ele.propertyName + '</label>' + '<input   name="ids" value="'+ele.id+'"  type="hidden">'
							+ '<div class="col-md-7">' + '<input class="form-control" name="category" type="text">'
							+ '</div>' + '</div>';
				});

				$('.selectHTML').html(html);
			}
		}

		function sortNumber() {
			var sort = $.trim($('#sort').val());
			if (sort == '') {
				$('#sort').val(99);
			}
			if (sort > 99) {
				$('#sort').val(99);
			}
		}
	</script>
</body>
</html>