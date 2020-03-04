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
    <title>编辑卡规则</title>
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
                        <i class="icon-table"></i>编辑卡规则
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}user/editCardRule" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">银行名称</label>

                                <div class="col-md-7">
                                    <input id="linkId" value="${map.bankId }" name="linkId" type="hidden"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">规则标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入规则标题" type="text" value="${map.title }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">支付类型</label>

                                <div class="col-md-7">
                                    <select id="payType" name="payType" class="select2">
                                        <option value="0" <c:if test="${map.payType == 0}">selected</c:if>>绑卡支付</option>
                                        <option value="1" <c:if test="${map.payType == 1}">selected</c:if>>网银支付</option>
                                        <option value="2" <c:if test="${map.payType == 2}">selected</c:if>>快捷支付</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">规则类型</label>

                                <div class="col-md-7">
                                    <select id="ruleType" name="ruleType" class="select2">
                                        <option value="0" <c:if test="${map.ruleType == 0}">selected</c:if>>排除</option>
                                        <option value="1" <c:if test="${map.ruleType == 1}">selected</c:if>>包含</option>
                                        <option value="2" <c:if test="${map.ruleType == 2}">selected</c:if>>触发</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">规则状态</label>

                                <div class="col-md-7">
                                    <select id="status" name="status" class="select2">
                                        <option value="0" <c:if test="${map.status == 0}">selected</c:if>>待定</option>
                                        <option value="1" <c:if test="${map.status == 1}">selected</c:if>>启用</option>
                                        <option value="2" <c:if test="${map.status == 2}">selected</c:if>>放弃</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">表达式说明</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">1.英文字母“*”代表匹配数据，如：“6225*”,选择排除时，代表以6225开头的数据；选择包含时，代表开头包含6225的数据</p>

                                    <p class="form-control-static">2.选择排除类型时，如果不输入触发规则，则排除整个银行；如果输入触发规则，则参考1</p>

                                    <p class="form-control-static">3.选择包含类型时，如果不输入触发规则，则包含整个银行；如果输入触发规则，则参考1</p>

                                    <p class="form-control-static">4.选择触发类型时，必须填写规则，功能待定</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">触发表达式</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="triggerExpression" id="triggerExpression" placeholder="请输入触发表达式" type="text" value="${map.triggerExpression }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">规则详情</label>

                                <div class="col-md-7">
                                    <!-- 加载编辑器的容器 -->
                                    <script id="detail" name="detail" type="text/plain" height="500"></script>
                                    <textarea rows="" cols="" hidden="true" name="htmlDetail" id="htmlDetail">${map.htmlDetail }</textarea>
                                    <textarea rows="" cols="" hidden="true" name="textDetail" id="textDetail">${map.textDetail }</textarea>
                                </div>
                            </div>
                            <input type="hidden" name="id" id="id" value="${map.id }"/>
                            <input type="hidden" name="keywords" id="keywords" value="${keyword}"/>
                            <input type="hidden" name="payTypes" id="payTypes" value="${payType}"/>
                            <input type="hidden" name="ruleTypes" id="ruleTypes" value="${ruleType}"/>
                            <input type="hidden" name="statuss" id="statuss" value="${status}"/>
                            <input type="hidden" name="pages" id="pages" value="${page}"/>

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
            $('#htmlDetail').text(ue.getContent());
            $('#textDetail').text(ue.getPlainTxt());
        });

        $('#pub').click(function () {
            $('#htmlDetail').text(ue.getContent());
            $('#textDetail').text(ue.getContentTxt());
        });
        $("#user-list a:eq(0)").addClass("current");
        $("#user-list a:eq(12)").addClass("current");
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