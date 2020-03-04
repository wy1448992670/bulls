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
    <title>派发体验金</title>
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
                派发体验金
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>派发体验金
                    </div>
                    <div class="widget-content padded clearfix">
                        <form method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">体验金金额</label>

                                <div class="col-md-7">
                                    <input class="form-control" value="100" name="amount" id="amount"
                                           placeholder="请输入需要派发的利率" type="text">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">指定到用户</label>

                                <div class="col-md-7">
                                    <input id="selectUser" name="userId" type="hidden"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" data-loading-text="努力派发中..." type="button"
                                            id="send">派发
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
        $('.select2able').select2();
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
        $("#send").click(function () {
            var amount = $.trim($("#amount").val());
            if (amount == '') {
                alert('请输入需要派发的金额');
                return false;
            }
            if (confirm("您确定给用户派发体验金吗?!")) {
                $.ajax({
                    url: '${basePath}exp/sendExpMoney',
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