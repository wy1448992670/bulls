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
    <title>物权类型</title>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
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
                物权类型列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>产品列表<a class="btn btn-sm btn-primary-outline pull-right" href="add"
                                                         id="add-row"><i class="icon-plus"></i>新增产品</a>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="list">
                            <div class="form-group col-md-8"></div>
                            <div class="form-group col-md-4">
                                <div>
                                    <select class="select2able" name="status">
                                        <option value="" <c:if test="${status == null }">selected</c:if>>产品状态</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>未启用</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>已启用</option>
                                        <option value="2" <c:if test="${status == 2 }">selected</c:if>>已删除</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th style="width: 50px;">
                                    序号
                                </th>
                                <th>
                                    物权名称
                                </th>
                                <th>
                                    开放额度
                                </th>
                                <th>
                                    产品利率
                                </th>
                                <th>
                                    起投金额
                                </th>
                                <th>
                                    递增金额
                                </th>
                                <th>
                                    可用红包
                                </th>
                                <th>
                                    可用加息券
                                </th>
                                <th>
                                    授权服务期限
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    产品状态
                                </th>
                                <th>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="g" items="${list }" varStatus="i">
                                <tr>
                                    <td style="text-align: center;">
                                            ${i.count }
                                    </td>
                                    <td>
                                            ${g.name }
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${g.openAmount}"  pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                                <fmt:formatNumber value="${g.annualizedMin }" type="percent" maxFractionDigits="3"
                                                                  groupingUsed="false"/>
                                    </td>
                                    <td>
                                            ${g.minAmount }
                                    </td>
                                    <td>
                                            ${g.increaseAmount }
                                    </td>
                                    <td>
                                        <c:if test="${g.supportHongbao==0}">
                                            可用
                                        </c:if>
                                        <c:if test="${g.supportHongbao==1}">
                                            不可用
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${g.supportCoupon==0}">
                                            可用
                                        </c:if>
                                        <c:if test="${g.supportCoupon==1}">
                                            不可用
                                        </c:if>
                                    </td>
                                    <td>
                                            ${g.outDays }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${g.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${g.status==0}">
                                            未启用
                                        </c:if>
                                        <c:if test="${g.status==1}">
                                            启用中
                                        </c:if>
                                        <c:if test="${g.status==2}">
                                            已删除
                                        </c:if>
                                    </td>
                                    <td>
                                        <a class="btn btn-success" href="update?id=${g.id }" id="delete">编辑</a>
                                        <%--<c:if test="${g.status==0}">--%>
                                                <%----%>
                                        <%--</c:if>--%>
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
        $('.select2able').select2({width: "200"});
        $(".select2able").change(function () {
            $("#form").submit();
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?status=${status}&page=" + page;
            }
        });
    });
</script>
</body>
</html>