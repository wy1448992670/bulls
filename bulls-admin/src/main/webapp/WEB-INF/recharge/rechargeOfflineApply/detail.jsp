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
    <link href="${basePath}css/jquery-fileupload/jquery.fileupload.css" media="all" rel="stylesheet" type="text/css"/>
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
                        <form  method="post" class="form-horizontal" id="validate-form" ENCTYPE="multipart/form-data">
                            <input name="id" id="id" type="hidden" value="${data.id}">
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
                                    <p class="form-control-static">
                                        ${data.bankcard_num}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">银行流水号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.serial_number}
                                    </p>
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
                                        <c:if test="${data.path == null}">
                                            <img src="${aPath}images/no-image.gif" />
                                        </c:if>
                                        <c:if test="${data.path != null}">
                                            <a class="gallery-item fancybox" rel="g1" href="${aPath}upload/${data.path}" title="线下转账凭证">
                                                <img src="${aPath}upload/${data.path}" />

                                                <div class="actions">
                                                    <i class="icon-zoom-in"></i>
                                                </div>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">申请单最后编辑人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.last_update_user_name}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">申请单最后编辑时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.last_update_time}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${data.state == 0 }">
                                            <span class="label label-warning">申请中</span>
                                        </c:if>
                                        <c:if test="${data.state == 1 }">
                                            <span class="label label-success">审核通过</span>
                                        </c:if>
                                        <c:if test="${data.state == -1 }">
                                            <span class="label label-danger">审核不通过</span>
                                        </c:if>
                                    </p>
                                </div>
                            </div>
                        
                            <div class="form-group">
                                <label class="control-label col-md-2">审核人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                            ${data.auditor_name}
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">审核时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                            ${data.audit_time}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">审核备注</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${data.audit_remark}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                                    <c:if test="${type == 1}">
                                        <%--<button class="btn btn-primary" type="submit" onclick="edit()">修改</button>--%>
                                    </c:if>
                                    <c:if test="${type == 2}">
                                        <%--<button class="btn btn-primary" type="submit" onclick="audit()">审核</button>--%>
                                    </c:if>
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
        // $('.select2able').select2();

        $("#validate-form").validate({
            rules: {
                state: {
                    required: true
                }
            },
            messages: {
                state: {
                    required: "请选择审核状态"
                }
            }

        });
    });
    
    function setName(){
        $('[type=file]').attr('name','file');
    }

    function edit(){
        if (confirm('确定提交修改吗？')) {
            $('#validate-form').attr('action','${basePath}recharge/rechargeOfflineApply/edit');
            $('#validate-form').submit();
        }
    }

    function audit(){
        $('#validate-form').attr('action','${basePath}recharge/rechargeOfflineApply/audit');
        if (confirm('确定提交审核吗？')) {
            $('#validate-form').submit();
        }
    }

</script>
</body>
</html>
