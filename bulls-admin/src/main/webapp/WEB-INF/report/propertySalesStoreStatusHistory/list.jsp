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
    <title>资产销售库存历史查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    	<script src="${basePath}js/comm.js" type="text/javascript"></script>
    <style type="text/css">
        .input-col4 {
            /*float: right;*/
        }
        .table {
            /*table-layout: fixed;*/
        }
        .search1{margin-right:10px !important;}

        .table .over {
            overflow: hidden;
            /*width: 40%;*/
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                资产销售库存历史查询
            </h1>
		 
            <div class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                	<span aria-hidden="true">&times;</span>
                </button>
                <strong>提示：</strong>

                <p>1. 查询在某一时点数，公司所有牛只的销售、库存状态。条件：1、目前同一只牛在一个月内只出售一次，2、同一只牛在同一期间有多种状态，取最后一个状态</p>
                <p>2. 代养日期:最后一次饲养开始日期,可能是查询当月内，也可能是跨月</p>
                <p>3. 订单号:取最后一次商场/物权订单号。</p>
                <p>4. 上次回购日期:取最后一次回购数据，第一次销售为空。。</p>
                <p>3. 本期代养成本:公司收取客户的管理费+饲养费
本期与累计：查询时点所属月份称为本期，开始饲养日到查询时点合计为累计。
例：5月7号出售，饲养期3个月。
5月31日统计时，5月7-31日的饲养成本为当月=累计；6月30日统计时，6月1-30日为当月，5月7-6月30为累计。</p>
            </div>
            <c:if test="${!empty message }">
                <div class="alert alert-danger text-center" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only"></span>
                        ${message }
                </div>
            </c:if>
        </div>

        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>
                    		资产销售库存历史查询
                    	<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
                    	<shiro:hasPermission name="report:propertySalesStoreStatusHistory:export">
	                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="exportExcel()"
	                           href="javascript:;">
	                           <i class="icon-plus"></i>导出Excel
	                        </a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="new_search()"> 搜索</button>
                    </div>
					<script>
                    function exportExcel(){
                    	var parameter="parameter=1";
                    	
                    	var datePoint = $("#datePoint").val();
						if(datePoint!=""){
							parameter+="&datePoint="+datePoint;
						}
						var saleStatus = $("#saleStatus").val();
						if(saleStatus!=""){
							parameter+="&saleStatus="+saleStatus;
						}
						var minYueLing = $("#minYueLing").val();
						if(minYueLing!=""){
							parameter+="&minYueLing="+minYueLing;
						}
                    	var maxYueLing = $("#maxYueLing").val();
                    	if(maxYueLing!=""){
							parameter+="&maxYueLing="+maxYueLing;
						}
                    	var pageSize= $("#pageSize").val();
                    	if(pageSize!=""){
							parameter+="&pageSize="+pageSize;
						}
                    	var orderBy= $("#orderBy").val();
                    	if(orderBy!=""){
							parameter+="&orderBy="+orderBy;
						}
                    	var adesc= $("#adesc").val();
                    	if(adesc!=""){
							parameter+="&adesc="+adesc;
						}
						location.href="${basePath}report/propertySalesStoreStatusHistory/export?"+parameter;
                    }
                    function new_search() {
                    	$("#page").val(1);
        				$("#form").submit();
        			};
        			function search() {
        				$("#form").submit();
        			};
                    </script>
                    <div class="widget-content padded clearfix">
                    	<div id="myModal">
							<form class="search1 AppFixed form-inline col-lg-6 pull-right" id="form" action="list">
								<div class="row">
									<div class="form-group col-md-2  col-xs-4 text-right">查询时间点</div>
									<div class="form-group col-md-4 col-xs-8">
										<input class="form-control datepicker"
											value="<fmt:formatDate value="${datePoint}" pattern="yyyy-MM-dd" />"
											name="datePoint" id="datePoint" type="text"
											placeholder="请选择查询时间点" />
									</div>
									<div class="form-group col-md-2 col-xs-4 text-right">销售状态</div>
									<div class="form-group col-md-4 col-xs-8">
										<select class="select2able" name="saleStatus" id="saleStatus">
													<option value=""
														<c:if test="${saleStatus == null }">selected</c:if>>所有</option>
													<option value="true"
														<c:if test="${saleStatus == true }">selected</c:if>>已出售</option>
													<option value="false"
														<c:if test="${saleStatus == false }">selected</c:if>>未出售</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-2 col-xs-4 text-right">月龄>=</div>
									<div class="form-group col-md-4 col-xs-8">
										<input class="form-control keyword" value="${minYueLing}" name="minYueLing" id="minYueLing" type="number" placeholder="起始月龄" />
									</div>
									<div class="form-group col-md-2 col-xs-4 text-right">月龄<</div>
									<div class="form-group col-md-4 col-xs-8">
										<input class="form-control keyword" value="${maxYueLing}" name="maxYueLing" id="maxYueLing" type="number" placeholder="结束月龄" />
									</div>
								</div>
								
								<input type="hidden" name="page" id="page" value="${page}" />
								<input type="hidden" name="pageSize" id="pageSize" value="${pageSize}" />
								<input type="hidden" name="orderBy" id="orderBy" value="${orderBy}" />
								<input type="hidden" name="adesc" id="adesc" value="${adesc}" />
							    	<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="new_search()">搜索</button>
								    <button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
							 </form>
							</div>
							<div class="table-responsive">
							<table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th class="order_by" id="ear_number" name="ear_number" >耳标号</th>
                                <th class="order_by" id="current_yue_ling" name="current_yue_ling" >牛只月龄</th>
                                <th class="order_by" id="is_raised_by_us" name="is_raised_by_us" >出售状态</th>
                                <th>数量</th>
                                <th class="order_by" id="last_deadline" name="last_deadline" >代养日期</th>
                                <th class="order_by" id="last_order_no" name="last_order_no" >订单号</th>
                               	<th class="order_by" id="last_user_true_name" name="last_user_true_name" >客户名称</th>
                                <th class="order_by" id="last_limit_days" name="last_limit_days" >饲养期限</th>
                                <th class="order_by" id="prior_due_time" name="prior_due_time" >上次回购日期</th>
                                <th class="order_by" id="prior_amount+prior_interest_amount" name="prior_amount+prior_interest_amount" >上次回购价款</th>
                                <th class="order_by" id="current_month_manager" name="current_month_manager" >本期代养成本</th>
                                <th class="order_by" id="sum_manager_fee" name="sum_manager_fee" >累计代养成本</th>
                            </tr>
                            <script>
                            $(function () {
	                            $(".order_by").click(function(){
	                            	if($(this).attr("name")==$("#orderBy").val()){
	                            		if($("#adesc").val()=="asc"){
	                            			$("#adesc").val("desc");
	                            		}else{
	                            			$("#adesc").val("asc");
	                            		}
	                            	}else{
	                            		$("#orderBy").val($(this).attr("name"));
	                            		$("#adesc").val("asc");
	                            	}
	                            	search();
	                            });
                            });
			                    
			                </script>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="property" varStatus="st">
                                <tr>
                                    <td>${property.rowNum}</td>
                                    <td>${property.earNumber}</td>
                                    <td>${property.currentYueLing}</td>
                                    <td>${property.saleStatus}</td>
                                    <td>${property.num}</td>
                                    <td><fmt:formatDate value="${property.lastDeadline}" pattern="yyyy-MM-dd" /></td>
                                    <td>${property.lastOrderNo}</td>
                                    <td>${property.lastUserTrueName}</td>
                                    <td>${property.lastLimitDays}</td>
                                    <td><fmt:formatDate value="${property.priorDueTime}" pattern="yyyy-MM-dd" /></td>
                                    <td><fmt:formatNumber value="${property.priorTotalAmount}" /></td>
                                    <td><fmt:formatNumber value="${property.currentMonthManager}" /></td>
                                    <td><fmt:formatNumber value="${property.sumManagerFee}" /></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </div>
                        <div class="text-right">
                        	<h4 class="pull-left">总结果数:<span class="label label-success"><fmt:formatNumber
                                value="${count}" maxFractionDigits="2"></fmt:formatNumber></span></h4>
                            <ul id="pagination"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">

    $(function () {
    	$(".datepicker").datepicker({
    	    format: 'yyyy-mm-dd',
    	    showSecond: true,
    	    timeFormat: "hh:mm:ss",
    	    dateFormat: "yy-mm-dd"
    	})
    	$('.select2able').select2({width: "150"});
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?datePoint=<fmt:formatDate value="${datePoint}" pattern="yyyy-MM-dd" />&saleStatus=${saleStatus}&minYueLing=${minYueLing}&maxYueLing=${maxYueLing}"+
                		"&pageSize=${pageSize}&orderBy=${orderBy}&adesc=${adesc}&page=" + page;
            }
        });
    });

</script>
</body>
</html>