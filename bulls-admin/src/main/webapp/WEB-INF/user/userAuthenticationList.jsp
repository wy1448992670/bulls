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
    <title>用户实名记录列表</title>
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
                        <i class="icon-table"></i>用户实名记录列表
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="userAuthenticationList">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control keyword" name="keyword" type="text" placeholder="请输入关键字" value="${keyword }"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <%--<a class="btn btn-sm btn-primary-outline pull-right" href="" id="add-row"><i class="icon-plus"></i>添加银行信息</a>--%>
                                    <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <select class="select2able" name="type">
                                        <option value="" <c:if test="${type == null}">selected</c:if>>所有操作类型</option>
                                        <option value="1" <c:if test="${type == 1 }">selected</c:if>>解除实名</option>
                                        <option value="0" <c:if test="${type == 0 }">selected</c:if>>实名认证</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-6">
                                    <select class="select2able" name="status">
                                        <option value="" <c:if test="${status == null }">selected</c:if>>所有状态</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>失败</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>已完成</option>
                                        <option value="2" <c:if test="${status == 2 }">selected</c:if>>进行中</option>
                                    </select>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>姓名</th>
                                <th>身份证号</th>
                                <th>用户电话</th>
                                <th>操作类型</th>
                                <th>状态信息</th>
                                <th>处理状态</th>
                                <th>操作时间</th>
                                <th width="80">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${s.index+1}</td>
                                    <td>${i.trueName}</td>
                                    <td>
                                    	<shiro:lacksPermission name="user:adminPhone">
                                            ${i.identificationNo.replaceAll("(\\d{6})\\d{5}(\\w{4})","$1*****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.identificationNo }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                        <a class="label label-success"><c:if test="${i.type == 0}">实名认证</c:if></a>
                                        <a class="label label-warning"><c:if test="${i.type == 1}">解除实名</c:if></a>
                                    </td>
                                    <td style="width: 400px;">${i.message}</td>
                                    <td>
                                        <a class="label label-danger"><c:if test="${i.status == 0}">失败</c:if></a>
                                        <a class="label label-success"><c:if test="${i.status == 1}">已完成</c:if></a>
                                        <a class="label label-warning"><c:if test="${i.status == 2}">进行中</c:if></a>
                                    </td>
                                    <td><fmt:formatDate value="${i.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <a class="btn btn-primary" href="editUserAuthentication?id=${i.id}&type=${type}&status=${status}&page=${page}&keyword=${keyword}">编辑</a>
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

    function aa() {
        $("#form").submit();
    }

    $(function () {
        $('.select2able').select2();
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "userAuthenticationList?keyword=${keyword}&status=${status}&type=${type}&page=" + page;
            }
        });
    });
</script>
</body>
</html>