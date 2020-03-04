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
    <title>更换手机查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                更换手机查询
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>更换手机查询
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form"
                              action="${basePath}user/changePhoneList">
                            <div class="row">
                                <div class="form-group col-md-5"></div>
                                <div class="form-group col-md-5 right">
                                    <input name="keyword" type="text" placeholder="请输入用户昵称、真实姓名、手机号搜索"
                                           class="form-control keyword" value="${keyword }"/>
                                </div>
                                <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()">搜索</button>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    序号
                                </th>
                                <th>
                                    昵称
                                </th>
                                <th>
                                    真实姓名
                                </th>
                                <th>
                                    变更时间
                                </th>
                                <th>
                                    变更信息
                                </th>
                                <th>
                                    新手机号码
                                </th>
                                <th>
                                    旧手机号码
                                </th>
                                <th>
                                    修改状态
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="x">
                                <tr>
                                    <td>
                                            ${x.count}
                                    </td>
                                    <td>
                                            ${i.username }
                                    </td>
                                    <td>
                                            ${i.trueName }
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${i.time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                            ${i.message}
                                    </td>
                                    <td>
                                    	<shiro:lacksPermission name="user:adminPhone">
                                            ${i.new_phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.new_phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                    	<shiro:lacksPermission name="user:adminPhone">
                                            ${i.old_phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.old_phone }
                                        </shiro:hasPermission>
                                    </td>
                                    <td>
                                        <c:if test="${i.status==0}"><span class="label label-info">失败</span></c:if>
                                        <c:if test="${i.status==1}"><span class="label label-success">成功</span></c:if>
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
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">

    function bb() {
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
        $('.select2able').select2({width: "150"});
        $('.active').click(function () {
            var userId = $(this).attr('userId');
            $.getJSON("${basePath}user/active/sina?id=" + userId, null, function (data) {
                alert(data.resultMsg);
            });
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "changePhoneList?keyword=${keyword}&page=" + page;
            }
        });
    });
</script>
</body>
</html>
