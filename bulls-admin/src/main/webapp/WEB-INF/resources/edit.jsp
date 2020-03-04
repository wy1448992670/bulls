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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>权限管理</title>
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
                        <i class="icon-table"></i>权限信息修改
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="edit" method="post" class="form-horizontal" id="validate-form">
                            <input type="hidden" name="id" value="${resources.id }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">权限名称</label>

                                <div class="col-md-4">
                                    <input class="form-control" value="${resources.name }" name="name" id="name"
                                           placeholder="请输入操作名称" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">权限URL</label>

                                <div class="col-md-4">
                                    <input class="form-control" value="${resources.url }" name="url" id="url"
                                           placeholder="请输入链接地址" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">权限字符串</label>

                                <div class="col-md-4">
                                    <input class="form-control" value="${resources.permission }" name="permission"
                                           id="permission" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">是否是菜单权限</label>

                                <div class="col-md-4">
                                    <select class="form-control col-md-2" name="ismenu">
                                        <option value="true" <c:if test="${resources.ismenu }">selected</c:if>>是
                                        </option>
                                        <option value="false" <c:if test="${!resources.ismenu }">selected</c:if>>否
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">权限父ID</label>

                                <div class="col-md-4">
                                    <select class="form-control col-md-2" name="parentId">
                                        <option value='0'>父权限</option>
                                        <c:forEach var="list" items="${fatherList }">
                                        <option value='${list.id}' ${list.id==resources.parentId?'selected':''}>${list.name}
                                            </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-4 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
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
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "150"});
    });
</script>
</body>
</html>