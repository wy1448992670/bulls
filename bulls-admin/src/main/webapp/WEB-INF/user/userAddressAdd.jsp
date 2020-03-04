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
    <title>新增用户地址</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                新增用户地址
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>新增用户地址
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}user/userAddressAdd" method="post" class="form-horizontal" id="validate-form" enctype="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">用户信息</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="keyword" id="keyword" placeholder="请输入用户昵称 或 真实用户姓名 或 电话" type="text" value="${username}"
                                           <c:if test="${username != null }">readOnly="true"</c:if> >
                                    <input name="id" id="id" type="hidden" value="${address.id}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">收件人姓名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" placeholder="请输入收件人姓名" type="text" value="${address.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">收件人电话</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="phone" id="phone" placeholder="请输入收件人电话" type="text" value="${address.phone}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">邮编</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="postcode" id="postcode" placeholder="请输入收件人邮编" type="text" value="${address.postcode}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">所属省市县/区</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="area" id="area" placeholder="请输入收件人所属省市" type="text" 
                                           value="${address.provinceName} ${address.cityName} ${address.areaName}" disabled>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">备注</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="remarks" id="remarks" placeholder="请输入备注" type="text"
                                           value="${address.remarks}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">详细收货地址</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="detail" id="detail" placeholder="请输入收件人详细地址" type="text" value="${address.detail}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">是否是默认地址</label>

                                <div class="col-md-7">
                                    <label class="radio-inline">
                                        <input name="reserve" type="radio" value="0" <c:if test="${address.reserve == 0 }">checked</c:if>>
                                        <span>否</span>
                                    </label>
                                    <label class="radio-inline">
                                        <input name="reserve" type="radio" value="1" <c:if test="${address.reserve == 1 }">checked</c:if>>
                                        <span>是</span>
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">添加</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- end DataTables Example -->
    </div>
</div>

<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();
        $("#user-list a:eq(13)").addClass("current");
        var flag =${flag};
        if (flag == 1) {
            alert("保存失败！")
        }
    });
</script>
</body>
</html>
