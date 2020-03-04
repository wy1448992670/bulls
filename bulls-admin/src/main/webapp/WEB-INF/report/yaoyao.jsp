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
    <title>鑫聚财摇一摇统计列表</title>
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
                鑫聚财摇一摇统计列表
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>鑫聚财摇一摇统计列表
                        <button class="btn btn-primary pull-right" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-inline hidden-xs col-lg-3 pull-right" id="form"
                              action="${basePath}report/yaoyao" method="get">
                            <div class="form-group col-md-12">
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${startTime }" name="startTime"
                                               type="text" placeholder="请选择起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <input class="form-control datepicker" value="${endTime }" name="endTime"
                                               type="text" placeholder="请选择结束时间"/>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr id="0">
                                <th rowspan="20">
                                    加息券:
                                </th>
                                <td rowspan="20">
                                    中奖加息券次数:&nbsp;<fmt:parseNumber integerOnly="true" value="${rateCouponCount }"/>
                                </td>
                                <td rowspan="4">
                                    中0.1%加息券次数:&nbsp;<fmt:parseNumber integerOnly="true"
                                                                      value="${leastRateCouponCount }"/>
                                </td>
                                <td>
                                    投资小于0.5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${aaRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="1">
                                <td>
                                    投资0.5w到2w:&nbsp;<fmt:parseNumber integerOnly="true" value="${abRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="2">
                                <td>
                                    投资大于2w到5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${acRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="3">
                                <td>
                                    投资大于5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${adRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="4">
                                <td rowspan="4">
                                    中0.2%加息券次数:&nbsp;<fmt:parseNumber integerOnly="true"
                                                                      value="${littleRateCouponCount }"/>
                                </td>
                                <td rowspan="1">
                                    投资小于0.5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${baRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="5">
                                <td>
                                    投资0.5w到2w:&nbsp;<fmt:parseNumber integerOnly="true" value="${bbRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="6">
                                <td>
                                    投资2w到5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${bcRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="7">
                                <td>
                                    投资大于5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${bdRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="8">
                                <td rowspan="4">
                                    中0.3%加息券次数:&nbsp;<fmt:parseNumber integerOnly="true"
                                                                      value="${mediumRateCouponCount }"/>
                                </td>
                                <td rowspan="1">
                                    投资小于0.5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${caRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="9">
                                <td rowspan="1">
                                    投资0.5w到2w:&nbsp;<fmt:parseNumber integerOnly="true" value="${cbRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="10">
                                <td rowspan="1">
                                    投资2w到5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${ccRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="11">
                                <td rowspan="1">
                                    投资大于5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${cdRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="12">
                                <td rowspan="4">
                                    中0.4%加息券次数:&nbsp;<fmt:parseNumber integerOnly="true"
                                                                      value="${bigRateCouponCount }"/>
                                </td>
                                <td rowspan="1">
                                    投资小于0.5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${daRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="13">
                                <td rowspan="1">
                                    投资0.5w到2w:&nbsp;<fmt:parseNumber integerOnly="true" value="${dbRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="14">
                                <td rowspan="1">
                                    投资2w到5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${dcRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="15">
                                <td rowspan="1">
                                    投资大于5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${ddRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="16">
                                <td rowspan="4">
                                    中0.5%加息券次数:&nbsp;<fmt:parseNumber integerOnly="true"
                                                                      value="${biggestRateCouponCount }"/>
                                </td>
                                <td rowspan="1">
                                    投资小于0.5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${eaRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="17">
                                <td rowspan="1">
                                    投资0.5w到2w:&nbsp;<fmt:parseNumber integerOnly="true" value="${ebRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="18">
                                <td rowspan="1">
                                    投资2w到5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${ecRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr id="19">
                                <td rowspan="1">
                                    投资大于5w:&nbsp;<fmt:parseNumber integerOnly="true" value="${edRateCouponCount }"/>
                                </td>
                            </tr>
                            <tr>
                                <th rowspan="3">
                                    红包
                                </th>
                                <td rowspan="3">
                                    中红包次数:<fmt:parseNumber integerOnly="true" value="${hongbaoCount }"/>&nbsp;
                                    红包总额:<fmt:formatNumber
                                        value="${leasthongbaoAmount+mediumhongbaoAmount+bighongbaoAmount }"
                                        pattern="0.00#"/>元
                                </td>
                                <td rowspan="1">
                                    中1-9分红包的次数:&nbsp;<fmt:parseNumber integerOnly="true" value="${leasthongbaoCount }"/>
                                </td>
                                <td rowspan="1">
                                    中1-9分红包的总金额:&nbsp;<fmt:formatNumber value="${leasthongbaoAmount }" pattern="0.00#"/>元
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="1">
                                    中0.1-1块毛钱红包的次数:&nbsp;<fmt:parseNumber integerOnly="true"
                                                                          value="${mediumhongbaoCount }"/>
                                </td>
                                <td rowspan="1">
                                    中0.1-1块毛红包的总金额:&nbsp;<fmt:formatNumber value="${mediumhongbaoAmount }"
                                                                           pattern="0.00#"/>元
                                </td>
                            </tr>
                            <tr>
                                <td rowspan="1">
                                    中1-9.9元红包的次数:&nbsp;<fmt:parseNumber integerOnly="true" value="${bighongbaoCount }"/>
                                </td>
                                <td rowspan="1">
                                    中1-9.9元红包的金额:&nbsp;&nbsp;
                                    <c:if test="${bighongbaoAmount==null }">
                                        0
                                    </c:if>
                                    <fmt:formatNumber value="${bighongbaoAmount}" pattern="0.00#"/>元
                                </td>
                            </tr>
                            <tr>
                                <th colspan="4">
                                    <p style="float: right">总计: 真实中奖率: <fmt:formatNumber
                                            value="${(hongbaoCount+rateCouponCount)/(nothingCount+rateCouponCount+hongbaoCount)*100 }"
                                            pattern="0.0#"/>% &nbsp;
                                        总摇一摇次数: <fmt:parseNumber integerOnly="true"
                                                                 value="${nothingCount+rateCouponCount+hongbaoCount }"/></p>
                                </th>
                            </tr>
                            </thead>
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
    function aa() {
        $("#form").submit();
    }
    ;
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
    });
</script>
</body>
</html>