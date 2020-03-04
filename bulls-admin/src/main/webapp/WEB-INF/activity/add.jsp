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
    <title>活动发布</title>
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
                活动发布
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>活动发布
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}activity/addActivity" method="post" class="form-horizontal"
                              id="validate-form" ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                    活动图片<br/>
                                    <em style="color:red;">(图片尺寸：750px*412px)</em>
                                </label>

                                <div class="col-md-4">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-new img-thumbnail" style="width: 200px; height: 150px;">
                                            <img alt="" src="${aPath}images/no-image.gif">
                                        </div>
                                        <div class="fileupload-preview fileupload-exists img-thumbnail"
                                             style="width: 1080px; max-height: 620px"></div>
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
                                <label class="control-label col-md-2">活动主题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" placeholder="请输入活动主题" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">活动开始时间</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="startTime" id="startTime"
                                           placeholder="请选择活动截止时间" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">活动截止时间</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="endTime" id="endTime"
                                           placeholder="请选择活动截止时间" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">链接地址</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="link" id="link" placeholder="请输入链接地址" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">活动描述</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="descript" id="descript" placeholder="请输入活动描述"
                                           type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="status">
                                        <option value="0">未启用</option>
                                        <option value="1">启用</option>
                                    </select>
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
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2able').select2();
        $("#validate-form").validate({
            rules: {
                name: {
                    required: true,
                    maxlength: 255
                }
            },
            messages: {
                name: {
                    required: "请输入Banner主题",
                    maxlength: "Banner主题不能超过255个字"
                }
            }

        });
    });
</script>
</body>
</html>
