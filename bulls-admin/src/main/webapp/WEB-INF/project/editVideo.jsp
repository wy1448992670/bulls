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
    <title>编辑视频</title>
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
        #imgHtml{
        	margin-right:20px;
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
            <h1>牧场管理</h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>
                        上传视频及封面
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="submitEditVideo" method="post" class="form-horizontal" id="validate-form"  onsubmit="return checkForm();">
                        	<input type="hidden" name="id" value="${video.id }" />
                            <input type="hidden" name="token" value="${sessionScope.token }"/>
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                  	  视频标题
                                    <br>
                                </label> 
                                <div class="col-md-7 upload-picture"> 
                                    <input class="form-control" name="title" id="title" value="${video.title }" placeholder="视频标题" type="text" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="control-label col-md-2">
                               		 视频封面图片
                                    <br>
                                </label>
                                <div class="col-md-2 upload-picture" id="imgHtml">
                             		<img width="150px" height="150px" src="${video.videoPageUrl }"  /> 
                             	 </div>
                             	 <div class="col-md-7" data-toggle="modal" href="#myModal"  id="enterprise-picture">
                           	 		<input type="hidden" name="videoPageUrl" id="picpath" value="${video.videoPageUrl }"/>
                                    <i class="iconfont" style="font-size: 150px; cursor: pointer;" id="addPic">&#xe602;</i>
                                 </div> 
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">
                                   	 视频内容
                                    <br>
                                </label>

                                <div class="col-md-7 upload-picture-1">
                                     <video src="${video.videoUrl }" style="width:20%;"  controls="controls" autoplay="autoplay"  muted="true"  />
                                     <input value="${video.videoUrl }" name="videoUrl"  />
                                </div>
                            </div>                     
                            <div class="form-group">
                                <label class="control-label col-md-2">是否推荐</label>
                                <div class="col-md-7">
                                    <select class="select2able" name="isRecommend"  id="isRecommend">
                                        <option value="0" <c:if test="${video.isRecommend == 0 }">selected</c:if> >否</option>
                                        <option value="1" <c:if test="${video.isRecommend == 1 }">selected</c:if>>是</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
								<label class="control-label col-md-2">显示时间</label>
								<div class="col-md-7"> 
									<input class="form-control" style="width:300px;" id="showTime" name="showTime"
									 placeholder="显示时间" class="Wdate"  value="<fmt:formatDate value='${video.showTime }' pattern="yyyy-MM-dd HH:mm:ss"/>" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
								</div>
							</div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <input class="btn btn-primary" type="submit" value="提交修改"/>
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
                <h4 class="modal-title">视频封面上传</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal" id="picture-form">
                    <div class="form-group">
                        <label class="control-label col-md-2">图片名称</label>

                        <div class="col-md-7">
                            <input class="form-control" name="picName" id="picName" placeholder="请输入图片名称" type="text"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">图片</label>

                        <div class="col-md-3">
								<span class="btn btn-success fileinput-button">
									<i class="glyphicon glyphicon-plus"></i>
									<span>上传</span>
									<input type="file" name="file" id="fileupload">
								</span>
                            <img src="" onerror="src='data:image/jpeg;base64,iVBORw0KGgoAAAANSUhEUgAAALcAAACtCAYAAADyI+5iAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAHoSURBVHhe7dyxDQAwDMOw/n9wkC39Q+DADwSPfrt7UCRussRNlrjJEjdZ4iZL3GSJmyxxkyVussRNlrjJEjdZ4iZL3GSJmyxxkyVussRNlrjJEjdZ4iZL3GSJmyxxkyVussRNlrjJEjdZ4iZL3GSJmyxxkyVussRNlrjJEjdZ4iZL3GSJmyxxkyVussRNlrjJejNzUGS5yRI3WeImS9xkiZsscZMlbrLETZa4yRI3WeImS9xkiZsscZMlbrLETZa4yRI3WeImS9xkiZsscZMlbrLETZa4yRI3WeImS9xkiZsscZMlbrLETZbzebIsN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE2WuMkSN1niJkvcZImbLHGTJW6yxE3U3gdSsqv4SyZGJgAAAABJRU5ErkJggg=='"  id="target" width="200px;"/>
							
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
                <button class="btn btn-primary" id="add-picture" disabled>修改</button>
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
        
            $("#picName").val("");
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
            .fileupload({
                 url: "upload?type=1",
                 autoUpload: false, // 不自动上传
                 formData: new FormData().append("picName", $.trim($("#picName").val())),
                 add: function (e, data) {
                     var file = data.files[0];
                     if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp)$/i).test(file.type)) {
                     	alert("错误的图片类型");
                         $(".alert-danger .alert-content").text("错误的图片类型");
                         $(".alert-danger").fadeIn().delay(2000).fadeOut();
                         return false;
                     }
                     if (file.size > 10000000) {// 10M
                     	alert("图片大于10M");
                          $(".alert-danger .alert-content").text("图片大于10M");
                          $(".alert-danger").fadeIn().delay(2000).fadeOut();
                         return false;
                     }
                     var reader = new FileReader();
                     reader.onload = function (e) {
                         $('#target').attr('src', e.target.result);
                     };
                     reader.readAsDataURL(file);
                     data.context = $("#add-picture").unbind("click").bind("click", function () {
                         if ($.trim($("#picName").val()) == '') {
                             alert("请输入图片名称");
                             return;
                         }
                         $('#little_img').val('1');
                         data.submit();
                     });
                     $("#add-picture").attr("disabled", false);
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
                         var $html = $('<a class="gallery-item fancybox" rel="g1" title="" picId="">'
                             + '<img src="" />' + '<div class="actions">'
                             + '<i class="icon-trash"></i><i class="icon-zoom-in"></i>' + '</div>'
                             + '</a>');
                         var path = '${aPath}upload/' + data.object.picturePath;
                         $("#picpath").val(path);
                         $html.attr("href", path).attr("title", data.object.name).attr("picId",
                             data.object.id);
                         $html.find("img").attr("src", path);
                         $('#imgHtml').html($html);
                         //$html.insertBefore($("#enterprise-picture"));
                         //$("#addPic").hide();
                         $("#myModal").modal("hide");
                         // 添加隐藏输入框 保存当前的图片ID
                         $("#validate-form")
                             .append(
                                 '<input id="picture-' + data.object.id + '" type="hidden" name="picture" value="' + data.object.id + '"/>'); 
                        }
                    }
                });
    });
     
   function checkForm() {
    	var title = $('#title').val();
		if (title == null || title == '') {
			alert("请输入视频标题");
			return false;
		}
		
    	var picpath = $('#picpath').val();
		if (picpath == null || picpath == '') {
			alert("请选择视频封面图");
			return false;
		}
	 	
		var showTime = $('#showTime').val(); 
		if (showTime == null || showTime == '') {
			alert("请选择显示时间");
			return false;
		}
    } 
</script>
</body>
</html>