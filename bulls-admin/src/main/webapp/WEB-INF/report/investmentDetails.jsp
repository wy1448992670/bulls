<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>每日客户资金详情</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }

        .heading label {
            font-size: 18px;
        }
    </style>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
    <link rel="stylesheet" href="${basePath}css/style.css">

</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                每日客户资金详情
            </h1>
            <c:if test="${!empty message }">
                <div class="alert alert-danger text-center" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only"></span>
                        ${message }
                </div>
            </c:if>
        </div>

        <!-- end DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        每日客户资金详情
                        <p class="pull-right">
                            总计:总充值<label class="label label-success"><fmt:formatNumber value="${sumRecharge}"
                                                                                       pattern="###,###.##"
                                                                                       type="number"/></label>元，
                            总提现<label class="label label-warning"><fmt:formatNumber value="${sumWithdraw}"
                                                                                    pattern="###,###.##"
                                                                                    type="number"/></label>元，
                            总投资<label class="label label-success"><fmt:formatNumber value="${sumAssets}"
                                                                                    pattern="###,###.##"
                                                                                    type="number"/></label>元，
                            <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="export-allCapitalDetail"
                               href="${basePath}report/export/investmentDetailsDay?day=<fmt:formatDate value="${day}" pattern="yyyy-MM-dd"/>"><i
                                    class="icon-plus"></i>导出Excel</a>
                        </p>
                    </div>

                    <div class="widget-content padded clearfix">
                        <form class="form-inline col-lg-2 pull-right" id="form" action="investmentDetails">
                            <div class="form-group col-md-7">
                                <input class="form-control datepicker"
                                       value="<fmt:formatDate value="${day}" pattern="yyyy-MM-dd"/>" name="day"
                                       type="text" placeholder="请选择起始时间"/>
                            </div>
                            <button class="btn btn-primary pull-right" type="button" onclick="aa()"> 搜索</button>
                        </form>
                        <table class="table table-bordered table-hover" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>用户ID</th>
                                <th>真实姓名</th>
                                <th>电话号码</th>
                                <th>总充值-总投资</th>
                                <th>总充值</th>
                                <th>总投资</th>
                                <th>总提现</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${list}" var="x" varStatus="st">
                                <tr>
                                    <td>${st.count }</td>
                                    <td>${x.id}</td>
                                    <td>${x.true_name}</td>
                                    <td>${x.phone}</td>
                                    <td><fmt:formatNumber value="${x.sumra}"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${x.sumr}"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${x.suma}"></fmt:formatNumber></td>
                                    <td><fmt:formatNumber value="${x.sumw}"></fmt:formatNumber></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="text-right">
                            <ul id="allCapitalDetail-pagination"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script src="${basePath}js/jquery-ui.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
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