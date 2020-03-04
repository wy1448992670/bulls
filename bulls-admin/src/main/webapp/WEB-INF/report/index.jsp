<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
    String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9;IE=8;IE=7;IE=EDGE">
<meta name="renderer" content="webkit">
<meta name="renderer" content="ie-comp">
<meta name="renderer" content="ie-stand">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务告警</title>
<link href="${basePath}css/bootstrap.min.css" media="all"
	rel="stylesheet" type="text/css" />
<link href="${basePath}css/style.css" media="all" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="modal-shiftfix">
		<div class="container-fluid main-content">

			<div class="row">
				<div class="col-lg-6">
					<div class="widget-container fluid-height">
						<div class="widget-content padded text-center"
							style="font-weight: bold; font-family: 'Arial'">
							<div class="heading">
								<h2>172.19.72.22~27</h2>
							</div>
							<table class="table biger16 table-bordered">
								<thead>
									<tr>
										<th>&nbsp;</th>
										<th>应用名称</th>
										<th>端口</th>
										<th>状态</th>
										<th>检测时间</th>
									</tr>
								</thead>
								<tbody id="alist">
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<div class="col-lg-6">
					<div class="widget-container fluid-height">
						<div class="widget-content padded text-center"
							style="font-weight: bold; font-family: 'Arial'">
							<div class="heading">
								<h2>负载均衡未启用</h2>
							</div>
							<table class="table biger16 table-bordered">
								<thead>
									<tr>
										<th>&nbsp;</th>
										<th>应用名称</th>
										<th>端口</th>
										<th>状态</th>
										<th>检测时间</th>
									</tr>
								</thead>
								<tbody id="blist">
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="widget-content padded">
							<div class="heading" id="withDrawAlert2"
								style="font-size: 26px; line-height: 5px; color: #000000; height: 5px">
							</div>
							<div class="heading" id="withDrawAlert"
								style="font-size: 26px; line-height: 5px; color: #000000; height: 5px">
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="widget-content padded">
							<div class="heading" id="rechargeAlert"
								style="font-size: 26px; line-height: 5px; color: #000000; height: 5px">
							</div>

							<div class="heading" id="rechargeAlert2"
								style="font-size: 26px; line-height: 5px; color: #000000; height: 5px">
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="widget-content padded">
							<div class="heading" id="registerAlert"
								style="font-size: 24px; line-height: 5px; color: #000000; height: 5px">
							</div>
							<div class="heading" id="registerAlert2"
								style="font-size: 24px; line-height: 5px; color: #000000; height: 5px">
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="widget-content padded">
							<div class="heading" id="investmentAlert"
								style="font-size: 24px; line-height: 5px; color: #000000; height: 5px">
							</div>
							<div class="heading" id="investmentAlert2"
								style="font-size: 24px; line-height: 5px; color: #000000; height: 5px">
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="widget-content padded">
							<div class="heading" id="clearAlert"
								style="font-size: 24px; line-height: 5px; color: #000000; height: 5px">
							</div>

							<div class="heading" id="clearAlert2"
								style="font-size: 24px; line-height: 5px; color: #000000; height: 5px">
							</div>

							<div class="heading" id="clearAlert3"
								style="font-size: 24px; line-height: 5px; color: #000000; height: 5px">
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>


	<script src="${basePath}js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		function query() {
			$
					.ajax({
						type : "GET",
						url : "${basePath}report/query?query="
								+ new Date().getTime(),
						dataType : "json",
						success : function(data) {
							if (data) {
								var myDate = new Date();
								var astr = "";
								var bstr = "";
								for (var i = 0; i < data.server.length; i++) {
									var s = "";
									var st = "";
									if (data.server[i].status == 0) {
										s = " style='background:red;color:#fff'";
										st = "异常";
									} else {
										st = "<font color=green>正常</font>";
									}
									var str = "<tr "+s+">";
									str += "<td><font style=\"font-weight: bold;\">"
											+ (i + 1) + "</font></td>";
									str += "<td><font style=\"font-weight: bold;\">"
											+ data.server[i].module
											+ "</font></td>";
									str += "<td><font style=\"font-weight: bold;\">"
											+ data.server[i].port
											+ "</font></td>";
									str += "<td><font style=\"font-weight: bold;\">"
											+ st + "</font></td>";
									str += "<td><font style=\"font-weight: bold;\">"
											+ data.server[i].ctime
											+ "</font></td>";
									str += "</tr>";
									if (data.server[i].name == "a") {
										astr += str;
									} else {
										bstr += str;
									}
								}
								$("#alist").html(astr);
								$("#blist").html(bstr);

								var withdrawFailStr = "";
								if (data.withdrawFail < 0) {
									withdrawFailStr = "提现处理中数据获取异常";
								} else {
									if (data.withdrawFail > 0) {
										withdrawFailStr = "当日总提现：<b style='color:blue'>"
												+ data.totalWithdrawToday
												+ "</b>笔，提现处理中数据：<b><font color='red'>"
												+ data.withdrawFail
												+ "</font></b> 笔"
									} else {
										withdrawFailStr = "当日总提现：<b style='color:blue'>"
												+ data.totalWithdrawToday
												+ "</b>笔，提现处理中数据：<b>"
												+ data.withdrawFail + "</b> 笔"
									}

								}

								$("#withDrawAlert").html(withdrawFailStr);
								$("#withDrawAlert2")
										.html(
												"当日提现总金额：<b style='color:blue'>"
														+ data.withdrawAmount
														+ "</b>元");

								//充值情况
								$("#rechargeAlert").html(
										"当日支付总金额：<b style='color:blue'>"
												+ data.totalChargeAmount
														.toFixed(2) + "</b>元");
								$("#rechargeAlert2")
										.html(
												"支付：<b style='color:blue'>"
														+ data.chargeCount
														+ "</b>笔，成功：<b style='color:green'>"
														+ data.chargeCountOfSuccess
														+ "</b>笔，失败：<b style='color:blue'>"
														+ data.chargeCountOfFail
														+ "</b>笔");
								//注册情况
								$("#registerAlert")
										.html(
												"总注册量：<b style='color:blue'>"
														+ data.allRegisterUser
														+ "</b>个，当日注册量：<b style='color:blue'>"
														+ data.todayRegisterUser
														+ "</b>个");
								$("#registerAlert2")
										.html(
												"当日签到：<b style='color:blue'>"
														+ data.todaySignedUser
														+ "</b>个");
								//投资情况
								$("#investmentAlert").html(
										"当日投资总额：<b style='color:blue'>"
												+ data.investmentAmount
														.toFixed(2) + "</b>元");
								$("#investmentAlert2").html(
										"投资笔数：<b style='color:blue'>"
												+ data.investmentCount
												+ "</b>笔");
								//赎回情况
								$("#clearAlert").html(
										"当日赎回总金额：<b style='color:blue'>"
												+ data.totalRedeemAmount
														.toFixed(2) + "</b>元");
								if (data.hasInterestedCount < 0) {
									$("#clearAlert2").html(
											"总赎回：<b style='color:blue'>"
													+ data.totalRedeemCount
													+ "</b>笔");
								} else {
									$("#clearAlert2")
											.html(
													"总赎回：<b style='color:blue'>"
															+ data.totalRedeemCount
															+ "</b>笔，已赎回：<b style='color:blue'>"
															+ data.hasInterestedCount
															+ "</b>笔");
								}

								$("#clearAlert3")
										.html(
												"本金：<b style='color:blue'>"
														+ data.totalCapitalAmount
																.toFixed(2)
														+ "</b>元，利息：<b style='color:blue'>"
														+ data.totalInterestAmount
																.toFixed(2)
														+ "</b>元");
								//活期情况
								/* $("#huoAlert").html("当日活期投资总额：<b style='color:blue'>" + data.investmentAmountHuo.toFixed(2) + "</b>元");
								$("#huoAlert2").html("投资笔数：<b style='color:blue'>" + data.investmentCountHuo + "</b>笔"); */
							}
						}
					});

		}
		var interval;
		function quartz() {
			//1000 * 60
			interval = window.setInterval("query()", 1000 * 60);//1分钟加载  
		}

		query();
		quartz();
	</script>
</body>
</html>