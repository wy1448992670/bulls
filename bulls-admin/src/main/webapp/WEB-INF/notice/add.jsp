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
    <link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>发布公告</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                公告管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>发布公告
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}notice/add" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">标题:</label>

                                <div class="col-md-5">
                                    <input class="form-control" name="title" id="title" placeholder="主题" type="text">
                                </div>
                                <div class="col-md-2"> 
                                	<label class="control-label col-md-3">颜色:</label>
                                	<div class="col-md-9">
                                 		<input class="form-control" name="titleColor" id="titleColor" placeholder="例:#000000" type="text">
                                 	</div>
								</div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">首页滚动显示:</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="isShow" id="isShow">
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">公告内容:</label>

                                <div class="col-md-7">
                                    <!-- 加载编辑器的容器 -->
                                    <script id="container" name="content" type="text/plain" height="500"></script>
                                    <textarea rows="" cols="" hidden="true" name="htmlContent"
                                              id="htmlContent"></textarea>
                                    <textarea rows="" cols="" hidden="true" name="textContent"
                                              id="textContent"></textarea>
                                </div>
                            </div>
                            <input type="hidden" name="status" id="status" value=""/>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-warning" type="submit" id="cao">保存为草稿</button>
                                    <button class="btn btn-success" type="submit" id="pub">直接发布</button>
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

<SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.config.js"></SCRIPT>  
<SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.all.js"></SCRIPT>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#cao').click(function () {
            var isShow = $("#isShow").val();
            if (isShow == 1) {
                alert("不允许将首页滚动显示的公告，保存到草稿状态");
                return false;
            }
            $('#status').val(0);
            $('#htmlContent').text(ue.getContent());
            $('#textContent').text(ue.getContentTxt());
        });
        $('#pub').click(function () {
            $('#status').val(1);
            $('#htmlContent').text(ue.getContent());
            $('#textContent').text(ue.getContentTxt());
        });
        var ue = UE.getEditor('container', {
            initialFrameHeight: 500
        });

        $('.select2able').select2();

        ue.addListener("keyup", function () {
            $('#htmlContent').text(ue.getContent());
            $('#textContent').text(ue.getContentTxt());
        });

        $("#validate-form").validate({
            ignore: "",
            rules: {
                title: {
                    required: true,
                    maxlength: 32
                },
                textContent: {
                    required: true
                },
                titleColor: {
                    required: true,
                }
            },
            messages: {
                title: {
                    required: "请输入公告标题",
                    maxlength: "标题不能超过32个字符"
                },
                textContent: {
                    required: "请输入公告内容"
                },
                titleColor: {
                    required: "请输入标题颜色"
                }
            }
        });
    });
</script>
</body>
</html>