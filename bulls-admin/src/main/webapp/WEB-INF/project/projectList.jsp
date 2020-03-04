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
    <title>批量创建散标项目</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                        <i class="icon-table"></i>批量创建散标项目
                    </div>
                    <div class="widget-content padded clearfix">
							<form class="form-inline hidden-xs col-lg-4 pull-right" id="form" action="projectList">
								<div class="row">
									<div class="form-group col-md-6"></div>
									<div class="form-group col-md-6">
										<input class="form-control" value="${orderno}"
											name="orderno" id="orderno" type="text"
											placeholder="合同编号" /> 
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6"></div>
									<div class="form-group col-md-6">
										<input class="form-control datepicker" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"
											name="startDate" id="startDate" type="text"
											placeholder="首次借款起始时间" /> 
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6"></div>
									<div class="form-group col-md-6">
											<input class="form-control datepicker" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"
											name="endDate" id="endDate" type="text"
											placeholder="截止时间" />
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6"></div>
									<div class="form-group col-md-3">
										<div>
											<select class="select2able" name="isCreatedProject">
												<option value="">全部</option>
												<option <c:if test="${isCreatedProject=='weichuangjian'}">selected</c:if> value="weichuangjian">未创建</option>
												<option <c:if test="${isCreatedProject=='yichuangjian'}">selected</c:if> value="yichuangjian">已创建</option>
											</select>
										</div>
									</div>
									<div class="form-group col-md-3"><button class="btn btn-primary pull-right hidden-xs" type="button">搜索</button></div>
								</div>
								<input type="hidden" name="page" value="${page}" />
							</form>

							<table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                            	<th>合同ID</th>
                                <th>借款人</th>
                                <th>房租所在位置</th>
                                <th>借款人身份证号</th>
                                <th>首次借款时间</th>
                                <th>首次借款金额（元）</th>
                                <th>单次借款期限（天）</th>
                                <th>约定借款次数</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="item" items="${list}" varStatus="i">
                                <tr>
                                	<td style="text-align: center;">
                                		${item.contractId}
                                    </td>
                                    <td style="text-align: center;">
                                       <a href="${basePath}contractFile/landlordList?page=1&keyword=${item.userName}">${item.landlordName}</a>
                                    </td>
                                    <td style="text-align: left;">
                                       ${item.addressStr}
                                    </td>
                                    <td style="text-align: center;">
                                        ${item.landlordIdCard}
                                    </td>
                                    <td style="text-align: center;">
                                        <fmt:formatDate value="${item.lendBeginTime}" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td style="text-align: center;">
                                        ${item.amountStr}
                                    </td>
                                    <td style="text-align: center;">
                                        ${item.day}
                                    </td>
                                    <td style="text-align: center;">
                                        ${item.lendCount}
                                    </td>
                                    <td style="text-align: center;">
                                        ${item.isCreatedProjectStr}
                                    </td>
                                    <td style="text-align: center;">
                                    <c:if test="${item.isCreatedProject=='weichuangjian'}">
                                    	<shiro:hasPermission name="project:projectList">
                                    	<a href="${basePath}project/projectBatchAdd?id=${item.contractId}" id="batchCreate">批量创建</a>
                                    	</shiro:hasPermission>
                                    </c:if>
                                    <c:if test="${item.isCreatedProject=='yichuangjian'}">
                                    	<a href="${basePath}project/search?id=${item.contractId}" id="search">查看</a>
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
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        
        searchClick();
        
        $('.select2able').select2({width: "150"});
        $(".select2able").change(function () {
            $("#form").submit();
        }); 
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
                return "projectList?orderno=${orderno}&startDate=<fmt:formatDate value="${startDate }" pattern="yyyy-MM-dd"/>&endDate=<fmt:formatDate value="${endDate }" pattern="yyyy-MM-dd"/>&isCreatedProject=${isCreatedProject}&page=" + page;
            }
        }); 
    });
    /* function report() {
        var startTime = $.trim($('#startTime').val());
        var endTime = $.trim($('#endTime').val());
        window.location.href = "${basePath}report/paymentExcel?startTime=" + startTime + "&endTime=" + endTime;
    }
    function aa() {
        $("#form").submit();
    } */
    
    
    function searchClick(){
    	$(".btn-primary").click(function(){
    		$("#form").submit();
    	})
    }
</script>
</body>
</html>