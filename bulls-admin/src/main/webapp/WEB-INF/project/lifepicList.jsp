<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
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
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css" />
    <style>
        html * {
            margin: 0;
            padding: 0;
        }
        .widget-container .widget-content{
        	width: 75% !important;
        	margin: auto;
        }
        .imgUl>li{
        	float:left;
        	width:25%;
        	padding:15px;
        }.imgUl>li>img{
        	width:100%;	
        	height: 300px;
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
							牛只生活照列表 
							<shiro:hasPermission name="project:lifePic:upload">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="addpic?earNumber=${earNumber}"> 
									上传牛只生活照
								</a>
							</shiro:hasPermission>
						</div>
						
						<div class="widget-content padded clearfix">
						    <ul class="imgUl">
								<c:forEach var="list" items="${list}" varStatus="s">
									<li>
								 		<img src="${list.upload.cdnPath }">
		     							 <shiro:hasPermission name="project:lifePic:delete">
		     						     	<i class="icon-trash" style="float:right;" onClick="deleteLifePic('${list.id}');"></i>
		     						     </shiro:hasPermission>
									</li>
								</c:forEach>
							</ul>
							<div style="clear:both"></div>
							<ul id="pagination" style="float: right"></ul> 
							<div class="col-md-7 text-center">
			                     <a class="btn btn-default-outline" href="list">返回</a>
			                </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">
function deleteLifePic(id){
    if (confirm("您确定要删除该图片吗?,图片一旦删除，将不可恢复!")) {
        $.ajax({
            url: "lifePictureDelete?id=" + id,
            type: "get", 
            success: function (data) {
            	var res = $.parseJSON(data);
                if (res.status == "success") { 
					window.location.reload();
                } else {
                    alert("服务器忙,请稍后重试");
                }
            }
        });
    }
}

$(function() {
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
	 
        
	$('#pagination')
	.bootstrapPaginator(
			{
				currentPage : parseInt('${page}'),
				totalPages : parseInt('${pages}'),
				bootstrapMajorVersion : 3,
				alignment : "right",
				pageUrl : function(type, page, current) {
					return "lifePicList?earNumber=${earNumber}&page=" + page;
				}
			});
});
</script>
</html>
