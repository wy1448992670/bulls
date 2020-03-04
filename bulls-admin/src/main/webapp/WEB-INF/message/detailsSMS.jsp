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
    <title>短信管理</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                短信管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>短信管理
                    </div>
                    <div class="widget-content padded clearfix pull-right">
                        <div class="row">
                        	<div class="form-group col-md-3  pull-right">
                                <shiro:hasPermission name="sms:detailsSMS:add">
                                    <a class="btn btn-sm btn-primary-outline pull-right" href="add" id="add-row"><i
                                            class="icon-plus"></i>新增模板</a>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="sms:detailsSMS:send">
                                    <a class="btn btn-sm btn-primary-outline pull-right" href="sendMessage" id="edit-row"><i
                                            class="icon-edit"></i>发送短信</a>
                                </shiro:hasPermission>
                            </div>
                        </div>
						<div class="table-responsive">		
                        <table class="table table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>
                                    模板ID
                                </th>
                                <th>
                                    模板内容
                                </th>
                                <th>
                                    短信平台
                                </th>
                                <th>
                                    使用状态
                                </th>
                                <th>
                                    操作
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="i" items="${list }">
                                <tr>
                                    <td>
                                            ${i.id}
                                    </td>
                                    <td>
                                            ${i.content}
                                    </td>
                                    <td>
                                        <c:if test="${i.type == 0 }">云片信息平台</c:if>
                                        <c:if test="${i.type == 1 }">畅卓信息平台</c:if>
                                    </td>
                                    <td>
                                        <c:if test="${i.status == 0 }">失效</c:if>
                                        <c:if test="${i.status == 1 }">启用</c:if>
                                    </td>
                                    <td>
                                        <shiro:hasPermission name="sms:detailsSMS:edit">
                                            <a href="${basePath}sms/editTemplet?id=${i.id }">编辑</a>&nbsp;
                                        </shiro:hasPermission>
                                        <shiro:hasPermission name="sms:detailsSMS:delete">
                                            <a href="${basePath}sms/deleteTemplet?id=${i.id }" onClick="return confirm('确定删除?');">删除</a>
                                        </shiro:hasPermission>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath}js/comm.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/bootstrap-datepicker.js" type="text/javascript"></script>

<script type="text/javascript">
    $(function () {
        $('.select2able').select2();
        $("#send").click(function () {
            var content = $.trim($("#content").val());
            if (content == '') {
                alert('请输入内容');
                return false;
            }
            if (content.length > 75) {
                alert('长度大于75!');
                return false;
            }
            if (confirm("您确定给用户发送短信吗?!")) {
                $.ajax({
                    url: '${basePath}sms/sendMessage',
                    data: $('#validate-form').serialize(),
                    type: "POST",
                    dataType: "json",
                    beforeSend: function () {
                        $("#send").button('loading');
                    },
                    success: function (data) {
                        alert(data);
                    },
                    complete: function () {
                        $("#send").button('reset');
                    }
                });
            }
        });

    });
</script>
</body>
</html>
