<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>线下补单申请</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css" />
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
    <link href="${basePath}css/fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .upload-picture a {
            display: inline-block;
            overflow: hidden;
            border: 0;
            vertical-align: top;
            margin: 0 5px 10px 0;
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
    <jsp:include page="../../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                线下补单申请详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}recharge/rechargeOfflineApply/edit" method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
                            <input type="hidden" value="${data.id}" name="id" id="id">
                            <div class="form-group">
                                <label class="control-label col-md-2">申请人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.applyer_name}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">发起人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.sourcer}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">充值金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.money}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">充值人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.user_name}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">银行卡号</label>

                                <div class="col-md-7">

                                    <input class="form-control" value="${data.bankcard_num}" name="bankcardNum" id="bankcardNum" placeholder="请输入银行卡号" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">银行流水号</label>

                                <div class="col-md-7">
                                    <input class="form-control" value="${data.serial_number}" name="serialNumber" id="serialNumber" placeholder="请输入银行流水号" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">申请时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.create_time}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">线下转账凭证</label>

                                <div class="col-md-7">

                                    <div class="form-control-static upload-picture">
                                        <div class="fileupload fileupload-new" data-provides="fileupload">
                                            <div class="fileupload-new img-thumbnail" style="width: 200px; height: 150px;">
                                                <c:if test="${data.path != null}"><img alt="" src="${aPath}upload/${data.path}"/></c:if>
                                                <c:if test="${data.path == null}"><img alt="" src="${aPath}images/no-image.gif"></c:if>
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
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">申请单上一次编辑人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.last_update_user_name}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">申请单上一次编辑时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.last_update_time}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                                    <button class="btn btn-primary" type="submit" onclick="return confirm('确定提交修改吗？');">修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-fileupload.js" type="text/javascript"></script>
<script type="text/javascript">

    $(function () {
    });


</script>
</body>
</html>
