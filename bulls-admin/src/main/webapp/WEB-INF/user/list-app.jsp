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
<title>用户列表</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.loading {
	position: relative;
	left: 40%;
	top: 40%;
	text-align: center;
	width: 50px;
	height: 50px;
	border-radius: 50%;
	border: 5px solid #BEBEBE;
	border-left: 5px solid #498aca;
	animation: load 1s linear infinite;
	-moz-animation: load 1s linear infinite;
	-webkit-animation: load 1s linear infinite;
	-o-animation: load 1s linear infinite;
}

@
-webkit-keyframes load {
	from {-webkit-transform: rotate(0deg);
}

to {
	-webkit-transform: rotate(360deg);
}

}
@
-moz-keyframes load {
	from {-moz-transform: rotate(0deg);
}

to {
	-moz-transform: rotate(360deg);
}

}
@
-o-keyframes load {
	from {-o-transform: rotate(0deg);
}

to {
	-o-transform: rotate(360deg);
}

}
.confirm_modal {
	top: 50% !important;
	margin-top: -60px;
}

.confirm_modal .modal-body {
	padding: 20px 10px;
	text-align: center;
}

.confirm_modal .modal-footer {
	text-align: center;
}

.confirm_modal.modal {
	width: 360px;
	margin-left: -180px;
}

