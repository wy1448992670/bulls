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
    <title>用户支付列表</title>
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
                支付管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户支付列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <shiro:hasPermission name="recharge:export">
                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()"
                           href="javascript:;"><i
                                class="icon-plus"></i>导出Excel</a>
                        </shiro:hasPermission>
                        <button class="btn btn-primary pull-right hidden-xs" type="button" onclick="aa()"> 搜索</button>
                    </div>
                    <script>
                    function yyccheck(){
                    	var flag = false;
                    	var status = $("#status").val();
                    	var payChannel = $("#payChannel").val();
                    	var keyword = $("#keyword").val();
                    	var client = $("#client").val();
                    	var code = $("#code").val();
                    	var startTime = $("#startTime").val();
                    	var endTime = $("#endTime").val();
                    	var departmentId = $("#departmentId").val();
						if(startTime!="" && endTime!=""){
							if(startTime<=endTime){
                    			flag = true;
                    		}else{
                    			alert("结束时间必须大于等于开始时间");
                    			return;
                    		}
                    	}
						if(flag){
                    		location.href="${basePath}report/export/recharge?status="+status+"&startTime="+startTime+"&endTime="+endTime+"&client=${client}&code=${code}&payChannel="+payChannel+"&keyword="+keyword+"&departmentId="+departmentId;
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
                                        <div class="input-col4">
                                            <select class="select2able" name="status" id="status">
                                                <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
                                                <option value="0" <c:if test="${status == 0 }">selected</c:if>>成功</option>
                                                <option value="1" <c:if test="${status == 1 }">selected</c:if>>处理中</option>
                                                <option value="2" <c:if test="${status == 2 }">selected</c:if>>失败</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <div class="input-col4">
                                            <select class="select2able" name="payChannel" id="payChannel">
                                                <option value="" <c:if test="${payChannel == null }">selected</c:if>>支付通道</option>
                                                <c:forEach var="item" items="${payChannels }">
                                                    <option value="${item.featureName}" <c:if test="${payChannel == item.featureName }">selected</c:if>>${item.description}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>


                                    <div class="form-group col-md-3 col-xs-12">
                                        <div class="input-col4">
                                            <select class="select2able" name="client" id="client">
                                                <option value="" <c:if test="${client == null }">selected</c:if>>客户来源 </option>
                                                <c:forEach var="item" items="${clients }">
                                                    <option value="${item.featureName}" <c:if test="${client == item.featureName }">selected</c:if>>${item.description}</option>
                                                </c:forEach>
                                            </select>
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
                                    <div class="form-group col-md-4">
                                        <input class="form-control keyword" name="keyword" id="keyword" type="text"
                                               placeholder="客户名称或支付订单号搜索" value="${keyword }"/>
                                    </div>

                                    <div class="form-group col-md-4 col-xs-12">
                                        <input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime"
                                               type="text" placeholder="请选择支付起始时间"/>
                                    </div>
                                    <div class="form-group col-md-4 col-xs-12">
                                        <input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime"
                                               type="text" placeholder="请选择支付结束时间"/>
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
                                    <th width="50">
                                        ID
                                    </th>
                                    <th>
                                        订单号
                                    </th>
                                    <th width="100">
                                        真实姓名
                                    </th>
                                    <th>
                                        手机号
                                    </th>
                                    <%--<th>--%>
                                    <%--渠道号--%>
                                    <%--</th>--%>
                                    <th width="80">
                                        银行
                                    </th>
                                    <th width="80">
                                        订单类型
                                    </th>
                                    <th>
                                        金额
                                    </th>
                                    <th>
                                        支付时间
                                    </th>
                                    <th width="50">
                                        终端
                                    </th>
                                    <th>
                                        支付通道
                                    </th>
                                    <th>
                                        状态
                                    </th>
                                    <shiro:hasPermission name="recharge:detail">
                                        <th width="50">详情</th>
                                    </shiro:hasPermission>
                                    <shiro:hasPermission name="recharge:fixed">
                                        <th width="50">
                                            操作
                                        </th>
                                    </shiro:hasPermission>
                                    <%--<th>
                                        归属坐席
                                    </th>--%>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${list }">
                                    <tr>
                                        <td>${i.id}</td>
                                        <td style="word-wrap: break-word;">
                                            支付订单:${i.order_no}
                                            <br />
                                            <c:if test="${i.pay_channel == 'offline'}">
                                                线下支付，无订单号
                                            </c:if>
                                            <c:if test="${i.pay_channel != 'offline'}">
                                                <c:if test="${i.order_type == 'investment'}">
                                                    物权订单:<a href="${basePath}investment/orderDetail?id=${i.other_id}">${i.businessOrderNo}</a>
                                                </c:if>
                                                <c:if test="${i.order_type == 'goods'}">
                                                    商城订单<a href="${basePath}shop/orderDetail?id=${i.other_id}">${i.businessOrderNo}</a>
                                                </c:if>
                                            </c:if>
                                        </td>
                                        <td>
                                            <a href="${basePath}user/detail/app?id=${i.user_id}">${i.trueName }</a>
                                        </td>
                                        <td>
                                            <shiro:lacksPermission name="user:adminPhone">
                                                ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                            </shiro:lacksPermission>
                                            <shiro:hasPermission
                                                    name="user:adminPhone">
                                                ${i.phone }
                                            </shiro:hasPermission>
                                        </td>
                                            <%--<td>--%>
                                            <%--${i.wxCode}--%>
                                            <%--</td>--%>
                                        <td>
                                            <c:if test="${null==i.bankName}">
                                                --
                                            </c:if>
                                            <c:if test="${null!=i.bankName}">
                                                ${i.bankName}
                                            </c:if>
                                        </td>
                                        <td style="word-wrap: break-word;">
                                            <c:forEach var="item" items="${orderTypes }">
                                                <c:if test="${i.order_type == item.featureName }">${item.description}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                                ${i.amount}元
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${i.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                                ${i.client}
                                        </td>
                                        <td>
                                            <c:forEach var="item" items="${payChannels }">
                                                <c:if test="${i.pay_channel == item.featureName }">${item.description}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td>
                                            <c:if test="${i.status == 0 }"><span
                                                    class="label label-success">成功</span></c:if>
                                            <c:if test="${i.status == 1 }"><span
                                                    class="label label-warning">处理中</span>
                                                <c:if test="${i.payChannel == 'fuioupay_quick' }"><span><a style="cursor:hand;" onclick="queryChargeResult(${i.id})">查询状态</a></span></c:if>
                                            </c:if>
                                            <c:if test="${i.status == 2 }"><span
                                                    class="label label-danger">失败</span></c:if>

                                        </td>
                                        <shiro:hasPermission name="recharge:detail">
                                            <td>
                                                <a href="${basePath}recharge/detail?id=${i.id}">详情</a>
                                            </td>
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="recharge:fixed">
                                            <td >
                                                <c:if test="${i.status == 1 }">

                                                    <%--<c:if test="${i.pay_channel == 'fuioupay_quick' || i.pay_channel == 'allinpay'}">
                                                        <a class="btn btn-warning btn-xs fix-on-line" rid="${i.id}">线上补单</a>
                                                    </c:if>--%>
                                                    <c:if test="${i.pay_channel == 'offline'}">
                                                        <a class="btn btn-danger btn-xs fix-under-line" rid="${i.id}">线下补单</a>
                                                    </c:if>
                                                    <c:if test="${i.pay_channel != 'offline'}">
                                                        --
                                                    </c:if>
                                                </c:if>
                                                <c:if test="${i.status != 1 }">
                                                    --
                                                </c:if>
                                            </td>
                                        </shiro:hasPermission>
                                            <%--<td>
                                                    ${i.cusName }
                                            </td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <ul id="pagination" style="float: right"></ul>
                        <h3 class="pull-left">充值成功总额:<span class="label label-success"><fmt:formatNumber
                                value="${totalAmount }" maxFractionDigits="2"></fmt:formatNumber></span></h3>

                        <h3 class="pull-left">充值人数:<span class="label label-success">${count }</span></h3>
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

    function queryChargeResult(id){
        $.ajax({
            url: '${basePath}recharge/queryChargeResult?id=' + id,
            dataType: 'json',
            method: 'post',
            success: function (data) {
                if (data != 'error') {
                    alert(data);
                    window.location.reload();
                } else {
                    alert('查询失败');
                }
            },
            error: function () {
                alert('查询失败');
            }, complete: function () {
            }
        });
    }
    function aa() {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        if(startTime!="" && endTime!=""){
            if(startTime > endTime){
                alert("结束时间必须大于等于开始时间");
                return;
            }
        }
        $("#form").submit();
    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        $('.select2able').select2();
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
                return "list?keyword=${keyword}&status=${status}&endTime=${endTime}&payChannel=${payChannel}&startTime=${startTime}&client=${client}&code=${code}&departmentId=${departmentId}&page=" + page;
            }
        });
        // $(".datepicker").datepicker({
        //     showSecond: true,
        //     timeFormat: "hh:mm:ss",
        //     dateFormat: "yy-mm-dd"
        // });
        // 线下补单
        $('.fix-under-line').click(function () {
            var $this = $(this);
            var id = $this.attr('rid');
            if (confirm("线下补单：一旦补单，将会增加用户账户余额，您确定这笔订单是掉单订单吗？")) {
                $.ajax({
                    url: '${basePath}recharge/fixByUnderLine?id=' + id,
                    dataType: 'json',
                    method: 'post',
                    beforeSend: function () {
                        $this.prop('disabled', true);
                    },
                    success: function (data) {
                        if (data == 'success') {
                            alert('补单成功');
                            window.location.reload();
                        } else if (data == 'error:auth')  {
                            alert('您没有权限操作');
                        }  else if (data == 'error:notAllowOption')  {
                            alert('订单类型不是充值单或充值方式不是线下充值或状态不是处理中');
                        } else {
                            alert('补单失败');
                        }
                    },
                    error: function () {
                        alert('补单失败');
                    }, complete: function () {
                        $this.prop('disabled', false);
                    }
                });
            }
        });
        $('.fix-on-line').click(function () {
            var $this = $(this);
            var id = $this.attr('rid');
            if (confirm("线上补单：一旦补单，将会增加用户账户余额，您确定这笔订单是掉单订单吗？")) {
                $.ajax({
                    url: '${basePath}recharge/fixByOnLine?id=' + id,
                    dataType: 'json',
                    method: 'post',
                    beforeSend: function () {
                        $this.prop('disabled', true);
                    },
                    success: function (data) {
                        if (data == 'success') {
                            alert('补单成功');
                            window.location.reload();
                        } else if (data == 'error:refuse')  {
                            alert('您没有权限操作');
                        } else {
                            alert('补单失败');
                        }
                    },
                    error: function () {
                        alert('补单失败');
                    }, complete: function () {
                        $this.prop('disabled', false);
                    }
                });
            }
        });
    });
</script>
</body>
</html>
