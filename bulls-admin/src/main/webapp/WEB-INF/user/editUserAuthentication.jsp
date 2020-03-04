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
    <title>编辑用户实名操作记录信息</title>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>
                用户管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑用户实名操作记录信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}user/editUserAuthentication" method="post" class="form-horizontal" id="validate-form">
                            <%--<div class="form-group">
                                <label class="control-label col-md-2">银行名称</label>
                                <div class="col-md-7">
                                    <input id="linkId" value="${map.bankName }" name="linkId" type="hidden" />
                                </div>
                            </div>--%>
                            <div class="form-group">
                                <label class="control-label col-md-2">姓名</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="trueName" id="trueName" disabled="true" type="text" value="${map.trueName }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">电话</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="phone" id="phone" disabled="true" type="text" value="${map.phone }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">身份证号</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="cardNo" id="cardNo" disabled="true" type="text" value="${map.identificationNo }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">操作时间</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="time" id="time" disabled="true" type="text" value="<fmt:formatDate value="${map.time}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">操作类型</label>

                                <div class="col-md-7">
                                    <select id="payType" name="payType" class="select2" disabled="true">
                                        <option value="0" <c:if test="${map.type == 0}">selected</c:if>>认证实名</option>
                                        <option value="1" <c:if test="${map.type == 1}">selected</c:if>>解除实名</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">处理状态</label>

                                <div class="col-md-7">
                                    <select id="status" name="status" class="select2" <c:if test="${map.type == 0}">disabled="true"</c:if>>
                                        <option value="0" <c:if test="${map.status == 0}">selected</c:if>>失败</option>
                                        <option value="1" <c:if test="${map.status == 1}">selected</c:if>>已成功</option>
                                        <option value="2" <c:if test="${map.status == 2}">selected</c:if>>进行中</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">上传图片</label>

                                <div class="col-md-7">
                                    <c:forEach var="ii" items="${uploads }">
                                        <div style="display: inline-block;margin-right: 10px;">
                                            <a href="${basePath }upload/${ii.path}" target="_blank">
                                                <img alt="" src="${basePath }upload/${ii.path}" width="120"/>
                                            </a>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">备注内容</label>

                                <div class="col-md-7">
                                    <textarea rows="10" class="form-control" name="note" id="note" placeholder="请输入备注内容">${map.note }</textarea>
                                </div>
                            </div>
                            <input type="hidden" name="id" id="id" value="${map.id }"/>
                            <input type="hidden" name="type" id="type" value="${type}"/>
                            <input type="hidden" name="statuss" id="statuss" value="${status}"/>
                            <input type="hidden" name="page" id="page" value="${page}"/>
                            <input type="hidden" name="keyword" id="keyword" value="${keyword}"/>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-success" type="submit" id="pub">保存</button>
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

<script src="${basePath}js/ueditor/ueditor.config.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${basePath}js/ueditor/ueditor.parse.min.js" type="text/java script"></script>
<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        $.validator.addMethod("checkType", function () {
            var ruleType = $("#ruleType").val();
            var triggerExpression = $("#triggerExpression").val();
            if (ruleType == 2) {
                if (triggerExpression == undefined || triggerExpression == null || triggerExpression == "") {
                    return false;
                }
            }
            return true;
        }, "选择触发规则时，触发表达式不能为空");

        $('.select2').select2();

        var ue = UE.getEditor('detail', {
            initialFrameHeight: 500
        });
        ue.ready(function () {
            ue.execCommand('insertHtml', $('#htmlContent').text());
        });
        ue.addListener("keyup", function () {
            $('#htmlContent').text(ue.getContent());
            $('#textContent').text(ue.getPlainTxt());
        });

        $("#validate-form").validate({
            ignore: "",
            rules: {
                bankId: {
                    required: true
                },
                title: {
                    required: true,
                    maxlength: 255
                },
                payType: {
                    required: true
                },
                ruleType: {
                    required: true
                },
                status: {
                    required: true
                }
                ,
                triggerExpression: {
                    checkType: true
                }
            },
            messages: {
                bankId: {
                    required: "请选择银行名称"
                },
                title: {
                    required: "请输入规则标题",
                    maxlength: "标题不能超过255个字符"
                },
                payType: {
                    required: "请选择支付方式"
                },
                ruleType: {
                    required: "请输入规则类型"
                }
//					 ,
//					 check:{
//						 required:"---------"
//					 }
            }
        });


        $('#linkId').select2({
            placeholder: "请选择银行机构",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}bank/getBankList",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        keyword: term, //search term
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            initSelection: function (element, callback) {
                callback({id: '${map.bankId}', name: '${map.bankName}'});//调用formatSelection
            },
            formatResult: function (object, container, query) {
                return object.name;
            },
            formatSelection: function (object, container) {
                //选中时触发
                return object.name;
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        });

    });
</script>
</body>
</html>