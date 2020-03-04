<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<style type="text/css">
.a-name {
	display: inline-block;
	overflow: hidden;
	width: 350px;
	text-overflow: ellipsis;
	white-space: nowrap;
	vertical-align: top;
}

i.home-count {
	font-size: 45px;
	display: inline-block;
	vertical-align: top;
	margin: 15px 12px 0 0;
}

.rank-i {
	border-radius: 10px;
	background-color: #ddd;
	font-size: 15px;
	width: 20px;
	display: inline-block;
	text-align: center;
	color: #fff;
	font-style: normal;
}

.rank-i.top {
	background-color: #34ADFF;
}
</style>
</head>
<body>
	<div class="modal-shiftfix">
		<!-- Navigation -->
		<jsp:include page="common/header.jsp"></jsp:include>
		<!-- End Navigation -->
		<div class="container-fluid main-content">
			<div class="row">
				<div class="form-group col-md-3 col-xs-6">
				<select class="select2able" name="departmentId" id="departmentId" >
					<c:forEach var="department" items="${departments}" >
						<option value="${department.id }" <c:if test="${departmentId == department.id }">selected</c:if>>${department.name }</option>
					</c:forEach>
				</select>
			</div>
			</div>
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container stats-container">
						<div class="col-md-4">
							<div class="number">
								<i class="iconfont home-count">&#xe60b;</i> ${realUserCount }人
							</div>
							<div class="text">用户数</div>
						</div>
						<div class="col-md-4">
							<div class="number">
								<i class="iconfont home-count">&#xe60a;</i>
								<c:if test="${investmentAmount < 10000 }"><fmt:formatNumber
	                                    value="${investmentAmount}" pattern="#.##" type="number"/>元</c:if>
								<c:if test="${investmentAmount >= 10000}">
									<fmt:formatNumber value="${investmentAmount / 10000}"
										pattern="#.##" type="number" />万</c:if>
								<c:if test="${investmentAmount >= 100000000}"><fmt:formatNumber
	                                    value="${investmentAmount / 100000000}" pattern="#.##" type="number"/>亿</c:if>
							</div>
							<div class="text">物权交易额</div>
						</div>
						<div class="col-md-4">
	                        <div class="number">
	                            <i class="iconfont home-count">&#xe605;</i>
	                            <c:if test="${goodsAmount < 10000 }"><fmt:formatNumber
	                                    value="${goodsAmount}" pattern="#.##" type="number"/>元</c:if>
	                            <c:if test="${goodsAmount >= 10000 && goodsAmount < 100000000}"><fmt:formatNumber
	                                    value="${goodsAmount / 10000}" pattern="#.##" type="number"/>万</c:if>
	                            <c:if test="${goodsAmount >= 100000000}"><fmt:formatNumber
	                                    value="${goodsAmount / 100000000}" pattern="#.##" type="number"/>亿</c:if>
	                        </div>
	                         <div class="text">商城交易额</div>
                    	</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<div class="widget-container fluid-height">
						<div class="heading">
							<i class="icon-bar-chart"></i>
						</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<div id="hour-line" style="height: 300px;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="heading">
							<i class="icon-bar-chart"></i>
						</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<div id="user-line" style="height: 300px;"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="heading">
							<i class="icon-bar-chart"></i>
						</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<div id="invest-line" style="height: 300px;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="heading">
							<i class="icon-bar-chart"></i>
						</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<div id="huo-invest-line" style="height: 300px;"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="widget-container fluid-height">
						<div class="heading">
							<i class="icon-bar-chart"></i>
						</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<div id="withdraw-line" style="height: 300px;"></div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-4">
					<div class="widget-container fluid-height">
						<div class="heading">资产排行</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<table class="table">
									<thead>
										<tr>
											<th>序号</th>
											<th>昵称</th>
											<th>实名</th>
											<th>总资产</th>
										</tr>
									</thead>
									<c:forEach var="a" items="${list1 }" varStatus="s">
										<tr>
											<td><i class="rank-i ${s.index <=2 ? 'top':'' }">${s.index+1 }</i></td>
											<td>${a.username }</td>
											<td>${a.true_name }</td>
											<td><fmt:formatNumber
													value="${a.balance_amount+a.frozen_amount+a.credit_amount+a.freozen_credit_amount+a.capital}"
													minFractionDigits="2" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div class="widget-container fluid-height">
						<div class="heading">在投金额排行</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<table class="table">
									<thead>
										<tr>
											<th>序号</th>
											<th>昵称</th>
											<th>实名</th>
											<th>在投金额</th>
										</tr>
									</thead>
									<c:forEach var="a" items="${list2 }" varStatus="s">
										<tr>
											<td><i class="rank-i ${s.index <=2 ? 'top':'' }">${s.index+1 }</i></td>
											<td>${a.username }</td>
											<td>${a.true_name }</td>
											<td><fmt:formatNumber
													value="${a.capital }"
													minFractionDigits="2" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div class="widget-container fluid-height">
						<div class="heading">收益排行</div>
						<div class="widget-content padded text-center">
							<div class="graph-container">
								<div class="caption"></div>
								<table class="table">
									<thead>
										<tr>
											<th>序号</th>
											<th>昵称</th>
											<th>实名</th>
											<th>总收益</th>
										</tr>
									</thead>
									<c:forEach var="a" items="${list3 }" varStatus="s">
										<tr>
											<td><i class="rank-i ${s.index <=2 ? 'top':'' }">${s.index+1 }</i></td>
											<td>${a.username }</td>
											<td>${a.true_name }</td>
											<td><fmt:formatNumber value="${a.interAmount }"
													minFractionDigits="2" /></td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${basePath}js/echart/echarts.common.min.js"></script>
	<script type="text/javascript" src="${basePath}js/echart/macarons.js"></script>
	<script type="text/javascript">
    $(function () {
        getHourData('hour-line');
        getUserData();
        getInvestData('invest-line');
        getRechargeData('huo-invest-line');
        getWithdrawData();
        
        $("#departmentId").change(function(){ 
          /*    getUserData();
             getInvestData('invest-line');
             getRechargeData('huo-invest-line');
             getWithdrawData(); */
             var departmentId = $("#departmentId").find('option:selected').val();
             window.location.href = "${basePath}home?departmentId="+departmentId;
        });
    });


    function createChart(id, option) {
        var myChart = echarts.init(document.getElementById(id), 'macarons');
        myChart.setOption(option);
    }

    var hourOption = {
        title: {
            text: '一周24小时平均投资/支付/提现图'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['金额']
        },
        toolbox: {
            show: true,
            feature: {
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                axisTick: {
                    alignWithLabel: true
                },
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value} 元'
                }
            }
        ],
        series: [
            {
                name: '牛只购买金额',
                type: 'bar',
                data: [],
                stack: '金额',
                barWidth: '40%',
                markPoint: {
                    data: []
                }
            },
            {
                name: '商城购买金额',
                type: 'bar',
                data: [],
                stack: '金额',
                barWidth: '40%',
                markPoint: {
                    data: []
                }
            },
            {
                name: '提现金额',
                type: 'bar',
                data: [],
                stack: '金额',
                barWidth: '40%',
                markPoint: {
                    data: []
                }
            }
        ]
    };

    var rechargeOption = {
        title: {
            text: '近一月支付'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['支付金额']
        },
        toolbox: {
            show: true,
            feature: {
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value} 元'
                }
            }
        ],
        series: [
            {
                name: '支付金额',
                type: 'line',
                data: [],
                areaStyle: {normal: {}},
                markPoint: {
                    data: []
                }
            }
        ]
    };

    var investOption = {
        title: {
            text: '近一月投资'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['投资金额']
        },
        toolbox: {
            show: true,
            feature: {
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true},
                saveAsImage: {show: true}
            }
        },
        calculable: true,
        xAxis: [
            {
                type: 'category',
                boundaryGap: false,
                data: []
            }
        ],
        yAxis: [
            {
                type: 'value',
                axisLabel: {
                    formatter: '{value} 元'
                }
            }
        ],
        series: [
            {
                name: '活期投资',
                type: 'line',
                data: [],
                areaStyle: {normal: {}},
                markPoint: {
                    data: []
                }
            },
            {
                name: '散标投资',
                type: 'line',
                data: [],
                areaStyle: {normal: {}},
                markPoint: {
                    data: []
                }
            },
            {
                name: '新手标投资',
                type: 'line',
                data: [],
                areaStyle: {normal: {}},
                markPoint: {
                    data: []
                }
            }
        ]
    };

    function getUserData() {
        var option = {
            title: {
                text: '近一月用户注册'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['用户注册数']
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} 人'
                    }
                }
            ],
            series: [
                {
                    name: '用户注册数',
                    type: 'line',
                    data: [],
                    areaStyle: {normal: {}},
                    markPoint: {
                        data: []
                    }
                }
            ]
        };

        $.ajax({
            url: "${basePath}report/user/increase?departmentId=${departmentId}",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var xArray = [];
                    var dataArray = [];
                    $.each(data, function (i, v) {
                        xArray.push(v.regTime);
                        dataArray.push(v.count);
                    });
                    option.xAxis[0].data = xArray;
                    option.series[0].data = dataArray;
                    createChart('user-line', option);
                }
            }
        });
    }

    function getHourData(id) {
        $.ajax({
            url: "${basePath}report/hour/report?departmentId=${departmentId}",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalxArray = [];
                    var normalDataArray = [];
                    var normalDataArray2 = [];
                    var normalDataArray3 = [];
                    var investData = data.list;
                    var rechargeData = data.list2;
                    var withdrawData = data.list3;
                    if (investData) {
                        $.each(investData, function (i, v) {
                            normalxArray.push(v.time);
                            normalDataArray.push(parseInt(v.amount));
                        });
                    }
                    if (rechargeData) {
                        $.each(rechargeData, function (i, v) {
                            normalxArray.push(v.time);
                            normalDataArray2.push(parseInt(v.amount));
                        });
                    }
                    if (withdrawData) {
                        $.each(withdrawData, function (i, v) {
                            normalxArray.push(v.time);
                            normalDataArray3.push(parseInt(v.amount));
                        });
                    }
                    hourOption.xAxis[0].data = normalxArray;
                    hourOption.series[0].data = normalDataArray;
                    hourOption.series[1].data = normalDataArray2;
                    hourOption.series[2].data = normalDataArray3;
                    createChart(id, hourOption);
                }
            }
        });
    }

    function getInvestData(id) {
        $.ajax({
            url: "${basePath}report/invest/increase?departmentId=${departmentId}",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalxArray = [];
                    var normalDataArray = [];
                    var normalDataArray2 = [];
                    var normalDataArray3 = [];
                    var huoData = data.huo;
                    $.each(huoData, function (i, v) {
                        normalxArray.push(v.time);
                        normalDataArray.push(v.money);
                    });

                    investOption.xAxis[0].data = normalxArray;
                    investOption.series[0].data = normalDataArray;
                    createChart(id, investOption);
                }
            }
        });
    }

    function getRechargeData(id) {
        $.ajax({
            url: "${basePath}report/recharge/increase?departmentId=${departmentId}",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalxArray = [];
                    var normalDataArray = [];
                    $.each(data, function (i, v) {
                        normalxArray.push(v.time);
                        normalDataArray.push(v.money);
                    });

                    rechargeOption.xAxis[0].data = normalxArray;
                    rechargeOption.series[0].data = normalDataArray;
                    createChart(id, rechargeOption);
                }
            }
        });
    }

    function getWithdrawData() {
        var option = {
            title: {
                text: "近一月提现"
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['提现金额']
            },
            toolbox: {
                show: true,
                feature: {
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: []
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} 元'
                    }
                }
            ],
            series: [
                {
                    name: '提现金额',
                    type: 'line',
                    data: [],
                    areaStyle: {normal: {}},
                    markPoint: {
                        data: []
                    }
                }
            ]
        };
        $.ajax({
            url: "${basePath}report/withdraw?departmentId=${departmentId}",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalxArray = [];
                    var normalDataArray = [];
                    $.each(data, function (i, v) {
                        normalxArray.push(v.time);
                        normalDataArray.push(v.money);
                    });
                    option.xAxis[0].data = normalxArray;
                    option.series[0].data = normalDataArray;
                    createChart('withdraw-line', option);
                }
            }
        });
    }
</script>
</body>
</html>