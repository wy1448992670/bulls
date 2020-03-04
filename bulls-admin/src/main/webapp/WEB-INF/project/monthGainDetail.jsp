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
    <link href="${basePath}js/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <title>查看智投</title>
    <style type="text/css">
    .form-group [class*="col-"] {
      padding-top: 6px;
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
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>智投详细
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}project/account/edit" method="post" class="form-horizontal"
                              id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">产品期限</label>
                                <div class="col-md-7">
                                  ${produtcList.limitDays}天
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">年化利率</label>
                                <div class="col-md-7">
                                   ${produtcList.annualized}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">加息利率</label>

                                <div class="col-md-7">
                                    ${produtcList.addAnnualized}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">实际发放金额</label>
                                <div class="col-md-7">
                                                                   ￥ ${produtcList.investedAmount}(元)
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">标题</label>

                                <div class="col-md-7">
                                    ${produtcList.title}
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">发布时间</label>
                                <div class="col-md-8">
                                   <fmt:formatDate value="${produtcList.startTime}" pattern="yyyy-MM-dd  hh:mm:ss"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">募集完成时间</label>
                                <div class="col-md-7">
                                    <fmt:formatDate value="${produtcList.endTime}" pattern="yyyy-MM-dd  hh:mm:ss"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">投资人数</label>
                                <div class="col-md-7">
                                   ${countInv}人 <a class="btn btn-sm btn-primary-outline" href="getMonthGainInvestment?prackgeId=${produtcList.id}" >查看详细</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="widget-container fluid-height ">
                    <div class="heading">
                        <i class="icon-table"></i>对应资产
                    </div>
                    <div class="widget-content padded clearfix">
                        <table class="table table-bordered trade" id="trade">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>ID</th>
                                <th>标题</th>
                                <th>期限</th>
                                <th>总金额(元)</th>
                                <th>投资金额(元)</th>
                                <th>加入时间</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${projectList }" var="pro" varStatus="index">
	                             <tr>
	                              <td>${index.index+1}</td>
	                              <td>${pro.id}</td>
	                              <td><a href="${basePath}project/detail?id=${pro.id}">${pro.title}</a></td>
	                              <td>${pro.limit_days}</td>
	                              <td>${pro.total_amount}</td>
	                              <td>${pro.invested_amount}</td>
	                              <td><fmt:formatDate value="${pro.create_time}" pattern="yyyy-MM-dd  hh:mm:ss"/></td>
	                             </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
        </div>
    </div>
    <!-- end DataTables Example -->
</div>
</div>

<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            format: 'yyyy-mm-dd'
        });
        $('.select2').select2();
        var option = {
            placeholder: "请选择项目名称",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}project/getProjectAccount?id=${account.projectId}",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        username: term,
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
                //初始化赋值
                callback({id: '${account.projectId}', title: '${account.projectTitle}'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.title;
            },
            formatSelection: function (object, container) {
                //选中时触发
                var id = object.id;
                $('#projectId').val(id);
                return object.title;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };

        $('#projectId').select2(option);
    });

</script>
</body>
</html>