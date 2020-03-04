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
    <title>产品列表</title>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
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
                智投项目
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>智投产品列表
                        <a class="btn btn-sm btn-primary-outline pull-right" style="margin-right: 10px;" href="${basePath}monthlyGainBuildConfig/list" target="_blank"
                                                         id="list-row">打包任务列表</a>
                        <a class="btn btn-sm btn-primary-outline pull-right"  style="margin-right: 10px;" href="monthGainadd" target="_blank"
                                                         id="add-row"><i class="icon-plus"></i>新增资产包</a>
                      
                        
                    </div>
                    <script type="text/javascript">
                    function monthExport(){
                    	var flag = false;
                    	var status = $("#status").val();
                    	var keyword = $("#keyword").val();
                    	var limitDays = $("#limitDays").val();
                    	var startDate = $("#startDate").val();
                    	var endDate = $("#endDate").val();
						if(startDate!="" && endDate!=""){
							if(startDate<=endDate){
                    			flag = true;
                    		}else{
                    			alert("募集结束时间必须大于等于募集开始时间");
                    			return;
                    		}
                    	}
						if(flag){
                    		location.href="${basePath}report/export/monthGainPackage?startDate="+startDate+"&endTime="+endDate+"&limitDays="+limitDays+"&status="+status+"&keyword="+keyword;
                    	}else{
                    		alert("募集开始时间区间为必选项");
                    	}
                    }
                    </script>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="monthGainList">
                        <div class="row">
                       	    <!-- <div class="form-group col-md-4">
                            </div> -->
                            <div class="form-group col-md-4">
                                <div>
                                    <input class="form-control keyword" id="keyword" name="keyword" type="text"  placeholder="请输入标题名称搜索" value="${keyword }"/>
                                </div>
                            </div>
                         </div>
                         <div class="row">
                            <div class="form-group col-md-4">
                                <div>
                                    <select class="select2able" name="status" id="status">
                                        <option value="" <c:if test="${status == null }">selected</c:if>>资产包状态</option>
                                        <option value="chuangjian" <c:if test="${status == 'chuangjian' }">selected</c:if>>预告中</option>
                                        <option value="mujizhong" <c:if test="${status == 'mujizhong' }">selected</c:if>>募集中</option>
                                        <option value="mujiwancheng" <c:if test="${status == 'mujiwancheng' }">selected</c:if>>募集完成</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-4">
                                <div>
                                    <select class="select2able" name="limitDays" id="limitDays">
                                        <option value="" <c:if test="${limitDays == null }">selected</c:if>>锁定期</option>
                                        <c:forEach items="${monthGainProductLimitDays }" var="days">
                                        	<option value="${days.limitDays}" <c:if test="${days.limitDays eq limitDays}">selected</c:if>>${days.limitDays}天</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${startDate }" name="startDate" id="startDate"
                                           type="text" placeholder="募集起始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker" value="${endDate }" name="endDate" id="endDate"
                                           type="text" placeholder="募集结束时间"/>
                                </div>
                                <shiro:hasPermission name="user:export:app">
                                  	<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="monthExport()" href="JavaScript:;"><i class="icon-plus"></i>导出Excel</a>
                                   </shiro:hasPermission>
                                 <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()"> 搜索</button>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th style="width: 50px;">
                                    序号
                                </th>
                                <th>
                                    资产包名称
                                </th>
                                <th>
                                    锁定期（天）
                                </th>
                                <th>
                                    募集金额
                                </th>
                                <th>
                                    已募集金额
                                </th>
                                <th>
                                    募集开始时间
                                </th>
                                <th>
                                   募集结束时间
                                </th>
                                <th>
                                    资产包状态
                                </th>
                                <th>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="monthg" items="${list }" varStatus="i">
                                <tr>
                                    <td style="text-align: center;">
                                            ${i.count }
                                    </td>
                                    <td>
                                            ${monthg.title }
                                    </td>
                                    <td>
                                            ${monthg.limitDays}
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${monthg.totalAmount}"  pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                        <fmt:formatNumber value="${monthg.investedAmount}" pattern="###,###.##"  type="number"/>
                                    </td>
                                    <td>
                                     	<c:if test="${monthg.status!='chuangjian'}">
                                        	<fmt:formatDate value="${monthg.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </c:if>
                                        <c:if test="${monthg.status=='chuangjian'}">
                                        	--------------
                                        </c:if>
                                    </td>
                                    <td>
                                        <fmt:formatDate value="${monthg.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </td>
                                    <td>
                                        <c:if test="${monthg.status=='chuangjian'}">
                                                                                     预告中
                                        </c:if>
                                        <c:if test="${monthg.status=='mujizhong'}">
                                                                                     募集中
                                        </c:if>
                                        <c:if test="${monthg.status=='mujiwancheng'}">
                                                                                     已完成
                                        </c:if>
                                    </td>
                                    <td>
                                        <a class="btn btn-success" href="getMonthGain?prackgeId=${monthg.id }" id="query">查看</a>
                                        <c:if test="${monthg.status ne 'mujiwancheng'}">
                                        	<a class="btn btn-primary" href="importAssetMarks?prackgeId=${monthg.id}">导入</a>
                                        </c:if>
                                        
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
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
  <script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
		function bb() {
		    $("#form").submit();
		}
    $(function () {
    	$(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
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
                return "monthGainList?keyword=${keyword}&status=${status}&limitDays=${limitDays}&startDate=${startDate}&endDate=${endDate}&page="+ page;
            }
        });
    });
</script>
</body>
</html>