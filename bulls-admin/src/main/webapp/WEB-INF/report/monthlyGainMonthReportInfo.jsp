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
    <title>智投月报表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all"
	rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet"
	type="text/css" />
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
                       	智投月报表
                    </div>
                    <div class="widget-content padded clearfix">
                    	<form class="form-inline hidden-xs col-lg-5 pull-right" id="form"
								action="monthlyGainMonthReportInfo">
                            <div class="row">

                                <div class="form-group col-md-2 "></div>

                                <div class="form-group col-md-6 ">
                                    <input class="form-control datepicker" value="${date }"
											id="datepicker" name="date" type="text" placeholder="请选择时间" />
                                </div>
                                <div class="form-group col-md-4 ">
										<button class="btn btn-primary pull-right hidden-xs"
											type="button" onclick="aa()">搜索</button>
									</div>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover text-center">
                              <thead>
                              <tr>
                                  <th colspan="4" rowspan="2" class="text-center">日期</th>
                                  <th colspan="4" class="text-center">周周盈7天</th>
                                  <th colspan="4" class="text-center">月月盈30天</th>
                                  <th colspan="4" class="text-center">季季盈90天</th>
                                  <th colspan="4" class="text-center">双季盈180天</th>
                                  <th colspan="4" class="text-center">汇总</th>
                              </tr>
                              <tr>
								<th class="text-center">投资金额</th>
		                        <th class="text-center">可申请退出</th>
		                        <th class="text-center">已申请退出金额</th>
		                        <th class="text-center">已退出金额</th>
		                        <th class="text-center">投资金额</th>
		                        <th class="text-center">可申请退出</th>
		                        <th class="text-center">已申请退出金额</th>
		                        <th class="text-center">已退出金额</th>
		                        <th class="text-center">投资金额</th>
		                        <th class="text-center">可申请退出</th>
		                        <th class="text-center">已申请退出金额</th>
		                        <th class="text-center">已退出金额</th>
		                        <th class="text-center">投资金额</th>
		                        <th class="text-center">可申请退出</th>
		                        <th class="text-center">已申请退出金额</th>
		                        <th class="text-center">已退出金额</th>
		                        <th class="text-center">总投资金额</th>
		                        <th class="text-center">总可申请退出</th>
		                        <th class="text-center">总已申请退出金额</th>
		                        <th class="text-center">总已退出金额</th>
                              </tr>
                              </thead>
                              <tbody>
                              <c:forEach items="${mapResult}" var="mapResult" > 
                                <tr>
                                	<td colspan="4">${mapResult.date}</td>
									<td><fmt:formatNumber value="${mapResult.amount_7_1}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_7_2}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${mapResult.amount_7_3}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_7_4}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${mapResult.amount_30_1}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_30_2}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${mapResult.amount_30_3}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_30_4}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${mapResult.amount_90_1}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_90_2}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${mapResult.amount_90_3}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_90_4}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${mapResult.amount_180_1}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_180_2}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${mapResult.amount_180_3}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_180_4}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${mapResult.amount_7_1+mapResult.amount_30_1+mapResult.amount_90_1+mapResult.amount_180_1}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_7_2+mapResult.amount_30_2+mapResult.amount_90_2+mapResult.amount_180_2}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${mapResult.amount_7_3+mapResult.amount_30_3+mapResult.amount_90_3+mapResult.amount_180_3}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${mapResult.amount_7_4+mapResult.amount_30_4+mapResult.amount_90_4+mapResult.amount_180_4}" pattern="0.00#"/> </td>
								</tr>
								</c:forEach>
								<tr>
                                	<td colspan="4">汇总</td>
									<td><fmt:formatNumber value="${amount_7_1_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_7_2_total}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${amount_7_3_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_7_4_total}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${amount_30_1_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_30_2_total}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${amount_30_3_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_30_4_total}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${amount_90_1_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_90_2_total}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${amount_90_3_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_90_4_total}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${amount_180_1_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_180_2_total}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${amount_180_3_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_180_4_total}" pattern="0.00#"/> </td>
								    <td><fmt:formatNumber value="${amount_1_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_2_total}" pattern="0.00#"/></td>
								    <td><fmt:formatNumber value="${amount_3_total}" pattern="0.00#"/></td> 
								    <td><fmt:formatNumber value="${amount_4_total}" pattern="0.00#"/> </td>
								</tr>
                              </tbody>
                          </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js"
		type="text/javascript"></script>
	<script type="text/javascript"
		src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script type="text/javascript">
	function aa() {
	    $("#form").submit();
	}
    $(function () {
    	$(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
    });
</script>
</body>
</html>