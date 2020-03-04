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
    <title>编辑活动</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                编辑活动
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑活动
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}activity/edit" method="post" class="form-horizontal" id="validate-form"
                              ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                    活动图片<br/>
                                    <em style="color:red;">(图片尺寸：336px*336px)</em>
                                </label>

                                <div class="col-md-4">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-new img-thumbnail" style="width: 100%;">
                                            <img alt=""
                                                 src="${aPath}upload/${activity.path}"
                                                 onload=javascript:DrawImage(this,850,350);>
                                            <input type="hidden" name="surface" value="${activity.surface}"/>
                                        </div>
                                        <div class="fileupload-preview fileupload-exists img-thumbnail"></div>
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
                                    <input class="form-control" name="name" id="name" placeholder="请输入活动主题" type="text"
                                           value="${activity.name }">
                                    <input name="id" type="hidden" value="${activity.id }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">活动开始时间</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="startTime" id="startTime" placeholder="请选择活动开始时间"
                                           type="text"
                                           value="<fmt:formatDate value="${activity.start_time }" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">活动截止时间</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="endTime" id="endTime" placeholder="请选择活动截止时间"
                                           type="text"
                                           value="<fmt:formatDate value="${activity.end_time }" pattern="yyyy-MM-dd"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">排除渠道</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="channel" id="channel" value="${activity.channel }"  placeholder="请输入要排除的渠道，英文逗号分隔" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">链接地址</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="link" id="link" placeholder="请输入链接地址" type="text"
                                           value="${activity.link }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">活动描述</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="descript" id="descript" placeholder="请输入活动描述"
                                           type="text" value="${activity.descript }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="status">
                                        <option value="0" <c:if test="${activity.status == 0 }">selected</c:if>>未启用
                                        </option>
                                        <option value="1" <c:if test="${activity.status == 1 }">selected</c:if>>启用
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">确认修改</button>
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

    });

    function DrawImage(ImgD, FitWidth, FitHeight) {
        var image = new Image();
        image.src = ImgD.src;
        if (image.width > 0 && image.height > 0) {
            if (image.width / image.height >= FitWidth / FitHeight) {
                if (image.width > FitWidth) {
                    ImgD.width = FitWidth;
                    ImgD.height = (image.height * FitWidth) / image.width;
                } else {
                    ImgD.width = image.width;
                    ImgD.height = image.height;
                }
            } else {
                if (image.height > FitHeight) {
                    ImgD.height = FitHeight;
                    ImgD.width = (image.width * FitHeight) / image.height;
                } else {
                    ImgD.width = image.width;
                    ImgD.height = image.height;
                }
            }
        }
    }
</script>
</body>
</html>
