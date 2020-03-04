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
    <title>推送模板</title>
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
                        <i class="icon-table"></i>推送模板
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}user/editUserPush" method="post" class="form-horizontal"
                              id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">模板名称</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" placeholder="请输入模板名称"
                                           value="${template.name }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">推送描述</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入推送描述，默认为鑫聚财"
                                           value="<c:if test="${template.title == null}">鑫聚财</c:if><c:if test="${template.title != null}">${template.title}</c:if>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">推送内容</label>

                                <div class="col-md-7">
                                    <textarea rows="10" class="form-control" name="content" id="content"
                                              placeholder="请输入推送内容">${template.content }</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">推送链接</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="url" id="url" placeholder="请输入推送链接"
                                           value="${template.url }">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">推送类型</label>

                                <div class="col-md-7">
                                    <select class="select2" name="type" id="type">
                                        <option value="0" <c:if test="${template.type == 0 }">selected</c:if>>单播
                                        </option>
                                        <option value="1" <c:if test="${template.type == 1 }">selected</c:if>>广播
                                        </option>
                                        <option value="2" <c:if test="${template.type == 2 }">selected</c:if>>列播
                                        </option>
                                        <option value="3" <c:if test="${template.type == 3 }">selected</c:if>>其他
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">推送状态</label>

                                <div class="col-md-7">
                                    <select class="select2" name="status" id="status">
                                        <option value="0" <c:if test="${template.status == 0 }">selected</c:if>>未推送
                                        </option>
                                        <option value="1" <c:if test="${template.status == 1 }">selected</c:if>>已推送
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">指定到用户</label>

                                <div class="col-md-7">
                                    <input id="userId" name="userId" type="hidden"/>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">单播时，必须选择至少一个用户</span></div>
                            </div>
                            <input type="hidden" name="id" id="id" value="${template.id }"/>
                            <input type="hidden" name="isPush" id="isPush" value="0"/>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-success" type="submit" id="pub">提交</button>
                                    <button class="btn btn-warning" type="buttion" id="push">保存并推送</button>
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

<script src="${basePath}js/jquery.validate.js" type="text/javascript"></script>
<script src="${basePath }js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#push').click(function () {
            var userId = $("#userId").val();
            var type = $("#type").val();
            var status = $("#status").val();
            if (status != 1) {
                alert("未推送状态不允许，直接推送");
                return false;
            }
            if (type == 0 || type == 2) {
                if (!userId) {
                    alert("选择单播或者列播时，必须至少推送给一个人");
                    return false;
                }
            }
            $('#isPush').val(1);
        });
        $('#pub').click(function () {
            $('#isPush').val(0);
        });

        $.validator.addMethod("checkType", function () {
            var userId = $("#userId").val();
            var type = $("#type").val();
            if (type == 0 || type == 2) {
                if (!userId) {
                    return false;
                }
            }
            return true;
        }, "选择单播或者列播时，必须至少推送给一个人");

        $('.select2').select2();

        var option = {
            placeholder: "请输入用户昵称、手机号或者真实姓名搜索",
            minimumInputLength: 0,
            multiple: true,
            ajax: {
                url: "${basePath}user/list/app/usable",
                dataType: 'json',
                quietMillis: 100,
                data: function (term) {
                    return {
                        username: term, //search term
                    };
                },
                results: function (data) {
                    return {results: data};
                }
            },
            formatResult: function (object, container, query) {
                return object.username + "(" + object.phone + ")";
            },
            formatSelection: function (object, container) {
                //选中时触发
                return object.username + "(" + object.phone + ")";
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#userId').select2(option);

        $("#validate-form").validate({
            ignore: "",
            rules: {
                /*userId: {
                 checkType:true
                 }*/
            },
            messages: {
                title: {
                    required: "请输入内容标题",
                    maxlength: "标题不能超过64个字符"
                }
            }
        });
    });

    function checkInput() {
        return true;
    }
    ;
</script>
</body>
</html>