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
    <title>创建活期项目</title>
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
                Banner明细
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>Banner明细
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}banner/add" method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">
                                    Banner<br/>
                                    <em style="color:red;">(图片尺寸：336px*336px)</em>
                                </label>

                                <div class="col-md-4">
                                    <div class="fileupload fileupload-new" data-provides="fileupload">
                                        <div class="fileupload-new img-thumbnail" style="width: 200px; height: 150px;">
                                            <c:if test="${banner.id!=null}"><img src="${aPath}upload/${banner.pictureUrl}"/></c:if>
                                            <c:if test="${banner.id==null}"><img alt="" src="${aPath}images/no-image.gif"></c:if>
                                        </div>
                                        <div class="fileupload-preview fileupload-exists img-thumbnail"
                                             style="width: 640px; max-height: 260px"></div>
                                        <div>
	                                        <span class="btn btn-default btn-file">
                                                <span class="fileupload-new">选择图片</span>
                                                <span class="fileupload-exists">修改</span>
                                                <input type="file" name="file">
                                            </span>
                                            <a class="btn btn-default fileupload-exists" data-dismiss="fileupload" href="#">删除</a>
                                        </div>
                                    </div>
                                </div>
                                <input type="hidden" name="types" value="${type}"/>
                                <input type="hidden" name="statuss" value="${status}"/>
                                <input type="hidden" name="sources" value="${source}"/>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">Banner主题</label>
                                <input type="hidden" name="id" value="${banner.id}"/>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" placeholder="请输入Banner主题"
                                           type="text" value="${banner.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">链接地址</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="link" id="link" placeholder="请输入链接地址" type="text" value="${banner.link }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">排序规则</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="seniority" id="seniority" placeholder="请输入数字如：1 2 3 等" type="text" value=" ${banner.seniority }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">终端类型</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="type">
                                        <option value="0" <c:if test="${banner.type==0}">selected </c:if>>APP</option>
                                        <option value="1" <c:if test="${banner.type==1}">selected </c:if>>PC</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">banner归属页</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="source">
                                        <option value="0" <c:if test="${banner.source==0}">selected </c:if>>首页</option>
                                        <option value="1" <c:if test="${banner.source==1}">selected </c:if>>首页-活动区</option>
                                        <option value="2" <c:if test="${banner.source==2}">selected </c:if>>商城-首页</option>
                                        <option value="3" <c:if test="${banner.source==3}">selected </c:if>>首页-开机广告</option>
                                        <option value="4" <c:if test="${banner.source==4}">selected </c:if>>首页-下区</option>
                                    </select>
                                    <em style="color:red;">* 首页-移动广告 如需跳转原生界面链接地址：无需配置，Banner主题：配置原生KEY</em>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="status">
                                        <option value="1"
                                                <c:if test="${banner.status==1}">selected </c:if> >未启用
                                        </option>
                                        <option value="0" <c:if test="${banner.status==0}">selected </c:if>>启用</option>
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
                annualized: {
                    required: true
                },
                totalAmount: {
                    required: true
                }
            },
            messages: {
                title: {
                    required: "请输入项目名称"
                },
                annualized: {
                    required: "请输入年化利率"
                },
                totalAmount: {
                    required: "请输入总融资金额"
                }
            }
        });
    });
</script>
</body>
</html>
