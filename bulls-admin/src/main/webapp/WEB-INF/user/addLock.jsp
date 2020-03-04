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
    <title>锁定用户</title>
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
                网站用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>锁定用户
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-horizontal" id="form">
                            <div class="form-group">
                                <label class="control-label col-md-2">用户姓名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" placeholder="请输入用户姓名" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">用户手机号码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="phone" id="phone" placeholder="请输入用户手机号码"
                                           type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">锁定金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="amount" value="50.0" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">锁定天数</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="lockDays" value="30" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="button" onclick="aa()">添加</button>
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
<script type="text/javascript">
    function aa() {
        // 使用 jQuery异步提交表单
        jQuery.ajax({
            url: '${basePath}user/activityLock/add',
            data: $('#form').serialize(),
            type: "POST",
            dataType: "json",
            success: function (data) {
                if (data.status == "0") {
                    alert("找不到该用户");
                }
                if (data.status == "1") {
                    alert("该用户未实名认证");
                }
                if (data.status == "2") {
                    alert("真实姓名不匹配");
                }
                if (data.status == "3") {
                    alert("该用户已经被锁定");
                }
                if (data.status == "4") {
                    alert("锁定成功!..");
                    window.location.href = "${basePath}user/activityLock/list";
                }
                if (data.status == "5") {
                    alert("系统异常!!!..请稍后再试...");
                }
            }
        });
    }
    $(function () {
        $('.select2able').select2();

        $("#form").validate({
            rules: {
                trueName: {
                    required: true,
                    minlength: 5,
                    maxlength: 20
                },
                phone: {
                    required: true,
                    minlength: 11,
                    maxlength: 11
                },
                amount: {
                    required: true
                }
            },
            messages: {
                trueName: {
                    required: "请输入用户名",
                    minlength: "用户姓名不能少于2个字",
                    maxlength: "用户姓名不能超过20个字"
                },
                phone: {
                    required: "请输入密码",
                    minlength: "手机号码必须为11位",
                    maxlength: "手机号码必须为11位"
                },
                amount: {
                    required: "锁定金额不能为空"
                }
            }
        });
    });
</script>
</body>
</html>