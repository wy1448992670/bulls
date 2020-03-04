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
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <title>智投报表</title>

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
                        智投报表
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="investIntellectualList">
                            <div class="row">
                                <div class="form-group col-md-4"></div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>"
                                           name="startTime" id="startTime"
                                           type="text" placeholder="请选择投资日开始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
                                           name="endTime" id="endTime"
                                           type="text" placeholder="请选择投资日结束时间"/>
                                </div>
                            </div>
                            <div class="row">
                                   <div class="form-group col-md-4"></div>
	                               <div class="form-group col-md-4">
	                                    <select class="select2able" name="limitDay" id="limitDay">
	                                        <option value="" <c:if test="${limitDay == null }">selected</c:if>>项目周期</option>
	                                        <option value="7" <c:if test="${limitDay == 7 }">selected</c:if>>7天</option>
	                                        <option value="30" <c:if test="${limitDay == 30 }">selected</c:if>>30天</option>
	                                        <option value="90" <c:if test="${limitDay == 90 }">selected</c:if>>90天</option>
	                                        <option value="180" <c:if test="${limitDay == 180 }">selected</c:if>>180天</option>
		                                    </select>
	                                </div>
									<div class="form-group col-md-4">
	                                    <select class="select2able" name="quitable" id="quitable" >
	                                        <option value=""  <c:if test="${quitable == null }">selected</c:if>>是否可申请退出</option>
	                                        <option value="0" <c:if test="${quitable == 0 }">selected</c:if>>是</option>
	                                        <option value="1" <c:if test="${quitable == 1 }">selected</c:if>>否</option>
	                                    </select>
	                                </div>                            </div>
                            <div class="row">
                                <div class="form-group col-md-4"></div>
                                <div class="form-group col-md-5 ">
                                    <div>
                                        <input class="form-control keyword" name="keyword" type="text"
                                               placeholder="请输入用户昵称、真实姓名称搜索" value="${keyword }" id="keyword"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3 ">
                                    <%-- <shiro:hasPermission name="user:export:app"> --%>
                                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                           id="export-allCapitalDetail" onclick="exportExecl()"    href="javascript:void(0)"><i
                                                class="icon-plus"></i>导出</a>
                                    <%-- </shiro:hasPermission> --%>
                                    <button class="btn btn-primary pull-right hidden-xs" type="button"
                                            onclick="search()"> 搜索
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <table class="table table-bordered table-hover" id="allCapitalDetail">
                            <thead>
                            <tr>

                                <th>序号</th>
                                <th>用户名</th>
                                <th>手机号</th>
                                <th>真实姓名</th>
                                <th>资产包名</th>
                                <th>基础年化</th>
                                <th>加息年化</th>
                                <th>饲养期（天）</th>
                                <th>持有天数（天）</th>
                                <th>投资时间</th>
                                <th>投资金额(元)</th>
                                <th>是否可申请退出</th>
                                <th>申请退出时间</th>
                                <th>退出完成时间</th>
                                <th>归属坐席</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${list}" var="i" varStatus="st">
                                <tr>
                                    <td>${st.count}</td>
                                    <td><a href="${basePath}user/detail/app?id=${i.userId}">${i.userName}</a></td>
                                    <td>
	                                    <shiro:lacksPermission name="user:adminPhone">
                                            ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${i.phone }
                                       </shiro:hasPermission>
                                    </td>
                                    <td><a href="${basePath}user/detail/app?id=${i.userId}">${i.trueName}</a></td>
                                    <td><a href="${basePath}project/getMonthGain?prackgeId=${i.packageId}">${i.title}</a></td>
                                    <td><fmt:formatNumber type="percent" value="${i.annualized}"/></td>
                                    <td><fmt:formatNumber type="percent" value="${i.addAnnualized}"/></td>
                                    <td>${i.limitDays}</td>
                                    <td>${i.holdDay}</td>
                                    <td><fmt:formatDate value="${i.time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatNumber value="${i.amount}" type="currency" pattern="0.00"/></td>
                                    <td>${i.quitStr}</td>
                                    <td> <c:if test="${not empty i.applyOutTime}"><fmt:formatDate value="${i.applyOutTime}" pattern="yyyy-MM-dd"/></c:if>
                                    	 <c:if test="${empty i.applyOutTime}">--------</c:if>
                                      </td>
                                    <td> 
                                    <c:if test="${not empty i.successOutTime}"><fmt:formatDate value="${i.successOutTime}" pattern="yyyy-MM-dd"/></c:if>
                                    	 <c:if test="${empty i.successOutTime}">--------</c:if></td>
                                    <td>
                                        ${i.customerName }
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <strong style="font-size: 20px;">
                             	投资总金额<label class="label label-success"><fmt:formatNumber value="${totalAmount}"     pattern="###,###.##"     type="number"/></label>元
                        </strong>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>

<script type="text/javascript">
function exportExecl(){
	var flag = true;
	var startTime = $("#startTime").val();
	var keyword = $("#keyword").val();
	var limitDay = $("#limitDay").val();
	var quitable = $("#quitable").val();
	var endTime = $("#endTime").val();
	if((startTime=="" && endTime!="")||(startTime!="" && endTime=="")){
		alert("投资时间区间必须填写");
		return;
	}
	if(startTime!="" && endTime!=""){
		if(startTime<=endTime){
			flag = true;
		}else{
			alert("投资结束时间必须大于等于投资开始时间");
			return;
		}
	}else{
		flag=false;
	}
	if(flag){
		var url="${basePath}report/export/investPayment?startTime="+startTime+"&endTime="+endTime+"&keyword="+keyword+"&limitDay="+limitDay+"&quitable="+limitDay;
		window.open(url);
	}else{
		alert("时间区间为必选项");
	}
}

    function search() {
        $("#form").submit();
    }
    $(function () {
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('.select2able').select2({width: "150"});
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "investIntellectualList?keyword=${keyword}&limitDay=${limitDay}&quitable=${quitable}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
    });

</script>
</body>
</html>