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
    <title>解绑银行卡</title>
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
                        <form action="activityLock" method="post" class="form-horizontal" id="form">
                            <input type="hidden" name="userId" value="${user.id }">
                            <input type="hidden" name="cardId" value="${bankCard.id }">

                            <div class="form-group">
                                <label class="control-label col-md-2">用户名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${user.username }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">真实姓名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${user.trueName == null }">
                                            此用户还没有进行实名化
                                        </c:if>
                                        <c:if test="${user.trueName != null }">
                                            ${user.trueName }
                                        </c:if>
                                    </p>

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">是否参与过该活动</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${hasActivitied == false }">
                                            <span class="label label-success">可参与</span>
                                        </c:if>
                                        <c:if test="${hasActivitied == true }">
                                            <span class="label label-danger">已参与</span>
                                        </c:if>
                                    </p>

                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">手机号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${user.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${user.phone }
                                        </shiro:hasPermission>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">再投金额:</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${investmentAmount == 0 }">
                                            此用户还没有投资
                                        </c:if>
                                        ${investmentAmount }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">账户余额:</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${availableBalance }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <c:if test="${availableBalance + investmentAmount >= 50 && !hasActivitied }">
                                        <button class="btn btn-primary" type="button" onclick="aa()">锁定</button>
                                    </c:if>
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
        if (confirm("您确定该用户参加锁定50元活动吗?!")) {
            $("#form").submit();
        }
    }
    ;
    $(function () {
        $('.select2able').select2();
    });
</script>
</body>
</html>