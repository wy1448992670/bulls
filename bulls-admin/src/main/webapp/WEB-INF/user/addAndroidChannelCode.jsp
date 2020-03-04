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
    <title>编辑渠道代码记录</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet"
          type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all"
          rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/datepicker.css" media="all" rel="stylesheet"
          type="text/css"/>
    <style type="text/css">
        .table {
            table-layout: fixed;
        }

        .table .over {
            overflow: hidden;
            width: 40%;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    </style>
</head>
<body>
<div class="modal-shiftfix">
    <!-- Navigation -->
    <jsp:include page="../common/header.jsp"></jsp:include>
    <!-- End Navigation -->
    <div class="container-fluid main-content">
        <div class="page-title">
            <h1>渠道代码管理</h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>编辑渠道代码记录
                    </div>
                    <div class="widget-content padded clearfix">
                        <%-- <form action="reply?id=${id}" method="post" --%>
                        <form action="${basePath}user/editAndroidChannel" method="post"
                              class="form-horizontal" id="validate-form" onsubmit="return check();">

                            <div class="form-group">

                                <label class="control-label col-md-2">渠道分类</label>
                                <div class="col-md-7">
                                    <select class="select2able" name="source" onchange="" >
                                        <option value="0">安卓</option>
                                        <option value="1">微信</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">

                                <label class="control-label col-md-2">渠道代码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="code" id="code" placeholder="请输入安卓渠道代码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">机构名称</label>

                                <div class="col-md-7">
                                    <input id=company name="company" type="hidden" placeholder="请选择机构名称"/>
                                </div>

                            </div>

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
<script src="${basePath}js/bootstrap-datepicker.js"
        type="text/javascript"></script>
<script type="text/javascript"
        src="${basePath}js/bootstrap-paginator.min.js"></script>
<script src="${basePath}js/select2.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2').select2();
        $('.select2able').select2();
        var option = {
            placeholder: "请输入渠道代码对应的角色组",
            minimumInputLength: 0,
            ajax: {
                url: "${basePath}user/androidChannelManagementSelect",
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
                return object.role + "(" + object.description + ")";
            },
            formatSelection: function (object, container) {
                //选中时触发
                var id = object.id;
                $('#company').val(id);
                return object.role + "(" + object.description + ")";
            },
            escapeMarkup: function (m) {
                return m;
            } // we do not want to escape markup since we are displaying html in results
        };
        $('#company').select2(option);


    });


    function check() {
        var code = $("#code").val();
        var company = $("#company").val();
        if (code == undefined || code == null || code == "") {
            alert("渠道代码不能为空！");
            return false;
        } else if (company == undefined || company == null || company == "") {
            alert("机构不能为空！");
            return false;
        } else {
            if (confirm("是否确定新增该渠道代码配置？")) {
                return true;
            } else {
                return false;

            }
        }
    }
    ;


</script>
</body>
</html>