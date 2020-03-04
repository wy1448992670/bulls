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
    <title>推广渠道明细</title>
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
                推广渠道明细
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>推广渠道明细
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}advertisementChannel/save" method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">渠道号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="channelNo" id="channelNo" placeholder="请输入渠道号" value="${advertisementChannel.channelNo}" type="text" readonly />
                                    <input type="hidden" name="id" value="${advertisementChannel.id}" />
                                    <input type="hidden" name="version" value="${advertisementChannel.version}" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">渠道名称</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="channelName" id="channelName" placeholder="请输入渠道名称" value="${advertisementChannel.channelName}" type="text" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">渠道类型</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="channelType" id="channelType">
                                        <option value="1" <c:if test="${advertisementChannel.channelType==1}">selected </c:if>>cps</option>
                                        <option value="2" <c:if test="${advertisementChannel.channelType==2}">selected </c:if>>cpc</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">统计类型</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="tongjiType" id="tongjiType">
                                        <option value="1" <c:if test="${advertisementChannel.tongjiType==1}">selected </c:if>>百度统计 </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">统计Key</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="tongjiKey" id="tongjiKey" placeholder="请输入统计Key"  value="${advertisementChannel.tongjiKey}" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                    顶部图片<br/>
                                    <em style="color:red;">(图片尺寸：750px*412px)</em>
                                </label>

                                <div class="col-md-4">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-new img-thumbnail" style="width: 200px; height: 150px;">
                                            <c:if test="${advertisementChannel.topImageId != null}"><img src="${aPath}upload/${upload.path}"/></c:if>
                                            <c:if test="${advertisementChannel.topImageId == null}"><img alt="" src="${aPath}images/no-image.gif"></c:if>
                                        </div>
                                        <div class="fileupload-preview fileupload-exists img-thumbnail"
                                             style="width: 640px; max-height: 260px"></div>
                                        <div>
                                            <span class="btn btn-default btn-file">
                                                <span class="fileupload-new">选择图片</span>
                                                <span class="fileupload-exists">修改</span>
                                                <input type="file" name="file" id="file">
                                            </span>
                                            <a class="btn btn-default fileupload-exists" data-dismiss="fileupload" href="#">删除</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">按钮文本</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="buttonText" id="buttonText" placeholder="不超过8个字" value="${advertisementChannel.buttonText}" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">注册成功文本</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="successText" id="successText" placeholder="请输入注册成功文本" value="${advertisementChannel.successText}" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">跳转url</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="redirectUrl" id="redirectUrl" placeholder="请输入跳转url" value="${advertisementChannel.redirectUrl}" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">规则文本</label>

                                <div class="col-md-7">
                                    <%--<input class="form-control" name="guizeText" id="guizeText" placeholder="请输入规则文本" type="text">--%>
                                    <%--<textarea class="form-control"  name="guizeText" id="guizeText"  placeholder="请输入文字限制120字" >${advertisementChannel.guizeText}</textarea>--%>
                                    <!-- 加载编辑器的容器 -->
                                    <script id="container" name="content" type="text/plain" height="500"></script>
                                    <textarea rows="" cols="" hidden="true" name="guizeText" id="guizeText">${advertisementChannel.guizeText}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <c:if test="${type == null || type != 0}">
                                        <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                                        <button class="btn btn-primary" type="submit">保存</button>
                                    </c:if>
                                    <c:if test="${type != null && type == 0}">
                                        <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">返回</a>
                                    </c:if>
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


<script type=text/javascript src="${basePath}ueditor/ueditor.config.js"></script>
<script type=text/javascript src="${basePath}ueditor/ueditor.all.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();

        var ue = UE.getEditor('container', {
            initialFrameHeight: 500
        });
        ue.ready(function () {
            ue.execCommand('insertHtml', $('#guizeText').text());
        });
        ue.addListener("keyup", function () {
            $('#guizeText').text(ue.getContent());
        });

        $("#validate-form").validate({
            rules: {
                channelNo: {
                    required: true
                },
                channelName: {
                    required: true
                },
                // channelType: {
                //     required: true
                // },
                // tonngjiType: {
                //     required: true
                // },
                // tongjiKey: {
                //     required: true
                // },
                buttonText: {
                    maxlength: 8
                }
            },
            messages: {
                channelNo: {
                    required: "请输入渠道编号",
                },
                channelName: {
                    required: "请输入渠道名称"
                },
                // channelType: {
                //     required: "请选择渠道类型"
                // },
                // tonngjiType: {
                //     required: "请选择统计类型"
                // },
                // tongjiKey: {
                //     required: "请输入统计Key"
                // },
                buttonText: {
                    maxlength: "不超过8个字"
                }
            }
        });
    });

</script>
</body>
</html>