.modal-footer .btn {
	min-width: 80px;
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
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							用户列表
							<!--  -->
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/>
							<%-- <shiro:hasPermission name="user:export:app">
                            <a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                               href="${basePath}report/export/user?level=${level}&status=${status}&codes=${codes}&type=${type}&startAmount=${startAmount}&endAmount=${endAmount}&keyword=${keyword}&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&investStartTime=<fmt:formatDate value="${investStartTime }" pattern="yyyy-MM-dd"/>&investndTime=<fmt:formatDate value="${investndTime }" pattern="yyyy-MM-dd"/>&customerName=${customerName}"
                               id="add-row"><i class="icon-plus"></i>导出excel</a>

                            <a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs"
                               href="${basePath}report/export/userPhone?status=${status}&type=${type}&startAmount=${startAmount}&endAmount=${endAmount}&keyword=${keyword}&endTime=<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&investStartTime=<fmt:formatDate value="${investStartTime }" pattern="yyyy-MM-dd"/>&investndTime=<fmt:formatDate value="${investndTime }" pattern="yyyy-MM-dd"/>&customerName=${customerName}"
                               id="add-row"><i class="icon-plus"></i>导出Phone</a>

                        </shiro:hasPermission> --%>
							<%--<button class="btn btn-sm btn-primary-outline hidden-xs  pull-right" type="button">导出申请 </button>--%>
							<shiro:hasPermission name="user:add:app">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="${basePath}user/add/app" id="add-row">
									<i class="icon-plus"></i>
									添加用户
								</a>
							</shiro:hasPermission>
							<shiro:hasPermission name="user:export:apply">
								<button class="btn btn-sm btn-info pull-right hidden-xs" type="button" onclick="exportApply()" style="margin: 0 10px 0 0;" >导出申请</button>
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="bb()">搜索</button>
						</div>

						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline  col-md-6 pull-right col-xs-11" id="form" action="${basePath}user/list/app">
									<input type="hidden" name="page" value="1" />
									<div class="row">
										<div class="form-group col-md-2 col-xs-6">
											<div>
												<select class="select2able" name="status" id="status">
													<option value="" <c:if test="${status == null }">selected</c:if>>用户状态</option>
													<option value="0" <c:if test="${status == 0 }">selected</c:if>>可用</option>
													<option value="1" <c:if test="${status == 1 }">selected</c:if>>不可用</option>
													<option value="2" <c:if test="${status == 2 }">selected</c:if>>已删除</option>
												</select>
											</div>
										</div>
										<div class="form-group col-md-2 col-xs-6">
											<div>
												<select class="select2able" name="level" id="level">
													<option value="" <c:if test="${level == null }">selected</c:if>>用户等级</option>
													<option value="0" <c:if test="${level == 0 }">selected</c:if>>普通用户</option>
													<option value="1" <c:if test="${level == 1 }">selected</c:if>>会员用户</option>
													<option value="2" <c:if test="${level == 2 }">selected</c:if>>vip用户</option>
												</select>
											</div>
										</div>
										<div class="form-group col-md-2 col-xs-6">

											<select class="select2able" name="isBankCard" id="isBankCard">
												<option value="" <c:if test="${isBankCard == null }">selected</c:if>>是否绑卡</option>
												<option value="0" <c:if test="${isBankCard == 0 }">selected</c:if>>未绑卡</option>
												<option value="1" <c:if test="${isBankCard == 1 }">selected</c:if>>已绑卡</option>
											</select>

										</div>
										<div class="form-group col-md-3 col-xs-6">
											<select class="select2able" name="isMigration" id="isMigration">
												<option value="" <c:if test="${isMigration == null }">selected</c:if>>是否迁移用户</option>
												<option value="1" <c:if test="${isMigration == 1 }">selected</c:if>>是</option>
												<option value="0" <c:if test="${isMigration == 0 }">selected</c:if>>否</option>
											</select>
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
										<div class="form-group col-md-6">
											<div>
												<input name="keyword" type="text" placeholder="请输入真实姓名、手机号搜索" class="form-control keyword" value="${keyword }" id="keyword" />
											</div>
										</div>
										<div class="form-group col-md-6">
										<div>
											<input name="inviteKeyword" type="text"
												placeholder="请输入推荐人真实姓名、手机号搜索"
												class="form-control keyword" value="${inviteKeyword }"  id="inviteKeyword" />
										</div>
									</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<div>
												<input class="form-control datepicker" value="<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>" id="startTime" name="startTime" type="text" placeholder="请选择注册起始时间" />
											</div>
										</div>
										<div class="form-group col-md-6">
											<div>
												<input class="form-control datepicker" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>" id="endTime" name="endTime" type="text" placeholder="请选择注册结束时间" />
											</div>
										</div>
									</div>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="bb()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<shiro:hasPermission name="user:adminfenpei">
												<th>
													<input type="checkbox" id="allcheck" onclick="checkdis()" style="display: block; float: left;">
												</th>
											</shiro:hasPermission>
											<th>ID</th>
											<th>所属部门</th>
											<th>创建渠道</th>
											<th>数据来源</th>
											<th>用户名</th>
											<th>真实姓名</th>
											<th>余额</th>
											<th>冻结余额</th>
											<th>授信金额</th>
											<th>授信冻结金额</th>
											<th>绑卡状态</th>
											<th>手机号</th>
											<th>性别</th>
											<th>注册时间</th>
											<th>最后登录时间</th>
											<th>会员等级</th>
											<th>状态</th>
											<th>推荐人手机号</th>
											<th>推荐人真实姓名</th>
											<!-- 
											<shiro:hasPermission name="user:editpayPassword">
												<th width="75">重置支付密码</th>
											</shiro:hasPermission>
											-->
											<th width="75">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${users }">
											<tr>
												<shiro:hasPermission name="user:adminfenpei">
													<td>
														<input class="checknum" name="choice" <c:if test="${not empty user.tag or user.nodistri=='1'}">disabled="disabled" title="已分配和黑名单不能分配" </c:if> type="checkbox" value="${user.id}" style="display: block;" />
													</td>
												</shiro:hasPermission>
												<td>${user.id }</td>
												<td>${user.department_name }</td>
												<td>${user.channel_name }</td>
												<td>
													<c:if test="${user.data_source == '0'}" >平台</c:if>
													<c:if test="${user.data_source == '1' }">亿亿理财</c:if>
													<c:if test="${user.data_source == '2' }">阿里分发市场</c:if>
													<c:if test="${user.data_source == '3' }">应用宝</c:if>
													<c:if test="${user.data_source == '4' }">华为应用市场</c:if>
													<c:if test="${user.data_source == '5' }">vivo应用商店</c:if>
													<c:if test="${user.data_source == '6' }">oppo</c:if>
													<c:if test="${user.data_source == '7' }">魅族</c:if>
													<c:if test="${user.data_source == '8' }">联通wo</c:if>
													<c:if test="${user.data_source == '9' }">联想</c:if>
													<c:if test="${user.data_source == '10' }">百度</c:if>
													<c:if test="${user.data_source == '11' }">小米</c:if>
													<c:if test="${user.data_source == '12' }">360手机助手</c:if>
												</td>
												<td>
													<c:if test="${user.level == 0}">
														<span class="label label-default">V0</span>
													</c:if>
													<c:if test="${user.level == 1}">
														<span class="label label-danger">V1</span>
													</c:if>
													${user.username }
												</td>
												<td width="60">${user.trueName }</td>
												<td>${user.balance_amount}</td>
												<td>${user.frozen_amount}</td>
												<td>${user.credit_amount}</td>
												<td>${user.freozen_credit_amount}</td>
												<td>
													<c:if test="${user.ct ==0 }">
														<span class="label label-danger">否</span>
													</c:if>
													<c:if test="${user.ct !=0 }">
														<span class="label label-success">是</span>
													</c:if>
												</td>
												<td>
													<shiro:lacksPermission name="user:adminPhone">
													${user.phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
													</shiro:lacksPermission>
													<shiro:hasPermission name="user:adminPhone">
														${user.phone }
													</shiro:hasPermission>
												</td>
												<td>
													<c:if test="${user.sex == -1 }">
														未知
													</c:if>
													<c:if test="${user.sex == 0 }">
														女
													</c:if>
													<c:if test="${user.sex == 1 }">
														男
													</c:if>
													<c:if test="${user.sex == 2 }">
														保密
													</c:if>
												</td>
												<td>
													<fmt:formatDate value="${user.create_date }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<fmt:formatDate value="${user.last_login_time }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<c:if test="${user.LEVEL == 0 }">
														普通用户
													</c:if>
													<c:if test="${user.LEVEL == 1 }">
														会员用户
													</c:if>
													<c:if test="${user.LEVEL == 2 }">
														vip用户
													</c:if>
												</td>
												<td>
													<c:if test="${user.status == 0 }">
														<span class="label label-success">可用</span>
													</c:if>
													<c:if test="${user.status == 1 }">
														<span class="label label-warning">已冻结</span>
													</c:if>
													<c:if test="${user.status == 2 }">
														<span class="label label-danger">已删除</span>
													</c:if>
												</td>
												<td>
													<shiro:lacksPermission name="user:adminPhone">
													${user.uiPhone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2")}
													</shiro:lacksPermission>
													<shiro:hasPermission name="user:adminPhone">
														${user.uiPhone }
													</shiro:hasPermission>
												</td>
												<td>${user.uiRealName }</td>
												<!-- 
												<shiro:hasPermission name="user:editpayPassword">
													<td>
														<a class="delete-row" href="javascript:void(0)" onclick="aa(${user.id })">重置支付密码</a>
													</td>
												</shiro:hasPermission>
												 -->
												<td>
													<shiro:hasPermission name="user:edit:app">
														<a class="delete-row" href="${basePath}user/edit/app?id=${user.id }">编辑</a>
													</shiro:hasPermission>
													<shiro:hasPermission name="user:freeze">
														<c:if test="${user.status == 0 }">
															<a class="delete-row" href="javascript:void(0)" onclick="freeze(${user.id })">冻结</a>
														</c:if>
														<c:if test="${user.status == 1 }">
															<a class="delete-row" href="javascript:void(0)" onclick="unfreeze(${user.id })">解冻</a>
														</c:if>
													</shiro:hasPermission>
													<shiro:hasPermission name="user:unbund">
														<a class="delete-row active" href="${basePath}user/unbund?userId=${user.id}">解绑</a>
													</shiro:hasPermission>
													<shiro:hasPermission name="user:setVip:app">
														<a class="delete-row" href="#" onclick="setVipUserId('${user.user_id}', '${user.LEVEL}', '${user.give_out_date}', '${user.give_scale}')"
														   data-toggle="modal" data-target="#setVipModal">设置vip</a>
													</shiro:hasPermission>
													<shiro:hasPermission name="user:detail:app">
														<a class="delete-row" href="${basePath}user/detail/app?id=${user.id }">详情</a>
													</shiro:hasPermission>
													<c:if test="${user.is_migration }">
														<a class="delete-row" rid="${user.id}" href="${basePath}user/listMigrationInvestment?id=${user.id }">查看迁移投资</a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>

							</div>
							<ul id="pagination" style="float: right"></ul>
						</div>


						<div class="modal fade" id="setVipModal" tabindex="-2" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="widget-content padded clearfix"><div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
										<h4 class="modal-title" id="myModalLabel">设置vip</h4>
									</div>
										<div class="modal-body">
											<form method="post" class="form-horizontal" id="validate-form">
												<div class="form-group">
													<label class="control-label col-md-4">会员等级</label>
													<div class="col-md-7">
														<select class="select2able" name="vipLevel" id="vipLevel" onchange="vipLevelChange()" required />
															<option value="">请选择</option>
															<option value="0">普通用户</option>
															<option value="1">会员用户</option>
															<option value="2">vip用户</option>
														</select>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-4">每月利息发放日期</label>
													<div class="col-md-7">
														<input class="form-control vip-dividend" name="giveOutDay" id="giveOutDay" placeholder="请输入每月利息发放日期" type="number" max="28" min="1" required />
														<em style="color:red;">&nbsp;* 如：每月3号，填写3，最大值为28。</em>
														<%--<input class="form-control datepicker" name="giveOutDay" id="giveOutDay" type="text" placeholder="请选择订单创建起始时间" required />--%>
													</div>
												</div>
												<div class="form-group">
													<label class="control-label col-md-4">利息发放比例(%)</label>
													<div class="col-md-7">
														<input class="form-control vip-dividend" name="giveScale" id="giveScale" placeholder="请输入利息发放比例" type="number" step="0.01"  max="99" min="0" required />
														<em style="color:red;">&nbsp;* 如：10.5%，填写10.5。</em>
													</div>
												</div>
											</form>
										</div>
									</div>
									<div class="modal-footer">
										<!-- data-dismiss="modal" -->
										<button type="button" onclick="setVipConfirm('OK')" class="btn btn-primary">确认</button>
										<button type="button" onclick="setVipConfirm('CANCEL')" class="btn">取消</button>
									</div>
									<input id="vipUserId" type="hidden" />
								</div>
								<!-- /.modal-content -->
							</div>
							<!-- /.modal -->
						</div>

					</div>
				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>
	</div>
	</div>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${basePath}js/bootstrap-paginator.min.js" type="text/javascript"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script type="text/javascript">

    function bb() {
        $("#form").submit();
    }
    function aa(id) {
        if (confirm("您确定给该用户重置支付密码吗?!")) {
            jQuery.ajax({
                url: '${basePath}user/editpayPassword',
                type: "GET",
                data: "id=" + id,
                dataType: "json",
                success: function (data) {
                    if (data.status == "1") {
                        alert("重置支付密码成功!...");
                        window.location.href = "${basePath}user/list/app";
                    }
                    if (data.status == "0") {
                        alert("重置支付密码!..");
                    }
                }
            });
        }

    }

    function freeze(id) {
        if (confirm("您确定冻结该账户吗?!")) {
            jQuery.ajax({
                url: '${basePath}user/freeze',
                type: "GET",
                data: "type=0&id=" + id,
                dataType: "json",
                success: function (data) {
                    if (data.status == "1") {
                        alert("冻结成功");
                        window.location.reload();
                    }
                    if (data.status == "0") {
                        alert("冻结失败");
                    }
                }
            });
        }

    }

    function unfreeze(id) {
        if (confirm("您确定给该账户解除冻结状态吗?!")) {
            jQuery.ajax({
                url: '${basePath}user/freeze',
                type: "GET",
                data: "type=2&id=" + id,
                dataType: "json",
                success: function (data) {
                    if (data.status == "1") {
                        alert("解除冻结成功");
                        window.location.reload();
                    }
                    if (data.status == "0") {
                        alert("解除冻结失败");
                    }
                }
            });
        }

    }
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd',
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });
        $('.select2able').select2({width: "150"});
        $('#vipLevel').select2();
        $(".keyword").keyup(function (e) {
            e = e || window.e;
            if (e.keyCode == 13) {
                $("#form").submit();
            }
        });
        $('.active').click(function () {
            var userId = $(this).attr('userId');
            $.getJSON("${basePath}user/active/sina?id=" + userId, null, function (data) {
                alert(data.resultMsg);
            });
        });
        $('#pagination').bootstrapPaginator({
            currentPage: parseInt('${page}'),
            totalPages: parseInt('${pages}'),
            bootstrapMajorVersion: 3,
            alignment: "right",
            pageUrl: function (type, page, current) {
                return "app?keyword=${keyword}&level=${level}&status=${status}&type=${type}&isBankCard=${isBankCard}&isMigration=${isMigration}&inviteKeyword=${inviteKeyword}&endTime="
                		+"<fmt:formatDate value="${endTime }" pattern="yyyy-MM-dd"/>&startTime=<fmt:formatDate value="${startTime }" pattern="yyyy-MM-dd"/>&departmentId=${departmentId}&page=" + page;
            }
        });
       /*  $(function () {
            $('#pagination').bootstrapPaginator({
                currentPage: parseInt('${page}'),
                totalPages: parseInt('${pages}'),
                bootstrapMajorVersion: 3,
                alignment: "right",
                pageUrl: function (type, page, current) {
                    return "list?name=${name}&key=${key}&page=" + page;
                }
            });

        }); */


    })
    //点击全选，子复选框被选中
    function checkdis(){
   		 var allcheck=document.getElementById("allcheck");
   	     var choice=document.getElementsByName("choice");
         for(var i=0;i<choice.length;i++){
        	 if(choice[i].disabled==false){
              choice[i].checked=allcheck.checked;
        	 }
          }
    }

    //点击子复选框,全选框 选中、取消
    function setAll(){
	    if(!$(".checknum").checked){
	    	$("#allcheck").prop("checked",false); // 子复选框某个不选择，全选也被取消
	    }
	    var choicelength=$("input[type='checkbox'][class='checknum']").length;
	    var choiceselect=$("input[type='checkbox'][class='checknum']:checked").length;

	    if(choicelength==choiceselect){
	   		 $("#allcheck").prop("checked",true);   // 子复选框全部部被选择，全选也被选择；1.对于HTML元素我们自己自定义的DOM属性，在处理时，使用attr方法；2.对于HTML元素本身就带有的固有属性，在处理时，使用prop方法。
	    }
    }

    function setVipUserId(userId, level, giveOutDay, giveScale){
        $('#vipUserId').val(userId);
		$("#vipLevel option[value="+level+"]").attr("selected", true);   //设置Select的项选中
        $('#setVipModal').find('.select2-chosen').text($("#vipLevel").find("option:selected").text());
        $('#giveOutDay').val(giveOutDay); // 发放日期
        $('#giveScale').val(giveScale); // 发放比例
		if (level != '2') {
		    $(".vip-dividend").attr("readonly", "readonly");
		}
    }

    function vipLevelChange() {
        var objS = document.getElementById("vipLevel");
        var value = objS.options[objS.selectedIndex].value;
        if (value != '2') {
            $(".vip-dividend").attr("readonly", "readonly");
		} else {
            $(".vip-dividend").removeAttr("readonly");
		}
	}

    function setVipConfirm(opt){
		var userId = $('#vipUserId').val();
        console.log(opt);
		if(opt == 'OK'){
		    var vipLevel = $.trim($("#vipLevel").val());
            var giveOutDay = parseInt($.trim($("#giveOutDay").val()));
            var giveScale = parseFloat($.trim($("#giveScale").val()));
            if (vipLevel == '' || vipLevel == '-1') {
                alert("会员等级不能为空！");
                return;
			}
            if (giveOutDay == '') {
                alert("每月利息发放日期不能为空！");
                return;
            }
            if (typeof giveOutDay != 'number' || giveOutDay%1 !== 0 || giveOutDay <= 0  || giveOutDay > 28) {
                alert("请输入正确的每月利息发放日期！");
                return;
            }
            if (giveScale == '') {
                alert("利息发放比例不能为空！");
                return;
            }
            if (typeof giveScale != 'number' || giveScale <= 0  || giveScale > 100) {
                alert("请输入正确利息发放比例！");
                return;
            }
			if (confirm("确认设置该用户为会员吗?")) {
			    var params = "userId=" + userId + "&giveOutDay=" + giveOutDay + "&giveScale=" + giveScale + "&level=" + vipLevel;
			    console.log(params);
				$.ajax({
                    url: '${basePath}user/setVipUserAjax',
                    type: "POST",
                    data: params,
                    dataType: "json",
                    success: function (data) {
                        console.log(data);
                        if (data.code == '-1') {
                            alert("error: " + data.msg);
                        } else {
                            $("#form").submit();
                            alert("操作成功");
                            $('#setVipModal').find('.close').click();
                        }
                    }
                });
			}
		} else{
			if (confirm("确认取消设置该用户为会员吗?")) {
                $('#setVipModal').find('.close').click();
			}
		}
    }
    
    function exportApply() {
        var params = Math.random().toString();
        <%--var params = "count=" + ${count};--%>
        var status = $.trim($("select[name='status']").val());
        if (status != "") {
            params += "&status=" + status;
        }
        var level = $.trim($("select[name='level']").val());
        if (level != "") {
            params += "&level=" + level;
        }
        var isBankCard = $.trim($("select[name='isBankCard']").val());
        if (isBankCard != "") {
            params += "&isBankCard=" + isBankCard;
        }
        var isMigration = $.trim($("select[name='isMigration']").val());
        if (isMigration != "") {
            params += "&isMigration=" + isMigration;
        }
        var departmentId = $.trim($("select[name='departmentId']").val());
        if (departmentId != "") {
            params += "&departmentId=" + departmentId;
        }
        var keyword = $.trim($("input[name='keyword']").val());
        if (keyword != "") {
            params += "&keyword=" + keyword;
        }
        var inviteKeyword = $.trim($("input[name='inviteKeyword']").val());
        if (inviteKeyword != "") {
            params += "&inviteKeyword=" + inviteKeyword;
        }
        var startTime = $.trim($("input[name='startTime']").val());
        if (startTime != "") {
            params += "&startTime=" + startTime;
        }
        var endTime = $.trim($("input[name='endTime']").val());
        if (endTime != "") {
            params += "&endTime=" + endTime;
        }
        // console.log("****************");
        console.log(params);
        if (params.indexOf("&") < 0) {
            alert("请选择筛选条件");
            return;
        }
        location.href = "${basePath}exportApply/add/user-app?" + params;
    }

</script>
</body>
</html>
