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
    <title>客户资金汇总历史查询</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    
    <style type="text/css">
        .input-col4 {
            /*float: right;*/
        }
        .table {
            /*table-layout: fixed;*/
        }

        .table .over {
            overflow: hidden;
            /*width: 40%;*/
            text-overflow: ellipsis;
            white-space: nowrap;
        }
        #allCapitalDetail th{
        	border:1px solid;
        }
        #allCapitalDetail td{
        	border:1px solid;
        }
    </style>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="/WEB-INF/common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                客户资金汇总历史查询
            </h1>
		 
            <div class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                	<span aria-hidden="true">&times;</span>
                </button>
                <strong>提示：</strong>

                <p>1.[统计开始时间]为输入值的0点,不输入,从2019-07-09开始统计<p>
                <p>2.[统计结束时间]为输入值后一天的0点,不输入,统计截止到当前时间<p>
                <p>3.例:需要查询2019年10月的数据,[统计起止时间点]分别为2019-10-01,2019-10-31</p>
                <p>4.例:需要查询2019年10月1日0点~2019年10月2日0点的数据,[统计起止时间]分别为2019-10-01,2019-10-01</p>
            </div>
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
                        <i class="icon-table"></i>
                    		资产销售库存历史查询
                    	<shiro:hasPermission name="report:assetsTradeHistorySum:export">
	                        <!-- <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="exportExcel()"
	                           href="javascript:;">
	                           <i class="icon-plus"></i>导出Excel
	                        </a> -->
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()"> 搜索</button>
                    </div>
					<script>
                    function exportExcel(){
                    	var parameter="parameter=1";
                    	
                    	var beginDate = $("#beginDate").val();
						if(beginDate!=""){
							parameter+="&beginDate="+beginDate;
						}
						
						var endDate = $("#endDate").val();
						if(endDate!=""){
							parameter+="&endDate="+endDate;
						}
						
						location.href="${basePath}report/assetsTradeHistorySum/export?"+parameter;
                    }
                    function search() {
        				$("#form").submit();
        			};
                    </script>
                    <div class="widget-content padded clearfix">
							<form class="form-inline col-lg-12 pull-right" id="form" action="list">
								<div class="row">
									<div class="form-group col-md-1 text-right">统计开始时间点</div>
									<div class="form-group col-md-2">
										<input class="form-control datepicker"
											value="<fmt:formatDate value="${beginDate}" pattern="yyyy-MM-dd" />"
											name="beginDate" id="beginDate" type="text"
											placeholder="统计开始时间点" />
									</div>
									<div class="form-group col-md-1 text-right">统计结束时间点</div>
									<div class="form-group col-md-2">
										<input class="form-control datepicker"
											value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />"
											name="endDate" id="endDate" type="text"
											placeholder="统计结束时间点" />
									</div>
								</div>

							</form>

							<table border="2" class="table table-bordered" id="allCapitalDetail" style="width:30%;">
                            <thead>
                            <tr>
                                <th colspan="2">客户资金汇总表</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr style="background:#FCD5B4" ><td>现金总收支</td><td><fmt:formatNumber value="${assetsTradeSumMap.cash_amount}" /></td></tr>
                            <tr style="background:#D9D9D9" ><td>现金充值</td><td><fmt:formatNumber value="${assetsTradeSumMap.cash_recharge_amount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>现金提现</td><td><fmt:formatNumber value="${assetsTradeSumMap.cash_withdraw_amount}" /></td></tr>
                            <tr style="background:#D9D9D9" ><td>现金购牛</td><td><fmt:formatNumber value="${assetsTradeSumMap.cash_invest_amount}" /></td></tr>
                            <tr style="background:#D9D9D9" ><td>现金购物</td><td><fmt:formatNumber value="${assetsTradeSumMap.cash_goodsorder_amount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>现金退款</td><td><fmt:formatNumber value="${assetsTradeSumMap.cash_refund_amount}" /></td></tr>
                            <tr><td></td><td></td></tr>
                            
                            <tr style="background:#FCD5B4" ><td>期初余额</td><td><fmt:formatNumber value="${assetsTradeSumMap.beginBalanceAmount+assetsTradeSumMap.beginFrozenAmount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>到期回款</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_buyback_principal_amount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>授信回款</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_buyback_interest_amount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>现金红包</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_cash_hongbao_amount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>余额充值</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_recharge_amount}" /></td></tr>
                            <tr style="background:#D9D9D9" ><td>余额提现</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_withdraw_amount}" /></td></tr>
                            <tr style="background:#D9D9D9" ><td>余额购牛</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_invest_amount}" /></td></tr>
                            <tr style="background:#D9D9D9" ><td>余额购物</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_goodsorder_amount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>余额退款</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_refund_amount}" /></td></tr>
                            <tr style="background:#E4F30F" ><td>余额总收支</td><td><fmt:formatNumber value="${assetsTradeSumMap.balance_amount}" /></td></tr>
                            <tr style="background:#FCD5B4" ><td>期末余额</td><td><fmt:formatNumber value="${assetsTradeSumMap.endBalanceAmount+assetsTradeSumMap.endFrozenAmount}" /></td></tr>
                            <tr><td></td><td></td></tr>
                            <tr style="background:#FCD5B4" ><td>期初授信金额</td><td><fmt:formatNumber value="${assetsTradeSumMap.beginCreditAmount+assetsTradeSumMap.beginFreozenCreditAmount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>本期新增</td><td><fmt:formatNumber value="${assetsTradeSumMap.credit_invest_amount}" /></td></tr>
                            <tr style="background:#D9D9D9" ><td>本期解冻</td><td><fmt:formatNumber value="${assetsTradeSumMap.credit_buyback_interest_amount}" /></td></tr>
							<tr style="background:#D9D9D9" ><td>授信购物</td><td><fmt:formatNumber value="${assetsTradeSumMap.credit_goodsorder_amount}" /></td></tr>
                            <tr style="background:#CCFFFF" ><td>授信退款</td><td><fmt:formatNumber value="${assetsTradeSumMap.credit_refund_amount}" /></td></tr>
                            <tr style="background:#E4F30F" ><td>授信金额总收支</td><td><fmt:formatNumber value="${assetsTradeSumMap.credit_amount}" /></td></tr>
                            <tr style="background:#FCD5B4" ><td>期末授信金额</td><td><fmt:formatNumber value="${assetsTradeSumMap.endCreditAmount+assetsTradeSumMap.endFreozenCreditAmount}" /></td></tr>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">

    $(function () {
    	$(".datepicker").datepicker({
    	    format: 'yyyy-mm-dd',
    	    showSecond: true,
    	    timeFormat: "hh:mm:ss",
    	    dateFormat: "yy-mm-dd"
    	})
    });

</script>
</body>
</html>