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
    <title>用户反馈查询</title>
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
            <h1>用户管理</h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>用户反馈内容编辑
                    </div>
                    <div class="widget-content padded clearfix">
                        <%-- <form action="reply?id=${id}" method="post" --%>
                        <form action="" method="post"
                              class="form-horizontal" id="validate-form" onsubmit="return check();">

                            <c:if test="${path != null}">
                                <div class="form-group">
                                    <label class="control-label col-md-2"></label>

                                    <div class="col-md-7">
                                        <p class="form-control-static">
                                            <img alt="" src="${aPath}upload/${path }" width="300px;"/>
                                        </p>
                                    </div>
                                </div>
                            </c:if>

                            <div class="form-group">
                                <label class="control-label col-md-2">问题描述</label>

                                <div class="col-md-7">
                                        <textarea rows="5" class="form-control" placeholder="" disabled="disabled"
                                                  name="projectDescription">${content }</textarea>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="control-label col-md-2">回复内容</label>

                                <div class="col-md-7">
										<textarea rows="10" class="form-control" name="replyContent"
                                                  id="replyContent" placeholder="请输入回复内容"></textarea>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="text-center col-md-7">
                                    <a class="btn btn-default-outline"
                                       onclick="javascript:window.history.go(-1);">取消</a>
                                    <button class="btn btn-primary" type="submit">回复</button>
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
    function check() {
        var content = $("#replyContent").val();
        if (content == undefined || content == null || content == "") {
            alert("回复内容为空！");
            return false;
        } else {
            if (confirm("是否确认回复反馈信息？")) {
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