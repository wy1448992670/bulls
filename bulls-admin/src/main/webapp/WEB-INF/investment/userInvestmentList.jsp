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
    <title>用户画像列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}js/bootstrap-select/css/bootstrap-select.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
    .manage-list {
    margin: 0;
    vertical-align: middle;
    font-size: 12px;
    color: #666;
    table-layout: fixed;
    }
    table {
    max-width: 100%;
    background-color: transparent;
    border-collapse: collapse;
    border-spacing: 0;
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
                鑫聚财报表统计管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户画像列表
                        <shiro:hasPermission name="user:export:app">
                            <a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                               href="javscript:void(0)" onclick="exportExecl()" id="add-row"><i class="icon-plus"></i>导出excel</a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-md-6 pull-right" id="form"
                              action="${basePath}investment/list/userInvestmentList">
                            <div class="row">
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="sex" id="sex">
                                            <option value="" <c:if test="${empty sex}">selected</c:if>>所有</option>
                                            <option value="0" <c:if test="${sex == 0 }">selected</c:if>>女</option>
                                            <option value="1" <c:if test="${sex == 1 }">selected</c:if>>男</option>
                                            <option value="2" <c:if test="${sex == 2 }">selected</c:if>>保密</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="code" id="code">
                                            <option value="all" selected="selected">所有</option>
                                            <c:forEach var="codes" items="${codelist}">
								        		  <option value ="${codes}" <c:if test="${codes == code }">selected</c:if>>${codes}</option>
		                                   </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="isNoob" id="isNoob">
                                            <option value="" <c:if test="${empty isNoob}">selected</c:if>>是否投资新手标</option>
                                            <option value="1" <c:if test="${isNoob == 1 }">selected</c:if>>是</option>
                                            <option value="0" <c:if test="${isNoob == 0 }">selected</c:if>>否</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="ishuoInv" id="ishuoInv">
                                            <option value="" <c:if test="${empty ishuoInv}">selected</c:if>>是否活期投资</option>
                                            <option value="1" <c:if test="${ishuoInv == 1 }">selected</c:if>>是</option>
                                            <option value="0" <c:if test="${ishuoInv == 0 }">selected</c:if>>否</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-2">
                                    <div>
                                        <select class="select2able" name="isCredit" id="isCredit">
                                            <option value="" <c:if test="${isCredit == null }">selected</c:if>>是否购买债转</option>
                                            <option value="1" <c:if test="${isCredit == 1 }">selected</c:if>>是</option>
                                            <option value="0" <c:if test="${isCredit == 0 }">selected</c:if>>否</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-7">
                                    <div>
		                                <select class="selectpicker" name="customerId" id="customerId" multiple="multiple" placeholder="选择坐席"  style="height:100px;width:800px;">
										   <c:forEach var="me" items="${mlist}">
								        		 <option value ="${me.id}">${me.true_name}</option>
		                                   </c:forEach>
										</select>
								   </div>
								</div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input name="userName"  id="userName" type="text" placeholder="请输入用户昵称、真实姓名、手机号搜索"
                                               class="form-control keyword" value="${userName}"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input name="identityCard"  id="identityCard" type="text" placeholder="请输入身份证搜索"
                                               class="form-control" value="${identityCard}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">${maxAge}
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control"  value="${minAge}"  id="minAge" name="minAge" type="text" placeholder="请选择起始年龄"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control"  value="${manAge}"  id="maxAge" name="maxAge" type="text" placeholder="请选择结束年龄"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="${startTime}"
                                               id="startTime" name="startTime" type="text" placeholder="请选择注册起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="${endTime}"
                                               id="endTime" name="endTime" type="text" placeholder="请选择注册结束时间"/>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="${bindCardStartTime }""
                                               id="bindCardStartTime" name="bindCardStartTime" type="text"
                                               placeholder="请选择绑卡起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="${bindCardEndTime}"
                                               id="bindCardEndTime" name="bindCardEndTime" type="text"
                                               placeholder="请选择绑卡结束时间"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="${investmentStartTime }"
                                               id="investmentStartTime" name="investmentStartTime" type="text"
                                               placeholder="请选择投资起始时间"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <div>
                                        <input class="form-control datepicker"
                                               value="${investmentEndTime}""
                                               id="investmentEndTime" name="investmentEndTime" type="text"
                                               placeholder="请选择投资结束时间"/>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" name="page" value="${page }"/>
                        </form>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>
                                    ID
                                    </th>
                                    <th>
                                                                      用户名
                                    </th>
                                    <th>
                                        手机号
                                    </th>
                                    <th>
                                      真实名字
                                    </th>
                                    <th>
                                        性别
                                    </th>
                                     <th>
                                        渠道
                                    </th>
                                    <th>
                                        年龄
                                    </th>
                                    <th>
                                        生日
                                    </th>
                                    <th>
                                        是否投资过新手标
                                    </th>
                                    <th>
                                        是否投资过活期
                                    </th>
                                    <th>
                                        投资30天
                                    </th>
                                     <th>
                                        投资60天
                                    </th>
                                    <th>
                                        投资90天
                                    </th>
                                    <th>
                                        投资180天
                                    </th>
                                    <th>
                                        投资240天
                                    </th>
                                    <th>
                                       投资270天
                                    </th>
                                    <th>
                                        投资360天
                                    </th>
                                    <th>
                                       在投金额
                                    </th>
                                     <th>
                                       账户余额
                                    </th>
              <!--                        <th>
                                       是否风险测评
                                    </th> -->
                                     <th>
                                       风险等级
                                    </th>
                                     <th>
                                       历史投资累计金额
                                    </th>
                                     <th>
                                       投资累计年化
                                    </th>
                                    <th>
                                      注册时间
                                    </th>
                                    <th>
                                       绑卡时间
                                    </th>
                                    <th>
                                       投资时间
                                    </th>
                                    <th>
                                       归属坐席
                                    </th>
                                    <th>
                                       是否购买债转
                                    </th>
                                    <th>
                                       债转金额
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="user" items="${list}">
                                    <tr>
                                        <td>
                                                ${user.id }
                                        </td>
                                        <td>
                                                ${user.username }
                                        </td>
                                        <td>
                                            <shiro:lacksPermission name="user:adminPhone">
	                                            ${user.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
	                                        </shiro:lacksPermission>
	                                        <shiro:hasPermission name="user:adminPhone">
	                                            ${user.phone }
	                                        </shiro:hasPermission>
                                        </td>
                                        <td>
                                                ${user.true_name }
                                        </td>
                                        <td>
                                                ${user.sexs}
                                        </td>
                                         <td>
                                                ${user.code}
                                        </td>
                                        <td>
                                               <fmt:formatNumber value="${user.age}" type="number"/>
                                        </td>
                                        <td>
                                                ${user.birth}
                                        </td>
                                        <td>
                                        
                                           <c:if test="${user.noobCount gt 0 }">
                                                                                是
                                           </c:if>
                                             <c:if test="${user.noobCount eq 0 }">
                                                                                 否
                                           </c:if>   
                                        </td>
                                        <td> 
                                        <c:if test="${user.huoInvestment gt 0 }">
                                                                                是
                                           </c:if>
                                             <c:if test="${user.huoInvestment eq 0 }">
                                                                                 否
                                           </c:if>
                                        </td>
                                        <td>
                                                ${user.limit_days_30}
                                        </td>
                                        <td>
                                                ${user.limit_days_60}
                                        </td>
                                        <td>
                                                ${user.limit_days_90}
                                        </td>
                                        <td>
                                                ${user.limit_days_120}
                                        </td>
                                        <td>
                                                ${user.limit_days_180}
                                        </td>
                                        <td>
                                                ${user.limit_days_270}
                                        </td>
                                        <td>
                                                ${user.limit_days_360}
                                        </td>
                                        <td>
                                                <fmt:formatNumber value="${user.uncollect_capital}"  pattern="#,###,###,###,###,##0.00" />
                                        </td>
                                        <td>
                                                <fmt:formatNumber value="${user.available_balance}"  pattern="#,###,###,###,###,##0.00" />
                                        </td>
                                        <td>
                                                ${user.risk_evaluate_score }
                                        </td>
                                         <td>
                                                ${user.amount}
                                        </td>
                                        <td>
                                                ${user.nh }
                                        </td>
                                        <td>
                                             <fmt:formatDate value="${user.register_time}"   pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
		                                        <c:if test="${not empty user.bindCardTime }">
		                                        <fmt:formatDate value="${user.bindCardTime}"   pattern="yyyy-MM-dd HH:mm:ss"/>
		                                        </c:if> 
                                        </td>
                                        <td>
                                                 <fmt:formatDate value="${user.investmentTime }"  pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                                ${user.customerName}
                                        </td>
                                        <td> <c:if test="${not empty user.creditAmount}">
                                                                                          是
                                            </c:if>
                                            <c:if test="${empty user.creditAmount}">
                                                                                          否
                                            </c:if>
                                        </td>
                                        <td>
                                           <fmt:formatNumber value="${user.creditAmount }"  pattern="#,###,###,###,###,##0.00" />
                                        </td>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div>
                           <b>查询总数:</b>${count}<br/>
                           <b>在投金额总数:</b><fmt:formatNumber value="${userInvestmentListCountMap.invCount}"  pattern="#,###,###,###,###,##0.00" />
                           <b>历史投资累计金额总数:</b><fmt:formatNumber value="${userInvestmentListCountMap.hisCountAmount}"  pattern="#,###,###,###,###,##0.00" />
                           <b>投资累计年化总数:</b><fmt:formatNumber value="${userInvestmentListCountMap.hisCountAmountNh}"  pattern="#,###,###,###,###,##0.00" />
                           <b>债转金额总数:</b><fmt:formatNumber value="${userInvestmentListCountMap.creditCountAmount}"  pattern="#,###,###,###,###,##0.00" /><br/>
                        </div>
                        <ul id="pagination" style="float: right"></ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-select/js/bootstrap-select.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-select/js/i18n/defaults-zh_CN.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function () {
		$("#customerId").selectpicker('val','${customerId}'.split(","));
	    $(".datepicker").datepicker({
	        format: 'yyyy-mm-dd',
	        showSecond: true,
	        timeFormat: "hh:mm:ss",
	        dateFormat: "yy-mm-dd"
	    });
	    $('.select2able').select2({width: "150"});
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
	            return "/investment/list/userInvestmentList?isNoob=${isNoob}&ishuoInv=${ishuoInv}&isCredit=${isCredit}&identityCard=${identityCard}&sex=${sex}&code=${code}&customerId=${customerId}&userName=${userName}&minAge=${minAge}&maxAge=${maxAge}&startTime=${startTime}&endTime=${endTime}&bindCardStartTime=${bindCardStartTime}&bindCardEndTime=${bindCardEndTime}&investmentStartTime=${investmentStartTime}&investmentEndTime=${investmentEndTime}&page="+page;
	        }
	    });
	})
	function exportExecl(){
        	var flag = true;
			var customerIds=$("#customerId").val()==null?"":$("#customerId").val();
			if(customerIds!=''){
				customerIds.join(",");
			}
        	var sex = $("#sex").val();
        	var identityCard = $("#identityCard").val();
        	var userName =$("#userName").val();
        	var isCredit=$("#isCredit").val();
        	var isNoob=$("#isNoob").val();
        	var ishuoInv=$("#ishuoInv").val();
        	var code = $("#code").val();
        	//var customerId = $("#customerId").val();
        	var minAge=$("#minAge").val();
        	var maxAge=$("#maxAge").val();
        	var startTime = $("#startTime").val();
        	var endTime = $("#endTime").val();
        	var bindCardStartTime=$("#bindCardStartTime").val();
        	var bindCardEndTime=$("#bindCardEndTime").val();
        	var investmentStartTime=$("#investmentStartTime").val();
        	var investmentEndTime=$("#investmentEndTime").val();
        	if(ishuoInv==''&&isNoob==''&&isCredit==''&&identityCard==''&&sex==''&&userName==''&&customerIds==''&&code=='all'&&minAge==''&&maxAge==''&&startTime==''&&endTime==''&&bindCardStartTime==''&&bindCardEndTime==''&&investmentStartTime==''&&investmentEndTime==''){
        		alert("请至少选择一个条件");
    			return;
        	}
        	if((minAge=="" && maxAge!="")||(minAge!="" && maxAge=="")){
        		alert("年龄区间必须填写");
        		return;
        	}
        	if(minAge!="" && maxAge!=""){
				if(minAge<=maxAge){
        			flag = true;
        		}else{
        			alert("结束年龄必须大于等于开始年龄");
        			return;
        		}
        	}
        	if((startTime=="" && endTime!="")||(startTime!="" && endTime=="")){
        		alert("注册时间区间必须填写");
        		return;
        	}
			if(startTime!="" && endTime!=""){
				if(startTime<=endTime){
        			flag = true;
        		}else{
        			alert("注册结束时间必须大于等于注册开始时间");
        			return;
        		}
        	}else{
        		flag=false;
        	}
			if((bindCardStartTime=="" && bindCardEndTime!="")||(bindCardStartTime!="" && bindCardEndTime=="")){
	    		alert("绑卡时间区间必须填写");
	    		return;
	    	}
			if(bindCardStartTime!="" && bindCardEndTime!=""){
				if(bindCardStartTime<=bindCardEndTime){
        			flag = true;
        		}else{
        			alert("绑卡结束时间必须大于等于绑卡开始时间");
        			return;
        		}
        	}
			if(investmentStartTime!="" && investmentEndTime!=""){
				if(investmentStartTime<=investmentEndTime){
        			flag = true;
        		}else{
        			alert("投资结束时间必须大于等于投资开始时间");
        			return;
        		}
        	}
			if((investmentStartTime=="" && investmentEndTime!="")||(investmentStartTime!="" && investmentEndTime=="")){
	    		alert("投资时间区间必须填写");
	    		return;
	    	}
			if(flag){
				var url="${basePath}report/export/userInvestmentList?startTime="+startTime+"&endTime="+endTime+"&bindCardStartTime="+bindCardStartTime+"&bindCardEndTime="+bindCardEndTime+"&investmentStartTime="+investmentStartTime+"&investmentEndTime="+investmentEndTime+"&sex="+sex+"&code="+code+"&userName="+userName+"&customerId="+customerIds+"&minAge="+minAge+"&maxAge="+maxAge+"&identityCard="+identityCard+"&isNoob="+isNoob+"&isCredit="+isCredit+"&ishuoInv="+ishuoInv;
				window.open(url);
        	}else{
        		alert("时间区间为必选项");
        	}
	}
	function bb(){
		var flag=true;
    	var startTime = $("#startTime").val();
    	var endTime = $("#endTime").val();
    	var minAge=$("#minAge").val();
    	var maxAge=$("#maxAge").val();
    	var bindCardStartTime=$("#bindCardStartTime").val();
    	var bindCardEndTime=$("#bindCardEndTime").val();
    	var investmentStartTime=$("#investmentStartTime").val();
    	var investmentEndTime=$("#investmentEndTime").val();
    	if((minAge=="" && maxAge!="")||(minAge!="" && maxAge=="")){
    		alert("年龄区间必须填写");
    		return;
    	}
    	if(minAge!="" && maxAge!=""){
			if(minAge<=maxAge){
    			flag = true;
    		}else{
    			alert("结束年龄必须大于等于开始年龄");
    			return;
    		}
    	}
    	if((startTime=="" && endTime!="")||(startTime!="" && endTime=="")){
    		alert("注册时间区间必须填写");
    		return;
    	}
		if((startTime!="" && endTime!="")){
			if(startTime<=endTime){
    			flag = true;
    		}else{
    			alert("注册结束时间必须大于等于注册开始时间");
    			return;
    		}
    	}
		if((bindCardStartTime=="" && bindCardEndTime!="")||(bindCardStartTime!="" && bindCardEndTime=="")){
    		alert("绑卡时间区间必须填写");
    		return;
    	}
		if(bindCardStartTime!="" && bindCardEndTime!=""){
			if(bindCardStartTime<=bindCardEndTime){
    			flag = true;
    		}else{
    			alert("绑卡结束时间必须大于等于绑卡开始时间");
    			return;
    		}
    	}
		if((investmentStartTime=="" && investmentEndTime!="")||(investmentStartTime!="" && investmentEndTime=="")){
    		alert("投资时间区间必须填写");
    		return;
    	}
		if(investmentStartTime!="" && investmentEndTime!=""){
			if(investmentStartTime<=investmentEndTime){
    			flag = true;
    		}else{
    			alert("投资结束时间必须大于等于投资开始时间");
    			return;
    		}
    	}
		if(flag){
		    $("#form").submit();
    	}
	}
</script>
</body>
</html>
