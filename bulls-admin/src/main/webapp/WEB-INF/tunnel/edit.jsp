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
    <title>编辑通道</title>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
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
                通道管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑通道
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}tunnel/edit" method="post" class="form-horizontal" id="validate-form">
                            <input type="hidden" name="id" value="${tunnel.id }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2">通道名称</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" value="${tunnel.name }"
                                           placeholder="请输入通道名称" type="text">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">通道类型</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="type">
                                        <option value="0" <c:if test="${tunnel.type==0 }">selected</c:if>>支付</option>
                                        <option value="1" <c:if test="${tunnel.type==1 }">selected</c:if>>提现</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">权重</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="weight" value="${tunnel.weight }" id="weight"
                                           placeholder="请输入通道权重0-100之间" type="text">
                                    <em style="color:red;">&nbsp;* 支付或提现优先级会根据权重数值排序，数值越大，优先级越高。</em>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">状态</label>

                                <div class="col-md-7">
                                    <select class="select2able" name="status">
                                        <option value="0" <c:if test="${tunnel.status==0 }">selected</c:if>>激活</option>
                                        <option value="1" <c:if test="${tunnel.status==0 }">selected</c:if>>屏蔽</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">编辑</button>
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
        $("#validate-form").validate({
            rules: {
                name: {
                    required: true,
                    maxlength: 255
                },
                weight: {
                    required: true,
                    range: [0, 100]
                }
            },
            messages: {
                name: {
                    required: "请输入通道名称",
                    maxlength: "通道名称不能超过255个字"
                },
                weight: {
                    required: "请输入通道权重",
                    range: "权重在0-100之间"
                }
            }

        });
    });
</script>
</body>
</html>
