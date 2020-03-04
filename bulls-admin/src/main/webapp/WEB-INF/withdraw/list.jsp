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
    <title>用户提现列表</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/style.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<!-- 模态框（Modal） -->
<div class="modal fade" id="auditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					提现审核
				</h4>
			</div>
			<div id="text"></div>
			<div class="modal-body">
				<textarea id="remark" class="form-control" placeholder="请输入拒绝原因(不超过20个字)"></textarea>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" 
						data-dismiss="modal">关闭
				</button>
				<button type="button" class="btn btn-primary" onclick="refuse()">
					拒绝提现
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                提现管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
        	<input type="hidden" id="aid" />
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户提现列表
                        <img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;">
                        <shiro:hasPermission name="withdraw:export">
                            <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id=""
                               href="javascript:;" onclick="yyccheck()"><i
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
                    	var createStartTime = $("#createStartTime").val();
                    	var createEndTime = $("#createEndTime").val();
                    	var startTime = $("#startTime").val();
                    	var endTime = $("#endTime").val();
                    	var departmentId = $("#departmentId").val();
                    	if(createStartTime!="" && createEndTime!=""){
                    		if(createStartTime<=createEndTime){
                    			flag = true;
                    		}else{
                    			alert("申请提现结束时间必须大于等于申请提现开始时间");
                    			return;
                    		}
                    	}
						if(startTime!="" && endTime!=""){
							if(startTime<=endTime){
                    			flag = true;
                    		}else{
                    			alert("提现成功结束时间必须大于等于提现成功开始时间");
                    			return;
                    		}
                    	}
						if(flag){
                    		location.href="${basePath}report/export/withdraw?status="+status+"&startTime="+startTime
                    				+"&endTime="+endTime+"&createEndTime="+createEndTime+"&createStartTime="+createStartTime
                    				+"&type=${type}&payChannel="+payChannel+"&keyword="+keyword+"&departmentId="+departmentId;
                    	}else{
                    		alert("申请提现时间或提现成功时间区间为必选项");
                    	}
                    }
                    </script>
                    <div class="widget-content padded clearfix">
                        <div id="myModal">
                            <form class="search1 AppFixed form-inline col-lg-5 pull-right col-xs-11" id="form" action="list">
                                <input type="hidden" name="page" value="1"/>

                                <div class="row">
                                    <div class="form-group col-md-3">
                                        <div>
                                            <select class="select2able" name="status" id="status">
                                                <option value="" <c:if test="${status == null }">selected</c:if>>所有</option>
                                                <c:forEach var="item" items="${withdrawStatus }">
                                                    <option value="${item.code}" <c:if test="${status == item.code }">selected</c:if>>${item.description}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-3">
                                        <div>
                                            <select class="select2able" name="payChannel" id="payChannel">
                                                <option value="" <c:if test="${payChannel == null }">selected</c:if>>代付通道</option>
                                                <c:forEach var="item" items="${payChannels }">
                                                    <option value="${item.featureName}" <c:if test="${payChannel == item.featureName }">selected</c:if>>${item.description}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-3 col-xs-12">
                                        <div>
                                            <input class="form-control keyword" name="keyword" id="keyword" type="text"
                                                   placeholder="客户名称或订单号搜索" value="${keyword }"/>
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
                                    <div class="form-group col-md-6 col-xs-12">
                                        <input class="form-control datepicker" value="${createStartTime }"
                                               name="createStartTime" id="createStartTime"
                                               type="text" placeholder="请选择申请提现起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6 col-xs-12">
                                        <input class="form-control datepicker" value="${createEndTime }"
                                               name="createEndTime" id="createEndTime"
                                               type="text" placeholder="请选择申请提现结束时间"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6 col-xs-12">
                                        <input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime"
                                               type="text" placeholder="请选择提现成功起始时间"/>
                                    </div>
                                    <div class="form-group col-md-6 col-xs-12">
                                        <input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime"
                                               type="text" placeholder="请选择提现成功结束时间"/>
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
                                    <th>ID</th>
                                    <th>提现订单号</th>
                                    <th>真实姓名</th>
                                    <th>代付通道</th>
                                    <th>提现金额</th>
                                    <th>实际金额</th>
                                    <th>申请时间</th>
                                    <th>成功时间</th>
                                    <th>卡号</th>
                                    <c:if test="${type == 0 }">
                                        <th>
                                            技术审核人
                                        </th>
                                        <th>
                                            技术审核时间
                                        </th>
                                        <th>
                                            财务复审人
                                        </th>
                                        <th>
                                            财务复审时间
                                        </th>
                                    </c:if>
                                    <th>状态</th>
                                    <c:if test="${type == 1 }">
                                        <th>
                                            备注
                                        </th>
                                    </c:if>
                                    <shiro:hasPermission name="withdraw:detail">
                                        <th>详情</th>
                                    </shiro:hasPermission>
                                    <th>
                                        操作
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="i" items="${list }">
                                    <tr>
                                        <td>${i.id }</td>
                                        <td>${i.order_no }</td>
                                        <td>
                                            <a href="${basePath}user/detail/app?id=${i.user_id}">${i.true_name }</a>
                                        </td>
                                        <td>
                                            <c:forEach var="item" items="${payChannels }">
                                                <c:if test="${i.pay_channel == item.featureName }">${item.description}</c:if>
                                            </c:forEach>
                                        </td>
                                        <td>${i.amount}元</td>
                                        <td>${i.real_amount}元</td>
                                        <td>
                                            <fmt:formatDate value="${i.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value="${i.successTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                        </td>
                                        <td>
                                            <shiro:lacksPermission name="user:adminPhone">
                                                ${i.card_no.replaceAll("(\\d{4})\\d{5}(\\d{4})","$1*****$2")}
                                            </shiro:lacksPermission>
                                            <shiro:hasPermission name="user:adminPhone">
                                                ${i.card_no }
                                            </shiro:hasPermission> (${i.bankName })
                                        </td>
                                        <c:if test="${type == 0 }">
                                            <td>
                                                技术审核人
                                            </td>
                                            <td>
                                                技术审核时间
                                            </td>
                                            <td>
                                                财务复审人
                                            </td>
                                            <td>
                                                财务复审时间
                                            </td>
                                        </c:if>
                                        <td>
                                            <c:if test="${i.status == 0 }"><span
                                                    class="label label-warning">待审核</span></c:if>
                                            <c:if test="${i.status == 1 }"><span
                                                    class="label label-success">提现成功</span></c:if>
                                            <c:if test="${i.status == 2 }"><span
                                                    class="label label-danger">提现失败</span></c:if>
                                            <c:if test="${i.status == 3 }"><span
                                                    class="label label-default">取消</span></c:if>
                                            <c:if test="${i.status == 4 }"><span
                                                    class="label label-info">银行处理中</span></c:if>
                                            <c:if test="${i.status == 5 }"><span
                                                    class="label label-inverse">挂起(状态未知）</span></c:if>
                                            <c:if test="${i.status == 6 }"><span
                                                    class="label label-danger">拒绝提现</span></c:if>
                                        </td>
                                        <c:if test="${type == 1 }">
                                            <td>
                                                    ${i.tech_remark}
                                            </td>
                                        </c:if>
                                        <shiro:hasPermission name="withdraw:detail">
                                            <td><a class="edit-row" href="detail?id=${i.id }">详情</a></td>
                                        </shiro:hasPermission>
                                            <%--<td><a class="edit-row" href="${basePath}withdraw/detail?id=${i.id }">详情</a></td>--%>

                                        <td>
                                            <c:if test="${i.status==0 }">
                                            	<shiro:hasPermission name="withdraw:audit">
	                                            	<button class="btn btn-primary btn-xs audit"
	                                                    data-loading-text="处理中..." withdrawid="${i.id }">审核
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="withdraw:reject">
	                                                <button class="btn btn-danger btn-xs"
	                                                    data-loading-text="处理中..." withdrawid="${i.id }" onclick="showRefuse('${i.id}', this)" data-toggle="modal" data-target="#auditModal">拒绝
	                                                </button>
                                                </shiro:hasPermission>
                                            </c:if>
                                            <c:if test="${i.status==4 }">
                                                <shiro:hasPermission name="withdraw:reject">
	                                                <button class="btn btn-danger btn-xs reject"
	                                                    data-loading-text="处理中..." withdrawid="${i.id }">驳回
	                                                </button>
                                                </shiro:hasPermission>
                                                <shiro:hasPermission name="withdraw:supplement">
	                                                <button class="btn btn-danger btn-xs bu"
	                                                    data-loading-text="处理中..." withdrawid="${i.id }">补单
	                                                </button>
                                                </shiro:hasPermission>
                                            </c:if>
                                        </td>
                                            <%--<td>--%>
                                            <%--${i.cusName }--%>
                                            <%--</td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <ul id="pagination" style="float: right"></ul>
                        <h3 class="pull-left">提现总额:
                            <span class="label label-success">
                                <fmt:formatNumber value="${totalAmount }" maxFractionDigits="2"></fmt:formatNumber>
                            </span>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript">
    function aa() {
        $("#form").submit();
    }
    
    function showRefuse(id, o) {
    	$("#text").empty();
    	var sb = new Array();
    	sb.push("<table class='table table-striped table-hover'>");
    	sb.push("<tr align='center'><td>ID:</td><td>"+$(o).parent().parent().find("td").eq(0).html()+"</td></tr>");
    	sb.push("<tr align='center'><td>提现订单号:</td><td>"+$(o).parent().parent().find("td").eq(1).html()+"</td></tr>");
    	sb.push("<tr align='center'><td>真实姓名: </td><td>"+$(o).parent().parent().find("td").eq(2).find("a").html()+"</td></tr>");
    	sb.push("<tr align='center'><td>提现金额: </td><td>"+$(o).parent().parent().find("td").eq(4).html()+"</td></tr>");
    	sb.push("<tr align='center'><td>实际金额	:</td><td>"+$(o).parent().parent().find("td").eq(5).html()+"</td></tr>");
    	sb.push("<tr align='center'><td>申请时间:</td><td>"+$(o).parent().parent().find("td").eq(6).html()+"</td></tr>");
    	sb.push("</table>");
    	$("#text").append(sb.join(""));
    	$('#aid').val(id); 
    }
    
    function refuse() {
    	var id = $('#aid').val();
    	var remark = $('#remark').val();
        if (confirm("您确定拒绝该次提现吗？")) {
        	$.ajax({
                url: '${basePath}withdraw/refuse?id=' + id + '&remark=' + remark,
                type: 'post',
                success: function () {
                    alert("拒绝成功，已发送短信至该用户。");
                    window.location.reload();
                },
                error: function () {
                    alert('请求失败，请重试');
                },
            });
        }
    };

    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        $('.select2able').select2();
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "list?keyword=${keyword}&status=${status}&type=${type}&endTime=${endTime}"
                		+"&startTime=${startTime}&createEndTime=${createEndTime}&createStartTime="
                		+"${createStartTime}&method=${method}&departmentId=${departmentId}&page=" + page;
            }
        });

        $('.audit').click(function (e) {
            var $this = $(this);
            //window.location.href='${basePath}withdraw/audit?id=' + $(this).attr('withdrawid');
            if (confirm("您确定通过该次提现吗？")) {
                $.ajax({
                    url: '${basePath}withdraw/auditBankProcess?id=' + $(this).attr('withdrawid'),
                    type: 'post',
                    beforeSend: function () {
                        $this.button('loading');
                    },
                    success: function () {
                        alert("审核成功!");
                        window.location.reload();
                    },
                    error: function () {
                        alert('请求失败，请重试');
                    },
                    complete: function () {
                        $this.button('reset');
                    }
                });
            }
        });

        $('.reject').click(function (e) {
            var $this = $(this);
            if (confirm("您确定该次提现失败，并返还金额到该用户账户吗？该次操作不可逆转，请谨慎！")) {
                $.ajax({
                    url: '${basePath}withdraw/reject?id=' + $(this).attr('withdrawid'),
                    type: 'post',
                    beforeSend: function () {
                        $this.button('loading');
                    },
                    success: function (data) {
                        alert(data);
                        window.location.reload();
                    },
                    error: function () {
                        alert('请求失败，请重试');
                    },
                    complete: function () {
                        $this.button('reset');
                    }
                });
            }
        });

        $('.bu').click(function (e) {
            var $this = $(this);
            if (confirm("您确定该次提现成功，并扣除用户账户冻结资金吗？该次操作不可逆转，请谨慎！")) {
                $.ajax({
                    url: '${basePath}withdraw/bu?id=' + $(this).attr('withdrawid'),
                    type: 'post',
                    beforeSend: function () {
                        $this.button('loading');
                    },
                    success: function (data) {
                        alert(data);
                        window.location.reload();
                    },
                    error: function () {
                        alert('请求失败，请重试');
                    },
                    complete: function () {
                        $this.button('reset');
                    }
                });
            }
        });
    });

    function stopDefault(e) {
        if (e && e.preventDefault) {
            e.preventDefault();
        } else {
            window.event.returnValue = false;
        }
        return false;
    }
</script>
</body>
</html>
