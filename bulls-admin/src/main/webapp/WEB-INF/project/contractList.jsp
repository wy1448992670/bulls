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
    <title>详情或合同内容列表</title>
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
                        <i class="icon-table"></i>详情或合同内容列表
                        <shiro:hasPermission name="project:addapp">
                            <a class="btn btn-sm btn-primary-outline pull-right"
                               href="${basePath}project/contractDetail" id="add-row"><i class="icon-plus"></i>创建子标题</a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="app">
                            <div class="form-group col-md-6">
                                <div>
                                    <select class="select2able" name="type">
                                        <option value="" <c:if test="${type == null }">selected</c:if>>所有</option>
                                        <option value="0" <c:if test="${type == 0 }">selected</c:if>>债权</option>
                                        <option value="1" <c:if test="${type == 1 }">selected</c:if>>散标</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-6">
                                <div>
                                    <input class="form-control keyword" name="keyword" type="text"
                                           placeholder="请输入标题关键字" value="${keyword }"/>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th width="80">序号</th>
                                <th>标题</th>
                                <th width="120">类型</th>
                                <!-- <th width="120">子标题序号</th>
                                <th >子标题</th> -->
                                <th width="120">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }" varStatus="st">
                                <tr>
                                    <td>${st.index+1}</td>
                                    <td>${g.title}</td>
                                    <td>
                                        <c:if test="${g.type == 0 }"><span class="label label-info">债权</span></c:if>
                                        <c:if test="${g.type == 1 }"><span class="label label-info">散标</span></c:if>
                                    </td>
                                        <%-- <td>${g.sort}</td>
                                        <td>${g.title}</td> --%>
                                    <td>
                                        <a class="edit-row" href="${basePath}project/contractDetail?id=${g.id }">编辑</a>
                                        <a class="delete-row" href="${basePath }project/contractList"
                                           onClick="return deleteContract(${g.id})" id="delete">删除</a>
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
    function deleteContract(id) {
        var flag = false;
        $.ajax({
            url: '${basePath}project/deleteContract',
            type: 'post',
            data: 'id=' + id,
            async: false,
            dataType: "json",
            success: function (result) {
                if (result.code) {
                    flag = true;
                } else {
                    flag = false;
                }
                alert(result.msg);
            }
        });
        return flag;
    }
    ;
    $(function () {
        $('.select2able').select2();
        $(".select2able").change(function () {
            $("#form").submit();
        });
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
                return "app?keyword=${keyword}&status=${status}&page=" + page;
            }
        });
    });
</script>
</body>
</html>