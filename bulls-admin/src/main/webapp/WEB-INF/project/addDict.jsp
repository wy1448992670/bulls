<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                新增字典
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>新增字典
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}dict/addDict" method="post" class="form-horizontal"
                              id="validate-form" ENCTYPE="multipart/form-data">

                            <input type="hidden" name="id" value="${tmDict.id}">
                            <div class="form-group">
                                <label class="control-label col-md-2">名称</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="tName" id="tName" placeholder="请输入名称" type="text" value="${tmDict.tName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">key</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="tKey" id="tKey" placeholder="请输入key" type="text"  value="${tmDict.tKey}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">value</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="tValue" id="tValue" placeholder="请输入value" type="text"  value="${tmDict.tValue}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">排序</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="tSort" id="tSort" placeholder="请输入排序"  type="number"  value="${tmDict.tSort}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">tp</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="tp" id="tp" placeholder="请输入tp" type="text" value="${tmDict.tp}">
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">添加</button>
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

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
</body>
</html>