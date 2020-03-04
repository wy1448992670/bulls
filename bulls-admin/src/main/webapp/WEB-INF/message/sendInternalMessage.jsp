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
    <title>发送站内信</title>
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
                站内信管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>发送站内信 
                    </div>
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form"
                              action="${basePath}sms/send/internal/msg"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <label class="control-label col-md-2">站内信标题</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="title" id="title" placeholder="请输入需要发送的站内信标题"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">短信内容</label>

                                <div class="col-md-7">
                                    <textarea class="form-control" rows="10" name="content" id="content"
                                              placeholder="请输入需要发送的站内信内容"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">指定到用户</label>

                                <div class="col-md-7">
                                    <input id="selectUser" name="userId" type="hidden"/>
                                </div>
                                <div class="col-md-3"><span class="label label-danger">如不选择用户，则发送给所有用户！</span></div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">批量发送</label>

                                <div class="col-md-7">
                                    <input type="file" name="file" title="选择txt文件" class="btn-file"/>
                                </div>
                            </div>
                            <div class="col-md-3" id="temp">
                                    <a class="btn btn-success pull-right hidden-xs" href="${basePath}template/internalMessage.txt" download="下载站内信模版">下载站内信模版(.txt格式)</a>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" data-loading-text="努力发送中..." type="button"
                                            id="send">发送
                                    </button>
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
        $('#selectUser').select2(option);
        $("#send").click(function (e) {
            var title = $.trim($("#title").val());
            var content = $.trim($("#content").val());
            if (title == '' && content == '') {
                alert('请输入标题和内容');
                return false;
            }
            if (confirm("您确定给用户发送站内信吗?!")) {
                stopDefault(e);
                $("#validate-form").submit();
                <%--$.ajax({--%>
                <%--url: '${basePath}sms/send/internal/msg',--%>
                <%--type: 'post',--%>
                <%--data: $('#validate-form').serialize(),--%>
                <%--success: function (data) {--%>
                <%--alert("发送成功");--%>
                <%--},--%>
                <%--error: function () {--%>
                <%--alert('发送失败');--%>
                <%--},--%>
                <%--complete: function () {--%>
                <%--window.location.reload();--%>
                <%--}--%>
                <%--});--%>
            }
        });

        if (${!empty flag}) {
            if (${flag==1}) {
                alert("发送成功");
                window.location.href = "${basePath}sms/internal/msg/list";
            } else {
                alert("发送失败");
                window.location.reload();
            }
        }
    });

    function stopDefault(e) {
        if (e && e.preventDefault) {
            e.preventDefault();
        } else {
            window.event.returnValue = false;
        }
        return false;
    }
</script>
</body>
</html>