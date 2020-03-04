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
    <title>编辑银行信息</title>
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
                        <i class="icon-table"></i>编辑银行信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <form action="${basePath}user/editBankManagement" method="post" class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">银行名称</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="name" id="name" placeholder="请输入银行名称"
                                           value="${bank.name }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">银行代码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="code" id="code" disabled="true" placeholder="请输入银行代码"
                                           value="${bank.code }">
                                </div>
                            </div>
                           <%--  <div class="form-group">
                                <label class="control-label col-md-2">连连代码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="llCode" id="llCode" disabled="true" placeholder="请输入连连代码"
                                           value="${bank.llCode }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">新浪代码</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="sinaCode" id="sinaCode" disabled="true" placeholder="请输入EC银行编码"
                                           value="${bank.sinaCode }">
                                </div>
                            </div>
 --%>
                            <div class="form-group">
                                <label class="control-label col-md-2">首次绑定最大限额（元）</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="firstBindMaxAmount" id="firstBindMaxAmount" placeholder="请输入首次绑定最大限额"
                                           value="${bank.firstBindMaxAmount }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">绑卡每日最大限额（元）</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="bindDailyMaxAmount" id="bindDailyMaxAmount" placeholder="请输入绑卡每日最大限额"
                                           value="${bank.bindDailyMaxAmount }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">绑卡单笔最大限额（元）</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="bindSingleMaxAmount" id="bindSingleMaxAmount" placeholder="请输入绑卡单笔最大限额"
                                           value="${bank.bindSingleMaxAmount }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">绑卡单笔最小限额（元）</label>

                                <div class="col-md-7">
                                    <input class="form-control" name="bindSingleMinAmount" id="bindSingleMinAmount" placeholder="请输入绑卡单笔最小限额"
                                           value="${bank.bindSingleMinAmount }">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">银行公告</label>

                                <div class="col-md-7">
                                    <textarea rows="2" class="form-control" name="announcement" id="announcement"
                                              placeholder="请输入备注内容">${bank.announcement }</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">备注内容</label>

                                <div class="col-md-7">
                                    <textarea rows="10" class="form-control" name="note" id="note"
                                              placeholder="请输入备注内容">${bank.note }</textarea>
                                </div>
                            </div>

                            <input type="hidden" name="id" id="id" value="${bank.id }"/>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline" onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-success" type="submit" id="pub">提交</button>
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
        $('#pub').click(function () {
        });

        $('.select2').select2();

        $("#validate-form").validate({
            ignore: "",
            rules: {
                name: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "请输入银行名称",
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