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
    <title>质押应收账款清单编辑</title>
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
                        <i class="icon-table"></i>质押应收账款清单编辑
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}project/account/edit" method="post" class="form-horizontal"
                              id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">配置项目</label>

                                <div class="col-md-7">
                                    <input id=projectId name="projectId" type="hidden" placeholder="请选择你要配置的项目"
                                           value="${account.projectId }" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">第三方债务人名称及通讯信息</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="userInfo" id="userInfo"
                                           placeholder="请输入第三方债务人名称及通讯信息"
                                           value="${account.userInfo }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">交易合同名称</label>

                                <div class="col-md-7">
                                    <input type="text" class="form-control" name="name" value="${account.name}"
                                           placeholder="请输入交易合同名称" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">交易合同编号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="orderNo" type="text" placeholder="请输入交易合同编号"
                                           value="${account.orderNo }" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">签署日期</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="signDate" type="text"
                                           placeholder="请选择签署日期"
                                           value="<fmt:formatDate value="${account.signDate }" pattern="yyyy-MM-dd"/>"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">应收账款基础交易</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="basicAmount" type="text" placeholder="请输入应收账款基础交易"
                                           value="${account.basicAmount }" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">应收账款金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="amount" type="text" placeholder="请输入应收账款金额"
                                           value="${account.amount }" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">应收账款到期日</label>

                                <div class="col-md-7">
                                    <input class="form-control datepicker" name="endTime" type="text"
                                           placeholder="请选择应收账款到期日"
                                           value="<fmt:formatDate value="${account.endTime }" pattern="yyyy-MM-dd"/>"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <select class="select2" name="status">
                                        <option value="0" <c:if test="${account.status == 0 }">selected</c:if>>启用
                                        </option>
                                        <option value="1" <c:if test="${account.status == 1 }">selected</c:if>>停止
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <input type="hidden" name="id" id="id" value="${account.id }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-success" type="submit" id="pub">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
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