<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>编辑企业</title>
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
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                房东图片管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑企业
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="edit" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">相关图片</label>
                                <div class="col-md-7 upload-picture">
                                    <c:forEach var="pic" items="${enterprise.pictures }">
	                                    <a class="gallery-item fancybox" style="height:205px" rel="g1" href="/upload/${pic.picturePath }"
	                                       picId="${pic.id }" title="${pic.name }">
	                                       
	                                        <c:choose> 
	                                        	<c:when test="${fn:endsWith(pic.name, '.png') || fn:endsWith(pic.name, '.jpg') || fn:endsWith(pic.name, '.gif')}">  
	                                        		<img src="${aPath}${pic.picturePath}"/>
					   							</c:when>
	
	                                            <c:when test="${fn:endsWith(pic.name, '.docx') || fn:endsWith(pic.name, '.doc')}">
	                                                <img src="${aPath}icons/W.jpg"/>
	                                            </c:when>
	
	                                            <c:when test="${fn:endsWith(pic.name, '.xlsx') || fn:endsWith(pic.name, '.xls')}">
	                                                <img src="${aPath}icons/X.jpg"/>
	                                            </c:when>
	
	                                            <c:when test="${fn:endsWith(pic.name, '.pdf')}">
	                                                <img src="${aPath}icons/PDF.jpg"/>
	                                            </c:when>
	
	                                            <c:when test="${fn:endsWith(pic.name, '.zip')}">
	                                                <img src="${aPath}icons/ZIP.jpg"/>
	                                            </c:when>
	
	                                            <c:when test="${fn:endsWith(pic.name, '.ppt')}">
	                                                <img src="${aPath}icons/P.jpg"/>
	                                            </c:when>
	
	                                        </c:choose>
	                                        <div class="actions">
	                                            <i class="icon-trash"></i><i class="icon-zoom-in"></i>
	                                        </div>
	                                    </a>

                                    </c:forEach>
                                    <a data-toggle="modal" href="#myModal" id="enterprise-picture">
                                        <i class="iconfont" style="font-size: 150px;cursor: pointer;">&#xe602;</i>
                                    </a>
                                </div>
                            </div>
                            <input type="hidden" name="id" value="${enterprise.id }"/>
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
                <h4 class="modal-title">
                    文件上传
                </h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal" id="picture-form">
                    <div class="form-group">

                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-2">文件</label>

                        <div class="col-md-3">
                                    <span class="btn btn-success fileinput-button">
					                    <i class="glyphicon glyphicon-plus"></i>
					                    <span>上传</span>
					                    <input type="file" name="file" id="fileupload">
					                </span>
                            <img src="" id="target" width="200px;"/>

                            <div class="alert alert-danger" style="display: none;width: 300px;">
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
<script type="text/javascript">
    $(function () {
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
        $("#enterprise-picture").click(function () {
            $("#target").attr("src", "");
            $("#add-picture").attr("disabled", true);
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
        $("#fileupload").fileupload({
            url: "upload?enterpriseId=${enterprise.id }",
            maxFileSize: 10000000, //10M
            autoUpload: false, //不自动上传
            acceptFileTypes: /(\.|\/)(gif|jpe?g|png|bmp|pdf|vnd.ms-powerpoint|vnd.openxmlformats-officedocument.presentationml.presentation|vnd.ms-excel|vnd.openxmlformats-officedocument.spreadsheetml.sheet|msword|vnd.openxmlformats-officedocument.wordprocessingml.document|x-zip-compressed)$/i,
            formData: new FormData(),
            add: function (e, data) {
                var file = data.files[0];
                if (!new RegExp(/(\.|\/)(gif|jpe?g|png|bmp|pdf|vnd.ms-powerpoint|vnd.openxmlformats-officedocument.presentationml.presentation|vnd.ms-excel|vnd.openxmlformats-officedocument.spreadsheetml.sheet|msword|vnd.openxmlformats-officedocument.wordprocessingml.document|x-zip-compressed)$/i).test(file.type)) {
                    $(".alert-danger .alert-content").text("错误的文件类型");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                if (file.size > 10000000) {//10M
                    $(".alert-danger .alert-content").text("文件大于10M");
                    $(".alert-danger").fadeIn().delay(2000).fadeOut();
                    return false;
                }
                var reader = new FileReader();
                reader.onload = function (e) {
                	
                	if(file.name.indexOf(".png") != -1 || file.name.indexOf(".jpg") != -1  || file.name.indexOf(".gif") != -1  || file.name.indexOf(".jpeg") != -1 ){
                		$('#target').attr('src', e.target.result);
                		
                	}else if(file.name.indexOf(".docx") != -1  || file.name.indexOf(".doc") != -1 ){
                		$('#target').attr('src', "${aPath}icons/W.jpg");
                		
                	}else if(file.name.indexOf(".xlsx") != -1  || file.name.indexOf(".xls") != -1 ){
                		$('#target').attr('src', "${aPath}icons/X.jpg");
                		
                	}else if(file.name.indexOf(".pdf") != -1 ){
                		$('#target').attr('src', "${aPath}icons/PDF.jpg");
                		
                	}else if(file.name.indexOf(".zip") != -1 ){
                		$('#target').attr('src', "${aPath}icons/ZIP.jpg");
                		
                	}else if(file.name.indexOf(".ppt") != -1 ){
                		$('#target').attr('src', "${aPath}icons/P.jpg");
                		
                	}
                	
                };
                reader.readAsDataURL(file);
                data.context = $("#add-picture").unbind("click").bind("click", function () {
                    data.submit();
                });
                $("#add-picture").attr("disabled", false);
            },
            done: function (e, result) {
                var data = JSON.parse(result.result);
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
                    var path = '${aPath}';
                    
                    if(data.object.picturePath.indexOf(".png") != -1 || data.object.picturePath.indexOf(".jpg") != -1  || data.object.picturePath.indexOf(".gif") != -1  || data.object.picturePath.indexOf(".jpeg") != -1 ){
                    	path += data.object.picturePath;
                		
                	}else if(data.object.picturePath.indexOf(".docx") != -1  || data.object.picturePath.indexOf(".doc") != -1 ){
                		path += "icons/W.jpg";
                		
                	}else if(data.object.picturePath.indexOf(".xlsx") != -1  || data.object.picturePath.indexOf(".xls") != -1 ){
                		path += "icons/X.jpg";
                		
                	}else if(data.object.picturePath.indexOf(".pdf") != -1 ){
                		path += "icons/PDF.jpg";
                		
                	}else if(data.object.picturePath.indexOf(".zip") != -1 ){
                		path += "icons/ZIP.jpg";
                		
                	}else if(data.object.picturePath.indexOf(".ppt") != -1 ){
                		path += "icons/P.jpg";
                		
                	}
                    
                    
                    $html.attr("href", path).attr("title", data.object.name).attr("picId", data.object.id);
                    $html.find("img").attr("src", path);
                    $html.insertBefore($("#enterprise-picture"));
                    $("#myModal").modal("hide");
                    //添加隐藏输入框 保存当前的图片ID
                    $("#validate-form").append('<input id="picture-' + data.object.id + '" type="hidden" name="picture" value="' + data.object.id + '"/>');
                }
            }
        });
    });
</script>
</body>
</html>