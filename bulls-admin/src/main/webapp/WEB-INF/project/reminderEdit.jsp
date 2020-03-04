<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>温馨提示编辑</title>
	<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
	<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
    <link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
	<style type="text/css">
		.upload-picture a{display:inline-block; overflow: hidden;border: 0;vertical-align: top;margin: 0 5px 10px 0;background: #fff;}
		.gallery-item:hover{background: #000;}
		#validate-form i.icon-zoom-in{width: 36px;height: 36px;font-size: 18px;line-height: 35px;margin-top: 0;}
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
                   温馨提示编辑
                </h1>
            </div>
            <!-- DataTables Example -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="widget-container fluid-height clearfix">
                        <div class="heading">
                            <i class="icon-table"></i>温馨提示编辑
                        </div>
                        <div class="widget-content padded clearfix">
                            <form action="reminderCozyEdit" method="post" class="form-horizontal" id="validate-form">
                            <ul class="reminderAdd_ul" style="list-style:none;">
                          	<li class="form-group">
                                    <label class="control-label col-md-2">提示标题（所属页）</label>
                                    <div class="col-md-7">
                                        <input class="form-control" name="title" id="title" value="${prompt.title}" type="text" readonly="readonly">
                                    </div>
                                </li>
                                <li class="form-group" >
                                    <label class="control-label col-md-2">顺序</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="parent" name="parent" value="${prompt.parentId}" type="text" />
                                    </div>                           
                                </li>
                                <li class="form-group" >
                                    <label class="control-label col-md-2">提示内容</label>
                                    <div class="col-md-7">
                                        <input class="form-control" id="context" name="context" value="${prompt.context}" type="text" />
                                        <input class="form-control" name="id" value="${prompt.id}" type="hidden" />
                                        <input class="form-control" name="page" value="${page}" type="hidden" />
                                    </div>
                                </li>
                            </ul>
                                <div class="form-group">
                                	<label class="control-label col-md-2"></label>
                                    <div class="text-center col-md-7">
                                        <button class="btn btn-primary" type="submit">更新</button>
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
    <script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
    <script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
    <script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script>
    <script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
    <script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
    <script type="text/javascript">
    	$(function(){
    	        var temp = "";
    	        $('#add-row').on('click', function() {
    	        	temp = '<li class="form-group" id="items">'+
                        '<label class="control-label col-md-2">提示内容</label>'+
                        '<div class="col-md-7">'+
                           '<input class="form-control" name="context" type="text" />'+
                        '</div> '+                        
                    '</li>';
        				$(temp).appendTo($(".reminderAdd_ul"));
    	        });
    	        $(".btn btn-primary").change(function(){
	  	        	$("#form").submit();
 	        	});
    	});
    </script>
</body>
</html>