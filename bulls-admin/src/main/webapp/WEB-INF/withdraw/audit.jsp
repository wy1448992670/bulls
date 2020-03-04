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
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>提现审核</title>
    <style type="text/css">
        .table td, .table th {
            text-align: center;
        }
    </style>
</head>
<body>
	<div class="modal fade" id="auditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">提现确认</h4>
				</div>
				<div class="modal-body">
					<textarea id="remark" class="form-control" placeholder="请输入审核说明(20字以内)"></textarea>
				</div>
				<div class="modal-footer">
					<!-- data-dismiss="modal" -->
					<button type="button" onclick="auditConfirm(2)" class="btn btn-primary">拒绝提现</button>
					<button type="button" onclick="auditConfirm(1)" class="btn btn-primary">同意提现</button>
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
            <h1>
                提现审核详情
            </h1>
            <c:if test="${!empty message }">
                <div class="alert alert-danger text-center" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only"></span>
                        ${message }
                </div>
            </c:if>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>提现审核
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="auditBankProcess" method="post" class="form-horizontal" id="validate-form">
                            <input type="hidden" name="id" value="${draw.id }"/>
                            <input name="auditRemark" type="hidden" />
							<input name="isPass" type="hidden" />


                            <div class="form-group">
                                <label class="control-label col-md-2">提现者真实姓名</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.trueName }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">提现金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.amount}
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">实际到帐金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.realAmount }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">提现方式</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${draw.withdrawals}
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">提现申请时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${draw.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">提现卡号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <%--<shiro:lacksPermission name="user:adminPhone">
                                            ${draw.cardNo.replaceAll("(\\d{4})\\d{5}(\\d{4})","$1*****$2")}
                                        </shiro:lacksPermission>--%>
                                        <shiro:hasPermission name="user:adminPhone">
                                            ${draw.cardNo }
                                        </shiro:hasPermission>
                                    </p>
                                </div>
                            </div>

                            <c:if test="${draw.techOperateUserId==null}">
                                <%--<div class="form-group">
                                    <label class="control-label col-md-2">状态</label>

                                    <div class="col-md-7">
                                        <select class="select2able" name="status">
                                            <option value="">请选择</option>
                                            <option value="2">失败</option>
                                            <option value="3">取消</option>
                                        </select>
                                    </div>
                                </div>--%>
                                <%--<div class="form-group">--%>
                                    <%--<label class="control-label col-md-2">技术备注</label>--%>

                                    <%--<div class="col-md-7">--%>
                                        <%--<div class="col-md-7">--%>
                                            <%--<input class="form-control" name="techRemark" type="text" value="${draw.techRemark }">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </c:if>

                            <c:if test="${draw.techOperateUserId !=null && draw.financeOperateUserId== null}">
                                <%--<div class="form-group">
                                    <label class="control-label col-md-2">状态</label>

                                    <div class="col-md-7">
                                        <select class="select2able" name="status">
                                            <option value="">请选择</option>
                                            <option value="2">失败</option>
                                            <option value="3">取消</option>
                                        </select>
                                    </div>
                                </div>--%>
                                <%--<div class="form-group">--%>
                                    <%--<label class="control-label col-md-2">技术备注</label>--%>

                                    <%--<div class="col-md-7">--%>
                                        <%--<input class="form-control" name="techRemark" disabled="disabled" type="text" value="${draw.techRemark }">--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="form-group">--%>
                                    <%--<label class="control-label col-md-2">财务备注</label>--%>

                                    <%--<div class="col-md-7">--%>
                                        <%--<div class="col-md-7">--%>
                                            <%--<input type="hidden" name="techOperateUserId" value="${draw.techOperateUserId }"/>--%>
                                            <%--<input class="form-control" name="financeRemark" type="text" value="${draw.financeRemark }">--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </c:if>

                            <!-- 最后一步修改成功 -->
                            <c:if test="${draw.techOperateUserId !=null && draw.financeOperateUserId != null}">
                                <%--<div class="form-group">--%>
                                    <%--<label class="control-label col-md-2">状态</label>--%>

                                    <%--<div class="col-md-7">--%>
                                        <%--<select class="select2able" name="status">--%>
                                            <%--<option value="1">成功</option>--%>
                                            <%--<option value="2">失败</option>--%>
                                            <%--<option value="3">取消</option>--%>
                                        <%--</select>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="form-group">--%>
                                    <%--<label class="control-label col-md-2">技术备注</label>--%>

                                    <%--<div class="col-md-7">--%>
                                        <%--<input class="form-control" name="techRemark" id="techRemark" disabled="disabled" type="text" value="${draw.techRemark }">--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="form-group">--%>
                                    <%--<label class="control-label col-md-2">财务备注</label>--%>

                                    <%--<div class="col-md-7">--%>
                                        <%--<input class="form-control" name="financeRemark" id="financeRemark" disabled="disabled" type="text" value="${draw.financeRemark }">--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </c:if>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>

                                    <shiro:hasPermission name="withdraw:audit">
                                        <button class="btn btn-primary"  onclick="audit('${draw.id }')" data-toggle="modal" data-target="#auditModal">
                                            审核
                                        </button>
                                    </shiro:hasPermission>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div class="col-lg-6">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户资金明细表
                        <%--<a class="btn btn-sm btn-primary-outline pull-right" href="${basePath}report/export/trade?userId=${draw.id}" id="export-trade">--%>
                            <%--<i class="icon-plus"></i>导出Excel</a>--%>
                    </div>
                    <div class="widget-content padded clearfix">
                        <jsp:include page="../common/tradeRecordFromDetail.jsp" flush="true">
                            <jsp:param name="userId" value="${draw.userId}"/>
                            <jsp:param name="businessId" value="${draw.id}"/>
                            <jsp:param name="tableName" value="withdraw"/>
                        </jsp:include>
                    </div>
                </div>
            </div>

        </div>

    </div>
</div>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-paginator.min.js"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2({width: "100"});
        $("#investment-list a:eq(0)").addClass("current");
        // $("#investment-list a:eq(3)").addClass("current");
        // showTradeDetail(1);
    });


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
    
    function auditConfirm(i){
		{
			  var id = $('#id').val();
			  if(i == 1){
				  var r=confirm("确认同意提现吗?");
			  } else{
				  var r=confirm("确认拒绝提现吗?");
			  }
			  var isPass = i==1?true:false;
			  if (r)
			    {
				  	$("[name=auditRemark]").val($('#remark').val());
				  	$("[name=isPass]").val(isPass);

				  	$("#validate-form").submit();
				  	
			    }
			  }
	}

	function audit(id){
		console.log(id)
		$('#id').val(id);
	}
</script>
</body>
</html>
