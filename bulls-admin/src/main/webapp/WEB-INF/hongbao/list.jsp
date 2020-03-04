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
    <title>红包查询</title>
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
                        <i class="icon-table"></i>红包查询
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <shiro:hasPermission name="hongbao:export">
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
                            var type = $("#type").val();
                            var application = $("#application").val();
                            var startSendTime = $("#startSendTime").val();
                            var endSendTime = $("#endSendTime").val();
                            var startUseTime = $("#startUseTime").val();
                            var endUseTime = $("#endUseTime").val();
                            var departmentId = $("#departmentId").val();
                            if(startSendTime && endSendTime && startSendTime!="" && endSendTime!=""){
                                if(startSendTime<=endSendTime){
                                    flag = true;
                                }else{
                                    alert("红包送出结束时间必须大于等于红包送出开始时间");
                                    return;
                                }
                            }
                            if(startUseTime && endUseTime && startUseTime!="" && endUseTime!=""){
                                if(startUseTime<=endUseTime){
                                    flag = true;
                                }else{
                                    alert("红包使用结束时间必须大于等于红包使用开始时间");
                                    return;
                                }
                            }
                            if(flag){
                                location.href="${basePath}report/export/hongbao?keyword=" + keyword
                                    + "&type=" + type
                                    + "&application=" + application
                                    + "&startSendTime=" + startSendTime
                                    + "&endSendTime=" + endSendTime
                                    + "&startUseTime=" + startUseTime
                                    + "&endUseTime=" + endUseTime
                                	+ "&departmentId=" + departmentId;
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
                                    <div class="form-group col-md-3">
                                        <div>
                                            <select class="select2able" name="type" id="type">
                                                <option value="0" <c:if test="${type == 0 }">selected</c:if>>选择类型</option>
                                                <%--<option value="0" <c:if test="${type == 0 }">selected</c:if>>利息红包</option>--%>
                                                <option value="1" <c:if test="${type == 1 }">selected</c:if>>现金红包</option>
                                                <option value="2" <c:if test="${type == 2 }">selected</c:if>>牧场红包</option>
                                                <option value="3" <c:if test="${type == 3 }">selected</c:if>>商城券</option>
                                                <option value="4" <c:if test="${type == 4 }">selected</c:if>>拼牛红包</option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-3">
                                        <div>
                                            <select class="select2able" name="application" id="application">
                                                <option value="2" <c:if test="${application == 2 }">selected</c:if>>全部
                                                </option>
                                                <option value="0" <c:if test="${application == 0 }">selected</c:if>>未使用
                                                </option>
                                                <option value="1" <c:if test="${application == 1 }">selected</c:if>>使用
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <div>
                                            <input class="form-control keyword" name="keyword" type="text" id="keyword"
                                                   placeholder="请输入用户名称或者手机号搜索" value="${keyword }"/>
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
                                        <input class="form-control datepicker" value="${startSendTime }" name="startSendTime" id="startSendTime"
                                               type="text" placeholder="请选择红包发出起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${endSendTime }" name="endSendTime" id="endSendTime"
                                               type="text" placeholder="请选择红包发出结束时间"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${startUseTime }" name="startUseTime" id="startUseTime"
                                               type="text" placeholder="请选择红包使用起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${endUseTime }" name="endUseTime" id="endUseTime"
                                               type="text" placeholder="请选择红包使用结束时间"/>
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
                                    <th>
                                        ID
                                    </th>
                                    <th>
                                        真名
                                    </th>
                                    <th>
                                        手机号
                                    </th>
                                    <th>
                                        红包金额
                                    </th>
                                    <th>
                                        类型
                                    </th>
                                    <th>
                                        红包标题
                                    </th>
                                    <th>
                                        发出时间
                                    </th>
                                    <th>
                                        使用时间
                                    </th>
                                    <th>
                                        过期时间
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${list }">
                                    <tr>
                                        <td>
                                                ${i.id }
                                        </td>
                                        <td>
                                            <a href="${basePath}user/detail/app?id=${i.user_id}">${i.trueName }</a>
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
                                            <fmt:formatNumber value="${i.amount }" maxFractionDigits="2"></fmt:formatNumber>
                                            <c:if test="${i.type == 0 and i.source == 1 }">
                                                <span class="label label-danger">加息${i.limit_day }天</span>
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:if test="${i.type == 2 }"><span
                                                    class="label label-warning">牧场红包</span></c:if>
                                            <c:if test="${i.type == 1 }"><span class="label label-danger">现金红包</span></c:if>
                                            <c:if test="${i.type == 3 }"><span class="label label-primary">商城券</span></c:if>
                                            <c:if test="${i.type == 4 }"><span class="label label-success">拼牛红包</span></c:if>
                                        </td>
                                        <td>
                                                ${i.descript }
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${i.send_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${i.use_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${i.expire_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <ul id="pagination" style="float: right"></ul>
                        <h3 class="pull-left">红包总额:
                            <span class="label label-success">
                                <fmt:formatNumber value="${useSum }" maxFractionDigits="2"></fmt:formatNumber>
                            </span>
                            <!-- <fmt:formatNumber value="${unuseSum }" maxFractionDigits="2"></fmt:formatNumber></span> -->
                        </h3>
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
            format: 'yyyy-mm-dd'
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
                return "list?keyword=${keyword}&application=${application}&type=${type}&endSendTime=${endSendTime}&startSendTime=${startSendTime}"
                    +"&endUseTime=${endUseTime}&startUseTime=${startUseTime}&departmentId=${departmentId}&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
    });


</script>
</body>
</html>
