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
    <title>用户反馈查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table {
            table-layout: fixed;
        }

        .table .over {
            overflow: hidden;
            width: 40%;
            text-overflow: ellipsis;
            white-space: nowrap;
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
                用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户反馈内容编辑
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="" class="form-horizontal" id="validate-form">
                            <c:if test="${path != null}">
                                <div class="form-group">
                                    <label class="control-label col-md-2"></label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            <img alt="" src="${aPath}upload/${path }" width="300px;"/>
                                        </p>
                                    </div>
                                </div>
                            </c:if>


                            <div class="form-group">
                                <label class="control-label col-md-2">昵称</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${info.username }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">真名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${info.trueName }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">手机号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${info.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${info.phone }
                                        </shiro:hasPermission>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">反馈内容</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${info.content }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">反馈时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${info.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">回复时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${info.reply_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">回复人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${i.adminTrueName != null}">
                                            ${info.adminTrueName }
                                        </c:if>
                                        <c:if test="${i.adminTrueName == null}">
                                            ${info.adminUserName }
                                        </c:if>

                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">回复内容</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${info.replyContent }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">返回</a>
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
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
</script>
</body>
</html>