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
    <title>用户交易记录查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .table {
            /*table-layout: fixed;*/
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
                投资管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>交易记录查询
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                        <shiro:hasPermission name="trade:list:export">
                            <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" href="javascript:;" onclick="exportExcel()"><i class="icon-plus"></i>导出交易记录</a>
                              <script>
                                  function exportExcel(){
                                  	var parameter="parameter=1";
                                  	var aoeType = $("#aoeType").val();
              						if(aoeType!=""){
              							parameter+="&aoeType="+aoeType;
              						}
              						var keyword = $("#keyword").val();
              						if(keyword!=""){
              							parameter+="&keyword="+keyword;
              						}
              						var startDate = $("#startDate").val();
              						if(startDate!=""){
              							parameter+="&startDate="+startDate;
              						}
              						var endDate = $("#endDate").val();
              						if(endDate!=""){
              							parameter+="&endDate="+endDate;
              						}
              						var departmentId = $("#departmentId").val();
              						if(departmentId!=""){
              							parameter+="&departmentId="+departmentId;
              						}
                                      location.href="${basePath}trade/list/export?"+parameter;
                                  }
                              </script>
	                 </shiro:hasPermission>
                    </div>

                    <div class="widget-content padded clearfix">
                        <div class="form-inline hidden-xs col-lg-5 col-xs-6 pull-left">
                            <shiro:hasPermission name="trade:exportDetail">
                                <div class="row" style="text-align:left">
                                    <div class="form-group col-md-4" style="margin-right:80px;">
                                        <input class="form-control datepicker" name="exportDate" id="exportDate"
                                               placeholder="请选择要导出资金快照的时间点" type="text"/>
                                    </div>
                                    <div class="form-group col-md-4 right hidden-xs">
                                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                                           onclick="yyccheck()" href="javascript:;">
                                            <i class="icon-plus"></i>导出所选日期的客户资金快照Excel
                                        </a>
                                    </div>
                                    <script>
                                        function yyccheck() {
                                            var date = $("#exportDate").val();
                                            if (!date || date == "") {
                                            	alert("请选择导出资金快照的时间点");
                                                return;
                                            } 
                                            var parameter="date=" + date;
                                            var departmentId = $("#departmentId").find("option:selected").val();
                      						if(departmentId!=""){
                      							parameter+="&departmentId="+departmentId;
                      						}
                                            
                                            location.href = "${basePath}report/export/tradeRecordDetail?"+parameter;
                                        }
                                    </script>
                                </div>
                            </shiro:hasPermission>
                        </div>
                        <div id="myModal">
                            <form class="search1 AppFixed form-inline col-lg-5 pull-right col-xs-11" id="form" action="list">
                                <input type="hidden" name="page" value="1"/>
                                <div class="row">
                                    <div class="form-group col-md-4 ">
                                        <select class="select2able" name="aoeType" id="aoeType">
                                            <option value="" <c:if test="${aoeType == null }">selected</c:if>>选择类型</option>
                                            <c:forEach var="item" items="${accountOperate }">
                                                <option value="${item.featureName}" <c:if test="${aoeType == item.featureName }">selected</c:if>>
                                                        ${item.description}&nbsp;(${item.accountType.description}：${item.accountOperateType.description})
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <%--<div class="form-group col-md-6 ">
                                        <select class="select2able" name="accountType">
                                            <option value="" <c:if test="${accountType == null }">selected</c:if>>选择账户类型</option>
                                            <c:forEach var="item" items="${accountTypes }">
                                                <option value="${item.featureType}" <c:if test="${accountType == item.featureType }">selected</c:if>>${item.description}</option>
                                            </c:forEach>
                                        </select>
                                    </div>--%>
                                    <div class="form-group col-md-4">
                                        <input class="form-control keyword" name="keyword" type="text" id="keyword"
                                               placeholder="请输入用户名称或者手机号搜索" value="${keyword }"/>
                                    </div>
                                    <div class="form-group col-md-4 col-xs-6">
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
                                        <input class="form-control datepicker" value="${startDate }" name="startDate" id="startDate"
                                               type="text" placeholder="请选择起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${endDate }" name="endDate" id="endDate"
                                               type="text" placeholder="请选择结束时间"/>
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
                                    <th>真名</th>
                                    <th>手机号</th>
                                    <th>交易金额</th>
                                    <th>账户可用余额</th>
                                    <th>冻结余额</th>
                                    <th>授信资金</th>
                                    <th>冻结授信资金</th>
                                    <th>交易类型</th>
                                    <th>账户类型</th>
                                    <th>资金操作类型</th>
                                    <th>交易时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${list }">
                                    <tr>
                                        <td>${i.id }</td>
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
                                        <td>
                                            <fmt:formatNumber value="${i.amount }" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.balanceAmount }" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.frozenAmount }" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.creditAmount }" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${i.frozenCreditAmount }" maxFractionDigits="2"/>
                                        </td>
                                        <td>
                                                ${i.aoeTypeMsg }
                                        </td>
                                        <td>
                                                ${i.accountTypeMsg }
                                        </td>
                                        <td>
                                                ${i.accountOperateTypeMsg }
                                        </td>
                                        <td>
                                                ${i.createDate }
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
                return "list?keyword=${keyword}&aoeType=${aoeType}&endDate=${endDate}&startDate=${startDate}"
                		+"&departmentId=${departmentId}&page=" + page;
            }
        });
    });

</script>
</body>
</html>
