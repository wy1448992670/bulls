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
    <title>添加用户</title>
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
                后台用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>添加管理用户
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="add" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">用户名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="username" id="username" placeholder="请输入用户名"
                                           type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">真名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="trueName" id="trueName" placeholder="请输入真名"
                                           type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">密码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="password" id="password" placeholder="请输入密码"
                                           type="password">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">确认密码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="password2" placeholder="请再次输入密码" type="password">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">性别</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="sex">
                                        <option value="0">女</option>
                                        <option value="1">男</option>
                                        <option value="2">保密</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">对应部门</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="departmentId">
                                        <c:forEach var="item" items="${departments }">
											<option value="${item.id}">${item.name}</option>
										</c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label"></label>

                                <div class="text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">添加</button>
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

        $("#validate-form").validate({
            rules: {
                username: {
                    required: true,
                    maxlength: 20,
                    remote: {
                        url: "checkName",     //后台处理程序
                        type: "get",
                        dataType: "json",
                        data: {                     //要传递的数据
                            username: function () {
                                return $("#username").val();
                            }
                        }
                    }
                },
                trueName: {
                    required: true,
                    maxlength: 20
                },
                password: {
                    required: true,
                    checkPwd: true
                },
                password2: {
                    required: true,
                    checkPwdConfirm: true,
                    equalTo: "#password"
                }
            },
            messages: {
                username: {
                    required: "请输入用户名",
                    maxlength: "用户名不能超过20个字",
                    remote: "用户名已存在"
                },
                trueName: {
                    required: "请输入真名",
                    maxlength: "真名不能超过20个字"
                },
                password: {
                    required: "请输入密码",
                    minlength: "密码至少5个字",
                    maxlength: "密码不能超过20个字"
                },
                password2: {
                    required: "请再次输入密码",
                    minlength: "密码至少5个字",
                    maxlength: "密码不能超过20个字",
                    equalTo: "两次输入密码不一致"
                }
            }
        });
        
        
        $.validator.addMethod("checkPwd",function(value,element,params){  
            var checkPwd = /^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{8,20}$/;  
            return this.optional(element)||(checkPwd.test(value));  
        },"请输入正确的密码(密码必须为8~20位并且包含字母数字)！");  
        
        $.validator.addMethod("checkPwdConfirm",function(value,element,params){  
            var checkPwdConfirm = /^(?=.*[0-9].*)(?=.*[a-zA-Z].*).{8,20}$/;  
            return this.optional(element)||(checkPwdConfirm.test(value));  
        },"请输入正确的确认密码(密码必须为8~20位并且包含字母数字)！");  
    });
</script>
</body>
</html>