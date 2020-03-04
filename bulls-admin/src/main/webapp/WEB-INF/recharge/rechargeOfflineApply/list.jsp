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
    <jsp:include page="../../common/header.jsp"></jsp:include>
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
                        <i class="icon-table"></i>线下充值申请列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <shiro:hasPermission name="recharge:rechargeOfflineApply:export">
                        <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="yyccheck()"
                           href="javascript:;"><i
                                class="icon-plus"></i>导出Excel</a>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="recharge:rechargeOfflineApply:add">
                            <a class="btn btn-sm btn-primary-outline pull-right" id="add-row"
                               href="${basePath}recharge/rechargeOfflineApply/add"><i class="icon-plus"></i>新建转账申请</a>
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
						if(startTime!="" && endTime!=""){
							if(startTime<=endTime){
                    			flag = true;
                    		}else{
                    			alert("结束时间必须大于等于开始时间");
                    			return;
                    		}
                    	}
						if(flag){
                    		location.href="${basePath}report/export/recharge?status="+status+"&startTime="+startTime+"&endTime="+endTime+"&client=${client}&code=${code}&payChannel="+payChannel+"&keyword="+keyword+"";
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
                                    <div class="form-group col-md-4">
                                        <div class="input-col4">
                                            <select class="select2able" name="state" id="state">
                                                <option value="" <c:if test="${state == null }">selected</c:if>>所有</option>
                                                <option value="0" <c:if test="${state == 0 }">selected</c:if>>申请中</option>
                                                <option value="1" <c:if test="${state == 1 }">selected</c:if>>审核通过</option>
                                                <option value="-1" <c:if test="${state == -1 }">selected</c:if>>审核不通过</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-md-4">
                                        <input class="form-control keyword" name="keyword" id="keyword" type="text"
                                               placeholder="客户名称/客户手机/申请人/发起人" value="${keyword }"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-4 col-xs-12">
                                        <input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime"
                                               type="text" placeholder="请选择申请起始时间"/>
                                    </div>
                                    <div class="form-group col-md-4 col-xs-12">
                                        <input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime"
                                               type="text" placeholder="请选择申请结束时间"/>
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
                                    <th width="50">ID</th>
                                    <th>申请人</th>
                                    <th width="100">申请时间</th>
                                    <th>发起人</th>
                                    <th>充值金额</th>
                                    <th>充值人id</th>
                                    <th>充值人手机</th>
                                    <th>充值人</th>
                                    <th>银行卡</th>
                                    <th>银行流水号</th>
                                    <th>审核人</th>
                                    <th>审核时间</th>
                                    <th>状态</th>
                                    <th>审核内容</th>
                                    <shiro:hasPermission name="recharge:rechargeOfflineApply:detail">
                                        <th>
                                            详情
                                        </th>
                                    </shiro:hasPermission>
                                    <th width="50">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${list }">
                                    <tr>
                                        <td>${i.id}</td>
                                        <td>${i.applyer_name}</td>
                                        <td><fmt:formatDate value="${i.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>${i.sourcer }</td>
                                        <td><fmt:formatNumber value="${i.money}"  pattern="#,##0.00" /></td>
                                        <td>${i.user_id }</td>
                                        <td>
                                            <shiro:lacksPermission name="user:adminPhone">
                                                ${i.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
                                            </shiro:lacksPermission>
                                            <shiro:hasPermission name="user:adminPhone">
                                                ${i.phone }
                                            </shiro:hasPermission>
                                        </td>
                                        <td>${i.true_name }</td>
                                        <td>${i.bankcard_num }</td>
                                        <td>${i.serial_number }</td>
                                        <td>${i.auditor_name }</td>
                                        <td><fmt:formatDate value="${i.audit_time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                        <td>
                                            <c:if test="${i.state == 0 }">
                                            	<span class="label label-warning">申请中</span>
                                            </c:if>
                                            <c:if test="${i.state == 1 }">
                                            	<span class="label label-success">审核通过</span>
                                            </c:if>
                                            <c:if test="${i.state == -1 }">
                                            	<span class="label label-danger">审核不通过</span>
                                            </c:if>
                                        </td>
                                        <td>${i.audit_remark }</td>
                                        <shiro:hasPermission name="recharge:rechargeOfflineApply:detail">
                                            <td>
                                                <%--<a href="${basePath}recharge/rechargeOfflineApply/detail?type=0&id=${i.id}">详情</a>--%>
                                                <a href="${basePath}recharge/rechargeOfflineApply/detail?id=${i.id}">详情</a>
                                            </td>
                                        </shiro:hasPermission>
                                        <td>
                                            <c:if test="${i.state == 0 || i.state == 1}">
                                                <shiro:hasPermission name="recharge:rechargeOfflineApply:edit">
                                                    <button class="btn btn-warning btn-xs edit" data-id="${i.id }" >编辑</button>
                                                </shiro:hasPermission>
                                                <c:if test="${i.state == 0 }">
                                                    <shiro:hasPermission name="recharge:rechargeOfflineApply:audit">
                                                        <button class="btn btn-primary btn-xs audit" data-loading-text="处理中..." data-id="${i.id }">审核</button>
                                                    </shiro:hasPermission>
                                                    <shiro:hasPermission name="recharge:rechargeOfflineApply:delete">
                                                        <button class="btn btn-danger btn-xs delete" data-id="${i.id }" >删除</button>
                                                    </shiro:hasPermission>
                                                </c:if>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
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
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">

    function aa() {

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
                return "list?keyword=${keyword}&state=${state}&endTime=${endTime}&startTime=${startTime}&page=" + page;
            }
        });

        $('.audit').click(function (e) {
            window.location.href='${basePath}recharge/rechargeOfflineApply/audit?id=' + $(this).attr('data-id');
            <%--window.location.href='${basePath}recharge/rechargeOfflineApply/audit?type=2&id=' + $(this).attr('data-id');--%>
        });

        $('.edit').click(function (e) {
            window.location.href='${basePath}recharge/rechargeOfflineApply/editLoading?id=' + $(this).attr('data-id');
            <%--window.location.href='${basePath}recharge/rechargeOfflineApply/detail?type=1&id=' + $(this).attr('data-id');--%>
        });

        $('.delete').click(function (e) {
            if (confirm("确认删除线下充值申请记录吗？！")) {
                $.ajax({
                    url: "${basePath}recharge/rechargeOfflineApply/delete",
                    type:"Post",
                    data: {
                        id: $(this).attr('data-id')
                    },
                    dataType:"json",
                    success:function(res){
                        if (res.code == 1) {
                            alert(res.msg)
                            window.location.href='${basePath}recharge/rechargeOfflineApply/list';
                        } else {
                            alert(res.msg)
                        }
                    }
                });
            }
        });
    });
</script>
</body>
</html>
