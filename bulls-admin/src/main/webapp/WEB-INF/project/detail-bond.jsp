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
    <title>债券转让详细信息</title>
    <link href="${basePath}css/select2.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/jquery.fancybox.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="${basePath}css/font-awesome.css" media="all" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        .upload-picture a {
            display: inline-block;
            overflow: hidden;
            border: 0;
            vertical-align: top;
            margin: 0 5px 10px 0;
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
            <h1>
                项目管理
            </h1>
        </div>
        <!-- DataTables Example -->
        <div class="row">
            <div class="col-lg-12">
                <div class="widget-container fluid-height clearfix">
                    <div class="heading">
                        <i class="icon-table"></i>债券转让详细信息
                    </div>
                    <div class="widget-content padded clearfix">
                        <form class="form-horizontal" id="validate-form">
                            <div class="form-group">
                                <label class="control-label col-md-2">转让者</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <a href="${basePath}user/detail/app?id=${bond.userId}">${bond.user.username }</a>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">转让金额</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        ${bond.totalAmount }
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">项目名称</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <a href="${basePath}project/detail?id=${bond.parentId}">${bond.project.title }</a>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">创建时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${bond.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">转让截至时间</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <fmt:formatDate value="${bond.deadline }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2">转让状态</label>

                                <div class="col-md-7">
                                    <p class="form-control-static">
                                        <c:if test="${bond.status == 0 }"><span
                                                class="label label-default">转让中</span></c:if>
                                        <c:if test="${bond.status == 1 }"><span
                                                class="label label-success">转让完成</span></c:if>
                                    </p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">投资详细</label>

                                <div class="col-md-7">
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>
                                                序号
                                            </th>
                                            <th>
                                                用户名
                                            </th>
                                            <th>
                                                用户昵称
                                            </th>
                                            <th>
                                                注册时间
                                            </th>
                                            <th>
                                                投资额
                                            </th>

                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="g" items="${list }" varStatus="t">
                                            <tr>
                                                <td>
                                                        ${t.count }
                                                </td>
                                                <td>
                                                        ${g.username }
                                                </td>
                                                <td>
                                                        ${g.true_name }
                                                </td>

                                                <td>
                                                    <fmt:formatDate value="${g.register_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
                                                </td>
                                                <td>
                                                        ${g.amount }
                                                </td>

                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <ul id="pagination" style="float: right"></ul>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-2"></label>

                                <div class="col-md-7 text-center">
                                    <a class="btn btn-default-outline" href="javascript:history.go(-1);">返回</a>
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
<script src="${basePath}js/jquery.fancybox.pack.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('.select2able').select2();

        $(".fancybox").fancybox({
            maxWidth: 700,
            height: 'auto',
            fitToView: false,
            autoSize: true,
            padding: 15,
            nextEffect: 'fade',
            prevEffect: 'fade',
            helpers: {
                title: {
                    type: "outside"
                }
            }
        });
    });
</script>
</body>
</html>