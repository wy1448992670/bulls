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
    <title>分享明细</title>
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
                分享明细
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>分享明细
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}share/add" method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                    分享图片<br/>
                                    <em style="color:red;">(图片尺寸：200px*200px)</em>
                                </label>
                                <div class="col-md-4">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-new img-thumbnail" style="width: 100%;">
                                            <img alt=""
                                                 src="${aPath}upload/${path}"
                                                 onload=javascript:DrawImage(this,850,350);>
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
                                <label class="control-label col-md-2">分享主题</label>
                                <input type="hidden" name="id" value="${share.id}"/>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入分享主题"
                                           type="text" value="${share.title }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">分享内容</label>

                                <div class="col-md-7">
                                    <textarea class="form-control" name="context" id="name" placeholder="请输入分享主题"
                                              type="text">${share.context }</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">链接地址</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="link" id="link" placeholder="请输入链接地址" type="text" value="${share.link }">
                                </div>
                            </div>

                            <%--<div class="form-group">--%>
                            <%--<label class="control-label col-md-2">类型</label>--%>

                            <%--<div class="col-md-7">--%>
                            <%--<select class="select2able" name="type">--%>
                            <%--<option value="1" <c:if test="${share.type==1}">selected </c:if> >更多</option>--%>
                            <%--<option value="3" <c:if test="${share.type==3}">selected </c:if> >最新活动</option>--%>
                            <%--<option value="4" <c:if test="${share.type==4}">selected </c:if>>佣金</option>--%>
                            <%--</select>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="status">
                                        <option value="1"
                                                <c:if test="${share.status==1}">selected </c:if> >未启用
                                        </option>
                                        <option value="0" <c:if test="${share.status==0}">selected </c:if>>启用</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">保存</button>
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
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();
        $("#validate-form").validate({
            rules: {
                title: {
                    required: true
                },
                context: {
                    required: true
                },
                link: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入分享标题"
                },
                context: {
                    required: "请输入分享内容"
                },
                link: {
                    required: "请输入分享地址"
                }
            }
        });
    });
</script>
</body>
</html>
