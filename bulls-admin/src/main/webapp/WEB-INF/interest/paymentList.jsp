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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <title>回款查询</title>

</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>投资管理</h1>
        </div>
        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        回款客户名单
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="paymentList">
                            <div class="row">
                                <div class="form-group col-md-4"></div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>"
                                           name="startTime"
                                           type="text" placeholder="请选择回款日开始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
                                           name="endTime"
                                           type="text" placeholder="请选择回款日结束时间"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4"></div>
                                <div class="form-group col-md-5 ">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text"
                                               placeholder="请输入用户昵称、真实姓名称搜索" value="${keyword }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3 ">
                                    <shiro:hasPermission name="user:export:app">
                                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                           id="export-allCapitalDetail"
                                           href="${basePath}report/paymentListReport?keyword=${keyword}&startTime=<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"><i
                                                class="icon-plus"></i>导出</a>
                                    </shiro:hasPermission>
                                    <button class="btn btn-primary pull-right hidden-xs" type="button"
                                            onclick="search()"> 搜索
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover" id="allCapitalDetail">
                            <thead>
                            <tr>

                                <th>序号</th>
                                <th>用户名</th>
                                <th>手机号</th>
                                <th>真实姓名</th>
                                <th>回款利息</th>
                                <th>回款本金</th>
                                <th>今日投资</th>
                                <th>投资时间</th>
                                <th>回款时间</th>
                                <th>是否是安鑫赚</th>
                                <th>
                                    归属坐席
                                </th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${list}" var="i" varStatus="st">
                                <tr>
                                    <td>${st.count}</td>
                                    <td>${i.username}</td>
                                    <td><a href="${basePath}user/detail/app?id=${i.id}">
                                        <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                        </shiro:hasPermission>
                                    </a></td>
                                    <td>${i.true_name}</td>
                                    <td>${i.interest}</td>
                                    <td>${i.capital}</td>
                                    <td>${i.todayAmount}</td>
                                    <td><fmt:formatDate value="${i.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatDate value="${i.date}" pattern="yyyy-MM-dd"/></td>
                                    <td>
                                            ${i.flag }
                                    </td>
                                    <td>
                                            ${i.cusName }
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <strong style="font-size: 20px;">
                            回款本金总额<label class="label label-success"><fmt:formatNumber value="${list2.capital}"
                                                                                        pattern="###,###.##"
                                                                                        type="number"/></label>元
                            回收利息总额<label class="label label-warning"><fmt:formatNumber value="${list2.interest}"
                                                                                     pattern="###,###.##"
                                                                                     type="number"/></label>元
                            <%--，--%>
                            <%--定期利息<label class="label label-info"><fmt:formatNumber value="${map.regularAmount}"--%>
                                                                                  <%--pattern="###,###.##"--%>
                                                                                  <%--type="number"/></label>元--%>
                        </strong>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>

<script type="text/javascript">


    function search() {
        $("#form").submit();
    }
    $(function () {
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });


        $('.select2able').select2({width: "150"});
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "paymentList?keyword=${keyword}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
    });

</script>
</body>
</html>