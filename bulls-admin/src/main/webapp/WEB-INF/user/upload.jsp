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
	<title>上传头像</title>
</head>
<body>
<div class="modal-shiftfix">
        <!-- Navigation -->
      	<jsp:include page="../common/header.jsp"></jsp:include>
        <!-- End Navigation -->
        <div class="container-fluid main-content">
            <div class="page-title">
                <h1>
                    后台用户管理
                </h1>
            </div>
            <!-- DataTables Example -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="widget-container fluid-height clearfix">
                        <div class="heading">
                            <i class="icon-table"></i>上传头像
                        </div>
                        <div class="widget-content padded clearfix">
                        	<form action="${basePath}user/edit/avatar" method="post" class="form-horizontal" enctype="multipart/form-data">
	                             <div class="form-group">
	                                    <label class="control-label col-md-2">头像</label>
	                                    <div class="col-md-4">
	                                        <div class="fileupload fileupload-new" data-provides="fileupload">
	                                            <div class="fileupload-new img-thumbnail" style="width: 200px; height: 150px;">
	                                            	<c:choose>
	                                            		<c:when test="${sessionScope.user.avatar != null}">
	                                            			<img src="${aPath}upload/${sessionScope.user.avatar}">
	                                            		</c:when>
	                                            		<c:otherwise>
	                                            			<img alt="" src="${aPath}images/no-image.gif">
	                                            		</c:otherwise>
	                                            	</c:choose>
	                                            </div>
	                                            <div class="fileupload-preview fileupload-exists img-thumbnail" style="width: 200px; max-height: 150px"></div>
	                                            <div>
	                                                <span class="btn btn-default btn-file">
	                                                    <span class="fileupload-new">选择图片</span>
	                                                    <span class="fileupload-exists">修改</span>
	                                                    <input type="file" name="file">
	                                                </span><a class="btn btn-default fileupload-exists" data-dismiss="fileupload" href="#">删除</a>
	                                            </div>
	                                        </div>
	                                    </div>
	                                </div>
	                                <div class="form-group">
	                                 	<label class="control-label col-md-2"></label>
	                                    <div class="col-md-4">
                                       		<input type="submit" class="btn btn-success" value="立刻上传"/>
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
    
    <script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
    <script type="text/javascript">
    	$(function(){
    		$('.fileupload').fileupload();
    	});
    </script>
</body>
</html>