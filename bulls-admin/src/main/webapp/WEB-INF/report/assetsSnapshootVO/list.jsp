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
                时点数资金统计
            </h1>
		 
            <div class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                	<span aria-hidden="true">&times;</span>
                </button>
                <strong>提示：</strong>
                <p>1、“余额”=“客户资金汇总表”里第17行“期末余额”</p>
                <p>2、“授信金额”=“客户资金汇总表”里第24行“期末授信金额”</p>
                <p>3、资产金额：查询时点，客户持有的牛只物权本金，包括余额支付、红包支付、现金支付（也等于管理费+饲养费+购牛款）。</p>
                <p>4. 查询时间点未选择时间点后一天的0点.
                	例：选择2019-10-01,查询的数据是2019-10-02 00:00:00的数据</p>
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
                    		时点数资金统计
                    	<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
                    	<shiro:hasPermission name="report:assetsSnapshootVO:export">
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
                    	var orderBy= $("#orderBy").val();
                    	if(orderBy!=""){
							parameter+="&orderBy="+orderBy;
						}
                    	var adesc= $("#adesc").val();
                    	if(adesc!=""){
							parameter+="&adesc="+adesc;
						}
						location.href="${basePath}report/assetsSnapshootVO/export?"+parameter;
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
                                <th class="order_by" id="user_true_name" name="user_true_name" >客户名称</th>
                                <th class="order_by" id="username" name="username" >用户名</th>
                                <th>余额</th>
                                <th>授信金额</th>
                                <th>资产金额</th>
                               	<th>合计金额</th>
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
                                    <td>${property.userTrueName}</td>
                                    <td>${property.username}</td>
                                    <td>${property.wholeBalanceAmount}</td>
                                    <td>${property.wholeCreditAmount}</td>
                                    <td>${property.amount}</td>
                                    <td>${property.sumAmount}</td>
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
                return "list?datePoint=<fmt:formatDate value="${datePoint}" pattern="yyyy-MM-dd" />"+
                		"&pageSize=${pageSize}&orderBy=${orderBy}&adesc=${adesc}&page=" + page;
            }
        });
    });

</script>
</body>
</html>