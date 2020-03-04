<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品后台评价列表</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
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
				<h1>商品后台评价列表</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							后台评价列表
						</div>

							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th>评价时间</th>
											<th>评价内容</th>
											<th>评价图片</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>${g.id }</td>
												<td><fmt:formatDate value="${g.createDate }" pattern="yyyy-MM-dd hh:mm" /></td>
												<td>${g.content }</td>
												<td>
													<c:forEach var="img" items="${g.assessImgs}">
														<a href="${img.upload.cdnPath}" target="_blank" > <img src="${img.upload.cdnPath}" height="80px" width="80px"> </a>
													</c:forEach>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<ul id="pagination" style="float: right"></ul>
						</div>
					</div>
				</div>

			<shiro:hasPermission name="shop:addAppraise">
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							添加评价
						</div>
						<div class="widget-content padded clearfix">
							<form action="addGoodsComment" method="post" class="form-horizontal" id="validate-form">
								<input type="hidden" value="${goodId }" name="goodId" />

								<div class="form-group">
									<label class="control-label col-md-2">评价</label>
									<div class="col-md-7">
										<textarea  class="form-control" name="content" id="content" cols="30" rows="10"></textarea>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">
										上传图片
										<br>
										<font color="red">(图片尺寸：750px*750px)</font>
									</label>
									<div class="col-md-7 upload-picture">
										<c:forEach var="pic" items="${good.goodsPictures }">
											<c:if test="${pic.type==12}">
												<a class="gallery-item fancybox" rel="g1" href="${aPath}upload/${pic.upload.path }" picId="${pic.id }" title="${pic.name }">
													<img src="${aPath}upload/${pic.upload.path }" />
													<div class="actions">
														<i class="icon-trash"></i>
														<i class="icon-zoom-in"></i>
													</div>
												</a>
											</c:if>
										</c:forEach>
										<a data-toggle="modal" href="#myModal2" id="borrow-picture">
											<i class="iconfont" style="font-size: 150px; cursor: pointer;">&#xe602;</i>
										</a>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="text-center col-md-7">
										<a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
										<button class="btn btn-primary" type="button" onclick="checkPost();">编辑</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</shiro:hasPermission>

			</div>
			<!-- end DataTables Example -->
		</div>
	</div>


	<div class="modal fade" id="myModal2">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" class="close" data-dismiss="modal" type="button">&times;</button>
					<h4 class="modal-title">商品图片上传</h4>
				</div>
				<div class="modal-body">
					<form action="" class="form-horizontal" id="picture-form2">
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


	<script src="${basePath}js/jquery-1.10.2.min.js"></script>
	<script src="${basePath}js/jquery-ui.js"></script>
	<script src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>


	<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
	<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>

	<script type="text/javascript">

		$(function() {
			$('#pagination').bootstrapPaginator({
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "appraiseList?goodId=${goodId}&page=" + page;
				}
			});


            $(".upload-picture").on("click", ".icon-trash", function () {
                var $this = $(this);
                if (confirm("您确定要删除该图片吗?,图片一旦删除，将不可恢复!")) {
                    var picId = $this.parent().parent().attr("picId");
                    $.ajax({
                        url: "delete/picture?id=" + picId,
                        type: "post",
                        dataType: "json",
                        success: function (data) {
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
            $(".upload-picture").click(function () {
                $("#target2").attr("src", "");
                $("#add-picture2").attr("disabled", true);
            });
            $(".fancybox").fancybox({
                maxWidth: 700,
                height: 'auto',
                fitToView: false,
                autoSize: true,
                padding: 15,
                nextEffect: 'fade',
                prevEffect: 'fade',
                helpers: {
                    title: {
                        type: "outside"
                    }
                }
            });

            $("#fileupload2").fileupload({
                url: "uploadCommentImg",
                autoUpload: false, //不自动上传
                add: function (e, data) {
                    var file = data.files[0];
                    if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                        $(".alert-danger .alert-content").text("错误的图片类型");
                        $(".alert-danger").fadeIn().delay(2000).fadeOut();
                        return false;
                    }
                    if (file.size > 512000*6) {//10M
                        $(".alert-danger .alert-content").text("图片大于3M");
                        $(".alert-danger").fadeIn().delay(2000).fadeOut();
                        return false;
                    }
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('#target2').attr('src', e.target.result);
                    };
                    reader.readAsDataURL(file);
                    data.context = $("#add-picture2").unbind("click").bind("click", function () {
                        data.submit();
                    });
                    $("#add-picture2").attr("disabled", false);
                },
                added: function (e, data) {
                    console.log(data.files);
                },
                done: function (e, result) {
                    var data = JSON.parse(result.result);
                    console.log(data);
                    if (data.status == "error") {
                        $(".alert-danger .alert-content").text(data.message);
                        $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    } else {
                        var $html = $('<a class="gallery-item fancybox" rel="g1" title="" picId="">' +
                            '<img src="" />' +
                            '<div class="actions">' +
                            '<i class="icon-trash"></i><i class="icon-zoom-in"></i>' +
                            '</div>' +
                            '</a>');
                        var path = '${aPath}upload/' + data.object.picturePath;
                        $html.attr("href", path).attr("title", data.object.name).attr("picId", data.object.id);
                        $html.find("img").attr("src", path);
                        $html.insertBefore($("#borrow-picture"));
                        $("#myModal2").modal("hide");
                        //添加隐藏输入框 保存当前的图片ID
                        $("#validate-form").append('<input id="picture-' + data.object.id + '" type="hidden" name="picture2" value="' + data.object.id + '"/>');
                    }
                }
            });

		});


        function checkPost() {
            $('#validate-form').submit();
        }


	</script>
</body>
</html>