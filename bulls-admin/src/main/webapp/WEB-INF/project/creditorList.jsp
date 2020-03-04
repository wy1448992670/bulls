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
    <title>债权列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>债权列表
                        <!-- <a class="btn btn-sm btn-primary-outline pull-right" href="add" id="add-row"><i class="icon-plus"></i>创建项目</a> -->
                        <shiro:hasPermission name="project:add">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="creditorDetail" id="add-row"><i
                                    class="icon-plus"></i>创建债权</a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="creditorList">
                            <div class="form-group col-md-5">
                                <div>
                                    <select class="select2able" name="status">
                                        <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>未开启</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>启用</option>
                                        <option value="2" <c:if test="${status == 2 }">selected</c:if>>停止</option>
                                        <option value="3" <c:if test="${status == 3 }">selected</c:if>>其他</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-7">
                                <div>
                                    <input class="form-control keyword" name="keyword" type="text"
                                           placeholder="请输入债权名称或内容搜索" value="${keyword }"/>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>债权名称</th>
                                <th>债权状态</th>
                                <th>所属项目名称</th>
                                <th>所属项目年化收益</th>
                                <th>所属项目总资金</th>
                                <th>所属项目投资期限</th>
                                <th>所属项目开放日期</th>
                                <th>所属项目截止日期</th>
                                <th width="120">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="p" items="${list }" varStatus="st">
                                <tr>
                                    <td>${st.index+1 }</td>
                                    <td>${p.title }</td>
                                    <td>
                                        <select class="select2able" name="status" disabled="disabled">
                                            <option value="" <c:if test="${p.status == null }">selected</c:if>>所有
                                            </option>
                                            <option value="0" <c:if test="${p.status == 0 }">selected</c:if>>未开启
                                            </option>
                                            <option value="1" <c:if test="${p.status == 1 }">selected</c:if>>启用</option>
                                            <option value="2" <c:if test="${p.status == 2 }">selected</c:if>>停止</option>
                                            <option value="3" <c:if test="${p.status == 3 }">selected</c:if>>其他</option>
                                        </select>
                                    </td>
                                    <td>
                                        <a href="${basePath }user/detail/app?id=${p.projectId }">${p.projecTitle }</a>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${p.annualized }" type="percent" maxFractionDigits="3"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>${p.totalAmount }</td>
                                    <td>${p.limitDays }</td>
                                    <td>
                                        <fmt:formatDate value="${p.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${p.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="${basePath }project/creditorDetail?id=${p.id}"
                                           id="edit">编辑</a>
                                        <a class="delete-row" href="${basePath }project/creditorList"
                                           onClick="return deleteDetail(${p.id})" id="delete">删除</a>
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
    ;
    function deleteDetail(id) {
        var flag = false;
        $.ajax({
            url: '${basePath}project/deleteCreditorConfig',
            type: 'post',
            data: 'id=' + id,
            async: false,
            dataType: "json",
            success: function (result) {
                if (result) {
                    flag = true;
                } else {
                    alert("删除项目债权配置失败");
                    flag = false;
                }
            }
        });
        return flag;
    }
    ;
    $(function () {

        $('.select2able').select2({width: "150"});
        /* $(".select2able").change(function(){
         $("#form").submit();
         }); */
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "creditorList?keyword=${keyword}&status=${status}&page=" + page;
            }
        });
    });
</script>
</body>
</html>