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
    <title>智投项目</title>
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
                智投项目
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>智投项目投资列表
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th style="width: 50px;">
                                    序号
                                </th>
                                <th>
                  ID
                                </th>
                                <th>
                                    昵称
                                </th>
                                <th>
                                手机号
                                </th>
                               <th>
          	 投资总金额
                               </th>
                                <th>
                                    持有中金额
                                </th>
                                <th>
			锁定期                                
                                </th>
                                <th>
                                   转入时间
                                </th>
                                <th>
                                已投资天数
                                </th>
                                <th>
                                   已转出金额
                                </th>
                                <th>
                                  申请退出时间
                                </th>
                                <th>
                                   成功退出时间
                                </th>
                                <th>
                                状态
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="pro" items="${produtcList}" varStatus="i">
                                <tr>
                                    <td style="text-align: center;">
                                            ${i.index+1 }
                                    </td>
                                    <td>
                                            ${pro.id }
                                    </td>
                                    <td>
                                            <a href="${basePath}user/detail/app?id=${pro.id}">${pro.username}</a>
                                            
                                    </td>
                                    <td>
                                    	<a href="${basePath}user/detail/app?id=${pro.id}">
                                    	<shiro:lacksPermission name="user:adminPhone">
                                             ${pro.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                         </shiro:lacksPermission>
                                         <shiro:hasPermission name="user:adminPhone">
                                             ${pro.phone }
                                         </shiro:hasPermission>
                                         </a>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${pro.amount}"  pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                    	<fmt:formatNumber value="${pro.remain_amount}"  pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                    <!-- 锁定期 -->
                                    	  ${pro.limitDays}
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${pro.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                    <!-- 已投资天数 -->
                                    	${pro.diffDays}
                                    </td>
                                    <td>
                                   		 <fmt:formatNumber value="${pro.amount-pro.remain_amount}"  pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${pro.apply_out_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                  	<td>
                                        <fmt:formatDate value="${pro.success_out_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                    	<c:if test="${pro.status eq 0}">持有中</c:if>
                                    	<c:if test="${pro.status eq 1}">退出中</c:if>
                                    	<c:if test="${pro.status eq 2}">已退出</c:if>
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
		function bb() {
		    $("#form").submit();
		}
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
                return "getMonthGainInvestment?prackgeId=${prackgeId}&page=" + page;
            }
        });
    });
</script>
</body>
</html>