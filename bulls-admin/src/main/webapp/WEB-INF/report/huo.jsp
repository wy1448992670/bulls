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
    <title>活期/定期统计图</title>
    <link rel="stylesheet" href="${basePath}css/style.css">
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="row">
            <div class="col-md-6">
                <div class="widget-container fluid-height">
                    <div class="heading">
                        <i class="icon-bar-chart"></i>
                    </div>
                    <div class="widget-content padded text-center">
                        <div class="graph-container">
                            <div class="caption"></div>
                            <div id="huo-invest-pie" style="height: 300px;"></div>
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
                            <div id="AI-recharge-pie" style="height: 300px;"></div>
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
                            <div id="year-pie" style="height: 300px;"></div>
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
                            <div id="keepAmount" style="height: 300px;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered" id="allCapitalDetail">
                            <thead>
                            <tr>
                                <th>
                                    上线时长
                                </th>
                                <th>
                                    在投用户数
                                </th>
                                <th>
                                    实名用户数
                                </th>
                                <th>
                                    充值用户数
                                </th>
                                <th>
                                    真实注册用户总数
                                </th>
                                <th>
                                    投资用户占比
                                </th>
                                <th>
                                    人均充值额
                                </th>
                                <th>
                                    人均提现金额
                                </th>
                                <th>
                                    人均剩余金额
                                </th>
                                <th>
                                    充值总额
                                </th>
                                <th>
                                    提现总额
                                </th>
                                <th>
                                    提现/充值比例
                                </th>
                                <th>
                                    剩余总额
                                </th>
                                <th>
                                    日均充值
                                </th>
                                <th>
                                    日均提现
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    ${betweenDay }天
                                </td>
                                <th>
                                    ${huoCount }
                                </th>
                                <td>
                                    ${trueNameCount }
                                </td>
                                <th>
                                    ${huoRechargeCount }
                                </th>
                                <td>
                                    ${realUserCount }
                                </td>
                                <td>
                                    <fmt:formatNumber value="${huoCount/realUserCount*100 }" pattern="0.0#"/>%
                                </td>
                                <td>
                                    <c:if test="${(rechargeAmount - withdrawAmount)/realUserCount >= 10000 }">
                                        <fmt:formatNumber value="${rechargeAmount/realUserCount/10000 }"
                                                          pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${(rechargeAmount - withdrawAmount)/realUserCount < 10000 }">
                                        <fmt:formatNumber value="${rechargeAmount/realUserCount }" pattern="0.0#"/>元
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${withdrawAmount/realUserCount >= 10000 }">
                                        <fmt:formatNumber value="${withdrawAmount/realUserCount/10000 }"
                                                          pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${withdrawAmount/realUserCount < 10000 }">
                                        <fmt:formatNumber value="${withdrawAmount/realUserCount }" pattern="0.0#"/>元
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${(rechargeAmount - withdrawAmount)/realUserCount >= 10000 }">
                                        <fmt:formatNumber
                                                value="${(rechargeAmount - withdrawAmount)/realUserCount/10000 }"
                                                pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${(rechargeAmount - withdrawAmount)/realUserCount < 10000 }">
                                        <fmt:formatNumber value="${(rechargeAmount - withdrawAmount)/realUserCount }"
                                                          pattern="0.0#"/>元
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${rechargeAmount >= 10000 }">
                                        <fmt:formatNumber value="${rechargeAmount/10000 }" pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${rechargeAmount < 10000 }">
                                        ${rechargeAmount }元
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${withdrawAmount >= 10000 }">
                                        <fmt:formatNumber value="${withdrawAmount/10000 }" pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${withdrawAmount < 10000 }">
                                        ${withdrawAmount }元
                                    </c:if>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${withdrawAmount/rechargeAmount*100 }" pattern="0.0#"/>%
                                </td>
                                <td>
                                    <c:if test="${rechargeAmount - withdrawAmount  >= 10000 }">
                                        <fmt:formatNumber value="${(rechargeAmount - withdrawAmount)/10000 }"
                                                          pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${rechargeAmount - withdrawAmount  < 10000 }">
                                        ${rechargeAmount - withdrawAmount}元
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${rechargeAmount/betweenDay >= 10000 }">
                                        <fmt:formatNumber value="${rechargeAmount/betweenDay/10000 }" pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${rechargeAmount/betweenDay < 10000 }">
                                        <fmt:formatNumber value="${rechargeAmount/betweenDay }" pattern="0.0#"/>元
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${withdrawAmount/betweenDay >= 10000 }">
                                        <fmt:formatNumber value="${withdrawAmount/betweenDay/10000 }" pattern="0.0#"/>万元
                                    </c:if>
                                    <c:if test="${withdrawAmount/betweenDay < 10000 }">
                                        <fmt:formatNumber value="${ withdrawAmount/betweenDay }" pattern="0.0#"/>元
                                    </c:if>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${basePath}js/echart/echarts.common.min.js"></script>
