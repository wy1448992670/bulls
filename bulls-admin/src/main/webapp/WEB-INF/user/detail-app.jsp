<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户详细信息</title>
<style type="text/css">
strong.money {
	color: #007aff;
	font-size: 18px;
}

.qaCs {
	font-weight: bold;
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
				<h1>用户管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-6">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							基础信息
						</div>
						<div class="widget-content padded clearfix">
							<form method="post" class="form-horizontal" id="validate-form">
								<div class="form-group">
									<label class="control-label col-md-2"></label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${user.avatar == null }">
												<img alt="" src="${aPath}upload/login.png" />
											</c:if>
											<c:if test="${user.avatar != null }">
												<img alt="" src="${aPath}upload/${user.avatar }" width="200px;" />
											</c:if>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">用户名</label>

									<div class="col-md-7">
										<p class="form-control-static">${user.username }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">Token</label>

									<div class="col-md-7">
										<p class="form-control-static">${user.token }</p>
									</div>
								</div>

								<%--  <div class="form-group">
                                <label class="control-label col-md-2">新浪支付密码</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${user.hasSinaPayPassword == 0}">未设置</c:if>
                                        <c:if test="${user.hasSinaPayPassword == 1}">已设置</c:if>
                                    </p>
                                </div>
                            </div>
 --%>
								<div class="form-group">
									<label class="control-label col-md-2">使用版本</label>

									<div class="col-md-7">
										<p class="form-control-static">${user.appVersion}</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">手机号</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<shiro:lacksPermission name="user:adminPhone">
                                            ${user.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                        </shiro:lacksPermission>
											<shiro:hasPermission name="user:adminPhone">
                                            ${user.phone }
                                        </shiro:hasPermission>
										</p>
									</div>
								</div>
								
								<div class="form-group">
									<label class="control-label col-md-2">创建渠道</label>

									<div class="col-md-7">
										<p class="form-control-static">${channel.channelName }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">
										<b>数据来源</b>
									</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${user.dataSource == '0'}" >平台</c:if>
											<c:if test="${user.dataSource == '1' }">亿亿理财</c:if>
											<c:if test="${user.dataSource == '2' }">阿里分发市场</c:if>
											<c:if test="${user.dataSource == '3' }">应用宝</c:if>
											<c:if test="${user.dataSource == '4' }">华为应用市场</c:if>
											<c:if test="${user.dataSource == '5' }">vivo应用商店</c:if>
											<c:if test="${user.dataSource == '6' }">oppo</c:if>
											<c:if test="${user.dataSource == '7' }">魅族</c:if>
											<c:if test="${user.dataSource == '8' }">联通wo</c:if>
											<c:if test="${user.dataSource == '9' }">联想 </c:if>
											<c:if test="${user.dataSource == '10' }">百度</c:if>
											<c:if test="${user.dataSource == '11' }">小米</c:if>
											<c:if test="${user.dataSource == '12' }">360手机助手</c:if>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">邮箱</label>

									<div class="col-md-7">
										<p class="form-control-static">${user.email }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">性别</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${user.sex == 0 }">女</c:if>
											<c:if test="${user.sex == 1 }">男</c:if>
											<c:if test="${user.sex == 2 }">保密</c:if>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">生日</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${user.birthday }" pattern="yyyy-MM-dd" />
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">真实姓名</label>

									<div class="col-md-7">
										<p class="form-control-static">${user.trueName }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">身份证</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<shiro:lacksPermission name="user:adminPhone">
                                            ${user.identityCard.replaceAll("(\\d{6})\\d{5}(\\w{4})","$1*****$2")}
                                        </shiro:lacksPermission>
											<shiro:hasPermission name="user:adminPhone">
                                            ${user.identityCard }
                                        </shiro:hasPermission>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">户籍所在地</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<shiro:hasPermission name="user:adminPhone">
												${user.idAddress }
											</shiro:hasPermission>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">用户风险等级</label>
									<div class="col-md-7">
										<p class="form-control-static">${userRisk}</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">测评时间</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${user.riskEvaluateTime != null}">
                                            ${user.riskEvaluateTime}
                                        </c:if>
											<c:if test="${user.riskEvaluateTime == null}">
                                            -
                                        </c:if>
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">注册时间</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${user.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">注册IP</label>

									<div class="col-md-7">
										<p class="form-control-static" id="registerIp">${user.registerIp }</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">最后登入时间</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<fmt:formatDate value="${user.lastLoginTime }" pattern="yyyy-MM-dd HH:mm:ss" />
										</p>
									</div>
								</div>

								<div class="form-group">
									<label class="control-label col-md-2">最后登入IP</label>

									<div class="col-md-7">
										<p class="form-control-static" id="lastLoginIp">${user.lastLoginIp }</p>
									</div>
								</div>

								<%-- <div class="form-group">
                                <label class="control-label col-md-2">更新时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${user.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div> --%>


								<div class="form-group">
									<label class="control-label col-md-2">会员等级</label>
									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${user.level == 0 }">普通用户</c:if>
											<c:if test="${user.level == 1 }">会员用户</c:if>
											<c:if test="${user.level == 2 }">vip用户</c:if>
										</p>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-md-2">状态</label>

									<div class="col-md-7">
										<p class="form-control-static">
											<c:if test="${user.status == 0 }">
												<span class="label label-success">可用</span>
											</c:if>
											<c:if test="${user.status == 1 }">
												<span class="label label-warning">不可用</span>
											</c:if>
											<c:if test="${user.status == 2 }">
												<span class="label label-danger">已删除</span>
											</c:if>
											<c:if test="${user.status == 3 }">
												<span class="label label-danger">测试帐号</span>
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
							<i class="icon-table"></i>
							资产详细信息
						</div>
						<div class="widget-content padded clearfix">
							<form method="post" class="form-horizontal">
								<div class="widget-content padded clearfix">
									<div class="table-responsive">
										<div class="table-responsive">
											<table class="table table-bordered">
												<tr>
													<td>总资产</td>
													<td colspan="2">
														<strong class="money">
															<fmt:formatNumber value="${totalAmount }"></fmt:formatNumber>
														</strong>
														元
													</td>
													<td>累计利润</td>
													<td colspan="2">
														<strong class="money">
															<fmt:formatNumber value="${interestAmount}"></fmt:formatNumber>
														</strong>
														元
													</td>
												</tr>
												<tr>
													<td>可用余额</td>
													<td colspan="2">
														<strong class="money">
															<fmt:formatNumber value="${asset.balanceAmount }"></fmt:formatNumber>
														</strong>
														元
													</td>
													<td>冻结金额</td>
													<td colspan="2">
														<strong class="money">
															<fmt:formatNumber value="${asset.frozenAmount }"></fmt:formatNumber>
														</strong>
														元
													</td>
												</tr>
												<tr>
													<td>授信金额</td>
													<td colspan="2">
														<strong class="money">
															<fmt:formatNumber value="${asset.creditAmount }"></fmt:formatNumber>
														</strong>
														元
													</td>
													<td>授信冻结金额</td>
													<td colspan="2">
														<strong class="money">
															<fmt:formatNumber value="${asset.freozenCreditAmount }"></fmt:formatNumber>
														</strong>
														元
													</td>
												</tr>
											</table>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>

					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							物权订单列表 【
							<a id="qa1" href="javascript:showOrder(1, 1);">饲养中</a>
							|
							<a id="qa2" href="javascript:showOrder(2, 1);">已卖出</a>
							】
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered" id="orders">
									<thead>
										<tr>
											<th>订单号</th>
											<th>投资金额(元)</th>
											<th>加息总金额(元)</th>
											<th>投资时间</th>
											<th>投资期限(天)</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="text-right">
								<ul id="orders_pagination"></ul>
							</div>
						</div>
					</div>

					<!-- 商城订单数据 -->
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							商城订单列表 【
							<a id="qb0" href="javascript:showShopOrder(0, 1);">未支付</a>
							|
							<a id="qb2" href="javascript:showShopOrder(2, 1);">已支付</a>
							|
							<a id="qb3" href="javascript:showShopOrder(4, 1);">已发货</a>
							】
						</div>
						<div class="widget-content padded clearfix">
							<div class="table-responsive">
								<table class="table table-bordered" id="shopOrders">
									<thead>
										<tr>
											<th>订单号</th>
											<th>订单金额(元)</th>
											<th>实际支付金额(元)</th>
											<th>余额支付金额(元)</th>
											<th>授信支付金额(元)</th>
											<th>购买时间</th>
										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="text-right">
								<ul id="shopOrders_pagination"></ul>
							</div>
						</div>
					</div>

					<!-- 有权限才显示用户银行卡信息 -->
					<c:set var="hasBankCardPerm" value="false"></c:set>
					<shiro:hasPermission name="user:bank:card">
						<c:set var="hasBankCardPerm" value="true"></c:set>
						<div class="widget-container fluid-height clearfix">
							<div class="heading">
								<i class="icon-table"></i>
								银行卡信息
							</div>
							<div class="widget-content padded clearfix">
								<div class="table-responsive">
									<table class="table table-bordered" id="bankCard">
										<thead>
											<tr>
												<th>卡ID</th>
												<th>预留手机号</th>
												<th>银行</th>
												<th>卡号</th>
											</tr>
										</thead>
										<tbody>
										</tbody>
									</table>
								</div>
								<div class="text-right">
									<ul id="bankCard_pagination"></ul>
								</div>
							</div>
						</div>
					</shiro:hasPermission>

					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							交易记录
							<%--  <a class="btn btn-sm btn-primary-outline pull-right"
                           href="${basePath}report/export/trade?userId=${user.id}" id="export-trade"><i
                                class="icon-plus"></i>导出Excel</a> --%>
						</div>
						<div class="widget-content padded clearfix">
							<jsp:include page="../common/tradeRecordFromDetail.jsp" flush="true">
								<jsp:param name="userId" value="${user.id}" />
								<jsp:param name="limit" value="5" />
							</jsp:include>
							<div class="text-right">
								<ul id="trade_pagination"></ul>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>

	<script src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script type="text/javascript">
    $(function () {
    	if("${error}"!=""){
        	alert("${error}");
        	return false;
        }
        // showTradeDetail(1);
        showOrder(1, 1);
        showShopOrder(0, 1);
        // 智投
        showMonthlyGainOrder(0,1);

        var res = ${hasBankCardPerm};
        if (res) {
        	showBankCard(1);
        }
        //  getIp();
    });

    function getIp() {
        $.getJSON('${basePath}ipInfo?ip=${user.lastLoginIp}', null, function (data) {
            $('#lastLoginIp').append('(' + data.region + '/' + data.city + '/' + data.isp + ')');
        });

        $.getJSON('${basePath}ipInfo?ip=${user.registerIp}', null, function (data) {
            $('#registerIp').append('(' + data.region + '/' + data.city + '/' + data.isp + ')');
        });
    }

    function getBankCard() {
        $.getJSON('${basePath}user/bank/card?userId=${user.id}', null, function (data) {
            var sina1 = data.sinaCardId;
            if (!sina1) {
                sina1 = '未绑定';
            }
            var sina2 = data.sinaWithdrawCardId;
            if (!sina2) {
                sina2 = '未绑定';
            }

            var tr = "<tr><td>" + data.id + "</td><td>" + data.phone.substring(0,3)+"****"+data.phone.substring(data.phone.length-4,data.phone.length)+ "</td><td>" + data.bank.name + "</td><td>" + data.cardNumber.substring(0,4)+"***********"+data.cardNumber.substring(data.cardNumber.length-4,data.cardNumber.length) + "</td></tr>";
            $("#bankCard tbody").html(tr);
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

    //物权订单列表
    var index = 0;
    function showOrder(status, page) {
    	$("#qa"+index).removeClass("qaCs");
    	$("#qa"+status).addClass("qaCs");
    	index= status;
        $.ajax({
            url: "${basePath}investment/listOrderAjax",
            type: "get",
            dataType: "json",
            data: "page=" + page + "&orderStatus="+status + "&userId=${user.id}",
            success: function (obj) {
                $("#orders tbody").empty();
                for (var i = 0; i < obj.list.length; i++) {
                	var order = obj.list[i];
                    var tr = "<tr><td>"+order.orderNo+"</td><td>"+isundefined(order.amount)+"</td><td>"+isundefined(order.addInterest)+"</td><td>"+new Date(order.createDate).format('yyyy-MM-dd hh:mm:ss')+"</td><td>"+order.limitDays+"</td></tr>";
                    $("#orders tbody").append(tr);
                }
                if (obj.pages > 0) {
                    $('#orders_pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showOrder("+obj.status+"," + page + ")";
                        }
                    });
                }
            }
        });
    }

    function isundefined(v){
    	return v==undefined?0:v;
    }

    //商城订单列表
    var index = 0;
    function showShopOrder(status, page) {
    	$("#qb"+index).removeClass("qaCs");
    	$("#qb"+status).addClass("qaCs");
    	index= status;
        $.ajax({
            url: "${basePath}shop/listOrderAjax",
            type: "get",
            dataType: "json",
            data: "page=" + page + "&orderStatus="+status + "&userId=${user.id}",
            success: function (obj) {
                $("#shopOrders tbody").empty();
                for (var i = 0; i < obj.list.length; i++) {
                	var order = obj.list[i];
                    var tr = "<tr><td>"+order.order_no+"</td><td>"+isundefined(order.total_money)+"</td><td>"+isundefined(order.real_pay_money)+"</td><td>"+isundefined(order.balance_pay_money)+"</td><td>"+isundefined(order.credit_pay_money)+"</td><td>"+new Date(order.create_date).format('yyyy-MM-dd hh:mm:ss')+"</td></tr>";
                    $("#shopOrders tbody").append(tr);
                }
                if (obj.pages > 0) {
                    $('#shopOrders_pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showShopOrder("+obj.status+"," + page + ")";
                        }
                    });
                }
            }
        });
    }

    //银行卡信息
    function showBankCard(page) {
        $.ajax({
            url: "${basePath}user/listBankAjax",
            type: "get",
            dataType: "json",
            data: "page=" + page + "&userId=${user.id}",
            success: function (obj) {
                $("#bankCard tbody").empty();
                for (var i = 0; i < obj.list.length; i++) {
                	var bankCard = obj.list[i];
                    var tr = "<tr><td>"+bankCard.id+"</td><td>"+bankCard.phone+"</td><td>"+bankCard.name+"</td><td>"+bankCard.card_number+"</td></tr>";
                    $("#bankCard tbody").append(tr);
                }
                if (obj.pages > 0) {
                    $('#bankCard_pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showBankCard(" + page + ")";
                        }
                    });
                }
            }
        });
    }

    // 智投数据列表
    function showMonthlyGainOrder(status,page) {
    	// 先全部清除样式
    	$(".mg_a").removeClass("qaCs");
    	// 后追加样式
    	$("#mg"+status).addClass("qaCs");
        $.ajax({
            url: "${basePath}investment/monthlyGainProjectList",
            type: "get",
            dataType: "json",
            data: "page=" + page + "&userId=${user.id}&status="+status,
            success: function (obj) {
            	console.info(obj);
            	// 标题栏更换
            	$(".mg_tr").hide();
            	$("#mg_tr_"+status).show();
            	// 清空内容显示区域
                $("#mgOrders tbody").empty();
            	// 内容元素填充
                for (var i = 0; i < obj.list.length; i++) {
                	var order = obj.list[i];
                    var tr;
                    if(status==0){
                    	// 持有中
                    	tr = inHavingItems(order);
                    }else if(status==1){
                    	// 退出中
                    	tr = abortingItems(order);
                    }else if(status==2){
                    	// 已退出
                    	tr = exitedItems(order);
                    }
                    $("#mgOrders tbody").append(tr);
                }
                if (obj.pages > 0) {
                    $('#mg_orders_pagination').bootstrapPaginator({
                        currentPage: parseInt(obj.page),
                        totalPages: parseInt(obj.pages),
                        bootstrapMajorVersion: 3,
                        alignment: "right",
                        pageUrl: function (type, page, current, limit) {
                            return "javascript:showMonthlyGainOrder("+obj.status+","+ page + ")";
                        }
                    });
                }
            }
        });
    }

    // 持有中
    function inHavingItems(obj){
    	var html = '<tr>';
    	// 订单号
    	html +='<td>'+obj.investmentId+'</td>';
    	// 智投项目
    	html +='<td>'+obj.productName+'</td>';
    	// 年化利率
    	var productRate = parseFloat(obj.productRate);
    	productRate = Math.floor(productRate * 10000) / 100;
    	html +='<td>'+productRate+'%</td>';
    	// 授权服务期
    	html +='<td>'+obj.limitDays+'</td>';
    	// 持有天数
    	var holdDays = obj.holdDays-1;
    	html +='<td>'+holdDays+'</td>';
    	// 出借时间
    	var time = obj.time;
    	time = formatDateTime(time);
    	html +='<td>'+time+'</td>';
    	// 出借金额
    	html +='<td>'+obj.amount+'</td>';
    	// 预期收益
    	var expectEarnings;

    	if(obj.isQuit==1){
    		// 可退出
    		expectEarnings = Math.floor(obj.amount*obj.productRate/365*holdDays*100)/100;
    	}else{
    		// 不可退出
    		expectEarnings = Math.floor(obj.amount*obj.productRate/365*obj.limitDays*100)/100;
    	}
    	html +='<td>'+expectEarnings+'</td>';
    	// 是否可退出
    	var isCan;
    	if(obj.isQuit==1){
    		isCan='可退出';
    	}else{
    		isCan='不可退出';
    	}
    	html +='<td>'+isCan+'</td>';
    	html +='</tr>';
    	return html;
    }

    // 退出中
    function abortingItems(obj){
    	var html = '<tr>';
    	// 订单号
    	html +='<td>'+obj.investmentId+'</td>';
    	// 智投项目
    	html +='<td>'+obj.productName+'</td>';
    	// 年化利率
    	var productRate = parseFloat(obj.productRate);
    	productRate = Math.floor(productRate * 10000) / 100;
    	html +='<td>'+productRate+'%</td>';
    	// 授权服务期
    	html +='<td>'+obj.limitDays+'</td>';
    	// 出借金额
    	html +='<td>'+obj.amount+'</td>';
    	// 出借时间
    	var time = obj.time;
    	time = formatDateTime(time);
    	html +='<td>'+time+'</td>';
    	// 申请退出时间
    	var applyQuitDate = obj.applyQuitDate;
    	applyQuitDate = formatDateTime(applyQuitDate);
    	html +='<td>'+applyQuitDate+'</td>';
    	// 已退出本金
    	var returnAmount = obj.returnAmount;
    	html +='<td>'+returnAmount+'</td>';
    	// 待退出本金
    	var interestUsableAmount = obj.interestUsableAmount;
    	html +='<td>'+interestUsableAmount+'</td>';
    	html +='</tr>';
    	return html;
    }

    // 已退出
    function exitedItems(obj){
    	var html = '<tr>';
    	// 订单号
    	html +='<td>'+obj.investmentId+'</td>';
    	// 智投项目
    	html +='<td>'+obj.productName+'</td>';
    	// 年化利率
    	var productRate = parseFloat(obj.productRate);
    	productRate = Math.floor(productRate * 10000) / 100;
    	html +='<td>'+productRate+'%</td>';
    	// 授权服务期
    	html +='<td>'+obj.limitDays+'</td>';
    	// 出借金额
    	html +='<td>'+obj.amount+'</td>';
    	// 出借时间
    	var time = obj.time;
    	time = formatDateTime(time);
    	html +='<td>'+time+'</td>';
    	// 申请退出时间
    	var applyQuitDate = obj.applyQuitDate;
    	applyQuitDate = formatDateTime(applyQuitDate);
    	html +='<td>'+applyQuitDate+'</td>';
    	// 成功退出时间
    	var quitDate = obj.quitDate;
    	quitDate = formatDateTime(quitDate);
    	html +='<td>'+quitDate+'</td>';
    	// 累计收益
    	var totalEarnings = obj.totalEarnings;
    	html +='<td>'+totalEarnings+'</td>';
    	return html;
    }

    // 时间戳转日期
    function formatDateTime(inputTime) {
	    var date = new Date(inputTime);
	    var y = date.getFullYear();
	    var m = date.getMonth() + 1;
	    m = m < 10 ? ('0' + m) : m;
	    var d = date.getDate();
	    d = d < 10 ? ('0' + d) : d;
	    var h = date.getHours();
	    h = h < 10 ? ('0' + h) : h;
	    var minute = date.getMinutes();
	    var second = date.getSeconds();
	    minute = minute < 10 ? ('0' + minute) : minute;
	    second = second < 10 ? ('0' + second) : second;
	    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
    }

</script>
</body>
</html>
