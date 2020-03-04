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
                        <i class="icon-table"></i>编辑锁定用户
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-horizontal" id="form" method="post" action="edit">
                            <input type="hidden" name="id" value="${wl.id }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">用户姓名</label>

                                <div class="col-md-7">
                                    <input name="name" value="${wl.name }" type="hidden">

                                    <p class="form-control-static">
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${wl.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${wl.phone }
                                        </shiro:hasPermission>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">用户手机号码</label>

                                <div class="col-md-7">
                                    <input name="phone" value="${wl.phone }" type="hidden">

                                    <p class="form-control-static">
                                        <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">锁定金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="amount" value="${wl.amount }" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">锁定天数</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="lockDays" value="${wl.lockDays }" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">修改</button>
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

        $("#form").validate({
            rules: {
                amount: {
                    required: true
                },
                lockDays: {
                    required: true
                }
            },
            messages: {
                amount: {
                    required: "锁定金额不能为空"
                },
                lockDays: {
                    required: "锁定天数不能为空"
                }
            }
        });
    });
</script>
</body>
</html>