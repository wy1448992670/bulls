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
    <title>幸运号码管理 - 详情</title>
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
                幸运号码管理 - 详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>用户名</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>散标累计年化投资额(元)</th>
                                <th>幸运号码个数</th>
                                <th>幸运号码</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list}" varStatus="s">
                                <tr>
                                    <td>${i.username}</td>
                                    <td>${i.trueName}</td>
                                    <td>${i.phone}</td>
                                    <td>${i.amount}</td>
                                    <td>${i.count}</td>
                                    <td>
                                        <a href="javascript:void(0);" onclick="allCode(${i.userId})">查看</a>
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
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "detail?period=${period}&page=" + page;
            }
        });
    });
    
    function allCode(userId){
    	$.ajax({
            url: "${basePath}luckeyCode/all/code?userId="+userId+"&period="+"${period}",
            dataType: "json",
            success: function (data) {
                if (data) {
                	var all = "幸运号码: ";
                	for(var i=0; i<data.length; i++){
                		all += data[i].id + "     ";
                	}
					alert(all)                	
                }
            }
        });
    }
    
</script>
</body>
</html>