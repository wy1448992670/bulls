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
    <title>智投产品信息列表</title>
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
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>智投产品信息
                    </div>
                    <div class="widget-content padded clearfix">

                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>
                                    名称
                                </th>
                                <th>
                                    创建时间
                                </th>
                                <th>
                                    最低投资金额
                                </th>
                                <th>
                                    最高投资金额
                                </th>
                                <th>
                                    递增金额
                                </th>
                                <th>
                                    总投资金额
                                </th>
                                <th>
                                    可申请退出的总金额
                                </th>
              					<th>
                                    已申请退出的总金额
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${projectList}" varStatus="s">
                                <tr>
                                    <td>${i.productId}</td>
                                    <td>
                                        ${i.name}${i.limitDays}天
                                    </td>
                                    <td>
                                       <fmt:formatDate value="${i.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.minAmount}" type="currency" pattern="0.00"/>元
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${i.maxAmount}" type="currency" pattern="0.00"/>元
                                    </td>
                                     <td><fmt:formatNumber value="${i.increaseAmount}" type="currency" pattern="0.00"/>元
                                    </td>
                                    <td><fmt:formatNumber value="${i.totalAmount}" type="currency" pattern="0.00"/>元</td>
                                    <td>
                                        <span class="glyphicon glyphicon-plus" title="查看一周内可申请退出金额"  id="add${i.productId}" onclick="loadData(this,'${i.productId}')"></span>
                                        <span class="glyphicon glyphicon-minus" style="display: none;" id="sub${i.productId}" onclick="clearData(this,'${i.productId}')"></span>
                                        <fmt:formatNumber value="${i.quitTotalAmount}" type="currency" pattern="0.00"/>元
                                    </td>
                                     <td>
                                        <fmt:formatNumber value="${i.quitedTotalAmount}" type="currency" pattern="0.00"/>元
                                    </td>
                                </tr>
                                <c:forEach items="${i.onceWeek}" var="data">
	                                <tr class="${i.productId}" style="display:none;">
	                                        <td colspan="6" align="right"></td>
					               	        <td align="center">${data.dateStr}可申请退出</td>
					              	        <td colspan="1">${data.amount}元</td>
					              	        <td></td>  
	                                 </tr>
                                 </c:forEach>
                            </c:forEach>
                            </tbody>
                        </table>
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
    function clearData(obj,productId){
    	 $("#add"+productId+"").toggle();
   	     $("#sub"+productId+"").toggle();
    	 $("."+productId+"").hide();
    }
     function loadData(obj,productId){
    	 $("#add"+productId+"").toggle();
   	     $("#sub"+productId+"").toggle();
   	     $("."+productId+"").show();
     }
    
    </script>
</body>
</html>