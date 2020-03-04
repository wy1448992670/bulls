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
<title>导出申请</title>
<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css" />
<link href="${basePath}css/datepicker.css" media="all" rel="stylesheet" type="text/css" />
<style type="text/css">
.table .over {
	overflow: hidden;
	width: 40%;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.numberLine {
	word-break: break-all;
	word-wrap: break-word;
}
</style>
</head>
<body>

	<div class="modal fade" id="auditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">申请确认</h4>
				</div>
				<div class="modal-body">
					<textarea id="remark" class="form-control" placeholder="请输入审核说明"></textarea>
				</div>
				<div class="modal-footer">
					<!-- data-dismiss="modal" -->
					<button type="button" onclick="auditConfirm(2)" class="btn btn-primary">拒绝申请</button>
					<button type="button" onclick="auditConfirm(1)" class="btn btn-primary">同意申请</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>


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
				<input type="hidden" id="aid" />
				<div class="col-lg-12">
					<div class="widget-container fluid-height clearfix">
						<div class="heading">
							<i class="icon-table"></i>
							导出申请列表
							<img data-toggle="modal" data-target="#myModal" onclick="comm.toggleSearch()" src="${basePath }images/select.png" class="hidden-md hidden-sm hidden-lg pull-right" style="width: 34px; height: 34px;" />

							<button class="btn btn-primary pull-right hidden-xs" type="button" onclick="search()">搜索</button>
						</div>
						<div class="widget-content padded clearfix">
							<div id="myModal">
								<form class="search1 AppFixed  form-inline  col-lg-5 pull-right col-xs-11" id="form" action="exportApplyList">
									<input type="hidden" name="page" value="1" />

									<div class="row">
										<div class="form-group col-md-4">
										</div>
										<div class="form-group col-md-4 col-xs-12">
											<div>
												<input class="form-control keyword" name="trueName" id="trueName" type="text" placeholder="请输入真实姓名搜索" value="${trueName }" />
											</div>
										</div>
										<div class="form-group col-md-4 pull-right  col-xs-12">
											<select class="select2able" name="auditStatus" id="auditStatus">
												<option value="">请选择审核状态</option>
												<option value="0" <c:if test="${auditStatus == 0 }">selected</c:if>>申请中</option>
												<option value="1" <c:if test="${auditStatus == 1 }">selected</c:if>>通过</option>
												<option value="2" <c:if test="${auditStatus == 2 }">selected</c:if>>拒绝</option>
											</select>
										</div>
									</div>
									<div class="row">
										<div class="form-group col-md-4">
										</div>
										<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${startTime }" name="startTime" id="startTime" type="text" placeholder="请选择申请起始时间" />
										</div>
										<div class="form-group col-md-4">
											<input class="form-control datepicker" value="${endTime }" name="endTime" id="endTime" type="text" placeholder="请选择申请结束时间" />
										</div>
									</div>
									<input type="hidden" name="page" value="${page }" />
									<button class="btn btn-primary pull-right  hidden-md hidden-sm hidden-lg" type="button" onclick="search()">搜索</button>
									<button data-dismiss="modal" class="hidden-md hidden-sm hidden-lg" onclick="comm.toggleSearch()">关闭</button>
								</form>
							</div>
							<div class="table-responsive">
								<jsp:useBean id="now" class="java.util.Date" scope="page"/>
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th width="50">ID</th>
											<th>申请列表</th>
											<th>申请人</th>
											<th width="160px">申请时间</th>
											<th>申请原因</th>
											<th>审核状态</th>
											<th>审核人员</th>
											<th width="160px">审核时间</th>
											<th>审核说明</th>
											<th width="160px">过期时间</th>
											<th width="120">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${list }">
											<tr>
												<td>${i.id }</td>
												<td>
													<c:if test="${i.target_list == 0 }">网站用户列表</c:if>
												</td>
												<td>${i.apply_user_name }</td>
												<td>
													<fmt:formatDate value="${i.apply_time }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>${i.apply_reason }</td>
												<td>
													<c:if test="${i.apply_status == 0 }">
														<span class="label label-info">申请中</span>
													</c:if>
													<c:if test="${i.apply_status == 1 }">
														<span class="label label-success">通过</span>
													</c:if>
													<c:if test="${i.apply_status == 2 }">
														<span class="label label-danger">拒绝</span>
													</c:if>
												</td>
												<td>${i.audit_user_name }</td>
												<td>
													<fmt:formatDate value="${i.audit_time }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td class="numberline">${i.audit_remark }</td>
												<td
													<c:if test="${i.expire_time < now }">
														style="color:red;"
													</c:if>
												>
													<fmt:formatDate value="${i.expire_time }" pattern="yyyy-MM-dd HH:mm:ss" />
												</td>
												<td>
													<%-- <shiro:hasPermission name="shop:orderDetail"> --%>
														<a href="${basePath}exportApply/applyDetail?id=${i.id}">详情</a>&nbsp;
                                    				<%-- </shiro:hasPermission> --%>
													<shiro:hasPermission name="exportApply:exportApplyAudit">
														<a>
															<c:if test="${i.apply_status == 0 }">
																<button class="btn btn-primary btn-xs" onclick="audit('${i.id}')" data-toggle="modal" data-target="#auditModal">审核</button>
															</c:if>
														</a>
													</shiro:hasPermission>
													
													<shiro:hasPermission name="exportApply:download">
														<a href="#" onclick="exportTarget(${i.id})">
															<c:if test="${i.apply_status == 1 }">
																<c:if test="${i.expire_time > now }">下载</c:if>
															</c:if>
														</a>
													</shiro:hasPermission>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							<form id="exportApplyAudit" action="${basePath}exportApply/exportApplyAudit" method="post">
								<input name="auditRemark" type="hidden" />
								<input name="id" type="hidden" />
								<input name="auditStatus" type="hidden" />
							</form>
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
	function auditConfirm(i){
		{
			  var id = $('#aid').val();
			  if(i == 1){
				  var r=confirm("确认同意申请吗?");
			  } else{
				  var r=confirm("确认拒绝申请吗?");
			  }
			  var status = i==1?1:2;
			  if (r)
			    {
				  	$("[name=auditRemark]").val($('#remark').val());
				  	$("[name=id]").val(id);
				  	$("[name=auditStatus]").val(status);

				  	$("#exportApplyAudit").submit();
			    }
			  }
	}

	function audit(id){
		console.log(id)
		$('#aid').val(id);
	}
    function search() {
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var auditStartTime = $("#auditStartTime").val();
        var auditEndTime = $("#auditEndTime").val();
        if(startTime!="" && endTime!=""){
            if(startTime > endTime){
                alert("申请结束时间必须大于等于开始时间");
                return;
            }
        }
        $("#form").submit();
    };
	
	function exportTarget(applyId) {
        location.href="${basePath}user/export/list/app?applyId=" + applyId;
    }
	
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2able').select2({width: ""});
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
                return "exportApplyList?trueName=${trueName}&keyword=${keyword}&auditStatus=${auditStatus}"
					+"&startTime=${startTime}&endTime=${endTime}&page=" + page;
            }
        });

        $(".datepicker").datepicker({
            showSecond: true,
            timeFormat: "hh:mm:ss",
            dateFormat: "yy-mm-dd"
        });

        if (${!empty flag}) {
            if (${flag==0}) {
                alert("审核成功");
                window.location.href = "${basePath}exportApply/exportApplyList";
            } else if(${flag==-1}) {
                alert("审核失败");
            }
        }
    });

</script>
</body>
</html>
