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
    <title>项目列表</title>
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
                        <i class="icon-table"></i>活期项目列表
                        <shiro:hasPermission name="project:addapp">
                            <a class="btn btn-sm btn-primary-outline pull-right" href="${basePath}project/add/app"
                               id="add-row"><i class="icon-plus"></i>创建项目</a>
                        </shiro:hasPermission>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="app">
                            <div class="form-group col-md-5">
                                <div>
                                    <select class="select2able" name="status">
                                        <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>创建</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>预购中</option>
                                        <option value="2" <c:if test="${status == 2 }">selected</c:if>>投资中</option>
                                        <option value="3" <c:if test="${status == 3 }">selected</c:if>>投资完成</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-7">
                                <div>
                                    <input class="form-control keyword" name="keyword" type="text"
                                           placeholder="请输入项目名称搜索" value="${keyword }"/>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    名称
                                </th>
                                <th>
                                    年化收益
                                </th>
                                <th>
                                    已投金额
                                </th>
                                <th>
                                    进度
                                </th>
                                <th>
                                    总额
                                </th>
                                <th>
                                    真实金额
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    状态
                                </th>
                                <th width="60"></th>
                                <th width="60"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }">
                                <tr>
                                    <td>
                                            ${g.title }
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.annualized }" type="percent" maxFractionDigits="3"
                                                          groupingUsed="false"/>
                                    </td>
                                    <td>
                                            ${g.investedAmount }
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.investedAmount/g.totalAmount }"
                                                          maxFractionDigits="2" type="percent"/>
                                    </td>
                                    <td>
                                            ${g.totalAmount / 10000} 万
                                    </td>
                                    <td>
                                        <c:if test="${g.trueAmount==null}">
                                            0元
                                        </c:if>
                                        <c:if test="${g.trueAmount!= null}">
                                            ${g.trueAmount }元
                                        </c:if>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 0 }"><span
                                                class="label label-default">创建</span></c:if>
                                        <c:if test="${g.status == 1 }"><span
                                                class="label label-warning">预购</span></c:if>
                                        <c:if test="${g.status == 2 }"><span class="label label-info">投资中</span></c:if>
                                        <c:if test="${g.status == 3 }"><span
                                                class="label label-success">完成</span></c:if>
                                    </td>
                                    <td>
                                        <c:if test="${g.status == 2 }">
                                            <a class="edit-row" href="${basePath}project/invest?id=${g.id }">投资</a>
                                        </c:if>
                                    </td>
                                    <td>
                                        <a class="delete-row" href="${basePath}project/editapp?id=${g.id }" id="delete">编辑</a>

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
        $('.select2able').select2({width: "150"});
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