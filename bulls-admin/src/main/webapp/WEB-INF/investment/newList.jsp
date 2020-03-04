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
    <title>安鑫赚用户投资列表</title>
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
                安鑫赚用户投资列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>安鑫赚用户投资列表
                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                           href="${basePath}report/export/investmentNew?keyword=${keyword}&seq=${seq}&codes=${codes}&type=${type}&endTime=${endTime}&investType=${investType}&source=${source}&startTime=${startTime}"><i
                                investTyp class="icon-plus"></i>导出Excel</a>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">

                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="newList">
                            <div class="row">
                                <div class="form-group col-md-3">
                                    <select class="select2able" name="seq">
                                        <option value="" <c:if test="${seq == null }">selected</c:if>>无金额限制</option>
                                        <option value="0" <c:if test="${seq == 0 }">selected</c:if>>0-1000元</option>
                                        <option value="1" <c:if test="${seq == 1 }">selected</c:if>>1000-2000元</option>
                                        <option value="2" <c:if test="${seq == 2 }">selected</c:if>>2000-5000元</option>
                                        <option value="3" <c:if test="${seq == 3 }">selected</c:if>>5000-10000元</option>
                                        <option value="4" <c:if test="${seq == 4 }">selected</c:if>>10000元以上</option>
                                    </select>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="type">
                                            <option value="" <c:if test="${type == null }">selected</c:if>>加入方式</option>
                                            <option value="5" <c:if test="${type == 5 }">selected</c:if>>手动投资</option>
                                            <option value="6" <c:if test="${type == 6 }">selected</c:if>>利息复投</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <select class="select2able" name="source">
                                            <option value="" <c:if test="${source == null }">selected</c:if>>客户来源</option>
                                            <option value="0" <c:if test="${source == 0 }">selected</c:if>>PC网站</option>
                                            <option value="1" <c:if test="${source == 1 }">selected</c:if>>安卓</option>
                                            <option value="2" <c:if test="${source == 2 }">selected</c:if>>IOS</option>
                                            <option value="3" <c:if test="${source == 3 }">selected</c:if>>WAP</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <input class="form-control " value="${codes }" name="codes"
                                               type="text" placeholder="请输入渠道号"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${startTime }" name="startTime"
                                           type="text" placeholder="请选择起始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${endTime }" name="endTime"
                                           type="text" placeholder="请选择结束时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control keyword" name="keyword" type="text"
                                           placeholder="请输入用户昵称、真实姓名、手机号搜索" value="${keyword }"/>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真名
                                </th>
                                <th>
                                    金额
                                </th>

                                <th>
                                    投资时间
                                </th>
                                <th>
                                    使用红包金额
                                </th>
                                <th>
                                    加入方式
                                </th>
                                <th>
                                    渠道
                                </th>
                                <th>
                                    来源
                                </th>
                                <%--<th>--%>
                                    <%--投资合同--%>
                                <%--</th>--%>
                                 <th>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>${i.id}</td>
                                    <%--<td>--%>
                                        <%--<c:if test="${i.type != 1 }">--%>
                                            <%--<a href="${basePath}project/detail?id=${i.project_id}">--%>
                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${empty i.title }">${i.title2}</c:when>--%>
                                                    <%--<c:otherwise>${i.title}</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                            <%--</a>--%>
                                        <%--</c:if>--%>
                                        <%--<c:if test="${i.type == 1 }">--%>
                                         <%--&lt;%&ndash;<a href="#">&ndash;%&gt;--%>
                                             <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
                                                 <%--&lt;%&ndash;<c:when test="${empty i.title }">${i.title2}</c:when>&ndash;%&gt;--%>
                                                 <%--&lt;%&ndash;<c:otherwise>${i.title}</c:otherwise>&ndash;%&gt;--%>
                                             <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
                                         <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
                                        <%--</c:if>--%>
                                        <%--&lt;%&ndash;<a href="${basePath}project/detail?id=${i.project_id}">&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;<c:choose>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<c:when test="${empty i.title }">${i.title2}</c:when>&ndash;%&gt;--%>
                                                <%--&lt;%&ndash;<c:otherwise>${i.title}</c:otherwise>&ndash;%&gt;--%>
                                            <%--&lt;%&ndash;</c:choose>&ndash;%&gt;--%>
                                        <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
                                    <%--</td>--%>

                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.username }</a>
                                    </td>
                                    <td>
                                        <a href="${basePath}user/detail/app?id=${i.user_id}">${i.trueName }</a>
                                    </td>
                                    <td>
                                            ${i.amount}元
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${i.hb_amount > 0 }">${i.hb_amount}元</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.type == 5 }">手动投资</c:if>
                                        <c:if test="${i.type == 6 }">利息复投</c:if>
                                    </td>
                                    <td>
                                            ${i.code}
                                    </td>
                                    <td>
                                        <c:if test="${i.terminal == 0 }">PC端</c:if>
                                        <c:if test="${i.terminal == 1 }">安卓</c:if>
                                        <c:if test="${i.terminal == 2 }">IOS</c:if>
                                        <c:if test="${i.terminal == 3 }">WAP</c:if>
                                    </td>

                                        <%--<td>--%>
                                            <%--<c:if test="${i.type==0 or i.type==2 or i.type==4 or i.type==5 or i.type==6}">--%>
                                                <%--<a target="_blank" href="${basePath}investment/contract?investmentId=${i.id}&userId=${i.user_id}">查看合同</a>--%>
                                            <%--</c:if>--%>
                                        <%--</td>--%>

                                    <c:if test="${i.type==1}">
                                        <td>
                                            <c:if test="${i.id>=104452}">
                                                <a target="_blank" href="${basePath}investment/contractIntermediary?investmentId=${i.id}&userId=${i.user_id}">查看合同</a>
                                            </c:if>
                                            <c:if test="${i.id<104452}">
                                                无
                                            </c:if>
                                        </td>
                                    </c:if>
                                    <td>
                                        <a target="" href="${basePath}investment/investDetail?investmentId=${i.id}">详细</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <%--<B>投资总额:</B>${totalAmount }--%>
                        <%--<B>投资人数:</B>${sum }--%>
                        <ul id="pagination" style=" float: right">
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
        function aa() {
            $("#form").submit();
        }
        $(function () {
            $(".datepicker").datepicker({
                format: 'yyyy-mm-dd'
            });
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
                    return "newList?keyword=${keyword}&seq=${seq}&type=${type}&codes=${codes}&endTime=${endTime}&investType=${investType}&source=${source}&startTime=${startTime}&page=" + page;
                }
            });
        });

        function isUserable() {
            var flag = $('select[name="type"]').val();
            if (flag == 0 || flag == 4) {
                $('select[name="investType"]').attr("disabled", false);
            } else {
                $('select[name="investType"]').attr("disabled", true);
            }
        }
        isUserable();

    </script>
</body>
</html>