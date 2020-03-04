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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.config.js"></SCRIPT>  
	<SCRIPT type=text/javascript src="${basePath}ueditor/ueditor.all.js"></SCRIPT>
    <title>发布新闻</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                新闻管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>发布新闻
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="add" method="post" class="form-horizontal" id="validate-form"
                              ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入新闻标题"
                                           type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">类型</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="type" id="type">
                                        <option value="1">媒体报道</option>
                                        <option value="2">公司动态</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">新闻来源</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="source" id="source" placeholder="请输入新闻来源"
                                           type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">发布时间</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="releaseTime"
                                           type="text" placeholder="请选择起始时间"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">媒体图片<br>
                                	<em style="color:red;">(图片尺寸：240px*160px)</em>
                                </label>

                                <div class="col-md-4">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-new img-thumbnail" style="width: 150px; height: 100px;">
                                            <img alt="" src="${aPath}images/no-image.gif">
                                        </div>
                                        <div class="fileupload-preview fileupload-exists img-thumbnail"
                                             style="width: 150px; max-height: 100px"></div>
                                        <div>
	                                                <span class="btn btn-default btn-file">
	                                                    <span class="fileupload-new">选择图片</span>
	                                                    <span class="fileupload-exists">修改</span>
	                                                    <input type="file" name="file">
	                                                </span><a class="btn btn-default fileupload-exists"
                                                              data-dismiss="fileupload" href="#">删除</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">是否置顶</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="isTop" id="isShow">
                                        <option value="0">否</option>
                                        <option value="1">是</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">内容</label>

                                <div class="col-md-7">
                                    <!-- 加载编辑器的容器 -->
                                    <script id="container" name="content" type="text/plain" height="500"></script>
                                    <textarea rows="" cols="" hidden="true" name="htmlContent"
                                              id="htmlContent"></textarea>
                                    <textarea rows="" cols="" hidden="true" name="textContent"
                                              id="textContent"></textarea>

                                </div>
                            </div>
                            <input type="hidden" name="status" id="status" value="0"/>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <div class="text-center col-md-7">
                                        <a class="btn btn-default-outline"
                                           onclick="javascript:window.history.go(-1);">取消</a>
                                        <button class="btn btn-warning" type="submit" id="cao">保存为草稿</button>
                                        <button class="btn btn-success" type="submit" id="pub">直接发布</button>
                                    </div>
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
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.ui.widget.js" type="text/javascript"></script>
<script src="${basePath}js/jquery-fileupload/jquery.fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
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
            rules: {
                username: {
                    required: true,
                    minlength: 4,
                    maxlength: 20
                },
                password: {
                    required: true,
                    minlength: 5,
                    maxlength: 20
                }
            },
            messages: {
                username: {
                    required: "请输入用户名",
                    minlength: "用户名至少4个字",
                    maxlength: "用户名不能超过20个字",
                },
                password: {
                    required: "请输入密码",
                    minlength: "密码至少5个字",
                    maxlength: "密码不能超过20个字"
                }
            }
        });
    });
</script>
</body>
</html>