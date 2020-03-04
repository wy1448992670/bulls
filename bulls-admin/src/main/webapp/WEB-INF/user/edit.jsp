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
    <title>编辑用户</title>
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
                        <i class="icon-table"></i>编辑管理用户
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="edit" method="post" class="form-horizontal" id="validate-form">
                            <input type="hidden" name="id" value="${user.id }"/>
                            <div class="form-group">
                                <label class="control-label col-md-2">用户名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="username" id="username" disabled type="text"
                                           value="${user.username }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">真名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="trueName" id="trueName" disabled type="text"
                                           value="${user.trueName }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">邮箱</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="email" id="email" type="text"
                                           value="${user.email }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">性别</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="sex">
                                        <option value="0" <c:if test="${user.sex == 0 }">selected</c:if>>女</option>
                                        <option value="1" <c:if test="${user.sex == 1 }">selected</c:if>>男</option>
                                        <option value="2" <c:if test="${user.sex == 2 }">selected</c:if>>保密</option>
                                    </select>
                                </div>
                            </div>
                            <c:if test="${roleName=='customService'}">
                                <input type="hidden" name="status" value="${user.status }"/>
                                <input type="hidden" name="roleId" value="${roleId }"/>
                            </c:if>
                            <c:if test="${roleName!='customService'}">
                                <div class="form-group">
                                    <label class="control-label col-md-2">角色</label>

                                    <div class="col-md-7">
                                        <select class="form-control" name="roleId">
                                            <c:forEach var="list" items="${list }">
                                            <option value='${list.id}' ${list.id == roleId ? 'selected="selected"' : '' } >${list.description}
                                                </c:forEach>
                                        </select>
                                    </div>
                                </div>
                             <div class="form-group">
                                <label class="control-label col-md-2">对应部门</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="departmentId">
                                        <c:forEach var="item" items="${departments }">
											<option value="${item.id}" <c:if test="${user.departmentId == item.id }">selected</c:if>>${item.name}</option>
										</c:forEach>
                                    </select>
                                </div>
                            </div>

                                <div class="form-group">
                                    <label class="control-label col-md-2">状态</label>

                                    <div class="col-md-7">
                                        <label class="radio-inline">
                                            <input name="status" type="radio" value="0"
                                                   <c:if test="${user.status == 0 }">checked</c:if>>
                                            <span>可用</span>
                                        </label>
                                        <label class="radio-inline">
                                            <input name="status" type="radio" value="1"
                                                   <c:if test="${user.status == 1 }">checked</c:if>>
                                            <span>不可用</span>
                                        </label>
                                        <label class="radio-inline">
                                            <input name="status" type="radio" value="2"
                                                   <c:if test="${user.status == 2 }">checked</c:if>>
                                            <span>已删除</span>
                                        </label>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${(roleId==18) && (roleName=='customService'|| roleName=='Admin')}">
                                <div class="form-group">
                                    <label class="control-label col-md-2">月度计划</label>

                                    <div class="col-md-7">
                                        <input class="form-control" name="monthPlan" id="monthPlan" type="text"
                                               value="${user.monthPlan }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">日度计划</label>

                                    <div class="col-md-7">
                                        <input class="form-control" name="dayPlan" id="dayPlan" type="text"
                                               value="${user.dayPlan }">
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">编辑</button>
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
                    minlength: 4,
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
                email: {
                    required: false,
                    minlength: 6,
                    maxlength: 64,
                    email: true
                },
                password: {
                    required: true,
                    minlength: 5,
                    maxlength: 20
                },
                password2: {
                    required: true,
                    minlength: 5,
                    maxlength: 20,
                    equalTo: "#password"
                }
            },
            messages: {
                username: {
                    required: "请输入用户名",
                    minlength: "用户名至少4个字",
                    maxlength: "用户名不能超过20个字",
                    remote: "用户名已存在"
                },
                trueName: {
                    required: "请输入真名",
                    maxlength: "真名不能超过20个字"
                },
                email: {
                    minlength: "邮箱至少6个字符",
                    maxlength: "邮箱最多64个字符",
                    email: "不是正确的邮箱"
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
    });
</script>
</body>
</html>