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
<title>添加icon分组</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css"
	media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.fileinput-button input {
	width: 75px;
}

.check_block {
	display: block;
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
				<h1>icon管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>添加icon分组
						</div>
						<div class="widget-content padded clearfix">
							<form action="${basePath}icon/saveIconGroup?groupId=${group.id}"
								method="post" class="form-horizontal" id="validate-form">
								<div class="form-group">
									<label class="control-label col-md-2">组名</label>

									<div class="col-md-7">
										<input class="form-control" name="title" id="title"
											value="${group.title }" placeholder="请输入组名" type="text">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">类型</label>

									<div class="col-md-7">
										<select class="select2able" name="type" id="type">
											<option value="0" <c:if test="${group.type == 0 }">selected</c:if>>首页</option>
											<option value="1" <c:if test="${group.type == 1 }">selected</c:if>>个人主页</option>
											<option value="2" <c:if test="${group.type == 2 }">selected</c:if>>tabbarIcon</option>
											<option value="3" <c:if test="${group.type == 3 }">selected</c:if>>tabbarIconGray</option>
											<option value="4" <c:if test="${group.type == 4 }">selected</c:if>>账户中心</option>
											<option value="5" <c:if test="${group.type == 5 }">selected</c:if>>关于我们</option>
											<option value="6" <c:if test="${group.type == 6 }">selected</c:if>>商城导航</option>
											<option value="7" <c:if test="${group.type == 7 }">selected</c:if>>商城首页-ICON</option>
											<option value="8" <c:if test="${group.type == 8 }">selected</c:if>>商城首页-活动</option>
										</select>
									</div>
								</div>

								<!-- 列表栏 -->
								<div class="form-group icons" id="iconList">
									<label class="control-label col-md-2">icon列表</label>

									<div class="col-md-7" id="imgs">
										<c:if test="${group.type == 0}">
											<c:forEach var="pic" items="${list }">
												<div style="display: inline-block; margin-right: 10px;">
													<img alt="" src="${aPath}upload/${pic.path}" width="60" />

													<p style="margin: 0; text-align: center;">${pic.title }</p>
													<input type="hidden" name="home" class="id-class"
														value="${pic.picId }" /> <input type="hidden"
														name="homeIcon" class="id-class" value="${pic.iconId }" />
													<input type="hidden" name="homeId" class="id-class"
														value="${pic.linkId }" />
												</div>
											</c:forEach>
										</c:if>
									</div>
									<div class="col-md-7" id="imgs2">
										<c:if test="${group.type == 1 || group.type == 2 || group.type == 3 || group.type == 4 || group.type == 5 || group.type == 6}">
											<c:forEach var="pic" items="${list }">
												<div style="display: inline-block; margin-right: 10px;"
													class="list-class">
													<img alt="" src="${aPath}upload/${pic.path}" width="60" />

													<p style="margin: 0; text-align: center;">${pic.title }</p>
													<input type="hidden" name="me" class="id-class"
														value="${pic.picId }" /> <input type="hidden"
														name="meIcon" class="id-class" value="${pic.iconId }" /> <input
														type="hidden" name="meId" class="id-class"
														value="${pic.linkId }" />
												</div>
											</c:forEach>
										</c:if>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">编辑icon元素</label>

									<div class="col-md-7">
										<a href="#myModal" id="iconBtn" role="button"
											class="btn btn-primary" data-toggle="modal">添加</a>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">适应版本</label>

									<div class="col-md-7">
										<input class="form-control" name="version" id="version"
											value="${group.version }" placeholder="请输入分组对应版本号"
											type="text">
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">状态</label>

									<div class="col-md-7">
										<label class="radio-inline"> <input name="status"
											type="radio" value="0"
											<c:if test="${group.status == 0}">checked</c:if>> <span>闲置</span>
										</label> <label class="radio-inline"> <input name="status"
											type="radio" value="1"
											<c:if test="${group.status == 1}">checked</c:if>> <span>可用</span>
										</label>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline"
											onclick="javascript:window.history.go(-1);">取消</a>
										<button class="btn btn-primary" id="createGroup" type="submit">创建分组</button>
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
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">icon选择</h4>
				</div>
				<div class="modal-body body_content">
					<form action="add" method="post" class="form-horizontal"
						id="validate-form">
						<table class="table table-bordered table-striped  table-hover">
							<thead>
								<tr>
									<th class="check-header hidden-xs"><label> <input
											id="checkAll" name="checkAll" type="checkbox"> <span></span>
									</label></th>
									<th>序号</th>
									<th>名称</th>
									<th>icon</th>
								</tr>
							</thead>
							<tbody id="iconBody">
							</tbody>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="saveBtn" class="btn btn-primary">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>


	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-fileupload.js"
		type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js"
		type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js"
		type="text/javascript"></script>
	<script src="${basePath}js/common/common.js" type="text/javascript"></script>

	<script type="text/javascript">
		window.onload = function() {
			$('.select2able').select2();
			$('#type').change(function() {

				var value = "${group.id}";
				if (Tools.isEmpty(value)) {
					value = "";
				}
				var val = $(this).val();
				if (val == 0) {
					$('#imgs').show();
					$('#imgs2').hide();
				} else {
					$('#imgs2').show();
					$('#imgs').hide();
				}
				getIronList(value);
			});

			$("#createGroup").on('click', function() {
				var title = $("#title").val();
				var version = $("#version").val();
				if (Tools.isEmpty(title)) {
					alert("icon分组名称不能为空！")
					return false;
				}
				if (Tools.isEmpty(version)) {
					alert("版本号不能为空！")
					return false;
				}
			});

			$(".fileupload")
					.fileupload(
							{
								url : "${basePath}icon/upload",
								autoUpload : true, //不自动上传
								add : function(e, data) {
									var $img = $(e.target).prev().prev();
									for (var i = 0; i < data.files.length; i++) {
										var file = data.files[i];
										console.log(file)
										if (!new RegExp(
												/(\.|\/)(gif|jpe?g|png|bmp)$/i)
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
											$img.attr('src', e.target.result);
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
										$("#validate-form")
												.append(
														'<input type="hidden" name="picture" value="' + data.id + '"/>');
									}
								}
							});

			$("#validate-form").validate({
				rules : {
					title : {
						required : true,
						maxlength : 20
					},
					trueName : {
						required : true,
						maxlength : 20
					},
					password : {
						required : true,
						minlength : 5,
						maxlength : 20
					},
					password2 : {
						required : true,
						minlength : 5,
						maxlength : 20,
						equalTo : "#password"
					}
				},
				messages : {
					username : {
						required : "请输入用户名",
						maxlength : "用户名不能超过20个字",
						remote : "用户名已存在"
					},
					trueName : {
						required : "请输入真名",
						maxlength : "真名不能超过20个字"
					},
					password : {
						required : "请输入密码",
						minlength : "密码至少5个字",
						maxlength : "密码不能超过20个字"
					},
					password2 : {
						required : "请再次输入密码",
						minlength : "密码至少5个字",
						maxlength : "密码不能超过20个字",
						equalTo : "两次输入密码不一致"
					}
				}
			});

			$("#iconBtn").on("click", function() {
				var value = "${group.id}";
				if (Tools.isEmpty(value)) {
					value = "";
				}
				getIronList(value);
			});
		};

		function getClassData(obj, className) {
			var length = obj.find(".className").length;
			var array = [];
			for (var i = 0; i < length; i++) {
				var value = obj.find(".className").eq(i).val();
				array.push(value);
			}
			return array;
		}

		$("#saveBtn")
				.on(
						"click",
						function() {
							var array = getData();
							if (!Tools.isEmpty(array)) {
								//array.sort(getSortFun('asc', 'sort'));
								var type = $("#type").val();
								var temp = "";
								var name_value = type == 0 ? "home" : "me";
								var name2_value = type == 0 ? "homeIcon"
										: "meIcon";
								var name3_value = type == 0 ? "homeId" : "meId";
								var img_value = type == 0 ? "imgs" : "imgs2";
								for ( var i in array) {
									temp += '<div style="display: inline-block;margin-right: 10px;" class="fileinput-button">'
											+ '<img alt="" src=' + array[i].src + ' width="60"/>'
											+ '<p style="margin:0;text-align:center;">'
											+ array[i].title
											+ '</p>'
											+ '<input type="hidden" name="' + name3_value + '" value="' + array[i].linkId + '"/>'
											+ '<input type="hidden" name="' + name2_value + '" value="' + array[i].iconId + '"/>'
											+ '<input type="hidden" name="' + name_value + '" value="' + array[i].picId + '"/></div>';
								}
								$("#" + img_value).html(temp);
								$('#myModal').modal('hide');
							}

						});

		function getIronList(value) {
			$
					.ajax({
						url : '${basePath}icon/getIconList',
						type : 'get',
						data : 'groupId=' + value,
						dataType : "json",
						success : function(result) {
							var temp = "";
							var icons = result;
							$
									.each(
											icons,
											function(i, item) {
												temp += '<tr class="dataTr">'
														+ '<td><label><input class="checkOne" name="'
														+ (i + 1)
														+ '" type="checkbox"><span class="checkImg"></span></label></td>'
														+ '<td>'
														+ (i + 1)
														+ '</td>'
														+ '<input class="link-class" type="hidden" name="linkId" value="' + item.id + '"/>'
														+ '<input class="pic-class" type="hidden" name="picId" value="' + item.picId + '"/>'
														+ '<input class="icon-class" type="hidden" name="iconId" value="' + item.iconId + '"/>'
														+ '<td class="dataTitle">'
														+ item.title
														+ '</td>'
														+ '<td>'
														+ '<img class="image" alt="" src="${aPath}upload/' + item.path + '" width="60"/>'
														+ '</td></tr>';

											});
							$('#iconBody').html(temp);
						}
					});
		};

		$("#checkAll").on("click", function() {
			var check = $(this).is(':checked');
			if (check) {
				$(".checkOne").prop("checked", true);
			} else {
				$(".checkOne").prop("checked", false);
			}
		});

		function getData() {
			var array = [];
			var length = $(".dataTr").length;
			var check = {};
			for (var i = 0; i < length; i++) {
				var checked = $(".dataTr").eq(i).find(".checkOne").prop(
						"checked");
				var data = {};

				if (checked) {
					var linkId = $(".dataTr").eq(i).find(".link-class").val();
					var picId = $(".dataTr").eq(i).find(".pic-class").val();
					var iconId = $(".dataTr").eq(i).find(".icon-class").val();
					var src = $(".dataTr").eq(i).find(".image").prop("src");
					var title = $(".dataTr").eq(i).find(".dataTitle").html();
					data.linkId = linkId;
					data.picId = picId;
					data.iconId = iconId;
					data.src = src;
					data.title = title;
					array.push(data);
				}
			}
			return array;
		};

		/* 		function getListData(){
		 var array = [];
		 var length = $(".dataTr").length;
		 var check = {};
		 for(var i = 0; i < length;i ++){
		 var checked = $(".dataTr").eq(i).find(".checkOne").prop("checked");
		 var data={};
		 if(checked){
		 var id = $(".dataTr").eq(i).find(".dataId").html();
		 var src = $(".dataTr").eq(i).find(".image").prop("src");
		 var title = $(".dataTr").eq(i).find(".dataTitle").html();
		 data.id=id;
		 data.src=src;
		 data.title=title;
		 array.push(data);
		 }
		 }
		 return array;
		 } */
	</script>
</body>
</html>