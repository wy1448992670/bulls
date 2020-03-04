<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>上传牛只生活照</title>
    <link href="${basePath}css/fileupload/lifepic.css"  rel="stylesheet" type="text/css"/>
    <script src="${basePath}js/lifepic/easyUploader.jq.js" type="text/javascript"></script>
    <style>
        html * {
            margin: 0;
            padding: 0;
        }
        .widget-container .widget-content{
        	width: 75% !important;
        	margin: auto;
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
				<h1>项目管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							上传牛只生活照 
						</div>
						<div class="widget-content padded clearfix">
							 <div id="uploader" class="widget-content"></div>
							  <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                                </div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
     <script>
        var uploader = easyUploader({
            id: "uploader",
            accept: '.jpg,.png,.doc,.docx',
            action: '${basePath}project/uploadLifePic',
            dataFormat: 'formData',
            maxCount: 20,
            maxSize: 3,
            multiple: true,
            data: null,
            beforeUpload: function(file, data, args) {
            	 data.append('earNumber', '${earNumber}');
            },
            onChange: function(fileList) {
            	console.log(fileList);
                /* input选中时触发 */
            },
            onRemove: function(removedFiles, files) {
                console.log('onRemove', removedFiles);
            },
            onSuccess: function(res) {
                console.log('onSuccess', res);
            },
            onError: function(err) {
                console.log('onError', err);
            },
        });
    </script>
</body>
</html>
