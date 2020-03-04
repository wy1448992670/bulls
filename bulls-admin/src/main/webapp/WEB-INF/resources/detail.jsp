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
    <title>权限详细信息</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                权限管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>权限详细信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="edit" method="post" class="form-horizontal" id="validate-form">
                            <input type="hidden" name="id" value="${resources.id }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">权限名称</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${resources.name }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">权限URL</label>

                                <div class="col-md-7">
                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            ${resources.url }
                                        </p>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">权限URL</label>

                                <div class="col-md-7">
                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            ${resources.url }
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">权限URL</label>

                                <div class="col-md-7">
                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            ${resources.url }
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
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

</body>
</html>