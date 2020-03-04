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
    <title>添加用户</title>
    <%--<link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>--%>
    <link href="${basePath}css/bootstrap.min.css" rel="stylesheet">
    <link href="${basePath}css/min/font-awesome.min.css" rel="stylesheet">
    <link href="${basePath}css/min/bootstrap-table.min.css" rel="stylesheet">
    <link href="${basePath}css/tranfer.css" rel="stylesheet">
    <style type="text/css">
    
        .opacity input[type="checkbox"] {
            /* 强制覆盖style.css中的样式，否则复选框不显示 */
            display: block !important;
        }
        .condition {
            border: 1px #bbbbbb solid;
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
                导出申请管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>导出网站用户信息申请
                    </div>
                    <div class="widget-content padded clearfix">
                        <%--<form class="form-horizontal" id="validate-form">--%>
                        <div class="row">
                            <div class="col-md-3">
                                <h3><b>筛选条件</b>（共${count}条数据）</h3>
                                <c:forEach var="i" items="${conditionList }">
                                    <div class="row">
                                        <div class="col-md-6"><b>${i.propertyName}:</b></div>
                                        <%--<div class="col-md-6" style="text-align: right;"><b>${i.propertyName}:</b></div>--%>
                                        <div class="col-md-6"><b>${i.valueName}</b></div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="col-md-7">
                                <%--<div class="row">--%>
                                    <%--<button class="btn btn-default pull-left hidden-xs" id="refresh">刷新</button>--%>
                                    <%--<button class="btn btn-default pull-left hidden-xs" id="btn">获取选中数据</button>--%>
                                <%--</div>--%>
                                <%-- 穿梭框 --%>
                                <h3><b>显示列</b></h3>
                                <div id="transferContainer" class="row" style="height: 450px;"></div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-10">
                                <textarea rows="4" class="form-control" placeholder="请输入申请原因" name="applyReason" id="applyReason"></textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-10">
                                <div class="checkbox">
                                    <label>
                                        用户敏感信息是否加密
                                    </label>
                                    <input type="checkbox" name="isEncryptChk" id="isEncryptChk" >
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="text-center">
                                <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                                <button class="btn btn-primary" type="button" onclick="addExportApply()">添加</button>
                            </div>
                        </div>
                        <%--</form>--%>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<%--<script src="${basePath}js/select2.js" type="text/javascript"></script>--%>
<%--<script src="${basePath}js/lifepic/jquery.min.js"></script>--%>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/min/3.3.1/jquery.min.js"></script>
<script src="${basePath}js/min/bootstrap.min.js"></script>
<script src="${basePath}js/min/bootstrap-table.min.js"></script>
<script src="${basePath}js/min/bootstrap-table-zh-CN.min.js"></script>
<script src="${basePath}js/transfer.js"></script>
<script type="text/javascript">
    $(function () {
        $("#isEncrypt").val(1);
        var data = [{
            "colCode": "id",
            "colName": "用户ID",
            "flag": true,
        },{
            "colCode": "department_name",
            "colName": "所属部门",
            "flag": true,
        },{
            "colCode": "channel_name",
            "colName": "创建渠道",
            "flag": true,
        },{
            "colCode": "username",
            "colName": "用户名",
            "flag": true,
        },{
            "colCode": "trueName",
            "colName": "真实姓名",
            "flag": true,
        },{
            "colCode": "phone",
            "colName": "手机号",
            "flag": true,
        },{
            "colCode": "LEVEL",
            "colName": "会员等级",
            "flag": false,
        },{
            "colCode": "ct",
            "colName": "绑卡状态",
            "flag": false,
        },{
            "colCode": "balance_amount",
            "colName": "余额",
            "flag": false,
        },{
            "colCode": "frozen_amount",
            "colName": "冻结余额",
            "flag": false,
        },{
            "colCode": "credit_amount",
            "colName": "授信金额",
            "flag": false,
        },{
            "colCode": "freozen_credit_amount",
            "colName": "授信冻结金额",
            "flag": false,
        },{
            "colCode": "status",
            "colName": "状态",
            "flag": false,
        },{
            "colCode": "sex",
            "colName": "性别",
            "flag": false,
        },{
            "colCode": "create_date",
            "colName": "注册时间",
            "flag": false,
        },{
            "colCode": "last_login_time",
            "colName": "最后登录时间",
            "flag": false,
        },{
            "colCode": "uiPhone",
            "colName": "推荐人手机号",
            "flag": false,
        },{
            "colCode": "uiRealName",
            "colName": "推荐人真实姓名",
            "flag": false,
        }];
    
        $('#transferContainer').transfer({
            titles: ['待选列', '已选列'],
            search: true,
            uniqueId: "colCode", //唯一id
            dataSource: data,
            maxSelect: 18,
            diffKey: 'flag',
            unselectColumns: [{
                field: 'flag',
                checkbox: true
            }, {
                field: 'colName',
                title: '网站用户列名'
            }]
        });
    
        /* 获取选中数据 */
        $('#btn').click(function() {
            var data = $('#transferContainer').transfer('getData', 'selectData');
            console.log(data)
        });
    
        $('#refresh').click(function() {
            $('#transferContainer').transfer('refresh', data)
        });

        // $("#validate-form").validate();
    });
    
    function addExportApply() {

        var applyReason = $.trim($("#applyReason").val());
        // console.log(applyReason);
        var isEncryptChk = document.getElementById("isEncryptChk").checked;
        // console.log(isEncryptChk);
        var exportApplyConditions = ${conditionList};
        // console.log(exportApplyConditions);
        // 选中的列
        var exportApplyColumns = $('#transferContainer').transfer('getData', 'selectData');
        if (exportApplyColumns == null || exportApplyColumns.length == 0) {
            alert("请选择导出数据的列");
            return;
        }
        if (applyReason == '' || exportApplyColumns.length == 0) {
            alert("请输入申请原因");
            return;
        }
        var params = {
            "applyReason": applyReason,
            "encrypt": isEncryptChk ? 1 : 0,
            "encryptColumn": "trueName,true_name,phone",
            "exportApplyConditions": exportApplyConditions,
            "exportApplyColumns": exportApplyColumns
        };
        console.log(params);
        $.ajax({
            type: 'post',
            url: "${basePath}exportApply/add",
            data: params,
            dataType: 'json',
            // traditional: true,
            success : function(data) {
                if (data == "success") {
                    if (confirm("是否前往导出申请列表?")) {
                        location.href = "${basePath}exportApply/exportApplyList";
                    } else {
                        window.history.go(-1);
                    }
                } else {
                    alert("添加失败");
                }
            }
        }); 
    }
    
</script>
</body>
</html>
