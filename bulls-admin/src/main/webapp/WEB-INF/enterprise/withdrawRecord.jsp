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
    <title>企业管理列表</title>
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
                企业管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>借款人提现记录
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form" action="withdrawRecord">
                        	<div class="row">
								<div class="form-group col-md-6"></div>
								<div class="form-group col-md-6">
									<input class="form-control keyword" name="keyword" type="text"
                                           placeholder="用户姓名/手机号" value="${keyword}"/>
								</div>
							</div>
                            <input type="hidden" name="page" value="${page}"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                   用户名
                                </th>
                                <th>
                                    姓名
                                </th>

                                <th>
                                    手机号
                                </th>
                                <th>
                                    身份证号码
                                </th>
                                <th>
                                    合同编号
                                </th>
                                <th>
                                    是否已提现
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${list}">
                                <tr>
                                    <td>${item.username}</td>
                                    <td>${item.trueName}</td>
                                    <td>${item.phone}</td>
                                    <td>${item.identityCard}</td>
                                    <td>${item.contractId}</td>
                                    <td>${item.isDoneStr}</td>
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
<script type="text/javascript">
    $(function () {
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
                return "withdrawRecord?keyword=${keyword}&page=" + page;
            }
        });
    });
</script>
</body>
</html>