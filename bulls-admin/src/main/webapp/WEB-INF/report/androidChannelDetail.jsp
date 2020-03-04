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
    <title>安卓渠道详情统计</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
        }
    </style>
    <link rel="stylesheet" href="${basePath}css/style.css">

</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>报表统计</h1>
        </div>
        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        安卓渠道详情统计
                        <p class="pull-right">
                            总计:注册<label class="label label-success">${map.regcount }</label>人,绑卡<label
                                class="label label-warning">${map.bindcount }</label>人,
                            投资<label class="label label-info">${map.tzshu }</label>人,
                            充值<label class="label label-success"><fmt:formatNumber value="${map.chongzhi }"
                                                                                   pattern="#.##"
                                                                                   type="number"/></label>元,
                            投资<label class="label label-warning"><fmt:formatNumber value="${map.touzi }" pattern="#.##"
                                                                                   type="number"/></label>元,
                            提现<label class="label label-info"><fmt:formatNumber value="${map.tixian }" pattern="#.##"
                                                                                type="number"/></label>元
                        </p>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="androidChannelDetail">
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>" id="date"
                                           name="date" type="text" placeholder="请选择结束时间"/>
                                </div>
                                <div class="form-group col-md-6">
                                    <input class="form-control datepicker" value="<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>" name="endTime"
                                           type="text" placeholder="请选择结束时间"/>
                                </div>


                                <div class="form-inline hidden-xs col-lg-5 pull-right">
                                    <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()">
                                        搜索
                                    </button>
                                </div>
                            </div>
                        </form>
                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>机构</th>
                                <th>安卓渠道代码</th>
                                <th>激活人数</th>
                                <th>点击数</th>
                                <th>注册人数</th>
                                <th>绑卡人数</th>
                                <th>投资人数</th>
                                <th>充值金额</th>
                                <th>投资金额</th>
                                <th>提现金额</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list }" var="i" varStatus="st">
                                <tr>
                                    <td>
                                        <c:if test="${i.jigou == null}">
                                            未知机构
                                        </c:if>
                                        <c:if test="${i.jigou != null}">
                                            ${i.jigou}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.code == null}">
                                            未知渠道
                                        </c:if>
                                        <c:if test="${i.code != null}">
                                            ${i.code}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.linkcnt == null}">
                                            0
                                        </c:if>
                                        <c:if test="${i.linkcnt != null}">
                                            ${i.linkcnt}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.clickcnt == null}">
                                            0
                                        </c:if>
                                        <c:if test="${i.clickcnt != null}">
                                            ${i.clickcnt}
                                        </c:if>
                                    </td>
                                    <td>${i.regcount }</td>
                                    <td>${i.bindcount }</td>
                                    <td>${i.tzshu }</td>
                                    <td><fmt:formatNumber value="${i.chongzhi}" pattern="#.##" type="number"/></td>
                                    <td><fmt:formatNumber value="${i.touzi}" pattern="#.##" type="number"/></td>
                                    <td><fmt:formatNumber value="${i.tixian}" pattern="#.##" type="number"/></td>
                                    <td><a href="${basePath}report/gotoAndroidChannelDetailFromCode?code=${i.code}&startDate=<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>"
                                           class="btn btn-primary">查看详情</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">

    function aa() {
        $("#form").submit();
    }

    $(function () {

        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });

        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "androidChannelDetail?date=<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });
    });
</script>
</body>
</html>