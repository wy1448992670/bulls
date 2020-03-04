<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>创建商品分类</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
	<script src="${basePath}js/97DatePicker/WdatePicker.js" type="text/javascript"></script>
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
        #target2{
        	display:none;
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
            <h1>商品分类管理</h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>
                        	新增商品分类
                    </div>
                    <div class="widget-content padded clearfix">
                        <form  method="post" class="form-horizontal" id="validate-form" >
                            <input type="hidden" name="token" value="${sessionScope.token }"/>
                            <div class="form-group">
                                <label class="control-label col-md-2">
                             	 商品分类名称
                                    <br>
                                </label> 
                                <div class="col-md-7 upload-picture"> 
                                    <input class="form-control" name="categoryName" id="categoryName" placeholder="分类名称" type="text" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                                                                                               商品分类banner
                                    <br>
                                </label>
                                <input type="hidden" id="little_img"/>
                                <div class="col-md-7 upload-picture">
                                    <a data-toggle="modal" href="#myModal"  id="enterprise-picture">
                                        <i class="iconfont" style="font-size: 150px; cursor: pointer;" id="addPic">&#xe602;</i>
                                    </a>
                                </div>
                            </div>
          
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <input class="btn btn-primary" type="submit" value="新增"/>
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
					<h4 class="modal-title">商品分类banner图片上传</h4>
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
							<label class="control-label col-md-2">商品分类banner图片</label>

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
 

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
    	$('.select2able').select2({width: "150"});
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

        // 进来清空
        $(".upload-picture").click(function () { 
            $("#target").attr("src", "");
            $("#add-picture").attr("disabled", true);

            // $("#picName2").val("");
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
        $("#fileupload")
		.fileupload(
				{
					url : "upload?type=23",
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
									data.id);
							$html.find("img").attr("src", path);
							$html.insertBefore($("#enterprise-picture"));
							$("#myModal").modal("hide");
							//添加隐藏输入框 保存当前的图片ID
							$("#validate-form")
									.append('<input id="picture-' + data.object.id + '" type="hidden" name="adUploadId" value="' + data.id + '"/>');
						}
					}
				});
    });
     
    $('#validate-form').submit(function (event) {
    	var categoryName = $('#categoryName').val();
		if ($.trim(categoryName) == null || $.trim(categoryName) == '') {
			alert("请输入商品分类名称");
			return false;
		}
     
    /* 	var little_img = $('#little_img').val();
		if (little_img == null || little_img == '') {
			alert("请选择商品分类banner图");
			return false;
		} */
	  
        var data = $(this).serialize();
        $.ajax({ 
            type: "POST",
            url: "addCategory",
            data: data,  
            processData: false,
            success: function (data) {
            	console.log(data) 
                if (data.status == "success") {
                    alert("添加成功！");
                    window.location.href="listCategory";
                } else {
                   alert("添加失败！");
                }
            }
        });
        return false;
    });
</script>
</body>
</html>