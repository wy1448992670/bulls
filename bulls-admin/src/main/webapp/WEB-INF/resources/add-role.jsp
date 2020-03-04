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
    <title>添加角色</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
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
                角色管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>添加角色
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="add" method="post" class="form-horizontal" id="validate-form">


                            <div class="form-group">
                                <label class="control-label col-md-2">角色名称</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="role" id="role" placeholder="请输入角色名称"
                                           type="text"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">角色描述</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="description" id="description"
                                           placeholder="请输入角色描述" type="text"/>
                                </div>
                            </div>
                            <!--
                             <div class="form-group">
                                <label class="control-label col-md-2">权限URL</label>
                                <div class="col-md-7">
                                    <input class="form-control" name="url" id="url" placeholder="请输入URL名称" type="text"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">权限状态</label>
                                <div class="col-md-7">
                                    <select class="select2able" name="permission">
                                        <option value="0">正常</option>
                                        <option value="1">禁用</option>
                                    </select>
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">创建</button>
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
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();
        $("#validate-form").validate({
            rules: {
                role: {
                    required: true,
                    maxlength: 255,
                    remote: {
                        url: "checkName",     //后台处理程序
                        type: "get",
                        dataType: "json",
                        data: {                     //要传递的数据
                            username: function () {
                                return $("#role").val();
                            }
                        }
                    }
                }
            },
            messages: {
                role: {
                    required: "请输入角色名称",
                    maxlength: "角色名称不能超过255个字",
                    remote: "角色名称已存在"
                }
            }
        });
    });
</script>
</body>
</html>