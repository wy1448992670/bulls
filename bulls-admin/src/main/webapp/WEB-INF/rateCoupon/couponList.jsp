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
    <title>领券中心</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                领券中心
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>券码列表
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="create()">创建券</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-5 pull-right" id="form" action="couponList">
                            <div class="row">
                                <div class="form-group col-md-3 ">
                                    <div>
                                        <select class="select2able" name="type">
                                            <option value="" <c:if test="${type == null }">selected</c:if>>请选择类型</option>
                                            <option value="1" <c:if test="${type == 1 }">selected</c:if>>加息券</option>
                                            <option value="0" <c:if test="${type == 0 }">selected</c:if>>投资红包</option>
                                            <option value="2" <c:if test="${type == 2 }">selected</c:if>>现金红包</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-md-3 ">
                                    <div>
                                        <input class="form-control " name="stockBalance" type="text"
                                               placeholder="剩余库存小于等于" value="${stockBalance}"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <input class="form-control" name="minDays" type="text"
                                               placeholder="适用最低周期" value="${minDays}"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3"></div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <input class="form-control" name="minAmount" type="text"
                                               placeholder="起投金额" value="${minAmount}"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-3">
                                    <div>
                                        <input class="form-control" name="keyword" type="text"
                                               placeholder="红包金额/加息利率" value="${keyword}"/>
                                    </div>
                                </div>

                            </div>
                            <input type="hidden" name="page" value="${page}"/>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    序号
                                </th>
                                <th>
                                    标题
                                </th>
                                <th>
                                    券类型
                                </th>
                                <th>
                                    红包金额/加息利率
                                </th>
                                <th>
                                    起投金额
                                </th>
                                <th>
                                   试用最低周期
                                </th>
                                <th>
                                    有效期
                                </th>
                                <th>
                                    库存上限
                                </th>
                                <th>
                                    剩余库存
                                </th>
                                <th>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list}" varStatus="v">
                                <tr>
                                    <td>${v.index + 1}</td>
                                    <td>${i.title}</td>
                                    <td>${i.typeStr}</td>
                                    <td><c:if test="${i.type==0}">${i.amount}</c:if>
                                        <c:if test="${i.type==1}">${i.rate}</c:if>
                                        <c:if test="${i.type==2}">${i.amount}</c:if></td>
                                    <td>${i.minAmount}</td>
                                    <td>${i.minDays}</td>
                                    <td>${i.effectiveDays}</td>
                                    <td>${i.stockLimit}</td>
                                    <td>${i.stockBalance}</td>
                                    <td>
                                        <a href="${basePath}coupon/couponEdit?templateId=${i.templateId}">编辑</a>
                                        <a href="${basePath}coupon/couponDeleted?templateId=${i.templateId}">删除</a>
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
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath = '${basePath}';
    function search() {
        $("#form").submit();
    }
    
    function create(){
    	location.href = basePath + "/coupon/couponAdd";
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
                return "couponList?keyword=${keyword}&type=${type}&stockBalance=${stockBalance}&minDays=${minDays}&minAmount=${minAmount}&page=" + page;
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