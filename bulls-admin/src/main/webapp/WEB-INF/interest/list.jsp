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
    <title>用户利息列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table {
            /*table-layout: fixed;*/
        }

        .table .over {
            overflow: hidden;
            /*width: 40%;*/
            text-overflow: ellipsis;
            /*white-space: nowrap;*/
        }

        .input-col4 {
            /*float: right;*/
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
                用户利息列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户利息列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <shiro:hasPermission name="interest:export">
                            <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()"
                               href="javascript:;"><i
                                    class="icon-plus"></i>导出Excel</a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <script>
                        function yyccheck(){
                            var flag = false;
                            var keyword = $.trim($("#keyword").val());
                            var investmentStartDate = $("#investmentStartDate").val();
                            var investmentEndDate = $("#investmentEndDate").val();
                            var interestStartDate = $("#interestStartDate").val();
                            var interestEndDate = $("#interestEndDate").val();
                            var orderNo = $("#orderNo").val();
                            var hasDividended = $("#hasDividended").val();
                            var departmentId = $("#departmentId").val();
                            // console.log('investmentStartDate:' + investmentStartDate);
                            // console.log('investmentEndDate:' + investmentEndDate);
                            // console.log('interestStartDate:' + interestStartDate);
                            // console.log('interestEndDate:' + interestEndDate);
                            if(interestStartDate && interestEndDate && interestStartDate!="" && interestEndDate!=""){
                                if(interestStartDate<=interestEndDate){
                                    flag = true;
                                }else{
                                    alert("派息结束时间必须大于等于派息开始时间");
                                    return;
                                }
                            }
                            if(investmentStartDate && investmentEndDate && investmentStartDate!="" && investmentEndDate!=""){
                                if(investmentStartDate<=investmentEndDate){
                                    flag = true;
                                }else{
                                    alert("投资结束时间必须大于等于投资开始时间");
                                    return;
                                }
                            }
                            if(flag){
                                location.href="${basePath}report/export/interest?" +"keyword=" + keyword
                                    +"&investmentStartDate=" + investmentStartDate
                                    +"&investmentEndDate=" + investmentEndDate
                                    +"&interestStartDate=" + interestStartDate
                                    +"&interestEndDate=" + interestEndDate
                                    +"&orderNo=" + orderNo
                                    +"&hasDividended=" + hasDividended+"&departmentId="+departmentId;
                            }else{
                                alert("时间区间为必选项");
                            }
                        }
                    </script>
                    <div class="widget-content padded clearfix">
                        <div id="myModal">
                            <form class="search1 AppFixed form-inline col-lg-5 pull-right col-xs-11" id="form" action="list">
                                <input type="hidden" name="page" value="1"/>
                                <div class="row">
                                    <div class="form-group col-md-3 ">
                                        <div class="input-col4">
                                            <select class="select2able" name="hasDividended" id="hasDividended">
                                                <option value="" <c:if test="${hasDividended == null }">selected</c:if>>选择派息状态</option>
                                                <c:forEach var="item" items="${dividended }">
                                                    <option value="${item.featureType}" <c:if test="${hasDividended == item.featureType }">selected</c:if>>${item.description}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3 ">
                                        <div class="input-col4">
                                            <input class="form-control keyword" name="orderNo" type="text" id="orderNo"
                                                   placeholder="请输入订单号搜索" value="${orderNo }"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3 ">
                                        <div class="input-col4">
                                            <input class="form-control keyword" name="keyword" type="text" id="keyword"
                                                   placeholder="请输入用户昵称、真实姓名称搜索" value="${keyword }"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3 col-xs-6">
										<select class="select2able" name="departmentId" id="departmentId">
											<option value="">请选择部门</option>
											<c:forEach var="department" items="${departments}">
												<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
											</c:forEach>
										</select>
									</div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${interestStartDate }" name="interestStartDate" id="interestStartDate"
                                               type="text" placeholder="请选择派息起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${interestEndDate }" name="interestEndDate" id="interestEndDate"
                                               type="text" placeholder="请选择派息结束时间"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${investmentStartDate }" name="investmentStartDate" id="investmentStartDate"
                                               type="text" placeholder="请选择投资起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${investmentEndDate }" name="investmentEndDate" id="investmentEndDate"
                                               type="text" placeholder="请选择投资结束时间"/>
                                    </div>
                                </div>
                                <input type="hidden" name="page" value="${page }"/>
                                <button class="btn btn-primary pull-right hidden-md hidden-sm hidden-lg" type="button" onclick="aa()"> 搜索</button>
                                <button data-dismiss="modal" class="btn hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
                            </form>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>真实姓名</th>
                                    <th>手机号</th>
                                    <th>订单号</th>
                                    <th>利息</th>
                                    <th>本金</th>
                                    <th>总利息</th>
                                    <th>项目期数</th>
                                    <th>派息状态</th>
                                    <th>派息时间</th>
                                    <th>投资时间</th>
                                    <%--<th>操作</th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${list }">
                                    <tr>
                                        <td>${i.id}</td>
                                        <td>
                                            <a href="${basePath}user/detail/app?id=${i.userId}">${i.trueName }</a>
                                        </td>
                                        <td>
                                            <shiro:lacksPermission name="user:adminPhone">
                                                ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                            </shiro:lacksPermission>
                                            <shiro:hasPermission name="user:adminPhone">
                                                ${i.phone }
                                            </shiro:hasPermission>
                                        </td>
                                        <td  style="word-wrap: break-word;">
                                            <a href="${basePath}investment/orderDetail?id=${i.investmentId}">${i.orderNo}</a>
                                        </td>
                                        <td>${i.interestAmount}</td>
                                        <td>${i.capitalAmount}</td>
                                        <td>${i.interestAmountTotal}</td>
                                        <td>${i.stage}</td>
                                        <td>${i.hasDividendedDesc}</td>
                                        <td>
                                            <fmt:formatDate value="${i.updateDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${i.investmentCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                            <%--<td>
                                                <a href="${basePath}interest/detail?userId=${i.id}&startTime=${startTime}&endTime=${endTime}">查看详细记录</a>
                                            </td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <%--<strong style="font-size: 20px;">
                                总计：利息总额
                                <label class="label label-success">
                                    <fmt:formatNumber value="${map.totalAmount}" pattern="###,###.##" type="number"/>
                                </label>元，
                                活期利息
                                <label class="label label-warning">
                                    <fmt:formatNumber value="${map.huoAmount}" pattern="###,###.##" type="number"/>
                                </label>元，
                                散标利息
                                <label class="label label-info">
                                    <fmt:formatNumber value="${map.regularAmount}" pattern="###,###.##" type="number"/>
                                </label>元
                            </strong>--%>
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
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    function aa() {
        $("#form").submit();
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        $('.select2able').select2();
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
                return "list?keyword=${keyword}"
                    +"&investmentStartDate=${investmentStartDate}"
                    +"&investmentEndDate=${investmentEndDate}"
                    +"&interestStartDate=${interestStartDate}"
                    +"&interestEndDate=${interestEndDate}"
                    +"&orderNo=${orderNo}"
                    +"&hasDividended=${hasDividended}"
                    +"&departmentId=${departmentId}&page=" + page;
            }
        });
    });

</script>
</body>
</html>
