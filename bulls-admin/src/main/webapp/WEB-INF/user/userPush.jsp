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
    <title>推送管理</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                        <i class="icon-table"></i>推送管理<a class="btn btn-sm btn-primary-outline pull-right"
                                                         href="editUserPush" id="add-row"><i class="icon-plus"></i>添加推送模板</a>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="userPush">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <select class="select2" name="type" id="type">
                                        <option value="" <c:if test="">selected</c:if>>全部类型</option>
                                        <option value="0" <c:if test="${template.type == 0 }">selected</c:if>>单播
                                        </option>
                                        <option value="1" <c:if test="${template.type == 1 }">selected</c:if>>广播
                                        </option>
                                        <option value="2" <c:if test="${template.type == 2 }">selected</c:if>>列播
                                        </option>
                                        <option value="3" <c:if test="${template.type == 3 }">selected</c:if>>其他
                                        </option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <select class="select2" name="status">
                                        <option value="" <c:if test="">selected</c:if>>全部状态</option>
                                        <option value="0" <c:if test="${template.status == 0 }">selected</c:if>>未推送
                                        </option>
                                        <option value="1" <c:if test="${template.status == 1 }">selected</c:if>>已推送
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text"
                                               placeholder="请输入用户名搜索" value="${keyword }"/>
                                    </div>
                                </div>
                                <button class="btn btn-primary pull-right hidden-xs" type="submit"> 搜索</button>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>

                                <th>序号</th>
                                <th>模板名称</th>
                                <%--<th>推送描述</th>
                                <th>推送内容</th>--%>
                                <th>推送类型</th>
                                <th>附加路径</th>
                                <th>创建时间</th>
                                <th>推送状态</th>
                                <th width="120">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.index+1}</td>
                                    <td>
                                            ${i.name }
                                    </td>
                                    <td>
                                        <c:if test="${i.type == 0 }">
                                            单播
                                        </c:if>
                                        <c:if test="${i.type == 1 }">
                                            广播
                                        </c:if>
                                        <c:if test="${i.type == 2 }">
                                            列播
                                        </c:if>
                                        <c:if test="${i.type == 3 }">
                                            其他
                                        </c:if>
                                    </td>
                                    <td>
                                            ${i.url}
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${i.status == 0 }">
                                            未推送
                                        </c:if>
                                        <c:if test="${i.status == 1 }">
                                            已推送
                                        </c:if>
                                    </td>
                                    <td>
                                        <a class="edit-row" href="editUserPush?id=${i.id }">编辑</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2').select2();

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "userPush?keyword=${keyword}&status=${status}&type=${type}&page=" + page;
            }
        });
    });
</script>
</body>
</html>