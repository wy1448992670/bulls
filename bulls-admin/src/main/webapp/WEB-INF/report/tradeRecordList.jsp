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
    <title>交易记录查询</title>

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
                        交易记录
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="tradeRecord">
                            <div class="row">
                                <div class="form-group col-md-4"></div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>"
                                           name="startTime" id="startTime"
                                           type="text" placeholder="请选择交易时间开始时间"/>
                                </div>
                                <div class="form-group col-md-4">
                                    <input class="form-control datepicker"
                                           value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>"
                                           name="endTime" id="endTime"
                                           type="text" placeholder="请选择交易时间结束时间"/>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-md-4"></div>
                                <div class="form-group col-md-5 ">
                                    <div>
                                        <input class="form-control keyword" name="trueName" type="text"
                                               placeholder="请输入用户真实姓名称搜索" id="trueName" value="${trueName }"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3 ">
                                    <button class="btn btn-primary pull-right hidden-xs" type="button"
                                            onclick="exportTradeRecord()"> 导出
                                    </button>
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
                                <th>用户ID</th>
                                <th>真实姓名</th>
                                <th>渠道</th>
                                <th>注册时间</th>
                                <th>交易类型</th>
                                <th>交易时间</th>
                                <th>最后时间</th>
                                <th>交易金额</th>
                                <th>年化收益</th>
                                <th>是否</th>
                            </tr>
                            </thead>

                            <tbody>
                            <c:forEach items="${tradeRecordList}" var="tr" varStatus="i">
                                <tr>
                                    <td>${i.index+1}</td>
                                    <td>${tr.uid}</td>
                                    <td>${tr.name}</td>
                                    <td>${tr.code}</td>
                                    <td><fmt:formatDate value="${tr.regtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>${tr.type}</td>
                                    <td><fmt:formatDate value="${tr.ctime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td><fmt:formatDate value="${tr.retime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>${tr.amount}</td>
                                    <td>${tr.amounts}</td>
                                    <td>${tr.isa}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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
	function DateMinus(sDate,wDate){ 
       var sdate = new Date(sDate); 
       var days = wDate.getTime() - sdate.getTime(); 
	   var day = parseInt(days / (1000 * 60 * 60 * 24)); 
	   return day; 
	}
    function exportTradeRecord(){
    	var name=$("#trueName").val();
    	var stt=$("#startTime").val();
    	var ett=$("#endTime").val();
    	var st=new Date(stt);
    	var et=new Date(ett);
    	if(DateMinus(st,et)>31){
    		alert("时间间隔大于一个月,请重新选择");
    		return;
    	}
    	var url="${basePath}report/tradeRecord/export?trueName="+name+"&startTime="+stt+"&endTime="+ett;
    	window.open(url);
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
                return "tradeRecord?trueName=${trueName}&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&page=" + page;
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