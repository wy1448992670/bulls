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
    <style type="text/css">
        strong.money {
            color: #007aff;
            font-size: 18px;
        }
        
        .upload-picture a {
            display: inline-block;
            overflow: hidden;
            border: 0;
            vertical-align: top;
            margin: 0 5px 10px 0;
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
                线下补单申请审核
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}recharge/rechargeOfflineApply/audit" method="post" class="form-horizontal" id="validate-form">

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
                                    <%--<p class="form-control-static">--%>
                                        <%--${data.voucher_pic}--%>
                                    <%--</p>--%>

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
                                    <select class="select2able" name="state" id="state">
                                        <option value="1">通过</option>
                                        <option value="-1">不通过</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">审核内容</label>
                                
                                <div class="col-md-7">
                                    <textarea rows="4" class="form-control" placeholder="请输入审核内容" name="auditRemark" id="auditRemark"></textarea>
                                    <input name="id" id="id" type="hidden" value="${data.id}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                                    <button class="btn btn-primary" type="submit" onclick="return confirm('确定提交审核吗？');">审核</button>
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
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();

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

</script>
</body>
</html>
