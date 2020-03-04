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
    <title></title>
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
                幸运号码管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>幸运号码列表
                        <%--<shiro:hasPermission name="activity:add">
                            <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                               href="${basePath}activity/sendMessage"><i class="icon-plus"></i>发布活动</a>
                        </shiro:hasPermission>--%>
                    </div>
                    <div class="widget-content padded clearfix">
                       <%-- <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="list">
                            <div class="form-group col-md-5 pull-right">
                                <div>
                                    <select class="select2able" name="status" id="status">
                                        <option value="">请选择活动状态</option>
                                        <option value="0" <c:if test="${status == 0 }">selected</c:if>>未启用</option>
                                        <option value="1" <c:if test="${status == 1 }">selected</c:if>>启用</option>
                                    </select>
                                </div>
                                <input type="hidden" name="page" value="${page }"/>
                            </div>
                        </form>--%>

                        <table class="table table-bordered table-hover" id="setList">
                            <thead>
                            <tr>
                                <th>
                                    期数
                                </th>
                                <th>
                                    标的
                                </th>
                                <th>
                                    状态
                                </th>
                                <th>
                                    操作
                                </th>
                                <th width="310px;">
                                    中奖号码
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }" varStatus="s">
                                <tr>
                                    <td>
                                            ${i.period}
                                    </td>
                                    <td>
                                            ${i.projectName}
                                    </td>
                                    <td id="${i.period}">
                                            ${i.statusStr}
                                    </td>
                                    <td>
                                            <a class="" href="${basePath}luckeyCode/detail?period=${i.period}">查看详情</a>

                                    </td>
                                    <td id="name${i.period}">
                                        <c:if test="${i.statusStr =='已开奖'}">
                                            ${i.winNumber}(${i.trueName})
                                        </c:if>
                                        <c:if test="${i.statusStr =='进行中'}">
                                            -
                                        </c:if>
                                        <c:if test="${i.statusStr =='待开奖'}">
                                            <input type="text" id="winNumber${i.period}"/>&nbsp;&nbsp;<button type="button" class="btn btn-primary" onclick="addWinNumber('${i.id}','${i.period}')">设置中奖号码</button>
                                        </c:if>
                                    </td>
                                    <%--<td>
                                        <shiro:hasPermission name="activity:edit">
                                            <a class="delete-row" href="${basePath}activity/detail?id=${i.id }">编辑</a>&nbsp;
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="activity:forward">
                                            <a class="delete-row" href="${basePath}activity/activityForward?id=${i.id }">活动详情</a>
                                        </shiro:hasPermission>
                                    </td>--%>
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
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">

    $(function () {
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?period=${period}&page=" + page;
            }
        });
    });

    function addWinNumber(id,period){

        var winNumber=$("#winNumber"+period).val();

        $.ajax({
            url: "${basePath}luckeyCode/give/prize?settingId="+id+"&period="+period+"&luckyCode="+winNumber,
            dataType: "json",
            success: function (data) {
                if (data) {

                    if(data.code=='ok'){
                        alert(data.msg);
                        changeTable(period,data.trueName);
                    }else {
                        alert(data.msg);
                    }
                }
            }
        });
    }

    function changeTable(period,trueName){
        var winNumber=$('#winNumber'+period).val();
        $("#"+period).text("已开奖");
        $("#name"+period).html(winNumber+'('+trueName+')');
    }
</script>
</body>
</html>
