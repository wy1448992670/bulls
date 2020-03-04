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
    <title>编辑活动</title>
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
                编辑活动
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑活动
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}activity/updateChrismasLottery" method="post" class="form-horizontal"
                              id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">奖品单号</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${map.id }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">奖品名称</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${map.name }
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">中奖人</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${map.true_name != null }">${map.true_name }</c:if>
                                        <c:if test="${map.true_name == null }">${map.username }</c:if>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">中奖人电话</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${map.phone != null }">${map.phone }</c:if>
                                        <c:if test="${map.phone == null }">${map.loginPhone }</c:if>

                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">中奖时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${map.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">奖品状态</label>

                                <div class="col-md-7">
                                    <select class="select2able" id="status" name="status">
                                        <option value="0" <c:if test="${map.status == 0 }">selected</c:if>>未中奖</option>
                                        <option value="1" <c:if test="${map.status == 1 }">selected</c:if>>未发放</option>
                                        <option value="2" <c:if test="${map.status == 2 }">selected</c:if>>已发放</option>
                                        <option value="3" <c:if test="${map.status == 3 }">selected</c:if>>用户已收货
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <input style="display:none;" class="form-control" name="id" id="id" value="${map.id }">
                            <c:if test="${map.giftId != 3 and map.giftId != 4 and map.giftId != 10 }">
                                <div class="form-group">
                                    <label class="control-label col-md-2">收件人姓名</label>

                                    <div class="col-md-7">
                                        <input class="form-control" name="postName" id="postName" placeholder="请输入收件人姓名"
                                               type="text" value="${map.postName }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">收件人电话</label>

                                    <div class="col-md-7">
                                        <input class="form-control" name="postPhone" id="postPhone"
                                               placeholder="请输入收件人电话" type="text" value="${map.postPhone }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">收件地址</label>

                                    <div class="col-md-7">
                                        <input class="form-control" name="postAddress" id="postAddress"
                                               placeholder="请输入收件地址" type="text" value="${map.postAddress }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">邮编</label>

                                    <div class="col-md-7">
                                        <input class="form-control" name="postCode" id="postCode" placeholder="请输入邮编"
                                               type="text" value="${map.postCode }">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">快递机构</label>

                                    <div class="col-md-7">
                                        <select class="select2able" id="trackType" name="trackType">
                                            <option value="">请选择快递机构</option>
                                            <option value="1" <c:if test="${map.trackType == 1 }">selected</c:if>>顺丰快递
                                            </option>
                                            <option value="2" <c:if test="${map.trackType == 2 }">selected</c:if>>圆通快递
                                            </option>
                                            <option value="3" <c:if test="${map.trackType == 3 }">selected</c:if>>韵达快递
                                            </option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">快递单号</label>

                                    <div class="col-md-7">
                                        <input class="form-control" name="trackNo" id="trackNo" placeholder="请输入快递单号"
                                               type="text" value="${map.trackNo }">
                                    </div>
                                </div>
                            </c:if>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">提交</button>
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
    });
</script>
</body>
</html>