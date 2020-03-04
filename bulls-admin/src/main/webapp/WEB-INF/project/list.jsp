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
<title>物权资产</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.table .over {
	overflow: hidden;
	width: 40%;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.numberLine {
    word-break: break-all; word-wrap: break-word;
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
				<h1>牧场管理</h1>
			</div>
			<!-- DataTables Example -->
			<div class="row">
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							物权资产列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;"/">
							<shiro:hasPermission name="project:export">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" id="" onclick="exportExcel()" href="javascript:;">
									<i class="icon-plus"></i>
									导出Excel
								</a>
							</shiro:hasPermission>
							<script>
								function exportExcel() {
									var parameter = "parameter=1";

									var status = $("#status").val();
									if (status != "") {
										parameter += "&status=" + status;
									}
									var noob = $("#noob").val();
									if (noob != "") {
										parameter += "&noob=" + noob;
									}
                                    var buyAgain = $("#buyAgain").val();
                                    if (buyAgain != "") {
                                        parameter += "&buyAgain=" + buyAgain;
                                    }
                                    var projectType = $("#projectType").val();
                                    if (projectType != "") {
                                        parameter += "&projectType=" + projectType;
                                    }
									var limitDays = $("#limitDays").val();
									if (limitDays != "") {
										parameter += "&limitDays=" + limitDays;
									}
									var keyword = $("#keyword").val();
									if (keyword != "") {
										parameter += "&keyword=" + keyword;
									}
									var createTimeStart = $("#createTimeStart").val();
									if (createTimeStart != "") {
										parameter += "&createTimeStart=" + createTimeStart;
									}
									var createTimeEnd = $("#createTimeEnd").val();
									if (createTimeEnd != "") {
										parameter += "&createTimeEnd=" + createTimeEnd;
									}
									var payTimeStart = $("#payTimeStart").val();
									if (payTimeStart != "") {
										parameter += "&payTimeStart=" + payTimeStart;
									}
									var payTimeEnd = $("#payTimeEnd").val();
									if (payTimeEnd != "") {
										parameter += "&payTimeEnd=" + payTimeEnd;
									}
									var buybackTimeStart = $("#buybackTimeStart").val();
									if (buybackTimeStart != "") {
										parameter += "&buybackTimeStart=" + buybackTimeStart;
									}
									var buybackTimeEnd = $("#buybackTimeEnd").val();
									if (payTimeEnd != "") {
										parameter += "&buybackTimeEnd=" + buybackTimeEnd;
									}
									var dueTimeStart = $("#dueTimeStart").val();
									if (dueTimeStart != "") {
										parameter += "&dueTimeStart=" + dueTimeStart;
									}
									var dueTimeEnd = $("#dueTimeEnd").val();
									if (dueTimeEnd != "") {
										parameter += "&dueTimeEnd=" + dueTimeEnd;
									}
									var feedTime = $("#feedTime").val();
									if (feedTime != "") {
										parameter += "&feedTime=" + feedTime;
									}

									location.href = "${basePath}project/export?" + parameter;
								}
								function new_search() {
									$("#page").val(1);
									$("#form").submit();
								};
								function search() {
									$("#form").submit();
								};
							</script>
							&nbsp;
							<%-- <a class="btn btn-sm btn-primary-outline pull-right" onclick="desc(this)">
								<c:if test="${desc == 'desc' }">
                        	降序 ↓
                        	</c:if>
								<c:if test="${desc == 'asc' }">
                        	升序 ↑
                        	</c:if>
							</a> --%>
							&nbsp;
							<shiro:hasPermission name="project:add">
								<a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="add" id="add-row">
									<i class="icon-plus"></i>
									创建物权项目
								</a>
								<!--     <a class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="addLoan" style="margin-left: 10px;"><i
                                    class="icon-plus"></i>创建个人借贷项目</a> -->
							</shiro:hasPermission>
							<button class="btn btn-primary pull-right  hidden-xs" type="button" onclick="new_search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed form-inline  col-lg-6 pull-right " id="form" action="list">
									<input type="hidden" name="page" value="1" />
									<input type="hidden" name="desc" id="desc" value="${desc}" />
									<div class="row">
										<div class="form-group col-md-2 col-xs-6">
											<select class="select2able" name="status" id="status">
												<option value="" <c:if test="${status == null }">selected</c:if>>所有项目状态</option>
												<option value="0" <c:if test="${status.get(0) == 0 }">selected</c:if>>待上架</option>
												<option value="1" <c:if test="${status.get(0) == 1 }">selected</c:if>>上架</option>
												<option value="2" <c:if test="${status.get(0) == 2 }">selected</c:if>>待付款</option>
												<option value="3" <c:if test="${status.get(0) == 3 }">selected</c:if>>已售出</option>
												<option value="4" <c:if test="${status.get(0) == 4 }">selected</c:if>>已回购</option>
												<option value="5" <c:if test="${status.get(0) == 5 }">selected</c:if>>已死亡</option>
												<%--<option value="6" <c:if test="${status.get(0) == 6 }">selected</c:if>>已删除</option>--%>
											</select>
										</div>
										<div class="form-group col-md-2 col-xs-6">
											<select class="select2able" name="noob" id="noob">
												<option value="" <c:if test="${noob == null }">selected</c:if>>标地类型</option>
												<option value="0" <c:if test="${noob == 0 }">selected</c:if>>普通散标</option>
												<option value="1" <c:if test="${noob == 1 }">selected</c:if>>新手标</option>
											</select>
										</div>
										<div class="form-group col-md-2 col-xs-6">
											<select class="select2able" name="limitDays" id="limitDays">
												<option value="" <c:if test="${limitDays == null }">selected</c:if>>所有项目期限</option>
												<c:forEach items="${days }" var="day">
													<option value="${day.days }" <c:if test="${limitDays == day.days }">selected</c:if>>${day.days }</option>
												</c:forEach>
											</select>
										</div>
										<div class="form-group col-md-2 col-xs-6">
											<select class="select2able" name="buyAgain" id="buyAgain">
												<option value="0" <c:if test="${buyAgain == 0 }">selected</c:if>>非续购项目</option>
												<option value="1" <c:if test="${buyAgain == 1 }">selected</c:if>>续购项目</option>
											</select>
										</div>
										<div class="form-group col-md-2 col-xs-6">
											<select class="select2able" name="projectType" id="projectType">
												<option value="" <c:if test="${projectType == null }">selected</c:if>>项目类型</option>
												<option value="0" <c:if test="${projectType == 0 }">selected</c:if>>养牛</option>
												<option value="1" <c:if test="${projectType == 1 }">selected</c:if>>拼牛</option>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-6">
											<input class="form-control keyword" type="text" value="${keyword }" name="keyword" id="keyword" placeholder="请输入项目名称或者耳标号" />
										</div>
										<input type="hidden" name="page" value="1" />
										<div class="form-group col-md-6 hidden-xs">
											<input class="form-control datepicker" type="text" value="${feedTime }" name="feedTime" id="feedTime" placeholder="是否饲养期查询时间点" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-3">
											<input class="form-control datepicker" type="text" value="${createTimeStart}" name="createTimeStart" id="createTimeStart" placeholder="发标时间选择起始时间" />
										</div>
										<div class="form-group col-md-3">
											<input class="form-control datepicker" type="text" value="${createTimeEnd}" name="createTimeEnd" id="createTimeEnd" placeholder="发标时间选择结束时间" />
										</div>
										<div class="form-group col-md-3 hidden-xs">
											<input class="form-control datepicker" type="text" value="${payTimeStart}" name="payTimeStart" id="payTimeStart" placeholder="支付时间选择起始时间" />
										</div>
										<div class="form-group col-md-3 hidden-xs">
											<input class="form-control datepicker" type="text" value="${payTimeEnd}" name="payTimeEnd" id="payTimeEnd" placeholder="支付时间选择结束时间" />
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-3 hidden-xs">
											<input class="form-control datepicker" type="text" value="${buybackTimeStart }" name="buybackTimeStart" id="buybackTimeStart" placeholder="回购时间选择起始时间" />
										</div>
										<div class="form-group col-md-3 hidden-xs">
											<input class="form-control datepicker" type="text" value="${buybackTimeEnd }" name="buybackTimeEnd" id="buybackTimeEnd" placeholder="回购时间选择结束时间" />
										</div>
										<div class="form-group col-md-3 hidden-xs">
											<input class="form-control datepicker" type="text" value="${dueTimeStart }" name="dueTimeStart" id="dueTimeStart" placeholder="到期时间选择起始时间" />
										</div>
										<div class="form-group col-md-3 hidden-xs">
											<input class="form-control datepicker" type="text" value="${dueTimeEnd }" name="dueTimeEnd" id="dueTimeEnd" placeholder="到期时间选择结束时间" />
										</div>
									</div>
									<shiro:hasPermission name="user:export:app">
										<div class="row">
											<%-- 	<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime" type="text" placeholder="还款计划请选择起始时间" />
										</div>
										<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime" type="text" placeholder="还款计划请选择结束时间" />
										</div> --%>
											<div class="form-group col-md-4">
												<!-- 			<a style="margin-left: 10px;" class="btn btn-sm btn-primary-outline pull-right hidden-xs" href="#" onclick="report()">
												<i class="icon-plus"></i>
												导出还款计划
											</a> -->
											</div>
										</div>
									</shiro:hasPermission>
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="new_search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<table class="table table-bordered table-hover text-striped" style="min-width:1600px;" spellcheck="true">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th>名称</th>
											<th width="50">年化</th>
											<th width="50">加息</th>
											<!-- <th>截止时间</th> -->
											<th width="50">期限/天</th>
											<th>加息券生息天数</th>

                                            <th>募集金额</th>
                                            <th>耳标编号号</th>
                                            <th>真实耳标编号号</th>

											<th>GPS设备编号</th>


											<th>创建时间</th>
											<!-- <th>支持发标时间</th> -->
											<th>上架时间</th>
											<th>支付时间</th>
											<th>回购时间</th>
											<th>项目类型</th>
											<th>状态</th>
											<!-- 	<th>排序</th> -->
											<th>操作</th>

										</tr>
									</thead>
									<tbody>
										<c:forEach var="g" items="${list }">
											<tr>
												<td>${g.id}</td>
												<td>
													<c:if test="${g.noob==1}">
														<span class="label label-primary">新手标</span>
													</c:if>
													<c:if test="${g.noob==2}">
														<span class="label label-danger">VIP标</span>
													</c:if>
													<c:if test="${g.projectType==3}">
														<span class="label label-warning">超级标</span>
													</c:if>
													<c:out value="${g.title }" escapeXml="false" />
												</td>
												<td>
													<fmt:formatNumber value="${g.annualized }" type="percent" maxFractionDigits="3" groupingUsed="false" />
												</td>
												<td>
													<fmt:formatNumber value="${g.increaseAnnualized }" type="percent" maxFractionDigits="5" groupingUsed="false" />
												</td>
												<%-- 											<td>
												<fmt:formatDate value="${g.deadline }" pattern="yyyy-MM-dd HH:mm:ss" />
											</td> --%>
												<td>${g.limitDays }</td>

												<td>${g.rateCouponDays}天</td>

												<td>${g.totalAmount }</td>
												<td>${g.earNumber }</td>

                                                <td>${g.realEarNumber }</td>
                                                <td class="numberline">${g.gpsNumber }</td>

												<td>
													<fmt:formatDate value="${g.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<%-- 		<td>
												<fmt:formatDate value="${g.lendBeginTime }" pattern="yyyy-MM-dd" />
											</td>  --%>
												<td>
													<fmt:formatDate value="${g.startTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<fmt:formatDate value="${g.tradeTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<fmt:formatDate value="${g.buyBackTime }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<c:if test="${g.projectType == 0 }">
														<span class="label label-info">养牛</span>
													</c:if>
													<c:if test="${g.projectType == 1 }">
														<span class="label label-warning">拼牛</span>
													</c:if>
												</td>
												<td>

													<c:if test="${g.status == 0 }">
														<span class="label label-warning">${g.projectStatusName}</span>
													</c:if>
													<c:if test="${g.status == 1 }">
														<span class="label label-info">${g.projectStatusName}</span>
													</c:if>
													<c:if test="${g.status == 2 }">
														<span class="label label-danger">${g.projectStatusName}</span>
													</c:if>
													<c:if test="${g.status == 3 }">
														<span class="label label-success">${g.projectStatusName}</span>
													</c:if>
													<c:if test="${g.status == 4 }">
														<span class="label label-primary">${g.projectStatusName}</span>
													</c:if>
													<c:if test="${g.status == 5 }">
														<span class="label label-danger">${g.projectStatusName}</span>
													</c:if>
													<c:if test="${g.status == 6 }">
														<span class="label label-danger">${g.projectStatusName}</span>
													</c:if>
												<td>
													<shiro:hasPermission name="project:lowerShelves">
														<c:if test="${g.status == 1}">
															<button class="btn btn-warning btn-xs" type="button" onclick="lowerShelves('${g.id}')">下架</button>
														</c:if>
													</shiro:hasPermission>
													<shiro:hasPermission name="project:edit">
														<c:if test="${g.status ==0 && !g.buyAgain}">
															<a class="edit-row" href="edit?id=${g.id }&listParams=${listParams}">编辑</a>
															<%--<a class="edit-row" href="edit?id='${g.id }'">编辑</a>--%>
														</c:if>
													</shiro:hasPermission>

													<shiro:hasPermission name="project:detail">
														<a class="delete-row" href="detail?id=${g.id }" id="delete">详细</a>
													</shiro:hasPermission>
													<c:if test="${!g.buyAgain}">
														<shiro:hasPermission name="project:add">
															<a class="delete-row" href="add?id=${g.id }" id="copyCreate">复制创建</a>
														</shiro:hasPermission>
													</c:if>
													<shiro:hasPermission name="project:lifePic:upload">
													<a class="delete-row" href="lifePicList?earNumber=${g.earNumber }" >牛只生活照</a>
													</shiro:hasPermission>
												</td>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<div style="font-size: 16px">
								待上架总头数：
								<font color="red" style="font-weight: normal;">${statisticsInfo[0]}</font>
								已上架总头数：
								<font color="red" style="font-weight: normal;">${statisticsInfo[1]}</font>
								饲养中头数：
								<font color="red" ;style="font-weight: normal;">${statisticsInfo[2]}</font>
							</div>
							<ul id="pagination" style="float: right"></ul>
						</div>
					</div>

				</div>
			</div>
			<!-- end DataTables Example -->
		</div>
	</div>
	<script src="${basePath}js/jquery-1.10.2.min.js"></script>
	<script src="${basePath}js/jquery-ui.js"></script>
	<script src="${basePath}js/bootstrap-paginator.min.js"></script>
	<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
	<script src="${basePath}js/select2.js" type="text/javascript"></script>
	<script src="${basePath}js/comm.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
			$(".datepicker").datepicker({
                format: 'yyyy-mm-dd',
                showSecond: true,
                timeFormat: "hh:mm:ss",
                dateFormat: "yy-mm-dd"
			});
			$('.select2able').select2({
				width : "150"
			});
			$(".keyword").keyup(function(e) {
				e = e || window.e;
				if (e.keyCode == 13) {
					$("#form").submit();
				}
			});
			$('#pagination')
					.bootstrapPaginator(
							{
								currentPage : parseInt('${page}'),
								totalPages : parseInt('${pages}'),
								bootstrapMajorVersion : 3,
								alignment : "right",
								pageUrl : function(type, page, current) {
									return "list?status=${status.get(0)}&noob=${noob}&buyAgain=${buyAgain}&projectType=${projectType}&limitDays=${limitDays}&keyword=${keyword}"
											+ "&createTimeStart=${createTimeStart}&createTimeEnd=${createTimeEnd}"
											+ "&payTimeStart=${payTimeStart}&payTimeEnd=${payTimeEnd}"
											+ "&buybackTimeStart=${buybackTimeStart}&buybackTimeEnd=${buybackTimeEnd}"
											+ "&feedTime=${feedTime}" + "&page=" + page;
								}
							});
		});
		function report() {
			var startTime = $.trim($('#startTime').val());
			var endTime = $.trim($('#endTime').val());
			var keyword = $.trim($('#keyword').val());
			window.location.href = "${basePath}report/paymentExcel?startTime=" + startTime + "&endTime=" + endTime
					+ "&keyword=" + keyword;
		}
		function desc(o) {
			var dc = $("#desc").val();
			if (dc == "asc") {
				$("#desc").val("desc");
			} else {
				$("#desc").val("asc");
			}
			$("#form").submit();
		}
		function lowerShelves(id) {
		    console.log(id);
            if (confirm("是否确认下架该牛只?!")) {
                $.ajax({
                    type: 'post',
                    url: "${basePath}project/lowerShelvesAjax",
                    data: {
                        id: id
                    },
                    dataType: 'json',
                    success : function(data) {
                        if (data.code == "1") {
                            alert(data.msg);
                            // 刷新
                            location.reload()
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        }
	</script>

</body>
</html>
