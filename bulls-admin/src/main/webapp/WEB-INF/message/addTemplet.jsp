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
    <title>新增短信模板</title>
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
                短信管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>新增短信模板
                    </div>
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">短信内容</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="content" id="content" placeholder="请输入需要发送的短信内容"/>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">注意！字数需低于75个字！</span></div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">选择平台</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="type" id="type">
                                        <option value="" <c:if test="${type == null }">selected</c:if>>选择类型</option>
                                        <option value="0" <c:if test="${type == 0 }">selected</c:if>>云片短信平台</option>
                                        <option value="1" <c:if test="${type == 1 }">selected</c:if>>畅卓短信平台</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">是否启用</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="status">
                                        <option value="0" <c:if test="${type == null }">selected</c:if>>失效</option>
                                        <option value="1" <c:if test="${type == 1 }">selected</c:if>>启用</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" data-loading-text="提交中..." type="button" id="send">
                                        提交
                                    </button>
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
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();
        $("#send").click(function () {
            var content = $.trim($("#content").val());
            var type = $.trim($("#type").val());
            if (content == '') {
                alert('请输入内容');
                return false;
            }
            if (type == '') {
                alert('请选择短信平台!');
                return false;
            }

            $.ajax({
                url: '${basePath}sms/addTemplet',
                data: $('#validate-form').serialize(),
                type: "POST",
                dataType: "json",
                beforeSend: function () {
                    $("#send").button('loading');
                },
                success: function (data) {
                    alert(data);
                    window.location.href = "${basePath}sms/detailsSMS";
                },
                complete: function () {
                    $("#send").button('reset');
                }
            });

        });

    });
</script>
</body>
</html>