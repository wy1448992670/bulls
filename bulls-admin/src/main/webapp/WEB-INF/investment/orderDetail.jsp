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
    <title>订单详情</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        strong.money {
            color: #007aff;
            font-size: 18px;
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
                订单详情
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">订单号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${investment.orderNo }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">用户真实姓名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${investment.trueName }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">投资金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${investment.amount }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">投资期限</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${investment.limitDays }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">投资时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${investment.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">总利息</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${investment.interestAmount }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">红包金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${investment.hongbaoMoney }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">需支付金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${investment.remainAmount}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">支付状态</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <%--<c:forEach var="item" items="${investPayState }">
                                            <c:if test="${investment.payStatus == item.code }">${item.description}</c:if>
                                        </c:forEach>--%>
                                        <c:if test="${investment.payStatus == 0 }">
                                            <span class="label label-default">未支付</span>
                                        </c:if>
                                        <c:if test="${investment.payStatus == 1 }">
                                            <span class="label label-warning">支付中</span>
                                        </c:if>
                                        <c:if test="${investment.payStatus == 2 }">
                                            <span class="label label-success">已支付</span>
                                        </c:if>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">订单状态</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <%--<c:forEach var="item" items="${investmentState}">
                                            <c:if test="${item.code == investment.orderStatus}">${item.description}</c:if>
                                        </c:forEach>--%>
                                        <c:if test="${investment.orderStatus == 0 }">
                                            <span class="label label-default">未饲养</span>
                                        </c:if>
                                        <c:if test="${investment.orderStatus == 1 }">
                                            <span class="label label-warning">饲养期</span>
                                        </c:if>
                                        <c:if test="${investment.orderStatus == 2 }">
                                            <span class="label label-success">已卖牛</span>
                                        </c:if>
                                        <c:if test="${investment.orderStatus == 3 }">
                                            <span class="label label-danger">已取消</span>
                                        </c:if>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>派息明细列表
                       <%--  <a class="btn btn-sm btn-primary-outline pull-right"
                           href="#" id="export-interest">TODO sq 导出<i class="icon-plus"></i>导出Excel</a> --%>
                    </div>
                    <div class="widget-content padded clearfix">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="interest">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>利息</th>
                                    <th>本金</th>
                                    <th>项目期数</th>
                                    <th>状态</th>
                                    <th>加息金额</th>
                                    <th>时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                        <div class="text-right">
                            <ul id="pagination"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "100"});
        showInterest();
    });

    function showInterest(page) {
        page = page ? page : 1;
        $.ajax({
            url: "${basePath}investment/interest",
            type: "get",
            dataType: "json",
            data: "investmentId=" + ${investment.id} + "&page=" + page,
            success: function (obj) {
                var dividended = obj.dividended;
                console.log(obj)

                $("#interest tbody").empty();
                for (var i = 0; i < obj.list.length; i++) {
                    var interest = obj.list[i];
                    // var typeMsg = interest.type && interest.type == 1 ? '定期' : '';
                    var hasDividended = interest.hasDividended.toString();
                    var dividendedMsg = '';
                    console.log("hasDividended-"+i+"-"+hasDividended);
                    if (dividended) {
                        for (var j = 0; j < dividended.length; j++) {
                            var dividend = dividended[j];
                            if (dividend.code === hasDividended) {
                                dividendedMsg = dividend.description;
                            }
                        }
                    }
                    var tr = "<tr>" +
                        "<td>" + (interest.id ? interest.id : '') + "</td>" +
                        "<td>" + (interest.interestAmount ? interest.interestAmount : '0') + "</td>" +
                        "<td>" + (interest.capitalAmount ? interest.capitalAmount : '0') + "</td>" +
                        "<td>" + (interest.stage ? interest.stage : '') + "</td>" +
                        "<td>" + (dividendedMsg) + "</td>" +
                        "<td>" + (interest.addInterest  ? interest.addInterest  : '0') + "</td>" +
                        "<td>" + (interest.date ? new Date(interest.date).format('yyyy-MM-dd') : '') + "</td>" +
                        "</tr>";
                    $("#interest tbody").append(tr);
                }
                // console.log(obj);
                if (obj.pages > 0) {
                    $('#pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showInterest(" + page + ")";
                        }
                    });
                }
            }
        });
    }

    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(),    //day
            "h+": this.getHours(),   //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length == 1 ? o[k] :
                    ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }
</script>
</body>
</html>