<script type="text/javascript" src="${basePath}js/echart/macarons.js"></script>
<script type="text/javascript">
    $(function () {
        getHuoPie();
        getAndroidIOSPie();
        getYearPie();
        getKeepAmount();
    });

    function createChart(id, option) {
        var myChart = echarts.init(document.getElementById(id), 'macarons');
        myChart.setOption(option);
    }

    function getHuoPie() {
        var option = {
            title: {
                text: '活期用户投资金额比例图',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: ['50-100', '101-1000', '1001-2000', '2001-5000', '5001-1000', '10000以上']
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '投资金额',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%']
                }
            ]
        };
        $.ajax({
            url: "${basePath}report/huo/pie",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalDataArray = [];
                    $.each(data, function (i, v) {
                        normalDataArray.push({value: v.count, name: v.type});
                    });
                    option.series[0].data = normalDataArray;
                    createChart('huo-invest-pie', option);
                }
            }
        });

    }
    function getAndroidIOSPie() {
        var option = {
            title: {
                text: '安卓IOS充值金额比例图',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: ['android', 'IOS']
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '充值金额',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%']
                }
            ]
        };
        $.ajax({
            url: "${basePath}report/AIRecharge/pie",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalDataArray = [];
                    $.each(data, function (i, v) {
                        normalDataArray.push({value: v.count, name: v.type});
                    });
                    option.series[0].data = normalDataArray;
                    createChart('AI-recharge-pie', option);
                }
            }
        });
    }

    function getYearPie() {
        var option = {
            title: {
                text: '年龄阶层投资金额比例图',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: []
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel'],
                        option: {
                            funnel: {
                                x: '25%',
                                width: '50%',
                                funnelAlign: 'left',
                                max: 1548
                            }
                        }
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '投资金额',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%']
                }
            ]
        };
        $.ajax({
            url: "${basePath}report/huo/birthday",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalDataArray = [];
                    var legend = [];
                    $.each(data, function (i, v) {
                        normalDataArray.push({value: v.amount, name: v.year + "0后"});
                        legend.push(v.year + "0后");
                    });
                    option.series[0].data = normalDataArray;
                    option.legend.data =
                            createChart('year-pie', option);
                }
            }
        });
    }
    function getKeepAmount() {
        var option = {
            title: {
                text: '每月留存统计图'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['留存金额']
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
                    name: '留存金额',
                    type: 'line',
                    data: [],
                    markPoint: {
                        data: []
                    }
                }
            ]
        };
        $.ajax({
            url: "${basePath}report/huo/keepAmount",
            dataType: "json",
            success: function (data) {
                if (data) {
                    var normalxArray = [];
                    var normalDataArray = [];
                    $.each(data, function (i, v) {
                        normalxArray.push(v.type);
                        normalDataArray.push(v.count);
                    });
                    option.xAxis[0].data = normalxArray;
                    option.series[0].data = normalDataArray;
                    createChart('keepAmount', option);
                }
            }
        });
    }
</script>
</body>
</html>