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
    <title>版本管理新增</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                版本管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>版本管理
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}project/versionAddSave" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">版本标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入要更新的版本标题" type="text" value="${map.title }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">版本号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="version" id="version" placeholder="请输入要更新的版本号（如：3.0.0）" type="text" value="${map.version }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">终端类型</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="client" id="client" placeholder="请输入要更新的终端" type="text" value="${map.client }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">链接地址</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="url" id="url" placeholder="请输入链接地址" type="text" value="${map.url }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">公告内容</label>

                                <div class="col-md-7">
                                    <!-- 加载编辑器的容器 -->
                                    <script id="container" name="content" type="text/plain" height="500"></script>
                                    <textarea rows="" cols="" hidden="true" name="htmlContent" id="htmlContent">${map.htmlContent }</textarea>
                                    <textarea rows="" cols="" hidden="true" name="textContent" id="textContent">${map.textContent }</textarea>
                                </div>
                            </div>
                            <input type="hidden" name="id" id="id" value="${map.id }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
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

<script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#cao').click(function () {
            $('#status').val(0);
        });
        $('#pub').click(function () {
            $('#status').val(1);
        });
        $('.select2').select2();

        var ue = UE.getEditor('container', {
            initialFrameHeight: 500
        });
        ue.ready(function () {
            ue.execCommand('insertHtml', $('#htmlContent').text());
        });
        ue.addListener("keyup", function () {
            $('#htmlContent').text(ue.getContent());
            $('#textContent').text(ue.getPlainTxt());
        });

        // $("#project-list a:eq(0)").addClass("current");
        $("#project-list a:eq(7)").addClass("current");
        $("#validate-form").validate({
            ignore: "",
            rules: {
                title: {
                    required: true,
                    maxlength: 64
                },
                textContent: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入内容标题",
                    maxlength: "标题不能超过64个字符"
                },
                textContent: {
                    required: "请输入详细内容"
                }
            }
        });
    });
</script>
</body>
</html>
